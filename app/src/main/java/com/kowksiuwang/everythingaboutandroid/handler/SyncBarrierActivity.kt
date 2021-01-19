package com.kowksiuwang.everythingaboutandroid.handler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.kowksiuwang.everythingaboutandroid.R
import kotlinx.android.synthetic.main.activity_sync_barrier.*
import kotlin.concurrent.thread

class SyncBarrierActivity : AppCompatActivity() {
    val handler = Handler() {
        when (it.what) {
            1 -> {
                tv_content.text = "${tv_content.text}   同步消息"
            }
            2 -> {
                tv_content.text = "${tv_content.text}  异步消息"
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_barrier)
        btn_sent_async_msg.setOnClickListener {
            val msg = Message()
            msg.isAsynchronous = true
            msg.what = 2
            handler.sendMessage(msg)
            thread{}
            Thread{}.start()
        }
        btn_sent_sync_msg.setOnClickListener {
            handler.sendEmptyMessage(1)
        }
        btn_add_sync_barrier.setOnClickListener {
        }
    }
}