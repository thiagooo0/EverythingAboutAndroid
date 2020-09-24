package com.kowksiuwang.everythingaboutandroid.media.encoder.controller

import android.media.MediaCodec
import android.util.Log
import kotlinx.coroutines.*
import java.io.FileOutputStream
import java.lang.Exception
import java.nio.Buffer
import java.nio.ByteBuffer

/**
 * 这里编码保存之后，播放没声音，有点尴尬，先放下，以后再搞
 */
class AACRecorder : BaseEncoderController() {
    private var fileInputStream: FileOutputStream? = null
    private var scope =
        CoroutineScope(kotlinx.coroutines.Job() + kotlinx.coroutines.Dispatchers.Default)

    private fun log(content: String) {
        Log.d("AACRecorder", content)
    }

    override fun start(path: String) {
        super.start(path)
        try {
            fileInputStream = FileOutputStream(path)
        } catch (e: Exception) {
            log("initFileStreamError $e")
        }
    }

    override fun stop() {
        super.stop()
        fileInputStream?.flush()
        fileInputStream?.close()
    }

    override fun release() {
        super.release()
        scope.cancel()
    }

    override fun audioDataReceiver(buffer: ByteBuffer, info: MediaCodec.BufferInfo) {
        super.audioDataReceiver(buffer, info)
//        scope.launch(Dispatchers.Default) {
//            if (info.flags == 0) {
        fileInputStream?.let {
            val result = ByteArray(buffer.remaining() + 7)
            addADTS2Packet(result, buffer.remaining())
//                    val test = ByteArray(info.size)
            buffer.get(result, 7, buffer.remaining())
//                    withContext(Dispatchers.IO) {
            it.write(result)
//                    }
//                }
//            }

        }

    }

    /**
     * ADTS头包含了AAC文件的采样率、通道数、帧数据长度等信息。ADTS头分为固定头信息和可变头信息两个部分，
     * 固定头信息在每个帧中的是一样的，可变头信息在各个帧中并不是固定值。ADTS头一般是7个字节((28+28)/ 8)长度，
     * 如果需要对数据进行CRC校验，则会有2个Byte的校验码，所以ADTS头的实际长度是7个字节或9个字节。
     */
    private fun addADTS2Packet(packet: ByteArray, packetLen: Int): ByteArray {
        val profile = 1
        val freqIdx = 4
        val chanCfg = 2

        //syncword：同步头，永远不变的0xFFF
        packet[0] = 0xFF.toByte()
        //前四位是syncword的后四位，f。
        //id：一位，MPEG Version: 0 for MPEG-4, 1 for MPEG-2，这里用了MPEG-2
        //layer ：两位 永远00
        //protection_absent ： 是否有crc校验。0要，1不用。如果需要，adts报文变为9个字节。这里写了1。
        packet[1] = 0xf9.toByte()
        //profile ： 2位。表示acc的级别。MPEG-2中定义了4种，0:main profile, 1:LC(low Complexity profile)
        //      2:SSR(Scalable Sampling Rate profile), 3: reserved
        //sampling_frequency_index : 4位。表示采样率。 0: 96000 Hz，1: 88200 Hz ，2: 64000 Hz ，
        //      3: 48000 Hz ，4: 44100 Hz ，5: 32000 Hz ，6: 24000 Hz ，7: 22050 Hz ，8: 16000 Hz ，
        //      9: 12000 Hz ，10: 11025 Hz ，11: 8000 Hz ，12: 7350 Hz ，13: Reserved ，14: Reserved ，
        //      15: frequency is written explictly
        //private_bit : 1位
        //channel_configuration ： 3位声道数。 0：96000， 2：64000， 3：48000， 4：44100。。等等
        packet[2] = ((profile shl 6) + (freqIdx shl 2) + (chanCfg shr 2)).toByte()
        //这里还有2位的channel_configuration
        //original_copy:1位，编码时设置0，解码时忽略
        //home：1位，编码时设置0，解码时忽略
        //进入可变字节
        //copyrighted_id_bit：1位，编码时设置为0，解码时忽略
        //copyrighted_id_start：1位，编码时设置为0，解码时忽略
        //aac_frame_length：13位，ADTS帧长度包括ADTS长度和AAC声音数据长度的和。即 aac_frame_length = (protection_absent == 0 ? 9 : 7) + audio_data_length
        //      所以这里有2位的aac_frame_length
        packet[3] = (((chanCfg and 3) shl 6) + (packetLen shr 11)).toByte()
        //8位都是aac_frame_length
        packet[4] = ((packetLen and 0x7ff) shr 3).toByte()
        //3位的aac_frame_length
        //adts_buffer_fullness：11位，固定为0x7FF。表示是码率可变的码流，这里有5位
        packet[5] = (((packetLen and 7) shl 5) + 0x1f).toByte()
        //6位的adts_buffer_fullness
        //number_of_raw_data_blocks_in_frame：2位表示当前帧有number_of_raw_data_blocks_in_frame + 1 个原始帧(一个AAC原始帧包含一段时间内1024个采样及相关数据)。
        packet[6] = 0xFC.toByte()

        return packet
    }
}