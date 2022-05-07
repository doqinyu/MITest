package test.test;

import java.util.*;

public class Test_0424 {
    //122
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        if (n < 2) {
            return 0;
        }
        //dp[i] = 1 表示到第i天为止，所能获得的最大收益
        //dp[n]:存储历史收益最大值
        int [] dp = new int[n + 1];
        //如果第一天买入
        maxProfit2(prices, 1, dp, false, prices[0]);
        //如果第一天不买
        maxProfit2(prices , 1, dp, true, Integer.MAX_VALUE);
        return dp[n];
    }


    /**
     * @param prices
     * @param i   第i天(从0开始)
     * @param dp
     * @param hasSold  在第i天之前是否卖出过股票 true:卖出过股票 false:持有股票
     * @param buyPrice 最近一次买入的价格
     * @return
     */
    public void maxProfit2(int[] prices , int i, int [] dp, boolean hasSold, int buyPrice) {
        //到达最后一天时，更新历史收益最大值
        if (i  >= prices.length) {
            dp[prices.length] = Math.max(dp[prices.length], dp[prices.length -1]);
            return ;
        }
        //如果之前卖出过股票，那么当天只能选择买或者不买
        if (hasSold) {
            dp[i] = dp[i-1];
            int newBuyPrice = prices[i];
            //当天买入
            maxProfit2(prices, i + 1, dp, !hasSold, newBuyPrice);
            //当天不买
            maxProfit2(prices, i + 1, dp, hasSold, buyPrice);
        } else {
            //当天卖出
            dp[i] = dp[i-1] + prices[i] - buyPrice;
            maxProfit2(prices, i + 1, dp, !hasSold, Integer.MAX_VALUE);
            //当天不卖
            dp[i] = dp[i-1];
            maxProfit2(prices, i + 1, dp, hasSold, buyPrice);
        }
    }

    //122 todo 解法2
    public int maxProfit(int[] prices) {
        int sum = 0;
        // 遍历整个数组
        for (int i = 0; i < prices.length -1; i++) {
            // 取差价为正的加到利润里面
            sum += Math.max(0, prices[i+1] - prices[i]);
        }
        // 返回最大利润
        return sum;
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
            //如果s.charAt(i)不是合法字符，直接跳过
            while ((s.charAt(i) < '0' || s.charAt(i) > 'z')
                    || (s.charAt(i) < 'A' && s.charAt(i) > '9')
                    || (s.charAt(i) < 'a' && s.charAt(i) > 'Z')) {
                i++;
            }
            //如果s.charAt(j)不是合法字符
            while ((s.charAt(j) < '0' || s.charAt(j) > 'z')
                    || (s.charAt(j) < 'A' && s.charAt(j) > '9')
                    || (s.charAt(j) < 'a' && s.charAt(j) > 'Z')) {
                j--;
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
        int[] dp = new int[n];
        //先找到评分最小的位置
        int minIndex = 0;
        int min = ratings[0];
        for (int i = 1; i < n ; i++) {
            if (ratings[i] < min) {
                minIndex = i;
                min = ratings[i];
            }
        }
        int sum = 1;
        dp[minIndex] = 1;
        //给minIndex左边的同学发糖果
        for (int i = minIndex -1; i>=0; i--) {
            if (ratings[i] > ratings[i+1]) {
                dp[i] = dp[i+1] +1;
            } else {
                dp[i] = 1;
            }
            sum +=dp[i];
        }

        //给minIndex右边的同学发糖果
        for (int i = minIndex +1; i< n; i++) {
            if (ratings[i] > ratings[i-1]) {
                dp[i] = dp[i-1] +1;
            } else {
                dp[i] = 1;
            }
            sum +=dp[i];
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
        if (s.equals("")) {
            return true;
        }
        for (String sub : wordDict) {
            if (s.startsWith(sub)) {
                return wordBreak(s.substring(sub.length()), wordDict);
            }
        }
        return false;
    }

    /**
     * 141
     * O(1)（即，常量）内存
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
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
        while (k1 !=k2) {
            k1 = k1.next;
            k2 = k2.next;
        }
        return k1;
    }

    //162
    public int findPeakElement(int[] nums) {
        int n = nums.length;
        int i = 0;
        int j = n-1;
        int mid = 0;
        while (i <= j) {
            mid = (i + j)/ 2;
            //nums[mid] < nums[mid +1] 满足的区间一定不是
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
        int r1 = numerator/denominator;
        numerator = numerator % denominator;
        if (numerator == 0) {
            return String.valueOf(r1);
        }
        numerator = numerator % denominator;
        //商列表
        List<Integer> merchant = new ArrayList<>();
        //key = 余数 ，value = 在小数中对应的位置(merchant)，从0开始
        Map<Integer, Integer> map = new HashMap<>();

        int k = -1;//循环所在的位置的下一个, -1表示不存在
        while (numerator != 0) {
            while (numerator * 10 < denominator) {
                merchant.add(0);
                numerator = numerator * 10;
            }
            numerator = numerator * 10;
            if (map.get(numerator) != null) {
                k = map.get(numerator);
                break;
            }

            map.put(numerator, merchant.size());

            int shang = numerator / denominator;
            numerator = numerator % denominator;
            merchant.add(shang);
        }
        StringBuilder result = new StringBuilder(r1 + ".");
        //如果存在循环
        if (k != -1) {
            for (int i = 0 ; i< k-1; i++) {
                result.append(merchant.get(i));
            }
            result.append("(");
            for (int i = k-1; i< merchant.size()-1; i++) {
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
        //String s = "applepenapple";
        //List<String> wordDIct = Arrays.asList(new String[]{"apple", "pen"});
        //boolean palindrome = test_24.isPalindrome(s);
        //int candy = test_24.candy(p);
        //int i = test_24.singleNumber(p);
        //boolean b = test_24.wordBreak(s, wordDIct);
//        List<Integer> list = new ArrayList<>();
//        list.add(-2);
//        list.add(0);
//        list.add(-3);
        int n1 = 2;
        int n2 = 1;
        //String s = test_24.fractionToDecimal(n1, n2);
        System.out.println();
    }
}
