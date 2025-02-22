package no.ntnu.idatx1005.demo.dao;

import java.sql.*;

public class DatabaseTest {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            String jdbcUrl = "jdbc:postgresql://localhost:5432/idatt1005-db";
            String username = "idatt1005user";
            String password = "idatt1005password";

            // Register DB driver
            Class.forName("org.postgresql.Driver");

            // Connect to the DB
            Connection connection = null;
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Perform DB operations
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM userinfo";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next())
            {
                System.out.println("ID:" + resultSet.getString("userId"));
                System.out.println("username:" + resultSet.getString("username"));
                System.out.println("password:" + resultSet.getString("password"));
            }

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
