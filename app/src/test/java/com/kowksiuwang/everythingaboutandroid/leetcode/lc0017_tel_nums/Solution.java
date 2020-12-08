package com.kowksiuwang.everythingaboutandroid.leetcode.lc0017_tel_nums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kwoksiuwang on 2020/10/30!!!
 */
public class Solution {
    private List<String> result;
    private char[][] chars = new char[][]{{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}, {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};

    public List<String> letterCombinations(String digits) {
        result = new ArrayList<>();
        if (digits.length() != 0) {
            find(digits, 0, "");
        }
        return result;
    }

    char[] currentChar;

    private void find(String digits, int index, String s) {
        if (index == digits.length()) {
            result.add(s);
            return;
        } else {
            currentChar = chars[digits.charAt(index) - '2'];
            for (char c : currentChar) {
                find(digits, index + 1, s + c);
            }

        }
    }

    public List<String> letterCombinations1(String digits) {
        result = new ArrayList<>();
        if (digits.length() != 0) {
            find1(digits, 0, new StringBuilder());
        }
        return result;
    }

    private void find1(String digits, int index, StringBuilder s) {
        if (index == digits.length()) {
            result.add(s.toString());
            return;
        } else {
            currentChar = chars[digits.charAt(index) - '2'];
            for (char c : currentChar) {
                find1(digits, index + 1, s.append(c));
                s.deleteCharAt(index);
            }

        }
    }
}
