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

    public static void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        //从边界上的0出发，所有与边界相邻的最终不会被包围
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                dfs(board, 0, j, m, n);
            }
            if (board[m-1][j] == 'O') {
                dfs(board, m-1, j, m, n);
            }
        }

        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                dfs(board, i, 0, m, n);
            }
            if (board[i][n-1] == 'O') {
                dfs(board, i, n-1, m, n);
            }
        }


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'Y') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    public static void dfs(char[][] board, int i,int j, int m, int n) {
        //System.out.println("dfs before i = " + i + ", j = " + j + ", board = " + board[i][j]);
        if (i<0 || i>=m || j<0 || j >=n || board[i][j] == 'X' || board[i][j] == 'Y') {
            return;
        }
        board[i][j] = 'Y';
        System.out.println("dfs i = " + i + ", j = " + j + ", board = " + board[i][j]);
        dfs(board, i-1, j, m, n);
        dfs(board, i+1, j, m, n);
        dfs(board, i, j-1, m, n);
        dfs(board, i, j+1, m, n);
    }

    public static List<List<String>> partition(String s) {
        List<List<String>> ret = new ArrayList<>();
        int n = s.length();
        //【重点】f[i][j]: s[i...j] 是否是回文子串
        boolean[][] f = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(f[i], true);
        }
        for (int i = n-1; i>= 0; i--) {
            for (int j = i+1; j < n; j++) {
                f[i][j] = f[i+1][j-1] && (s.charAt(i) == s.charAt(j));
            }
        }

