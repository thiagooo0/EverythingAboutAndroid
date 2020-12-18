package com.kowksiuwang.everythingaboutandroid.leetcode.lc0714_best_time_to_sell_stock;

/**
 * 给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；非负整数 fee 代表了交易股票的手续费用。
 * <p>
 * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
 * <p>
 * 返回获得利润的最大值。
 * <p>
 * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
 * <p>
 * 示例 1:
 * <p>
 * 输入: prices = [1, 3, 2, 8, 4, 9], fee = 2
 * 输出: 8
 * 解释: 能够达到的最大利润:
 * 在此处买入 prices[0] = 1
 * 在此处卖出 prices[3] = 8
 * 在此处买入 prices[4] = 4
 * 在此处卖出 prices[5] = 9
 * 总利润: ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
 * 注意:
 * <p>
 * 0 < prices.length <= 50000.
 * 0 < prices[i] < 50000.
 * 0 <= fee < 50000.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * Created by kwoksiuwang on 12/17/20!!!
 */
public class Solution {
    /**
     * 耻辱的一天，动态规划
     *
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit(int[] prices, int fee) {
        if (prices.length == 0) {
            return 0;
        }
        int hasStock = -prices[0];
        int noStock = 0;
        for (int i = 1; i < prices.length; i++) {
            hasStock = Math.max(hasStock, noStock - prices[i]);
            noStock = Math.max(noStock, hasStock + prices[i] - fee);
        }
        return noStock;
    }

    /**
     * 贪心
     *
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit2(int[] prices, int fee) {
        int n = prices.length;
        int buy = prices[0] + fee;
        int profit = 0;
        for (int i = 1; i < n; ++i) {
            if (prices[i] + fee < buy) {//假设今天的价格比我买的底，那就改为在今天买。
                buy = prices[i] + fee;//交易费用前提
            } else if (prices[i] > buy) {
                profit += prices[i] - buy;//只要高于买入价格，那就马上成交。
                //这里直接把买入价格设置为当前价格。因为如果后面价格一路走低，那buy就会被更新，并且
                //把交易费用算上。如果价格走高，那通过prices[i] - buy就可以得到差价，并且把差价算入利润。
                buy = prices[i];

            }
        }
        return profit;
    }

}
