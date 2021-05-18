package mybatis.luban.Test;

import mybatis.luban.mapper.BlogMapper;
import mybatis.luban.mapper.UserMapper;
import mybatis.luban.model.BlogDO;
import mybatis.luban.model.UserDO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class UserMapperTest {
    SqlSessionFactoryBuilder factoryBuilder;
    SqlSessionFactory factory;
    SqlSession sqlSession;
    UserMapper userMapper;

    public UserMapperTest() throws IOException {
        factoryBuilder = new SqlSessionFactoryBuilder();
        factory = factoryBuilder.build(Resources.getResourceAsStream("mybatis/mybatis-config2.xml"));
        sqlSession = factory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    public void test() {
        UserDO userDO = userMapper.selectUserById(1);
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        UserMapperTest userMapperTest = new UserMapperTest();
        userMapperTest.test();
    }
}
