package mybatis.luban.Test;

import mybatis.luban.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultHandlerTest {
    SqlSessionFactoryBuilder factoryBuilder;
    SqlSessionFactory factory;
    SqlSession sqlSession;
    UserMapper mapper;

    public ResultHandlerTest() throws IOException {
        factoryBuilder = new SqlSessionFactoryBuilder();
        factory = factoryBuilder.build(Resources.getResourceAsStream("mybatis/mybatis-config2.xml"));
        sqlSession = factory.openSession();
        mapper = sqlSession.getMapper(UserMapper.class);
    }

    public void test() {
        List<Object> list = new ArrayList<>();
        ResultHandler handler = new ResultHandler()  {
            @Override
            public void handleResult(ResultContext resultContext) {
                //如果当前遍历到的结果集数量超过1，那么给该结果集打上stop=true标志。下一次遍历结果集时则停止遍历。
                if (resultContext.getResultCount() > 1)  {
                    resultContext.stop();
                }
                list.add(resultContext.getResultObject());
            }
        };
        //select 没有返回结果,因为返回结果在handler中自行处理了
        sqlSession.select("mybatis.luban.mapper.UserMapper.selectBatchUser", handler);
        //sqlSession.selectList("mybatis.mapper.UserMapper.selectBatchUser", handler);
        System.out.println("list.size = " + list.size());//list.size=2
    }

    public static void main(String[] args) throws IOException {
        ResultHandlerTest resultHandlerTest = new ResultHandlerTest();
        resultHandlerTest.test();
    }
}
