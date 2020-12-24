package com.kowksiuwang.everythingaboutandroid.leetcode.lc0135_candy;

import org.junit.Test;

/**
 * [1,2,3,4,3,2,1]
 * [1,2,2]
 * [2,1,3]
 * Created by kwoksiuwang on 12/24/20!!!
 */
public class Solution {
    @Test
    public void test() {
        candy(new int[]{1, 6, 10, 8, 7, 3, 2});
    }

    public int candy(int[] ratings) {
        int[] candyList = new int[ratings.length];
        candyList[0] = 1;
        int i = 1;
        int j = 0;
        while (i < ratings.length) {
            if (ratings[i] == ratings[i - 1]) {
                candyList[i] = 1;
                j = i;
            } else if (ratings[i] > ratings[i - 1]) {
                candyList[i] = candyList[i - 1] + 1;
                j = i;
            } else {
                if (candyList[i - 1] == 1) {
                    int k = i - 1;
                    candyList[i] = 1;
                    while (k >= j) {
                        candyList[k] = Math.max(candyList[k + 1] + 1, candyList[k]);
                        k--;
                    }
                } else {
                    candyList[i] = 1;
                }
            }
            i++;
        }
        int result = 0;
        for (int num : candyList) {
            result += num;
        }
        return result;
    }

    /**
     * 两次遍历
     * 先从左向右，再从右向左。
     * 从左向右的时候，只统计增加的值。遇到递增的情况，就比左边的+1，否则是1。
     * 从右向左的时候，
     *
     * @param ratings
     * @return
     */
    public int candy2(int[] ratings) {
        int result = 0;
        if (ratings.length == 0) {
            return result;
        }
        int[] left = new int[ratings.length];
        left[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }
        result = Math.max(left[ratings.length - 1], 1);
        int right = 1;
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right++;
            } else {
                right = 1;
            }
            result += Math.max(left[i], right);
        }
        return result;
    }

    /**
     * 最精妙的答案。一次循环
     *
     * 重点1：递增数列正常计算。水平都是1。
     * 重点2：递减数列也是从1开始递增，因为一直递增到数列的最低点的时候，把得到的值反过来，就是正确的值了。除非降序序列比升序序列长，那么顶点的数值就不对了。
     * 重点3：递减开始的时候，通过顶点的糖果数得到升序的长度。当降序长度==升序长度（由于降序从顶点下一个点开始算的，所有相等的时候，实际上已经是长了1），把递增的糖果数再加1，
     * 这样变相把后面降序比升序长的长度都累计起来，这个累计起来的糖果数加到顶点头上，顶点的糖果数也变正确了。
     * 6，7，6，5，4，3       原始数组
     * 1，2，1，3，4，5       循环后的
     * 1，2，1，2，3，4+3     实际上降序的5开始，每次计算都格外加多了1，总共格外加多了3，这个3就是降序比赠序长的长度，也是顶点应该增加的值。
     * 1，2+3 ，4，3，2，1    物归原位
     *
     *
     * 官方解释：
     * 前一个同学分得的糖果数量为 pre：
     * <p>
     * 如果当前同学比上一个同学评分高，说明我们就在最近的递增序列中，直接分配给该同学pre+1 个糖果即可。
     * <p>
     * 否则我们就在一个递减序列中，我们直接分配给当前同学一个糖果，并把该同学所在的递减序列中所有的同学都再多分配一个糖果，以保证糖果数量还是满足条件。
     * <p>
     * 我们无需显式地额外分配糖果，只需要记录当前的递减序列长度，即可知道需要额外分配的糖果数量。
     * <p>
     * 同时注意当当前的递减序列长度和上一个递增序列等长时，需要把最近的递增序列的最后一个同学也并进递减序列中。
     * <p>
     * 这样，我们只要记录当前递减序列的长度 dec，最近的递增序列的长度 inc 和前一个同学分得的糖果数量 pre 即可。
     *
     * @param ratings
     * @return
     */
    public int candy3(int[] ratings) {
        int result = 1;//第0位先默认为1
        int incLength = 1;//递增数为1，这是考虑到第0位的值。
        int decNum = 0;//递减的时候每次需要增加的糖果数
        int pre = 1;//上一位的数组。
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] < ratings[i - 1]) {
                decNum++;
                if (decNum == incLength) {
                    decNum++;
                }
                result += decNum;
                pre = 1;

            } else {
                pre = ratings[i] == ratings[i - 1] ? 1 : pre + 1;
                result += pre;

                incLength = pre;
                decNum = 0;
            }
        }
        return result;
    }
}
