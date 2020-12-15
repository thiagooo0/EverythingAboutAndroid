package com.kowksiuwang.everythingaboutandroid.leetcode.lc0659_is_possible;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by kwoksiuwang on 12/4/20!!!
 */
public class Solution {
    /**
     * 由于需要将数组分割成一个或多个由连续整数组成的子序列，因此只要知道子序列的最后一个数字和子序列的长度，就能确定子序列。
     * <p>
     * 当 x 在数组中时，如果存在一个子序列以 x-1 结尾，长度为 k，则可以将 x 加入该子序列中，得到长度为 k+1 的子序列。如果不存在以 x-1 结尾的子序列，则必须新建一个只包含 x 的子序列，长度为 1。
     * <p>
     * 当 x 在数组中时，如果存在多个子序列以 x-1 结尾，应该将 x 加入其中的哪一个子序列？由于题目要求每个子序列的长度至少为 3，显然应该让最短的子序列尽可能长，因此应该将 x 加入其中最短的子序列。
     * <p>
     * 基于上述分析，可以使用哈希表和最小堆进行实现。
     * <p>
     * 哈希表的键为子序列的最后一个数字，值为最小堆，用于存储所有的子序列长度，最小堆满足堆顶的元素是最小的，因此堆顶的元素即为最小的子序列长度。
     * <p>
     * 遍历数组，当遍历到元素 x 时，可以得到一个以 x 结尾的子序列。
     * <p>
     * 如果哈希表中存在以 x-1 结尾的子序列，则取出以 x-1 结尾的最小的子序列长度，将子序列长度加 1 之后作为以 x 结尾的子序列长度。此时，以 x-1 结尾的子序列减少了一个，以 x 结尾的子序列增加了一个。
     * <p>
     * 如果哈希表中不存在以 x-1 结尾的子序列，则新建一个长度为 1 的以 x 结尾的子序列。
     * <p>
     * 由于数组是有序的，因此当遍历到元素 x 时，数组中所有小于 x 的元素都已经被遍历过，不会出现当前元素比之前的元素小的情况。
     * <p>
     * 遍历结束之后，检查哈希表中存储的每个子序列的长度是否都不小于 3，即可判断是否可以完成分割。由于哈希表中的每条记录的值都是最小堆，堆顶元素为最小的子序列长度（以当前的键为最后一个数字的子序列），因此只要遍历每个最小堆的堆顶元素，即可判断每个子序列的长度是否都不小于 3。
     * <p>
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/split-array-into-consecutive-subsequences/solution/fen-ge-shu-zu-wei-lian-xu-zi-xu-lie-by-l-lbs5/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public boolean isPossible1(int[] nums) {
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<Integer, PriorityQueue<Integer>>();
        for (int x : nums) {
            if (!map.containsKey(x)) {
                map.put(x, new PriorityQueue<Integer>());
            }
            if (map.containsKey(x - 1)) {
                int prevLength = map.get(x - 1).poll();
                if (map.get(x - 1).isEmpty()) {
                    map.remove(x - 1);
                }
                map.get(x).offer(prevLength + 1);
            } else {
                map.get(x).offer(1);
            }
        }
        Set<Map.Entry<Integer, PriorityQueue<Integer>>> entrySet = map.entrySet();
        for (Map.Entry<Integer, PriorityQueue<Integer>> entry : entrySet) {
            PriorityQueue<Integer> queue = entry.getValue();
            if (queue.peek() < 3) {
                return false;
            }
        }
        return true;
    }

    public boolean isPossible2(int[] nums) {
        //贪心算法。两个hash表一个存储数组中每个数字出现的总数，另外一个存储分割后的以n为结尾的数序列的总数。
        HashMap<Integer, Integer> numSum = new HashMap<>();
        HashMap<Integer, Integer> arrayLength = new HashMap<>();
        for (int i : nums) {
            numSum.put(i, numSum.getOrDefault(i, 0) + 1);

        }
        for (int i : nums) {
            if (numSum.get(i) == 0) {
                continue;
            }
            if (arrayLength.containsKey(i - 1)) {
                if (arrayLength.get(i - 1) == 1) {
                    arrayLength.remove(i - 1);
                    arrayLength.put(i, arrayLength.getOrDefault(i,0)+1);
                } else {
                    arrayLength.put(i - 1, arrayLength.get(i - 1) - 1);
                    if (arrayLength.containsKey(i)) {
                        arrayLength.put(i, arrayLength.get(i) + 1);
                    } else {
                        arrayLength.put(i, 1);
                    }
                }
                numSum.put(i, numSum.get(i) - 1);
            } else {
                int i1 = numSum.getOrDefault(i + 1, 0);
                int i2 = numSum.getOrDefault(i + 2, 0);
                if (i1 > 0 && i2 > 0) {
                    numSum.put(i + 1, i1 - 1);
                    numSum.put(i + 2, i2 - 1);
                    numSum.put(i, numSum.get(i) - 1);
                    arrayLength.put(i + 2, arrayLength.getOrDefault(i + 2, 0) + 1);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void Test() {
        isPossible2(new int[]{1, 2, 2, 3, 3, 4, 4, 5});
        System.out.println();
    }
}
