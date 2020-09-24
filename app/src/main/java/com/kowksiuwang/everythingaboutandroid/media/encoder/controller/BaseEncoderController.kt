package com.kowksiuwang.everythingaboutandroid.media.encoder.controller

import android.media.MediaCodec
import android.media.MediaFormat
import com.kowksiuwang.everythingaboutandroid.media.encoder.EncoderDataListener
import com.kowksiuwang.everythingaboutandroid.media.encoder.KAudioEncoder
import java.nio.ByteBuffer

public open class BaseEncoderController {

    private val audioEncoder: KAudioEncoder by lazy { KAudioEncoder() }
    private val audioDataListener = object :
        EncoderDataListener {
        override fun onDataAvailable(buffer: ByteBuffer, info: MediaCodec.BufferInfo) {
            audioDataReceiver(buffer, info)
        }

        override fun onFormatChanged(format: MediaFormat) {
            audioFormatChanged(format)
        }

    }

    open fun audioDataReceiver(buffer: ByteBuffer, info: MediaCodec.BufferInfo) {

    }

    open fun audioFormatChanged(format: MediaFormat) {

    }

    open fun init() {
        audioEncoder.init()
    }

    open fun start(path: String) {
        audioEncoder.addEncoderDataListener(audioDataListener)
        audioEncoder.start()
    }

    open  fun stop() {
        audioEncoder.stop()
    }

    open fun release() {
        audioEncoder.release()
    }
}