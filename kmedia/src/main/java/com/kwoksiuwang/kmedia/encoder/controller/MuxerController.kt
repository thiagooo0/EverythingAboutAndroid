package com.kwoksiuwang.kmedia.encoder.controller

import android.media.MediaCodec
import android.media.MediaFormat
import android.media.MediaMuxer
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import java.nio.ByteBuffer

/**
 * 通过MediaMuxer保存音视频文件
 */
class MuxerController : BaseEncoderController() {
    private var muxer: MediaMuxer? = null
    private var audioTrackID = -1
    private var isMuxerStart = false

    private val tag = "MuxerController"

    override fun audioDataReceiver(buffer: ByteBuffer, info: MediaCodec.BufferInfo) {
        super.audioDataReceiver(buffer, info)
        if (isMuxerStart && !isPause) {
            try {
                Log.d(tag, "muxer ptus ${info.presentationTimeUs} ${info.flags}")
                muxer?.writeSampleData(audioTrackID, buffer, info)
            } catch (e: Exception) {
                Log.d(tag, "writeSampleData error : $e")
            }


        } else {
            Log.d(tag, "writeSampleData pause")
        }
    }

    override fun audioFormatChanged(format: MediaFormat) {
        super.audioFormatChanged(format)
        if (!isMuxerStart) {
            muxer?.let {
                if (audioTrackID == -1) {
                    Log.d(tag, "add audio track")
                    audioTrackID = it.addTrack(format)
                }
                it.start()
                isMuxerStart = true
            }
        }
    }

    @Volatile
    private var isPause = false
//    override fun pause() {
//        isPause = true
//    }
//
//    override fun restart() {
//        isPause = false
//    }

    override fun start(path: String) {
        Log.d(tag, "start")
        isMuxerStart = false
        muxer = MediaMuxer(path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
        super.start(path)
    }

    override fun stop() {
        super.stop()
        Log.d(tag, "stop")
        GlobalScope.launch(Dispatchers.Default) {
            try {
                delay(1000)
                isMuxerStart = false
                //muxer一旦stop了，就不能再次start了，所以每次都要创建一个新的
                if (audioTrackID != -1) {
                    audioTrackID = -1
                    muxer?.stop()
                }
                muxer?.release()
                audioTrackID = -1
            } catch (e: Exception) {
                Log.d(tag, "stop fail $e")
            }
        }

    }

}