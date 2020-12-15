package com.kowksiuwang.everythingaboutandroid.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.*

/**
 * Created by GuoShaoHong on 2020/12/14.
 */
public class KCoroutine {
    fun <R, T> launchCoroutine(receiver: R, block: suspend R.() -> T) {
        block.startCoroutine(receiver, object : Continuation<T> {
            override val context: CoroutineContext = EmptyCoroutineContext

            override fun resumeWith(result: Result<T>) {
                println("launchCoroutine resumeWith: ${result.getOrDefault("null value")}")
                context[CoroutineExceptionHandler]

            }
        })
    }

    /**
     * 一个简单的协程，通过createCoroutine()方法创建协程。
     * 可以先看下createCoroutine()的定义。 fun <T> (suspend () -> T).createCoroutine(completion: Continuation<T>): Continuation<Unit>
     * suspend{}中的函数，就是createCoroutine的receiver。表示一个不接收参数并且返回一个T类型的挂起函数才能调用createCoroutine()
     * 参数是Continuation类型的completion会在执行完后调用。
     * 返回的也是Continuation类型的值。为啥参数和返回值是同一个类型呢？中间做了什么操作？
     *
     * 如果要运行协程，调用continuation1.resume(Unit)即可。
     *
     */
    val continuation1 = suspend {
        println("In continue")
        5
    }.createCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("resumeWith ${result.isSuccess} ${result.getOrNull()}")
        }
    })

    /**
     * 真正的挂起实在程序执行流程发生异步调用的时候才会发生，挂起函数不一定就会挂起，比如下面这个。
     */
    suspend fun suspendFunc01(a: Int) {
        thread { }
        return
    }

    /**
     * 一个函数想要让自己挂起，无非就是一个continuation实例（这句话没看懂，暂时粗略理解为，只是开了个thread，也不叫异步调用，因为它返回不了。
     * 只能通过continuation才能做到挂起和异步返回）。
     */
    suspend fun suspendFunc02(a: String, b: String) = suspendCoroutine<Int> { continuation ->
        thread {
            continuation.resumeWith(Result.success(5))
        }
    }


    @org.junit.Test
    public fun test() {
        continuation1.resume(Unit)
//        launchCoroutine(Test()) {
//            println("test1")
//            delay(199)
//            println("test2")
//            coroutineContext[CoroutineName]
//            1
//        }
        runBlocking {
            delay(1000)
        }

    }
}