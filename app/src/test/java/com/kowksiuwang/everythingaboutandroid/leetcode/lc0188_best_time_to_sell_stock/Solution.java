package com.kowksiuwang.everythingaboutandroid.leetcode.lc0188_best_time_to_sell_stock;

/**
 *
 * 给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：k = 2, prices = [2,4,1]
 * 输出：2
 * 解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
 * 示例 2：
 *
 * 输入：k = 2, prices = [3,2,6,5,0,3]
 * 输出：7
 * 解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
 *      随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。
 * Created by kwoksiuwang on 12/28/20!!!
 */
public class Solution {

    /**
     * 参考123题即可，思路完全相同，分步动态规划，买卖买卖这样逐个用动态规划求解
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit(int k, int[] prices) {
        if(k == 0 || prices == null || prices.length == 0){
            return 0;
        }
        int[] maxProfits = new int[k*2];
        int i = 0;
        while(i < k*2){
            maxProfits[i]= -prices[0];
            //System.out.println("profits["+i+"]=" + maxProfits[i]);
            maxProfits[i+1] = 0;
            //System.out.println("profits["+i+1+"]=" + maxProfits[i+1]);
            i+=2;
        }
        i = 1;
        int j = 0;
        while(i < prices.length){
            j = k*2-1;
            maxProfits[j] = Math.max(maxProfits[j], maxProfits[j-1] + prices[i]);
            //System.out.println("profits["+j+"]=" + maxProfits[j]);
            j--;
            while(j > 0){
                maxProfits[j] = Math.max(maxProfits[j] , maxProfits[j-1] - prices[i]);
                //System.out.println("profits["+j+"]=" + maxProfits[j]);
                j--;
                maxProfits[j] = Math.max(maxProfits[j], maxProfits[j-1] + prices[i]);
                //System.out.println("profits["+j+"]=" + maxProfits[j]);
                j--;
            }
            maxProfits[j] = Math.max(maxProfits[j], -prices[i]);
            //System.out.println("profits["+j+"]=" + maxProfits[j]);
            i++;
        }
        int result = 0;
        for(int profits : maxProfits){
            result = Math.max(result, profits);
        }
        return result;
    }
}
