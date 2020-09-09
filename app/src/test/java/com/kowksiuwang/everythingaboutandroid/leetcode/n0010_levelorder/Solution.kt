package com.kowksiuwang.everythingaboutandroid.leetcode.n0010_levelorder

import com.kowksiuwang.everythingaboutandroid.leetcode.data.TreeNode
import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

/**
 * 从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。
 *
 * 例如:
 * 给定二叉树: [3,9,20,null,null,15,7],
 *
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 返回：
 *
 * [3,9,20,15,7]
 *  
 *
 * 提示：
 *
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

        Assert.assertEquals(levelOrder(root).contentEquals(intArrayOf(3, 9, 20, 15, 7)), true)
    }

    /**
     * 广度遍历。
     * 使用了队列的特性。
     * 先把根节点放进队列。然后每次从对列中取出一个节点，并且把该节点的左右节点放入队列中。直到队列清空，则表明遍历完了。
     */
    fun levelOrder(root: TreeNode?): IntArray {
        val queue = LinkedList<TreeNode>()
        if (root == null) {
            return intArrayOf()
        }
        val array = ArrayList<Int>()
        queue.push(root)
        var temp: TreeNode
        while (queue.size != 0) {
            temp = queue.poll()
            array.add(temp.`val`)
            if (temp.left != null) {
                queue.offer(temp.left)
            }
            if (temp.right != null) {
                queue.offer(temp.right)
            }
        }
        return array.toIntArray()
    }
}