package test.test;

import java.util.*;

public class Test_0429 {

    /**
     * 667
     * https://blog.csdn.net/cx_cs/article/details/122625369
     * 假定第一个数为1，根据差值 (k, k-1, k-2,...1)构造序列
     * 1,(1+k),2,k,3,k-1
     * 到差值为1时，正好使用完序列1,2,...(1+k)
     * 其余的数字依次补齐即可.
     * 偶数序列: diff = a[i+1] - a[i] ==> a[i+1] = diff + a[i]
     * 奇数序列: diff = a[i] - a[i+1] ==> a[i+1] = a[i] - diff
     * @param n
     * @param k
     * @return
     */
    public int[] constructArray(int n, int k) {
        int[] res = new int[n];
        res[0] = 1;
        int diff = k;
        int i = 1;
        for (; i <= k; i++) {
            //如果是偶数序列
            if ((i+1) %2 == 0) {
                res[i] = res[i-1] + diff;
            } else {
                res[i] = res[i-1] - diff;
            }
            diff --;
        }
        //后面的2+k,...n 直接补齐(第k+1处对应2+k)
        for (; i< n; i++) {
            res[i] = i+1;
        }
        return res;
    }

    /**
     * 684
     * @param edges
     * @return
     */
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] res = new int[2];
        //已经联通的节点集合
        Set<Integer> set = new HashSet<>();
        //先把第一条边连接的节点添加到集合中
        set.add(edges[0][0]);
        set.add(edges[0][1]);
        for (int i = 1; i< n; i++) {
            //如果边(edges[i][0],edges[i][1])冗余
            if (set.contains(edges[i][0]) && set.contains(edges[i][1])) {
                res[0] = edges[i][0];
                res[1] = edges[i][1];
            } else if (!set.contains(edges[i][0])) {
                //如果不包含节点edges[i][0],包含edges[i][1],因此该边会联通edges[i][0]节点。将该节点添加到集合中
                set.add(edges[i][0]);
            } else {
                set.add(edges[i][1]);
            }
        }
        return res;
    }

    /**
     * 696
     * @param s
     * @return
     */
    public int countBinarySubstrings(String s) {
        int res = 0;
        int n1 = 1;
        int i = 1;
        //找到第一个连续0或者1的数量
        while (i< s.length()&& s.charAt(i) == s.charAt(i-1)) {
            n1++;
            i++;
        }
        int n2 = 1;
        do {
            i++;
            //找到第二个连续1或者0的数量
            while (i< s.length()&& s.charAt(i) == s.charAt(i-1)) {
                n2++;
                i++;
            }
            res += Math.min(n1,n2);
            n1 = n2;
            n2 = 1;
        } while (i < s.length());
        return res;
    }

    //697
    public int findShortestSubArray(int[] nums) {
        //key = nums[i], value = nums[i]出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(map.get(nums[i]), 0) + 1);
        }
        //记录出现的最高频次
        int maxCnt = -1;
        //key = nums[i]出现的次数, value = 所有的nums[i]
        Map<Integer, List<Integer>> m2 = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (m2.get(entry.getValue()) == null) {
                m2.put(entry.getValue(), new ArrayList<>());
            }
            m2.get(entry.getValue()).add(entry.getKey());
            maxCnt = Math.max(maxCnt, entry.getValue());
        }
        //key = 频次最高的nums[i], value = 出现的位置
        Map<Integer, List<Integer>> m3 = new HashMap<>();

        for (int i = 0; i< nums.length; i++) {
            if (m2.get(maxCnt).contains(nums[i])) {
                if (m3.get(nums[i]) == null) {
                    m3.put(nums[i], new ArrayList<>());
                }
                m3.get(nums[i]).add(i);
            }
        }

        int minDiff = nums.length;
        for (Map.Entry<Integer, List<Integer>> entry: m3.entrySet()) {
            List<Integer> list = entry.getValue();
            list.sort(Comparator.naturalOrder());
            minDiff = Math.min(minDiff, list.get(list.size() - 1) -list.get(0) + 1);
        }
        return minDiff;
    }



    public static void main(String[] args) {
        Test_0429 test_0429 = new Test_0429();
        int n = 8;
        int k = 4;
        //int[] ints = test_0429.constructArray(n, k);
        String s = "10101";
        //int i = test_0429.countBinarySubstrings(s);
        int[] p = {1,2,2,3,1};
        int shortestSubArray = test_0429.findShortestSubArray(p);

        System.out.println();
    }
}
