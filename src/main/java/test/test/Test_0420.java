package test.test;

import java.util.*;
/**
 * 重点整理的题目：
 * 50
 */
public class Test_0420 {

    //36:有效的数独
    public boolean isValidSudoku(char[][] board) {
        if (board.length != 9 || board[0].length != 9) {
            return false;
        }

        //rows[i][j] = 1，表示第 i 行出现数字 j
        int[][] rows = new int[9][10];
        //cols[i][j] = 1，表示第 i 列出现数字 j
        int[][] cols = new int[9][10];
        //ge[i][j] = 1，表示第 i 个小宫格出现数字 j
        int[][] ge = new int[9][10];
        for (int i = 0; i< 9; i++) {
            for (int j = 0; j< 9;j ++) {
                if (board[i][j] == '.') {
                    continue;
                }
                int num = board[i][j] -'0';
                //如果第i行出现过数字num，那么直接返回不合法
                if (rows[i][num] == 1) {
                    return false;
                }
                //如果第j列出现过数字num，那么直接返回不合法
                if (cols[j][num] == 1) {
                    return false;
                }
                //todo 计算(i,j)所在的九宫格
                int d = (i/3)*3 + j/3;
                //如果该九宫格内出现过数字num，那么直接返回不合法
                if (ge[d][num] == 1) {
                    return false;
                }
                //标记该数字出现的位置
                rows[i][num] = 1;
                cols[j][num] = 1;
                ge[d][num] = 1;
            }
        }

        return true;
    }


    //38
    public String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }

        String s = countAndSay(n - 1);
        StringBuilder result = new StringBuilder();
        int cnt = 1;
        int i = 0;
        char target = s.charAt(0);
        do {
            target = s.charAt(i);
            cnt = 1;
            i++;
            while(i < s.length() && s.charAt(i) == target) {
                i++;
                cnt ++;
            }
            result.append(String.valueOf(cnt));
            result.append(target);
        } while (i < s.length());

        return result.toString();
    }

    //46
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length == 1) {
            List<Integer> list = new ArrayList<>();
            list.add(nums[0]);
            result.add(list);
            return result;
        }
        int n = nums.length;
        int[] pres = Arrays.copyOfRange(nums, 0,  n- 1);

        //获取nums[0,..., n-2]的组合方式
        List<List<Integer>> preList = permute(pres);
        //nums[n-1] 可插入的位置有 0...n-1
        for (int i = 0; i < n; i++) {
            for (List<Integer> pre: preList) {
                List desc = new ArrayList();
                desc.addAll(pre);
                desc.add(i, nums[n-1]);
                result.add(desc);
            }
        }

        return result;
    }


    /**
     * 48
     *  (i,j) -> (j, n-1-i)
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        // level 代表层级
        for (int level = 0; level < n/2; level ++) {
            for (int k = 0; k < (n+1)/2; k++) {
                int temp = matrix[level][k];
                int i = level;
                int j = k;
                matrix[i][j] = matrix[n-1-j][i];
                matrix[n-1-j][i] = matrix[n-1-i][n-1-j];
                matrix[n-1-i][n-1-j] = matrix[j][n-1-i];
                matrix[j][n-1-i] = temp;
            }
        }
    }

    //49
    public List<List<String>> groupAnagrams(String[] strs) {
        //key = str, value = str出现的异位词
        Map<String, List<String>> map = new HashMap<>();

        //List中的每一个元素按照字典排序
        for (int i = 0; i < strs.length ; i++) {
            char[] chars = strs[i].toCharArray();
            Arrays.sort(chars);
            String sortS = new String(chars);
            if (map.get(sortS) == null) {
                List<String> subList = new ArrayList<>();
                subList.add(strs[i]);
                map.put(sortS, subList);
            } else {
                map.get(sortS).add(strs[i]);
            }
        }

        List<List<String>> result = new ArrayList<>();
        for (List<String> subList: map.values()) {
            result.addAll(Collections.singleton(subList));
        }
        return result;
    }


    //50[todo] 快速乘
    public double myPow(double x, int n) {
        if (n == 0 || x == 1) {
            return 1;
        }

        if(x!=-1.0 && n==Integer.MIN_VALUE)
            return 0;

        if (x == -1.0) {
            if (Math.abs(n)%2 == 0) {
                return 1;
            } else {
                return -1;
            }
        }

        if (n == 1) {
            return x;
        }

        int sig = 1;
        if (n < 0) {
            n = -n;
            sig = -1;
        }

        double ans = 1;
        while(n !=0) {
            if ((n & 1) ==1) {
                n--;
                ans = ans * x;
            }

            x = x*x;
            n = n/2;
        }
        if (sig < 0) {
            ans = 1/ans;
        }
        return ans;
    }

    //53
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        //dp[i] 表示以nums[i]结尾的最大子序列
        int[] dp = new int[n];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i< n; i++) {
            /**
             * 如果nums[i]加入前面的子序列，dp[i]=dp[i-1]
             * 如果nums[i]不加入前面的子序列，dp[i]=nums[i]
             */
            dp[i] = Math.max(dp[i-1]+ nums[i], nums[i]);
            //max取以每个nums[i]结尾的最大子序列即可
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * 55
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        //能跳到的最远下标
        int f = 0;
        for (int i = 0; i< nums.length ; i++) {
            f = Math.max(f, i + nums[i]);
            //如果此处为0，且不是最后一个元素，那么必然被卡住
            if (nums[i] == 0 && f <= i && i != nums.length-1) {
                return false;
            }
        }
        return true;
    }

    //56
    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }
        //按照第一个坐标升序排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        List<int[]> result = new ArrayList<>();
        int start = intervals[0][0];
        int end = intervals[0][1];
        int k = 1;
        while (k < intervals.length) {
            // intervals[k] 与前一个区间无交集
            if (intervals[k][0] > end) {
                int[] tmp = new int[2];
                tmp[0] = start;
                tmp[1] = end;
                result.add(tmp);

                start = intervals[k][0];
                end = intervals[k][1];
            } else if (intervals[k][1] > end){
                //两区间存在交集，且需要更新重叠区间最大值
                end = intervals[k][1];
            }
            k++;
        }
        int[] tmp = new int[2];
        tmp[0] = start;
        tmp[1] = end;
        result.add(tmp);

        int[][] list = new int[result.size()][2];
        for (int i = 0; i< result.size() ; i++) {
            list[i][0] = result.get(i)[0];
            list[i][1] = result.get(i)[1];
        }
        return list;
    }

    public static void main(String[] args) {
        Test_0420 test_20 = new Test_0420();
        double x = 2.00000;
        int[] ns = {1,2,3};
        //int[][] nums = {{1,4},{4,5}};
        //test_20.merge(nums);
        List<List<Integer>> permute = test_20.permute(ns);
        System.out.println();
    }
}
