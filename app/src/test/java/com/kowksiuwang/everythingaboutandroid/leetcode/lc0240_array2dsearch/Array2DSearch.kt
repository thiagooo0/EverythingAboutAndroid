package com.kowksiuwang.everythingaboutandroid.lc0240_array2dsearch

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
 *
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 * 示例:
 *
 * 现有矩阵 matrix 如下：
 *
 * [
 * [1,   4,  7, 11, 15],
 * [2,   5,  8, 12, 19],
 * [3,   6,  9, 16, 22],
 * [10, 13, 14, 17, 24],
 * [18, 21, 23, 26, 30]
 * ]
 * 给定 target = 5，返回 true。
 *
 * 给定 target = 20，返回 false。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-a-2d-matrix-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * Created by GuoShaoHong on 2020/8/3.
 */
class Array2DSearch {
    @Test
    fun test() {
        assertEquals(
            findNumberIn2DArray(
                arrayOf(
                ), 5
            ),
            false
        )
        assertEquals(
            findNumberIn2DArray(
                arrayOf(
                    intArrayOf(),
                    intArrayOf(),
                    intArrayOf(),
                    intArrayOf(),
                    intArrayOf()
                ), 5
            ),
            false
        )
        assertEquals(
            findNumberIn2DArray(
                arrayOf(
                    intArrayOf(1, 4, 7, 11, 15),
                    intArrayOf(2, 5, 8, 12, 19),
                    intArrayOf(3, 6, 9, 16, 22),
                    intArrayOf(10, 13, 14, 17, 24),
                    intArrayOf(18, 21, 23, 26, 30)
                ), 5
            ),
            true
        )

        assertEquals(
            findNumberIn2DArray(
                arrayOf(
                    intArrayOf(1, 4, 7, 11, 15),
                    intArrayOf(2, 5, 8, 12, 19),
                    intArrayOf(3, 6, 9, 16, 22),
                    intArrayOf(10, 13, 14, 17, 24),
                    intArrayOf(18, 21, 23, 26, 30)
                ), 20
            ),
            false
        )

        assertEquals(
            findNumberIn2DArray(
                arrayOf(
                    intArrayOf(1, 4, 7, 11, 15, 38),
                    intArrayOf(2, 5, 8, 12, 19, 46),
                    intArrayOf(3, 6, 9, 16, 22, 47),
                    intArrayOf(10, 13, 14, 17, 24, 48),
                    intArrayOf(18, 21, 23, 26, 30, 55),
                    intArrayOf(19, 22, 25, 29, 35, 50)
                ), 20
            ),
            false
        )
    }

    private fun findNumberIn2DArray(matrix: Array<IntArray>, target: Int): Boolean {
        //暴力二分查找
//        return findNumberIn2DArrayDichotomy(matrix, target)
        //逐行逐列排除，时间复杂度m+n(假设Matrix[m][n])
        return findNumberIn2DArrayExclude(matrix, target)
    }

    /**
     * 找到取值区域包含目标数的数字，然后通过二分法寻找是否存在改数
     */
    private fun findNumberIn2DArrayDichotomy(matrix: Array<IntArray>, target: Int): Boolean {
        if (matrix.isEmpty() || matrix[0].isEmpty()) {
            return false
        }
        var i = 0
        while (i < matrix.size && matrix[i][0] <= target) {
            if (matrix[i][matrix[0].size - 1] >= target) {
                if (dichotomy(matrix[i], target)) {
                    return true
                }
            }
            i++
        }
        return false
    }

    private fun dichotomy(
        array: IntArray,
        target: Int,
        startIndex: Int = 0,
        endIndex: Int = array.size - 1
    ): Boolean {
        val midOne = (startIndex + endIndex) / 2

        if (array[startIndex] == target || array[endIndex] == target || array[midOne] == target) {
            return true
        }

        //判断前后两个指针中间相邻的情况
        if (midOne == startIndex || midOne == endIndex) {
            return false
        }

        if (array[midOne] > target) {
            return dichotomy(array, target, startIndex, midOne)
        } else {
            return dichotomy(array, target, midOne, endIndex)
        }

        return false
    }

    /**
     * 排除法，从左下角开始排除，一次可以排除掉一行或者一列。当[i,j]>target的时候，说明当前行整行都>target，那j可以-1。
     * 当[i,j]<target的时候，说明当前列整列都<target，i可以+1。（由于是从左下角开始的，所以i左边和j下面的要么不存在，要么被排除了）
     */
    private fun findNumberIn2DArrayExclude(matrix: Array<IntArray>, target: Int): Boolean {
        if (matrix.isEmpty() || matrix[0].isEmpty() || target < matrix[0][0] || target > matrix[matrix.size - 1][matrix[0].size - 1]) {
            return false
        }
        var i = matrix.size - 1
        var j = 0
        while (i >= 0 && j < matrix[0].size) {
            when {
                matrix[i][j] == target -> {
                    return true
                }
                matrix[i][j] > target -> {
                    i--
                }
                matrix[i][j] < target -> {
                    j++
                }
            }
        }
        return false
    }
}