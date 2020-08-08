package com.kowksiuwang.everythingaboutandroid.n0007_findrepeatnumber

import org.junit.Assert
import org.junit.Test

/**
 * 找出数组中重复的数字。
 *
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
 *
 * 示例 1：
 * 输入：
 * [2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 *
 * 限制：
 * 2 <= n <= 100000
 *
 * 作者：画手大鹏
 * 链接：https://leetcode-cn.com/leetbook/read/illustrate-lcof/xzktv1/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * Created by GuoShaoHong on 2020/8/8.
 */
class FindRepeatNumber {

    @Test
    fun test() {
        Assert.assertEquals(findRepeatNumberTest(intArrayOf(0, 0, 1, 2, 3)), 0)
        Assert.assertEquals(findRepeatNumberTest(intArrayOf(0, 1, 1, 2, 3)), 1)
        Assert.assertEquals(
            findRepeatNumberTest(
                intArrayOf(
                    0,
                    1,
                    4,
                    2,
                    3,
                    5,
                    6,
                    7,
                    8,
                    9,
                    12,
                    13,
                    14,
                    15,
                    16,
                    1,
                    6
                )
            ), 1
        )
    }

    /**
     * 这里可以用hashset，同时因为2 <= n <= 100000，所以也可以用一个IntArray(100_001)来做判断
     */
    fun findRepeatNumberTest(nums: IntArray): Int {
        val set = HashSet<Int>()
        if (nums.isEmpty()) {
            return -1
        }
        for (i in nums) {
            if (set.contains(i)) {
                return i
            } else {
                set.add(i)
            }
        }
        return -1
    }
}