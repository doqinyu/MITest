package mybatis.luban.Test;

import mybatis.luban.mapper.BlogMapper;
import mybatis.luban.model.BlogMap;
import mybatis.luban.model.CommentDO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * 单个resultMap中的懒加载设置 lazy为懒加载,不调用（get()）,不从数据查询
 *     </resultMap> eager急加载，查询主表时，就把子集合查询出来
 *
 * 触发懒加载的时机（Configuration.lazyLoadTriggerMethods）
 * object.toString()
 * object.hashCode()
 * object.equals()
 * object.clone()
 *
 * debug 模式默认调用toString方法，会触发懒加载
 * 懒加载是一次操作
 */
//懒加载示例
public class LazyTest {
    static SqlSessionFactoryBuilder factoryBuilder;
    static SqlSessionFactory factory;
    static Configuration configuration;

    static {
        try {
            factoryBuilder = new SqlSessionFactoryBuilder();
            factory = factoryBuilder.build(Resources.getResourceAsStream("mybatis/mybatis-config2.xml"));
            configuration = factory.getConfiguration();
            configuration.setLazyLoadTriggerMethods(new HashSet<String>());
            System.out.println("configuration.getLazyLoadTriggerMethods().size() = " + configuration.getLazyLoadTriggerMethods().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    SqlSession sqlSession;
    BlogMapper blogMapper;

    public LazyTest() throws IOException {
        sqlSession = factory.openSession();
        blogMapper = sqlSession.getMapper(BlogMapper.class);
    }

    //调用设置方法后，懒加载将会被移除
    public void resetTest() {
        //BlogMap blogDO = blogMapper.selectBlogById(1);
        BlogMap blogDO = sqlSession.selectOne("selectBlogById", 1);
        //blogDO.getComments();
        blogDO.getAuthor();
        //blogDO.setComments(new ArrayList<CommentDO>()); //set后，移除懒加载属性
        System.out.println("重新设值后的评论数为：" + blogDO.getComments().size());
    }

    //序列化 ---> 字节码 ---> 反序列化为对象
    //注：需要设置configurationFactory,指定configuration
    public void lazySerializableTest() throws Exception{
        //BlogMap blogDO = blogMapper.selectBlogById(1);
        BlogMap blogDO = sqlSession.selectOne("selectBlogById", 1);
        byte[] bytes = writeObject(blogDO);
        BlogMap blogDO2 = (BlogMap) readObject(bytes);
        System.out.println("反序列化完成");
        //System.out.println("重反序列化后的评论数为：" + blogDO2.getComments().size());
    }

    public byte[] writeObject(Object obj) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        byte[] bytes = bos.toByteArray();
        oos.close();
        bos.close();
        return bytes;
    }

    public Object readObject(byte[] bytes) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return ois.readObject();
    }

    public void serializableTest() throws Exception {
        Bean bean = new Bean("000","aaa");
        byte[] bytes = writeObject(bean);
        Object bean2 = readObject(bytes);
        System.out.println();
    }


    //序列化与反序列化时需要指定ConfigurationFactory
    //重点在于 方法名getConfiguration和 返回结果Configuration
    public static class ConfigurationFactory {
        public static Configuration getConfiguration() {
            return configuration;
        }
    }

    private static class Bean implements Serializable {
        public String id;
        public String name;

        //在序列化时，重写对象。这样就可以检测到哪些属性需要序列化，哪些属性不需要序列化。真正序列化到的是writeReplace返回的对象
        protected final Object writeReplace() throws ObjectStreamException{
            System.out.println("write replace");
            return new Bean("8888", "llll");
        }

        //在反序列化时，转换对象
        protected final Object readResolve() {
            System.out.println("read resolve");
            this.name = "鲁班大叔";
            return this;
        }

        public Bean(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }



    public static void main(String[] args) throws Exception {
        LazyTest lazyTest = new LazyTest();
        lazyTest.resetTest();
        //lazyTest.lazySerializableTest();
        //lazyTest.serializableTest();
    }
}
