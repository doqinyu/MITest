package test.test2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Solution {
    /**
     * 记录应匹配的字符串及其个数
     */
    Map<Character, Integer> ori = new HashMap<Character, Integer>();
    /**
     * 记录窗口的目标字符串及其出现的次数
     */
    Map<Character, Integer> cnt = new HashMap<Character, Integer>();

    public String minWindow(String s, String t) {
        int tLen = t.length();
        //计算目标字符串及其出现的次数
        for (int i = 0; i < tLen; i++) {
            char c = t.charAt(i);
            ori.put(c, ori.getOrDefault(c, 0) + 1);
        }
        int l = 0, r = -1;
        int len = Integer.MAX_VALUE, ansL = -1, ansR = -1;
        int sLen = s.length();
        while (r < sLen) {
            ++r;
            //如果当前字符s.charAt(r) 在目标字符集合中， 更新窗口出现的次数
            if (r < sLen && ori.containsKey(s.charAt(r))) {
                cnt.put(s.charAt(r), cnt.getOrDefault(s.charAt(r), 0) + 1);
            }
            //check(): 当前窗口是否包括目标子串。如果是，则收缩窗口
            while (check() && l <= r) {
                if (r - l + 1 < len) {
                    len = r - l + 1;
                    ansL = l;
                    ansR = l + len;
                }
                //左移时，移除窗口出现的目标字符次数
                if (ori.containsKey(s.charAt(l))) {
                    cnt.put(s.charAt(l), cnt.getOrDefault(s.charAt(l), 0) - 1);
                }
                ++l;
            }
        }
        return ansL == -1 ? "" : s.substring(ansL, ansR);
    }

    /**
     * 通过比较窗口中目标字符的出现次数来判断当前窗口是否包括目标子串
     * @return true:包括  false:不包括
     */
    public boolean check() {
        Iterator iter = ori.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Character key = (Character) entry.getKey();
            Integer val = (Integer) entry.getValue();
            if (cnt.getOrDefault(key, 0) < val) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String s1 = solution.minWindow(s, t);
        System.out.println();
    }

}
