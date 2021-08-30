package proxy.dynamicProxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    //目标对象
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-------------插入前置通知代码------------");
        //执行相应的目标方法,返回执行的结果
        Object rs = method.invoke(target, args);
        System.out.println("-------------插入后置处理代码------------");
        System.out.println("rs = " + rs);
        return rs;
    }
}