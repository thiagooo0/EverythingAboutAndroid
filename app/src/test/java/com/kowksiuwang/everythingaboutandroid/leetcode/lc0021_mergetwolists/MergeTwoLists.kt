package com.kowksiuwang.everythingaboutandroid.leetcode.lc0021_mergetwolists

import com.kowksiuwang.everythingaboutandroid.leetcode.data.ListNodeOneSide
import com.kowksiuwang.everythingaboutandroid.leetcode.data.createListNodeOneSide
import org.junit.Assert
import org.junit.Test

/**
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
 *
 * 示例：
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 *
 * Created by GuoShaoHong on 2020/8/13.
 */
class MergeTwoLists {
    @Test
    fun test() {
        Assert.assertEquals(
            mergeTwoLists(
                createListNodeOneSide(1, 2, 3, 4),
                createListNodeOneSide(6, 7, 8, 9)
            )!! == createListNodeOneSide(1, 2, 3, 4, 6, 7, 8, 9), true
        )
        Assert.assertEquals(
            mergeTwoLists(
                createListNodeOneSide(1, 2, 6),
                createListNodeOneSide(3, 4, 7, 8, 9)
            )!! == createListNodeOneSide(1, 2, 3, 4, 6, 7, 8, 9), true
        )
        Assert.assertEquals(
            mergeTwoLists(
                createListNodeOneSide(1, 2, 3, 4, 6, 7, 9),
                createListNodeOneSide(8)
            )!! == createListNodeOneSide(1, 2, 3, 4, 6, 7, 8, 9), true
        )
        Assert.assertEquals(
            mergeTwoLists(
                createListNodeOneSide(1, 9),
                createListNodeOneSide(2, 3, 4, 6, 7, 8)
            )!! == createListNodeOneSide(1, 2, 3, 4, 6, 7, 8, 9), true
        )
    }


    /**
     * 这题也可以用递归做。。
     */

    fun mergeTwoLists(l1: ListNodeOneSide?, l2: ListNodeOneSide?): ListNodeOneSide? {
        if (l1 == null) {
            return l2
        }
        if (l2 == null) {
            return l1
        }
        var lNew: ListNodeOneSide? = null
        var lNewH: ListNodeOneSide? = null
        var l1H = l1
       var l2H = l2
        while (l1H != null && l2H != null) {
            if (l1H.`val` < l2H.`val`) {
                if (lNew == null) {
                    lNew = l1H
                    lNewH = lNew
                } else {
                    lNew.next = l1H
                    lNew = lNew.next
                }
                l1H = l1H.next
            } else {
                if (lNew == null) {
                    lNew = l2H
                    lNewH = lNew
                } else {
                    lNew.next = l2H
                    lNew = lNew.next
                }
                l2H = l2H.next
            }
        }
        if (l2H != null) {
            lNew!!.next = l2H
        }
        if (l1H != null) {
            lNew!!.next = l1H
        }
        return lNewH
    }
}