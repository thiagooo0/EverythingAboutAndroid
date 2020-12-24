package com.kowksiuwang.everythingaboutandroid

/**
 *  Created by kwoksiuwang on 12/23/20!!!
 */

fun printlnK(msg: String = "") {
    println("${System.currentTimeMillis()} [${Thread.currentThread().name}] : $msg")
}