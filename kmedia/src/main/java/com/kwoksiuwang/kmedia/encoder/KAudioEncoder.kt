package com.kwoksiuwang.kmedia.encoder

import android.media.*
import com.kowksiuwang.everythingaboutandroid.media.encoder.BaseEncoder
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.lang.Exception

class KAudioEncoder : BaseEncoder() {

    private var audioRecord: AudioRecord? = null
    private var audioEncoder: MediaCodec? = null

    private val simpleRate = 44100
    private val bitRate = 64000
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private val audioChannel = AudioFormat.CHANNEL_IN_DEFAULT
    private val minBufferSize: Int by lazy {
        AudioRecord.getMinBufferSize(
            simpleRate,
            audioChannel,
            audioFormat
        )
    }

    private val mediaFormat: MediaFormat by lazy {
        val mediaFormat = MediaFormat.createAudioFormat(
            MediaFormat.MIMETYPE_AUDIO_AAC,
            simpleRate,
            audioChannel
        )
        mediaFormat.setInteger(
            MediaFormat.KEY_AAC_PROFILE,
            MediaCodecInfo.CodecProfileLevel.AACObjectLC
        )
        mediaFormat.setInteger(MediaFormat.KEY_CHANNEL_MASK, AudioFormat.CHANNEL_IN_MONO)
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, bitRate)
        mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 100 * 1024)
        mediaFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, audioChannel)
        mediaFormat
    }

    private val statusIdle = 0
    private val statusInit = 1
    private val statusRunning = 2
    private val statusPause = 3
    private val statusStop = 4
    private var status = statusIdle

    private var scope = CoroutineScope(Job() + Dispatchers.Default)
    private var readRecorderJob: Job? = null
    private var writeEncoderJob: Job? = null
    private var readEncoderJob: Job? = null

    private var channel = Channel<ByteArray>()

    private var ptusUtils = PTUSUtils()

    /**
     * @throws
     */
    override fun init() {
        super.init()
        if (status != statusIdle) {
            return
        }
        log("init")
        //init Encoder
        val codecName = findEncoderName()
        if (codecName.isEmpty()) {
            throw Exception("无可用的编码器。")
        }
        log("获取到解码器 ： $codecName")
        audioEncoder = MediaCodec.createByCodecName(codecName)
//        audioEncoder =  MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC)

        //init audioRecord
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            simpleRate,
            audioChannel,
            audioFormat,
            minBufferSize
        )
        status = statusInit
    }

    override fun start() {
        super.start()
        if (status != statusInit && status != statusStop) {
            log("start fail. current status:$status")
            return
        }
        log("start")
        ptusUtils.start()

        status = statusRunning
        audioRecord?.startRecording()

        //每次start之前，都要configure一次。
        audioEncoder!!.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        audioEncoder?.start()

        openJobs()

    }

    override fun pause() {
        super.pause()
        if (status == statusRunning) {
            ptusUtils.pause()
            status = statusPause
            log("pause")
        }
    }

    override fun restart() {
        super.restart()
        if (status == statusPause) {
            ptusUtils.restart()
            status = statusRunning
            log("restart")
        }
    }

    override fun stop() {
        super.stop()
        if (status != statusRunning && status != statusPause) {
            return
        }
        log("stop")
        status = statusStop
        stopJobs()
        ptusUtils.stop()
        try {
            audioRecord?.stop()
            audioEncoder?.stop()
        } catch (e: Exception) {
            log("stop error: $e")
        }
    }

    override fun release() {
        super.release()
        log("release")
        status = statusStop
        try {
            audioRecord?.release()
            audioEncoder?.release()
        } catch (e: Exception) {
            log("release error: $e")
        }
        status = statusIdle
    }

    private fun openJobs() {
        readRecorder()
        write2Encoder()
        readFromEncoder()
    }

    private fun stopJobs() {
        scope.launch {
            readRecorderJob?.cancel()
            readEncoderJob?.cancel()
            writeEncoderJob?.cancel()
        }
    }

    private fun readRecorder() {
        readRecorderJob = scope.launch(Dispatchers.Default) {
            while (isActive && (status == statusRunning || status == statusPause)) {
//                if (status == statusRunning) {
                val tempData = ByteArray(minBufferSize)
                audioRecord?.read(tempData, 0, minBufferSize)
                channel.offer(tempData)
//                }

            }
        }
    }

    private fun write2Encoder() {
        writeEncoderJob = scope.launch(Dispatchers.Default) {
            while (isActive) {
                if (status == statusRunning) {
                    val data = async { channel.receive() }
                    //-1 mean wait forever
                    val index = audioEncoder?.dequeueInputBuffer(100000L) ?: -1
                    if (index >= 0) {
//                        log("write2Encoder write 1")
                        val buffer = audioEncoder?.getInputBuffer(index)
                        if (isActive && (status == statusRunning)) {
                            val ptus = ptusUtils.getPTUS()
//                            log("ptus $ptus")
                            log("write2Encoder write 2 $ptus")
                            buffer?.let {
                                it.put(data.await())
                                audioEncoder?.queueInputBuffer(
                                    index,
                                    0,
                                    data.await().size,
                                    ptus,
                                    0
                                )
                            }
                        }
                    } else {
                        log("write2Encoder have nothing to input")
                    }
                } else {
                    log("write2Encoder not running")
                    delay(100)
                }
            }
        }
    }

    private fun readFromEncoder() {
        readEncoderJob = scope.launch(Dispatchers.Default) {
            while (isActive) {
                if (status == statusRunning || status == statusPause) {
                    try {
//                        log("readFromEncoder start read")
                        val bufferInfo = MediaCodec.BufferInfo()
                        //wait and get the buffer
                        val index =
                            audioEncoder?.dequeueOutputBuffer(bufferInfo, 100000L)
                                ?: MediaCodec.INFO_TRY_AGAIN_LATER
                        when (index) {
                            MediaCodec.INFO_TRY_AGAIN_LATER -> {
//                                log("readFromEncoder try later")
                                //do nothing,just try again later
                            }
                            MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                                log("readFromEncoder format changed")
                                audioEncoder?.outputFormat?.let { onFormatChanged(it) }
                            }
                            else -> {
                                if (status == statusRunning) {
//                                    log("readFromEncoder read")
                                    audioEncoder?.getOutputBuffer(index)?.let {
                                        onDataAvailable(it, bufferInfo)
                                        audioEncoder?.releaseOutputBuffer(index, false)
                                    }
                                } else {
//                                    log("readFromEncoder immediate release")
                                    audioEncoder?.releaseOutputBuffer(index, false)
                                }
                            }

                        }
                    } catch (e: Exception) {
                        log("readFromEncoder $e")
                    }

                } else {
//                    log("readFromEncoder not running")
                    delay(100)
                }
            }
        }
    }

    //寻找可用的解码器
    private fun findEncoderName(): String {
        return try {
            //只招常规的编码器。在android l中，mediaFormat中不能包含KEY_FRAME_RATE属性。
            MediaCodecList(MediaCodecList.REGULAR_CODECS).findEncoderForFormat(mediaFormat)
        } catch (e: Exception) {
            ""
        }
    }

    private fun log(content: String) {
        super.log("AudioEncoder", content)
    }

}