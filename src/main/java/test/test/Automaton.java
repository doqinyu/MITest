package test.test;

import java.util.HashMap;
import java.util.Map;

/**
 * leetcode: 8
 * 有限状态机
 */
public class Automaton {
    String state = "start";
    int sign = 1;
    //todo 注意这里用的是long
    long ans = 0;
    //状态机
    Map<String, String[]> map = new HashMap(){
        {
            /**
             * 第一列代表：输入空格""
             * 第二列代表：输入符号"+"或者"-"
             * 第一列代表：输入数字
             * 第一列代表：输入其他
             * key 代表起始状态
             * value[0]:代表输入空格后，转变的状态
             * value[1]:代表输入符号后，转变的状态
             * value[2]:代表输入数字后，转变的状态
             * value[3]:代表输入其他后，转变的状态
             */
            put("start", new String[]{"start","signed","inNumber", "end"});
            put("signed", new String[]{"end","end","inNumber", "end"});
            put("inNumber", new String[]{"end","end","inNumber", "end"});
            put("end", new String[]{"end","end","end", "end"});
        }
    };

    public void compute(char t) {
        //计算输入对应的列
        int col = getCol(t);
        //根据输入更新下一个状态
        state = map.get(state)[col];
        if (state == "signed") {
            sign = t == '-' ? -1:1;
        } else if (state == "inNumber") {
            ans = ans * 10 + t - '0';
            ans = sign == 1? Math.min(ans, (long)Integer.MAX_VALUE) : Math.min(ans, -(long)Integer.MIN_VALUE);
        }
    }

    /**
     *
     * @param state
     * @return
     */
    public int getCol(char state) {
        if (state == ' ') {
            return 0;
        }
        if (state == '+' || state == '-') {
            return 1;
        }
        if (state >= '0' && state <= '9') {
            return 2;
        }

        return 3;
    }

}
