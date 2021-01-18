package com.kowksiuwang.everythingaboutandroid.coroutine

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.IllegalStateException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

/**
 * 别想那些有的没的，认真学习才是硬道理
 *  Created by kwoksiuwang on 2020/12/22!!!
 */

/**
 * 这个dispose是干嘛的我现在还是不太清楚啊，感觉是类似监听器的东西吧。
 */
interface Disposable {
    fun dispose()
}

/**
 * dispose的实现类，保存类一个job变量，和一个onComplete函数
 */
class CompletionHandlerDisposable<T>(val job: Job, val onComplete: (Result<T>) -> Unit) :
    Disposable {
    override fun dispose() {
        job.remove(this)
    }
}

typealias OnComplete = () -> Unit
typealias OnCancel = () -> Unit

/**
 * job的接口。
 */
interface Job : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<Job>

    override val key: CoroutineContext.Key<*>
        get() = Job

    val isActive: Boolean

    fun invokeOnCancel(onCancel: OnCancel): Disposable

    fun invokeOnCompletion(onComplete: OnComplete): Disposable

    fun cancel()

    fun remove(disposable: Disposable)

    suspend fun join()
}

/**
 * 协程的状态类。
 */
sealed class CoroutineState {
    class Imcomplete : CoroutineState()
    class Cancelling : CoroutineState()
    class Complete<T>(val value: T? = null, val exception: Throwable? = null) : CoroutineState()

    private var disposableList: DisposableList = DisposableList.Nil

    fun from(state: CoroutineState): CoroutineState {
        this.disposableList = state.disposableList
        return this
    }

    fun with(disposable: Disposable): CoroutineState {
        this.disposableList = DisposableList.Cons(disposable, this.disposableList)
        return this
    }

    fun without(disposable: Disposable): CoroutineState {
        this.disposableList = this.disposableList.remove(disposable)
        return this
    }

    fun <T> notifyCompletion(result: Result<T>) {
        this.disposableList.loopOn<CompletionHandlerDisposable<T>> {
            it.onComplete(result)
        }
    }

    fun clear() {
        this.disposableList = DisposableList.Nil
    }
}

sealed class DisposableList() {
    object Nil : DisposableList()
    class Cons(val head: Disposable, val tail: DisposableList) : DisposableList()
}

fun DisposableList.remove(disposable: Disposable): DisposableList {
    return when (this) {
        DisposableList.Nil -> this
        is DisposableList.Cons -> {
            if (head == disposable) {
                return tail
            } else {
                return DisposableList.Cons(head, tail.remove(disposable))
            }
        }
    }
}

/**
 * 通过尾递归去给数据链中的所有dispose执行action。
 */
tailrec fun DisposableList.forEach(action: (Disposable) -> Unit) {
    when (this) {
        DisposableList.Nil -> return
        is DisposableList.Cons -> {
            action(this.head)
            this.tail.forEach(action)
        }
    }
}

/**
 * 内联函数。编译的时候，直接把代码编译到被调用处。
 *
 * 对T类型的dispose执行action？，这是什么意思
 */
inline fun <reified T : Disposable> DisposableList.loopOn(crossinline action: (T) -> Unit) =
    forEach {
        when (it) {
            is T -> action(it)
        }
    }

/**
 * 即实现了job是coroutine的element，也是Continuation可以接收到挂起函数执行完之后的回调？
 */
abstract class AbstractCoroutine<T>(context: CoroutineContext) : Job, Continuation<T> {
    protected val state = AtomicReference<CoroutineState>()
    override val context: CoroutineContext

    val isCompleted
        get() = state.get() is CoroutineState.Complete<*>

    override val isActive: Boolean
        get() = when (state.get()) {
            is CoroutineState.Complete<*>,
            is CoroutineState.Cancelling -> false
            else -> true
        }

    init {
        state.set(CoroutineState.Imcomplete())
        this.context = context + this
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun invokeOnCancel(onCancel: OnCancel): Disposable {
        TODO("Not yet implemented")
    }

    /**
     * 迷惑，这里传给doOnCompleted的函数（block）是单纯调用一下收到的参数onComplete，但是在 doOnCompleted 里面
     * 高了一轮，给这个block传个Result是为啥呢？
     */
    override fun invokeOnCompletion(onComplete: OnComplete): Disposable {
        return doOnCompleted { _ -> onComplete() }
    }

    protected fun doOnCompleted(block: (Result<T>) -> Unit): Disposable {
        //这里传入的也是block，等于之后 resumeWith 传给block的result也是没用的，只是单纯告知完成了，而结果并没有返回啊。
        val disposable = CompletionHandlerDisposable<T>(this, block)
        val newState = state.updateAndGet {
            when (it) {
                is CoroutineState.Imcomplete -> {
                    CoroutineState.Imcomplete().from(it).with(disposable)
                }
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(it).with(disposable)
                }
                is CoroutineState.Complete<*> -> {
                    it
                }
            }
        }
        //如果现在已经完成了，就
        (newState as? CoroutineState.Complete<T>)?.let {
            block(
                when {
                    it.value != null -> Result.success(it.value)
                    it.exception != null -> Result.failure(it.exception)
                    else -> throw IllegalStateException("won`t happen")
                }
            )
        }
        return disposable
    }

