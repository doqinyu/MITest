package mybatis.luban.Test;

import mybatis.luban.mapper.BlogMapper;
import mybatis.luban.mapper.UserMapper;
import mybatis.luban.model.BlogDO;
import mybatis.luban.model.UserDO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.*;

public class UserMapperTest {
    SqlSessionFactoryBuilder factoryBuilder;
    SqlSessionFactory factory;
    SqlSession sqlSession;
    UserMapper userMapper;

    public UserMapperTest() throws IOException {
        factoryBuilder = new SqlSessionFactoryBuilder();
        factory = factoryBuilder.build(Resources.getResourceAsStream("mybatis/mybatis-config2.xml"));
        sqlSession = factory.openSession(true);
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    public void test() {
        UserDO userDO = userMapper.selectUserById(1);
        System.out.println();
    }

    /**
     * 使用sqlSession而不直接使用执行器的原因是：降低执行器的复杂性
     */
    public void test1() {
        List<Object> list = sqlSession.selectList("mybatis.luban.mapper.UserMapper.selectUserById", 1);
        System.out.println(list.get(0));
    }

    //指定一级缓存执行器
    public void test2() {
        SqlSession sqlSession = factory.openSession(ExecutorType.REUSE, true);
        List<Object> list = sqlSession.selectList("mybatis.luban.mapper.UserMapper.selectUserById", 1);
        list = sqlSession.selectList("mybatis.luban.mapper.UserMapper.selectUserById", 1);
        System.out.println(list.get(0));
    }

    //参数映射
    public void paramTest() {
        //UserDO wx = userMapper.selectUserByNameOrAge2("wx", 1);
        userMapper.selectUserByUser("wx", new UserDO());
        System.out.println();
    }


    public static void main(String[] args) throws IOException {
        UserMapperTest userMapperTest = new UserMapperTest();
        //userMapperTest.test();
        //userMapperTest.test1();
        //userMapperTest.test2();
        userMapperTest.paramTest();
    }
}
