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

    @PostConstruct
    public void init() {
        jdbcUtil = this;
    }

    public static void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        conn = null;
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
            Connection conn = DriverManager.getConnection(jdbcUtil.url, jdbcUtil.user, jdbcUtil.password);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            closeConn(conn);
            return resultSet;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
