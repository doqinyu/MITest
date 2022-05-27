package test.test;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 重点整理的题目：
 * 3 7 8(有限状态机) 14 15 20 29 33 34
 */
public class Test_041819 {
    //3 最长不重复字串

    /**
     * 滑动窗口解法
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int maxLen = 0;
        //最长不重复字串的左边下标值(包含)
        int left = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            /*
                如果s[i]字符中出现过，收缩滑动窗口。下一个不重复字串的起始位置为
                    如果s[i]在当前最长字串中, left = 上一次重复位置的下一个
                    如果s[i]不在当前最长字串中, left 不变
            * */
            if (map.get(s.charAt(i)) != null) {
                left = Math.max(left, map.get(s.charAt(i))+ 1);

            }
            map.put(s.charAt(i), i);
            //更新 maxLen
            maxLen = Math.max(maxLen, i - left + 1);

        }

        return maxLen;
    }


    //以s[left,right]为对称点，找到s中的最长回文子串
    public String longestPalindrome(String s, int left, int right) {
        //String target = "";
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
            //target = s.substring(left, right +1);
        }
        return s.substring(left + 1, right);
    }

    //5 最长回文字串
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        //最长回文字串的最小下标(包含)
        String target = s.substring(0,1);
        //最长回文字串的最小下标(包含)
        for (int i = 0; i < s.length() - 1; i++) {
            //奇对称最长值
            String s1 = longestPalindrome(s, i, i);
            //偶对称最长值
            String s2 = "";
            if (s.charAt(i) == s.charAt(i + 1)) {
                s2 = longestPalindrome(s, i, i + 1);
            }

            if (s1.length() > target.length() && s1.length() > s2.length()) {
                target = s1;
            } else if (s2.length() > target.length() && s2.length() > s1.length()) {
                target = s2;
            }

        }

        return target;

    }

    /**
     * 7
     * https://leetcode.cn/problems/reverse-integer/solution/zheng-shu-fan-zhuan-by-leetcode-solution-bccn/
     * @param x
     * @return
     */
    public int reverse(int x) {
        int rev = 0;
        //因为x可能为负数，所以循环跳出的条件是x !=0
        while (x != 0) {
            if (rev < Integer.MIN_VALUE /10 || rev > Integer.MAX_VALUE / 10) {
                return 0;
            }
            int digit = x % 10;
            rev = rev * 10 + digit;
            x = x/10;
        }
        return rev;
    }

    /**
     * 8
     *https://leetcode.cn/problems/string-to-integer-atoi/solution/zi-fu-chuan-zhuan-huan-zheng-shu-atoi-by-leetcode-/
     * @param s
     * @return
     */
    public int myAtoi(String s) {
        Automaton automaton = new Automaton();
        for (int i = 0; i< s.length(); i++) {
            automaton.compute(s.charAt(i));
        }
        return (int) (automaton.sign * automaton.ans);
    }

    //9
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        String s = String.valueOf(x);
        int mid = s.length() / 2;
        int i = 0;
        int j = 0;
        //如果有偶数个字符
        if (s.length() % 2 == 0) {
            i = mid - 1;
            j = mid;
        } else {
            //如果有奇数个字符
            i = mid - 1;
            j = mid + 1;
        }
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i--;
            j++;
        }
        if (i < 0) {
            return true;
        }
        return false;
    }

    //13
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int result = 0;
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == 'I' && i < s.length() - 1 && (s.charAt(i + 1) == 'V' || s.charAt(i + 1) == 'X')) {
                result += map.get(s.charAt(i + 1)) - 1;
                i = i + 2;
            } else if (s.charAt(i) == 'X' && i < s.length() - 1 && (s.charAt(i + 1) == 'L' || s.charAt(i + 1) == 'C')) {
                result += map.get(s.charAt(i + 1)) - 10;
                i = i + 2;
            } else if (s.charAt(i) == 'C' && i < s.length() - 1 && (s.charAt(i + 1) == 'D' || s.charAt(i + 1) == 'M')) {
                result += map.get(s.charAt(i + 1)) - 100;
                i = i + 2;
            } else {
                result += map.get(s.charAt(i));
                i++;
            }
        }
        return result;
    }

    //14
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 1) {
            return strs[0];
        }
        if (strs[0] == null || strs[0].length() == 0) {
            return "";
        }

        int i = 0;
        char target;
        boolean macth = true;
        do {
            target = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                //如果某个字符串已经遍历完，或者遇到不匹配的前缀，最长公共前缀结束
                if (i >= strs[j].length() || strs[j].charAt(i) != target) {
                    macth = false;
                    break;
                }
            }
            i++;
        } while (i < strs[0].length() && macth);

        //todo 跳出循环时，可能匹配，也可能不匹配，因此需要判断
        if(macth) {
            return strs[0].substring(0, i);
        }
        return strs[0].substring(0, i - 1);
    }

    //15
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length < 3) {
            return result;
        }
        //首先从小到大排序 a<=b<=c
        Arrays.sort(nums);
        //固定第一个,查找另外两个
        for (int i = 0; i < nums.length - 2; i++) {
            //第一个数去重 todo nums[i] != nums[i-1])
            if (i == 0 || (nums[i] != nums[i - 1])) {
                int j = i + 1;
                int k = nums.length - 1;
                //双指针移动法
                while (j < k) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[k]);
                        result.add(list);
                        //todo 第二个数去重
                        j++;
                        while (j < k && nums[j]== nums[j-1]) {
                            j++;
                        }

                        //todo 第三个数去重
                        k--;
                        while (j < k && nums[k]== nums[k+1]) {
                            k--;
                        }
                    } else if (nums[i] + nums[j] + nums[k] > 0) {
                        k--;
                    } else {
                        j++;
                    }
                }
            }
        }
        return result;
    }

    //20
    public boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }
        List<Character> list = new ArrayList<>();
        for (int i = 0; i< s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '[' ||s.charAt(i) == '{') {
                list.add(s.charAt(i));
            } else if (list.size() <= 0) {
                //todo list size判断
                return false;
            } else if (s.charAt(i) == ')' && list.get(list.size() - 1) !='('
                        || s.charAt(i) == '}' && list.get(list.size() - 1) !='{'
                        || s.charAt(i) == ']' && list.get(list.size() - 1) !='[') {
                    return false;
            } else {
                list.remove(list.size()-1);
            }

        }
        return list.size() == 0;
    }



    //22
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        dfs(result, "", 0,0, n);
        return result;
    }

    public void dfs(List<String> result, String target, int left, int right, int n) {
        if (left == right && left == n) {
            result.add(target);
        }
        if (left < n) {
            dfs(result, target + "(", left+1, right, n);
        }
        if (right < left) {
            dfs(result, target + ")", left, right+1, n);
        }
    }

    //26
    public int removeDuplicates(int[] nums) {
        int k = 0;
        for (int i = 1; i< nums.length;) {

            if (nums[i] != nums[k]) {
                k++;
                nums[k] = nums[i];
            }
            i++;
        }
        return k+1;
    }

    //28
    public int strStr(String haystack, String needle) {
        if (haystack.equals("") && needle.equals("")) {
            return 0;
        }
        if (haystack.length() < needle.length()) {
            return -1;
        }

        for (int i = 0; i <= haystack.length() - needle.length() ;i++) {
            String subStr = haystack.substring(i, i+needle.length());
            if (subStr.equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 29
     * https://leetcode.cn/problems/divide-two-integers/solution/liang-shu-xiang-chu-by-leetcode-solution-5hic/
     *  首先边界处理
     *  然后符号处理
     *  然后使用快速乘判断
     * @param dividend
     * @param divisor
     * @return
     */
    public int divide(int dividend, int divisor) {
        //边界处理
        //考虑被除数为最小值的情况
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == 1) {
                return Integer.MIN_VALUE;
            }
            //结果溢出，根据题意，返回Integer的最大值
            if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
        }

        //考虑除数为最小值的情况
        if (divisor == Integer.MIN_VALUE) {
            return dividend == Integer.MIN_VALUE ? 1: 0;
        }

        //考虑被除数为 0 的情况
        if (dividend == 0) {
            return 0;
        }

        /**
         * todo 这里将除数与被除数变的同号。便于计算
         * 之所以将被除数与除数都变成负数，是因为，如果存在某个数为Integer.MIN_VALUE时，其相反数溢出32位。
         * 因此变成负数不会溢出
         */
        boolean negative = false;
        if (dividend > 0) {
            negative = !negative;
            dividend = -dividend;
        }
        if (divisor > 0) {
            negative = !negative;
            divisor = - divisor;
        }
        //由于z >=0，且为32位整数，因此先从[1, -dividend]开始找。如果找不到，那么 z 必然为0
        int left = 1;
        int right = dividend == Integer.MIN_VALUE ? Integer.MAX_VALUE: -dividend;
        int ans = 0;
        while (left <= right) {
            //注意溢出，并且不能使用除法.todo 这里右移运算优先级较低，因此需要将整个右移包括起来
            int mid = left + ((right - left) >> 1);
            //首先判断 z * y >= x是否满足
            boolean match = checkQuickAdd(mid, divisor, dividend);
            if (match) {
                ans = mid;
                //注意溢出。当mid为最大值时，不能再左移
                if (mid == Integer.MAX_VALUE) {
                    break;
                }

                left = mid + 1;
            } else {
                right = mid -1;
            }
        }

        return negative ? -ans: ans;
    }

    /**
     * 返回 z* y >= x是否成立
     * 其中 z >= 0, y < 0, x < 0
     * @param z
     * @param y
     * @param x
     * @return
     */
    public boolean checkQuickAdd(int z, int y, int x) {
        int res = 0;
        while (z != 0) {
            if ((z & 1) == 1) {
                /**
                 * 为了保证 res + y >= x
                 * 由于res + y 可能为溢出，因此将上式等价变成
                 * 保证 res >= x - y
                 */
                if (res < x - y) {
                    return false;
                }
               res = res + y;
            }
            /**
             * 如果还可以继续累加时，还需要保证 y + y > x
             * 这是因为 y 为负数，越加越小。如果当前累加的值已经小于 x，那么继续累加后的值必然比 x 更小
             * 那么 (z+1) * y <= x，不满足条件
             */
            if (z > 1) {
                if (y < x - y) {
                    return false;
                }
            }
            y = y + y;
            z = z >> 1;
        }
        return true;
    }

    /**
     * todo 【重点】快速加计算 x * y
     * @param x
     * @param y
     * @return
     */
    public int quickAdd(int x, int y) {
        long ans = 0;
        while (y > 0) {
            if ((y & 1) == 1) {
                y--;
                ans = (ans + x);
            }
            y = y/2;
            x = x + x;
        }
        return (int) ans;
    }



    //33
    public int search(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return nums[0] == target? 0: -1;
        }
        int i = 0;
        int j = n - 1;

        while (i <= j) {
            int mid = (i + j) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] >= nums[0]) {
                if (target >= nums[0] && target < nums[mid]) {
                    j = mid - 1;
                } else {
                    i = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[n-1]) {
                    i = mid + 1;
                } else {
                    j = mid - 1;
                }
            }
        }

        return -1;

    }

    //34
    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1,-1};
        }
        int i = 0;
        int j = nums.length-1;
        int mid = (i + j)/2;
        //查找左边界
        while (i <= j) {
            if (nums[mid] >= target) {
                j = mid -1;
            } else {
                i = mid +1;
            }
            mid = (i + j)/2;
        }
        int left = i;
        i = 0;
        j = nums.length-1;
        mid = (i + j)/2;
        //查找右边界
        while (i <= j) {
            if (nums[mid] <= target) {
                i = mid +1;
            } else {
                j = mid -1;
            }
            mid = (i + j)/2;
        }
        int right = j;
        left = left < 0? 0:left;
        right = right > nums.length? nums.length -1: right;
        //如果计算得到的左边界，比右边界大，说明不存在target
        if(left > right) {
            return new int[]{-1,-1};
        }
        return new int[] {left, right};
    }

    public static void main(String[] args) {
        Test_041819 t = new Test_041819();
        String s = "a";
        String t1 = "a";
        //int i = t.lengthOfLongestSubstring(s);
        int x = -123;
       // int reverse = t.reverse(x);
        //int i = t.myAtoi(s);
        //String[] strs = {"flower","flower","flower","flower"};
        //int[] nums = {5,7,7,8,8,10};
        //List<List<Integer>> lists = t.threeSum(nums);

        //boolean valid = t.isValid(s);
        //int i = t.strStr(s, t1);
        int divide = t.divide(-10, -3);

        //int i = t.quickAdd(5, 53);
        //int x = 6;
        int y = -3;
        int z = 4;
        //int divide = t.divide(-2147483648, 2);
        //int target = 7;
        //int search = t.search(nums, x);
        //int[] ints = t.searchRange(nums, x);
        System.out.println();
    }
}
