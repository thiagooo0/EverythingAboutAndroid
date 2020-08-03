package com.kowksiuwang.everythingaboutandroid.leetcode.n0003_oddevensort

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
 * Created by GuoShaoHong on 2020/8/3.
 */
class OddEvenSort {
    @Test
    fun test() {
        assertEquals(sort(intArrayOf(2, 4, 1, 3)).contentEquals(intArrayOf(3, 1, 4, 2)), true)
    }

    fun sort(array: IntArray): IntArray {
        if (array.isEmpty()) return array
        var i = 0
        var j = array.size - 1
        while (i != j) {
            when {
                array[i] % 2 == 1 -> {
                    i++
                }
                array[j] % 2 == 0 -> {
                    j--
                }
                else -> {
                    exchangeNum(array, i, j)
                }
            }
        }
        return array
    }

    fun exchangeNum(array: IntArray, i: Int, j: Int) {
        array[i] = array[j] - array[i]
        array[j] = array[j] - array[i]
        array[i] = array[j] + array[i]
    }
}