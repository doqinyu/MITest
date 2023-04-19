package test.test2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static String trans(String old) {
        //边界判断
        if (old == null || old.equals("") || old.length() < 2 || !old.contains("/")) {
            System.out.println("input error");
            return null;
            //throw new Exception("input error");
        }
        List<String> resList = new ArrayList();
        //先用/做切分
        String[] list = old.split("/");
        for (int i = 1; i < list.length; i++) {
            String s = list[i];
            System.out.println("s = " + s);
            //非法判断
            if (s == null || s.equals("")) {
                System.out.println("input error");
                return null;
                //throw new Exception("input error");
            }
            //如果停留在当前目录，不做处理
            if (s.equals(".")) {
                continue;
            } else if (s.equals("..")) {
                //移除上一级路径
                resList.remove(resList.size()-1);
            } else {
                //添加当前路径
                resList.add("/" + s);
            }
        }

        //拼接结果
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < resList.size(); i++) {
            res.append(resList.get(i));
        }


        return res.toString();
    }

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        String s = "/a/b/c/./d/../e/../f";
        String res = trans(s);
        System.out.println(res);
    }
}
