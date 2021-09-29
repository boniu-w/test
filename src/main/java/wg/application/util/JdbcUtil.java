package wg.application.util;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtil {

    public static void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }
}
