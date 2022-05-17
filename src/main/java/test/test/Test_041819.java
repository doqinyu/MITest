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

    //7
    public int reverse(int x) {
        int sig = 1;
        if (x < 0) {
            sig = -1;
            x = -x;
        }
        int max = (int)(Math.pow(2,31)/10);
        List<Integer> list = new ArrayList<>();
        int y = x;
        //反向记录余数
        while (y > 0) {
            int remainder = y % 10;
            y = y / 10;
            list.add(remainder);
        }
        y = 0;
        //正向累加，同时判断是否溢出
        for (int i = 0; i < list.size(); i++) {
            //2的31次方的最高位为7
            if (y > max || (y == max && i < list.size()-1 && list.get(i+1) >= 7)) {
                y = 0;
                break;
            }

            y = y * 10 + list.get(i);
        }

        y = y * sig;
        return y;
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

    //29
    public int divide(int dividend, int divisor) {
        if((dividend==Integer.MIN_VALUE && divisor==-1)|| divisor==0)
            return Integer.MAX_VALUE;

        // 当传入的int值为Integer.MIN_VALUE时,Math.abs(Integer.MIN_VALUE)=Integer.MAX_VALUE + 1;
        long m = dividend;
        long n = divisor;
        if (dividend == Integer.MIN_VALUE) {
            //todo + 1L,不能 + 1(+1表示int类型相加)
            m = Integer.MAX_VALUE + 1L;
        } else {
            m = Math.abs(dividend);
        }
        if (divisor == Integer.MIN_VALUE) {
            n = Integer.MAX_VALUE + 1L;
        } else {
            n = Math.abs(divisor);
        }

        int sig = (dividend > 0) ^ (divisor > 0) ? -1: 1;
        if (n == 1) {
            return (int)(sig * m);
        }
        long res = 0;
        while (m >= n) {
            long t = n;
            long p = 1;
            while (m >= (t<<1)) {
                t = t << 1;
                p = p <<1;
            }

            res += p;
            m = m - t;
        }
        return (int)(res*sig);
    }

    /**
     * todo 【重点】快速加计算 x + y
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
        //int x = 0;
        //int reverse = t.reverse(x);
        //int i = t.myAtoi(s);
        //String[] strs = {"flower","flower","flower","flower"};
        int[] nums = {5,7,7,8,8,10};
        //List<List<Integer>> lists = t.threeSum(nums);

        //boolean valid = t.isValid(s);
        //int i = t.strStr(s, t1);
        //int divide = t.divide(-2147483648, -1);

        //int i = t.quickAdd(5, 53);
        int x = 6;
        int y = -3;
        int z = 4;
        //int divide = t.divide(-2147483648, 2);
        //int target = 7;
        //int search = t.search(nums, x);
        int[] ints = t.searchRange(nums, x);
        System.out.println();
    }
}
