package com.kowksiuwang.everythingaboutandroid.leetcode.lc0144_binary_tree_preorder_traversal;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kwoksiuwang on 2020/10/27!!!
 */
public class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if (root == null) {
            return result;
        }
        TreeNode node = root;
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (node != null || !result.isEmpty()) {
            while (node != null) {
                result.add(node.val);
                stack.push(node);
                node = node.left;
            }
            node = stack.pop().right;
        }
        return result;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}