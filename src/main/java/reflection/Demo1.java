package reflection;
//黑马B站：https://www.bilibili.com/video/BV1C4411373T?p=2&spm_id_from=pageDriver

import reflection.domain.Person;
import reflection.domain.Student;

/*
 * 获取Class对象的三种方式
 * 1. class.forName("全类名")：将字节码文件加载到内存，返回Class对象
 *                  多用于配置文件，将类名定义在配置文件中。读取文件，加载类
 * 2. 类名.class：通过类名的属性class获取
 *                  多用于参数的传递
 * 3. 对象.getClass()：getClass()方法在Object类中定义
 *                  多用于对象获取字节码的方式
 *
 * 结论：同一个字节码文件(*.class)在一次程序运行过程中，只会被加载一次。无论通过哪一种方式获取的Class对象都是同一个.
 * */
public class Demo1 {

    //1. class.forName("全类名")：将字节码文件加载到内存，返回Class对象
    public static Class test1() throws ClassNotFoundException {
        Class cls1 = Class.forName("reflection.domain.Person");
        System.out.println(cls1);
        return cls1;
    }

    //2. 类名.class：通过类名的属性class获取
    public static Class test2() throws ClassNotFoundException {
        Class cls2 = Person.class;
        System.out.println(cls2);
        return cls2;
    }

    //3. 对象.getClass()：getClass()方法在Object类中定义
    public static Class test3() throws ClassNotFoundException {
        Class cls3 = new Person().getClass();
        System.out.println(cls3);
        return cls3;
    }

    public static void main(String[] args) throws Exception {
        Class cls1 = test1();
        Class cls2 = test2();
        Class cls3 = test3();

        System.out.println("cls1 == cls2 ? " + (cls1 == cls2));//true
        System.out.println("cls1 == cls3 ? " + (cls1 == cls3));//true
        System.out.println("cls2 == cls3 ? " + (cls2 == cls3));//true

        Class cls4 = Student.class;
        System.out.println("cls1 == cls4 ? " + (cls1 == cls4));//false

    }
}
