package test.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinStack {
    public List<Integer> minList;
    public List<Integer> list;
    public int size = 0;

    public MinStack() {
        minList = new ArrayList<>();
        list = new ArrayList<>();
    }

    public void push(int val) {
        list.add(val);
        minList.add(val);
        minList = minList.stream().sorted().collect(Collectors.toList());
        size ++;
    }

    public void pop() {
        int last = list.get(size - 1);
        list.remove(size -1);
        minList.remove(new Integer(last));
        size --;
    }

    public int top() {
        return list.get(size - 1);
    }

    public int getMin() {
        return minList.get(0);
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        minStack.getMin();
        minStack.pop();
        minStack.top();
        minStack.getMin();
    }
}
