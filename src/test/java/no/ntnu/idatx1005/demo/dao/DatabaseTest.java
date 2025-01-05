package no.ntnu.idatx1005.demo.dao;

import no.ntnu.idatx1005.demo.data.User;

import java.sql.*;
import java.util.UUID;

public class DatabaseTest {
    public static void main(String[] args) throws SQLException {
        String jdbcURL = "jdbc:sqlite::resource:testdb";

        Connection connection = DriverManager.getConnection(jdbcURL);

        System.out.println("Connected to database.");

        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "userId uuid primary key, " +
                "username VARCHAR(255), " +
                "password VARCHAR(255)" +
                ");";
                //"Create table IF NOT EXISTS user (ID int primary key, name varchar(50))";

        Statement statement = connection.createStatement();

        statement.execute(sql);

        System.out.println("Created table students.");

        User user = new User(UUID.randomUUID(), "Nam Ha Minh", "fafafa");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users (userId, username, password) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setObject(1, user.getUserId(), Types.OTHER);
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());
        int rows = preparedStatement.executeUpdate();

        if (rows > 0) {
            System.out.println("Inserted a new row.");
        }

        connection.close();
    }
}
