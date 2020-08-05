package com.kowksiuwang.everythingaboutandroid.leetcode.n0004_twosum

import org.junit.Assert
import org.junit.Test

/**
 * 输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和等于s，则输出任意一对即可。
 *  
 * 示例 1：
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[2,7] 或者 [7,2]
 *
 * 示例 2：1
 * 输入：nums = [10,26,30,31,47,60], target = 40
 * 输出：[10,30] 或者 [30,10]
 *  
 * 限制：
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^6
 *
 * Created by GuoShaoHong on 2020/8/5.
 */
class TwoSum {
    @Test
    fun Test() {
        Assert.assertEquals(
            twoSumSearch(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 15).contentEquals(
                intArrayOf(5, 10)
            ),
            true
        )
        Assert.assertEquals(
            twoSumSearch(intArrayOf(1, 2, 3, 4, 5, 6, 10), 15).contentEquals(
                intArrayOf(5, 10)
            ),
            true
        )
        Assert.assertEquals(
            twoSumSearch(
                intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 22, 28, 48, 99),
                102
            ).contentEquals(
                intArrayOf(3, 99)
            ),
            true
        )
        Assert.assertEquals(
            twoSumSearch(
                intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 22, 28, 48, 99),
                9
            ).contentEquals(
                intArrayOf(1, 8)
            ),
            true
        )
        Assert.assertEquals(
            twoSumSearch(intArrayOf(1, 2), 3).contentEquals(
                intArrayOf(1, 2)
            ),
            true
        )
    }

    fun twoSumSearch(array: IntArray, target: Int): IntArray {
        var i = 0
        var j = array.size - 1
        while (i != j) {
            when {
                array[i] + array[j] > target -> {
                    j--
                }
                array[i] + array[j] < target -> {
                    i++
                }
                else -> {
                    return intArrayOf(array[i], array[j])
                }
            }
        }
        return intArrayOf()
    }
}