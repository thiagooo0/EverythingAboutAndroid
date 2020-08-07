package com.kowksiuwang.everythingaboutandroid.leetcode.lc0155_minstack

import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * push(x) —— 将元素 x 推入栈中。
 * pop() —— 删除栈顶的元素。
 * top() —— 获取栈顶元素。
 * getMin() —— 检索栈中的最小元素。
 *  
 *
 * 示例:
 *
 * 输入：
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 *
 * 输出：
 * [null,null,null,null,-3,null,0,-2]
 *
 * 解释：
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 *  
 *
 * 提示：
 * pop、top 和 getMin 操作总是在 非空栈 上调用。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/min-stack
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * Created by GuoShaoHong on 2020/8/7.
 */
/**
 * 其实用array效率可能会高不少
 */
class MinStack {
    data class ListNode(
        val num: Int,
        var frontNode: ListNode? = null,
        var nextNode: ListNode? = null
    )

    var head: ListNode? = null
    var end: ListNode? = null

    fun push(x: Int) {
        if (head == null) {
            head = ListNode(x)
            end = head
        } else {
            end!!.nextNode = ListNode(x, end)
            end = end!!.nextNode
        }
    }

    fun pop() {
        if (end == null) {
            return
        }
        if (end!!.frontNode == null) {
            end = null
            head = null
        } else {
            end = end!!.frontNode
            end!!.nextNode = null
        }
    }

    fun top(): Int {
        return if (end == null) {
            -1
        } else {
            end!!.num
        }
    }

    fun getMin(): Int {
        if (head == null) {
            return -1
        }
        var p = head!!.nextNode
        var min = head!!.num
        while (p != null) {
            if (p.num < min) {
                min = p.num
            }
            p = p.nextNode
        }
        return min
    }
}

/**
 * 这里主要考察的辅助栈，有同步和非同步两种形式。
 *
 * 对于同步的情况，辅助栈每一位的数代表了从头到对应位置之间的最小值。每次插入值的时候，都和栈顶比较，如果自己小就把自己存进去，
 * 如果栈顶小，就存栈顶。pop的时候，两个栈一起pop。
 *
 * 对于异步栈，则是同步的改进版，只是存进小于等于栈顶数的数。pop的时候只有两个栈栈顶数相同的时候，辅助栈才pop。这样可以有效节省空间。
 * 当然第一次的时候，怎么都要加的。
 *
 * 此处实现异步栈
 */
class MinStackAsync {
    private val stack = Stack<Int>()
    private val assistStack = Stack<Int>()
    fun push(x: Int) {
        if (stack.isEmpty()) {
            assistStack.push(x)
        } else {
            if (x <= assistStack.peek()) {
                assistStack.push(x)
            }
        }
        stack.push(x)
    }

    fun pop() {
        val top = stack.pop()
        if (assistStack.peek() == top) {
            assistStack.pop()
        }
    }

    fun top(): Int {
        return stack.peek()
    }

    fun getMin(): Int {
        return assistStack.peek()
    }
}

//["MinStack","push","push","push","top","pop","getMin","pop","getMin","pop","push","top","getMin","push","top","getMin","pop","getMin"]
//[[],[2147483646],[2147483646],[2147483647],[],[],[],[],[],[],[2147483647],[],[],[-2147483648],[],[],[],[]]
class MinStackTest {
    @Test
    fun test() {
        val minStack = MinStack()
        minStack.apply {
            push(2147483646)
            push(2147483646)
            push(2147483647)
            Assert.assertEquals(top(), 2147483647)
            pop()
            Assert.assertEquals(getMin(), 2147483646)
            pop()
            Assert.assertEquals(getMin(), 2147483646)
            pop()
            push(2147483647)
            top()
            Assert.assertEquals(getMin(), 2147483647)
            push(-2147483648)
            Assert.assertEquals(top(), -2147483648)
            Assert.assertEquals(getMin(), -2147483648)
            pop()
            Assert.assertEquals(getMin(), 2147483647)
        }
    }
}