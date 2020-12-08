package com.kowksiuwang.everythingaboutandroid.leetcode.lc0034_find_first_and_end;

/**
 * Created by kwoksiuwang on 12/1/20!!!
 */
public class Solution {
    public int[] searchRange(int[] nums, int target) {
        int i = 0, j = nums.length - 1, current = 0;
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;
        int center = findCenter(nums, i, j, target);
        if (center != -1) {
            i = center;
            j = center;
            while (i > 0 && nums[i] == target) {
                i--;
            }
            while (j < nums.length - 1 && nums[j] == target) {
                j++;
            }
            result[0] = i;
            result[1] = j;
        }
        return result;
    }

    private int findCenter(int[] nums, int i, int j, int target) {
        if (nums[i] == target) {
            return i;
        } else if (nums[i] > target) {
            return -1;
        }
        if (nums[j] == target) {
            return j;
        }
        if (nums[j] < target) {
            return -1;
        }
        if (i >= j) {
            return -1;
        }
        int center = (i + j) / 2;
        if (nums[center] > target)
            return findCenter(nums, i, center, target);
        else if (nums[center] < target)
            return findCenter(nums, center, j, target);
        else
            return center;
    }

    public static int[] searchRange1(int[] nums, int target) {

        int left = 0;
        int right = nums.length - 1;
        int leftAns = -1;
        int rightAns = -1;
        if (nums.length == 1 && nums[0] == target) {
            return new int[]{0, 0};
        }
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {

                leftAns = mid;
                rightAns = mid;
                while (leftAns - 1 >= 0 && nums[leftAns - 1] == target) {
                    leftAns--;
                }
                while (rightAns + 1 < nums.length && nums[rightAns + 1] == target) {
                    rightAns++;
                }
                break;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return new int[]{leftAns, rightAns};
    }
}
