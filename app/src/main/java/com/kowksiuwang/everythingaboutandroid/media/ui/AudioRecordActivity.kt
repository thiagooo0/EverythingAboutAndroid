package com.kowksiuwang.everythingaboutandroid.media.ui

import android.media.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.kowksiuwang.everythingaboutandroid.R
import com.kowksiuwang.everythingaboutandroid.media.encoder.EncoderDataListener
import com.kwoksiuwang.kmedia.encoder.KAudioEncoder
import com.kowksiuwang.everythingaboutandroid.media.encoder.controller.AACRecorder
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.io.File
import java.lang.Exception
import java.nio.ByteBuffer
import java.text.SimpleDateFormat

class AudioRecordActivity : AppCompatActivity() {
    private val rateInHz = 44100
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private val audioChannel = AudioFormat.CHANNEL_IN_DEFAULT
    private var audioRecord: AudioRecord? = null

    private var audioCodec: MediaCodec? = null

    private var muxer: MediaMuxer? = null
    private var audioTrackID = -1
    private var isMuxerStart = false

    private var recordingJob: Job? = null

    private var recordFile: File? = null

    private var timeFormat = SimpleDateFormat("MM:dd-hh:mm:ss")

    private val audioEncoder =
        KAudioEncoder()
    private val controller = AACRecorder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_record)
//        btn_init_record.setOnClickListener { controller.init() }
//        btn_start_record.setOnClickListener {
//            controller.start(
//                this.getExternalFilesDir("audio")!!.path + "/audio_${timeFormat.format(
//                    System.currentTimeMillis()
//                )}.aac"
//            )
//        }
//        btn_stop_record.setOnClickListener { controller.stop() }
//        btn_release_record.setOnClickListener { controller.release() }


    }

    private fun initRecord1() {
        recordFile =
            File(
                this.getExternalFilesDir("audio")!!.path + "audio_${timeFormat.format(
                    System.currentTimeMillis()
                )}.mp4"
            )
        log("record file ${recordFile!!.path}")
        recordFile!!.createNewFile()
        audioEncoder.init()
    }

    val audioDataListener = object :
        EncoderDataListener {
        override fun onDataAvailable(buffer: ByteBuffer, info: MediaCodec.BufferInfo) {
            if (isMuxerStart) {
                muxer?.writeSampleData(audioTrackID, buffer, info)
            }
        }

        override fun onFormatChanged(format: MediaFormat) {
            if (!isMuxerStart) {
                muxer?.let {
                    if (audioTrackID == -1) {
                        audioTrackID = it.addTrack(format)
                    }
                    it.start()
                    isMuxerStart = true
                }
            }
        }

    }

    private fun startRecord1() {
        isMuxerStart = false
        muxer = MediaMuxer(recordFile!!.path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)

        audioEncoder.addEncoderDataListener(audioDataListener)
        audioEncoder.start()
    }

    private fun stopRecord1() {
        isMuxerStart = false
        //muxer一旦stop了，就不能再次start了，所以每次都要创建一个新的
        muxer?.stop()
        muxer?.release()
        audioTrackID = -1
        audioEncoder.stop()
    }

    private fun releaseRecord1() {
        audioEncoder.release()
    }

    private fun startRecord(): Job {
        return lifecycleScope.launch(Dispatchers.Default) {
            try {

                val mediaFormat = MediaFormat.createAudioFormat(
                    MediaFormat.MIMETYPE_AUDIO_AAC,
                    rateInHz,
                    audioChannel
                )
                mediaFormat.setInteger(
                    MediaFormat.KEY_AAC_PROFILE,
                    MediaCodecInfo.CodecProfileLevel.AACObjectLC
                )
                mediaFormat.setInteger(MediaFormat.KEY_CHANNEL_MASK, AudioFormat.CHANNEL_IN_MONO)
                mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 64000)
                mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 100 * 1024)
                mediaFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, audioChannel)
//                mediaFormat.setInteger(MediaFormat.KEY_SAMPLE_RATE, rateInHz)
//                mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 3)

                withContext(Dispatchers.IO) {
                    //init MediaMuxer
                    muxer =
                        MediaMuxer(recordFile!!.path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
                    audioTrackID = muxer!!.addTrack(mediaFormat)
                    muxer!!.start()
                }


                audioCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC)
                audioCodec!!.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
                audioCodec!!.setCallback(object : MediaCodec.Callback() {
                    override fun onOutputBufferAvailable(
                        codec: MediaCodec,
                        index: Int,
                        info: MediaCodec.BufferInfo
                    ) {
                        val buffer = codec.getOutputBuffer(index)
                        buffer?.let {
                            muxer?.writeSampleData(audioTrackID, it, info)
                            codec.releaseOutputBuffer(index, false)
                        }
                    }

                    override fun onInputBufferAvailable(codec: MediaCodec, index: Int) {
                        lifecycleScope.launch {
                            val data = channel.receive()
                            val buffer = codec.getInputBuffer(index)
                            buffer?.clear()
                            buffer?.limit(data.size)
                            buffer?.put(data)
                            codec.queueInputBuffer(
                                index,
                                0,
                                data.size,
                                System.nanoTime() / 1000,
                                0
                            )
                        }
                    }

                    override fun onOutputFormatChanged(codec: MediaCodec, format: MediaFormat) {

                    }

                    override fun onError(codec: MediaCodec, e: MediaCodec.CodecException) {
                        log("cedec error $e")
                    }
                })
                audioCodec!!.start()


                //init audioRecord
                val minBufferSize = AudioRecord.getMinBufferSize(
                    rateInHz,
                    audioChannel,
                    audioFormat
                )
                audioRecord = AudioRecord(
                    MediaRecorder.AudioSource.MIC,
                    rateInHz,
                    audioChannel,
                    audioFormat,
                    minBufferSize
                )
                audioRecord?.startRecording()
                while (isActive) {
//                    val byteBuffer = ByteBuffer.allocate(512)
//                    audioRecord?.read(byteBuffer, 512, AudioRecord.READ_NON_BLOCKING)
                    val byteData = ByteArray(minBufferSize)
                    val size = audioRecord?.read(byteData, 0, minBufferSize)
                    log("read record data. size:$size")
                    channel.offer(byteData)
                }
            } catch (e: Exception) {
                log("startRecord error : $e")
            }
        }
    }

    private var channel = Channel<ByteArray>()

    private fun stopRecord() {
        lifecycleScope.launch {

            recordingJob?.cancelAndJoin()
            recordingJob = null
            audioRecord?.let {
                it.stop()
                it.release()
                audioRecord = null
            }

            audioCodec?.stop()
            audioCodec?.release()

            muxer?.stop()
            muxer?.release()
        }

    }

    private fun log(content: String) {
        Log.d(this::javaClass.name, content)
    }
}