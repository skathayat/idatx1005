package no.ntnu.idatx1005.demo.view;

import no.ntnu.idatx1005.demo.dao.PostgresDBConnectionProvider;
import no.ntnu.idatx1005.demo.dao.UserDAO;
import no.ntnu.idatx1005.demo.data.User;

import java.awt.*;
import java.util.Random;
import java.util.UUID;
import javax.swing.*;

/**
 * Main window for my application!
 *
 * @author nilstes
 */
public class MyWindow extends JFrame {

    /**
     * Constructor for window
     *
     * @param  title  Title ow the window
     * @see         Image
     */
    public MyWindow(String title) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 10, 1, 1));


        // This piece of code is just for testing the database connection
        // First we create a user and add it to the database
        // Then we get all users from the database and print them to the window
        UserDAO userDao = new UserDAO(PostgresDBConnectionProvider.instance());
        userDao.addUser(new User(UUID.randomUUID(), "user " + new Random().nextInt(1000), "1234"));
        java.util.List<User> users = userDao.getUsers();
        for (User user : users) {
            add(new JLabel(user.getUsername()));
        }

        setPreferredSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        pack();
    }
}
