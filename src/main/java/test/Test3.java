package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test3 {

    /**
     * 1. 三个同样的字母连在一起，一定是拼写错误，去掉一个的就好啦：比如 helllo -> hello
     * 2. 两对一样的字母（AABB型）连在一起，一定是拼写错误，去掉第二对的一个字母就好啦：比如 helloo -> hello
     * 3. 上面的规则优先“从左到右”匹配，即如果是AABBCC，虽然AABB和BBCC都是错误拼写，应该优先考虑修复AABB，结果为AABCC
     *
     * 输入例子：
     * 2
     * helloo
     * wooooooow
     * 输出例子：
     * hello
     * woow
     * @param list
     */
    public static void deal(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String res = dealSingle(list.get(i));
            System.out.println(res);
        }
    }

    public static String dealSingle(String s) {
        int i = 1;
        int n = s.length();
        StringBuilder sb = new StringBuilder();
        char last = s.charAt(0);
        //连续字母的对数
        int pair = 0;
        while (i < n) {
            //连续字母的个数
            int cnt = 1;
            while (i < n && s.charAt(i) == s.charAt(i-1)) {
                cnt++;
                i++;
            }
            //如果不连续
            if (cnt == 1) {
                sb.append(last);
            } else {
                //如果存在多个连续字母，按照2个连续字母处理
                if (cnt > 2) {
                    cnt = 2;
                }
                //如果存在连续的两个字符
                //如果是第一对连续字符
                if (pair == 0) {
                    pair = 1;
                    sb.append(last);
                    sb.append(last);

                } else {
                    //如果是第二对，只添加一个字符
                    sb.append(last);
                    //将pair重置
                    pair = 0;
                }
            }
            if (i < n) {
                last = s.charAt(i);
            }

            i++;
        }
        //如果sb的最后一个字符未被处理，那么直接添加
        if (sb.charAt(sb.length() - 1) != last) {
            sb.append(last);
        }
        return sb.toString();
    }

    //004
    public static void test () {
        Integer a = 1;
        Object obj = new Object();
        System.out.println(obj);
        UserMedalDO userMedalDO = new UserMedalDO();
        //userMedalDO.setLevel(1);
        test2(a, userMedalDO, obj);
        //System.out.println(userMedalDO.getLevel());
        System.out.println(obj);
    }

    public static void test2(Integer b, UserMedalDO userMedalDO, Object obj) {
        System.out.println("before " + obj);
        b = 2;
        obj = new Object();
        System.out.println("after " + obj);
        //userMedalDO.setLevel(2);

    }


    public static void main(String[] args) {
        test();
    }
}
