package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test5 {
    public static void getTarget(int a, int b) {
        /**
         key = 被除数的位置
         value = 存储计算过程中的被除数
         当被除数重复时，索命存在循环
         */
        int index = 0;
        Set<Integer> set = new HashSet();
        Map<Integer,Integer> map = new HashMap();
        // key = 被除数， value = 下标
        Map<Integer,Integer> reverseMap = new HashMap();
        reverseMap.put(a, 0);
        boolean loop = false;
        while (index < 100) {
            int shang = a * 10 / b;
            map.put(index ++ ,shang);

            //存在循环结
            if (set.contains(shang)) {
                loop = true;
                break;
            }
            reverseMap.put(shang, index);

            set.add(a);
            /**
             如果整除，则跳出循环
             */
            a = a * 10 % b;
            if (a == 0) {
                break;
            }

        }
        String res = "";
        String loopRes = "0.";
        //如果存在循环
        if (loop) {
            a = a * 10 % b;
            //找到循环的起始下标处
            int start = reverseMap.get(a);
            //循环结部分就是【start,index】对应的数
            for (int i = start; i < index; i++) {
                loopRes += (map.get(i));
            }

        } else {
            //如果可以,直接拼接map中的value
            for (int i = 0; i < index; i++) {
                res += (map.get(i));
            }
        }

        System.out.println("res = " + res);
        System.out.println("loopRes = " + loopRes);

    }

    public static void main(String[] args) {
        int a = 5;
        int b = 7;
        getTarget(a, b);
    }
}
