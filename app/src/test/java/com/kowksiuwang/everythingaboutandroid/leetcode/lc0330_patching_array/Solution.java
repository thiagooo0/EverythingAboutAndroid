package com.kowksiuwang.everythingaboutandroid.leetcode.lc0330_patching_array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 给定一个已排序的正整数数组 nums，和一个正整数 n 。从 [1, n] 区间内选取任意个数字补充到 nums 中，使得 [1, n] 区间内的任何数字都可以用 nums 中某几个数字的和来表示。请输出满足上述要求的最少需要补充的数字个数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [1,3], n = 6
 * 输出: 1
 * 解释:
 * 根据 nums 里现有的组合 [1], [3], [1,3]，可以得出 1, 3, 4。
 * 现在如果我们将 2 添加到 nums 中， 组合变为: [1], [2], [3], [1,3], [2,3], [1,2,3]。
 * 其和可以表示数字 1, 2, 3, 4, 5, 6，能够覆盖 [1, 6] 区间里所有的数。
 * 所以我们最少需要添加一个数字。
 * 示例 2:
 * <p>
 * 输入: nums = [1,5,10], n = 20
 * 输出: 2
 * 解释: 我们需要添加 [2, 4]。
 * 示例 3:
 * <p>
 * 输入: nums = [1,2,2], n = 5
 * 输出: 0
 * Created by kwoksiuwang on 12/29/20!!!
 */
public class Solution {
    @Test
    public void test() {
        minPatches(new int[]{1, 3}, 6);
        minPatches(new int[]{1, 5, 10}, 20);
        minPatches(new int[]{1, 2, 2}, 5);
        minPatches(new int[]{1, 2, 31, 33}, 2147483645);
    }

    /**
     * 难点1：如何知道现在的nums可以覆盖哪些数
     *
     * 这里有个大的bug，2147483645就会把数组搞爆。。。
     */
    public int minPatches(int[] nums, int n) {
        boolean[] coverNumbers = new boolean[n];
        for (int i = 1; i <= nums.length; i++) {
            findNum(nums, i, 0, coverNumbers);
            //printCoverNum(coverNumbers);
        }
        int p = 0;
        int addNumber = 0;
        while (p < coverNumbers.length) {
            if (coverNumbers[p]) {
                p++;
                continue;
            } else {
                //从后往前加
                for (int i = coverNumbers.length - 1; i >= 0; i--) {
                    if (coverNumbers[i] && ((i + p + 1) < coverNumbers.length)  && ((i + p + 1) >= 0)) {
                        try {
                            coverNumbers[i + p + 1] = true;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("ArrayIndexOutOfBoundsException" + "p:" + p + ", i=" + i + ";" + (i + p + 1));
                            return 2;
                        }
                    }
                }
                coverNumbers[p] = true;
//                printCoverNum(coverNumbers);
                //printCoverNum(coverNumbers);
                System.out.println("添加了" + p);
                p++;
                addNumber++;
            }
        }
        System.out.println("-----总共要添加" + addNumber + "个数");
        return addNumber;
    }

    private void findNum(int[] nums, int remainTime, int result, boolean[] coverNumber) {
        if (remainTime == 0) {
            if (result <= coverNumber.length) {
                coverNumber[result - 1] = true;
            }
            return;
        }
        int temp;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != -1) {
                temp = nums[i];
                nums[i] = -1;
                findNum(nums, remainTime - 1, result + temp, coverNumber);
                nums[i] = temp;
            }
        }
    }

    private void printCoverNum(boolean[] coverNumbers) {
        System.out.print("[");
        for (int j = 0; j < coverNumbers.length; j++) {
            if (coverNumbers[j]) {
                System.out.print((j + 1) + ",");
            } else {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }
}
