import java.sql.*;
import java.util.ArrayList;


/**
 * Provided username does not exist in the database
 */
class UserNotFoundException extends Exception {

    /**
     * Instantiates a new User not found exception.
     */
    public UserNotFoundException() {
        super("Invalid username");
    }
}

/**
 * The provided password is incorrect.
 */
class WrongPasswordException extends Exception {

    /**
     * Instantiates a new Wrong password exception.
     */
    public WrongPasswordException() {
        super("Provided password is incorrect");
    }
}

/**
 * The Username already exists in the database.
 */
class UsernameAlreadyExistsException extends Exception {

    /**
     * Instantiates a new Username already exists exception.
     */
    public UsernameAlreadyExistsException() {
        super("Provided username already exists in the database");
    }
}

/**
 * Saving user's data exception
 */
class DataSavingException extends Exception {

    /**
     * Instantiates a new Data Saving exception.
     *
     * @param message the message
     */
    public DataSavingException(String message) {
        super(message);
    }
}


/**
 * Loading user's data exception
 */
class DataLoadingException extends Exception {

    /**
     * Instantiates a new Data Loading exception.
     *
     * @param message the message
     */
    public DataLoadingException(String message) {
        super(message);
    }
}


/**
 * Database operations
 */
public class Database {

    /**
     * SQL Statement
     */
    private Statement stmt = null;
    /**
     * Database connection
     */
    private Connection conn = null;

