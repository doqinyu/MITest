package mybatis.luban.Test;

import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ExecutorTest {

    static SqlSessionFactoryBuilder builder;
    static SqlSessionFactory sqlSessionFactory;
    static Configuration configuration;
    static JdbcTransaction jdbcTransaction;
    static Connection connection;

    static {
        try {
            builder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = builder.build(Resources.getResourceAsStream("mybatis/mybatis-config2.xml"));
            configuration = sqlSessionFactory.getConfiguration();

            Properties properties = new Properties(Resources.getResourceAsProperties("datasource.properties"));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("userName"), properties.getProperty("password"));
            jdbcTransaction = new JdbcTransaction(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //简单执行器
    public static void simpleExecutorTest() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("mybatis.luban.mapper.BlogMapper.selectBlogById");

        List<Object> list = executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(4));
        list = executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(4));
        System.out.println(list.get(0));
    }

    //可重用执行器
    public static void reuseExecutorTest() throws SQLException {
        ReuseExecutor executor = new ReuseExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("mybatis.luban.mapper.BlogMapper.selectBlogById");

        List<Object> list = executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(4));
        list = executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(4));
        System.out.println(list.get(0));
    }

    //批处理执行器
    //只针对修改操作。查询操作相当于SimpleExecutor执行多次
    public static void batchExecutorTest() throws SQLException {
        BatchExecutor executor = new BatchExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("mybatis.luban.mapper.UserMapper.setName");
        Map param = new HashMap<>();
        param.put("id", 1);
        param.put("name", "test-7-12");
        executor.doUpdate(ms, param);
        executor.doUpdate(ms, param);
        executor.doFlushStatements(false);

    }

    //有一级缓存的简单执行器
    public static void simpleExecutorCacheTest() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("mybatis.luban.mapper.BlogMapper.selectBlogById");
        //这里使用的是query方法，不是doQuery方法
        List<Object> list = executor.query(ms, 1, RowBounds.DEFAULT, null);
        list = executor.query(ms, 1, RowBounds.DEFAULT, null);
        System.out.println(list.get(0));
    }

    //二级缓存执行器
    public static void cacheExecutorTest() throws SQLException {
        SimpleExecutor simpleExecutor = new SimpleExecutor(configuration, jdbcTransaction);
        CachingExecutor executor = new CachingExecutor(simpleExecutor);
        MappedStatement ms = configuration.getMappedStatement("mybatis.luban.mapper.UserMapper.selectUserById");
        //这里使用的是query方法，不是doQuery方法
        List<Object> list = executor.query(ms, 1, RowBounds.DEFAULT, null);
        executor.commit(true);
        list = executor.query(ms, 1, RowBounds.DEFAULT, null);
        System.out.println(list.get(0));
    }

    public static void main(String[] args) throws Exception {
        //simpleExecutorTest();
        //reuseExecutorTest();
        //batchExecutorTest();
        //simpleExecutorCacheTest();
        cacheExecutorTest();
    }

}
