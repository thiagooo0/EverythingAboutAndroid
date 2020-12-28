package com.kowksiuwang.everythingaboutandroid.leetcode.lc0123_max_profit;

import org.junit.Assert;
import org.junit.Test;

/**
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 * <p>
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
 * <p>
 * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [3,3,5,0,0,3,1,4]
 * 输出: 6
 * 解释: 在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
 *      随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
 * 示例 2:
 * <p>
 * 输入: [1,2,3,4,5]
 * 输出: 4
 * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。  
 *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。  
 *      因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 * 示例 3:
 * <p>
 * 输入: [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这个情况下, 没有交易完成, 所以最大利润为 0。
 * <p>
 * Created by kwoksiuwang on 12/28/20!!!
 */
public class Solution {
    @Test
    public void Test() {
        Assert.assertEquals(maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4}), 6);
    }

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        //最大利润从下面列举5种利润情况种选，每种都可以通过上一种前一天的利润做动态规划，求出自己今天的最大利润。
        //什么都不操作的最大利润，永恒是0，所有忽略了。
        //int doNoting = 0;
        //只买一次时的最大利润
        int buyOnce = -prices[0];
        //买+卖之后的最大利润
        int buyOnceSellOnce = 0;
        //买+卖+买的最大利润
        int buyTwiceSellOnce = -prices[0];
        //买+卖+买+卖的最大利润
        int buyTwiceSellTwice = 0;

        int i = 1;
        while (i < prices.length) {
            //这里反着来算，是因为每种利润都给予上一种利润前一天都值，如果顺着求，会把前一天都值抹掉，让下面的利润无法计算。

            //两次买卖，要么保持上一天的利润，要么把昨天买入的卖掉获取利润
            buyTwiceSellTwice = Math.max(buyTwiceSellTwice, buyTwiceSellOnce + prices[i]);
            //买卖买，要么保持上一天的利润，要么用上一天为止卖出得到的最大利润（不一定就是在上一天卖的）再卖一份现在的股票
            buyTwiceSellOnce = Math.max(buyTwiceSellOnce, buyOnceSellOnce - prices[i]);
            //一次买入+卖出，要么保持上一天的利润，要么把昨天买入的卖了，获取利润
            buyOnceSellOnce = Math.max(buyOnceSellOnce, buyOnce + prices[i]);
            //一次买入，要么保持上一天的买入价格，要么改为买入今天的
            buyOnce = Math.max(buyOnce, -prices[i]);
            i++;
        }

        return Math.max(0, Math.max(Math.max(buyOnce, buyOnceSellOnce), Math.max(buyTwiceSellOnce, buyTwiceSellTwice)));
    }


    public int maxProfit1(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int n = prices.length;
        //定义三维数组，第i天、交易了多少次、当前的买卖状态（0：卖出，1：买入）
        //这里定义的数组总数感觉有问题，dp[i][0][0]跟dp[i][2][1]是永远不用用到的，实际上应该只是存在两对交易对。
        int[][][] dp = new int[n][3][2];
        //初始化第一天，这里的dp[0][2][1]可以不用管，后面也不会用到
        dp[0][0][0] = 0;
        dp[0][0][1] = -prices[0];
        dp[0][1][0] = 0;
        dp[0][1][1] = -prices[0];
        dp[0][2][0] = 0;
        dp[0][2][1] = -prices[0];
        for (int i = 1; i < n; ++i) {
            //第0次卖出，永远==0；
            dp[i][0][0] = dp[i - 1][0][0];
            //第0次买入，要么保持之前的买入，要么是改为买入今天股票
            dp[i][0][1] = Math.max(dp[i - 1][0][1], dp[i - 1][0][0] - prices[i]);
            //第1次卖出，要么不操作保持之前获取到的利润，要么改为在今天卖出昨天买入的股票并且获取新的利润。
            dp[i][1][0] = Math.max(dp[i - 1][1][0], dp[i - 1][0][1] + prices[i]);
            //第1次买入，要么不操作保持之前的利润，要么在昨天第一次卖出的利润上面扣去今天买股票的价格获取新的利润。（这里只要第一次卖出的地方有利润，就会导致后者触发，否则只会一直选前者。）
            dp[i][1][1] = Math.max(dp[i - 1][1][1], dp[i - 1][1][0] - prices[i]);
            //第2次卖出，要么不操作保持，要么把前一天买入的买了获取新的利润。
            dp[i][2][0] = Math.max(dp[i - 1][2][0], dp[i - 1][1][1] + prices[i]);
        }
        //返回最大值
        int a = Math.max(dp[n - 1][0][0], dp[n - 1][0][1]);
        int b = Math.max(dp[n - 1][1][0], dp[n - 1][1][1]);
        return Math.max(Math.max(a, b), dp[n - 1][2][0]);
    }

    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int n = prices.length;
        //定义二维数组，5种状态
        int[][] dp = new int[n][5];
        //初始化第一天的状态
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[0][2] = 0;
        dp[0][3] = -prices[0];
        dp[0][4] = 0;
        for (int i = 1; i < n; ++i) {
            //dp[i][0]相当于初始状态，它只能从初始状态转换来
            dp[i][0] = dp[i - 1][0];
            //处理第一次买入、第一次卖出
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][1] + prices[i]);
            //处理第二次买入、第二次卖出
            dp[i][3] = Math.max(dp[i - 1][3], dp[i - 1][2] - prices[i]);
            dp[i][4] = Math.max(dp[i - 1][4], dp[i - 1][3] + prices[i]);
        }
        //返回最大值
        return Math.max(Math.max(Math.max(dp[n - 1][0], dp[n - 1][1]), Math.max(dp[n - 1][2], dp[n - 1][3])), dp[n - 1][4]);
    }

    public int maxProfit3(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int n = prices.length;
        //定义5种状态，并初始化第一天的状态
        int dp0 = 0;
        int dp1 = -prices[0];
        int dp2 = 0;
        int dp3 = -prices[0];
        int dp4 = 0;
        for (int i = 1; i < n; ++i) {
            //这里省略dp0，因为dp0每次都是从上一个dp0来的相当于每次都是0
            //处理第一次买入、第一次卖出
            dp1 = Math.max(dp1, dp0 - prices[i]);
            dp2 = Math.max(dp2, dp1 + prices[i]);
            //处理第二次买入、第二次卖出
            dp3 = Math.max(dp3, dp2 - prices[i]);
            dp4 = Math.max(dp4, dp3 + prices[i]);
        }
        //返回最大值
        return Math.max(0, Math.max(Math.max(dp1, dp2), Math.max(dp3, dp4)));
    }
}
