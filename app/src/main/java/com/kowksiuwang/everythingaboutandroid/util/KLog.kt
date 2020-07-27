package com.kowksiuwang.everythingaboutandroid.util

import android.util.Log

/**
 * Created by GuoShaoHong on 2020/7/23.
 */
class KLog(val tag: String) {
    fun d(msg: String) {
        Log.d(tag, msg)
    }
}