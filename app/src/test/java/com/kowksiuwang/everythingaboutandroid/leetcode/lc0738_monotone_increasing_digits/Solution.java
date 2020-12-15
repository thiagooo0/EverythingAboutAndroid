package com.kowksiuwang.everythingaboutandroid.leetcode.lc0738_monotone_increasing_digits;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by GuoShaoHong on 2020/12/15.
 */

public class Solution {
    @Test
    public void test() {
        Assert.assertEquals(monotoneIncreasingDigits(10), 9);
        Assert.assertEquals(monotoneIncreasingDigits(1000), 999);
        Assert.assertEquals(monotoneIncreasingDigits(210), 199);
        Assert.assertEquals(monotoneIncreasingDigits(1234), 1234);
        Assert.assertEquals(monotoneIncreasingDigits(332), 299);
        Assert.assertEquals(monotoneIncreasingDigits(67890), 67889);
    }

    /**
     * 基本思路，从头到尾遍历，看每个数是否符合标准。如果符合，就到下一个数。如果不符合，看自己减一是否符合，
     * 符合的话就可以把后面的全部变成9，否则就返回上一个数，让上一个数变成9
     * @param N
     * @return
     */
    public int monotoneIncreasingDigits(int N) {
        if (N / 10 == 0) {
            return N;
        }

        int[] ns = new int[String.valueOf(N).length()];
        for (int i = ns.length - 1; i >= 0; i--) {
            ns[i] = N % 10;
            N /= 10;
        }
        change(ns, 0);
        for (int i : ns) {
            N = N * 10 + i;
        }
        return N;
    }

    private boolean change(int[] ns, int index) {
        if (index == ns.length) {
            return true;
        }
        if (index == ns.length - 1) {
            if (ns[index] >= ns[index - 1]) {
                return true;
            } else {
                return false;
            }
        }
        if (index == 0) {
            if (ns[index] <= ns[index + 1]) {
                if (!change(ns, index + 1)) {
                    changeAllTo9(ns, index);
                }
            } else {
                changeAllTo9(ns, index);
            }
            return true;
        } else {
            if (ns[index] <= ns[index + 1]) {
                if (!change(ns, index + 1)) {
                    //先看下自己-1是否符合规矩。如果符合，就把后面归九，否则返回false；
                    if (ns[index - 1] <= ns[index] - 1) {
                        changeAllTo9(ns, index);
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                if (ns[index - 1] <= ns[index] - 1) {
                    changeAllTo9(ns, index);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * index的值-1，后面全部归9。要留意index值是0的情况。
     *
     * @param ns
     * @param index
     */
    private void changeAllTo9(int[] ns, int index) {
        int i = index;
        while (ns[i] == 0) {
            ns[i] = 9;
            i--;
        }
        ns[i] = ns[i] - 1;
        i = index + 1;
        while (i < ns.length) {
            ns[i] = 9;
            i++;
        }
    }
}
