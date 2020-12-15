package com.kowksiuwang.everythingaboutandroid.coroutine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.*
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext
import java.util.concurrent.Future
import kotlin.coroutines.*

/**
 * Created by GuoShaoHong on 9/3/2020.
 */
class Test {
    val task = fun() {

    }

    @Test
    fun test() {
        thread(block = task)
        GlobalScope.launch {
            joinTest(0)
            delay(10)
            joinTest(1)
        }
        Thread.sleep(10000)

    }

    val continuation = suspend {
        println("suspend 5")
        5
    }.createCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext = EmptyCoroutineContext
        override fun resumeWith(result: Result<Int>) {
            println("resumeWith ${result.getOrNull()}")
        }

    })

    fun test2() {

    }

    var job: Job? = null
    suspend fun joinTest(id: Int) {
        val pairs = id to 10

        job?.cancelAndJoin()
        job = GlobalScope.launch() {
            var i = 0
            while (i < 10) {
                print("$id $i \n")
                while (isActive) {
                    val i = 0
                    return@launch
                }
                i++
            }
        }
    }
}