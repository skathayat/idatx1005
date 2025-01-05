package no.ntnu.idatx1005.demo.dao;

import no.ntnu.idatx1005.demo.data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static no.ntnu.idatx1005.demo.dao.DBConnectionProvider.close;


/**
 * Data access object for User
 */
public class UserDAO {
    private DBConnectionProvider connectionProvider;

    public UserDAO(DBConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    /**
     * Returns a List of all registered users
     *
     * @return List of Users
     */
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Users");
            resultSet = preparedStatement.executeQuery();

            User user;
            while (resultSet.next()) {
                user = new User();
                user.setUserId(UUID.fromString(resultSet.getString("userId")));
                user.setUsername(resultSet.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return users;
    }

    /**
     * Helping method to get a User from a ResultSet
     *
     * @param resultSet ResultSet with the user
     * @return User object, or null if unsuccessful
     */
    private User getUserFromResultSet(ResultSet resultSet) {
        User user = null;
        try {
            if (resultSet.next()) {
                user = new User();
                user.setUserId(UUID.fromString(resultSet.getString("userId")));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(null, null, resultSet);
        }
        return user;
    }

    /**
     * Adds a new user to database with default ID
     *
     * @param user User object
     * @return new User or already registered user
     */
    public User addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //User userFromDb = getUserByUsername(user.getUsername());

        //Note: for simplicity in this example, we do not check if the user already exists. In a real application, you should check if the user already exists
        try {
            connection = connectionProvider.getConnection();
            User newUser = new User();
            preparedStatement = connection.prepareStatement("INSERT INTO Users (userId, username, password) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, user.getUserId(), Types.OTHER);
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            int result = preparedStatement.executeUpdate();

            if (result == 1) {
                newUser.setUsername(user.getUsername());
                newUser.setUserId(user.getUserId());
                return newUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return new User();
    }

}

