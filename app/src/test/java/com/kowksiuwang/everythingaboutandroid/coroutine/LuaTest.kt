package com.kowksiuwang.everythingaboutandroid.coroutine

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.IllegalStateException
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

/**
 *  Created by kwoksiuwang on 12/16/20!!!
 */
class LuaTest {
    /**
     * 简单说一下流程啊。
     * 为了简化称呼，producer生产者，consumer消费者和 runningBlocking中的代码，我们分别简称为p，c，b。
     *
     * p和c在变量初始化的时候，已经进入了created状态，可以随时调用了。
     * b开始运行，调用了p的resume方法。
     * p从头开始运行，打印 producer start ，进入循环中，打印 send 0，然后挂起。
     * b恢复，收到返回值，打印 runBlocking result 0。随后调用c的resume方法。
     * c从头开始执行，收到了从p传来的值0，打印 consumer start 0。进入循环，并且挂起，等待下一个值。
     * b进入第二次循环，继续p->c->p->c->p->c，分别输出了1，2，3，此处稍作简略 。
     * 来到最后一次，p返回了200，c通过yield方法收到。这里可以发现，其实p和c的方法是有一点错位的，p第一个循环传出去
     * 的值，c是从方法的param参数中获取的。p第二个循环传出去的值，是c第一次循环中从yeild中获得。所以p循环结束时传出去的
     * 200，由c最后一次循环获取。
     * 随后两个子协程都结束了，b的下一次循环判断为false，b的运行也结束了。
     */
    private val producer =
        Coroutine.create<Unit, Int>(EmptyCoroutineContext + CoroutineName("producer")) {
            println("producer start")
            for (i in 0..3) {
                println("send ${i}")
                yield(i)
            }
            200
        }

    private val consumer =
        Coroutine.create<Int, Unit>(EmptyCoroutineContext + CoroutineName("consumer")) { param: Int ->
            println("consumer start $param")
            for (i in 0..3) {
                val value = yield(Unit)
                println("value $value")
            }
        }

    @Test
    fun test() {
        runBlocking(EmptyCoroutineContext + CoroutineName("blocking")) {
            while (producer.isActive && consumer.isActive) {
                val result = producer.resume(Unit)
                println("runBlocking result $result")
                consumer.resume(result)
            }
            delay(2000)
        }
    }
}

/**
 * 协程的状态
 */
sealed class Status {
    class Created(val continuation: Continuation<Unit>) : Status()
    class Yielded<P>(val continuation: Continuation<P>) : Status()
    class Resumed<R>(val continuation: Continuation<R>) : Status()
    object Dead : Status()
}

/**
 * 通过定义scope，提供必要的方法
 */
interface CoroutineScope<P, R> {
    val parameter: P?
    suspend fun yield(value: R): P
}

class Coroutine<P, R>(
    override val context: CoroutineContext,
    private val block: suspend CoroutineScope<P, R>.(P) -> R
) : Continuation<R> {

    companion object {
        fun <P, R> create(
            context: CoroutineContext = EmptyCoroutineContext,
            block: suspend CoroutineScope<P, R>.(P) -> R
        ): Coroutine<P, R> {
            return Coroutine(context, block)
        }

    }

    private val scope = object : CoroutineScope<P, R> {
        override var parameter: P? = null

        /**
         * 内部协程运行到想要挂起的时候，会调用yield方法，这里只会在Resumed的情况下才会执行，因为只有Resumed状态才
         * 持有外部协程的continuation啊。
         */
        override suspend fun yield(value: R): P {
            return suspendCoroutine { continuation ->
                println("${continuation.context[CoroutineName]} yield")
                val previousStatus = status.getAndUpdate {
                    when (it) {
                        is Status.Created -> throw IllegalStateException("Never started")
                        is Status.Yielded<*> -> throw IllegalStateException("Already yielded")
                        is Status.Resumed<*> -> Status.Yielded(continuation)
                        is Status.Dead -> throw IllegalStateException("Already died")
                    }
                }
                //通过resume让外部协程恢复调用。
                (previousStatus as? Status.Resumed<R>)?.continuation?.resume(value)
            }
        }
    }

    private val status: AtomicReference<Status>

    val isActive: Boolean
        get() = status.get() != Status.Dead

    init {
        //在这里构建一个协程体
        val coroutineBlock: suspend CoroutineScope<P, R>.() -> R = {
            //这句暂时没看懂。
            println("coroutineBlock ${coroutineContext[CoroutineName]}")
            block(parameter!!)
        }
        //构建一个协程，把scope传进去作为作用域。后面的completion是本类，所以协程完成后，本类的resumeWith会收到
        //回调。
        val start = coroutineBlock.createCoroutine(scope, this)
        //把状态变成Created，让Created持有Continuation。
        status = AtomicReference(Status.Created(start))
    }

    //协程完成后，这里会收到回调。在这里把状态值改为dead，之后就不接收resume了。
    override fun resumeWith(result: Result<R>) {
        val previousStatus = status.getAndUpdate {
            when (it) {
                is Status.Created -> throw IllegalStateException("Never started")
                is Status.Yielded<*> -> throw IllegalStateException("Already yielded")
                is Status.Resumed<*> -> Status.Dead
                is Status.Dead -> throw IllegalStateException("Already died")
            }
        }
        println("${(previousStatus as? Status.Resumed<R>)?.continuation?.context!![CoroutineName]} resumeWith:${result}")
        (previousStatus as? Status.Resumed<R>)?.continuation?.resumeWith(result)
    }

    /**
     * 外部调用此方法。
     *
     * 假设状态正确，就会把外部传进来的continuation保存在Resumed状态中，留待以后内部协程yield的时候，
     * 通过外部的continuation让外部协程恢复。
     *
     * 然后判断上一个状态，如果是Created或者Yield，那就代表内部协程可以恢复执行，那就通过状态中持有的内部
     * 的continuation恢复内部协程。
     */
    suspend fun resume(value: P): R = suspendCoroutine { continuation ->
        println("${continuation.context[CoroutineName]} resume")
        val previousStatus = status.getAndUpdate {
            when (it) {
                is Status.Created -> {
                    scope.parameter = value
                    Status.Resumed(continuation)
                }
                is Status.Yielded<*> -> {
                    Status.Resumed(continuation)
                }
                is Status.Resumed<*> -> {
                    throw IllegalStateException("Already resumed")
                }
                is Status.Dead -> {
                    throw IllegalStateException("Already died")
                }
            }
        }
        when (previousStatus) {
            is Status.Created -> {
                println("${previousStatus.continuation.context[CoroutineName]} resume Status.Created")
                previousStatus.continuation.resume(Unit)
            }
            is Status.Yielded<*> -> {
                println("${(previousStatus as Status.Yielded<P>).continuation.context[CoroutineName]} resume Status.Yielded")
                (previousStatus as Status.Yielded<P>).continuation.resume(value)
            }
        }
    }

}