package com.kowksiuwang.everythingaboutandroid.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

/**
 * Created by GuoShaoHong on 2020/12/14.
 */
public class KCoroutine {
    fun <R, T> launchCoroutine(receiver: R, block: suspend R.() -> T) {
        block.startCoroutine(receiver, object : Continuation<T> {
            override val context: CoroutineContext = EmptyCoroutineContext

            override fun resumeWith(result: Result<T>) {
                println("launchCoroutine resumeWith: ${result.getOrDefault("null value")}")
            }
        })
    }

    @org.junit.Test
    public fun test() {
        launchCoroutine(Test()) {
            println("test1")
            delay(199)
            println("test2")
            1
        }
        runBlocking {
            delay(1000)
        }

    }
}