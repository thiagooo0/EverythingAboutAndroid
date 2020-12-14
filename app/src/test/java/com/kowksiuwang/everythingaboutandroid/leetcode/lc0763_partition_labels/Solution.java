package com.kowksiuwang.everythingaboutandroid.leetcode.lc0763_partition_labels;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一个字母只会出现在其中的一个片段。返回一个表示每个字符串片段的长度的列表。
 * <p>
 * 示例 1：
 * <p>
 * 输入：S = "ababcbacadefegdehijhklij"
 * 输出：[9,7,8]
 * 解释：
 * 划分结果为 "ababcbaca", "defegde", "hijhklij"。
 * 每个字母最多出现在一个片段中。
 * 像 "ababcbacadefegde", "hijhklij" 的划分是错误的，因为划分的片段数较少。
 *  
 * <p>
 * 提示：
 * <p>
 * S的长度在[1, 500]之间。
 * S只包含小写字母 'a' 到 'z' 。
 * <p>
 * Created by kwoksiuwang on 2020/10/22!!!
 */
public class Solution {
    @Test
    public void test() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, 9, 1, 7, 8);
        Assert.assertEquals(arrayList.equals(partitionLabels("ababcbacazdefegdehijhklij")), true);

        ArrayList<Integer> arrayList2 = new ArrayList<>();
        Collections.addAll(arrayList2, 1);
        Assert.assertEquals(arrayList2.equals(partitionLabels("a")), true);


        ArrayList<Integer> arrayList3 = new ArrayList<>();
        Collections.addAll(arrayList3, 4, 1, 1, 1, 1, 1);
        Assert.assertEquals(arrayList3.equals(partitionLabels("aaaabcdef")), true);
    }

    public List<Integer> partitionLabels(String S) {
        List<Integer> result = new ArrayList<>();
        //第一次遍历后，我要在每个字母第一次出现的时候，就知道它最后一次出现在什么地方。
        //这里为什么要搞两个hashmap，你是脑残吗？？？？？？一个数组不就搞掂了吗？？？
        HashMap<Character, Integer> mapHeadChar = new HashMap<>();
        HashMap<Character, Integer> mapTailChar = new HashMap<>();
        for (int i = 0; i < S.length(); i++) {
            if (!mapHeadChar.containsKey(S.charAt(i))) {
                mapHeadChar.put(S.charAt(i), i);
                mapTailChar.put(S.charAt(i), i);
            } else {
                mapTailChar.replace(S.charAt(i), i);
            }
        }

        //第二次遍历，通过第一个字母确定暂时当前片段最后一位的位置N，然后指针往后移动，判断当前字母最后一次出现的位置，如果大于N，则把N重新赋值。
        //一直到指针指向N。同理求出其他的所有片段。
        int head = 0;
        int tail = 0;
        int p = 0;
        while (head < S.length()) {
            tail = mapTailChar.get(S.charAt(head));
            while (p < tail) {
                p++;
                if (mapTailChar.get(S.charAt(p)) > tail) {
                    tail = mapTailChar.get(S.charAt(p));
                }
            }
            result.add(tail - head + 1);
            tail = ++p;
            head = p;
        }
        return result;
    }
}
