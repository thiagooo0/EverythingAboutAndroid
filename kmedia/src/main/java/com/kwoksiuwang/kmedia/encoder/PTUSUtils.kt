package com.kwoksiuwang.kmedia.encoder

import android.util.Log

/**
 * 获取时间戳的工具类，提供暂停和重新开始的方法，会自动积累暂停的时间，并且在重新开始后，在计算时间戳时减去暂停的时间
 *  Created by kwoksiuwang on 2020/9/25!!!
 */
class PTUSUtils {
    private var lastTime = -1L

    private var uselessTime = 0L
    private var pauseStartTime = 0L

    private var startTime = 0L

    fun getPTUS(): Long {
        //todo 这里为什么要用nanotime？，有空看一下
        val t = System.nanoTime() / 1000 - uselessTime - startTime
        return if (t < lastTime) {
            log("get < , t $t ,lastTime $lastTime， put ${lastTime + 100}")
            lastTime = t
            lastTime + 100
        } else {
            log("get >= , t $t ,lastTime $lastTime， put ${t}")
            lastTime = t
            t
        }
    }

    fun start() {
        startTime = System.nanoTime() / 1000
        lastTime = -1L
        uselessTime = 0L
    }

    fun stop() {
        startTime = 0
        lastTime = -1L
        uselessTime = 0L
    }

    fun pause() {
        pauseStartTime = System.nanoTime() / 1000
        log("pause pauseStartTime = $pauseStartTime")
    }

    fun restart() {
        uselessTime += (System.nanoTime() / 1000 - pauseStartTime - 100)
        pauseStartTime = 0
        log("restart uselessTime = ${uselessTime}")
    }

    private fun log(content: String) {
//        Log.d("PTUSUtils", contenent)
    }
}