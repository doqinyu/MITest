package test.test;

import java.util.*;
/**
 * 重点整理的题目：
 * 667 684 686
 */
public class Test_0429 {

    /**
     * 667
     * https://blog.csdn.net/cx_cs/article/details/122625369
     * 假定第一个数为1，根据差值 (k, k-1, k-2,...1)构造序列
     * 1,(1+k),2,k,3,k-1
     * 到差值为1时，正好使用完序列1,2,...(1+k)
     * 其余的数字依次补齐即可.(todo 证明)
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
     * 684 并查集
     * @param edges
     * @return
     */
    public int[] findRedundantConnection(int[][] edges) {
        /*
        * 在一棵树中， 边的数量 = 节点的数量 - 1
        * 由于本题存在一条重复的边，因此 节点的数量 = 边的数量
        * */
        int n = edges.length;
        //parent[i] = 节点 i 的根节点
        int[] parent = new int[n + 1];
        //初始时，每个节点的根节点是其自身
        for (int i = 1; i<= n; i++) {
            parent[i] = i;
        }
        //遍历所有的边
        for (int[] edge: edges) {
            //判断这条边的两个节点是否在同一棵树中,如果不在
            if (findParent(parent, edge[0]) != findParent(parent, edge[1])) {
                //合并这两棵树
                union(parent, edge[0], edge[1]);
            } else {
                //如果在，说明这条边重复
                return edge;
            }
        }
        return null;
    }

    /**
     * 将含有节点 n1 和 n2 的两棵树合并为一棵树
     * @param parent
     * @param n1
     * @param n2
     */
    public void union (int[] parent, int n1, int n2) {
        parent[findParent(parent, n1)] = findParent(parent, n2);
    }

    /**
     * 递归查找node的根节点
     * @param parent
     * @param node
     * @return
     */
    public int findParent(int[] parent, int node) {
        while (parent[node] != node) {
            parent[node] = findParent(parent, parent[node]);
        }
        return parent[node];
    }

    /**
     * 696
     * @param s
     * @return
     */
    public int countBinarySubstrings(String s) {
        int n = s.length();
        int ans = 0;
        int ptr = 0;
        //前一个连续相同字符子串的长度
        int last = 0;
        while (ptr < n) {
            //统计当前相同字符子串的长度
            char c = s.charAt(ptr);
            int cnt = 0;
            while (ptr < n && s.charAt(ptr) == c) {
                ptr++;
                cnt++;
            }
            //累加相邻的连续子串的数量
            ans += Math.min(cnt, last);
            last = cnt;
        }
        return ans;
    }

    //697
    public int findShortestSubArray(int[] nums) {
        Map<Integer, int[]> map = new HashMap<>();
        //key = nums[i], value = int[],  int[0]:出现的次数 int[1]:首次出现的位置 int[2]:最后出现的位置
        for (int i = 0; i < nums.length; i++) {
            //非首次出现
            if (map.containsKey(nums[i])) {
                //次数加1
                map.get(nums[i])[0]++;
                //更新最后一次出现的位置
                map.get(nums[i])[2] = i;
            } else {
                //首次出现
                map.put(nums[i], new int[] {1,i,i});
            }
        }
        //最高频次
        int maxCnt = -1;
        //最高频次对应的最短距离
        int minLen = Integer.MIN_VALUE;
        for (Map.Entry<Integer, int[]> entry: map.entrySet()) {
            if (entry.getValue()[0] > maxCnt) {
                maxCnt = entry.getValue()[0];
                minLen = entry.getValue()[2] - entry.getValue()[1] + 1;
            } else if (entry.getValue()[0] == maxCnt) {
                minLen = Math.min(minLen, entry.getValue()[2] - entry.getValue()[1] + 1);
            }
        }

        return minLen;
    }



    public static void main(String[] args) {
        Test_0429 test_0429 = new Test_0429();
        int n = 8;
        int k = 4;
        //int[] ints = test_0429.constructArray(n, k);
        String s = "1";
        int i = test_0429.countBinarySubstrings(s);
        int[] p = {1,2,2,3,1};
        //int shortestSubArray = test_0429.findShortestSubArray(p);
        int[][] edge = {{1,2},{2,3},{3,4},{4,5},{5,6},{6,7},{1,7}};
        //int[] redundantConnection = test_0429.findRedundantConnection(edge);

        System.out.println();
    }
}
