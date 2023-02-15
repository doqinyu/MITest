package test.test2;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {

    public static int minOperations(int[] nums, int x) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        int target = sum - x;
        //滑动窗口计算最长连续子数组之和为 target
        int left = 0;
        int right = 0;
        sum = 0;
        int span = -1;
        while (right < nums.length) {
            sum += nums[right];
            while (left <= right && sum > target) {
                sum = sum - nums[left];
                left ++;
            }

            if (sum == target) {
                span = Math.max(span, right - left + 1);
            }

            right ++;
        }

        if (span == -1) {
            return -1;
        }
        return nums.length - span;
    }


    public static int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        List<int[]>[] indexList = new List[n];
        for (int i = 0; i< n; i++) {
            indexList[i] = new ArrayList<int[]>();
        }
        for (int i = 0; i < redEdges.length; i++) {
            int n1 = redEdges[i][0];
            int n2 = redEdges[i][1];
            indexList[n1].add(new int[]{n2, 0});
        }

        for (int i = 0; i < blueEdges.length; i++) {
            int n1 = blueEdges[i][0];
            int n2 = blueEdges[i][1];
            indexList[n1].add(new int[] {n2, 1});
        }

        int[] res  = new int[n];
        Arrays.fill(res, Integer.MAX_VALUE);
        res[0] = 0;

        int[][]visited = new int[n][2];
        Queue<int[]> queue = new LinkedBlockingQueue<int[]>();
        // 起点 距离 颜色(0 = red 1 = blue)
        queue.offer(new int[] {0,0,0});
        queue.offer(new int[] {0,0,1});
        while (queue.size() > 0) {
            int[] cur = queue.poll();
            int node = cur[0];
            int dis = cur[1];
            int col = cur[2];
            List<int[]> nextList = indexList[node];
            for (int[] next: nextList) {
                int to = next[0];
                int toCol = next[1];
                if (col != toCol && visited[to][toCol] == 0) {
                    visited[to][toCol] = 1;
                    res[to] = Math.min(res[to], dis+1);
                    queue.offer(new int[] {to, dis+1, toCol});
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (res[i] == Integer.MAX_VALUE) {
                res[i] = -1;
            }
        }

        return res;

    }

    public static boolean canPartition(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        //如果总和是奇数
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum /2;
        /**
         * 动态规划
         * dp[i][j] 表示nums[0...i]能否凑成和为j
         */

        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        if (target >= nums[0]) {
            dp[nums[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            for (int j = target; j >= nums[i]; j--) {
                //不选择nums[i]
                dp[j] = dp[j] || dp[j-nums[i]];
            }
        }

        return dp[target];
    }


    public static int countSubstrings(String s) {
        int n = s.length();
        //dp[i]: 表示以s[i]为中心的最长回文子串的臂长
        int[] dp = new int[n];
        int c = 0;
        int i = 0;
        int r = 0;
        while (i < n) {
            int len = 0;
            //先找对称点
            int i_sync = 2 * c - i;
            if (i_sync > 0) {
                //获取对称点的回文串的长度
                int f_sync = dp[i_sync];
                //计算开始比较的位置
                int right = Math.min(r, 2 * c + f_sync);
                //开始扩展比较
                len = expand(i - right, i + right, s);
            } else {
                len = expand(i, i ,s);
            }

            dp[i] = len;
            if (i + len > r) {
                c = i;
                r = i + len;
            }

            i ++;
        }

        int res = 0;
        for (i = 0; i< n; i++) {
            res += dp[i];
        }
        return res;
    }

    public static int expand(int i, int j, String s) {
        if (i < 0 || j >=s.length() || i>j) {
            return 0;
        }
        int cnt = (j - i)/2;
        while (i >=0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i --;
            j ++;
            cnt ++;
        }
        return cnt;
    }

    public static int countSubstrings2(String s) {
        int n = s.length();
        StringBuffer t = new StringBuffer("$#");
        for (int i = 0; i < n; ++i) {
            t.append(s.charAt(i));
            t.append('#');
        }
        n = t.length();
        t.append("!");
        //f[i] 表示以 t[i]为中心的最长臂长(包含#)
        int[] f = new int[n];
        /**
         * iMax : 中心
         * rMax : 以 iMax为中心的右边界
         */
        int iMax = 0, rMax = 0, ans = 0;
        for (int i = 1; i < n; ++i) {
            // 初始化 f[i]
            f[i] = i <= rMax ? Math.min(rMax - i + 1, f[2 * iMax - i]) : 1;
            // 中心拓展
            while (t.charAt(i + f[i]) == t.charAt(i - f[i])) {
                ++f[i];
            }
            // 动态维护 iMax 和 rMax
            if (i + f[i] - 1 > rMax) {
                iMax = i;
                rMax = i + f[i] - 1;
            }
            // 统计答案, 当前贡献为 f[i] / 2 上取整(因为 f[i] 计算的臂长包含填充字符，所以要/2)
            ans += f[i] / 2;
        }

        return ans;
    }

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

    public static int[] advantageCount(int[] nums1, int[] nums2) {
        int n = nums1.length;
        Integer [] idx1 = new Integer[n];
        Integer [] idx2 = new Integer[n];
        int [] ans = new int[n];

        for (int i = 0; i< n ; i++) {
            idx1[i] = i;
            idx2[i] = i;
        }

        //将idx1中的下标按照nums1大小升序排序
        Arrays.sort(idx1, ((i, j) -> nums1[i] - nums1[j]));
        //将idx2中的下标按照nums1大小升序排序
        Arrays.sort(idx2, (i, j) -> nums2[i] - nums2[j]);


        int left = 0;
        int right = n-1;
        //从小到大遍历nums1中的元素
        for (int i = 0; i < n; i++ ) {
            //有优势，左移
            if (nums1[idx1[i]] > nums2[idx2[left]]) {
                ans[idx2[left]] = nums1[idx1[i]];
                left ++;
            } else {
                //没优势，匹配nums2剩下的最大的
                ans[idx2[right]] =nums1[idx1[i]];
                right--;
            }
        }
        return ans;
    }


    public static int[] advantageCount2(int[] nums1, int[] nums2) {
        int n = nums1.length;
        Integer[] index1 = new Integer[n];
        Integer[] index2 = new Integer[n];
        for (int i = 0; i< n; i++) {
            index1[i] = i;
            index2[i] = i;
        }

        Arrays.sort(index1, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return nums1[i1] - nums1[i2];
            }
        });


        Arrays.sort(index2, new Comparator<Integer> () {
            @Override
            public int compare(Integer i1, Integer i2) {
                return nums2[i1] - nums2[i2];
            }
        });

        int[] res = new int[n];
        int left =0;
        int right = n-1;
        for (int i = 0; i< n ;i ++) {
            //排序后是否有优势
            if (nums1[index1[i]] > nums2[index2[left]]) {
                res[index2[left]] = nums1[index1[i]];
                left ++;
            } else {
                res[index2[right]] = nums1[index1[i]];
                right --;
            }
        }

        return res;

    }

    public static void dayForWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println();
    }

    public static void main(String[] args) {
        dayForWeek();

    }
}
