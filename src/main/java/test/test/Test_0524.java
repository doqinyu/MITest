package test.test;

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

    //766
    public boolean isToeplitzMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        //中心对角线下半部判断
        for (int i = m-2; i>= 0; i--) {
            int max = Math.min(m-i, n);
            for (int j = 1; j < max; j++) {
                if (matrix[i+j][j] != matrix[i][0]) {
                    return false;
                }
            }
        }

        //中心对角线上半部确认
        for (int j = n-2; j > 0; j--) {
            int max = Math.min(m-1, n-j);
            for (int i = 1; i < max; i++) {
                if (matrix[0][j] != matrix[i][i + j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //769
    public int maxChunksToSorted(int[] arr) {
        int n = arr.length;
        int ans = 0;
        for (int i = 0; i < n; i ++) {
            //如果数字i在应该在的位置上，无需变更ans
            if (arr[i] == i) {
                continue;
            }

            ans = Math.max(ans, Math.max(i , arr[i]));
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
