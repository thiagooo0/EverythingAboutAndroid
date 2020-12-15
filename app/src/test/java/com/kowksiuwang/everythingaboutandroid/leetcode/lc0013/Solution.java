package com.kowksiuwang.everythingaboutandroid.leetcode.lc0013;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by GuoShaoHong on 2020/10/19.
 */

public class Solution {
    String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

    @Test
    public void test(){
        Assert.assertEquals(4, romanToInt("IV"));
        Assert.assertEquals(3, romanToInt("III"));
        Assert.assertEquals(58, romanToInt("LVIII"));
        Assert.assertEquals(1994, romanToInt("MCMXCIV"));
    }
    public int romanToInt(String s) {
        int result = 0;
        int romanIndex = 0;
        int sOffset = 0;
        while (sOffset < s.length() && romanIndex < roman.length) {
            if (s.startsWith(roman[romanIndex], sOffset)) {
                sOffset += roman[romanIndex].length();
                result += values[romanIndex];
            } else {
                romanIndex++;
            }
        }
        return result;
    }
}
