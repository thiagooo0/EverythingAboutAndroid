package com.kowksiuwang.everythingaboutandroid.leetcode.lc0154_findmin

import org.junit.Assert
import org.junit.Test

/**
 * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
 *
 * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
 *
 * 请找出其中最小的元素。
 *
 * 注意数组中可能存在重复的元素。
 *
 * 示例 1：
 *
 * 输入: [1,3,5]
 * 输出: 1
 * 示例 2：
 *
 * 输入: [2,2,2,0,1]
 * 输出: 0
 *
 * Created by GuoShaoHong on 2020/8/31.
 */
class Solution {


    @Test
    fun test() {
        Assert.assertEquals(findMin(intArrayOf(10, 1, 10, 10, 10)), 1)
        Assert.assertEquals(findMin(intArrayOf(5, 6, 7, 8, 1, 1, 2, 3, 5)), 1)
        Assert.assertEquals(findMin(intArrayOf(1, 5, 6, 7, 8, 8)), 1)
        Assert.assertEquals(findMin(intArrayOf(1, 1, 1, 1, 1)), 1)
        Assert.assertEquals(findMin(intArrayOf(5, 6, 7, 8, 1, 2, 3)), 1)
        Assert.assertEquals(findMin(intArrayOf(3, 3, 3, 5, 6, 7, 8, 1, 2, 3, 3, 3)), 1)
        Assert.assertEquals(findMin(intArrayOf(5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5)), 1)
        Assert.assertEquals(findMin(intArrayOf(5, 6, 7, 8, 1, 2, 4, 4, 5, 5, 5, 5, 5, 5)), 1)
        Assert.assertEquals(findMin(intArrayOf(5, 5, 5, 5, 5, 5, 1, 2, 3)), 1)
        Assert.assertEquals(findMin(intArrayOf(5, 6, 7, 8, 9, 10, 11, 12, 1, 1, 1, 1)), 1)
        Assert.assertEquals(findMin(intArrayOf(1, 1, 5, 6, 7, 8, 9, 10, 11, 12, 1, 1, 1, 1)), 1)
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
     *
     * 可以得到中间值K
     * 这里和153类似，不同之处在于数字会重复。重复的数字会影响二分时候的判断
     * 由于数组由升序数组旋转而来，除非没有旋转，否则k的大小一定不会在i和j之间。
     * 若k>i，则说明目标值在[k,j]，若k<j则说明目标值在[i,k]
     *
     * 而判断条件则是k>k+1或者k-1<k，即两个升序数组的结合处
     */
    fun find(nums: IntArray, i: Int, j: Int): Int {
        if (i == j) {
            return nums[i]
        }
        //如果现在是有序了，那就直接返回最小值
        if (nums[i] < nums[j]) {
            return nums[i]
        }

        val k = (i + j) / 2
        return when {

            nums[k] < nums[j] -> {
                find(nums, i, k)
            }
            nums[k] > nums[j] -> {
                find(nums, k + 1, j)
            }
            else -> {
                if (nums[k] > nums[i]) {
                    //如果k==j, 而k<i，说明最小值在左边
                    find(nums, i, k)
                } else {
                    //难点在这里，由于上面都是跟j进行对比，所以如果能进这里，就算j减了1，也能保留一个最小值在数组中。
                    find(nums, i, j - 1)
                }
            }
        }
    }
}