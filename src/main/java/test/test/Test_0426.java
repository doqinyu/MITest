package test.test;

import java.util.HashMap;
import java.util.Map;

public class Test_0426 {
    //240
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int i = 0;
        int j = 0;
        while (i< m && j < n) {
            if (matrix[i][j] == target) {
                return true;
            }

            if (matrix[i][j] > target) {
                return false;
            }
            //如果到最后一行
            if (i == m -1) {
                j = j+1;
            } else if (j == n-1) {
                i = i+1;
            } else {
                //如果右方的值 大于 下方的值
                if (matrix[i][j+1] > matrix[i+1][j]) {
                    if (matrix[i+1][j] > target) {
                        return false;
                    }

                    if (matrix[i][j+1] < target) {
                        j = j+1;
                    } else {
                        i = i +1;
                    }
                } else {
                    //如果下方的值 大于 右方的值
                    if (matrix[i][j+1] > target) {
                        return false;
                    }

                    if (matrix[i+1][j] < target) {
                        i = i +1;
                    } else {
                        j = j +1;
                    }
                }
            }
        }

        return false;
    }

    //242
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> ms = new HashMap<>();
        Map<Character, Integer> mt = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {

            Integer cnt = ms.get(s.charAt(i));
            if (cnt == null) {
                ms.put(s.charAt(i), 1);
            } else {
                ms.put(s.charAt(i), cnt + 1);
            }
            cnt = mt.get(s.charAt(i));
            if (cnt == null) {
                mt.put(s.charAt(i), 1);
            } else {
                mt.put(s.charAt(i), cnt + 1);
            }
        }

        for (Map.Entry<Character, Integer> entry: ms.entrySet()) {
            if (!mt.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }


    //270
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
        int n  = nums.length;
        int r1 = 0;
        int r2 = 0;
        for (int i = 0; i< n; i++) {
            r1 = r1 ^ nums[i];
            if (i != n-1) {
                r2 = r2 ^ (i + 1);
            }
        }

        return r1 ^ r2;
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

        int target = 31;
        int n = 45;
        //int i = test_26.numSquares(n);
        int[] p = {2};
        //test_26.moveZeroes(p);
        //int duplicate = test_26.findDuplicate(p);
        //int i = test_26.lengthOfLIS(p);
        //int i = test_26.coinChange(p, n);
        boolean powerOfThree = test_26.isPowerOfThree(n);
        System.out.println();
    }
}
