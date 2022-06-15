package test.test;

import java.util.*;
import java.util.stream.Collectors;
/**
 * 重点整理的题目：
 * 172 188 189 190 198 213（打家劫舍2题）202（分析） 204 215
 */
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
     * 172
     * https://blog.csdn.net/i_am_bird/article/details/78769566
     *
     * 从1到100的数字有多少个5？因为100÷5 = 20，所以在1到100之间有5个20的倍数。
     * 但是等等，实际上25是5×5，因此25的每个倍数具有5的额外因子，例如25×4 = 100，引入了额外的零。
     * 那么，我们需要知道有多少25的倍数在1到100之间呢？由于100÷25 = 4，因此在1到100之间有四个倍数25。最后，我们得到20 + 4 = 24的尾数为100！
     * 上面的例子告诉我们，我们需要关心5,5×5,5×5×5,5×5×5×5 ....
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
        if (k == 0 || prices == null || prices.length == 0) {
            return 0;
        }
        //统计所有正利润的区间以及利润值
        int n = prices.length;
        /**
         * buy[i][j]:到第i天为止至多进行j次交易，且第j次交易是买入（或者维持买状态）
         */
        int[][] buy = new int[n][k+1];
        /**
         * sold[i][j]:到第i天为止至多进行j次交易，且第j次交易是卖出（或者维持卖出状态）
         */
        int[][] sold = new int[n][k+1];
        for (int j = 1; j <= k; j++) {
            buy[0][j] = -prices[0];
        }
        for (int i = 1; i< n; i++) {
            for (int j = 1; j <= k; j++) {
                buy[i][j] = Math.max(buy[i-1][j], sold[i-1][j-1] - prices[i]);
                sold[i][j] = Math.max(sold[i-1][j], buy[i-1][j] + prices[i]);
            }
        }
        return sold[n-1][k];
    }

    /**
     * 189
     * 该方法基于如下的事实：当我们将数组的元素向右移动 k 次后，尾部 kmod n个元素会移动至数组头部，其余元素向后移动 k mod n 个位置。
     * 该方法为数组的翻转：
     * 我们可以先将所有元素翻转，这样尾部的 k mod n 个元素就被移至数组头部，
     * 然后我们再翻转 [0, k mod n-1]区间的元素
     * 最后翻转[k mod n, n-1]区间的元素即能得到最后的答案。
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        rotate(nums, 0, n-1);
        rotate(nums, 0, k-1);
        rotate(nums, k, n-1);
        System.out.println();
    }

    /**
     * 翻转 nums[l...r]
     * @param nums
     * @param l
     * @param r
     */
    public void rotate(int[] nums, int l, int r) {
        int i = l;
        int j = r;
        while (i < j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
            i ++;
            j --;
        }
    }

    //190
    public int reverseBits(int n) {
        int rev = 0;
        for (int i = 0; i < 32 && n != 0; ++i) {
            rev |= (n & 1) << (31 - i);
            n >>>= 1;
        }
        return rev;
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
        boolean [] cnt = new boolean[n];
        cnt[0] = true;
        cnt[1] = true;
        for (int i = 2; i* i < n; i++) {
            if (!cnt[i]) {
                //i的倍数均不是质数
                for (int j = i *2; j< n; j+=i) {
                    cnt[j] = true;
                }
            }
        }

        int res = 0;
        //从1开始统计质数的个数
        for (int i =1; i< n; i++) {
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
            if (map.get(s.charAt(i)) == null && map.get(t.charAt(i)) == null) {
                map.put(s.charAt(i), t.charAt(i));
                map.put(t.charAt(i), s.charAt(i));
            } else if ((map.get(s.charAt(i)) != null && map.get(t.charAt(i)) == null)
                    || (map.get(s.charAt(i)) == null && map.get(t.charAt(i)) != null)
                    || !map.get(s.charAt(i)).equals(map.get(s.charAt(i)))) {
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
        if (n == 1) {
            return nums[0];
        }
        //dp[i] 表示偷到第i家时所能获得的最大收益值
        int[] dp =new int[n];
        //假设偷第0家
        dp[0] = nums[0];
        dp[1] = nums[0];
        //最后一家dp[n-1]不能偷
        for (int i = 2; i< n-1 ; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
        }
        int max = dp[n-2];
        //重置dp
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
        int[] p = {1,2,3,4,5,6,7};
        int k = 3;
        //test_25.rotate(p, k);
        boolean isomorphic = test_25.isIsomorphic("badc", "baba");
        System.out.println();
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
        //int i = test_25.rob2(p);
        //int kthLargest = test_25.findKthLargest(p, k);
        System.out.println();

    }
}
