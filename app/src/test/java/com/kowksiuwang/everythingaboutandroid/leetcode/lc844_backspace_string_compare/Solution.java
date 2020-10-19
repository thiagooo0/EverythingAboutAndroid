package com.kowksiuwang.everythingaboutandroid.leetcode.lc844_backspace_string_compare;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by GuoShaoHong on 2020/10/19.
 */

public class Solution {
    @Test
    public void test() {
        Assert.assertEquals(backspaceCompare("bbbextm", "bbb#extm"), false);
        Assert.assertEquals(backspaceCompare("abc######", "abcd#########"), true);
        Assert.assertEquals(backspaceCompare("abc#", "##abcd##"), true);
        Assert.assertEquals(backspaceCompare("a#c", "b"), false);
        Assert.assertEquals(backspaceCompare("a##c", "#a#c"), true);
    }

    public boolean backspaceCompare(String S, String T) {
        int i = S.length() - 1;
        int j = T.length() - 1;
        int tempBackspace = 0;
        while (i >= 0 || j >= 0) {
            tempBackspace = 0;
            while (i >= 0) {
                if (S.charAt(i) == '#') {
                    tempBackspace++;
                    i--;
                } else if (tempBackspace > 0) {
                    tempBackspace--;
                    i--;
                } else {
                    break;
                }
            }
            tempBackspace = 0;
            while (j >= 0) {
                if (T.charAt(j) == '#') {
                    tempBackspace++;
                    j--;
                } else if (tempBackspace > 0) {
                    tempBackspace--;
                    j--;
                } else {
                    break;
                }
            }
            if (i >= 0 && j >= 0) {
                if (S.charAt(i) != T.charAt(j)) {
                    return false;
                }
            } else if (i >= 0 || j >= 0) {
                return false;
            }
            i--;
            j--;

        }
        return true;
    }
}
