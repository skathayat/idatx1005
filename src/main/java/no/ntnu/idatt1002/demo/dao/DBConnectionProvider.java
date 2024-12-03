package no.ntnu.idatt1002.demo.dao;


import java.sql.*;

public class DBConnectionProvider {

    private final String url;
    private final String username;
    private final String password;

    private static DBConnectionProvider databaseConnectionProvider;

    private static final String IP_TO_DB_SERVER = "127.0.0.1";
    private static String DB_NAME = "myDB";
    private static final String DB_USE_SSL = "?useSSL=false";

    public DBConnectionProvider() {
        this.url = "jdbc:mysql://" + IP_TO_DB_SERVER + ":3307/" + DB_NAME + DB_USE_SSL;
        this.username = "root";
        this.password = "example";
    }

    public DBConnectionProvider(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnectionProvider instance() {
        if (databaseConnectionProvider == null) {
            databaseConnectionProvider = new DBConnectionProvider();
            return databaseConnectionProvider;
        } else {
            return databaseConnectionProvider;
        }
    }

    /**
     * Closes connections to database, makes sure that resultSets, and statements gets closed properly
     * @param connection the connection to be closed
     * @param preparedStatement the preparedStatement to be closed
     * @param resultSet the resultSet to be closed
     */
    static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
