package com.kowksiuwang.everythingaboutandroid.leetcode.lc0102_levelorder

import com.kowksiuwang.everythingaboutandroid.leetcode.data.TreeNode
import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

/**
 * 二叉树的层序遍历
 *
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 *
 * 示例：
 * 二叉树：[3,9,20,null,null,15,7],
 *
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 返回其层次遍历结果：
 *
 * [
 * [3],
 * [9,20],
 * [15,7]
 * ]
 *
 * Created by GuoShaoHong on 2020/8/27.
 */
class Solution {
    @Test
    fun test() {
        var root = TreeNode(3)

        val l1 = TreeNode(9)
        val r1 = TreeNode(20)

        val l2 = TreeNode(15)
        val r2 = TreeNode(7)

        root.left = l1
        root.right = r1

        l1.left = l2
        r1.right = r2
        levelOrder(root)
    }

    /**
     * 深度遍历。
     * 两个队列。
     * 一个用于存现在的层，一个用于存下一层，当现在层的队列未空，就把两个队列对调。直到两个队列都为空。
     *
     * 这题也可以用一个队列完成。每次while先得到队列中数量n，然后循环n次，从队列中读取节点，并且把子层节点加入队列。
     * 由于有n作为限制，不过读到下一层的队列。
     */
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        if (root == null) {
            return listOf()
        }
        var queueUsing = LinkedList<TreeNode>()
        var queueSave = LinkedList<TreeNode>()
        var list = ArrayList<ArrayList<Int>>()

        var queueTemp: LinkedList<TreeNode>
        var nodeTemp: TreeNode
        var listArray = ArrayList<Int>()

        queueUsing.offer(root)
        while (!queueUsing.isEmpty() || !queueSave.isEmpty()) {
            nodeTemp = queueUsing.poll()
            listArray.add(nodeTemp.`val`)

            nodeTemp.left?.let {
                queueSave.offer(it)
            }

            nodeTemp.right?.let {
                queueSave.offer(it)
            }

            if (queueUsing.isEmpty()) {
                queueTemp = queueUsing
                queueUsing = queueSave
                queueSave = queueTemp
                list.add(listArray)
                listArray = ArrayList()
            }
        }

        return list
    }
}