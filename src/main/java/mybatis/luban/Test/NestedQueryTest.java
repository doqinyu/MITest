package mybatis.luban.Test;

import mybatis.luban.mapper.BlogMapper;
import mybatis.luban.model.BlogMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class NestedQueryTest {
    SqlSessionFactoryBuilder factoryBuilder;
    SqlSessionFactory factory;
    SqlSession sqlSession;
    BlogMapper blogMapper;

    public NestedQueryTest() throws IOException {
        factoryBuilder = new SqlSessionFactoryBuilder();
        factory = factoryBuilder.build(Resources.getResourceAsStream("mybatis/mybatis-config2.xml"));
        sqlSession = factory.openSession();
        blogMapper = sqlSession.getMapper(BlogMapper.class);
    }

    //嵌套查询
    public void test() {
        BlogMap blogDO = blogMapper.selectBlogById(1);
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        NestedQueryTest nestedQueryTest = new NestedQueryTest();
        nestedQueryTest.test();
    }
}
