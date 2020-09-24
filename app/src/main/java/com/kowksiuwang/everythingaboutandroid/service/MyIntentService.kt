package com.kowksiuwang.everythingaboutandroid.service

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyIntentService : IntentService("MyTestIntentService") {
    private fun log(t: String) {
        Log.d("MyIntentService", t)
    }

    /**
     * 通过onStartCommand传进来的intent，会一个个传到这里来。
     * 这里非主线程。可以做阻塞的工作。
     */
    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent start do task ${intent!!.action} ${Thread.currentThread()}")
        Thread.sleep(1000)
        log("onHandleIntent end task ${intent!!.action} ${Thread.currentThread()}")
    }

}