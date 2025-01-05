package no.ntnu.idatx1005.demo.data;


import java.util.UUID;

/**
 * Class for the User object as saved in database
 */
public class User {
    private UUID userId;
    private String username;
    private String password;

    public User(){}

    public User(UUID userId, String username, String password){
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
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
}
