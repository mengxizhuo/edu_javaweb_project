package com.zpark.gmd.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
    //创建全局变量DataSource
    private static DataSource dataSource = null;
    static {
        //1.创建Properties对象
        Properties properties = new Properties();

        try {
            //2,读取配置文件
            properties.load(JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties"));
            //3,获取连接
            dataSource = DruidDataSourceFactory.createDataSource(properties);

        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    public static void closeAll(Connection connection,java.sql.Statement statement,java.sql.ResultSet resultSet) {
        try {
            if(resultSet !=null)
                resultSet.close();
            if(statement !=null){
                statement.close();
            }
            if(connection !=null){
                connection.close();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
