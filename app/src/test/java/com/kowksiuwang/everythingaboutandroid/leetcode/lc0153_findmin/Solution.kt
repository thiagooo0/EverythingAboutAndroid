package com.kowksiuwang.everythingaboutandroid.leetcode.lc0153_findmin

import org.junit.Assert
import org.junit.Test

/**
 * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
 *
 * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
 *
 * 请找出其中最小的元素。
 *
 * 你可以假设数组中不存在重复元素。
 *
 * 示例 1:
 *
 * 输入: [3,4,5,1,2]
 * 输出: 1
 * 示例 2:
 *
 * 输入: [4,5,6,7,0,1,2]
 * 输出: 0
 *
 * Created by GuoShaoHong on 2020/8/31.
 */
class Solution {

    @Test
    fun test() {
        Assert.assertEquals(findMin(intArrayOf(5, 6, 7, 8, 1, 2, 3)), 1)
        Assert.assertEquals(findMin(intArrayOf(1, 5, 6, 7, 8)), 1)
        Assert.assertEquals(findMin(intArrayOf(5, 6, 7, 8, 1, 2, 3)), 1)
        Assert.assertEquals(findMin(intArrayOf(5, 6, 7, 8, 1, 2)), 1)
        Assert.assertEquals(findMin(intArrayOf(5, 1, 2, 3)), 1)
        Assert.assertEquals(findMin(intArrayOf(5, 6, 7, 8, 9, 10, 11, 12, 1)), 1)
        Assert.assertEquals(findMin(intArrayOf(5, 6, 7, 8, 9, 10, 11, 12, 1, 2, 3, 4)), 1)
    }

    /**
     * 这一题当然可以使用暴力法。
     *
     * 也可以使用二分法，
     */
    fun findMin(nums: IntArray): Int {
        if (nums.isEmpty()) {
            return 0
        }
        if (nums.size == 1) {
            return nums[0]
        }
        if (nums[0] < nums[nums.size - 1]) {
            return nums[0]
        }
        return find(nums, 0, nums.size - 1)
    }

    /**
     * 不断通过二分法找到最小值。
     * 我可以得到中间值K
     * 由于数组由升序数组旋转而来，除非没有旋转，否则k的大小一定不会在i和j之间。
     * 若k>i，则说明目标值在[k,j]，若k<j则说明目标值在[i,k]
     *
     * 而判断条件则是k>k+1或者k-1<k，即两个升序数组的结合处
     */
    fun find(nums: IntArray, i: Int, j: Int): Int {
        if (i == j) {
            return nums[i]
        }
        val k = (i + j) / 2
        if (nums[k] > nums[k + 1]) {
            return nums[k + 1]
        }
        if (nums[k - 1] > nums[k]) {
            return nums[k]
        }

        return if (nums[k] < nums[j]) {
            find(nums, i, k)
        } else {
            find(nums, k, j)
        }
    }
}