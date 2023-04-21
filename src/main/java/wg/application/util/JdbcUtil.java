package wg.application.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;

@Component
public class JdbcUtil {
    
    @Value("${spring.datasource.driver-class-name}")
    String driver;
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String user;
    @Value("${spring.datasource.password}")
    String password;
    
    private static JdbcUtil jdbcUtil;
    
    public static Connection conn;
    public static PreparedStatement preparedStatement;
    public static ResultSet resultSet;
    
    @PostConstruct
    public void init() {
        System.out.println(" >>>>>>>>>>>  jdbc init  <<<<<<<<<<<");
        jdbcUtil = this;
        System.out.println("jdbcUtil.password : " + jdbcUtil.password);
    }
    
    /************************************************************************
     * @author: wg
     * @description: 先于 @postConstruct 执行
     * @params:
     * @return:
     * @createTime: 13:17  2023/4/21
     * @updateTime: 13:17  2023/4/21
     ************************************************************************/
    static {
        // System.out.println(jdbcUtil.password); // 异常
        System.out.println("jdbc 静态代码块");
    }
    
    public static void closeConn() throws SQLException {
        if (conn != null) {
            resultSet.close();
            System.out.println("resultSet 关闭完成");
            preparedStatement.close();
            System.out.println("preparedStatement 关闭完成");
            conn.close();
            System.out.println("conn 关闭完成");
        }
    }
    
    /************************************************************************
     * @description: jdbc 查询
     * @author: wg
     * @date: 14:22  2021/12/22
     * @params:
     * @return:
     ************************************************************************/
    public static ResultSet jdbcQuery(String sql) {
        try {
            Class.forName(jdbcUtil.driver);
            conn = DriverManager.getConnection(jdbcUtil.url, jdbcUtil.user, jdbcUtil.password);
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            return resultSet;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /************************************************************************
     * @author: wg
     * @description: try-with-resources 结构, 相当于 try-catch-finally 会 自动关闭资源
     * @params:
     * @return:
     * java.sql.SQLException: Operation not allowed after ResultSet closed
     * 说明不能 return ResultSet
     * @createTime: 14:48  2023/4/21
     * @updateTime: 14:48  2023/4/21
     ************************************************************************/
    public static ResultSet jdbcQuery1(String sql) {
        try (Connection conn = DriverManager.getConnection(jdbcUtil.url, jdbcUtil.user, jdbcUtil.password);
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            Class<?> aClass = Class.forName(jdbcUtil.driver);
            
            return resultSet;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
