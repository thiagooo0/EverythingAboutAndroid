package com.kowksiuwang.everythingaboutandroid.leetcode.lc0321_create_max_num;
/**
 * nums1 = [3, 4, 6, 5]
 * nums2 = [9, 1, 2, 5, 8, 3]
 * 答案：[9, 8, 6, 5, 3]
 * 首先，对于每个数组，求出1～k最大的数。
 * 6
 * 6，5
 * 4，6，5
 * 3，4，5，6
 * <p>
 * 9
 * 9，8
 * 9，8，3
 * 9，5，8，3
 * 9，2，5，8，3
 * 这里从大到小排列会好一点啊。
 * <p>
 * 然后两两配对，求出最佳的解。
 */

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by kwoksiuwang on 12/2/20!!!
 */
public class Solution {
    @Test
    public void test() {
        Assert.assertArrayEquals(maxNumber(new int[]{2, 1, 7, 8, 0, 1, 7, 3, 5, 8, 9, 0, 0, 7, 0, 2, 2, 7, 3, 5, 5}, new int[]{2, 6, 2, 0, 1, 0, 5, 4, 5, 5, 3, 3, 3, 4}, 35), new int[]{2, 6, 2, 2, 1, 7, 8, 0, 1, 7, 3, 5, 8, 9, 0, 1, 0, 5, 4, 5, 5, 3, 3, 3, 4, 0, 0, 7, 0, 2, 2, 7, 3, 5, 5});
        Assert.assertArrayEquals(maxNumber(new int[]{1,2}, new int[]{}, 2), new int[]{1,2});
        Assert.assertArrayEquals(maxNumber(new int[]{3, 4, 6, 5}, new int[]{9, 1, 2, 5, 8, 3}, 5), new int[]{9, 8, 6, 5, 3});
        Assert.assertArrayEquals(maxNumber(new int[]{6, 7}, new int[]{6, 0, 4}, 5), new int[]{6, 7, 6, 0, 4});
        Assert.assertArrayEquals(maxNumber(new int[]{6, 7, 0, 0, 0}, new int[]{6, 0, 4, 7, 8, 9}, 5), new int[]{9, 7, 0, 0, 0});
        Assert.assertArrayEquals(maxNumber(new int[]{3, 9}, new int[]{8, 9}, 3), new int[]{9, 8, 9});
        Assert.assertArrayEquals(maxNumber(new int[]{3, 9}, new int[]{8, 9}, 0), new int[]{});
        Assert.assertArrayEquals(maxNumber(new int[]{3, 9, 0, 0, 0}, new int[]{8, 0, 0, 0, 9}, 3), new int[]{9, 9, 0});

    }
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        int[] maxSubsequence = new int[k];
        int start = Math.max(0, k - n), end = Math.min(k, m);
        for (int i = start; i <= end; i++) {
            int[] subsequence1 = maxSubsequence(nums1, i);
            int[] subsequence2 = maxSubsequence(nums2, k - i);
            int[] curMaxSubsequence = merge(subsequence1, subsequence2);
            if (compare(curMaxSubsequence, 0, maxSubsequence, 0) > 0) {
                System.arraycopy(curMaxSubsequence, 0, maxSubsequence, 0, k);
            }
        }
        return maxSubsequence;
    }

    public int[] maxSubsequence(int[] nums, int k) {
        int length = nums.length;
        int[] stack = new int[k];
        int top = -1;
        int remain = length - k;
        for (int i = 0; i < length; i++) {
            int num = nums[i];
            while (top >= 0 && stack[top] < num && remain > 0) {
                top--;
                remain--;
            }
            if (top < k - 1) {
                stack[++top] = num;
            } else {
                remain--;
            }
        }
        return stack;
    }

    public int[] merge(int[] subsequence1, int[] subsequence2) {
        int x = subsequence1.length, y = subsequence2.length;
        if (x == 0) {
            return subsequence2;
        }
        if (y == 0) {
            return subsequence1;
        }
        int mergeLength = x + y;
        int[] merged = new int[mergeLength];
        int index1 = 0, index2 = 0;
        for (int i = 0; i < mergeLength; i++) {
            if (compare(subsequence1, index1, subsequence2, index2) > 0) {
                merged[i] = subsequence1[index1++];
            } else {
                merged[i] = subsequence2[index2++];
            }
        }
        return merged;
    }

    public int compare(int[] subsequence1, int index1, int[] subsequence2, int index2) {
        int x = subsequence1.length, y = subsequence2.length;
        while (index1 < x && index2 < y) {
            int difference = subsequence1[index1] - subsequence2[index2];
            if (difference != 0) {
                return difference;
            }
            index1++;
            index2++;
        }
        return (x - index1) - (y - index2);
    }

    public int[] maxNumber1(int[] nums1, int[] nums2, int k) {
        if (k == 0) {
            return new int[0];
        }
        int[] result = new int[k];
        int[][] nums1Result = new int[k + 1][k];
        int[][] nums2Result = new int[k + 1][k];
        findSingleNumsResult(nums1, nums1Result, k);
        findSingleNumsResult(nums2, nums2Result, k);

        int len = k;
        while (len >= 0) {
            if (nums1.length >= k - len && nums2.length >= len) {
                int[] temp1 = nums1Result[len];
                int[] temp2 = nums2Result[k - len];
                int i = 0, j = 0;
                while (i + j < k) {
                    int max = Math.max(temp1[i], temp2[j]);
                    if (max < result[i + j]) {
                        break;
                    } else if (max > result[i + j]) {
                        //全部复制过去
                        while (i + j < k) {
                            if (temp1[i] > temp2[j]) {
                                result[i + j] = temp1[i];
                                i++;
                            } else if (temp1[i] < temp2[j]) {
                                result[i + j] = temp2[j];
                                j++;
                            } else if (j == k - 1) {//让后面有东西的先出来。
                                result[i + j] = temp1[i];
                                i++;
                            } else if (i == k - 1) {
                                result[i + j] = temp2[j];
                                j++;
                            } else {
                                boolean isLeft = compare(temp1, temp2, i, j, k);
                                if (isLeft) {
                                    result[i + j] = temp1[i];
                                    i++;
                                } else {
                                    result[i + j] = temp2[j];
                                    j++;
                                }
                            }
                        }
                        break;
                    } else {
                        if (temp1[i] > temp2[j]) {
                            i++;
                        } else {
                            j++;
                        }
                    }
                }
            }
            len--;
        }
        System.out.println("" + Arrays.toString(result));
        return result;
    }

    /**
     * @return true 第一个
     */
    private boolean compare(int[] nums1, int[] nums2, int i, int j, int k) {
        if (nums1[i] > nums2[j]) {
            return true;
        } else if (nums1[i] < nums2[j]) {
            return false;
        } else if (j == k - 1) {//让后面有东西的先出来。
            return true;
        } else if (i == k - 1) {
            return false;
        } else {
            return compare(nums1, nums2, i + 1, j + 1, k);
        }
    }

    private void findSingleNumsResult(int[] nums, int[][] result, int k) {
        for (int i = 0; i < k; i++) {
            int len = k - i;
            result[i][0] = -1;
            for (int j = 0; j < nums.length; j++) {
                int l = 0;
                //如果后面的位数不够，必须要往后退。如果位数够，并且大于，就写。如果-1，必须写
                while (l < len) {
                    if (result[i][l] == -1) {
                        result[i][l] = nums[j];
                        if (l + 1 < k) {
                            result[i][l + 1] = -1;
                        }
                        break;
                    }
                    if (result[i][l] < nums[j] && l + (nums.length - j) >= len) {
                        result[i][l] = nums[j];
                        if (l + 1 < k) {
                            result[i][l + 1] = -1;
                        }
                        break;
                    }
                    l++;
                }

            }
        }
    }
}
