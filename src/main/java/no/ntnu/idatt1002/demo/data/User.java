package no.ntnu.idatt1002.demo.data;


/**
 * Class for the User object as saved in database
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private byte[] salt;

    public User(){}

    public User(int userId, String username, String password, byte[] salt){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
