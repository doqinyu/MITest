package test.test;

import java.util.HashMap;
import java.util.Map;
/**
 * 重点整理的题目：
 * 240 179 287 300 322（多个背包问题）
 */
public class Test_0426 {
    //240
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int i = 0;
        int j = n-1;
        //z 字解法
        while (i< m && j >= 0) {
            if (matrix[i][j] == target) {
                return  true;
            }

            if (target > matrix[i][j]) {
                i++;
            } else {
                j--;
            }
        }
        return false;
    }

    //242
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] snt = new int[26];
        int[] tnt = new int[26];
        for (int i = 0; i < s.length(); i++) {

            snt[s.charAt(i) - 'a'] ++;
            tnt[t.charAt(i) - 'a'] ++;
        }
        for (int i = 0; i< 26; i++) {
            if (snt[i] != tnt[i]) {
                return false;
            }
        }
        return true;
    }


    //279
    public int numSquares(int n) {
        if (n < 4) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;
        for (int i = 4; i <= n; i++) {
            dp[i] = dp[i-1] +1;
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }

    //283
    public void moveZeroes(int[] nums) {
        int n = nums.length;
        //移动后的数组下标
        int i = 0;
        //移动前的数组下标
        int j = 0;
        while (j < n) {
            //非0数赋值
            if (nums[j] != 0) {
                nums[i] = nums[j];
                i ++;

            }
            j ++;
        }
        // 0 后移
        while (i < n) {
            nums[i ++] = 0;
        }
    }

    //287
    public int findDuplicate(int[] nums) {
        for (int i = 0; i< nums.length; i++) {
            int n = Math.abs(nums[i]);
            if (nums[n - 1] < 0) {
                return n;
            }
            nums[n - 1] = -nums[n - 1];
        }
        return -1;
    }

    //300
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        //dp[i]表示以nums[i]结尾的最长递增子序列
        dp[0] = 1;
        int max = dp[0];
        for (int i = 1; i < n ;i ++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                //找到离nums[i]最近的且比nums[i]小的
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            max = Math.max(max, dp[i]);
        }

        return max;
    }


    //322
    public int coinChange(int[] coins, int amount) {
        //dp[i]:表示兑换钱数为i时所需的最少零钱数
        int [] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (i >= coins[j] && dp[i - coins[j]] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        if (dp[amount] == Integer.MAX_VALUE) {
            return -1;
        }
        return dp[amount];
    }

    //326
    public boolean isPowerOfThree(int n) {
        if (n <= 0) {
            return false;
        }

        while (n > 0) {
            if (n == 1) {
                return true;
            }

            if (n % 3 != 0) {
                return false;
            }
            n = n/3;
            
        }
        return true;
    }
    
    public static void main(String[] args) {
        Test_0426 test_26 = new Test_0426();
        int[][] m = {
                {1,4,7,11,15},
                {2,5,8,12,19},
                {3,6,9,16,22},
                {10,13,14,17,24},
                {18,21,23,26,30}
        };

        int target = 5;
        int n = 12;
        //boolean b = test_26.searchMatrix(m, target);
        //int i = test_26.numSquares(n);
        int[] p = {1,3,4,2,2};
        //test_26.moveZeroes(p);
        int duplicate = test_26.findDuplicate(p);
        //int i = test_26.lengthOfLIS(p);
        //int i = test_26.coinChange(p, n);
        //boolean powerOfThree = test_26.isPowerOfThree(n);
        System.out.println();
    }
}
