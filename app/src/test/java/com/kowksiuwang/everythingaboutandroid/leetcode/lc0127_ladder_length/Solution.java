package com.kowksiuwang.everythingaboutandroid.leetcode.lc0127_ladder_length;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by kwoksiuwang on 2020/11/5!!!
 */
public class Solution {
//    Map<String, Integer> wordId = new HashMap<String, Integer>();
//    List<List<Integer>> edge = new ArrayList<List<Integer>>();
//    int nodeNum = 0;
//
//    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
//        wordId.clear();
//        edge.clear();
//        nodeNum = 0;
//        for (String word : wordList) {
//            addEdge(word);
//        }
//        addEdge(beginWord);
//        if (!wordId.containsKey(endWord)) {
//            return 0;
//        }
//        int[] dis = new int[nodeNum];
//        Arrays.fill(dis, Integer.MAX_VALUE);
//        int beginId = wordId.get(beginWord), endId = wordId.get(endWord);
//        dis[beginId] = 0;
//
//        Queue<Integer> que = new LinkedList<Integer>();
//        que.offer(beginId);
//        while (!que.isEmpty()) {
//            int x = que.poll();
//            if (x == endId) {
//                return dis[endId] / 2 + 1;
//            }
//            for (int it : edge.get(x)) {
//                if (dis[it] == Integer.MAX_VALUE) {
//                    dis[it] = dis[x] + 1;
//                    que.offer(it);
//                }
//            }
//        }
//        return 0;
//    }
//
//    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
//        wordId.clear();
//        edge.clear();
//        nodeNum = 0;
//        for (String word : wordList) {
//            addEdge(word);
//        }
//        addEdge(beginWord);
//        if (!wordId.containsKey(endWord)) {
//            return 0;
//        }
//
//        int[] disBegin = new int[nodeNum];
//        Arrays.fill(disBegin, Integer.MAX_VALUE);
//        int beginId = wordId.get(beginWord);
//        disBegin[beginId] = 0;
//        Queue<Integer> queBegin = new LinkedList<Integer>();
//        queBegin.offer(beginId);
//
//        int[] disEnd = new int[nodeNum];
//        Arrays.fill(disEnd, Integer.MAX_VALUE);
//        int endId = wordId.get(endWord);
//        disEnd[endId] = 0;
//        Queue<Integer> queEnd = new LinkedList<Integer>();
//        queEnd.offer(endId);
//
//        while (!queBegin.isEmpty() && !queEnd.isEmpty()) {
//            int queBeginSize = queBegin.size();
//            for (int i = 0; i < queBeginSize; ++i) {
//                int nodeBegin = queBegin.poll();
//                if (disEnd[nodeBegin] != Integer.MAX_VALUE) {
//                    return (disBegin[nodeBegin] + disEnd[nodeBegin]) / 2 + 1;
//                }
//                for (int it : edge.get(nodeBegin)) {
//                    if (disBegin[it] == Integer.MAX_VALUE) {
//                        disBegin[it] = disBegin[nodeBegin] + 1;
//                        queBegin.offer(it);
//                    }
//                }
//            }
//
//            int queEndSize = queEnd.size();
//            for (int i = 0; i < queEndSize; ++i) {
//                int nodeEnd = queEnd.poll();
//                if (disBegin[nodeEnd] != Integer.MAX_VALUE) {
//                    return (disBegin[nodeEnd] + disEnd[nodeEnd]) / 2 + 1;
//                }
//                for (int it : edge.get(nodeEnd)) {
//                    if (disEnd[it] == Integer.MAX_VALUE) {
//                        disEnd[it] = disEnd[nodeEnd] + 1;
//                        queEnd.offer(it);
//                    }
//                }
//            }
//        }
//        return 0;
//    }
//
//
//    public void addEdge(String word) {
//        addWord(word);
//        int id1 = wordId.get(word);
//        char[] array = word.toCharArray();
//        int length = array.length;
//        for (int i = 0; i < length; ++i) {
//            char tmp = array[i];
//            array[i] = '*';
//            String newWord = new String(array);
//            addWord(newWord);
//            int id2 = wordId.get(newWord);
//            edge.get(id1).add(id2);
//            edge.get(id2).add(id1);
//            array[i] = tmp;
//        }
//    }
//
//    public void addWord(String word) {
//        if (!wordId.containsKey(word)) {
//            wordId.put(word, nodeNum++);
//            edge.add(new ArrayList<Integer>());
//        }
//    }

