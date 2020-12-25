package com.kowksiuwang.everythingaboutandroid.leetcode.lc0455_snacks;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
 * <p>
 * 对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，都有一个尺寸 s[j] 。
 * 如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，
 * 并输出这个最大数值。
 * <p>
 * Created by kwoksiuwang on 12/25/20!!!
 */
public class Solution {
    @Test
    public void Test() {
        Assert.assertEquals(findContentChildren(new int[]{1, 2, 3}, new int[]{3}), 1);
        Assert.assertEquals(findContentChildren(new int[]{1, 2}, new int[]{1, 2, 3}), 2);
        Assert.assertEquals(findContentChildren(new int[]{4, 5, 3, 2, 7}, new int[]{1, 2, 3, 6, 7, 8}), 5);
    }

    /**
     * 排序，然后双指针分饼干。
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0;
        int j = 0;
        int satisfyChild = 0;
        while (i < g.length && j < s.length) {
            while (j < s.length && s[j] < g[i]) {
                j++;
            }
            if (j < s.length) {
                i++;
                j++;
                satisfyChild++;
            }
        }
        return satisfyChild;
    }

    /**
     * 思路相同，算法改良。
     * 指向孩子的指针和分到的糖果数两个变量可以合并。
     * 只需要沿着排好序的饼干数组数下来，统计有没有对应的孩子即可。
     *
     */
    public int findContentChildren2(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int count = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] >= g[count]) {
                count++;
                if (count == g.length) {
                    break;
                }
            }
        }
        return count;
    }
}
