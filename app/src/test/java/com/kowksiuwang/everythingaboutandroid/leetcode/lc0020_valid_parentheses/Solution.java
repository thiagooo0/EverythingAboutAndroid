package com.kowksiuwang.everythingaboutandroid.leetcode.lc0020_valid_parentheses;

import java.util.Stack;

/**
 * Created by kwoksiuwang on 12/9/20!!!
 */
public class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for(char c : chars){
            if(c=='('||c=='{'||c=='['){
                //入栈
                stack.push(c);
            }else{
                if(stack.size() == 0){
                    return false;
                }
                char lastC = stack.pop();
                if((lastC=='('&&c!=')') || (lastC=='{'&&c!='}') || (lastC=='['&&c!=']')){
                    return false;
                }
            }
        }
        return stack.size()==0;
    }

    /**
     * 用数组代替栈
     * @param s
     * @return
     */
    public boolean isValid2(String s) {
        char[] stack = new char[s.length()];
        char[] chars = s.toCharArray();
        int stackIndex = 0;
        //System.out.println("--stack:" + Arrays.toString(stack) + " stackIndex:"+stackIndex);
        for(char c : chars){
            if(c=='('||c=='{'||c=='['){
                //入栈
                stack[stackIndex++] = c;
                //System.out.println("stack:" + Arrays.toString(stack) + " stackIndex:"+stackIndex);
            }else{
                if(stackIndex == 0){
                    return false;
                }
                char lastC = stack[--stackIndex];
                //System.out.println("lastC:" + lastC + " c:" + c);
                if((lastC=='('&&c!=')') || (lastC=='{'&&c!='}') || (lastC=='['&&c!=']')){
                    return false;
                }
            }
        }
        return stackIndex==0;
    }
}
