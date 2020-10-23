package com.kowksiuwang.everythingaboutandroid.handler

import android.os.Handler
import kotlin.concurrent.thread


/**
 *  Created by kwoksiuwang on 2020/9/27!!!
 */
class MHandler {
    var handler = Handler()
    fun handle() {
        thread {
            handler.sendEmptyMessage(1)
        }
    }
}