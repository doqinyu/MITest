package reflection;

import reflection.domain.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.FileHandler;

/**
 * Class对象功能
 * 1.获取成员变量们 Field[] getFields(): 获取所有public修饰的成员变量
 *               Field getField(String name): 获取指定名称的成员变量
 *               Field[] getDeclaredFields(): 获取所有所有的成员变量，不考虑修饰符
 *               Field getDeclaredField(String name)
 *
 * 2.获取构造方法们 Constructor<?>[] getConstructors()
 *               Constructor<T> getConstructor(Class<?>... parameterTypes)
 *               Constructor<?>[] getDeclaredConstructors()
 *               Constructor<T> getDeclaredConstructor(Class<?>... parameterTypes)
 *
 * 3.获取成员方法们 Method[] getMethods()
 *               Method getMethod(String name, Class<?>... parameterTypes)
 *               Class<?>[] getDeclaredClasses()
 *               Method getDeclaredMethod(String name, Class<?>... parameterTypes)
 *
 * 4.获取类名
 *          String getName()
 *
 *
 *
 * Filed：成员变量
 *      1.设置值
 *              set(Object obj, Object value)
 *      2.获取值
 *              Object get(Object obj)
 *      3.忽略访问权限修饰符的安全检查
 *              d.setAccessible(true);//暴力反射
 *
 * Constructor
 *      1.创建对象:T newInstance(Object ... initargs)
 *      2.如果使用空参数构造方法创建对象，操作可以简化: Class对象的newInstance方法
 *
 * Method:方法对象
 *      1.执行方法 Object invoke(Object obj, Object... args)
 *      2.获取方法名 String getName()
 */
public class Demo2 {

    //Filed
    public static void fieldTest() throws Exception {
        Class personClass = Person.class;

        Field[] fields = personClass.getFields();
        for (Field field:fields) {
            System.out.println(field);
        }

        Person p = new Person();
        Field a = personClass.getField("a");
        // 获取成员变量a的值
        Object aValue = a.get(p);
        System.out.println(aValue);
        //设置成员变量a的值
        a.set(p, "zhangsan");
        System.out.println(p);

        System.out.println("===============================");

        Field[] declaredFields = personClass.getDeclaredFields();
        for (Field field: declaredFields) {
            System.out.println(field);
        }

        Field d = personClass.getDeclaredField("d");
        //当通过反射获取非public属性时，需要忽略访问权限修饰符的安全检查（否则报错）
        d.setAccessible(true);//暴力反射
        Object dValue = d.get(p);
        System.out.println(dValue);

    }

    //Constructor
    public static void constructTest() throws Exception {
        Class<Person> personClass = Person.class;

        Constructor constructor = personClass.getConstructor(String.class, int.class);
        System.out.println(constructor);
        //用构造器来创建对象
        Object lisi = constructor.newInstance("lisi", 24);
        System.out.println(lisi);

        System.out.println("===============================");
        Constructor constructor1 = personClass.getConstructor();
        System.out.println(constructor1);
        //用构造器来创建对象
        Object lisi1 = constructor1.newInstance();
        System.out.println(lisi1);

        //无参构造方法创建对象：Class对象的newInstance方法
        Person person = personClass.newInstance();
        System.out.println(person);
        System.out.println("lisi1 == person ? " + (lisi1 == person));//false
    }

    //Method
    public static void methodTest() throws Exception {
        Class personClass = Person.class;

        //获取指定名称的方法
        Method eatMethod = personClass.getMethod("eat");
        //执行方法
        Person p = new Person();
        eatMethod.invoke(p);


        //获取有参数指定名称的方法
        Method eatMethod2 = personClass.getMethod("eat",String.class);
        //执行方法
        eatMethod2.invoke(p,"fruit");

        System.out.println("===============================");

        //获取所有public修饰的方法
        Method[] methods = personClass.getMethods();
        for (Method method: methods) {
            System.out.println(method.getName());
        }
    }

    public static void main(String[] args) throws Exception {
        //fieldTest();
        //constructTest();
        methodTest();
    }
}
