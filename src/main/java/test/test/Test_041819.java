package test.test;

import java.util.*;

public class Test_041819 {
    //3 最长不重复字串
    public int lengthOfLongestSubstring(String s) {
        int maxLen = 1;
        //最长不重复字串的左边下标值(包含)
        int left = 0;
        //最长不重复字串的右边下标值(包含)
        int right = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            //如果s[i]字符在当前最长字串中未出现过，最长不重复字串+1
            if (map.get(s.charAt(i)) == null) {
                map.put(s.charAt(i), i);
                right++;
            } else {
                //如果s[i]字符在当前最长字串中出现过,下一次最长重复字串从当前字符开始再计算
                maxLen = Math.max(maxLen, right - left);
                left = i;
                right = i;
            }
        }

        return Math.max(maxLen, right - left + 1);

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
        //最长回文字串的最小下标(包含)
        String target = "";
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
        //标识x是否为负数
        boolean nagtive = false;
        int target = 0;

        if (x < 0) {
            nagtive = true;
        }
        while (x != 0) {
            int remainder = x % 10;
            x = x / 10;
            target = target * 10 + remainder;
        }

        //如果结果未溢出
        if ((target > 0 && !nagtive) || (target < 0 && nagtive)) {
            return target;
        }
        //溢出则返回0
        return 0;
    }

    //8
    public int myAtoi(String s) {
        //是否开始
        boolean isBegin = false;
        //是否是负数
        boolean isNagative = false;
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            //如果当前字符是空格，若还没有开始计数，则直接跳过
            if (s.charAt(i) == ' ') {
                if (!isBegin) {
                    continue;
                } else {
                    //否则直接截断停止
                    break;
                }
            } else if (s.charAt(i) == '0') {
                if (!isBegin) {
                    continue;
                } else {
                    //否则计数
                    result = result * 10;
                }
            } else if (s.charAt(i) == '-') {
                if (!isBegin) {
                    isBegin = true;
                    isNagative = true;
                } else {
                    break;
                }
            } else if (s.charAt(i) >= '1' && s.charAt(i) <= '9') {
                isBegin = true;
                result = result * 10 + s.charAt(i) - '0';
            } else {
                break;
            }
        }

        if (isNagative) {
            result = -result;
            //负数溢出
            if (result > 0) {
                result = Integer.MIN_VALUE;
            }
        } else if (result < 0) {
            //正数溢出
            result = Integer.MAX_VALUE;
        }

        return result;
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

                        j++;
                        k--;
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
        List<Character> list = new ArrayList<>();
        for (int i = 0; i< s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '[' ||s.charAt(i) == '{') {
                list.add(s.charAt(i));
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

        for (int i = 0; i < haystack.length() - needle.length() ;i++) {
            String subStr = haystack.substring(i, i+needle.length());
            if (subStr.equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    //29
    public int divide(int dividend, int divisor) {
        boolean isSame= true;
        if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0) ) {
            isSame = false;
        }
        if (dividend < 0) {
            dividend = -dividend;
        }
        if (divisor < 0) {
            divisor = -divisor;
        }
        int result = 0;
        while (dividend > divisor) {
            dividend -= divisor;
            result++;
        }

        if (!isSame) {
            result = -result;
        }

        return result;
    }

    //33
    public int search(int[] nums, int target) {
        int i = 0;
        int j = nums.length - 1;
        if (nums[i] == target) {
            return i;
        }
        if (nums[j] == target) {
            return j;
        }

        //先二分查找分界点
        int mid = (i + j)/2;
        while (i < j) {
            if (nums[mid] == target || (nums[mid] < nums[mid-1] && nums[mid] < nums[mid+1])) {
                return mid;
            }
            if (nums[mid] > nums[0]) {
                i = mid +1;
            } else if (nums[mid] < nums[0]) {
                j= mid - 1;
            }
            mid = (i + j)/2;
        }

        if (nums[mid] == target) {
            return mid;
        }
        i = 0;
        j = 0;
        //如果target 比头元素大，pre = true
        if (target > nums[0]) {
            j = mid -1;
        } else {
            i = mid;
        }
        //在有序数组中二分查找
        mid = (i + j)/2;
        while (i < j) {
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] > target) {
                j = mid -1;
            } else if (nums[mid] < target) {
                i= mid + 1;
            }
            mid = (i + j)/2;
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

        return new int[] {left, right};
    }

    public static void main(String[] args) {
        Test_041819 t = new Test_041819();
        //String s = "()";
        //int x = 10;
        //String[] strs = {"dog","racecar","car"};
        int[] nums = {5,7,7,8,8,10};
        int target = 7;
        //int search = t.search(nums, target);
        int[] ints = t.searchRange(nums, target);
        System.out.println();
    }
}
