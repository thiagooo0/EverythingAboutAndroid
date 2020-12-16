package com.kowksiuwang.everythingaboutandroid.leetcode.lc0290_word_pattern;

import java.util.HashMap;

/**
 * Created by GuoShaoHong on 2020/12/16.
 */

class Solution {
    public boolean wordPattern(String pattern, String s) {
        HashMap<Character , String> map = new HashMap<>();
        HashMap<String, Character> reverseMap = new HashMap<>();
        char[] chars = pattern.toCharArray();
        String[] target = s.split(" ");
        if(chars.length != target.length){
            return false;
        }
        for(int i = 0; i < chars.length; i++){
            char c = chars[i];
            String v = map.getOrDefault(c, " ");
            char k = reverseMap.getOrDefault(target[i], ' ');

            //System.out.println("匹配模式： " + c +"  对比："+ target[i] +  " : " + v);
            if(v == " "&& k == ' '){
                map.put(c, target[i]);
                reverseMap.put(target[i], c);
            }else if(!v.equals(target[i]) || k != c){
                return false;
            }
        }

        return true;
    }
}
