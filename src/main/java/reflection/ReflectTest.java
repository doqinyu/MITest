package reflection;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 案例：
 *      需求：写一个"框架"，可以帮我们创建任意类的对象，并且执行其中任意方法
 *      前提：不能改变该类的任何代码，可以创建任意类的对象，可以执行任意方法
 *
 *      实现：1、配置文件
 *           2、反射
 *
 *      步骤：1、将需要创建的对象的全类名和需要执行的方法定义在配置文件中
 *           2、在程序中加载读取配置文件
 *           3、使用反射技术来加载类文件进内存
 *           4、创建对象
 *           5、执行方法
 */
public class ReflectTest {
    public static void main(String[] args) throws Exception {
        //加载配置文件
        Properties properties = new Properties();
        properties.load(ReflectTest.class.getClassLoader().getResourceAsStream("reflection/pro.properties"));

        //获取配置文件中定义的信息
        String className = (String) properties.get("className");
        String methodName = (String) properties.get("methodName");

        //加载类到内存
        Class cls= Class.forName(className);
        //创建对象
        Object o = cls.newInstance();
        //获取方法对象
        Method method = cls.getMethod(methodName);
        //执行方法
        method.invoke(o);

    }
}
