package com.kowksiuwang.everythingaboutandroid.kotlin

import java.util.function.Function

/**
 *  高阶函数
 *  Created by kwoksiuwang on 12/10/20!!!
 */
class HighClassMethod {
    fun m1(m: (i: Int) -> Unit) {

    }

    fun m2(i: Int) {

    }

    fun m3(m: (i: Int, j: Int, k: String) -> Boolean) {

    }

    fun m4(f1: (Int) -> String, f2: (Int, Int) -> String) {

    }

    private fun test() {
        m1 { i: Int ->
            var j = i
            var k = 0
            var b = j + k
        }
        m1(::m2)
        val hct = HighClassTest()
        m1(hct::dealInt)
        val age = hct::age
        val tall = 100
        val pair = age to tall
        age come tall

        val unit = Unit

        m1 { i ->
            val a = 0
            m2(i)
        }

        m4(fun(i: Int): String = "", fun(i: Int, j: Int): String { return "$i" })
    }

    private fun testJava() {
        val jm = JavaMethods()
        jm.n1 { it ->
            return@n1 "$it ok"
        }
        jm.n2(
            fun(i: Int): String {
                return "$i"
            },
            fun(s1: String, s2: String): Int {
                return s1.length + s2.length
            })

        jm.n2({ i -> "$i" }, { s1, s2 -> s1.length + s2.length })
    }
}

public infix fun <A, B> A.come(that: B): Pair<A, B> = Pair(this, that)

class HighClassTest() {
    val age = 2
    fun dealInt(i: Int) {

    }
}