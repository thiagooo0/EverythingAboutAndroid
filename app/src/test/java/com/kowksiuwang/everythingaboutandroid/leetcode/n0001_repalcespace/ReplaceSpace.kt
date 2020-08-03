package com.kowksiuwang.everythingaboutandroid.leetcode.n0001_repalcespace

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.lang.StringBuilder

/**
 *
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 * 限制：0 <= s 的长度 <= 10000
 * Created by GuoShaoHong on 2020/8/3.
 */
class ReplaceSpace {
    @Test
    fun Test() {
        assertEquals(replaceSpace("hallo word"), "hallo%20word")
        assertEquals(replaceSpace("  hallo word  "), "%20%20hallo%20word%20%20")
        assertEquals(replaceSpace(" "), "%20")
        assertEquals(replaceSpace("h"), "h")
        assertEquals(replaceSpace("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhoweirrrrrrrrrrrrrrr"), "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh%20hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhoweirrrrrrrrrrrrrrr")
    }

    fun replaceSpace(str: String): String {
        return replaceSpaceViolence(str)
    }

    fun replaceSpaceViolence(str: String): String {
        val nStr = StringBuilder()
        var j = 0
        while (j < str.length) {
            if (str[j] == ' ') {
                nStr.append("%20")
            } else {
                nStr.append(str[j])
            }
            j++
        }
        return nStr.toString()
    }
}