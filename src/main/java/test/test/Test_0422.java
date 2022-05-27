package test.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 重点整理的题目：
 * 91 121(买卖股票的三种题型)
 */
public class Test_0422 {
    //91
    public int numDecodings(String s) {
        int n = s.length();
        int[] f = new int[n + 1];
        //空字符串可以有 1 种解码方法，解码出一个空字符串。
        f[0] = 1;
        for (int i = 1; i <= n; ++i) {
            //如果当前字符s[i-1]不为0，那么一定有解法s[0,,i-2],s[i-1]
            if (s.charAt(i - 1) != '0') {
                f[i] += f[i - 1];
            }
            //如果前一个字符不为0，字符串s[i-2,i-1] <= 26，那么还存在解法 s[0...i-3],s[i-2,i-1]
            if (i > 1 && s.charAt(i - 2) != '0' && ((s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0') <= 26)) {
                f[i] += f[i - 2];
            }
        }
        return f[n];
    }

    //108
    public TreeNode sortedArrayToBST(int[] nums) {
        TreeNode root = sortedArrayToBST(nums, 0, nums.length - 1);
        return root;
    }

    public TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        if (left < 0 || right >= nums.length || left > right) {
            return null;
        }
        TreeNode root = new TreeNode();
        int mid = (left + right) / 2;
        root.val = nums[mid];
        //如果到达叶子节点
        if (left == right) {
            return root;
        }

        root.left = sortedArrayToBST(nums, left, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, right);
        return root;
    }

    //118
    public List<List<Integer>> generate(int numRows) {

        List<List<Integer>> result = new ArrayList<>();
        if (numRows == 1) {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            result.add(list);
            return result;
        }

        if (numRows == 2) {
            List<Integer> list1 = new ArrayList<>();
            list1.add(1);
            result.add(list1);

            List<Integer> list2 = new ArrayList<>();
            list2.add(1);
            list2.add(1);
            result.add(list2);
            return result;
        }

        //先生成 numRows - 1的杨辉三角数
        List<List<Integer>> generate = generate(numRows - 1);
        result.addAll(generate);

        List<Integer> pre = generate.get(generate.size() - 1);
        List<Integer> curList = new ArrayList<>();
        curList.add(1);
        for (int i = 1; i < pre.size(); i++) {
            curList.add(pre.get(i) + pre.get(i - 1));
        }
        curList.add(1);
        result.add(curList);
        return result;
    }

    //121
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n < 2) {
            return 0;
        }
        //int[] dp = new int[n];
        //能买入的最小值
        int min = Math.min(prices[0], prices[1]);
        //能卖出的最大值
        int max = prices[1];

        int result = 0;
        for (int i = 1; i < n; i++) {
            //如果第i天买入
            if (prices[i] < min) {
                min = prices[i];
                max = prices[i];
            }
            //如果第i天卖出
            if (prices[i] > max) {
                max = prices[i];
            }

            result = Math.max(result, max - min);
        }
        return result;
    }


    public static void main(String[] args) {
        Test_0422 test_22 = new Test_0422();
        int n = 5;
        //int[] ns = {1,2,3,4,5};
        String s = "301";
        int i = test_22.numDecodings(s);
        System.out.println();
    }
}
