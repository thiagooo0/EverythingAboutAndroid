package com.kowksiuwang.everythingaboutandroid.leetcode.lc0925_long_pressed_name;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kwoksiuwang on 2020/10/21!!!
 */
public class Solution {
    @Test
    public void text() {
        Assert.assertEquals(isLongPressedName("abc", "aaabbbccc"), true);
        Assert.assertEquals(isLongPressedName("saeed", "ssaaedd"), false);
        Assert.assertEquals(isLongPressedName("leelee", "lleeelee"), true);
        Assert.assertEquals(isLongPressedName("laiden", "laiden"), true);
        Assert.assertEquals(isLongPressedName("alex", "aaleex"), true);
    }

    public boolean isLongPressedName(String name, String typed) {
        int i = 0;
        int j = 0;
        while (i < name.length() && j < typed.length()) {
            if (name.charAt(i) == typed.charAt(j)) {
                if (i + 1 < name.length() && j + 1 < typed.length()) {
                    if (!(name.charAt(i) != name.charAt(i + 1) && typed.charAt(j) == typed.charAt(j + 1))) {
                        i++;
                    }
                } else if (i == name.length() - 1 && j == typed.length() - 1) {
                    return true;
                }
                j++;
            } else {
                return false;
            }

        }
        if (j < typed.length() || i < name.length()) {
            return false;
        }
        return true;
    }
}
