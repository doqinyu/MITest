package mybatis.luban.Test;

import mybatis.luban.mapper.BlogMapper;
import mybatis.luban.model.BlogMap;
import mybatis.luban.model.BlogMap3;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class JoinTest {

    SqlSessionFactoryBuilder sqlSessionFactoryBuilder;
    SqlSessionFactory factory;
    SqlSession sqlSession;
    BlogMapper blogMapper;

    public JoinTest () throws Exception {
        sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        factory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis/mybatis-config2.xml"));
        sqlSession = factory.openSession();
        blogMapper = sqlSession.getMapper(BlogMapper.class);
    }

    //联合查询测试
    public void test() {
        BlogMap blogMap = blogMapper.selectBlogById2(1);
        System.out.println();
    }

    //循环引用测试
    public void test2() {
        BlogMap3 blogMap3 = blogMapper.selectBlogById3(1);
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        JoinTest joinTest = new JoinTest();
        //joinTest.test();
        joinTest.test2();
    }
}