//        List<String> ans = new ArrayList<>();
//        ans.add(s.charAt(0)+"");
        dfs(ret, new ArrayList<>(), s, 0, n, f);
        return ret;

    }

    /**
     *
     * @param ret s[0...i-1] 所有回文子串的集合
     * @param ans s[0...i-1] 是回文子串的一种排列
     * @param i   当前搜索到的字符串下标
     * @param n   字符串 s 的长度
     */
    public static void dfs (List<List<String>> ret, List<String> ans, String s, int i, int n, boolean[][] f) {
        //已经搜索到最后一个字符，说明找到组合ans
        if (i == n) {
            ret.add(new ArrayList<String>(ans));
            return;
        }
        //从 i 处开始搜索，找到某个j,使得 s[i...j] 是回文子串
        for (int j = i; j < n; j++) {
            if (f[i][j]) {
                ans.add(s.substring(i, j+1));
                dfs(ret, ans,s,j+1,n,f);
                ans.remove(ans.size()-1);
            }
        }
    }

    public static int maxProduct(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        //fmax 代表以nums[i] 结尾的连续子数组的最大值
        int[] fmax = new int[n];
        //fmin 代表以nums[i] 结尾的连续子数组的最小值
        int[] fmin = new int[n];
        fmax[0] = nums[0];
        fmin[0] = nums[0];
        int max = nums[0];

        for (int i = 1; i< n; i++) {
            fmax[i] = Math.max(Math.max(fmax[i-1] * nums[i], nums[i]), fmin[i-1] * nums[i]);
            fmin[i] = Math.min(Math.min(fmin[i-1] * nums[i], nums[i]), fmax[i-1] * nums[i]);

            max = Math.max(max, fmax[i]);
        }

        return max;
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>((o1, o2) -> {
            return o2[0] - o1[0];
        });

        //初始化 k 个元素的大根堆
        for (int i = 0; i < k-1; i++) {
            queue.add(new int[]{nums[i], i});
        }

        int index = 0;
        int left = 0;
        int right = k-1;
        while (right < n) {
            queue.add(new int[] {nums[right], right});
            int[] arr = null;
            //如果堆顶元素不在滑动窗口内
            //不断移除堆顶元素，直至该元素在滑动窗口内
            while (queue.size() > 0){
                arr = queue.peek();
                if (arr[1] >= left) {
                    break;
                }
                queue.poll();
            }
            res[index++] = arr[0];
            left++;
            right++;
        }


        return res;
    }

    /**
     *给定一个 24 小时制（小时:分钟 "HH:MM"）的时间列表，找出列表中任意两个时间的最小时间差并以分钟数表示。
     *
     *
     *
     * 示例 1：
     *
     * 输入：timePoints = ["23:59","00:00"]
     * 输出：1
     * 示例 2：
     *
     * 输入：timePoints = ["00:00","23:59","00:00"]
     * 输出：0
     * @param timePoints
     * @return
     */
    public static int findMinDifference(List<String> timePoints) {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < timePoints.size(); i++) {
            String[] s = timePoints.get(i).split(":");
            int[] ans = new int[] {Integer.parseInt(s[0]), Integer.parseInt(s[1])};
            list.add(ans);
        }
        //list 按照 "时"，"分" 排序
        Collections.sort(list, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] != o2[0]) {
                    return o1[0] - o2[0];
                }
                return o1[1] - o2[1];
            }
        });

        int res = Integer.MAX_VALUE;
        for (int i = 1; i < list.size(); i++) {
            int cur = (list.get(i)[0] - list.get(i-1)[0]) * 60 + list.get(i)[1] - list.get(i-1)[1];
            //逆时针计算差值与顺时针计算差值取最小，即为两个时刻的最小差值
            cur = Math.min(cur, 24 * 60 - cur);
            res = Math.min(res, cur);
        }

        //计算最后一个与第一个的差值
        if (list.size() > 2) {
            int last = list.size() - 1;
            int cur = list.get(last)[0] - list.get(0)[0] * 60 + list.get(last)[1] - list.get(0)[1];
            //逆时针计算差值与顺时针计算差值取最小，即为两个时刻的最小差值
            cur = Math.min(cur, 24 * 60 - cur);
            res = Math.min(res, cur);
        }

        return res;
    }


    public static int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int res = 0;
        int n = nums.length;
        for (int i = 0; i < n - 2; i++) {
            /**
             * 在[i+2,n-1]之间找到一个临界点k，使得 n[i] + n[j] > n[k]
             * 那么 (n[i], n[j+1], n[k]) ...... (n[i], n[k-1], n[k])
             *     (n[i], n[j], n[j+1]) ...... (n[i], n[j], n[k-1])
             *    也都满足三角形规则 ,组合数共有 (k-j) * 2 -1
             *
             */

            for (int j = i + 1; j < n - 1; j++) {
                //二分查找临界点k
                int l = j + 1;
                int r = n - 1 ;
                int mid = (l + r) / 2;
                while (l <= r) {
                    if (nums[i] + nums[j] <= nums[mid]) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                    mid = (l + r) / 2;
                }
                //满足条件跳出循环
                if (nums[i] + nums[j] > nums[mid]) {
                    res += mid - j;
                    System.out.println(" i = " + i + ", j = " + j + ", k = " + mid + ", res = " + res);
                }

            }
        }

        return res;
    }


    public static int calculate(String s) {
        //先转后缀表达式
        String s1 = mediumToAfter(s);
        //再计算后缀表达式
        return comAfter(s1);
    }

    /**
     * todo 中缀表达式转后缀表达式
     * 逻辑处理：
     * 如果是数值，直接输出数值
     * 如果是 '('，直接入栈
     * 如果是 + - * / 符号，则比较栈顶符号与当前符号的优先级大小
     *      如果栈顶为空，或者栈顶符号优先级 < 当前符号的优先级，则当前符号入栈
     *      如果栈顶符号优先级 >= 当前符号的优先级，则栈顶符号出栈
     *  如果是')'，则栈顶元素出栈，直至遇到 '('
     *
     * 将栈中的符号全部弹出
     *
     * @param s
     * @return
     */
    public static String mediumToAfter(String s) {
        StringBuilder res = new StringBuilder();
        Stack<Character> stack = new Stack<Character>();
        int i = 0;
        int n = s.length();
        while (i < n) {
            //如果是数字，直接输出
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                res.append(s.charAt(i));
            } else if (s.charAt(i) == '(') {
                //如果是  '('，直接入栈
                stack.add(s.charAt(i));

            } else if (s.charAt(i) == ')') {
                //如果是')'，则栈顶元素出栈，直至遇到 '('
                while (stack.peek() != '(') {
                    res.append(stack.pop());
                }
                //将左括号出栈
                stack.pop();
            } else {
                //如果是 + - * / 符号，则比较栈顶符号与当前符号的优先级大小
                //计算当前元素的优先级
                int curPriority = getPriority(s.charAt(i));
                //当栈不为空 且 栈顶元素的优先级 >= 当前元素的优先级时，栈顶元素出栈
                while (!stack.isEmpty() && getPriority(stack.peek()) >= curPriority) {
                    res.append(stack.pop());
                }
                //将当前元素入栈
                stack.add(s.charAt(i));
            }

            i++;
        }
        //将栈中的符号全部弹出
        while (!stack.isEmpty()) {
            res.append(stack.pop());
        }
        return res.toString();
    }

    public static int getPriority(char c) {
        switch(c) {
            case '(' : return 1;
            case '+' : return 2;
            case '-' : return 2;
            case '*' : return 3;
            case '/' : return 3;
            case ')' : return 4;
        }
        return -1;
    }

    /**
     * 计算后缀表达式的值
     * 如果是数字，直接入栈
     * 如果是运算符，则从栈中弹出两个操作数，并将操作结果重新入栈
     * 计算完成后的栈顶元素即为最终结果
     * @param s
     * @return
     */
    public static int comAfter(String s) {
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        int n = s.length();
        while (i < n) {
            //如果是数字，直接入栈
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                stack.add(Integer.parseInt(s.charAt(i)+""));
            } else {
                //如果是运算符，则从栈中弹出两个操作数，并将操作结果重新入栈
                int a = stack.pop();
                int b = stack.pop();
                switch (s.charAt(i)) {
                    case '+' : stack.add(b + a);break;
                    case '-' : stack.add(b - a);break;
                    case '*' : stack.add(b * a);break;
                    case '/' : stack.add(b / a);break;
                }
            }
            i++;
        }
        return stack.pop();
    }

    public int divide(int a, int b) {
        //处理边界
        if (a == 0) {
            return 0;
        }

        if (b == Integer.MIN_VALUE) {
            return a == Integer.MIN_VALUE ? 1: 0;
        }

        if (a == Integer.MIN_VALUE) {
            if (b == 1) {
                return Integer.MIN_VALUE;
            }
            if (b == -1) {
                return Integer.MAX_VALUE;
            }
        }

        /**
         最小值是1，最大值是 a, 二分法，查找最大的mid，使得 mid * b <= a 且 (mid+1) * b > a
         如果查找不到，则为0

         正数的范围比负数的范围小，为了避免溢出，统一处理成负数。即找到 z,使得
         z * y >= x, 且 (z+1) * y < x
         其中 z > 0 , y < 0, x < 0
         */
        //两数是否异号
        boolean neg = false;
        if (a > 0) {
            a = -a;
            neg = !neg;
        }

        if (b > 0) {
            b = -b;
            neg = !neg;
        }
        int ans = 0;
        int left = 1;
        int right = a == Integer.MIN_VALUE ? Integer.MAX_VALUE : -a;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            //如果满足 mid * b >= a, mid 变大
            if (check(mid, b, a)) {
                ans = mid;
                //当mid为最大值时，不能左移
                if (mid == Integer.MAX_VALUE) {
                    break;
                }
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return neg? -ans : ans;

    }

    /**
     快速加算法计算 z * b >= a 是否满足条件
     */
    public boolean check(int z, int b, int a) {
        int ans = 0;
        while (z > 0) {
            if ((z & 1) == 1) {
                z = z-1;
                //预判 ans + b >= a
                if (ans < a - b) {
                    return false;
                }
                ans += b;
            }

            //预判 b + b >= x
            if ( z > 1 && b < a - b) {
                return false;
            }

            z = z >> 1;
            b = b + b;
        }
        return true;
    }



    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int i = 0;
        while (i < n) {
            /**
             * sumOfGas : 初始化从当前位置出发，累计的加油量
             * sumOfCost : 初始化从当前位置出发，累计的耗油量
             * cnt:  初始化从当前位置出发，累计到达的加油站的数量
             */
            int sumOfGas = 0, sumOfCost = 0;
            int cnt = 0;
            while (cnt < n) {
                //初始化出发的起始位置为 j
                int j = (i + cnt) % n;
                sumOfGas += gas[j];
                sumOfCost += cost[j];
                //如果不能到达下一个加油站，直接跳转到下一个无法到达的位置继续搜寻
                if (sumOfCost > sumOfGas) {
                    break;
                }
                cnt++;
            }
            //如果已经走过来 n 个加油站，说明可以绕行一圈，直接返回结果
            if (cnt == n) {
                return i;
            } else {
                //下一个无法到达的位置为 i + cnt + 1
                i = i + cnt + 1;
            }
        }
        //如果没有一个位置可以绕行一周，直接返回-1
        return -1;
    }


//    public String reco(String s) {
//
//    }

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
    public int lengthOfLongestSubstring2(String s) {
        int n = s.length();
        if (n == 0) {
            return 0;
        }
        int ans = 0;
        int left = 0;
        int right = 0;
        Map<Character, Integer> map = new HashMap<>();
        while (right < n) {
            Integer index = map.get(s.charAt(right));
            if (index != null) {
                left = Math.max(left, index + 1);
            }

            map.put(s.charAt(right), right);
            ans = Math.max(ans, right - left + 1);
            right ++;
        }

        return ans;

    }

    public static void main(String[] args) {
        Test test = new Test();
        ArrayList<Integer> list = new ArrayList<>();
        int[] gas = {1,2,3,4,5};
        int[] cost = {3,4,5,1,2};
        //int i = test.canCompleteCircuit(gas, cost);
        String s = "tmmzuxt";
        int i = test.lengthOfLongestSubstring2(s);

        System.out.println();
    }
}