    /**
     * Returns a database connection.
     *
     * @return database connection
     *
     * @throws SQLException           SQL exception
     * @throws ClassNotFoundException SQLite classpath issue
     */
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:./Database.db");
        conn.setAutoCommit(false);
        return conn;
    }

    /**
     * Check if the given username exists in the database or not
     *
     * @param username user's username
     *
     * @return true if the username exists in the database
     *
     * @throws SQLException           SQL Exception
     * @throws ClassNotFoundException SQLite classpath issue
     */
    private boolean usernameExists(String username) throws SQLException, ClassNotFoundException {
        conn = getConnection();
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM User where username='" + username + "';");
        if (rs.next()) {
            stmt.close();
            conn.close();
            return true;
        }
        stmt.close();
        conn.close();
        return false;
    }

    /**
     * User login.
     * <p>
     * If the provided username and password are correct, a user object will be returned
     *
     * @param username username
     * @param password password
     *
     * @return user object
     *
     * @throws SQLException           SQL error
     * @throws ClassNotFoundException SQLite class not found exception
     * @throws UserNotFoundException  username is incorrect
     * @throws WrongPasswordException incorrect password
     */
    public User login(String username, String password) throws SQLException, ClassNotFoundException, UserNotFoundException, WrongPasswordException {
        /*
          check if the given username exists or not
         */
        if (!usernameExists(username))
            throw new UserNotFoundException();

        User user = new User();
        conn = getConnection();
        stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM User where username='" + username + "' AND Password = '" + password + "';");

        if (rs.next()) {
            user.setName(rs.getString("Name"));
            user.setAddress(rs.getString("Address"));
            user.setUsername(rs.getString("Username"));
            user.setLoss(rs.getInt("Loss"));
            user.setWin(rs.getInt("Win"));
            stmt.close();
            conn.close();
            return user;
        } else {
            stmt.close();
            conn.close();
            throw new WrongPasswordException();
        }
    }

    /**
     * Register a new user
     *
     * @param name     the name
     * @param address  the address
     * @param username username
     * @param password password
     *
     * @return the boolean
     *
     * @throws SQLException                   SQLite exception
     * @throws ClassNotFoundException         SQLite class path not found
     * @throws UsernameAlreadyExistsException username already exists exception
     */
    public boolean register(String name, String address, String username, String password) throws SQLException, ClassNotFoundException, UsernameAlreadyExistsException {
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || address.isEmpty())
            throw new SQLException("Cannot register user. Make sure all fields are filled");

        System.out.println("checking user");
        if (usernameExists(username))
            throw new UsernameAlreadyExistsException();
        System.out.println("done checking user");
        conn = getConnection();
        stmt = conn.createStatement();
        System.out.println("in insert");
        String sql = "INSERT INTO User (Name,Address,username, password) " + "VALUES ('" + name + "','" + address + "','" + username + "','" + password + "');";
        System.out.println("after insert");
        /*
         * Number of effected rows.
         *
         * Should be 1 if the insert operation is successful
         */
        int i = stmt.executeUpdate(sql);

        stmt.close();
        conn.commit();
        conn.close();

        if (i > 0) return true; // registered successfully

        // Unknown error
        throw new SQLException("Cannot register user. Database error");
    }

    /**
     * Gets all user's results ordered by the top wins
     *
     * @return list of users
     *
     * @throws SQLException           sql exception
     * @throws ClassNotFoundException SQLite classpath issue
     */
    public ArrayList<User> getResults() throws SQLException, ClassNotFoundException {
        conn = getConnection();
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Name,Win,Loss FROM User ORDER BY Win DESC;");

        // users list
        ArrayList<User> users = new ArrayList<>();

        /*
         * retrieve all users and add them to the list
         */
        while (rs.next()) {
            User user = new User();
            user.setName(rs.getString("Name"));
            user.setLoss(rs.getInt("Loss"));
            user.setWin(rs.getInt("Win"));
            users.add(user);
        }

        rs.close();
        stmt.close();
        conn.close();
        return users;
    }

    /**
     * Save game data into user's profile
     *
     * @param username username
     * @param dataGame Game object
     *
     * @return the boolean
     *
     * @throws SQLException           the sql exception
     * @throws ClassNotFoundException SQLite classpath exception
     * @throws UserNotFoundException  user not found exception
     * @throws DataSavingException    the data saving exception
     */
    public boolean saveGame(String username, String dataGame) throws SQLException, ClassNotFoundException, UserNotFoundException, DataSavingException {
        try {
            return saveUserData(username, "GameData", dataGame);
        } catch (DataSavingException e) {
            throw new DataSavingException("Cannot save user's game data");
        }
    }

    /**
     * Load user's serialized data according to the given field
     *
     * @param username  user's username
     * @param fieldName database field containing the serialized data
     *
     * @return serialized data object
     *
     * @throws SQLException           SQL exception
     * @throws ClassNotFoundException SQLite classpath issue
     * @throws UserNotFoundException  user not found exception
     * @throws DataLoadingException   loading user's data exception
     */
    private String loadSerializedData(String username, String fieldName) throws SQLException, ClassNotFoundException, UserNotFoundException, DataLoadingException {
        /*
         * check if the given username exists or not
         */
        if (!usernameExists(username))
            throw new UserNotFoundException();

        conn = getConnection();
        stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT " + fieldName + " FROM User WHERE Username='" + username + "';");

        String serData = rs.getString(fieldName);

        rs.close();
        stmt.close();
        conn.close();

        if (serData == null || serData.isEmpty())
            throw new DataLoadingException(""); // no data available

        return serData;
    }


    /**
     * Load game user's data
     *
     * @param username user's username
     *
     * @return serialized game data
     *
     * @throws SQLException           sql exception
     * @throws ClassNotFoundException class not found exception
     * @throws UserNotFoundException  user not found exception
     * @throws DataLoadingException   data loading exception
     */
    public String loadGame(String username) throws SQLException, ClassNotFoundException, UserNotFoundException, DataLoadingException {
        try {
            return loadSerializedData(username, "GameData");
        } catch (DataLoadingException e) {
            throw new DataLoadingException("No game data is available");
        }
    }

    /**
     * Load user's settings data
     *
     * @param username user's username
     *
     * @return serialized settings data
     *
     * @throws UserNotFoundException  user not found exception
     * @throws SQLException           sql exception
     * @throws ClassNotFoundException class not found exception
     * @throws DataLoadingException   data loading exception
     */
    public String loadSettings(String username) throws UserNotFoundException, SQLException, ClassNotFoundException, DataLoadingException {
        try {
            return loadSerializedData(username, "Settings");
        }catch (DataLoadingException e){
            throw new DataLoadingException("No Settings data is available");
        }
        catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param username  user's username
     * @param fieldName database field name
     *
     * @return true if the field was increased successfully
     *
     * @throws SQLException           SQL exception
     * @throws ClassNotFoundException SQLite classpath issue
     */
    private boolean increaseFiled(String username, String fieldName) throws SQLException, ClassNotFoundException {

        conn = getConnection();
        stmt = conn.createStatement();
        String sql = "UPDATE User SET " + fieldName + " = " + fieldName + " +1 WHERE Username='" + username + "';";
        int i = stmt.executeUpdate(sql);

        stmt.close();
        conn.commit();
        conn.close();

        if (i > 0) return true; // field increased successfully

        // Unknown error
        throw new SQLException("Cannot save game data. Database error");
    }

    /**
     * Increase user's loss.
     *
     * @param username user's username
     *
     * @return true if loss was increased successfully
     *
     * @throws SQLException           the sql exception
     * @throws ClassNotFoundException the class not found exception
     */
    public boolean increaseLoss(String username) throws SQLException, ClassNotFoundException {
        return increaseFiled(username, "Loss");
    }

    /**
     * Increase user's wins.
     *
     * @param username user's username
     *
     * @return true if  win was increased successfully
     *
     * @throws SQLException           the sql exception
     * @throws ClassNotFoundException the class not found exception
     */
    public boolean increaseWins(String username) throws SQLException, ClassNotFoundException {
        return increaseFiled(username, "Win");
    }

    /**
     * Save user's settings
     *
     * @param username the username
     * @param settings the settings
     *
     * @return the boolean
     *
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the sql exception
     * @throws DataSavingException    the data saving exception
     * @throws UserNotFoundException  the user not found exception
     */
    public boolean saveSettings(String username, String settings) throws ClassNotFoundException, SQLException, DataSavingException, UserNotFoundException {
        try {
            return saveUserData(username, "Settings", settings);
        } catch (DataSavingException e) {
            throw new DataSavingException("Cannot save user's settings");
        }
    }

    /**
     * Save user's data in the given database column
     *
     * @param username  user's username
     * @param fieldName database field name
     * @param data      serialized data
     *
     * @return serialized game data
     *
     * @throws SQLException           SQL exception
     * @throws ClassNotFoundException SQLite classpath issue
     * @throws UserNotFoundException  user not found exception
     * @throws DataSavingException    saving user's data exception
     */
    private boolean saveUserData(String username, String fieldName, String data) throws SQLException, ClassNotFoundException, UserNotFoundException, DataSavingException {
        /*
          check if the given username exists or not
         */
        if (!usernameExists(username))
            throw new UserNotFoundException();

        conn = getConnection();
        stmt = conn.createStatement();
        String sql = "UPDATE User SET " + fieldName + " = '" + data + "' WHERE Username= '" + username + "';";

        int i = stmt.executeUpdate(sql);

        stmt.close();
        conn.commit();
        conn.close();

        if (i > 0) return true;

        // Unknown error
        throw new DataSavingException("");
    }

}
