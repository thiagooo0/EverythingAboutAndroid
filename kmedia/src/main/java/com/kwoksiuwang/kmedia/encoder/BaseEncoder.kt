package com.kowksiuwang.everythingaboutandroid.media.encoder

import android.media.MediaCodec
import android.media.MediaFormat
import android.util.Log
import java.nio.ByteBuffer

open class BaseEncoder :
    EncoderDataListener {
    open fun log(tag: String = "", content: String) {
        Log.d("Encoder_$tag", content)
    }

    private val encoderDataListeners: ArrayList<EncoderDataListener> by lazy {
        ArrayList<EncoderDataListener>()
    }

    open fun init() {
    }

    open fun start() {

    }

    open fun stop() {

    }

    open fun release() {

    }

    fun addEncoderDataListener(l: EncoderDataListener) {
        encoderDataListeners.add(l)
    }

    fun removeEncoderDataListener(l: EncoderDataListener) {
        encoderDataListeners.remove(l)
    }

    override fun onDataAvailable(buffer: ByteBuffer, info :MediaCodec.BufferInfo) {
        for (l in encoderDataListeners) {
            l.onDataAvailable(buffer, info)
        }
    }

    override fun onFormatChanged(format: MediaFormat) {
        for (l in encoderDataListeners) {
            l.onFormatChanged(format)
        }
    }

}

interface EncoderDataListener {
    fun onDataAvailable(buffer: ByteBuffer, info :MediaCodec.BufferInfo)
    fun onFormatChanged(format: MediaFormat)
}