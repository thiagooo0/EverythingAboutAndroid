package com.kowksiuwang.everythingaboutandroid.media.encoder.controller

import android.media.MediaCodec
import android.media.MediaFormat
import android.media.MediaMuxer
import java.nio.ByteBuffer

/**
 * 通过MediaMuxer保存音视频文件
 */
class MuxerController : BaseEncoderController() {
    private var muxer: MediaMuxer? = null
    private var audioTrackID = -1
    private var isMuxerStart = false

    override fun audioDataReceiver(buffer: ByteBuffer, info: MediaCodec.BufferInfo) {
        super.audioDataReceiver(buffer, info)
        if (isMuxerStart) {
            muxer?.writeSampleData(audioTrackID, buffer, info)
        }
    }

    override fun audioFormatChanged(format: MediaFormat) {
        super.audioFormatChanged(format)
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

    override fun start(path: String) {
        isMuxerStart = false
        muxer = MediaMuxer(path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
    }

    override fun stop() {
        isMuxerStart = false
        //muxer一旦stop了，就不能再次start了，所以每次都要创建一个新的
        muxer?.stop()
        muxer?.release()
        audioTrackID = -1
    }

}