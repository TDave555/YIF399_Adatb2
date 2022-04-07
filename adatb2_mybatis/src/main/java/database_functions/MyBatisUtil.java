package database_functions;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    private static final String CONFIG_LOCATION = "mybatis-config.xml";

    public MyBatisUtil() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(CONFIG_LOCATION);
        MyBatisUtil.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSessionFactory getFactory() {
        return MyBatisUtil.sqlSessionFactory;
    }
}
