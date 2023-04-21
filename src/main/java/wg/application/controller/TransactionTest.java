package wg.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.algorithm.IdWorker;
import wg.application.entity.InformationSchema;
import wg.application.entity.User;
import wg.application.vo.Result;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/transaction_test")
public class TransactionTest {
    private static final String mysqlDriver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/wg?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8";
    private static final String user = "root";
    private static final String password = "root";

    @RequestMapping(value = "/test1")
    public void test() {
        String sql = "select * from user";
        Connection conn = null;
        try {
            Class.forName(mysqlDriver);
            conn = DriverManager.getConnection(url, user, password);
            assert false;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            boolean execute = preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/get_all_user")
    public void getAllUser() {
        String sql = "select * from user";
        Connection conn = null;
        try {
            Class.forName(mysqlDriver);
            conn = DriverManager.getConnection(url, user, password);
            assert false;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            // boolean execute = preparedStatement.execute();
            // System.out.println(execute);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet.getRow());
            System.out.println(resultSet);
            ArrayList<User> users = new ArrayList<>();
            while (resultSet.next()) {
                System.out.println(resultSet.getRow());
                User user1 = new User();
                user1.setName(resultSet.getString("name"));
                user1.setAge(resultSet.getInt("age"));
                users.add(user1);
            }
            users.forEach(System.out::println);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/add1")
    public void add1() {
        String sql = "insert user(id, name, age, birthday, gender) values(?, ? , ? , ? ,?)";
        try {
            Class.forName(mysqlDriver);
            Connection conn = DriverManager.getConnection(url, user, password);
            assert false;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            IdWorker idWorker = new IdWorker(1, 1, 1);
            preparedStatement.setLong(1, idWorker.nextId());
            preparedStatement.setString(2, "赵敏");
            preparedStatement.setInt(3, 12);
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(5, "0");

            boolean execute = preparedStatement.execute();
            System.out.println(execute);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/add_user")
    public void addUser() {
        String sql = "insert user(id, name, age, birthday, gender) values(?, ? , ? , ? ,?)";
        LocalDateTime localDateTime = LocalDateTime.of(1985, 7, 7, 20, 35, 20);
        long time = Timestamp.valueOf(localDateTime).getTime();
        try {
            Class.forName(mysqlDriver);
            Connection conn = DriverManager.getConnection(url, user, password);
            assert false;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            IdWorker idWorker = new IdWorker(1, 1, 1);
            preparedStatement.setLong(1, idWorker.nextId());
            preparedStatement.setString(2, "张无忌");
            preparedStatement.setInt(3, 13);
            preparedStatement.setTimestamp(4, new Timestamp(time));
            preparedStatement.setString(5, "1");

            boolean execute = preparedStatement.execute();
            System.out.println(execute);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/update_user_by_id/{id}")
    public void updateUserById(@PathVariable String id) {
        String sql = "update user set birthday = ? where id = ?";
        LocalDateTime localDateTime = LocalDateTime.of(1985, 6, 20, 20, 35, 20);
        long time = Timestamp.valueOf(localDateTime).getTime();
        try {
            Class.forName(mysqlDriver);
            Connection conn = DriverManager.getConnection(url, user, password);
            assert false;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTimestamp(1, new Timestamp(time));
            preparedStatement.setLong(2, Long.parseLong(id));

            int i = preparedStatement.executeUpdate();
            System.out.println(i);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/query_user_by_id/{id}")
    public Result<HashMap<String, Object>> queryUserById(@PathVariable(value = "id") String id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Result<HashMap<String, Object>> result = new Result<>();
        String sql = "select * from user where id = ?";
        try {
            Class.forName(mysqlDriver);
            Connection conn = DriverManager.getConnection(url, user, password);
            assert false;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, Long.parseLong(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getTimestamp("birthday"));
                Timestamp birthday = resultSet.getTimestamp("birthday");
                hashMap.put("birthday", birthday);
            }
            result.setData(hashMap);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/get_all_table")
    public void getTables() {
        String sql = "SELECT" +
                "* " +
                "FROM" +
                "INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                "WHERE" +
                "referenced_table_name = 'basic_data';";
        Connection conn = null;
        try {
            Class.forName(mysqlDriver);
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet.getRow());
            System.out.println(resultSet);
            ArrayList<InformationSchema> informationSchemas = new ArrayList<>();
            while (resultSet.next()) {
                InformationSchema informationSchema = new InformationSchema();
                informationSchema.setColumnName(resultSet.getString("column_name"));
                informationSchema.setReferencedColumnName(resultSet.getString("referenced_column_name"));
                informationSchema.setTableName(resultSet.getString("table_name"));
                informationSchema.setReferencedTableName(resultSet.getString("referenced_table_name"));
                informationSchemas.add(informationSchema);
            }
            informationSchemas.forEach(System.out::println);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
