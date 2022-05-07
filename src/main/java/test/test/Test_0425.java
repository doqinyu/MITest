package test.test;

import java.util.*;
import java.util.stream.Collectors;

public class Test_0425 {
    //171
    public int titleToNumber(String columnTitle) {
        int result = 0;
        for (int i = columnTitle.length() - 1; i >= 0; i--) {
            result = (int) (result + (columnTitle.charAt(i) - 64) * Math.pow(26, columnTitle.length() - 1 - i));
        }
        return result;
    }

    /**
     * todo 172 给定一个整数 n ，返回 n! 结果中尾随零的数量。
     *
     * @param n
     * @return
     */
    public int trailingZeroes(int n) {
        int sum = 0;
        while (n >= 5) {
            n = n/5;
            sum += n;
        }

        return sum;
    }

    /**
     * 188
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit(int k, int[] prices) {
        //统计所有正利润的区间以及利润值
        int n = prices.length;
        int[] dp = new int[n];
        List<Integer> profit = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            dp[i] = prices[i] - prices[i - 1];
        }
        //将连续的正利润区间叠加
        int start = 1;
        int end = 1;
        while (end < n) {
            while (start < n && dp[start] <= 0) {
                start++;
            }

            if (start >= n) {
                break;
            }
            int sum = dp[start];
            end = start + 1;
            while (end < n && dp[end] > 0) {
                sum += dp[end];
                end++;
            }
            //将连续正利润的区间叠加，计算区间总利润sum
            profit.add(sum);
            start = end;
        }

        //将所有的利润倒序排序
        profit = profit.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //累加利润最高的前n个区间
        for (int i =1 ;i < Math.min(k, profit.size()) ; i++) {
            profit.set(0, profit.get(0) + profit.get(i));
        }
        return profit.get(0);
    }

    //189
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        int tmp = nums[0];
        int target = 0;
        do {
            int end = target;
            if (end < k) {
                end = target + n;
            }
            int next = end - k;
            nums[target] = nums[next];
            target = next;
        }while (target != 0);

        nums[k] = tmp;
        System.out.println();
    }

    //190
    public int reverseBits(int n) {
        int ret = 0;
        //首先判断符号位，如果结果为负数
        if ((n & 1) == 1) {
            ret = 1;
        }
        //从下一位开始计数
        n = n >> 1;

        for (int i = 0; i< 31; i++) {
            ret = ret <<1;
            ret = ret | (n & 1);
            n = n >>1;
        }
        return ret;
    }


    //191
    public int hammingWeight(int n) {
        int cnt = 0;
        for (int i = 0; i< 32; i++) {
            if ((n & 1) == 1) {
                cnt ++;
            }
            n = n >> 1;
        }
        return cnt;
    }

    //198
    public int rob(int[] nums) {
        if (null == nums || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] =  Math.max(nums[0], nums[1]);
        for (int i = 2; i< n; i++) {
            dp[i] = Math.max(dp[i-2] + nums[i], dp[i-1]);
        }

        return dp[n-1];
    }

    //200
    public int numIslands(char[][] grid) {
        int cnt = 0;
        int[][]dp = new int[grid.length][grid[0].length];
        for (int i = 0; i< grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && dp[i][j] == 0) {
                    dfs(grid,dp, i,j);
                    cnt ++;
                }
            }
        }
        return cnt;
    }

    public void dfs(char[][] grid, int[][]dp , int i, int j) {
        if ( i< 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == '0' || dp[i][j] == 1) {
            return;
        }
        dp[i][j] = 1;
        dfs(grid,  dp,i-1,j);
        dfs(grid,  dp, i+1,j);
        dfs(grid,  dp, i,j-1);
        dfs(grid,  dp, i,j+1);
    }

    //202
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        while (!set.contains(n) && n != 1) {
            set.add(n);
            int r = 0;
            while (n != 0) {
                int mod = n % 10;
                r += mod * mod;
                n = n /10;
            }
            n = r;
        }

        if (n == 1) {
            return true;
        }
        return false;
    }

    //204
    public int countPrimes(int n) {
        if (n < 2) {
            return 0;
        }
        //cnt[i]=true: i不是质数 false:i是质数
        boolean [] cnt = new boolean[n+1];
        cnt[0] = true;
        cnt[1] = true;
        for (int i = 2; i* i <= n; i++) {
            if (!cnt[i]) {
                //i的倍数均不是质数
                for (int j = i *2; j<= n; j+=i) {
                    cnt[j] = true;
                }
            }
        }

        int res = 0;
        //从1开始统计质数的个数
        for (int i =1; i<= n; i++) {
            if (!cnt[i]) {
                res ++;
            }
        }

        return res;
    }



    //205
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Character> map = new HashMap<>();
        for (int i =0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) == null) {
                map.put(s.charAt(i), t.charAt(i));
            } else if (map.get(s.charAt(i)) != t.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 213
     * @param nums
     * @return
     */
    public int rob2(int[] nums) {
        int n = nums.length;
        //dp[i] 表示偷到第i家时所能获得的最大收益值
        int[] dp =new int[n];
        //假设偷第0家
        dp[0] = nums[0];
        dp[1] = nums[0];
        //最后一家dp[n-1]不能偷
        for (int i = 2; i< n-1 ; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
        }
        dp[n-1] = dp[n-2];
        int max = dp[n-1];
        for (int i = 0; i< n; i++) {
            dp[i] = 0;
        }
        //假设不偷第0家
        dp[1] = nums[1];
        //最后一家dp[n-1]能偷
        for (int i = 2; i< n ; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
        }
        max = Math.max(max, dp[n-1]);
        return max;
    }

    //215
    public int findKthLargest(int[] nums, int k) {
        //建立大小为k的小根堆
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
//        //大根堆
//        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2 - o1;
//            }
//        });
        for (int i = 0; i< k; i++) {
            priorityQueue.add(nums[i]);
        }

        for (int i = k; i< nums.length; i ++) {
            if (nums[i] > priorityQueue.peek()) {
                priorityQueue.poll();
                priorityQueue.add(nums[i]);
            }
        }
        return priorityQueue.peek();
    }



    public static void main(String[] args) {
        Test_0425 test_25 = new Test_0425();
        String s = "ZY";
        //int i = test_25.titleToNumber(s);
        //int k = 3;
        //int i = test_25.maxProfit(k, p);
        //test_25.rotate(p ,k );
        //int n = -3;
        //int i = test_25.trailingZeroes(n);
        //int i = test_25.reverseBits(n);
        //int i = test_25.hammingWeight(n);
        //int rob = test_25.rob(p);
//        char[][] grid = {
//                {'1','1','0','0','0'},
//                {'1','1','0','0','0'},
//                {'0','0','1','0','0'},
//                {'0','0','0','1','1'}
//        };
        //int i = test_25.numIslands(grid);
        int n = 10;
        //boolean happy = test_25.isHappy(n);
        //int i = test_25.countPrimes(n);
        int [] p = {3,2,1,5,6,4};
        int k =2;
        //int i = test_25.rob2(p);
        int kthLargest = test_25.findKthLargest(p, k);
        System.out.println();

    }
}
