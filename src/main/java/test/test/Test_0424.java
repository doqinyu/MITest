package test.test;

import java.util.*;

/**
 * 重点整理的题目：
 * 122(买卖股票的三种题型) 135 139 162 166
 */
public class Test_0424 {
    /**
     * 122
     * todo 重点
     * 考虑到不能同时交易，因此每天交易结束后，只可能存在两种情况：手里没有股票，手里存在一只股票待卖出
     * 因此定义状态如下：
     *  dp[i][0]:第i天结束交易后，手里不存在股票的最大收益值
     *  dp[i][1]:第i天结束交易后，手里存在一只股票的最大收益值
     *
     * dp[i][0]状态转移方程如下：
     * dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
     * 即：前一天没有股票了，或者前一天持有一只股票，今日卖出股票
     *
     * dp[i][1]状态转移方程如下"
     * dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);
     * 即：前一天存在一只待卖出的股票今日仍然待卖出，或者前一天没有股票了，今日买入一只股票
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        int[][]dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);
        }
        //全部交易结束后，持有股票的收益一定低于不持有股票的收益，因此直接返回dp[n-1][0]
        return dp[n-1][0];
    }


    /**
     * 125 只考虑字母和数字字符，可以忽略字母的大小写。
     * '0'-'9' = [48,57]
     * 'A'-'Z' = [65, 90]
     * 'a'-'z' = [97, 122]
     */
    public boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            //todo 注意i < j 越界问题
            //如果s.charAt(i)不是合法字符，直接跳过
            while (i < j && ((s.charAt(i) < '0' || s.charAt(i) > 'z')
                    || (s.charAt(i) < 'A' && s.charAt(i) > '9')
                    || (s.charAt(i) < 'a' && s.charAt(i) > 'Z'))) {
                i++;
            }
            //todo 注意i < j 越界问题
            //如果s.charAt(j)不是合法字符
            while (i < j && ((s.charAt(j) < '0' || s.charAt(j) > 'z')
                    || (s.charAt(j) < 'A' && s.charAt(j) > '9')
                    || (s.charAt(j) < 'a' && s.charAt(j) > 'Z'))) {
                j--;
            }

            //todo 注意i < j 越界问题
            if (i >= j) {
                return true;
            }

