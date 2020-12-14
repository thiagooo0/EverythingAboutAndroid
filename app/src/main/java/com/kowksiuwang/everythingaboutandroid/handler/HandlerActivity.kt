package com.kowksiuwang.everythingaboutandroid.handler

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.kowksiuwang.everythingaboutandroid.R
import kotlin.concurrent.thread

/**
 * Handler
 * 构造方法：
 *      为了获取到
 *      looper和looper中的queue。对于没传入looper的构造方法，会直接通过Looper.myLooper获取当前现场的looper。
 *      可能为空的callback。callback为空的时候，发消息会怎样？
 *      一个暂时未看懂的属性 async 。默认为false。会根据这个属性设置message的setAsynchronous方法。
 *
 * createAsync():Handler ：一个看着很牛逼的方法。未解。重要的点是 async 为true。
 *      创造一个handler，它post的消息和runnable都不服从于synchronization barriers（比如垂直同步）
 *
 * @hide
 * getMain():Handler : 获取一个在主线程的handler。此handler为静态的变量。所以每次获取都是同一个。
 *
 * sendMessageDelayed: 最后也是调用sendMessageAtTime。时间通过SystemClock.uptimeMillis()+delayMillis获取到
 *
 * sendMessageAtTime: 最后调用enqueueMessage。
 *
 * sendMessageAnFrontOfQueue: 最后调用enqueueMessage。区别在于updateTime是0
 *
 * enqueueMessage: 给msg设置了几个属性。
 *      target：自己
 *      workSourceUid：线程的uid？
 *      setAsynchronous:构造方法中传进来的async
 *      然后调用queue的enqueueMessage方法。这个方法暂时不深究。
 *
 * removeMessage： 调用queue中对应的方法移除message。
 * removeCallbackAndMessage： 据说会把callback也去掉。这个要看下queue的代码才知道了。
 *
 *  Looper
 *  prepare（），只能调用一次。给threadLocal变量赋值，threadlocal中存储的就是looper对象。looper对象通过threadlocal，
 *  实现每个线程只会拥有一个looper。
 *
 *  loop()，在prepare之后调用。不断通过从message queue的next()方法取数据，并且调用对于handler的dispatchMessage()方法。
 *      记得在结束的时候调用quit()方法取结束loop。
 *
 *
 *  MessageQueue
 *  next()
 */
class HandlerActivity : AppCompatActivity() {
    private val mainThreadHandler = Handler(Handler.Callback { msg ->
        log(
            "mainThreadHandler",
            "msg what:${msg.what}"
        )
        true
    })


    private var threadHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)
        initHandler()
        initThread()
        SystemClock.uptimeMillis()
    }

    private fun initThread() {
        thread {
            var i = 0
            Thread.sleep(500)
            while (i < 5) {
                i++
                log("sendThread", "send $i")
                mainThreadHandler.sendMessage(Message.obtain(mainThreadHandler, i, null))
                threadHandler?.sendMessage(Message.obtain(threadHandler, i, null))

                //等于直接调用callback（在此线程上），那为啥又要跨线程呢。
//                mainThreadHandler.dispatchMessage(Message.obtain(mainThreadHandler, i+10, null))
//                threadHandler?.dispatchMessage(Message.obtain(threadHandler, i+10, null))

                //这个头插也不代表可以马上执行，如果马上又头插一次到话，就会抢先了。
                threadHandler?.sendMessageAtFrontOfQueue(threadHandler?.obtainMessage(i + 100)!!)
                //这个delay准确么
                threadHandler?.sendMessageDelayed(threadHandler?.obtainMessage(i + 1000)!!, 1000)
            }
        }
    }

    private fun initHandler() {
        thread {
            log("threadHandler", "thread init")
            Looper.prepare()
            //为何handler的创建一定要夹在prepare和loop之间？？
            threadHandler = Handler(Handler.Callback {
                log("threadHandler", "msg what:${it.what}")
                true
            })
            //looper可以感知到thread到生命状态吗，如果thread死掉了会怎样？
            Looper.loop()

            while (true) {
            }
        }
    }

    private fun log(tag: String = "", content: String) {
        Log.d(
            "HandlerActivity $tag",
            "$content --thread:${Thread.currentThread()} threadId:${Thread.currentThread().id}"
        )
    }
}