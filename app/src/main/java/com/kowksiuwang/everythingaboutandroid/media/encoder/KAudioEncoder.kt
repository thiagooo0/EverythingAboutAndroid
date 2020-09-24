package com.kowksiuwang.everythingaboutandroid.media.encoder

import android.media.*
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
    private val statusInit = 0
    private val statusRunning = 0
    private val statusStop = 0
    private var status = statusIdle

    private var scope = CoroutineScope(Job() + Dispatchers.Default)
    private var readRecorderJob: Job? = null
    private var writeEncoderJob: Job? = null
    private var readEncoderJob: Job? = null

    private var channel = Channel<ByteArray>()

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
        if (status != statusInit || status != statusStop) {
            return
        }
        log("start")
        status = statusRunning
        audioRecord?.startRecording()

        //每次start之前，都要configure一次。
        audioEncoder!!.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        audioEncoder?.start()

        openJobs()
    }

    override fun stop() {
        super.stop()
        if (status != statusRunning) {
            return
        }
        log("stop")
        status = statusStop
        stopJobs()
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

    //    private val audioTempData: ByteArray by lazy { ByteArray(minBufferSize) }
//    private val audioByteBuffer = ByteBuffer()
    private fun readRecorder() {
        readRecorderJob = scope.launch(Dispatchers.Default) {
            while (isActive && status == statusRunning) {
//                val temp = ByteBuffer.allocate()
                val tempData = ByteArray(minBufferSize)
                audioRecord?.read(tempData, 0, minBufferSize)
                channel.offer(tempData)
            }
        }
    }

    private fun write2Encoder() {
        writeEncoderJob = scope.launch(Dispatchers.Default) {
            while (isActive && status == statusRunning) {
                val data = async { channel.receive() }
                //-1 mean wait forever
                val index = audioEncoder?.dequeueInputBuffer(1000) ?: -1
                if (index >= 0) {
                    val buffer = audioEncoder?.getInputBuffer(index)
                    if (isActive && status == statusRunning) {
                        buffer?.let {
                            it.put(data.await())
                            audioEncoder?.queueInputBuffer(
                                index,
                                0,
                                data.await().size,
                                System.nanoTime() / 1000,
                                0
                            )
                        }
                    }
                }
            }
        }
    }

    private fun readFromEncoder() {
        readEncoderJob = scope.launch(Dispatchers.Default) {
            while (isActive && status == statusRunning) {
                val bufferInfo = MediaCodec.BufferInfo()
                //wait and get the buffer
                val index =
                    audioEncoder?.dequeueOutputBuffer(bufferInfo, 1000L)
                        ?: MediaCodec.INFO_TRY_AGAIN_LATER
                when (index) {
                    MediaCodec.INFO_TRY_AGAIN_LATER -> {
                        //do nothing,just try again later
                    }
                    MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                        audioEncoder?.outputFormat?.let { onFormatChanged(it) }
                    }
                    else -> {
                        audioEncoder?.getOutputBuffer(index)?.let {
                            onDataAvailable(it, bufferInfo)
                            audioEncoder?.releaseOutputBuffer(index, false)
                        }
                    }

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