package com.kowksiuwang.everythingaboutandroid.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SortJava {

    @Test
    public void test(){
        Assert.assertArrayEquals(testSort(new int[]{7,6,2,3,1,4,5}), new int[]{1,2,3,4,5,6,7});
        Assert.assertArrayEquals(testSort(new int[]{7,6,5,4,3,2,1}), new int[]{1,2,3,4,5,6,7});
        Assert.assertArrayEquals(testSort(new int[]{4,5,3,6,2,7,1}), new int[]{1,2,3,4,5,6,7});
        Assert.assertArrayEquals(testSort(new int[]{1,2,3,4,5,6,7}), new int[]{1,2,3,4,5,6,7});
        Assert.assertArrayEquals(testSort(new int[]{1,3,5,7,2,4,6}), new int[]{1,2,3,4,5,6,7});
        Assert.assertArrayEquals(testSort(new int[]{6,4,2,7,5,3,1}), new int[]{1,2,3,4,5,6,7});


    }

    public int[] testSort(int[] arrays){
        return fastSort(arrays);
    }

    /**
     *  普通的快排就这了。但是快排的优势在于随机数组，对于有序数组和重复数组有点无能为力。
     *  改良的方法。
     *  1，在k值的选择上优化，不是单单选第一个，而是取左中右三个，然后取大小处于中间的值。这样可以有效提高有序数组的排序效率。
     *  2，在遍历的过程中，遇到和k相同的数字都放到两边，k归位之后，把她们放会k的身边。这样可以有效提高重复数组的排序效率。
     */
    private int[] fastSort(int[] arrays){
        fastSort(arrays,0,arrays.length-1);
        return arrays;
    }

    private void fastSort(int[] arrays, int i, int j){
        if(i>=j){
            return;
        }

        int k = i;
        int end = j;
        while (i<j){
            while (arrays[j]>=arrays[k]&&j>i){
                j--;
            }
            while (arrays[i]<=arrays[k]&&i<j){
                i++;
            }
            if(i != j){
                arrays[i] = arrays[j] - arrays[i];
                arrays[j] = arrays[j] - arrays[i];
                arrays[i] = arrays[i] + arrays[j];
            }
        }

        if(i!=k){
            arrays[i] = arrays[k] - arrays[i];
            arrays[k] = arrays[k] - arrays[i];
            arrays[i] = arrays[i] + arrays[k];
        }
        fastSort(arrays, k, i-1);
        fastSort(arrays,i+1,end);
    }

}
