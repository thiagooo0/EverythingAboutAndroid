package com.kowksiuwang.everythingaboutandroid.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.*
import kotlin.concurrent.thread
import kotlin.coroutines.*

/**
 * Created by GuoShaoHong on 2020/12/14.
 */
public class KCoroutine {


    /**
     * 一个简单的协程，通过createCoroutine()方法创建协程。
     * 可以先看下createCoroutine()的定义。 fun <T> (suspend () -> T).createCoroutine(completion: Continuation<T>): Continuation<Unit>
     * suspend{}中的函数，就是createCoroutine的receiver。表示一个不接收参数并且返回一个T类型的挂起函数才能调用createCoroutine()
     * 参数是Continuation类型的completion会在执行完后调用。
     * 返回的也是Continuation类型的值。为啥参数和返回值是同一个类型呢？中间做了什么操作？
     * 如果要运行协程，调用continuation1.resume(Unit)即可。
     *
     * 我们的协程体(susupend{})，实际上会被编译器编译成一个匿名内部类。这个类继承自SuspendLambda类，也实现类Continuation接口。
     * 而返回的continuation是 SafeContinuation 的实例，但是它只是一个马甲，它内部持有的delegate属性才是真正的Continuation的本体,
     * 本体就是协程体生成的匿名内部类了。实际上返回的continuation实例，就是套了几层马甲的协程体而已。
     *
     * 至于resume做了什么操作，未知。猜想：生产匿名内部类的时候，把协程体放到类resumeWith方法内。
     *
     *
     */
    val continuation1 = suspend {
        println("In continuation1")

        5
    }.createCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("continuation1 resumeWith ${result.isSuccess} ${result.getOrNull()}")
        }
    })

    /**
     * 一步到位的startCoroutine方法，点进去可以看到，是帮我们调用类.resume(Unit)。当类初始化的时候，这里直接就执行了。
     * 但是每包装 SafeContinuation ，不知为何。
     */
