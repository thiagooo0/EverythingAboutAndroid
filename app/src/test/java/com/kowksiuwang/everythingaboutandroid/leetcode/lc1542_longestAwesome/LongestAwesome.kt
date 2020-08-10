package com.kowksiuwang.everythingaboutandroid.leetcode.lc1542_longestAwesome

import org.junit.Assert
import org.junit.Test

/**
 * 1542. 找出最长的超赞子字符串
 *
 * 给你一个字符串 s 。请返回 s 中最长的 超赞子字符串 的长度。
 * 「超赞子字符串」需满足满足下述两个条件：
 * 该字符串是 s 的一个非空子字符串
 * 进行任意次数的字符交换后，该字符串可以变成一个回文字符串
 *  
 * 示例 1：
 * 输入：s = "3242415"
 * 输出：5
 * 解释："24241" 是最长的超赞子字符串，交换其中的字符后，可以得到回文 "24142"
 *
 * 示例 2：
 * 输入：s = "12345678"
 * 输出：1
 *
 * 示例 3：
 * 输入：s = "213123"
 * 输出：6
 * 解释："213123" 是最长的超赞子字符串，交换其中的字符后，可以得到回文 "231132"
 *
 * 示例 4：
 * 输入：s = "00"
 * 输出：2
 *  
 * 提示：
 * 1 <= s.length <= 10^5
 * s 仅由数字组成
 *
 * Created by GuoShaoHong on 2020/8/10.
 */
class LongestAwesome {
    @Test
    fun test() {
        Assert.assertEquals(longestAwesome("213123"), 6)
        Assert.assertEquals(longestAwesome("4231394"), 3)
        Assert.assertEquals(longestAwesome("12345654321"), 11)
        Assert.assertEquals(longestAwesome("4331221"), 7)
        Assert.assertEquals(longestAwesome("22"), 2)
        Assert.assertEquals(longestAwesome("1"), 1)
        Assert.assertEquals(longestAwesome("532349123123"), 7)
        Assert.assertEquals(longestAwesome("532344123123"), 11)
    }

    fun longestAwesome(s: String): Int {
        return longestAwesome1(s)

    }

    fun longestAwesome1(s: String): Int {
        val statusIndexArray = IntArray(1 shl 11) {
            -2
        }
        var status = 0
        //0代表最开始遍历数组前的状态，坐标是-1。设置为-1是方便后面的计算。
        statusIndexArray[0] = -1
        //存放长的最赞字符串长度
        var result = 0
        val length = s.length
        for (index in 0 until length) {
            //求出当前index的status
            status = status xor (1 shl (s[index] - '0'))
            //判断变化次数全部为偶数的超赞字符串
            if (statusIndexArray[status] != -2) {
                //之前存在相同的状态值,通过index - statusIndexArray[status]求两个字符之间的长度，和result相比较
                result = Math.max(result, index - statusIndexArray[status])
            } else {
                //否则就赋值
                statusIndexArray[status] = index
            }
            //判断一个字符变化次数为奇数的超赞字符串。通过给status分别加上1左移0-9的值，在查看statusIndexArray中是否存在新的status，
            //如果存在就代表这两个status之间有且只有一个字符变化了奇数次。
            for (extendNum in 0..9) {
                //构造出一种比status多一个数会变化奇数次的status，然后查下statusIndexArray中是否存在新的status
                val statusNew = status xor (1 shl extendNum)
                if (statusIndexArray[statusNew] != -2) {
                    //之前存在statusNew,通过index - statusIndexArray[statusNew]求两个字符之间的长度，和result相比较
                    result = Math.max(result, index - statusIndexArray[statusNew])
                }
            }
        }
        return result
    }
}