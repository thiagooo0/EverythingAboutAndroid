package com.kowksiuwang.everythingaboutandroid.leetcode.lc0746_min_cost_climbing_starts;

import java.util.HashMap;

/**
 * Created by kwoksiuwang on 12/21/20!!!
 */
public class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int firstCost = cost[0];
        int secondCost = cost[1];
        int i = 2;
        while (i < cost.length) {
            int temp = Math.min(firstCost + cost[i], secondCost + cost[i]);
            firstCost = secondCost;
            secondCost = temp;
            i++;
        }
        return Math.min(firstCost, secondCost);
    }

    /**
     * 来自og的精妙代码，递归，遍历所有情况，并且找出最小的
     * @param cost
     * @return
     */
    public int minCostClimbingStairs2(int[] cost) {
        int i = 2;
        while (i < cost.length) {
            cost[i] = Math.min(cost[i - 2], cost[i - 1]) + cost[i];
            i++;
        }
        return Math.min(cost[i - 2], cost[i - 1]);
    }

    public int minCostClimbingStairsFromOG(int[] cost) {

        HashMap<Integer, Integer> map = new HashMap<>();
        int min = Math.min(minCostClimbingStairs(cost, 0, map), minCostClimbingStairs(cost, 1, map));
        return min;
    }

    public int minCostClimbingStairs(int[] cost, int index, HashMap<Integer, Integer> map) {

        if (index >= cost.length) {
            return 0;
        }
        Integer num = map.get(index);
        if (null != num) {
            return num;
        }
        int min = cost[index] + Math.min(minCostClimbingStairs(cost, index + 1, map), minCostClimbingStairs(cost, index + 2, map));
        map.put(index, min);
        return min;
    }
}
