package com.kowksiuwang.everythingaboutandroid.kotlin

import org.junit.Test

/**
 *  Created by kwoksiuwang on 12/11/20!!!
 */
class EqualTest {
    @Test
    fun test() {
        var a = "kot"
        var b = "lin"
        var c = a + b
        var d = "kotlin"
        System.out.println(c === d)
    }
}