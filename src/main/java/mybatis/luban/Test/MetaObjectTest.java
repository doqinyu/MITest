package mybatis.luban.Test;

import mybatis.luban.model.BlogDO;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * MetaObject的功能
 * 1。直接操作属性
 * 2。操作子属性
 * 3。自动创建属性对象
 * 4。自动查找属性名，支持下划线转驼峰
 * 5。基于索引访问数组。数组不能自动创建，需要手动创建
 * 6。访问Map。Map也不能自动创建，需要手动创建
 */
public class MetaObjectTest {

    //直接操作属性
    public void test() throws Exception {
        //装饰器模式
        Object obj = new BlogDO();
        Method getTitle = obj.getClass().getDeclaredMethod("getTitle");
        //Object invoke = getTitle.invoke(obj);
        Configuration configuration = new Configuration() ;
        MetaObject metaObject = configuration.newMetaObject(obj);
        metaObject.setValue("title","meta - set");
        System.out.println(metaObject.getValue("title"));
    }

    //复合属性设置。自动创建对象后设置属性
    public void testMultiProperties() throws Exception {
        Object obj = new BlogDO();
        Configuration configuration = new Configuration() ;
        MetaObject metaObject = configuration.newMetaObject(obj);
        metaObject.setValue("author.name","meta-set-user-name");
        System.out.println(metaObject.getValue("author.name"));
    }

    //查找驼峰命名
    public void testHumpNaming() {
        Object obj = new BlogDO();
        Configuration configuration = new Configuration() ;
        MetaObject metaObject = configuration.newMetaObject(obj);
        System.out.println(metaObject.findProperty("user_id", true));
    }

    //数组操作
    public void testArray() {
        Object obj = Mock.mockBlogDO();
        Configuration configuration = new Configuration() ;
        MetaObject metaObject = configuration.newMetaObject(obj);
        Object value = metaObject.getValue("comments[0].content");
        System.out.println(value);
    }

    //Map访问
    public void testMap() {
        Object obj = Mock.mockBlogDO();
        Configuration configuration = new Configuration() ;
        MetaObject metaObject = configuration.newMetaObject(obj);
        metaObject.setValue("labels", new HashMap<String,String>());

 //设置普通值
//        metaObject.setValue("labels[red]", "mock label");
//        System.out.println(metaObject.getValue("labels[red]"));

        //设置对象
        metaObject.setValue("labels[red]", Mock.mockUserDO());
        System.out.println(metaObject.getValue("labels[red].name"));
    }


    public static void main(String[] args) throws Exception {
        MetaObjectTest metaObjectTest = new MetaObjectTest();
        //metaObjectTest.test();
        //metaObjectTest.testMultiProperties();
        //metaObjectTest.testHumpNaming();
        metaObjectTest.testArray();
        //metaObjectTest.testMap();
    }
}
