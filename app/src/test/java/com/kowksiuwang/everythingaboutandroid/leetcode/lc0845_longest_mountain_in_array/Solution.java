package com.kowksiuwang.everythingaboutandroid.leetcode.lc0845_longest_mountain_in_array;

/**
 * Created by kwoksiuwang on 2020/10/25!!!
 */
public class Solution {
    /**
     * 暴力解法
     */
    public int longestMountain(int[] A) {
        //定理1:山脉之间不会存在交集，但是存在包含。对于包含关系我们只取最长的。
        //遍历一次，去找到山脉的起点的时候开始计数，到终点之后取消计数，然后继续寻找下一个山脉起点。
        int mountainLength = 0;
        int tempLength = 0;
        int mStart = -1;
        boolean hasFoundTop = false;

        for(int i = 0; i < A.length; i++){
            //通过start判断是在找起点还是数山脉长度
            if(mStart == -1){
                if(i+1 < A.length && A[i] < A[i+1]){
                    mStart = i;
                }
            }else{
                //到尽头了，唯一要做的就是计数
                if(i+1 == A.length){
                    if(hasFoundTop){
                        tempLength = i - mStart + 1;
                        mountainLength = Math.max(mountainLength, tempLength);
                    }
                }else if(hasFoundTop){
                    //找到山顶，之后就要一路下降，如果不下降就到头了
                    if(A[i] <= A[i+1]){
                        tempLength = i - mStart + 1;
                        mountainLength = Math.max(mountainLength, tempLength);
                        if(A[i] < A[i+1]){
                            //这是另外一个起点。
                            mStart = i;
                        }else{
                            mStart = -1;
                        }
                        hasFoundTop = false;
                    }
                }else{
                    if(A[i] > A[i+1]){
                        //找到山峰
                        hasFoundTop = true;
                    }else if(A[i] == A[i+1]){
                        mStart = -1;
                    }
                }
            }
        }
        return mountainLength;
    }
}
