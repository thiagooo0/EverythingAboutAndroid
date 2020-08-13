package com.kowksiuwang.everythingaboutandroid.leetcode.lc0191_HammingWeight;

import org.junit.Assert;
import org.junit.Test;

/**
 * 请实现一个函数，输入一个整数，输出该数二进制表示中 1 的个数。例如，把 9 表示成二进制是 1001，有 2 位是 1。因此，如果输入 9，则该函数输出 2。
 * <p>
 * 示例 1：
 * <p>
 * 输入：00000000000000000000000000001011
 * 输出：3
 * 解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。
 * 示例 2：
 * <p>
 * 输入：00000000000000000000000010000000
 * 输出：1
 * 解释：输入的二进制串 00000000000000000000000010000000 中，共有一位为 '1'。
 * 示例 3：
 * <p>
 * 输入：11111111111111111111111111111101
 * 输出：31
 * 解释：输入的二进制串 11111111111111111111111111111101 中，共有 31 位为 '1'。
 *  
 * Created by GuoShaoHong on 2020/8/12.
 */

public class HammingWeight {
    @Test
    public void test() {
        Assert.assertEquals(hammingWeight(9), 2);
        Assert.assertEquals(hammingWeight(2147483645), 30);
        Assert.assertEquals(hammingWeight(-3), 31);
        Assert.assertEquals(hammingWeight(-3), 31);

    }

    public int hammingWeight(int n) {
//        return hammingWeight1(n);
        return hammingWeight2(n);
    }

    /**
     * 不断把n往右移，并且检查最后1位是否1。重点在于无符号右移。
     */
    public int hammingWeight1(int n) {
        int count = 0;
        while (n != 0) {
            if ((n & 1) != 0) {
                count++;
            }
            n = n >>> 1;
        }
        return count;
    }

    /**
     * 这里用到了一个小技巧，对于任何数n，将其与n-1相与（&），都会把最后一个1去掉。
     * 因为n-1的时候，最后一个1会被置成0，然后后面的0都变成1。和n相与后，都会被消掉了。
     */
    public int hammingWeight2(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n &= (n - 1);
        }
        return count;
    }
}
