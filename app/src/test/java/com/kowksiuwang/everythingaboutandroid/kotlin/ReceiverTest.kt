package com.kowksiuwang.everythingaboutandroid.kotlin

/**
 * Created by GuoShaoHong on 2020/12/14.
 */
class ReceiverTest {
    fun test1(): Int {
        println("${this.toString()}")
        return 1
    }

    /**
     * 最基本的，是一个成员变量。如果去掉“ReceiverTest2.”，就跟fun3()一样，变量类型是一个方法，接收一个int，返回一个string。
     *
     * “ReceiverTest2.”指明了接收者是ReceiverTest2类，所以只有ReceiverTest2的实例才能使用此方法。如果像fun3
     * 那样没指定的，就默认是本类。
     * 除了lamda表达式，还可以用匿名函数去赋值了。
     *
     *  当然，fun2和fun3中的this也是不同的，fun2中的this是ReceiverTest2，而fun3中的this是ReceiverTest，即本类
     */


    var fun2: ReceiverTest2.(i: Int) -> String = { it ->
        "$it"
    }

    var fun3: (i: Int) -> String = { it ->
        "$it"
    }

    var fun4: ReceiverTest2.(i: Int) -> String = fun ReceiverTest2.(i: Int):String {
        return "$i"
    }

    private fun fun6(i: Int): String {
        return "$i"
    }

    private fun fun7(f: (Int) -> String) {

    }

    /**
     * receiver type在扩展函数和成员变量上的不同
     */
    fun Int.sumDouble(i: Int): Double {
        compareTo(2)
        plus(12)
        return 32434.0
    }

    val sumLong: Int.(i: Int) -> Long = {
        1L
    }

    fun main() {
        test1()
        fun7(fun3)
        fun7(::fun6)

        val t2 = ReceiverTest2()
        t2.fun2(1)
        fun3(1)

        1.sumDouble(1)
        2.sumLong(1)

    }

}

class ReceiverTest2 {
    fun main() {

    }
}