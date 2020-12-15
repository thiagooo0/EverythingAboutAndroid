package com.kowksiuwang.everythingaboutandroid.leetcode.lc1207_unique_occurrences;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kwoksiuwang on 2020/10/28!!!
 */
public class Solution {
    @Test
    public void test() {
        Assert.assertEquals(uniqueOccurrences(new int[]{1,2,2,1,1,3}), false);
    }

    public boolean uniqueOccurrences(int[] arr) {
        int[] result = new int[2001];
        for (int i = 0; i < arr.length; i++) {
            if (result[arr[i] + 1000] > 0) {
                return false;
            } else {
                result[arr[i] + 1000] = 1;
            }
        }
        return true;
    }
}