            char t1 = s.charAt(i);
            //如果s[i]是大写字符，转成小写
            if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                t1 = (char) (s.charAt(i) + 32);
            }
            char t2 = s.charAt(j);
            //如果s[j]是大写字符，转成小写
            if (s.charAt(j) >= 'A' && s.charAt(j) <= 'Z') {
                t2 = (char) (s.charAt(j) + 32);
            }
            if (t1 != t2) {
                System.out.println("i = " + i + ", t1 = " + t1 +" , j = " + j + ", t2 = " + t2);
                return false;
            }
            i++;
            j--;

        }
        return true;
    }

    //135
    public int candy(int[] ratings) {
        int n = ratings.length;
        //left[i]:从左往右排，至少给第i个孩子分的糖果数
        int[] left = new int[n];
        //right[i]:从右往左排，至少给第i个孩子分的糖果数
        int[] right = new int[n];
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            //如果当前孩子的分数比左边孩子高，那么必须保证left[i] = left[i-1] +1;
            if(ratings[i] > ratings[i-1]) {
                left[i] = left[i-1] +1;
            } else {
                left[i] = 1;
            }
        }
        right[n-1] = Math.max(1, left[n-1]);
        for (int i = n-2; i>=0; i--) {
            //如果当前孩子的分数比右边孩子高，那么必须保证right[i] = right[i+1] +1;
            if (ratings[i] > ratings[i+1]) {
                right[i] = right[i+1] + 1;
            } else {
                right[i] = 1;
            }
        }
        int sum = 0;
        for (int i = 0; i< n;i ++) {
            sum += Math.max(left[i], right[i]);
        }
        return sum;
    }

    /**
     * 136
     * 具有线性时间复杂度。 你可以不使用额外空间
     *  A ^ A = 0
     *  0 ^ B = B
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int r = nums[0];
        int n = nums.length;
        for (int i = 1; i<n; i++) {
            r = r ^ nums[i];
        }
        return r;
    }


    /**
     * 139
     * 可以重复使用字典中的单词
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        //dp[i]:是否存在子串s[0..i]
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            for (String w: wordDict) {
                int len = w.length();
                if (i >= len) {
                    String sub = s.substring(i-len, i);
                    if (sub.equals(w)) {
                        dp[i] = dp[i-len]||dp[i];
                    }
                }
            }
        }
        return dp[n];
    }

    /**
     * 141
     * O(1)（即，常量）内存
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        //慢指针每次走一步
        ListNode slow = head;
        //快指针每次走两步
        ListNode fast = head;
        //如果快指针能够走到头，说明没有环
        //如果存在快指针的下一个等于慢指针，说明有环
        while (fast != null && slow != null) {
            if (fast.next == slow) {
                return true;
            }
            slow = slow.next;
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
            }
        }
        return false;
    }

    //150
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i< tokens.length; i++) {
            //如果是运算符号
            if (tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/")) {
                Integer a = stack.pop();
                Integer b = stack.pop();
                switch (tokens[i]) {
                    case "+" : stack.push(b + a);break;
                    case "-" : stack.push(b - a);break;
                    case "*" : stack.push(b * a);break;
                    case "/" : stack.push(b / a);break;
                }
            } else {
                stack.push(Integer.parseInt(tokens[i]));
            }
        }
        return stack.pop();
    }

    /**
     * 160
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
        while (p1 !=null && p2 != null) {
            p1=p1.next;
            p2=p2.next;
        }
        ListNode k1 = headA;
        ListNode k2 = headB;
        //如果A链短
        if (p1 == null) {
            while (p2 != null) {
                p2 = p2.next;
                k2 = k2.next;
            }
        } else {
            //如果B链短
            while (p1 != null) {
                p1 = p1.next;
                k1 = k1.next;
            }
        }
        //k1据A链尾的距离 = k1据B链尾的距离
        while (k1 != null && k1 !=k2) {
            k1 = k1.next;
            k2 = k2.next;
        }
        return k1;
    }

    /**
     * 162
     * 如果我们从一个位置开始，不断地向高处走，那么最终一定可以到达一个峰值位置。
     * 因此，我们首先在 [0,n) 的范围内随机一个初始位置 i,然后根据nums[i-1],nums[i],nums[i+1] 三者的关系决定往哪个方向走：
     *  nums[i-1] < nums[i] > nums[i+1]，那么 i 就是峰值位置，直接返回即可
     *  nums[i-1] < nums[i] < nums[i+1]，那么 往 i+1 的方向走
     *  nums[i-1] > nums[i] > nums[i+1]，那么 往 i-1 的方向走
     *  nums[i-1] > nums[i] < nums[i+1]，那么 往 i+1 或者 i-1 均可。
     *
     *  现在我们规定：
     *  当 nums[i] < nums[i+1], 往 i+1 方向走
     *  当 nums[i] > nums[i+1], 往 i-1 方向走
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        int n = nums.length;
        int i = 0;
        int j = n-1;
        int mid = 0;
        while (i <= j) {
            mid = (i + j)/ 2;
            //nums[mid] < nums[mid +1] ,往 i+1 方向走
           if (mid < n -1 && nums[mid] < nums[mid +1]) {
               i = mid +1;
           } else {
               j = mid -1;
           }
        }
        return i;
    }


    /**
     * 166
     * @param numerator
     * @param denominator
     * @return
     */
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }
        //计算符号
        long sig = (numerator > 0 &&  denominator > 0) || (numerator < 0 &&  denominator < 0) ? 1L: -1L;
        long n = Math.abs((long)numerator);
        long d = Math.abs((long)denominator);
        //计算整数部分
        long r1 = n/d;
        n = n % d;
        //如果整除
        if (n == 0) {
            return String.valueOf((sig < 0 ? "-":"") +r1);
        }
        //商列表
        List<Long> merchant = new ArrayList<>();
        //key = 余数 ，value = 在小数中对应的位置(merchant)，从0开始
        Map<Long, Integer> map = new HashMap<>();

        int k = -1;//循环所在的位置的下一个, -1表示不存在
        while (n != 0) {
            if (map.get(n) != null) {
                k = map.get(n);
                break;
            }
            map.put(n, merchant.size());
            n = n * 10;
            long shang =  n / d;
            n = n % d;
            merchant.add(shang);
        }
        StringBuilder result = null;
        if (sig < 0) {
            result = new StringBuilder("-" + r1 + ".");
        } else {
            result = new StringBuilder(r1 + ".");
        }
        //如果存在循环
        if (k != -1) {
            for (int i = 0 ; k >= 1 && i<= k-1; i++) {
                result.append(merchant.get(i));
            }
            result.append("(");
            for (int i = k; i< merchant.size(); i++) {
                result.append(merchant.get(i));
            }
            result.append(")");
        } else {
            //如果不存在循环
            for (int i = 0 ; i< merchant.size(); i++) {
                result.append(merchant.get(i));  
            }
        }
        return result.toString();
    }

    /**
     * 169 时间复杂度为 O(n)、空间复杂度为 O(1) 的算法
     * 多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        int p = nums[0];
        int cnt = 1;
        for (int i = 1; i < nums.length; i++) {
            //众数 +1
            if (nums[i] == p) {
                cnt ++;
            } else {
                //非众数 -1
                cnt --;
            }
            if (cnt == 0) {
                p = nums[i];
                cnt = 1;
            }
        }
        return p;
    }



    public static void main(String[] args) {
        Test_0424 test_24 = new Test_0424();
        //String s = "leetcode";
        //List<String> wordDIct = Arrays.asList(new String[]{"leet", "code"});
        //boolean palindrome = test_24.isPalindrome(s);
        //int candy = test_24.candy(p);
        //int i = test_24.singleNumber(p);
        //boolean b = test_24.wordBreak(s, wordDIct);
//        List<Integer> list = new ArrayList<>();
//        list.add(-2);
//        list.add(0);
//        list.add(-3);
        int n1 = 0;
        int n2 = 1;
        int[] nm = {1,2,3,4};
        //int peakElement = test_24.findPeakElement(nm);
        String s = test_24.fractionToDecimal(n1, n2);
        //String s = test_24.fractionToDecimal(n1, n2);
        System.out.println();
    }
}
