package com.kowksiuwang.everythingaboutandroid.leetcode.lc0104_maximun_depth_of_binary_tree

import com.kowksiuwang.everythingaboutandroid.leetcode.data.TreeNode

/**
 *  二叉树的最大深度
 *
 *  给定一个二叉树，找出其最大深度。
 *
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 返回它的最大深度 3 。
 * Created by GuoShaoHong on 2020/8/25.
 */
class MaxDepthTest {
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        return search(root, 0)
    }

    /**
     * 深度遍历，一直往下走，走到底再回头
     */
    fun search(root: TreeNode?, deep: Int): Int {
        if (root == null) {
            return deep
        }
        return Math.max(search(root!!.left, deep + 1), search(root!!.right, deep + 1))
    }
}