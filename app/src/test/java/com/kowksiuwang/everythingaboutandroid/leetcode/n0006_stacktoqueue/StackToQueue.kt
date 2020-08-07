package com.kowksiuwang.everythingaboutandroid.leetcode.n0006_stacktoqueue

import java.util.*

/**
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 *
 * 示例 1：
 * 输入：
 * ["CQueue","appendTail","deleteHead","deleteHead"]
 * [[],[3],[],[]]
 * 输出：[null,null,3,-1]
 *
 * 示例 2：
 * 输入：
 * ["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
 * [[],[],[5],[2],[],[]]
 * 输出：[null,-1,null,null,5,2]
 *
 * 提示：
 * 1 <= values <= 10000
 * 最多会对 appendTail、deleteHead 进行 10000 次调用
 *
 * 作者：画手大鹏
 * 链接：https://leetcode-cn.com/leetbook/read/illustrate-lcof/xz8cid/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 * Created by GuoShaoHong on 2020/8/7.
 */
class StackToQueue {
    private val stack1 = Stack<Int>()
    private val stack2 = Stack<Int>()

    fun appendTail(value: Int) {
        stack1.push(value)
    }

    fun deleteHead(): Int {
        if (stack1.isEmpty()) {
            return -1
        }
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop())
        }
        val result = stack2.pop()
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop())
        }
        return result
    }
}