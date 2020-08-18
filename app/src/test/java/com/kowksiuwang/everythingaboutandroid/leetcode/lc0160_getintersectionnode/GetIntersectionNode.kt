package com.kowksiuwang.everythingaboutandroid.leetcode.lc0160_getintersectionnode

import com.kowksiuwang.everythingaboutandroid.leetcode.data.ListNodeOneSide

/**
 * 输入两个链表，找出它们的第一个公共节点。
 * Created by GuoShaoHong on 2020/8/13.
 */
class GetIntersectionNode {
    /**
     * 由于这个题目比较难测试，我就不在这里测试了。
     */

    fun getIntersectionNode1(headA: ListNodeOneSide?, headB: ListNodeOneSide?): ListNodeOneSide? {
        if (headA == null || headB == null) {
            return null
        }
        val hashSet = HashSet<ListNodeOneSide>()
        var hA = headA
        while (hA != null) {
            hashSet.add(hA)
            hA = hA!!.next
        }
        var hB = headB
        while (hB != null) {
            if (hashSet.contains(hB!!)) {
                return hB
            }
            hB = hB!!.next
        }
        return null
    }

    fun getIntersectionNode2(headA: ListNodeOneSide?, headB: ListNodeOneSide?): ListNodeOneSide? {
        if(headA==null || headB==null){
            return null
        }
        var hA = headA
        var hB = headB
        while(hB!=hA){
            hA = if(hA==null){headB}else{hA!!.next}
            hB = if(hB==null){headA}else{hB!!.next}
        }
        return hA
    }
}