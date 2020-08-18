package com.kowksiuwang.everythingaboutandroid.leetcode.lc0101_checkbinarytreesymmetric

import com.kowksiuwang.everythingaboutandroid.leetcode.data.TreeNode

/**
 * 给定一个二叉树，检查它是否是镜像对称的。
 *
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 *  
 *
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 *
 *   1
 *  / \
 * 2   2
 * \   \
 *  3    3
 *
 * Created by GuoShaoHong on 2020/8/18.
 */
class CheckBinaryTreeSymmetric {
    /**
     * 总体思路还是递归。每次都把一棵树分成两个树，然后左右相比。
     */
    fun isSymmetric(root: TreeNode?): Boolean {
        if (root == null) {
            return true
        }
        return isMirror(root.left, root.right)
    }

    fun isMirror(leftNode: TreeNode?, rightNode: TreeNode?): Boolean {
        if ((leftNode == null && rightNode != null) || (leftNode != null && rightNode == null)) {
            return false
        }
        if (leftNode == null && rightNode == null) {
            return true
        }
        return (leftNode!!.`val` == rightNode!!.`val`)
                && isMirror(leftNode.left, rightNode.right)
                && isMirror(leftNode.right, rightNode.left)
    }
}
