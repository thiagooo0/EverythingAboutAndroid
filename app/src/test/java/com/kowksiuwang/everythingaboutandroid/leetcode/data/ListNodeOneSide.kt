package com.kowksiuwang.everythingaboutandroid.leetcode.data

/**
 * 单向链表
 * Created by GuoShaoHong on 2020/8/12.
 */
data class ListNodeOneSide(var `val`: Int) {
    var next: ListNodeOneSide? = null
    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other is ListNodeOneSide) {
            val iH = this
            val jH = other as ListNodeOneSide
            if (jH.`val` == iH.`val`) {
                var i = iH.next
                var j = jH.next
                while (i != null && j != null) {
                    if (i.`val` != j.`val`) {
                        return false
                    }
                    i = i.next
                    j = j.next
                }
                return i == null && j == null
            }
        }
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

/**
 * 快捷创建单项链表的方法
 */
fun createListNodeOneSide(vararg intArray: Int): ListNodeOneSide? {
    if (intArray.isEmpty()) {
        return null
    }
    val head = ListNodeOneSide(intArray[0])
    var now = head

    for (i in 1 until intArray.size) {
        val n = ListNodeOneSide(intArray[i])
        now.next = n
        now = n
    }
    return head
}