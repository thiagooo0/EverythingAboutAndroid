package com.kowksiuwang.everythingaboutandroid.leetcode.n0007_findrepeatnumber

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
                    10,
                    9,
                    1,
                    8,
                    1,
                    1,
                    6
                )
            ), 9
        )
    }

    fun findRepeatNumberTest(nums: IntArray): Int {
//        return findRepeatNumberTest1(nums)
        return findRepeatNumberTest3(nums)
    }

    /**
     * 解法1：这里可以用hashset
     */
    fun findRepeatNumberTest1(nums: IntArray): Int {
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


    /**
     * 解法2：因为2 <= n <= 100000，所以可以用一个IntArray(100_001)来做判断。
     */
    //留空
// 1,2,3,4,5,5
    /**
     * 解法3：因为长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内，所以可以把数组本身拿来做哈希表。
     * 这样算法复杂度和解法1相同为n，但是空间复杂度降低了。
     */

    fun findRepeatNumberTest3(nums: IntArray): Int {
        if (nums.isEmpty()) {
            return -1
        }
        var n: Int
        for (i in nums.indices) {
            while (i != nums[i]) {
                //如果i位置上的数和目标位置上的数相同，就可以返回了
                if (nums[i] == nums[nums[i]]) {
                    return nums[i]
                }
                //否则就把nums[i]位置上的数放到index为num[i]的位置上去。
                n = nums[i]
                nums[i] = nums[nums[i]]
                nums[n] = n
            }
        }
        return -1
    }
}