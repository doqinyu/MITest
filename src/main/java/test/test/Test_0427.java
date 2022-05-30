package test.test;

import java.util.*;
/**
 * 重点整理的题目：
 * 334 371 378(图解)
 */
public class Test_0427 {

    //334
    public boolean increasingTriplet(int[] nums) {
        if (nums.length < 3) {
            return false;
        }
        //遍历数组过程中，寻找出两个最小的值分别作为min1和min2，再找到比这两个数大的数
        int one = Integer.MAX_VALUE;
        int two = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < one) {
                one = nums[i];
            } else if (nums[i] > one && nums[i] < two) {
                two = nums[i];
            } else if (nums[i] > two){
                //找到三个数 one two nums[i]
                return true;
            }
        }
        return false;
    }

    //344
    public void reverseString(char[] s) {
        int i = 0;
        int j = s.length - 1;
        while (i < j) {
            char t = s[i];
            s[i] = s[j];
            s[j] = t;
            i ++;
            j--;
        }
    }

    //347
    public int[] topKFrequent(int[] nums, int k) {
        //key = nums[i], value = nums[i]出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i< nums.length; i++) {
            if (map.get(nums[i]) == null) {
                map.put(nums[i], 1);
            } else {
                map.put(nums[i], map.get(nums[i]) +1);
            }
        }

        //创建一个小根堆 o[0]=当前元素 o[1]=该元素出现的次数
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        //按照出现的次数，创建有k个元素的大根堆
        int cnt = 0;
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            if (cnt < k) {
                queue.add(new int[] {entry.getKey(), map.get(entry.getKey())});
                cnt ++;
            } else if (map.get(entry.getKey()) > queue.peek()[1]){
                //如果出现频次比堆顶元素大
                queue.poll();
                queue.add(new int[] {entry.getKey(), map.get(entry.getKey())});
            }

        }

        int[] res = new int[queue.size()];
        int i = 0;
        while (queue.size()> 0) {
            res[i ++] = queue.poll()[0];
        }
        return res;
    }

    //350
    public int[] intersect(int[] nums1, int[] nums2) {
        //key = nums1中的元素，value = 该元素出现的次数
        Map<Integer, Integer> m1 = new HashMap<>();
        Map<Integer, Integer> m2 = new HashMap<>();
        for (int i = 0; i < nums1.length; i++) {
            m1.put(nums1[i], m1.getOrDefault(nums1[i], 0) + 1);
        }

        for (int i = 0; i < nums2.length; i++) {
            m2.put(nums2[i], m2.getOrDefault(nums2[i], 0) + 1);
        }

        List<Integer> res = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry: m1.entrySet()) {
            //存在交集
            if (m2.get(entry.getKey()) != null) {
                //交集出现的次数
                int cnt = Math.min(m2.get(entry.getKey()), entry.getValue());
                for (int i = 0; i< cnt ; i++) {
                    res.add(entry.getKey());
                }
            }
        }

        int[] r = new int[res.size()];
        int index = 0;
        for (Integer i: res) {
            r[index ++] = i;
        }
        return r;
    }

    /**
     * 371
     * https://blog.csdn.net/weixin_43354152/article/details/109038046
     * 两个数相加的和，等于两个数异或结果加上进位数左移1位后的结果
     * 由于两个数异或的结果加上进位数左移1位的结果，还是会产生新的进位，所以需要做反复的进位计算，直到最后进位数为0。
     * @param a
     * @param b
     * @return
     */
    public int getSum(int a, int b) {
        int sum = a;
        while(b != 0) {
            int carry = sum & b;
            sum = sum ^ b;
            b = carry << 1;
        }
        return sum;
    }

    /**
     * 378 (todo 重点)
     * https://www.jianshu.com/p/2ae6d2b32f06
     *
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest(int[][] matrix, int k) {
        // m 行
        int m = matrix.length;
        // n 列
        int n = matrix[0].length;
        int min = matrix[0][0];
        int max = matrix[m-1][n-1];
        while (min < max) {
            int mid = min + (max - min)/2;
            int cnt = count(matrix, m, n, mid);
            if (cnt < k) {
                min = mid +1;
            } else {
                //todo 可能存在多个数使得cnt = k，因此当找到cnt = k时，还需要继续查找 ，直至min = max
                max = mid;
            }
        }
        return min;
    }

    /**
     * 统计在 matrix 中比 mid 小的元素的个数
     * @param matrix
     * @param m
     * @param n
     * @param mid
     * @return
     */
    public int count(int[][] matrix, int m, int n, int mid) {
        int x = m-1;
        int y = 0;
        int cnt = 0;
        while (x >= 0 && y < n) {
            //如果第y列都小于等于mid
            if (matrix[x][y] <= mid) {
                //第y列有x+1个元素
                cnt += x + 1;
                y ++;
            } else {
                //如果第y列只有部分小于等于mid，依次往上查找边界
                x--;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Test_0427 test_0427 = new Test_0427();
        int[] p = {4,1,-1,2,-1,2,3};
        int[] ints = test_0427.topKFrequent(p, 2);
        //char[] s = {'H','a','n','n','a','h'};
        //test_0427.reverseString(s);
        //int[] ints = test_0427.topKFrequent(p, k);
        //int[] intersect = test_0427.intersect(p, p2);
//        int[][]m = {
//                {1,5,9},
//                {10,11,13},
//                {12,13,15}
//        };
//        int k = 8;
//        int i = test_0427.kthSmallest(m, k);
        //int[] ints = Arrays.copyOf(p, p.length);
        System.out.println();
    }
}
