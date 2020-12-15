package com.kowksiuwang.everythingaboutandroid.leetcode.lc0016_closest_three_sum;

import java.util.Arrays;

/**
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 *
 *  
 *
 * 示例：
 *
 * 输入：nums = [-1,2,1,-4], target = 1
 * 输出：2
 * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
 *  
 *
 * 提示：
 *
 * 3 <= nums.length <= 10^3
 * -10^3 <= nums[i] <= 10^3
 * -10^4 <= target <= 10^4
 * Created by kwoksiuwang on 2020/10/29!!!
 */
public class Solution {
    public int threeSumClosest(int[] nums, int target) {
        if(nums.length < 3){
            return 0;
        }
        if(nums.length==3){
            return nums[0]+nums[1]+nums[2];
        }
        Arrays.sort(nums);
        int sum = nums[0]+nums[1]+nums[2];
        int target2 = 0;
        int j = 0;
        int k = 0;
        int tempTarget = 0;
        for(int i = 0; i < nums.length; i++){
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            target2 = target - nums[i];
            j = i + 1;
            k = nums.length-1;
            while(k > j){
                tempTarget = nums[j] + nums[k];
                if(Math.abs(target - sum) > Math.abs(target - (tempTarget + nums[i]))){
                    sum = tempTarget + nums[i];
                }
                if(tempTarget == target2){
                    return target;
                }else if(tempTarget > target2){
                    k--;
                }else{
                    j++;
                }
            }
        }
        return sum;
    }
}
