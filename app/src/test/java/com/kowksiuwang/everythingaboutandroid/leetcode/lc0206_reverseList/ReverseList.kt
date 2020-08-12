package com.kowksiuwang.everythingaboutandroid.leetcode.lc0206_reverseList

import com.kowksiuwang.everythingaboutandroid.leetcode.data.ListNodeOneSide
import com.kowksiuwang.everythingaboutandroid.leetcode.data.createListNodeOneSide
import org.junit.Assert
import org.junit.Test

/**
 * 反转一个单链表。
 *
 * 示例:
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 * 进阶:
 * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
 *
 * Created by GuoShaoHong on 2020/8/12.
 */
class ReverseList {
    @Test
    fun Test() {
        Assert.assertEquals(
            reverseList(createListNodeOneSide(0, 1, 2, 3, 4)) == createListNodeOneSide(
                4,
                3,
                2,
                1,
                0
            ), true
        )
        Assert.assertEquals(
            reverseList(createListNodeOneSide(0, 1)) == createListNodeOneSide(1, 0), true
        )
        Assert.assertEquals(
            reverseList(createListNodeOneSide(0)) == createListNodeOneSide(0), true
        )
    }

    fun reverseList(head: ListNodeOneSide?): ListNodeOneSide? {
//        return reverserList1(head)
        return reverserListRecursion(head)
    }

    /**
     * 直接翻转，一个指针指向新链表的头部，另外一个指针指向旧链表的头部，逐个把旧链表的值挪去新表上。
     */
    fun reverserList1(head: ListNodeOneSide?): ListNodeOneSide? {
        var hNew: ListNodeOneSide? = null
        var hOld = head
        var hNext: ListNodeOneSide? = null
        while (hOld != null) {
            //先标出旧链表中下一个的位置，因为把hOld挪去新表之后就找不到了
            hNext = hOld.next
            //挪过去新表，并且让hNew继续指向头
            hOld.next = hNew
            hNew = hOld
            //hOld指向旧链表的头
            hOld = hNext
        }
        return hNew
    }

    /**
     * 使用递归，实际上就是上面方法的变形。原理也是一样的。
     */
    fun reverserListRecursion(head: ListNodeOneSide?): ListNodeOneSide? {
        return recursion(null, head)
    }

    private var hNext: ListNodeOneSide? = null
    private fun recursion(
        hNew: ListNodeOneSide?,
        hOld: ListNodeOneSide?
    ): ListNodeOneSide? {
        return if (hOld == null) {
            hNew
        } else {
            //先标出旧链表中下一个的位置，因为把hOld挪去新表之后就找不到了
            hNext = hOld.next
            //挪过去新表，并且让hNew继续指向头
            hOld.next = hNew
            //            hNew = hOld
            //hOld指向旧链表的头
            //            hOld = hNext
            recursion(hOld, hNext)
        }
    }
}