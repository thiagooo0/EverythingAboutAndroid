package com.kowksiuwang.everythingaboutandroid.coroutine

/**
 *  Created by kwoksiuwang on 12/16/20!!!
 */
class GeneratorTest {

}

interface Generator<T> {
    operator fun iterator(): Iterator<T>
}