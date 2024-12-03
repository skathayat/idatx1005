package no.ntnu.idatt1002.demo.dao;

import no.ntnu.idatt1002.demo.data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static no.ntnu.idatt1002.demo.dao.DBConnectionProvider.close;


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
     * @return List of Users
     */
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user");
            resultSet = preparedStatement.executeQuery();

            User user;
            while (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            close(connection, preparedStatement, resultSet);
        }
        return users;
    }

    /**
     * Returns a User object for given username
     * @param username Username as String
     * @return requested user object if found, null if not found
     */
    public User getUserByUsername(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username=?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            user = getUserFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(connection, preparedStatement, resultSet);
        }
        return user;
    }

    /**
     * Returns a User object for given userId
     * @param userId userId as int
     * @return requested user object if found, null if not found
     */
    private User getUserById(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE userId=?");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            user = getUserFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection, preparedStatement, resultSet);
        }
        return user;
    }

    /**
     * Helping method to get a User from a ResultSet
     * @param resultSet ResultSet with the user
     * @return User object, or null if unsuccessful
     */
    private User getUserFromResultSet(ResultSet resultSet){
        User user = null;
        try{
            if(resultSet.next()){
                user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setSalt(resultSet.getBytes("salt"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(null, null, resultSet);
        }
        return user;
    }

    /**
     * Adds a new user to database with default ID
     * @param user User object
     * @return new User or already registered user
     */
    public User addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User userFromDb = getUserByUsername(user.getUsername());
        if(userFromDb == null){
            try{
                connection = connectionProvider.getConnection();
                User newUser = new User();
                byte[] salt = generateSalt();
                String hashedPassword = hashPassword(user.getPassword(), salt);

                preparedStatement = connection.prepareStatement("INSERT INTO user (username, password, salt) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setBytes(3, salt);
                int result = preparedStatement.executeUpdate();

                newUser.setUsername(user.getUsername());

                if (result == 1) {
                    resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet.next()) newUser.setUserId(resultSet.getInt(1));
                    return newUser;
                }

            }catch(SQLException e){
                e.printStackTrace();

            }finally{
                close(connection, preparedStatement, resultSet);
            }
        }else {
            //Validate password
            byte[] salt = userFromDb.getSalt();
            String hashedPassword = userFromDb.getPassword();


            if (salt == null || hashedPassword == null) {
                User response = new User();
                response.setUsername(userFromDb.getUsername());
                response.setUserId(userFromDb.getUserId());
                return response;
            }

            String hash = hashPassword(user.getPassword(), salt);
            if(hashedPassword.equals(hash)) {
                User response = new User();
                response.setUsername(userFromDb.getUsername());
                response.setUserId(userFromDb.getUserId());
                return response;
            }
        }
        return new User();
    }

    /**
     * Generates a salt, for hashing
     * @return a random salt
     */
    public byte[] generateSalt() {
        return null;
    }

    /**
     * Method to hash a password with salt
     * @param password password to be hashed
     * @param salt salt to use when hashing
     * @return hashedPassword, null if unsuccessful
     */
    public String hashPassword(String password, byte[] salt) {
        return null;
    }

    /**
     * Edits the users username or password
     * @param userId userId as int
     * @param username Username as String
     * @param password password as String, if null it should not be updated
     * @return true if success on new username, false if not
     */
    public boolean editUser (int userId, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean updatePassword = false;
        boolean updatePasswordWithSalt = false;
        String sql;
        String hashedPassword = "";
        User user = null;
        if(password == null){
            sql = "UPDATE user SET username = ? WHERE userId = ?";
        }else {
            user = getUserById(userId);
            if(user.getPassword() == null){
                user.setSalt(generateSalt());
                hashedPassword = hashPassword(password, user.getSalt());
                sql = "UPDATE user SET username = ?, password = ?, salt = ? WHERE userId = ?";
                updatePasswordWithSalt = true;
            }else{
                hashedPassword = hashPassword(password, user.getSalt());
                sql = "UPDATE user SET username = ?, password = ? WHERE userId = ?";
                updatePassword = true;
            }
        }

        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            if(updatePassword){
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setInt(3,userId);
            }else if(updatePasswordWithSalt) {
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setBytes(3, user.getSalt());
                preparedStatement.setInt(4, userId);
            }else{
                preparedStatement.setInt(2,userId);
            }
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            close(connection, preparedStatement, null);
        }
        return false;
    }
}

