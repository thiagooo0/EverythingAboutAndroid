package com.kowksiuwang.everythingaboutandroid.leetcode.lc0189_rotate_array;

import org.junit.Assert;
import org.junit.Test;

/**
 * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [1,2,3,4,5,6,7] 和 k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右旋转 1 步: [7,1,2,3,4,5,6]
 * 向右旋转 2 步: [6,7,1,2,3,4,5]
 * 向右旋转 3 步: [5,6,7,1,2,3,4]
 * 示例 2:
 * <p>
 * 输入: [-1,-100,3,99] 和 k = 2
 * 输出: [3,99,-1,-100]
 * 解释:
 * 向右旋转 1 步: [99,-1,-100,3]
 * 向右旋转 2 步: [3,99,-1,-100]
 * 说明:
 * <p>
 * 尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
 * 要求使用空间复杂度为 O(1) 的 原地 算法。
 * Created by kwoksiuwang on 1/8/21!!!
 */
public class Solution {
    @Test
    public void Text() {
        Assert.assertArrayEquals(rotateTest(new int[]{1, 2, 3}, 2), new int[]{2, 3, 1});
        Assert.assertArrayEquals(rotateTest(new int[]{1, 2, 3, 4, 5, 6, 7}, 3), new int[]{5, 6, 7, 1, 2, 3, 4});
        Assert.assertArrayEquals(rotateTest(new int[]{-1, -100, 3, 99}, 2), new int[]{3, 99, -1, -100});
    }

    private int[] rotateTest(int[] nums, int k) {
        rotate(nums, k);
        return nums;
    }

    /***
     * 未验证
     * @param nums
     * @param k
     */
    public void rotate2(int[] nums, int k) {
        k = k % nums.length;
        if (k == 0) {
            return;
        }
        int i = 0;
        int change = 0;
        while (change < k) {
            int j = i + k;
            while (j < nums.length) {
                exchange(nums, i, j);
                j = i + k;
            }
            i++;
            change++;
        }
    }

    private void exchange(int[] nums, int i, int j) {
        nums[i] = nums[i] - nums[j];
        nums[j] = nums[i] + nums[j];
        nums[i] = nums[j] - nums[i];
    }

    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        if (k == 0) {
            return;
        }
        if (k > nums.length / 2) {
            k = nums.length - k;
            while (k > 0) {
                moveLeft(nums);
                k--;
            }
        } else {
            while (k > 0) {
                moveRight(nums);
                k--;
            }
        }
    }

    public void moveRight(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            nums[i] = nums[i] - nums[0];
            nums[0] = nums[i] + nums[0];
            nums[i] = nums[0] - nums[i];
        }
    }

    public void moveLeft(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            nums[i] = nums[i] - nums[i + 1];
            nums[i + 1] = nums[i] + nums[i + 1];
            nums[i] = nums[i + 1] - nums[i];
        }
    }
}
