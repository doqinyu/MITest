package proxy.dynamicProxy.jdk;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 *
 * 使用JDK动态代理的五大步骤；
 *  1、通过实现InvocationHandler接口来自定义自己的InvocationHandler
 *  2、通过Proxy.getProxyClass 获得动态代理类
 *  3、通过反射机制获得代理类的构造方法，方法签名为 getConstructor(InvocationHandler.class)
 *  4、通过构造返回获得代理对象并将自定义的InvocationHandler实例对象作为参数传入
 *  5、通过代理对象调用目标方法
 */
public class Main {

    public static void test1() throws Exception {
        //1、生成$Proxy0的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        //2、获取动态代理类
        Class proxyClass = Proxy.getProxyClass(IHello.class.getClassLoader(), IHello.class);
        //3、获得代理类的构造方法，并传入参数类型InvocationHandler.class
        Constructor constructor = proxyClass.getConstructor(InvocationHandler.class);
        //4、通过构造函数来创建动态代理对象，将自定义的InvocationHandler实例传入
        IHello o = (IHello) constructor.newInstance(new MyInvocationHandler((new HelloImpl())));
        //5、通过代理对象调用目标方法
        o.sayHello();
        o.eat();
    }

    /**
     * Proxy类中还有个将2~4步骤封装好的简便方法来创建动态代理对象，
     *   其方法签名为：newProxyInstance(ClassLoader loader,Class<?>[] instance, InvocationHandler h)
     *
     *       public static Object newProxyInstance(ClassLoader loader, //指定当前目标对象使用的类加载器，获取加载器的方法固定
     *                                           Class<?>[] interfaces, //目标对象实现的接口类型，使用泛型方法确认类型
     *                                           InvocationHandler h) //事件处理，执行目标对象的方法时，会触发事件处理器的方法
     *                                                                 （会把当前执行的目标对象方法作为参数传入）
     */

    public static void test2() {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        IHello o = (IHello) Proxy.newProxyInstance(IHello.class.getClassLoader(), new Class[]{IHello.class}, new MyInvocationHandler(new HelloImpl()));
        o.sayHello();
        o.eat();
    }

//    public static int test() {
//        int i = 1;
//        try {
//            return i;
//        } finally {
//            i++;
//        }
//    }
//
//    public static int test3() {
//        int i = 2;
//        try {
//            return 4;
//        } finally {
//            if (i == 2) {
//                return 0;
//            }
//        }
//    }

    public static void main(String[] args) throws Exception {
//        int val = test();
//        System.out.println(val);
//        val = test3();
//        System.out.println(val);
        String s = "abc";
    }
}
