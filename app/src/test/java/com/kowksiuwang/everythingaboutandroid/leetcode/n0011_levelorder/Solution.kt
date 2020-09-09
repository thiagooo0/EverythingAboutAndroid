package com.kowksiuwang.everythingaboutandroid.leetcode.n0011_levelorder

import com.kowksiuwang.everythingaboutandroid.leetcode.data.TreeNode
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

/**
 * 从上到下打印二叉树 III
 * 请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
 *
 *
 * 例如:
 * 给定二叉树: [3,9,20,null,null,15,7],
 *
 *      3
 *     / \
 *    9  20
 *      /  \
 *     15   7
 * 返回其层次遍历结果：
 *
 * [
 * [3],
 * [20,9],
 * [15,7]
 * ]
 *  
 *
 * 提示：
 * 节点总数 <= 1000
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

    fun levelOrder(root: TreeNode?): List<List<Int>> {
        if (root == null) {
            return listOf()
        }
        var isPositive = true
        val queue = LinkedList<TreeNode>()
        val mainList = ArrayList<LinkedList<Int>>()

        queue.offer(root)
        while (!queue.isEmpty()) {
            val size = queue.size
            val l = LinkedList<Int>()
            for (i in 0 until size) {
                val node = queue.poll()!!
                if (isPositive) {
                    l.addLast(node.`val`)
                } else {
                    l.addFirst(node.`val`)
                }
                node.left?.let {
                    queue.offer(it)
                }

                node.right?.let {
                    queue.offer(it)
                }

            }
            mainList.add(l)

            isPositive = !isPositive
        }
        return mainList
    }

    /**
     *
     */
    fun levelOrder2(root: TreeNode?): List<List<Int>> {
        val outer = ArrayList<List<Int>>()
        var flag = true
        var stack = LinkedList<TreeNode>()
        if (root != null) {
            stack.push(root)
        }
        while (!stack.isEmpty()) {
            val inner = ArrayList<Int>()
            val newStack = LinkedList<TreeNode>()
            for (i in stack.size - 1 downTo 0) {
                val node = stack.pop()
                inner.add(node.`val`)
                if (flag) {
                    if (node.left != null) {
                        newStack.push(node.left)
                    }
                    if (node.right != null) {
                        newStack.push(node.right)
                    }
                } else {
                    if (node.right != null) {
                        newStack.push(node.right)
                    }
                    if (node.left != null) {
                        newStack.push(node.left)
                    }
                }
            }
            stack = newStack
            outer.add(inner)
            flag = !flag
        }
        return outer
    }
}