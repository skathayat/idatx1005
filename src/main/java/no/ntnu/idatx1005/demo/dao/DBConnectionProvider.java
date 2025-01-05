package no.ntnu.idatx1005.demo.dao;


import java.sql.*;

public class DBConnectionProvider {
    private static DBConnectionProvider databaseConnectionProvider;
    private static String DB_NAME = "db";
    private static String DATABASE_URL = "jdbc:sqlite::resource:" + DB_NAME;

    public DBConnectionProvider() {
    }

    Connection getConnection() {
        try {
            // Note: In a real application, you should not connect to the database like this. Instead, you should use a connection pool.
            // In addition, database should be protected
            return DriverManager.getConnection(DATABASE_URL);
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

    /**
     * Method that creates a database if it does not exist.
     * Note that you should not use this method in production code. Instead, you should create the database tables via SQL scripts.
     * The method creates tables for User.
     *
     * @throws SQLException if creating the database fails
     */
    public static void createDB() throws SQLException {

        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {

            // Sql-statements for creating tables
            // NOTE: Passwords should never be stored in plain text in a real/production application. They should be hashed and salted.
            String createUserTable =
                    "CREATE TABLE IF NOT EXISTS Users (" +
                            "userId uuid primary key, " +
                            "username VARCHAR(255), " +
                            "password VARCHAR(255)" +
                            ");";
            executeStatement(connection, createUserTable);
        } catch (SQLException e) {
            throw new SQLException("Error creating tables: " + e.getMessage(), e);
        }
    }

    /**
     * Method that executes a sql statement.
     *
     * @param connection connection to the database
     * @param sql the sql statement to execute
     * @throws SQLException if executing the statement fails
     */
    private static void executeStatement(Connection connection, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error executing statement: " + e.getMessage(), e);
        }
    }
}
