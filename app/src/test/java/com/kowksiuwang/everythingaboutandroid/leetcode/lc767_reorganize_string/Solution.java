package com.kowksiuwang.everythingaboutandroid.leetcode.lc767_reorganize_string;

import org.junit.Test;

/**
 * Created by kwoksiuwang on 11/30/20!!!
 */
public class Solution {
    @Test
    public void test() {
        reorganizeString("");
        reorganizeString("a");
        reorganizeString("ac");
        reorganizeString("acc");
        reorganizeString("aaac");
        reorganizeString("accac");
        reorganizeString("cacac");
        reorganizeString("acieumkd");
        reorganizeString("ccccc");
        reorganizeString("cccccwwww");
        reorganizeString("adbewcccc");
    }

    public String reorganizeString(String S) {
        int len = S.length();
        if (S == null || len < 2) {
            return S;
        }
        int[] letters = new int[26];
        int biggest = 0;
        char[] result = S.toCharArray();
        for (char c : result) {
            biggest = Math.max(++letters[c - 'a'], biggest);
        }
        if (biggest > len / 2 + (len % 2)) {
            return "";
        }
        if (biggest==1){
            return S;
        }

        int odd = 1, even = 0;
        for (int i = 0; i < 26; i++) {
            //要保证长的放在偶数位，所以先写奇数位
            while (odd < len && letters[i] > 0 && letters[i] < len / 2 + 1) {
                result[odd] = (char) ('a' + i);
                letters[i]--;
                odd += 2;
            }
            while (even < len && letters[i] > 0) {
                result[even] = (char) ('a' + i);
                letters[i]--;
                even += 2;
            }
        }
        return String.valueOf(result);
    }
    public String answer(String S){
        final int len = S.length();
        int[] arr = new int[26];
        // 统计数量最多的字符数
        int maxCount = 0;
        for (char ch : S.toCharArray()) {
            maxCount = Math.max(++arr[ch - 'a'], maxCount);
        }
        if (maxCount > (len + 1) / 2) {
            return "";
        }
        // 遍历每个字符
        int even = 0, odd = 1;
        char[] result = new char[len];
        for (int pos = 0; pos < 26; pos++) {
            // 如果字符数量大于一半的 务必放在偶数位
            // length/2+1
            //
            while (odd < len && arr[pos] > 0 && arr[pos] < len / 2 + 1) {
                result[odd] = (char) ('a' + pos);
                odd += 2;
                arr[pos]--;
            }

            while (even < len && arr[pos] > 0) {
                result[even] = (char) ('a' + pos);
                even += 2;
                arr[pos]--;
            }
        }
        return String.valueOf(result);
    }
}