//    val continuation2 = suspend {
//        println("In continuation2")
//        2
//    }.startCoroutine(object : Continuation<Int> {
//        override val context: CoroutineContext
//            get() = EmptyCoroutineContext
//
//        override fun resumeWith(result: Result<Int>) {
//            println("continuation2 resumeWith ${result.isSuccess} ${result.getOrNull()}")
//        }
//
//    })

    /**
     * createCoroutine和startCoroutine方法都有另外一个重载，可以传入一个receiver，通过receiver可以给协程体指定作用域。
     * 协程体内可以直接使用作用域提供的状态和属性。比如 coroutine3() 中的协程体内，我们就可以直接使用Test的方法。
     *
     *
     * 但是由于kotlin的lambda表达式不支持指明receiver，所以我们给它封装了一个方法。
     *
     */
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
     * receiver既可以用来提供函数支持，也可以用于做函数限制。@RestrictsSuspension注解会让协程体无法使用外部的挂起函数。
     * 如coroutine3()中，传入 MyRestrictProducerScope 作为 receiver 时，无法调用 delay() 方法。
     */
    @RestrictsSuspension
    class MyRestrictProducerScope<T> {
        constructor() {}

        suspend fun produce(value: T) {}
    }

    @org.junit.Test
    public fun coroutine3() {
        launchCoroutine(Test()) {
            println("test1")
            doSomething()
            delay(100)
            println("test2")
            1
        }
        launchCoroutine(2) {
            println("test3")
            compareTo(2)
            "234"
        }

        launchCoroutine(MyRestrictProducerScope<Int>()) {
            //delay(200)
            produce(1)
        }
        runBlocking {
            delay(1000)
        }
    }

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
    suspend fun suspendFunc02(a: Int) = suspendCoroutine<Int> { continuation ->
        thread {
            println("suspendFunc02 $a")
            continuation.resumeWith(Result.success(a))
        }
    }

    /**
     *
     */
    suspend fun noSuspend() = suspendCoroutine<Int> {
        it.resume(100)
    }

    /**
     * CPS变换（Continuation-Passing-Style Transformation），通过传递Continuation来控制异步调用流程。
     * 把挂起后的逻辑封装到continuation中，在需要到时候再调用。（自己的理解）
     *
     *
     * 使用反射去使用noSuspend方法，会需要传入一个continuation对象。这就是挂起函数跟普通函数的区别，任何一个协程体和挂起函数
     * 都隐含着一个continuation实例，编译器能够对这个实例进行正确的传递。但是普通函数并不包含这个实例，所以不能调用挂起函数。
     */
    fun refFun() {
        val ref = ::noSuspend
        val result = ref.call(object : Continuation<Int> {
            override val context: CoroutineContext
                get() = TODO("Not yet implemented")

            override fun resumeWith(result: Result<Int>) {
                TODO("Not yet implemented")
            }

        })
    }

    /**
     * Continuation中的另外一个属性是coroutineContext，即协程的上下文。上下文主要承载了资源获取，配置管理等工作，是执行环节相关
     * 通用数据资源的统一管理者。
     *
     * 协程的context的数据结构特征比较明显，和list类似。几个context可以通过+，整合在一起，变成一个大的context。
     * CoroutineContext中有一个关键属性，key，就是某个context在集合中的索引。
     *
     * 下面我们构造了一个新的context，空的context+协程名+异常捕捉器。然后在创造协程的时候，把context赋值。
     *
     * 在协程体中，我们可以通过coroutineContext[CoroutineName]或者coroutineContext[CoroutineExceptionHandler]等方法通过key去获取
     * 对应等上下文，
     */
    private var mContext: CoroutineContext =
        EmptyCoroutineContext + CoroutineName("testContext") + CoroutineExceptionHandler { context, throwable ->
            println("exception $throwable")
        }

    private val contextTest = suspend {
        println("In coroutine ${Thread.currentThread().name} ${coroutineContext[CoroutineName]} ${coroutineContext[CoroutineExceptionHandler]}")
        //throw Throwable("error")
        1
    }.createCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = mContext

        override fun resumeWith(result: Result<Int>) {
            result.onFailure {
                println("onFailure CoroutineExceptionHandler : ${context[CoroutineExceptionHandler]}")
                context[CoroutineExceptionHandler]?.handleException(context, it)
            }
            result.onSuccess {
                println("onSuccess ${Thread.currentThread().name} result: ${it}")

            }
        }

    })

    @org.junit.Test
    public fun testContext() {
        contextTest.resume(Unit)
    }

    /**
     * 拦截器！！
     * 我们可以通过拦截器，拦截异步回调时的恢复调用。（协程的线程调度也是通过它去完成。）
     *
     * 我们先定义一个 LogContinuation 对象，毫无疑问，它是一个Continuation。
     * 我们可以看到LogContinuation构造方法里面接收一个Continuation，那就明白了，LogContinuation实际上是给原始
     * 的 Continuation 包装了一层。
     * LogContinuation通过resumeWith方法去对异步回调做操作，在调用原本的continuation的resumeWith()之前和之后，都能做对应的操作。
     * 当然，也可以直接不调用。那就等于拦截了。也可以返回一个虚假的result。可以说是为所欲为了。
     * 如果拦截了，下面的代码都不会执行了。
     *
     * 看 interceptorTest 作为例子。
     * 我们先看下continuation的调用情况。
     * interceptorTest 中我们调用了3次挂起函数 suspendFunc02() ，每次调用 suspendFunc02() 都会通过恢复调用一次。
     * 而协程启动的时候，也会调用一次恢复调用来执行协程体从开始到下次挂起之间的逻辑。
     * 所以恢复调用，即 LogContinuation 会被调用 1+3次。
     *！！！问题是， interceptorTest createContinue的时候传入的continuation只会在最后一个挂起函数恢复调用时被调用。（这是一个完成回调？）
     * 我觉得可以这么理解，continuation是负责协程体中所有非挂起函数的部分，挂起函数自己负责自己。当挂起函数返回了值，
     * 就会调用continuation的resumeWith方法，把得出来的值给它，让它在挂起点继续运行下去。
     *
     */
    class LogInterceptor : ContinuationInterceptor {
        override val key: CoroutineContext.Key<*> = ContinuationInterceptor
        override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
            println("LogInterceptor")
            return LogContinuation<T>(continuation)
        }
    }

    class LogContinuation<T>(private val continuation: Continuation<T>) :
        Continuation<T> by continuation {
        override fun resumeWith(result: Result<T>) {
            println("LogContinuation before resume : $result")
            //continuation.resumeWith( Result.success(99 as T))
            continuation.resumeWith(result)
            println("LogContinuation after resume : $result")
        }
    }

    private val interceptorTest = suspend {
        println("interceptorTest 1")
        suspendFunc02(1)
        println("interceptorTest 2")
        suspendFunc02(2)
        println("interceptorTest 3")
        suspendFunc02(3)
    }.createCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = LogInterceptor()

        override fun resumeWith(result: Result<Int>) {
            println("Continuation resumeWith resume : $result")
        }

    })
    private val interceptorTest2 = suspend {
        println("interceptorTest 1")
        suspendFunc02(1)
        println("interceptorTest 2")
        suspendFunc02(2)
        println("interceptorTest 3")
        suspendFunc02(3)
    }.createCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = this@KCoroutine.mContext

        override fun resumeWith(result: Result<Int>) {
            println("Continuation resumeWith resume : $result")
        }

    })

    @org.junit.Test
    public fun testInterceptor() {
        interceptorTest.resume(Unit)
        runBlocking {
            delay(1000)
        }
    }

    @org.junit.Test
    public fun test() {
//        continuation1.resume(Unit)

        launchCoroutine(Test()) {
            println("test1")
            joinTest(2)
            delay(199)
            println("test2")
            coroutineContext[CoroutineName]
            1
        }
        launchCoroutine(2) {

        }
        runBlocking {
            delay(1000)
        }

    }

    val threadExecutor = Executors.newScheduledThreadPool(1) { r ->
        Thread(r, "scheduled").apply { isDaemon = true }
    }
//        ThreadPoolExecutor(1, 1, 10000, TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>())

    /**
     * 自己模拟一个delay函数
     */
    private suspend fun kDelay(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) =
        suspendCoroutine<Unit> {
            threadExecutor.schedule({ it.resume(Unit) }, time, unit)
        }

    @org.junit.Test
    public fun delayTest() {
        runBlocking {
            println("开始delay ${System.currentTimeMillis()}")
            kDelay(1000)
            println("不再delay ${System.currentTimeMillis()}")
        }
    }
}