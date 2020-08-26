package com.kowksiuwang.everythingaboutandroid.leetcode.n0008_findk

import com.kowksiuwang.everythingaboutandroid.leetcode.data.TreeNode

/**
 *
 * 给定一棵二叉搜索树，请找出其中第 k 大的节点。
 *
 * 示例 1:
 *
 *
 * 输入: root = [3,1,4,null,2], k = 1
 * 3
 * / \
 * 1   4
 * \
 *    2
 * 输出: 4
 * 示例 2:
 *
 *
 * 输入: root = [5,3,6,2,4,null,null,1], k = 3
 * 5
 * / \
 * 3   6
 * / \
 * 2   4
 * /
 * 1
 * 输出: 4
 * 限制：
 *
 * 1 ≤ k ≤ 二叉搜索树元素个数
 *
 * Created by GuoShaoHong on 2020/8/25.
 */
class FindKTest {
    var currentK = 0
    var result = 0

    /**
     * 右中左地遍历
     */
    fun kthLargest(root: TreeNode?, k: Int): Int {
        currentK = k
        result = 0
        search(root)
        return result
    }

    fun search(root: TreeNode?) {
        //当到底了就返回
        if (root == null) {
            return
        }

        //先搜索右边，如果currentK==0，说明找到了，那就啥也不做直接返回。
        search(root.right)
        if (currentK == 0) {
            return
        }

        //到root本身。先把currentK--，表明把自己也算进去。如果currentK==0，说明自己就是被选中的人了。
        currentK--
        if (currentK == 0) {
            result = root.`val`
            return
        }

        //再找左边，这次不需要再判断currentK了,因为给result不需要在这里做，至于返回，本来就要返回了。
        search(root.left)
    }
}