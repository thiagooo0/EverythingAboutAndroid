package com.kowksiuwang.everythingaboutandroid.leetcode.lc0234_palindrome_linked_list;


import org.junit.Assert;
import org.junit.Test;

/**
 * 1 2 3 4 4 3 2 1
 * Created by kwoksiuwang on 2020/10/23!!!
 */
public class Solution {
    @Test
    public void Test() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);

        ListNode l1_b = new ListNode(1);
        ListNode l2_b = new ListNode(2);
        ListNode l3_b = new ListNode(3);
        ListNode l4_b = new ListNode(4);

        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;

//        l4_b.next = l3_b;
//        l3_b.next = l2_b;
//        l2_b.next = l1_b;
//
//        l5.next = l4_b;
        Assert.assertEquals(isPalindrome(l1), false);
    }

    public boolean isPalindrome(ListNode head) {
        if(head == null){
            return true;
        }
        ListNode mid = head;
        ListNode fast = head;
        ListNode slow = head;
        //通过快慢指针找到中点。
        //由于快指针每次都走两步，加上它起点也占一格，所以它走过的路程永远是奇数。
        //当链表为偶数，快指针踩到倒数第二步的时候，同时慢指针可以踩到前半部分的尾部。
        //当链表为奇数，快指针可以刚刚好踩到链表到尾部，同时慢指针可以踩到中心点。
        while (fast != null) {
            if (fast.next == null) {
                mid = slow;
                break;
            } else if (fast.next.next == null) {
                mid = slow;
                break;
            } else {
                fast = fast.next.next;
                slow = slow.next;
            }
        }

        //反转后半部分
        ListNode rightHead = null;
        ListNode tempHead;
        ListNode p = mid.next;
        ListNode tempP;
        while (p != null) {
            tempP = p.next;
            tempHead = rightHead;
            rightHead = p;
            rightHead.next = tempHead;
            p = tempP;
        }
        mid.next = rightHead;

        //比对
        ListNode h = head;

        while (rightHead != null) {
            if (h.val != rightHead.val) {
                return false;
            }
            h = h.next;
            rightHead = rightHead.next;
        }

        return true;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
