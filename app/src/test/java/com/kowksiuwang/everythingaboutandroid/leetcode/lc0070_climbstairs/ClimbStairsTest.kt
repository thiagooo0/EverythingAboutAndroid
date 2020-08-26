package com.kowksiuwang.everythingaboutandroid.leetcode.lc0070_climbstairs

/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
 *
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 *
 * 示例 1：
 *
 * 输入：n = 2
 * 输出：2
 * 示例 2：
 *
 * 输入：n = 7
 * 输出：21
 * 示例 3：
 *
 * 输入：n = 0
 * 输出：1
 * Created by GuoShaoHong on 2020/8/25.
 */
class ClimbStairsTest {
    fun climbStairs(n: Int): Int {
        var methodWith1 = 1
        var methodWith2 = 0
        var tempN2 = 0
        for (m in 2..n) {
            tempN2 = methodWith1
            methodWith1 += methodWith2
            methodWith2 = tempN2
        }
        return methodWith1 + methodWith2
    }
}