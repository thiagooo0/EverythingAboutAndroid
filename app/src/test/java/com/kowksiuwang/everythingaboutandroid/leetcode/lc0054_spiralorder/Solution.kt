package com.kowksiuwang.everythingaboutandroid.leetcode.lc0054_spiralorder

import org.junit.Assert
import org.junit.Test

/**
 * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
 *
 * 示例 1:
 *
 * 输入:
 * [
 * [ 1, 2, 3 ],
 * [ 4, 5, 6 ],
 * [ 7, 8, 9 ]
 * ]
 * 输出: [1,2,3,6,9,8,7,4,5]
 * 示例 2:
 *
 * 输入:
 * [
 * [1, 2, 3, 4],
 * [5, 6, 7, 8],
 * [9,10,11,12]
 * ]
 * 输出: [1,2,3,4,8,12,11,10,9,5,6,7]
 * Created by GuoShaoHong on 2020/9/2.
 */
class Solution {
    @Test
    fun test() {
        Assert.assertEquals(
            spiralOrder(
                arrayOf(
                    intArrayOf(1, 2, 3),
                    intArrayOf(4, 5, 6),
                    intArrayOf(7, 8, 9)
                )
            )
                    == listOf(1, 2, 3, 6, 9, 8, 7, 4, 5)
            , true
        )

        Assert.assertEquals(
            spiralOrder(
                arrayOf(
                    intArrayOf(1, 2, 3, 10),
                    intArrayOf(4, 5, 6, 11),
                    intArrayOf(7, 8, 9, 12),
                    intArrayOf(16, 15, 14, 13)
                )
            )
                    == listOf(1, 2, 3, 10, 11, 12, 13, 14, 15, 16, 7, 4, 5, 6, 9, 8)
            , true
        )

        Assert.assertEquals(
            spiralOrder(
                arrayOf(
                    intArrayOf(1, 2, 3, 10),
                    intArrayOf(4, 5, 6, 11),
                    intArrayOf(7, 8, 9, 12)
                )
            )
                    == listOf(1, 2, 3, 10, 11, 12, 9, 8, 7, 4, 5, 6)
            , true
        )

        Assert.assertEquals(
            spiralOrder(
                arrayOf(
                    intArrayOf(1)
                )
            )
                    == listOf(1)
            , true
        )

        Assert.assertEquals(
            spiralOrder(
                arrayOf(
                    intArrayOf(1, 2),
                    intArrayOf(4, 5)
                )
            )
                    == listOf(1, 2, 5, 4)
            , true
        )
    }

    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        if (matrix.isEmpty() || matrix[0].isEmpty()) {
            return listOf()
        }
        val maxCount = matrix.size * matrix[0].size
        val result = MutableList(maxCount) { 0 }
        var dir = 0 //0：左到右，1：上到下，2：右到左，3：下到上
        var dirTime = intArrayOf(0, 0, 0, 0)
        var count = 0
        var i = 0
        var j = 0
        while (count < maxCount) {
            result[count] = matrix[i][j]
            when (dir) {
                0 -> {
                    //撞墙了
                    if (j == matrix[0].size - 1 - dirTime[1]) {
                        dir = 1
                        dirTime[0]++
                        i++
                    } else {
                        j++
                    }
                }
                1 -> {
                    //撞墙了
                    if (i == matrix.size - 1 - dirTime[2]) {
                        dir = 2
                        dirTime[1]++
                        j--
                    } else {
                        i++
                    }
                }
                2 -> {
                    //撞墙了
                    if (j == dirTime[3]) {
                        dir = 3
                        dirTime[2]++
                        i--
                    } else {
                        j--
                    }
                }
                3 -> {
                    //撞墙了
                    if (i == dirTime[0]) {
                        dir = 0
                        dirTime[3]++
                        j++
                    } else {
                        i--
                    }
                }
                else -> {
                }
            }

            count++
        }
        return result
    }

    fun spiralOrder1(matrix: Array<IntArray>): List<Int> {
        val res = mutableListOf<Int>()
        if(matrix.isEmpty()){
            return res
        }
        var bottom = matrix.size - 1
        var right = matrix[0].size - 1
        var left = 0
        var top = 0
        while(bottom >= top && right >= left){
            for(i in left.. right){
                res.add(matrix[top][i])
            }
            top++
            for(i in top.. bottom){
                res.add(matrix[i][right])
            }
            right--
            if(bottom >= top){
                for(i in right downTo left){
                    res.add(matrix[bottom][i])
                }
                bottom--
            }
            if(right >= left){
                for(i in bottom downTo top){
                    res.add(matrix[i][left])
                }
                left++
            }
        }
        return res
    }
}