package com.kowksiuwang.everythingaboutandroid.leetcode.lc0057_inside;

/**
 * 给出一个无重叠的 ，按照区间起始端点排序的区间列表。
 *
 * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
 * 输出：[[1,5],[6,9]]
 * 示例 2：
 *
 * 输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * 输出：[[1,2],[3,10],[12,16]]
 * 解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
 * Created by kwoksiuwang on 2020/11/4!!!
 */
public class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        //指针指向，在区间之前，在区间之中，在区间之后
        int left = 0;
        int leftStatus = 0;
        int right = 0;
        int rightStatus = 0;
        return new int[0][0];
    }
}
