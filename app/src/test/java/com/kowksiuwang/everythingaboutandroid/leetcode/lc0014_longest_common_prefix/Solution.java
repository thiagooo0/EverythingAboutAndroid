package com.kowksiuwang.everythingaboutandroid.leetcode.lc0014_longest_common_prefix;

/**
 * Created by kwoksiuwang on 2020/10/24!!!
 */
public class Solution {
    public String longestCommonPrefix(String[] strs) {
        if(strs.length == 0){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        char currentChar;
        for (int i = 0; i < strs[0].length(); i++) {
            currentChar = strs[0].charAt(i);
            for (int j = 0; j < strs.length; j++) {
                if (i >= strs[j].length() || currentChar != strs[j].charAt(i)) {
                    return builder.toString();
                }
            }
            builder.append(currentChar);
        }
        return builder.toString();
    }
}
