package com.kowksiuwang.everythingaboutandroid.leetcode.lc0172_factorial_trailing_zeroe;

/**
 * 给定一个整数 n，返回 n! 结果尾数中零的数量。
 * <p>
 * Created by kwoksiuwang on 12/14/20!!!
 */
public class Solution {
    /**
     * 其实最终思路就是，把N！化成最简公因式 1*2*3*4*5*6*7=》1*2*3*（2*2）*5*（2*3）*7。。。
     * 结果里面有多少个0，就取决与有多少个2*5（10也会转化为2*5），而2的数量肯定比5多。所以就是算有多少个5。
     *
     * 想一下，25（5*5）之前，每5个数只会有一个5。125（5*5*5），每5个数只会有两个5。如此下来
     *
     * @param n
     * @return
     */
    public int trailingZeroes(int n) {
        int count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }
        return count;
    }

    public int trailingZeroes2(int n) {
        int zeroCount = 0;
        // We need to use long because currentMultiple can potentially become
        // larger than an int.
        long currentMultiple = 5;
        while (n >= currentMultiple) {
            zeroCount += (n / currentMultiple);
            currentMultiple *= 5;
        }
        return zeroCount;
    }
}
