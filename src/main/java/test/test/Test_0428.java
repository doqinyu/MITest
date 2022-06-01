package test.test;

import java.util.*;
/**
 * 重点整理的题目：
 * 409 416 621 647
 */
public class Test_0428 {
    //409
    public int longestPalindrome(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i< s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        //累加偶数
        int res = 0;

        for (Map.Entry<Character, Integer> entry: map.entrySet()) {
            //偶数全加上，奇数加entry.getValue()/2 * 2个
            res += entry.getValue()/2 * 2;

            //如果出现过奇数，加一次，作为回文的核心
            if (entry.getValue() % 2  == 1 && res % 2 == 0) {
                res ++;
            }
        }

        return res ;
    }

    //416 todo 动态规划算法优化解法二
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return false;
        }
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
        //[9,5]
        if (nums[0] <= sum) {
            dp[0][nums[0]] = true;
        }
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i< n; i++) {
            for (int j = 1; j <= sum; j++) {
                //不选择nums[i]
                dp[i][j] = dp[i-1][j];
                //可以选择nums[i]
                if ( j >= nums[i]) {
                    dp[i][j] = dp[i][j] | dp[i-1][j-nums[i]];
                }
            }
        }

        return dp[n-1][sum];
    }

    //416 动态规划空间优化解法三
    public boolean canPartition3(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return false;
        }
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
        for (int i = 0; i< n; i++) {
            //从后往前覆盖(从前往后可能会一个数字使用多次)
            for (int j = sum; j >= nums[i]; j--) {
                dp[j] = dp[j] | dp[j-nums[i]];

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
        if (nums.length == 1) {
            return 1;
        }
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
        if ((m * n != r * c) || (m == r && n == c)) {
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
        int[][]dp = new int[m+1][n+1];
        //初始化第一行
        for (int j = 1; j<= n; j++) {
            dp[0][j] = j;
        }
        //初始化第一列
        for (int i = 1; i<= m; i++) {
            dp[i][0] = i;
        }

        for (int i = 1; i<= m; i++) {
            for (int j = 1; j<= n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) +1;
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1]);
                }
            }
        }
        return dp[m][n];
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

    //645
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

    /**
     * todo [重要]5 Manacher 算法求解最长回文子串的长度
     * @param s
     * @return
     */
    public String longestPalindrome2(String s) {
        int start = 0, end = -1;
        StringBuffer t = new StringBuffer("#");
        for (int i = 0; i < s.length(); ++i) {
            t.append(s.charAt(i));
            t.append('#');
        }
        s = t.toString();

        List<Integer> arm_len = new ArrayList<Integer>();
        int right = -1, j = -1;
        for (int i = 0; i < s.length(); ++i) {
            int cur_arm_len;
            if (right >= i) {
                //找到 i 关于中心 j 的对称点i_sym
                int i_sym = j * 2 - i;
                // 计算不用重复计算的长度。小于 right - i 是为了保证 不重复计算的范围在 j 的有效范围内
                int min_arm_len = Math.min(arm_len.get(i_sym), right - i);
                //从 i - min_arm_len 开始扩展比较是否是回文子串,返回当前中心的最长臂长
                cur_arm_len = expand(s, i - min_arm_len, i + min_arm_len);
            } else {
                cur_arm_len = expand(s, i, i);
            }
            //arm_len[i] = 以 i 为中心的臂长。由于 i 是递增的，所以直接往list 里面添加值即可
            arm_len.add(cur_arm_len);
            //扩展后的最右边界更新
            if (i + cur_arm_len > right) {
                j = i;
                right = i + cur_arm_len;
            }
            //扩展后的臂长更新,更新最长回文子串的起始位置
            if (cur_arm_len * 2 + 1 > end - start) {
                start = i - cur_arm_len;
                end = i + cur_arm_len;
            }
        }

        StringBuffer ans = new StringBuffer();
        for (int i = start; i <= end; ++i) {
            if (s.charAt(i) != '#') {
                ans.append(s.charAt(i));
            }
        }
        return ans.toString();
    }

    public int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return (right - left - 2) / 2;
    }


    /**
     * 647
     * Manacher 算法
     * todo 需要注意的是不能让下标越界，有一个很简单的办法，就是在开头加一个 \$$，并在结尾加一个 !!，这样开头和结尾的两个字符一定不相等，循环就可以在这里终止。
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        int n = s.length();
        StringBuffer t = new StringBuffer("$#");
        for (int i = 0; i < n; ++i) {
            t.append(s.charAt(i));
            t.append('#');
        }
        n = t.length();
        t.append("!");
        //f[i] 表示以 t[i]为中心的最长臂长(包含#)
        int[] f = new int[n];
        /**
         * iMax : 中心
         * rMax : 以 iMax为中心的右边界
         */
        int iMax = 0, rMax = 0, ans = 0;
        for (int i = 1; i < n; ++i) {
            // 初始化 f[i]
            f[i] = i <= rMax ? Math.min(rMax - i + 1, f[2 * iMax - i]) : 1;
            // 中心拓展
            while (t.charAt(i + f[i]) == t.charAt(i - f[i])) {
                ++f[i];
            }
            // 动态维护 iMax 和 rMax
            if (i + f[i] - 1 > rMax) {
                iMax = i;
                rMax = i + f[i] - 1;
            }
            // 统计答案, 当前贡献为 f[i] / 2 上取整(因为 f[i] 计算的臂长包含填充字符，所以要/2)
            ans += f[i] / 2;
        }

        return ans;
    }

    public static void main(String[] args) {
        Test_0428 test_0428 = new Test_0428();
        String s = "abccccdd";
        //int i = test_0428.longestPalindrome(s);
        int[] p = {2,2,3,5};
        //int i = test_0428.minDistance("a", "b");
        int abc = test_0428.countSubstrings("abc");
        //boolean b = test_0428.canPartition3(p);
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
        //int[] p = {1,1};
        //int[] errorNums = test_0428.findErrorNums(p);
        //String s = "aaa";
        //int i = test_0428.countSubstrings(s);
        System.out.println();
    }
}
