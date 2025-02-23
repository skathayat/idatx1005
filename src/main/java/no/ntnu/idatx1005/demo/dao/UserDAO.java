package no.ntnu.idatx1005.demo.dao;

import no.ntnu.idatx1005.demo.data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Data access object for User
 */
public class UserDAO {
    private PostgresDBConnectionProvider connectionProvider;

    public UserDAO(PostgresDBConnectionProvider connectionProvider) {
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
            preparedStatement = connection.prepareStatement("SELECT * FROM userinfo");
            resultSet = preparedStatement.executeQuery();

            User user;
            while (resultSet.next()) {
                user = new User();
                user.setUserid(UUID.fromString(resultSet.getString("userId")));
                user.setUsername(resultSet.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            PostgresDBConnectionProvider.close(connection, preparedStatement, resultSet);
        }
        return users;
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

        // check if user already exists in database
        User existingUser = getUsers().stream().allMatch(u -> u.getUsername().equals(user.getUsername())) ? user : null;
        if(existingUser != null) {
            System.out.println("User already exists");
            return existingUser;
        }

        try {
            connection = connectionProvider.getConnection();
            User newUser = new User();
            preparedStatement = connection.prepareStatement("INSERT INTO userinfo (userId, username, password) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, user.getUserid(), Types.OTHER);
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            int result = preparedStatement.executeUpdate();

            if (result == 1) {
                newUser.setUsername(user.getUsername());
                newUser.setUserid(user.getUserid());
                return newUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            PostgresDBConnectionProvider.close(connection, preparedStatement, resultSet);
        }

        return new User();
    }

}

