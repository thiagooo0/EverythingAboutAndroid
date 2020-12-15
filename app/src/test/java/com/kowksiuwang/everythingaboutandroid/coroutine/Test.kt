package com.kowksiuwang.everythingaboutandroid.coroutine

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext

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

    var job: Job? = null
    suspend fun joinTest(id: Int) {
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