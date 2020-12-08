package com.kowksiuwang.everythingaboutandroid.leetcode.lc0861_score_after_flipping;

/**
 * Created by kwoksiuwang on 12/7/20!!!
 */
public class Solution {
    public int matrixScore(int[][] A) {
        int m = A.length, n = A[0].length;
        int ret = m * (1 << (n - 1));

        for (int j = 1; j < n; j++) {
            int nOnes = 0;
            for (int i = 0; i < m; i++) {
                if (A[i][0] == 1) {
                    nOnes += A[i][j];
                } else {
                    nOnes += (1 - A[i][j]); // 如果这一行进行了行反转，则该元素的实际取值为 1 - A[i][j]
                }
            }
            int k = Math.max(nOnes, m - nOnes);
            ret += k * (1 << (n - j - 1));
        }
        return ret;
    }

    public int matrixScore1(int[][] A) {
        if(A.length ==0 || A[0].length == 0){
            return 0;
        }
        int[] counts = new int[A[0].length];
        int result = 0;
        Boolean needToChange = false;
        for(int i = 0; i < A.length;i++){
            needToChange = A[i][0]==0;
            for(int j = 0; j<A[0].length;j++){
                Boolean isZero = A[i][j] ==0;
                if(needToChange){
                    if(isZero){
                        counts[j]++;
                    }
                }else if(!isZero){
                    counts[j]++;
                }
            }
        }
        for(int i = 0 ; i < counts.length; i++){
            if(counts[i] <= A.length/2){
                result = A.length-counts[i] + (result<<1);
            }else{
                result = counts[i] + (result<<1);
            }
        }

        return result;
    }
}
