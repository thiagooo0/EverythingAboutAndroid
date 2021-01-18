package com.kowksiuwang.everythingaboutandroid.coroutine

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

/**
 *  Created by kwoksiuwang on 12/23/20!!!
 */
class CoroutineLiteTest {

    @Test
    fun testDispatcher() {
        launch(Dispatchers.Default) {
            //这里如果不加调度器，第一句会在本协程所在的线程打印，第二句会在delay所在的线程打印，这是为什么呢？
            printlnK("lalala")
            kDelay(500)
            printlnK("wawawa")
        }
        runBlocking {
            kDelay(3000)
            printlnK("blocking finish")
        }
    }

    @Test
    fun test() {
        launch {
            printlnK()
            val deferred = async {
                getTest()
            }
            printlnK("打印 ${deferred.await()}")
            val deferred2 = async {
                getTest("第二条信息")
            }
            printlnK("打印 ${deferred.await()}  ${deferred2.await()} ")
        }

        runBlocking {
            kDelay(3000)
            printlnK("blocking finish")
        }
    }

    private suspend fun getTest(baseTest: String = ""): String {
        kDelay(1000)
        return "hahaha $baseTest"
    }
}