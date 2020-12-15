package com.kowksiuwang.everythingaboutandroid.leetcode.lc1024_video_stitching;

import java.util.Arrays;

/**
 * 你将会获得一系列视频片段，这些片段来自于一项持续时长为 T 秒的体育赛事。这些片段可能有所重叠，也可能长度不一。
 * <p>
 * 视频片段 clips[i] 都用区间进行表示：开始于 clips[i][0] 并于 clips[i][1] 结束。我们甚至可以对这些片段自由地再剪辑，例如片段 [0, 7] 可以剪切成 [0, 1] + [1, 3] + [3, 7] 三部分。
 * <p>
 * 我们需要将这些片段进行再剪辑，并将剪辑后的内容拼接成覆盖整个运动过程的片段（[0, T]）。返回所需片段的最小数目，如果无法完成该任务，则返回 -1 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], T = 10
 * 输出：3
 * 解释：
 * 我们选中 [0,2], [8,10], [1,9] 这三个片段。
 * 然后，按下面的方案重制比赛片段：
 * 将 [1,9] 再剪辑为 [1,2] + [2,8] + [8,9] 。
 * 现在我们手上有 [0,2] + [2,8] + [8,10]，而这些涵盖了整场比赛 [0, 10]。
 * 示例 2：
 * <p>
 * 输入：clips = [[0,1],[1,2]], T = 5
 * 输出：-1
 * 解释：
 * 我们无法只用 [0,1] 和 [1,2] 覆盖 [0,5] 的整个过程。
 * 示例 3：
 * <p>
 * 输入：clips = [[0,1],[6,8],[0,2],[5,6],[0,4],[0,3],[6,7],[1,3],[4,7],[1,4],[2,5],[2,6],[3,4],[4,5],[5,7],[6,9]], T = 9
 * 输出：3
 * 解释：
 * 我们选取片段 [0,4], [4,7] 和 [6,9] 。
 * 示例 4：
 * <p>
 * 输入：clips = [[0,4],[2,8]], T = 5
 * 输出：2
 * 解释：
 * 注意，你可能录制超过比赛结束时间的视频。
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= clips.length <= 100
 * 0 <= clips[i][0] <= clips[i][1] <= 100
 * 0 <= T <= 100
 * Created by kwoksiuwang on 2020/10/24!!!
 */
public class Solution {
    /**
     * 通过动态规划来解决这个问题。
     * 思路是通过一个数组dp[i]，记录(0，i]的视频所需的最短片段数。然后在i从1开始增长到T的过程中，不断遍历clips[a][b]。
     * 对于a<i<=b的片段，可以得到dp[i]和dp[a]+1的最小值，赋值给dp[i]。
     * 最后通过dp[T]得到片段数。
     * <p>
     * 1，为啥是(0，i]和 a<i<=b，为何前面不包括上呢。
     * 因为T=0的时候，返回值就是0，不需要任何片段。而如果T=a时，如果有一个片段[a,b]，不应该把这个此片段加进去的，
     * 因为a这个时间点已经被其他片段填充了，再加进去会导致结果多了1。
     * <p>
     * 2，dp[0]的初始值为0，而其他的赋值为Integer.MAX_VALUE - 1
     * 我们解题过程中需要找出小的，所以填充极大值Integer.MAX_VALUE - 1。dp[0]=0是固定的，而且如果写Integer.MAX_VALUE - 1
     * 会导致后面计算错误。
     */
    public int videoStitching1(int[][] clips, int T) {
        int[] dp = new int[T + 1];
        //
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        dp[0] = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < clips.length; j++) {
                if (clips[j][0] < i && i <= clips[j][1]) {
                    dp[i] = Math.min(dp[i], dp[clips[j][0]] + 1);
                }
            }
        }
        return dp[T] == Integer.MAX_VALUE - 1 ? -1 : dp[T];
    }

    /**
     * 贪心算法解决问题。
     */
    public int videoStitching2(int[][] clips, int T) {
        //创建一个数组m[i]，长度为T。m[i]表示可以到达的最远的位置。
        int[] m = new int[T + 1];
        for (int i = 0; i < clips.length; i++) {
            if (clips[i][0] < T && m[clips[i][0]] < clips[i][1]) {
                m[clips[i][0]] = clips[i][1];
            }
        }

        //片段数。
        int count = 0;
        //现在可以连续播放到的最远端。
        int last = 0;
        //代表正在使用的影片片段的结尾。
        int pre = 0;
        //遍历m数组，可以想象为影片播放的过程。每次i==pre的时候，就需要切换片段了，那下一段片段的结尾就是last了。
        for (int i = 0; i < T; i++) {
            last = Math.max(m[i], last);
            if (i == last) {
                return -1;
            }
            if (i == pre) {
                pre = last;
                count++;
            }
        }
        return count;
    }
}
