package com.kowksiuwang.everythingaboutandroid.leetcode.lc0048_group_anagrams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kwoksiuwang on 12/14/20!!!
 */
public class Solution {
    /**
     * 速度极慢的原始代码
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagramsBefore(String[] strs) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<List<String>> result = new ArrayList<>(map.values());

        for (String s : strs) {
            char[] keyCharArray = s.toCharArray();
            Arrays.sort(keyCharArray);
            //这里加个*拖低了效率。
            String key = "*" + String.valueOf(keyCharArray);
            //这里其实做了重复的代码，用getOrDefault可以完成两步操作。
            if (map.containsKey(key)) {
                map.get(key).add(s);
            } else {
                ArrayList<String> list = new ArrayList<>();
                list.add(s);
                map.put(key, list);
            }
        }
        for (ArrayList<String> l : map.values()) {
            result.add(l);
        }

        return result;
    }

    /**
     * 改良后的代码
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            char[] keyCharArray = s.toCharArray();
            Arrays.sort(keyCharArray);
            String key = "*" + String.valueOf(keyCharArray);
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(s);
            map.put(key, list);
        }

        return new ArrayList<>(map.values());
    }

    /**
     *
     */
    public List<List<String>> groupAnagrams2(String[] strs) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            int[] counts = new int[26];
            int length = str.length();
            for (int i = 0; i < length; i++) {
                counts[str.charAt(i) - 'a']++;
            }
            // 将每个出现次数大于 0 的字母和出现次数按顺序拼接成字符串，作为哈希表的键
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 26; i++) {
                if (counts[i] != 0) {
                    sb.append((char) ('a' + i));
                    sb.append(counts[i]);
                }
            }
            String key = sb.toString();
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<List<String>>(map.values());
    }

    /**
     * 来自og的精妙代码
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagramsOG(String[] strs) {
        HashMap<String, List<String>> stringStringHashMap = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String s = new String(chars);

            List<String> orDefault = stringStringHashMap.get(s);
            if (null == orDefault) {
                orDefault = new ArrayList<String>();
                stringStringHashMap.put(s, orDefault);
            }
            orDefault.add(str);
        }

        ArrayList<List<String>> lists = new ArrayList<>();
        for (List<String> item :
                stringStringHashMap.values()) {
            lists.add(item);
        }

        return lists;

    }
}
