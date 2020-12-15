package com.kowksiuwang.everythingaboutandroid.leetcode.lc_0204_count_primes;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 统计所有小于非负整数 n 的质数的数量。
 * Created by kwoksiuwang on 12/3/20!!!
 */
public class Solution {

    public int countPrimes(int n) {
        return 0;
    }

    @Test
    public void test() {
        Assert.assertEquals(countPrimes4(10), 4);
    }

    public boolean isPrimes(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 从小到大遍历，如果一个数是质数，那他的倍数
     *
     * @param n
     * @return
     */
    public int countPrimes2(int n) {
        int[] isPrime = new int[n];
        Arrays.fill(isPrime, 1);
        int ans = 0;
        for (int i = 2; i < n; ++i) {
            if (isPrime[i] == 1) {
                ans += 1;
                if ((long) i * i < n) {
                    for (int j = i * i; j < n; j += i) {
                        isPrime[j] = 0;
                    }
                }
            }
        }
        return ans;
    }

    public int countPrimes3(int n) {
        List<Integer> primes = new ArrayList<Integer>();
        int[] isPrime = new int[n];
        Arrays.fill(isPrime, 1);
        for (int i = 2; i < n; ++i) {
            if (isPrime[i] == 1) {
                primes.add(i);
            }
            for (int j = 0; j < primes.size() && i * primes.get(j) < n; ++j) {
                isPrime[i * primes.get(j)] = 0;
                if (i % primes.get(j) == 0) {
                    break;
                }
            }
        }
        return primes.size();
    }

    public int countPrimes4(int n) {
        List<Integer> result = new ArrayList<>();
        int[] isPrime = new int[n];
        Arrays.fill(isPrime, 1);
        for (int i = 2; i < n; i++) {
            if (isPrime[i] == 1) {
                result.add(i);
            }
            for (int j = 0; j < result.size() && (result.get(j) * i) < n; j++) {
                isPrime[result.get(j) * i] = 0;
                if (i % result.get(j) == 0) {
                    break;
                }
            }

        }
        return result.size();
    }

    public int countPrimes4P(int n) {
//        List<Integer> result = new ArrayList<>();
        int[] result = new int[n];
        int resultLength = 0;

        int[] isPrime = new int[n];
        Arrays.fill(isPrime, 1);
        for (int i = 2; i < n; i++) {
            if (isPrime[i] == 1) {
                result[resultLength++] = i;
            }
            for (int j = 0; j < resultLength && (result[j] * i) < n; j++) {
                isPrime[result[j] * i] = 0;
                if (i % result[j] == 0) {
                    break;
                }
            }

        }
        return resultLength;
    }

    public int countPrimes5(int n) {
        boolean[] isPrim = new boolean[n];
        Arrays.fill(isPrim, true);
        // 从 2 开始枚举到 sqrt(n)。
        for (int i = 2; i * i < n; i++) {
            // 如果当前是素数
            if (isPrim[i]) {
                // 就把从 i*i 开始，i 的所有倍数都设置为 false。
                for (int j = i * i; j < n; j += i) {
                    isPrim[j] = false;
                }
            }
        }

        // 计数
        int cnt = 0;
        for (int i = 2; i < n; i++) {
            if (isPrim[i]) {
                cnt++;
            }
        }
        return cnt;
    }

    public int countPrimes7(int n) {
        int count = 0;
        int[] array = new int[n]; //建立数组，默认整型数组元素都是0.
        // 从2开始进行比较，最后数组元素是0时，为质数，为1时为合数。
        for (int i = 2; i <= (int) Math.sqrt(n); i++) {
            if (array[i] == 0) {
                for (int j = i * i; j < n; j += i) {
                    array[j] = 1;
                }
            }
        }
        for (int i = 2; i < n; i++) {
            if (array[i] == 0) count++;
        }
        return count;
    }
}
