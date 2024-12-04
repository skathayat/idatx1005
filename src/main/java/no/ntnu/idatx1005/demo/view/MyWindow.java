package no.ntnu.idatx1005.demo.view;

import no.ntnu.idatx1005.demo.dao.DBConnectionProvider;
import no.ntnu.idatx1005.demo.dao.UserDAO;
import no.ntnu.idatx1005.demo.data.User;

import java.awt.*;
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
        setLayout(new GridLayout(1, 1, 1, 1));

        // get list of users from database and add them to the window
        UserDAO userDao = new UserDAO(DBConnectionProvider.instance());
        java.util.List<User> users = userDao.getUsers();
        for (User user : users) {
            add(new JLabel(user.getUsername()));
        }

        pack();
    }
}