    override suspend fun join() {
        when (state.get()) {
            is CoroutineState.Complete<*> -> return
            is CoroutineState.Imcomplete,
            is CoroutineState.Cancelling -> return joinSuspend()
        }
    }


    private suspend fun joinSuspend() = suspendCoroutine<Unit> {
        invokeOnCompletion { it.resume(Unit) }
    }

    override fun remove(disposable: Disposable) {
        state.getAndUpdate {
            when (it) {
                is CoroutineState.Imcomplete -> {
                    CoroutineState.Imcomplete().from(it).without(disposable)
                }
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(it).without(disposable)
                }
                is CoroutineState.Complete<*> -> {
                    it
                }
            }
        }
    }

    override fun resumeWith(result: Result<T>) {
        val newState = state.updateAndGet {
            when (it) {
                is CoroutineState.Imcomplete,
                is CoroutineState.Cancelling -> {
                    CoroutineState.Complete(result.getOrNull(), result.exceptionOrNull()).from(it)
                }
                is CoroutineState.Complete<*> -> {
                    throw Throwable("Already completed")
                }
            }
        }
        newState.notifyCompletion(result)
        newState.clear()

    }
}

/**
 * AbstractCoroutine 的实现类
 */
class StandaloneCoroutine<T>(context: CoroutineContext) : AbstractCoroutine<T>(context) {}

interface Deferred<T> : Job {
    suspend fun await(): T
}

class DeferredCoroutine<T>(context: CoroutineContext) : AbstractCoroutine<T>(context),
    Deferred<T> {
    override suspend fun await(): T {
        return when (val currentState = state.get()) {
            is CoroutineState.Cancelling,
            is CoroutineState.Imcomplete -> {
                return awaitSuspend()
            }
            is CoroutineState.Complete<*> -> {
                currentState.exception?.let { throw it } ?: (currentState.value as T)
            }
        }
    }

    private suspend fun awaitSuspend(): T = suspendCoroutine { continuation ->
        doOnCompleted { result ->
            continuation.resumeWith(result)
        }
    }
}

/**
 * launch
 */
fun launch(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineContext.() -> Unit
): Job {
    val completion = StandaloneCoroutine<Unit>(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}

fun <T> async(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineContext.() -> T
): Deferred<T> {
    val completion = DeferredCoroutine<T>(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}


val threadExecutor = Executors.newScheduledThreadPool(1) { r ->
    Thread(r, "scheduled").apply { isDaemon = true }
}
//        ThreadPoolExecutor(1, 1, 10000, TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>())

/**
 * 自己模拟一个delay函数
 */
suspend fun kDelay(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) =
    suspendCoroutine<Unit> {
        threadExecutor.schedule({ it.resume(Unit) }, time, unit)
    }

interface Dispatcher {
    fun dispatch(block: () -> Unit)
}

open class DispatcherContext(private val dispatcher: Dispatcher) :
    AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return DispatchContinuation(continuation, dispatcher)
    }
}

private class DispatchContinuation<T>(val delegate: Continuation<T>, val dispatcher: Dispatcher) :
    Continuation<T> {
    override val context: CoroutineContext
        get() = delegate.context

    override fun resumeWith(result: Result<T>) {
        dispatcher.dispatch {
            delegate.resumeWith(result)
        }
    }
}

object DefaultDispatcher : Dispatcher {
    private val threadGroup = ThreadGroup("DefaultDispatcher")
    private val threadIndex = AtomicInteger(0)
    private val executor =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1) { runnable ->
            Thread(
                threadGroup,
                runnable,
                "${threadGroup.name}-worker-${threadIndex.getAndIncrement()}"
            ).apply { isDaemon = true }
        }

    override fun dispatch(block: () -> Unit) {
        executor.submit(block)
    }
}

object AndroidDispatcher : Dispatcher {
    val handler = Handler(Looper.getMainLooper())
    override fun dispatch(block: () -> Unit) {
        handler.post(block)
    }

}

object Dispatchers {
    val Default by lazy { DispatcherContext(DefaultDispatcher) }
    val Android by lazy { DispatcherContext(AndroidDispatcher) }
}


private var coroutineIndex = AtomicInteger(0)
fun newCoroutineContext(context: CoroutineContext): CoroutineContext {
    val combined = context + CoroutineName("@coroutineIndex#${coroutineIndex.getAndIncrement()}")
    return if (combined !== Dispatchers.Default && combined[ContinuationInterceptor] == null) {
        combined + Dispatchers.Default
    } else {
        combined
    }
}

class CoroutineName(val name: String) : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<CoroutineName>

    override val key: CoroutineContext.Key<*>
        get() = Key

    override fun toString(): String {
        return name
    }
}

fun printlnK(msg: String = "") {
    println("${System.currentTimeMillis()} [${Thread.currentThread().name}] : $msg")
}