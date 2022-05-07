package test.test;

import java.util.*;

public class Test_0428 {
    //409
    public int longestPalindrome(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i< s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        //累加偶数
        int res = 0;
        //最大的奇数
        int maxOdd = 0;

        for (Map.Entry<Character, Integer> entry: map.entrySet()) {
            //偶数直接加
            if (entry.getValue() % 2 == 0) {
                res += entry.getValue();
            } else {
                //奇数取最大
                maxOdd = Math.max(maxOdd, entry.getValue());
            }
        }

        return res + maxOdd;
    }

    //416
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        //如果数组的和为奇数
        if (sum % 2 != 0) {
            return false;
        }
        sum = sum / 2;
        //dp[i] 表示是否使用nums[i];
        int[] dp = new int[n];
        boolean search = search(nums, dp, sum);
        return search;
    }

    public boolean search(int[] nums,int[] dp, int target) {
        if (target == 0) {
            return true;
        }

        for (int i = 0; i< nums.length; i++) {
            if (dp[i] == 0 && target >= nums[i]) {
                dp[i] = 1;
                boolean succ = search(nums, dp, target - nums[i]);
                if (succ) {
                    return succ;
                }
                //reset
                dp[i] = 0;
            }
        }
        return false;
    }

    //416 动态规划算法优化解法二
    public boolean canPartition2(int[] nums) {
        int n = nums.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        //如果数组的和为奇数
        if (sum % 2 != 0) {
            return false;
        }
        sum = sum / 2;
        //dp[i] 表示使用nums[0...i]是否可以凑成和为j的数;
        boolean [][] dp = new boolean [n][sum + 1];
        dp[0][nums[0]] = true;
        for (int i = 0; i< n; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i< n; i++) {
            for (int j = 1; j <= sum; j++) {
                //不选择nums[i]
                dp[i][j] = dp[i-1][j];
                //可以选择nums[i]
                if ( j >= nums[i]) {
                    dp[i][j] = dp[i][j] || dp[i][j-nums[i]];
                }
            }
        }

        return dp[n-1][sum];
    }

    //416 动态规划空间优化解法三
    public boolean canPartition3(int[] nums) {
        int n = nums.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        //如果数组的和为奇数
        if (sum % 2 != 0) {
            return false;
        }
        sum = sum / 2;
        //dp[i] 表示使用nums[0...i]是否可以凑成和为j的数;
        boolean [] dp = new boolean [sum + 1];
        dp[0] = true;
        dp[nums[0]] = true;
        for (int i = 1; i< n; i++) {
            for (int j = 1; j <= sum; j++) {
                //可以选择nums[i]
                if ( j >= nums[i]) {
                    dp[j] = dp[j] || dp[j-nums[i]];
                }
            }
        }
        return dp[sum];
    }


    /**
     * 442 https://www.jianshu.com/p/3cdeba0b71be
     * 遍历整个数组，将每一个数字视为数组位置信息，再将每一个位置对应的数字反转为负数，相当于做一个标识，表明这个数字对应的位置，已经有数字占用了，
     * 下一次再遇到这个数字如果发现是负数就表明已经出现过。
     * @param nums
     * @return
     */
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i =0 ;i < nums.length; i++) {
            //当前出现的数字是a
            int a = Math.abs(nums[i]);
            //将nums[a-1]处的数字变成负数，表示已经存在过
            //如果nums[a-1]为负数，表示之前出现过，现在是第二次出现
            if (nums[a-1] < 0) {
                res.add(a);
            } else {
                nums[a-1] = nums[a-1]* (-1);
            }
        }
        return res;
    }

    //485
    public int findMaxConsecutiveOnes(int[] nums) {
        int res = 0;
        int c = 0;
        for (int i = 0; i< nums.length; i++) {
            if (nums[i] == 1) {
                c++;
            } else {
                res = Math.max(res, c);
                c = 0;
            }
        }
        res = Math.max(res, c);
        return res;
    }

    //565
    public int arrayNesting(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]> 0) {
                nums[i] = - nums[i];
                int cnt = dfs(nums, i, -nums[i], 1);
                res = Math.max(res, cnt);
            }
        }
        return res;
    }
    public int dfs(int[] nums, int head, int next, int cnt) {
        if (next == head) {
            return cnt;
        }
        nums[next] = -nums[next];
        return dfs(nums, head, -nums[next], cnt + 1);
    }

    //566
    public int[][] matrixReshape(int[][] mat, int r, int c) {
        int m = mat.length;
        int n = mat[0].length;
        if (m * n != r * c) {
            return null;
        }
        if (m == r && n == c) {
            return mat;
        }

        int[][] p = new int[r][c];
        //数组下标
        int k = 0;
        //新数组下标索引
        while (k < m*n) {
            int i1 = k/n;
            int j1 = k%n;

            int i2 = k/c;
            int j2 = k%c;
            p[i2][j2] = mat[i1][j1];
             k++;
        }

        return p;
    }

    //583
    public int minDistance(String word1, String word2) {
        if (word1.equals("")) {
            return word2.length();
        }
        if (word2.equals("")) {
            return word1.length();
        }

        int m = word1.length();
        int n = word2.length();
        /**
         * dp[i][j] 表示将word1[0..i]与word2[0..j]变的相同所需的最少步骤
         */
        int[][]dp = new int[m][n];
        dp[0][0] = word1.charAt(0) == word2.charAt(0)? 0:2;
        //初始化第一行
        for (int j = 1; j< n; j++) {
            //能通过dp[0][j-1] + 1转化或者全删除(j+2)从而达到一致
            dp[0][j] = Math.min(dp[0][j-1] + 1, j + 2);
            //如果比较的字符相等
            if (word1.charAt(0) == word2.charAt(j)) {
                dp[0][j] = Math.min(dp[0][j],j);
            }
        }
        //初始化第一列
        for (int i = 1; i< m; i++) {
            //能通过dp[i][0] + 1转化或者全删除(i+2)从而达到一致
            dp[i][0] = Math.min(dp[i-1][0] + 1, i + 2);
            //如果比较的字符相等
            if (word1.charAt(i) == word2.charAt(0)) {
                dp[i][0] = Math.min(dp[i][0],i);
            }
        }

        for (int i = 1; i< m; i++) {
            for (int j = 1; j< n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) +1;
                if (word1.charAt(i) == word2.charAt(j)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1]);
                }
            }
        }
        return dp[m-1][n-1];
    }



    /**
     * 621
     * https://blog.csdn.net/qq_27007509/article/details/110727363
     * 设最多的任务需要执行 m 次，不考虑最后一轮的情况，前面一共需要 ( m - 1) * (n + 1) 的时间
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval(char[] tasks, int n) {
        //首先统计每个任务出现的次数
        int[] cnt = new int[26];
        for (int i = 0; i< tasks.length; i++) {
            cnt[tasks[i] - 'A'] ++;
        }
        Arrays.sort(cnt);
        //出现次数cnt[i]最多的任务有k个
        int k = 1;
        for (int i = 24; i>=0; i--) {
            if (cnt[i] == cnt[i+1]) {
                k++;
            } else {
                break;
            }
        }

        //如果最后一轮中出现过「待命」(出现次数最多的才会待命)
        int max = (cnt[25] - 1) * (n+1) + k;
        //如果最后一轮没有出现「待命」
        max = Math.max(max, tasks.length);
        return max;
    }

    //645 todo test
    public int[] findErrorNums(int[] nums) {
        int[] res = new int[2];
        for (int i = 0; i< nums.length; i++) {
            //如果 nums[i] + 1 之前访问过
            int a = Math.abs(nums[i]);
            if (nums[a-1] < 0) {
                res[0] = a;
            }
            //令 nums[a-1] 变成负数，标记a 出现过
            nums[a-1] = - Math.abs(nums[a-1]);
        }

        for (int i = 0; i< nums.length; i++) {
            //如果i+1 没有出现付哦
            if (nums[i] > 0) {
                res[1] = i + 1;
                break;
            }
        }
        return res;
    }

    //647 todo test
    public int countSubstrings(String s) {
        int n = s.length();
        //dp[i]表示s[0..i]的子串的 回文子串 的数目
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 1; i< n; i++) {
            //dp[i]的回文子串包含dp[i-1]的回文子串
            dp[i] = dp[i-1];
            int cnt = 0;
            //s[i]与s[j]构成回文子串的数量有cnt个
            for (int j = 0; j < i; j++) {
                if (s.charAt(j) == s.charAt(i)) {
                    cnt++;
                }
            }
            //加1代表s.charAt(i)构成回文子串
            dp[i] += cnt + 1;
        }
        return dp[n-1];
    }

    public static void main(String[] args) {
        Test_0428 test_0428 = new Test_0428();
        //String s = "bb";
        //int i = test_0428.longestPalindrome(s);
        //int[] p = {5,4,0,3,1,6,2};
        //test_0428.canPartition(p);
        //boolean b = test_0428.canPartition2(p);
        //boolean b = test_0428.canPartition3(p);
        //List<Integer> duplicates = test_0428.findDuplicates(p);
        //int maxConsecutiveOnes = test_0428.findMaxConsecutiveOnes(p);
        //int i = test_0428.arrayNesting(p);
//        int[][] p = {
//                {1,2},
//                {3,4}
//        };
//        int r = 1;
//        int c = 4;
//        int[][] ints = test_0428.matrixReshape(p, r, c);
        char[] task = {'A','A','A','B','B','B'};
        int n = 2;
        //int i = test_0428.leastInterval(task, n);
        int[] p = {1,1};
        //int[] errorNums = test_0428.findErrorNums(p);
        String s = "aaa";
        int i = test_0428.countSubstrings(s);
        System.out.println();
    }
}
