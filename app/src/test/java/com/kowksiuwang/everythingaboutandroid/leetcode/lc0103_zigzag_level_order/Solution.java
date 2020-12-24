package com.kowksiuwang.everythingaboutandroid.leetcode.lc0103_zigzag_level_order;

import com.kowksiuwang.everythingaboutandroid.leetcode.data.TreeNode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by kwoksiuwang on 12/22/20!!!
 */
public class Solution {
    @Test
    public void test(){
        //[3,9,20,null,null,15,7]
        TreeNode tn15 = new TreeNode(15, null, null);
        TreeNode tn7 = new TreeNode(7, null, null);
        TreeNode tn20 = new TreeNode(20, tn15, tn7);
        TreeNode tn9 = new TreeNode(9, null, null);
        TreeNode root = new TreeNode(3, tn9, tn20);
        zigzagLevelOrder(root);
    }
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        //唯一区别就是插入数组的时候是头插还是尾插。
        boolean isAddInEnd = true;
        while (stack.size() > 0) {
            List<Integer> list = new LinkedList<>();
            int size = stack.size();
            while (size > 0) {
                TreeNode node = stack.pollFirst();
                if (isAddInEnd) {
                    list.add(node.getVal());
                } else {
                    list.add(0, node.getVal());
                }
                if (node.getLeft() != null) {
                    stack.offer(node.getLeft());
                }
                if (node.getRight() != null) {
                    stack.offer(node.getRight());
                }
                size--;
            }
            result.add(list);
            isAddInEnd = !isAddInEnd;
        }
        return result;
    }
}
