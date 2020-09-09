package com.kowksiuwang.everythingaboutandroid.leetcode.lc0151_reversewords

import org.junit.Assert
import org.junit.Test
import java.lang.StringBuilder

/**
 * 给定一个字符串，逐个翻转字符串中的每个单词。
 *
 * 示例 1：
 *
 * 输入: "the sky is blue"
 * 输出: "blue is sky the"
 * 示例 2：
 *
 * 输入: "  hello world!  "
 * 输出: "world! hello"
 * 解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * 示例 3：
 *
 * 输入: "a good   example"
 * 输出: "example good a"
 * 解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 *  
 *
 * 说明：
 *
 * 无空格字符构成一个单词。
 * 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 *
 * Created by GuoShaoHong on 2020/9/2.
 */
class Solution {
    @Test
    fun test() {
        Assert.assertEquals(reverseWords(" i"), "i")
        Assert.assertEquals(reverseWords("a good    example"), "example good a")
        Assert.assertEquals(reverseWords("hallo world  "), "world hallo")
        Assert.assertEquals(reverseWords("  hello world!  "), "world! hello")
        Assert.assertEquals(reverseWords("the sky is blue"), "blue is sky the")
    }

    fun reverseWords(s: String): String {
//        return reverseWords1(s)
        return reverseWordsMe(s)
    }

    /**
     * 运用语言特性
     */
    fun reverseWords1(s: String): String {
        // trim:修正字符，去掉前后空格。
        // split:通过正则表达式，通过连续的空格把字符串分隔
        // reversed:反转数组
        // joiToString：把数组加入分隔符连成字符串
        return s.trim().split("\\s+".toRegex()).reversed().joinToString(" ")
    }


    /**
     * 双重指针。
     */
    fun reverseWordsMe(s: String): String {
        val result = StringBuilder()
//
        var end = s.length - 1
        var start = s.length - 1
        while (end > -1) {
            if (start == 0 || s[start] == ' ') {
                if (end == start && s[start] == ' ') {
                    end--
                    start--
                } else {
                    val sTemp = if (start == 0 && s[start] != ' ') {
                        s.substring(start, end + 1)
                    } else {
                        s.substring(start + 1, end + 1)
                    }
                    if (result.isEmpty()) {
                        result.append(sTemp)
                    } else {
                        result.append(" $sTemp")
                    }
                    start -= 1
                    end = start
                }
            } else {
                start--
            }
        }
        return result.toString()
    }

    @Test
    fun test2(){
        Assert.assertEquals(reverseLeftWords("asdfghj", 2), "dfghjas")
        Assert.assertEquals(reverseLeftWords("lrloseumgh", 16), "umghlrlose")
    }
    fun reverseLeftWords(s: String, n: Int): String {
        val tn = n%s.length
        return s.subSequence(tn, s.length).toString() + s.subSequence(0, tn)
    }
}