package com.kowksiuwang.everythingaboutandroid.leetcode.lc0842_split_array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kwoksiuwang on 12/8/20!!!
 */
public class Solution {

    @Test
    public void test() {
        splitIntoFibonacci("123456579");
        splitIntoFibonacci("11235813");
        splitIntoFibonacci("112358130");
        splitIntoFibonacci("0123");
        splitIntoFibonacci("9876543210987654321119753086421");
        splitIntoFibonacci("1101111");
    }

    public List<Integer> splitIntoFibonacci(String S) {
        List<Integer> result = new ArrayList<>();
        int[] sInt = new int[S.length()];
        char[] cS = S.toCharArray();
        for (int i = 0; i < cS.length; i++) {
            sInt[i] = Integer.parseInt(String.valueOf(cS[i]));
        }
        System.out.println("sInt:" + Arrays.toString(sInt));
        for (int i = 1; i < 11 && i <= sInt.length / 2; i++) {
            for (int j = i + 1; j < i + 11 && j <= sInt.length * 2 / 3; j++) {
                long first = jointNum(sInt, 0, i);
                long second = jointNum(sInt, i, j);
                System.out.println("第一个数:" + first + ",第二个数:" + second);
                if (first > Integer.MAX_VALUE || second > Integer.MAX_VALUE || first + second > Integer.MAX_VALUE) {
                    System.out.println("超出int范围");
                    continue;
                }

                result.add((int) first);
                result.add((int) second);
                if (check(sInt, j, result, (int) first, (int) second)) {
                    System.out.print("result:[");
                    for (int r : result) {
                        System.out.print(r + ",");
                    }
                    System.out.println("]");
                    System.out.println("-------");
                    return result;
                }
                result.clear();
            }
        }
        System.out.print("result:[");
        for (int r : result) {
            System.out.print(r + ",");
        }
        System.out.println("]");
        System.out.println("-------.");
        return result;
    }

    private boolean check(int[] array, int nextIndex, List<Integer> result, int first, int second) {
        while (nextIndex < array.length) {
            long nextNum = first + second;
            int nextNumLength = (nextNum + "").length();
            if (nextNum > Integer.MAX_VALUE) {
                return false;
            }
            if (array.length - nextIndex < nextNumLength) {
                return false;
            }
            if (jointNum(array, nextIndex, nextIndex + nextNumLength) == nextNum) {
                result.add((int) nextNum);
                first = second;
                second = (int) nextNum;
                nextIndex += nextNumLength;
            } else {
                return false;
            }
        }
        return true;
    }

    public long jointNum(int[] array, int start, int end) {
        int result = 0;
        while (start < end) {
            result = result * 10 + array[start];
            start++;
        }
        return result;
    }

    /**
     * 看看人家100%的算法
     */
    public List<Integer> splitIntoFibonacci2(String S) {
        List<Integer> res = new ArrayList<>();
        backtrack(S.toCharArray(), res, 0);
        return res;
    }

    public boolean backtrack(char[] digit, List<Integer> res, int index) {
        //边界条件判断，如果截取完了，并且res长度大于等于3，表示找到了一个组合。
        if (index == digit.length && res.size() >= 3) {
            return true;
        }
        for (int i = index; i < digit.length; i++) {
            //两位以上的数字不能以0开头
            if (digit[index] == '0' && i > index) {
                break;
            }
            //截取字符串转化为数字
            long num = subDigit(digit, index, i + 1);
            //如果截取的数字大于int的最大值，则终止截取
            if (num > Integer.MAX_VALUE) {
                break;
            }
            int size = res.size();
            //如果截取的数字大于res中前两个数字的和，说明这次截取的太大，直接终止，因为后面越截取越大
            if (size >= 2 && num > res.get(size - 1) + res.get(size - 2)) {
                break;
            }
            if (size <= 1 || num == res.get(size - 1) + res.get(size - 2)) {
                //把数字num添加到集合res中
                res.add((int) num);
                //如果找到了就直接返回
                if (backtrack(digit, res, i + 1))
                    return true;
                //如果没找到，就会走回溯这一步，然后把上一步添加到集合res中的数字给移除掉
                res.remove(res.size() - 1);
            }
        }
        return false;
    }

    //相当于截取字符串S中的子串然后转换为十进制数字
    private long subDigit(char[] digit, int start, int end) {
        long res = 0;
        for (int i = start; i < end; i++) {
            res = res * 10 + digit[i] - '0';
        }
        return res;
    }


}
