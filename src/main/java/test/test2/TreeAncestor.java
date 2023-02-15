package test.test2;

import java.util.ArrayList;
import java.util.List;

public class TreeAncestor {
    //dp[i][j] : 节点 i 的第 2^j 个祖先节点
    List<Integer>[] dp;

    public TreeAncestor(int n, int[] parent) {
        //System.out.println("n = "+ n);
        dp = new List[n];
        for (int i = 0; i < n ; i++) {
            // 初始化节点 i 的第 2^0 = 1 个祖先节点
            dp[i] = new ArrayList<Integer>();
            dp[i].add(parent[i]);
            //System.out.println("dp["+ i +"] [0] = " + dp[i].get(0));
        }

        /**
         * 计算节点 i 的第 2^j 个祖先节点 直至根节点
         * dp[i][j] = dp[dp[i][j-1]][j-1]
         */
//            for (int j = 1; Math.pow(2, j) <= n; j++) {
//                boolean allneg = true;
//                for (int i = 0; i< n; i++) {
//                    List cur = dp[i];
//                    int pre = dp[i].get(j-1);
//                    if (pre == -1) {
//                        cur.add(-1);
//                    } else {
//                        cur.add(dp[pre].get(j-1));
//                        allneg = false;
//                    }
//                }
//
//                //如果每个节点的计算都已到达根节点，那么后续不必再计算
//                if (allneg) {
//                    break;
//                }
//            }
        for (int j = 1; Math.pow(2, j) <= n; j++) {
            System.out.println("j = "+ j);
            // 所有的节点是否都到达根节点
            boolean allReached = true;
            for (int i = 0; i< n; i++) {
                //首先到达 j-1 处
                int next = dp[i].get(j-1);
                //如果不是根节点
                if (next != -1)  {
                    allReached = false;
                    //计算dp[i][j]，并添加到i的列表
                    next = dp[next].get(j-1);
                    dp[i].add(next);
                    System.out.println("j = "+ j + ", dp["+i+"]["+j+"] = " + next + ", allReached = " + allReached);
                } else {
                    dp[i].add(-1);
                }
            }

            if (allReached) {
                break;
            }
        }
        }

//        for (int i = 0; i< n; i ++) {
//            for (int j = 0; j< dp[i].size(); j ++) {
//                System.out.println("dp[" + i + "][" + j + "] = " + dp[i].get(j));
//            }
//        }

    public int getKthAncestor(int node, int k) {
        // 令 k = 2 ^ x1 + 2 ^ x2 + ... + 2 ^ xn
        int pos = 0;
        int res = node;
        while (k > 0 && res != -1) {
            int nextPos = k & 1;
            if (nextPos != 0) {
                if (pos >= dp[res].size()) {
                    res = -1;
                    break;
                }
                res = dp[res].get(pos);
            }

            k = k >> 1;
            pos ++;
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 5;
        int[] p = new int[] {-1,0,0,1,2};
        TreeAncestor treeAncestor = new TreeAncestor(n, p);
        int kthAncestor = treeAncestor.getKthAncestor(3, 5);
        System.out.println("kthAncestor = " + kthAncestor);
        int kthAncestor1 = treeAncestor.getKthAncestor(3, 2);
        System.out.println("kthAncestor1 = " + kthAncestor1);
        int kthAncestor2 = treeAncestor.getKthAncestor(2, 2);
        System.out.println("kthAncestor2 = " + kthAncestor2);
        int kthAncestor3 = treeAncestor.getKthAncestor(0, 2);
        System.out.println("kthAncestor3 = " + kthAncestor3);
        int kthAncestor4 = treeAncestor.getKthAncestor(2, 1);
        System.out.println("kthAncestor4 = " + kthAncestor4);
        System.out.println();
    }
}
