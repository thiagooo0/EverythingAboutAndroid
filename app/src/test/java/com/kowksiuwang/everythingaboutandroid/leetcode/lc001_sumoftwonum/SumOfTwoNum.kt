package com.kowksiuwang.everythingaboutandroid.leetcode.lc001_sumoftwonum

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * Created by GuoShaoHong on 2020/7/30.
 */
class SumOfTwoNum {
    @Test
    fun test() {
        assertEquals(twoSum(intArrayOf(0, 1, 2, 4), 3).contentEquals(intArrayOf(1, 2)), true)
        assertEquals(
            twoSum(intArrayOf(0, 9, 8, 2, 3, 4, 5), 10).contentEquals(intArrayOf(2, 3)),
            true
        )
        assertEquals(
            twoSum(intArrayOf(1, 3, 7, 9, 22, 24), 46).contentEquals(intArrayOf(4, 5)),
            true
        )
        assertEquals(
            twoSum(intArrayOf(22, 1, 3, 7, 9, 24), 46).contentEquals(intArrayOf(0, 5)),
            true
        )
    }

    /**
     *
     */
    fun twoSum(nums: IntArray, target: Int): IntArray {
        //哈希表解法
//        return twoSumWithHash(nums, target)

        //暴力解法
        return twoSumWithViolence(nums, target)
    }

    /**
     * 用哈希表帮忙，快速找到另一半再哪里
     */
    fun twoSumWithHash(nums: IntArray, target: Int): IntArray {
        val hashMap = HashMap<Int, Int>((nums.size * 1.5).toInt())
        val result = intArrayOf(0, 1)
        var otherHalf = 0
        for ((index, i) in nums.withIndex()) {
            otherHalf = target - i
            if (hashMap.contains(otherHalf)) {
                result[0] = hashMap[otherHalf]!!
                result[1] = index
                return result
            } else {
                hashMap[i] = index
            }
        }
        return result
    }

    /**
     * 暴力破解
     */
    fun twoSumWithViolence(nums: IntArray, target: Int): IntArray {
        for ((indexI, i) in nums.withIndex()) {
            for ((indexJ, j) in nums.withIndex()) {
                if (indexI != indexJ && i + j == target) {
                    return intArrayOf(indexI, indexJ)
                }
            }
        }
        return intArrayOf(0, 1)
    }
}