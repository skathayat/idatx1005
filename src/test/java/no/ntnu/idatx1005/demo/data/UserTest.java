package no.ntnu.idatx1005.demo.data;

import org.junit.jupiter.api.Test;

import java.util.UUID;


public class UserTest {

    @Test
    public void userConstructorTest() {
        User user = new User(UUID.randomUUID(), "username", "password");
        assert(user.getUsername().equals("username"));
    }
}
