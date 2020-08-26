package com.kowksiuwang.everythingaboutandroid.leetcode.lc0236_lowest_common_ancestor

import com.kowksiuwang.everythingaboutandroid.leetcode.data.TreeNode
import java.util.*

/**
 * Created by GuoShaoHong on 2020/8/26.
 */
internal class Solution {
    /**
     * 也是使用递归。判断条件只能从递归的返回值判断，如果返回值不为null，则代表找到了。
     * 假如左右子树的递归都不为空，那就代表了是公共祖先。
     * 如果只是一边的返回值为空，可能是下面有公共子树，或者找到了其中一颗。都做透传就行了。
     */
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null) {
            return null
        }
        if (root == p || root == q) {
            return root
        }
        val result1 = lowestCommonAncestor(root.left, p, q)
        val result2 = lowestCommonAncestor(root.right, p, q)
        if (result1 != null && result2 != null) {
            return root
        } else if (result1 != null) {
            return result1
        } else if (result2 != null) {
            return result2
        }
        return null
    }

}