package test.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * todo 三刷的题目：
 *   2 15 34 55 72
 */
public class Test_review_1_200 {
    //2
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode p = head;
        int carry = 0;
        ListNode p1 = l1;
        ListNode p2 = l2;
        ListNode tail = head;
        while (p1 != null || p2 !=null) {
            int s = p1.val + p2.val + carry;
            if (p1 != null) {
                s += p1.val;
            }
            if (p2 !=null) {
                s+=p2.val;
            }
            p = new ListNode(s%10);
            tail.next = p;
            tail = p;
            carry = s / 10;
            p1 = p1.next;
            p2 = p2.next;
        }
        head = head.next;
        return head;
    }


    //15
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length < 3) {
            return res;
        }
        Arrays.sort(nums);
        for (int i = 0; i< nums.length; i++) {
            //todo i 去重
            if (i == 0 || nums[i] != nums[i-1]) {
                int j = i + 1;
                int k = nums.length - 1;
                while (j < k) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[k]);
                        res.add(list);
                        //todo 找到这一个还要继续往下找
                        j++;
                        k--;
                    }  else if (nums[i] + nums[j] + nums[k] > 0) {
                        k--;
                    }  else {
                        j++;
                    }
                }
            }
        }
        return res;
    }

    //22
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        genParenthesis(res, 0, 0, n, "");
        return res;
    }
    
    public void genParenthesis(List<String> res, int left, int right, int n, String s) {
        if (left ==n && left == right) {
            res.add(s);
        }
        if (left < n) {
            //加左括号
            genParenthesis(res, left + 1, right, n, s + "(");
        }

        //加右括号
        if (left > right) {
            genParenthesis(res, left, right + 1, n, s + ")");
        }
    }

    //34
    public int[] searchRange(int[] nums, int target) {
        int[] res = {-1,-1};
        int i = 0;
        int j = nums.length - 1;
        //查找左边界
        while (i <= j) {
            int mid = (i + j)/2;
            //todo
            if (nums[mid] >= target) {
                j = mid - 1;
            } else {
                i = mid + 1;
            }
        }
        res[0] = i;
        i = 0;
        j = nums.length - 1;
        //查找右边界
        while (i <= j) {
            int mid = (i + j)/2;
            //todo
            if (nums[mid] <= target) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        res[1] = j;
        return res;
    }


    //36
    public boolean isValidSudoku(char[][] board) {
        //row[i][j] = 1:表示数字j在第i行出现过
        int[][] row = new int[9][10];
        //col[i][j] = 1:表示数字j在第i列出现过
        int[][] col = new int[9][10];
        // g[i][j] = 1:表示数字j在第i个九宫格出现过
        int[][] g = new int[9][10];
        for (int i = 0; i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                int num = board[i][j]-'0';
                //如果数字在第i行出现过
                if (row[i][num] == 1) {
                    return false;
                }

                //如果数字在第j列出现过
                if (col[j][num] == 1) {
                    return false;
                }


                int g1 = (i/3) * 3 + j/3;
                //如果数字在第j列出现过
                if (g[g1][num] == 1) {
                    return false;
                }
                row[i][num] = 1;
                col[j][num] = 1;
                g[g1][num] = 1;
            }
        }
        return true;
    }

    //55
    public boolean canJump(int[] nums) {
        int f = 0;
        for (int i = 0; i< nums.length; i++) {
            f = Math.max(f, i + nums[i]);
            if (nums[i] == 0 && f <=i && i != nums.length-1) {
                return false;
            }
        }
        return true;
    }

    //72
    public int minDistance(String word1, String word2) {
        int m = word1.length() + 1;
        int n = word2.length() + 1;
        int[][] dp = new int[m][n];
        for (int i = 1; i < m; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i< m; i++) {
            for (int j = 1; j< n; j++) {
                int cost = 0;
                if (word1.charAt(i-1) != word2.charAt(j-1)) {
                    cost = 1;
                }
                //todo 无论w1[j]是否等于w2[j],都需要比较dp[i-1][j-1]->dp[i][j]的最小步数
                dp[i][j] = Math.min(Math.min(dp[i][j-1]+1, dp[i-1][j]+1), dp[i-1][j-1]+ cost);
            }
        }
        return dp[m-1][n-1];
    }


    //73
    public void setZeroes(int[][] matrix) {
        boolean colFlag = false;
        boolean rowFlag = false;
        for (int i = 0; i< matrix.length; i++) {
            if (matrix[i][0] == 0) {
                colFlag = true;
                break;
            }
        }
        for (int j = 0; j< matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                rowFlag = true;
                break;
            }
        }

        for (int i = 1; i< matrix.length; i++) {
            for (int j = 1; j< matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = 1; i< matrix.length; i++) {
            for (int j = 1; j< matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        if (colFlag) {
            for (int i = 0; i< matrix[0].length; i++) {
                matrix[i][0] = 0;
            }
        }
        if (rowFlag) {
            for (int j = 0; j< matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }
        System.out.println();
    }

    //75
    public void sortColors(int[] nums) {
        int zero = 0;
        int one = 0;
        int two = 0;
        for (int i = 0; i< nums.length; i++) {
            if (nums[i] == 0) {
                zero ++;
            } else if (nums[i] == 1) {
                one++;
            } else {
                two++;
            }
        }

        int i = 0;
        while (zero > 0) {
            nums[i++] = 0;
            zero--;
        }
        while (one > 0) {
            nums[i++] = 1;
            one--;
        }
        while (two > 0) {
            nums[i++] = 2;
            two--;
        }
        System.out.println();
    }

    //91
    public int numDecodings(String s) {
        if (s.length() < 2){
            return s.length();
        }

        int n = s.length();
        //dp[i] 表示s[0..i]的解码方式
        int[] dp = new int[n];
        dp[0] = 1;
        if (s.charAt(0) == '0') {

        }
        return 0;
    }

    public static void main(String[] args) {
        Test_review_1_200 test_review = new Test_review_1_200();
        int [] p = {2,0,2,1,1,0};
        //List<List<Integer>> lists = test_preview.threeSum(p);
        int n = 8;
        //List<String> strings = test_preview.generateParenthesis(n);
        //test_review.searchRange(p, n);
        //boolean b = test_review.canJump(p);
        String w1 = "horse";
        String w2 = "ros";
        //int i = test_review.minDistance(w1, w2);
//        int[][] m ={
//                {1,1,1},
//                {1,0,1},
//                {1,1,1}
//        };
        //test_review.setZeroes(m);
        test_review.sortColors(p);
        System.out.println();
    }
}
