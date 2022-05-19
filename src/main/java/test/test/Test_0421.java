package test.test;

import java.util.*;
/**
 * 重点整理的题目：
 * 66 69 88
 */
public class Test_0421 {

    //62
    public int uniquePaths(int m, int n) {
        /**
         * dp[i][j] 表示从(0,0)开始到达(i,j)处的路径条数
         * 机器人每次只能向下或者向右移动一步
         */
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j< n; j++) {
            dp[0][j] = 1;
        }

        for (int i = 1; i < m ; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }

    /**
     * 66
     * 如果 digits的末尾没有 9，例如 [1, 2, 3]，那么我们直接将末尾的数加一，得到 [1, 2, 4]并返回；
     * 如果 digits 的末尾有若干个 99，例如 [1, 2, 3, 9, 9]，那么我们只需要找出从末尾开始的第一个不为 9的元素，即 3，将该元素加一，得到 [1, 2, 4, 9, 9]。随后将末尾的 99 全部置零，得到 [1, 2, 4, 0, 0]并返回。
     *
     如果 digits 的所有元素都是 9，例如 [9, 9, 9, 9, 9]，那么答案为 [1, 0, 0, 0, 0, 0]。我们只需要构造一个长度比digits 多 1的新数组，将首元素置为 1，其余元素置为 0即可。。

     * @param digits
     * @return
     */
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        //逆序遍历，找到第一个不为9的数字，加 1清0后返回
        for (int i = n-1; i>=0; i--) {
            if (digits[i]!=9) {
                digits[i]++;
                for (int j = i+1; j<n;j++) {
                    digits[j]=0;
                }
                return digits;
            }
        }
        //如果整个数组都是9
        int[] ans = new int[n+1];
        ans[0] = 1;
        return ans;
    }

    //69
    public int mySqrt(int x) {
        if (x <= 1) {
            return x;
        }
         int l = 1;
        int r = x/2;
        int mid = 0;
        int ans = 1;
        while (l <= r) {
            mid = (l + r)>>>1;
            //todo mid * mid 可能会越界int，因此需要转成(long)mid
            if ((long)mid * mid <=x) {
                ans = mid;
                l = mid +1;
            } else {
                //todo 不判断 (mid+1) * (mid+1) > x，是因为 (mid+1) * (mid+1)可能会超出long
                r = mid -1;
            }
        }

        return ans;
    }

    //70
    public int climbStairs(int n) {
        if ( n <= 2) {
            return n;
        }
        int [] dp = new int[n];
        dp[0] = 1;
        dp [1] = 2;
        for (int i = 2; i< n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n-1];
    }

    //72
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        //通过增加一个字符的方式使word1 -> word2 (前提是 word1 先转成 word2.substring(0, m-1))
        //通过替换一个字符的方式使word1 -> word2 (前提是 word1.substring(0,n-1) 先转成 word2.substring(0, m-1))
        //通过删除一个字符的方式使word1 -> word2 (前提是 word1.substring(0,n-1) 先转成 word2.substring(0, m))
        //多填充一列
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i<= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j<= n; j++) {
            dp[0][j] = j;
        }
        //dp[i][j] 表示把word1.substring(0,i) 变成 word2.substring(0,j)需要的最少的操作数
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = 0;
                //如果w1[i-1] == w2[j-1], 那么dp[i-1][j-1]无需额外的操作即可把w1变成w2
                if (word1.charAt(i-1) != word2.charAt(j-1)) {
                    cost = 1;
                }

                dp[i][j] = Math.min(dp[i-1][j] + 1, Math.min(dp[i][j-1] + 1, dp[i-1][j-1] + cost));
            }
        }
        return dp[m][n];
    }

    //73
    public void setZeroes(int[][] matrix) {
        //第一行是否有0
        boolean rowFlag = false;
        //第一列是否有0
        boolean colFlag = false;
        //判断第一行是否有0
        for (int j = 0; j< matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                rowFlag = true;
                break;
            }
        }
        //判断第一列是否有0
        for (int i = 0; i< matrix.length; i++) {
            if (matrix[i][0] == 0) {
                colFlag = true;
                break;
            }
        }

        //用原数组的第一行和第一列记录所有出现0的行和列
        for (int i = 1; i< matrix.length; i++) {
            for (int j =1; j< matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        //todo 遍历全数组。 m[0][j]=0可能是因为原数组的m[0][j]=0或者第j列出现过0.（[[1,0]]）
        for (int i = 1; i< matrix.length; i++) {
            for (int j = 1; j< matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        //如果第一行出现过0，那么第一行全为0
        if (rowFlag) {
            for (int j = 0; j< matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }
        //如果第一列出现过0，那么第一列全为0
        if (colFlag) {
            for (int i = 0;  i< matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }

    }

    public void setZeroes2(int[][] matrix) {
        //第一行是否有0
        boolean rowFlag = false;
        //第一列是否有0
        boolean colFlag = false;
        //判断第一行是否有0
        for (int j = 0; j< matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                rowFlag = true;
                break;
            }
        }
        //判断第一列是否有0
        for (int i = 0; i< matrix.length; i++) {
            if (matrix[i][0] == 0) {
                colFlag = true;
                break;
            }
        }

        //用原数组的第一行和第一列记录所有出现0的行和列
        for (int i = 1; i< matrix.length; i++) {
            for (int j =1; j< matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        //将标记行 = 0的所有列置为0
        for (int i = 1; i< matrix.length; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j< matrix[0].length; j++) {
                    matrix[i][j] = 0;
                }
            }
        }

        //将标记列 = 0的所有行置为0
        for (int j = 1; j< matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 1;  i< matrix.length; i++) {
                    matrix[i][j] = 0;
                }
            }
        }

        //如果第一行出现过0，那么第一行全为0
        if (rowFlag) {
            for (int j = 0; j< matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }
        //如果第一列出现过0，那么第一列全为0
        if (colFlag) {
            for (int i = 0;  i< matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }

    }


    //75
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) {
            return ;
        }
        int zero = 0;
        int one = 0;
        int two = 0;
        for (int i = 0 ; i< nums.length ; i++) {
            if (nums[i] == 0) {
                zero ++;
            } else if (nums[i] == 1) {
                one ++;
            } else {
                two ++;
            }
        }
        int k = 0;
        while (zero-- > 0) {
            nums[k++] = 0;
        }
        while (one-- > 0) {
            nums[k++] = 1;
        }
        while (two-- > 0) {
            nums[k++] = 2;
        }
    }

    //78
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> subsets = subsets(nums, 0, nums.length - 1);
        //最后加上一个空集
        List<Integer> empty = new ArrayList<>();
        subsets.add(empty);
        return subsets;
    }

    /**
     * 返回nums[start, end]的子集
     * @param nums
     * @param start
     * @param end
     * @return
     */
    public List<List<Integer>> subsets(int[] nums, int start, int end) {
        List<List<Integer>> result = new ArrayList<>();
        if (start == end && end == 0) {
            List<Integer> list = new ArrayList<>();
            list.add(nums[0]);
            result.add(list);
            return result;
        }

        List<List<Integer>> subsets = subsets(nums, start, end - 1);
        for (List<Integer> list: subsets) {
            List<Integer> old = new ArrayList<>();
            for (Integer num : list) {
                old.add(num);
            }
            result.addAll(Collections.singleton(old));
            list.add(nums[end]);
            result.addAll(Collections.singleton(list));
        }
        //加上本身
        List<Integer> mylist = new ArrayList<>();
        mylist.add(nums[end]);
        result.add(mylist);
        return result;
    }

    //79
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        //dp[i][j] 表示board[i][j]是否在某次搜索中已经标记过
        int[][]dp = new int[m][n];

        boolean dfs = dfs(board, word, dp, m, n);
        return dfs;
    }

    public boolean dfs (char[][] board, String word,int[][] dp, int m, int n) {
        for (int i = 0; i< m ; i++ ) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0) && dp[i][j] == 0) {
                        dp[i][j] = 1;
                        boolean found = dfs(board, word,1,  dp, i, j, m, n);
                        if (found == true) {
                            return true;
                        }
                        dp[i][j] = 0;
                }
            }
        }

        return false;
    }

    public boolean dfs (char[][] board, String word,int index, int[][] dp, int i, int j, int m, int n) {
        //下标越界
        if (i<0 || i>=m || j< 0||j>=n) {
            return false;
        }
        if (index == word.length()) {
            return true;
        }
        boolean found = false;
        //上面存在下一个字符
        if (i> 0 && dp[i-1][j] == 0 && board[i-1][j] == word.charAt(index)) {
            dp[i-1][j] = 1;
            found = dfs(board, word, index+1, dp, i-1, j,m,n);
            dp[i-1][j] = 0;
        }
        if (found) {
            return true;
        }

        //下面存在下一个字符
        if (i<m-1 && dp[i+1][j] == 0 && board[i+1][j] == word.charAt(index)) {
            dp[i+1][j] = 1;
            found = dfs(board, word, index+1, dp, i+1, j,m,n);
            dp[i+1][j] = 0;
        }
        if (found) {
            return true;
        }

        //左面存在下一个字符
        if (j>0 && dp[i][j-1] == 0 && board[i][j-1] == word.charAt(index)) {
            dp[i][j-1] = 1;
            found = dfs(board, word, index+1, dp, i, j-1,m,n);
            dp[i][j-1] = 0;
        }
        if (found) {
            return true;
        }

        //右面存在下一个字符
        if (j<n-1 && dp[i][j+1] == 0 && board[i][j+1] == word.charAt(index)) {
            dp[i][j+1] = 1;
            found = dfs(board, word, index+1, dp, i, j+1,m,n);
            dp[i][j+1] = 0;
        }

        return found;
    }

    //88
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) {
            return;
        }
        int i = m - 1;
        int j = n - 1;
        int k = m + n-1;
        while (i >=0 && j >=0) {
            if (nums1[i] >= nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        //todo nums2数组还存在未添加的数据时，将剩下的数据添加到nums1中
        while(j>=0) {
            nums1[k--] = nums2[j--];
        }
    }

    public static void main(String[] args) {
        Test_0421 test_21 = new Test_0421();
        char[][] board = {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };
//        char[][] board = {{'a'}};
        String word = "SEE";
        int[] nums1 = {0};
        int m = 0;
        int[] nums2 = {2};
        int n = 1;
        //int[][]ns = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        int[][]ns= {{1,1,1},{1,0,1},{1,1,1}};
        test_21.merge(nums1, m, nums2, n);
        //int i = test_21.mySqrt(2147395599);
        //test_21.setZeroes2(ns);
        //boolean exist = test_21.exist(board, word);

        System.out.println();
    }
}
