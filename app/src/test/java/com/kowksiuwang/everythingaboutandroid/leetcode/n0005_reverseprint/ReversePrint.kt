package com.kowksiuwang.everythingaboutandroid.leetcode.n0005_reverseprint

import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

/**
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 * 示例 1：
 * 输入：head = [1,3,2]
 * 输出：[2,3,1]
 *  
 * 限制：
 * 0 <= 链表长度 <= 10000
 *
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 * Created by GuoShaoHong on 2020/8/5.
 */
class ReversePrint {
    @Test
    fun test() {
        val h1 = ListNode(1)
        h1.next = ListNode(2)
        h1.next!!.next = ListNode(3)
        Assert.assertEquals(reversePrint(h1).contentEquals(intArrayOf(3, 2, 1)), true)
        Assert.assertEquals(reversePrintWithStack(h1).contentEquals(intArrayOf(3, 2, 1)), true)
    }

    fun reversePrint(head: ListNode?): IntArray {
        val array = ArrayList<Int>()
        var h = head
        while (h != null) {
            array.add(h.v)
            h = h.next
        }
        if (array.size != 0) {
            var i = 0
            var j = array.size - 1
            while (i < j) {
                exchangeNum(array, i, j)
                i++
                j--
            }
        }
        return array.toIntArray()
    }

    fun exchangeNum(arrayList: ArrayList<Int>, i: Int, j: Int) {
        arrayList[i] = arrayList[j] - arrayList[i]
        arrayList[j] = arrayList[j] - arrayList[i]
        arrayList[i] = arrayList[j] + arrayList[i]
    }

    /**
     * 栈！
     */
    fun reversePrintWithStack(head: ListNode?): IntArray {
        val stack = Stack<Int>()
        var h = head
        while (h != null) {
            stack.push(h.v)
            h = h.next
        }
        val array = IntArray(stack.size)
        var i = 0
        while (!stack.empty()) {
            array[i] = stack.pop()
            i++
        }
        return array
    }

    class ListNode(var v: Int) {
        var next: ListNode? = null
    }
}