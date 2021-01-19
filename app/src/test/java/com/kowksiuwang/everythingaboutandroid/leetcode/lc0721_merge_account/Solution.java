package com.kowksiuwang.everythingaboutandroid.leetcode.lc0721_merge_account;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定一个列表 accounts，每个元素 accounts[i] 是一个字符串列表，其中第一个元素 accounts[i][0] 是 名称 (name)，其余元素是 emails 表示该账户的邮箱地址。
 * <p>
 * 现在，我们想合并这些账户。如果两个账户都有一些共同的邮箱地址，则两个账户必定属于同一个人。请注意，即使两个账户具有相同的名称，它们也可能属于不同的人，因为人们可能具有相同的名称。一个人最初可以拥有任意数量的账户，但其所有账户都具有相同的名称。
 * <p>
 * 合并账户后，按以下格式返回账户：每个账户的第一个元素是名称，其余元素是按顺序排列的邮箱地址。账户本身可以以任意顺序返回。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：
 * accounts =
 * [["John", "johnsmith@mail.com", "john00@mail.com"],
 * ["John", "johnnybravo@mail.com"],
 * ["John", "johnsmith@mail.com", "john_newyork@mail.com"],
 * ["Mary", "mary@mail.com"]]
 * <p>
 * 输出：
 * [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],
 * ["John", "johnnybravo@mail.com"],
 * ["Mary", "mary@mail.com"]]
 * <p>
 * 解释：
 * 第一个和第三个 John 是同一个人，因为他们有共同的邮箱地址 "johnsmith@mail.com"。
 * 第二个 John 和 Mary 是不同的人，因为他们的邮箱地址没有被其他帐户使用。
 * 可以以任何顺序返回这些列表，例如答案 [['Mary'，'mary@mail.com']，['John'，'johnnybravo@mail.com']，
 * ['John'，'john00@mail.com'，'john_newyork@mail.com'，'johnsmith@mail.com']] 也是正确的。
 */

public class Solution {
    @Test
    public void test() {
        ArrayList<String> a1 = new ArrayList<>(Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"));
        ArrayList<String> a2 = new ArrayList<>(Arrays.asList("John", "johnnybravo@mail.com"));
        ArrayList<String> a3 = new ArrayList<>(Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"));
        ArrayList<String> a4 = new ArrayList<>(Arrays.asList("Mary", "mary@mail.com"));
//        ArrayList<String> a5 = new ArrayList<>(Arrays.asList("Jose", "johnsmith@mail.com", "john00@mail.com"));
        ArrayList<List<String>> testList = new ArrayList<>(Arrays.asList(a1, a2, a3, a4));
        List<List<String>> result = accountsMerge2(testList);
        for (List<String> r : result) {
            System.out.println(Arrays.toString(r.toArray()));
        }
    }

    class UnionFind2 {
        int[] parent;

        UnionFind2(int size) {
            parent = new int[size];
            for (int i = 1; i < size; i++) {
                parent[i] = i;
            }
        }

        void union(int i1, int i2) {
            parent[find(i1)] = find(i2);
        }

        int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }
    }

    public List<List<String>> accountsMerge2(List<List<String>> accounts) {
        HashMap<String, Integer> emailToIndex = new HashMap<>();
        HashMap<String, String> emailToName = new HashMap<>();
        int emailCount = 0;
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                if (!emailToIndex.containsKey(email)) {
                    emailToIndex.put(email, emailCount++);
                    emailToName.put(email, name);
                }
            }
        }

        UnionFind2 unionFind = new UnionFind2(emailCount);
        for (List<String> account : accounts) {
            int first = emailToIndex.get(account.get(1));
            for (int i = 2; i < account.size(); i++) {
                unionFind.union(first, emailToIndex.get(account.get(i)));
            }
        }

        HashMap<Integer, ArrayList<String>> indexToEmail = new HashMap<>();
        for (String email : emailToIndex.keySet()) {
            int index = unionFind.find(emailToIndex.get(email));
            ArrayList<String> list = indexToEmail.getOrDefault(index, new ArrayList<String>());
            list.add(email);
            indexToEmail.put(index, list);
        }

        List<List<String>> merge = new ArrayList<>();
        for (ArrayList<String> emails : indexToEmail.values()) {
            Collections.sort(emails);
            ArrayList<String> list = new ArrayList<>(emails);
            list.add(0, emailToName.get(emails.get(0)));
            merge.add(list);
        }
        return merge;
    }


    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Integer> emailToIndex = new HashMap<String, Integer>();
        Map<String, String> emailToName = new HashMap<String, String>();
        int emailsCount = 0;
        for (List<String> account : accounts) {
            String name = account.get(0);
            int size = account.size();
            for (int i = 1; i < size; i++) {
                String email = account.get(i);
                if (!emailToIndex.containsKey(email)) {
                    emailToIndex.put(email, emailsCount++);
                    emailToName.put(email, name);
                }
            }
        }
        UnionFind uf = new UnionFind(emailsCount);

        //同一个账号名的，都以第一个邮箱为头构造并查集。
        for (List<String> account : accounts) {
            String firstEmail = account.get(1);
            int firstIndex = emailToIndex.get(firstEmail);
            int size = account.size();
            for (int i = 2; i < size; i++) {
                String nextEmail = account.get(i);
                int nextIndex = emailToIndex.get(nextEmail);
                uf.union(firstIndex, nextIndex);
            }
        }
        //以几个头邮箱的index为key，后面存储了所有相关的email？？？那直接拿原始的数据转不就好了？？？
        Map<Integer, List<String>> indexToEmails = new HashMap<Integer, List<String>>();
        for (String email : emailToIndex.keySet()) {
            int index = uf.find(emailToIndex.get(email));
            List<String> account = indexToEmails.getOrDefault(index, new ArrayList<String>());
            account.add(email);
            indexToEmails.put(index, account);
        }
        List<List<String>> merged = new ArrayList<List<String>>();
        for (List<String> emails : indexToEmails.values()) {
            Collections.sort(emails);
            String name = emailToName.get(emails.get(0));
            List<String> account = new ArrayList<String>();
            account.add(name);
            account.addAll(emails);
            merged.add(account);
        }
        return merged;
    }

}

class UnionFind {
    int[] parent;

    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public void union(int index1, int index2) {
        parent[find(index2)] = find(index1);
    }

    public int find(int index) {
        if (parent[index] != index) {
            //通过递归，找到最后的根节点，并且让本节点直接指向根节点，从而达到缩短路径的目的，下次搜索的时候速度加快
            parent[index] = find(parent[index]);
        }
        return parent[index];
    }
}