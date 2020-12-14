package com.kwoksiuwang.kmedia.encoder.controller

import android.media.MediaCodec
import android.media.MediaFormat
import android.util.Log
import com.kowksiuwang.everythingaboutandroid.media.encoder.EncoderDataListener
import com.kwoksiuwang.kmedia.encoder.KAudioEncoder
import java.nio.ByteBuffer

public open class BaseEncoderController {
    private val tag = "BaseEncoderController"

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
        Log.d(tag, "init")
        audioEncoder.init()
    }

    open fun start(path: String) {
        Log.d(tag, "start")
        audioEncoder.addEncoderDataListener(audioDataListener)
        audioEncoder.start()
    }

    open fun restart() {
        Log.d(tag, "restart")
        audioEncoder.restart()
    }

    open fun pause() {
        Log.d(tag, "pause")
        audioEncoder.pause()
    }

    open fun stop() {
        Log.d(tag, "stop")
        audioEncoder.stop()
    }

    open fun release() {
        Log.d(tag, "")
        audioEncoder.release()
    }
}