    @Test
    public void test() {

        Assert.assertEquals(5, ladderLength("hit", "cog", Arrays.asList("hot", "dot", "dog", "lot", "log", "cog")));
        Assert.assertEquals(0, ladderLength("hit", "cog", Arrays.asList("hot", "dot", "dog", "lot", "log")));
        Assert.assertEquals(2, ladderLength("hit", "hot", Arrays.asList("hot", "dot", "dog", "lot", "log")));
    }

    Map<String, Integer> wordIds = new HashMap<>();
    List<List<Integer>> edge = new ArrayList<>();
    int edgeNums = 0;

    /**
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        wordIds.clear();
        edge.clear();
        edgeNums = 0;
        addEdge(beginWord);
        for (String w : wordList) {
            addEdge(w);
        }
        if (!wordIds.containsKey(endWord)) {
            return 0;
        }

        int[] disBegin = new int[edgeNums];
        Arrays.fill(disBegin, Integer.MAX_VALUE);
        LinkedList<Integer> queueBegin = new LinkedList<>();
        queueBegin.offer(wordIds.get(beginWord));
        disBegin[wordIds.get(beginWord)] = 0;

        int[] disEnd = new int[edgeNums];
        Arrays.fill(disEnd, Integer.MAX_VALUE);
        LinkedList<Integer> queueEnd = new LinkedList<>();
        queueEnd.offer(wordIds.get(endWord));
        disEnd[wordIds.get(endWord)] = 0;

        while (!queueBegin.isEmpty() && !queueEnd.isEmpty()) {
            int beginSize = queueBegin.size();
            for (int i = 0; i < beginSize; i++) {
                int id = queueBegin.poll();
                if (disEnd[id] != Integer.MAX_VALUE) {
                    return (disBegin[id] + disEnd[id]) / 2 + 1;
                }
                List<Integer> list = edge.get(id);
                for (int nextId : list) {

                    if (disBegin[nextId] == Integer.MAX_VALUE) {
                        disBegin[nextId] = disBegin[id] + 1;
                        queueBegin.offer(nextId);
                    }
                }
            }

            int endSize = queueEnd.size();
            for (int i = 0; i < endSize; i++) {
                int id = queueEnd.poll();
                if (disBegin[id] != Integer.MAX_VALUE) {
                    return (disEnd[id] + disBegin[id]) / 2 + 1;
                }
                List<Integer> list = edge.get(id);
                for (int nextId : list) {
                    if (disEnd[nextId] == Integer.MAX_VALUE) {
                        disEnd[nextId] = disEnd[id] + 1;
                        queueEnd.offer(nextId);
                    }
                }
            }
        }


        return 0;
    }

    private int addWord(String word) {
        int id;
        if (wordIds.containsKey(word)) {
            id = wordIds.get(word);
        } else {
            id = edgeNums;
            edge.add(edgeNums++, new ArrayList());
            wordIds.put(word, id);
        }
        return id;
    }

    private void addEdge(String word) {
        int id = addWord(word);
        //方法存疑.
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char temp = chars[i];
            chars[i] = '*';
            String newWord = new String(chars);
            int newId = addWord(newWord);
            edge.get(id).add(newId);
            edge.get(newId).add(id);
            chars[i] = temp;
        }
    }

    public static int ladderLength3(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord))
            return 0;
        Deque<String> q = new LinkedList<>();
        boolean[] visited = new boolean[wordList.size()];
        q.offerLast(beginWord);
        int cnt = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            ++cnt;
            for (int j = 0; j < size; j++) {
                String curWord = q.pollFirst();
                for (int i = 0; i < wordList.size(); i++) {
                    if (visited[i])
                        continue;
                    String s = wordList.get(i);
                    if (!diff(curWord, s))
                        continue;
                    if (s.equals(endWord))
                        return cnt + 1;

                    q.offerLast(s);
                    visited[i] = true;
                }
            }
        }
        return 0;
    }

    public static boolean diff(String fir, String sec) {
        int len = Math.min(fir.length(), sec.length());
        int diff = 0;
        for (int i = 0; i < len; i++) {
            if (fir.charAt(i) != sec.charAt(i))
                diff++;
        }
        if (diff == 1)
            return true;
        return false;
    }
}
