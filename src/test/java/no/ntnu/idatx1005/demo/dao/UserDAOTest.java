package no.ntnu.idatx1005.demo.dao;

public class UserDAOTest {

    /*
    private Connection connection;
    private Statement statement;
    private UserDAO userDAO;

    static MySQLContainer<?> mysql = new MySQLContainer<>(
            "mysql"
    );

    @BeforeAll
    static void beforeAll() {
        mysql.start();
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }

    @BeforeEach
    public void SetUp () {
        try {
            DBConnectionProvider connectionProvider = new DBConnectionProvider(
                    mysql.getJdbcUrl(),
                    mysql.getUsername(),
                    mysql.getPassword()
            );
            connection = connectionProvider.getConnection();
            userDAO = new UserDAO(connectionProvider);
            statement = connection.createStatement();

        } catch (SQLException se) {
            System.out.println("Could not connect to test-database");
            se.printStackTrace();
        }

        //DROP TABLE queries
        String dropTableUser = "DROP TABLE IF EXISTS user";


        //CREATE TABLE
        String createTableUser =
                "CREATE TABLE user (\n" +
                        "  userId INT(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  username VARCHAR(64) NOT NULL UNIQUE,\n" +
                        "  password TEXT,\n" +
                        "  salt BLOB,\n" +
                        "  PRIMARY KEY (userId)\n" +
                        ")";


        //INSERT INTO statements
        String inserIntoUser = "INSERT INTO user (username) VALUES ('username1')";
        String inserIntoUser2 = "INSERT INTO user (username) VALUES ('username2')";


        try {
            // execute queries
            statement.executeUpdate(dropTableUser);
            statement.executeUpdate(createTableUser);

            // execute insert updates
            statement.executeUpdate(inserIntoUser);
            statement.executeUpdate(inserIntoUser2);

        } catch (SQLException e) {
            System.out.println("Error: insert statements");
            e.printStackTrace();

        } finally {
            try {
                statement.close();

            } catch (SQLException se) {
                se.printStackTrace();
            }

        }
    }

    @AfterEach
    public void tearDown () {
        try {
            userDAO = null;
            connection.close();

        } catch (SQLException se) {
            System.out.println("disconnection failed");
            se.printStackTrace();
        }
    }


    @Test
    public void testGetUsers () {
        List<User> userlist = userDAO.getUsers();
        assertEquals(userlist.get(0).getUserId(),1);
        assertEquals(userlist.get(0).getUsername(),"username1");
        assertEquals(2,userDAO.getUsers().size());
    }

    @Test
    public void testGetUserByUsername () {
        User user = userDAO.getUserByUsername("username1");
        User user2 = userDAO.getUserByUsername("username2");
        assertEquals("username1",user.getUsername());
        assertEquals("username2",user2.getUsername());
        assertNotEquals(user.getUsername(),user2.getUsername());
    }

    @Test
    public void testUpdateUser () {
        assertTrue(userDAO.editUser(1,"newuser",null));
        List<User> userlist = userDAO.getUsers();
        assertEquals(userlist.get(0).getUsername(),"newuser");
    }


    @Test
    public void testAddUser () {
        List<User> beforeNewUser = userDAO.getUsers();
        User user = new User(3,"username3", "password",userDAO.generateSalt());
        userDAO.addUser(user);
        List<User> tmp = userDAO.getUsers();
        List<String> afterNewUser = new ArrayList<>();

        for (User user1 : tmp) {
            afterNewUser.add(user1.getUsername());
        }

        String expectedUsername = "username3";
        String actualUsername = user.getUsername();
        assertEquals(expectedUsername,actualUsername);
        assertNotEquals(beforeNewUser,afterNewUser);
    }

    */

}
