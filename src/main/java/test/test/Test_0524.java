package test.test;

import com.google.protobuf.Enum;
/**
 * 重点整理的题目：
 * 714 766 769
 */
public class Test_0524 {
    //714
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        //dp[i][0]:表示到第i天为止时，不持有股票的最大收益
        //dp[i][0]:表示到第i天为止时，持有一只股票的最大收益
        int[][]dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i< n; i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i] - fee);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);
        }
        return Math.max(dp[n-1][0], dp[n-1][1]);
    }


    /**
     * 766 根据定义，当且仅当矩阵中每个元素都与其左上角相邻的元素（如果存在）相等时，该矩阵为托普里茨矩阵
     * @param matrix
     * @return
     */
    public boolean isToeplitzMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j< n; j++) {
                if (matrix[i][j] != matrix[i-1][j-1]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 769
     * 首先从最左块开始找最小块的大小。
     * 如果前k个元素为[0...k]，可以直接把他们分为一个块。
     * 当检查[0..n-1] 中 前 k个元素是不是[0,,,k-1]时，只需要检查其中最大的数是不是k就行了
     * @param arr
     * @return
     */
    public int maxChunksToSorted(int[] arr) {
        int n = arr.length;
        int ans = 0;
        int max = 0;
        for (int i = 0; i < n; i ++) {
            max = Math.max(max, arr[i]);
            //前 i 个数的最大数是 i，可以将这 i 个元素分为一个块
            if (max == i) {
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Test_0524 test_0524 = new Test_0524();
        int[] p ={1,3,7,5,10,3};
        int fee = 3;
        int[][] m = {
                {1,2},
                {2,2}
        };
        //int i = test_0524.maxProfit(p, fee);
        boolean toeplitzMatrix = test_0524.isToeplitzMatrix(m);
        System.out.println();

    }
}
