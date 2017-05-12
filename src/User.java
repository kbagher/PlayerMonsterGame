import java.io.*;
import java.sql.SQLException;
import java.util.Base64;

/**
 * User class.
 * <p>
 * This class will store user's information, game settings and game data
 */
public class User implements Serializable {

    private String name;
    private String address;
    private String username;
    private int win;
    private int loss;

    /**
     * User login
     *
     * @param username username
     * @param password password
     *
     * @return user object
     *
     * @throws ClassNotFoundException SQLite classpath issue exception
     * @throws SQLException           sql exception
     * @throws WrongPasswordException wrong password exception
     * @throws UserNotFoundException  user not found exception
     */
    public static User login(String username, String password) throws ClassNotFoundException, SQLException, WrongPasswordException, UserNotFoundException {
        Database db = new Database();
        return db.login(username, password);
    }

    /**
     * Register new user.
     *
     * @param name     full name
     * @param address  address
     * @param username username
     * @param password password
     *
     * @return true if user has registered successfully
     *
     * @throws ClassNotFoundException         SQLite classpath issue exception
     * @throws SQLException                   sql exception
     * @throws UsernameAlreadyExistsException username already exists exception
     */
    public static boolean register(String name, String address, String username, String password) throws ClassNotFoundException, SQLException, UsernameAlreadyExistsException {
        Database db = new Database();
        return db.register(name, address, username, password);
    }

    /**
     * Gets username
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets total number of wins.
     *
     * @return the win
     */
    public int getWin() {
        return win;
    }

    /**
     * Sets number of wins.
     *
     * @param win the win
     */
    public void setWin(int win) {
        this.win = win;
    }

    /**
     * Gets total number of loss.
     *
     * @return the loss
     */
    public int getLoss() {
        return loss;
    }

    /**
     * Sets loss.
     *
     * @param loss the loss
     */
    public void setLoss(int loss) {
        this.loss = loss;
    }

    /**
     * Gets full name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Save users current game.
     * <p>
     * This method will serialize the objects into
     * a Base64 string and store it in the database
     *
     * @param g the Game object
     * @param s the Settings object
     *
     * @return true if game and settings are saved
     */
    public void saveGame(Game g, Settings s) throws IOException, ClassNotFoundException, SQLException, DataSavingException, UserNotFoundException {
        /*
         * Serialize and save the game object
         */
        Database db = new Database();
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutputStream oStream = new ObjectOutputStream(bStream);
        oStream.writeObject(g);
        oStream.flush();
        oStream.close();
        db.saveGame(username, Base64.getEncoder().encodeToString(bStream.toByteArray()));

        /*
         * Serialize and save the settings object
         */
        bStream = new ByteArrayOutputStream();
        oStream = new ObjectOutputStream(bStream);
        oStream.writeObject(s);
        oStream.flush();
        oStream.close();
        db.saveSettings(username, Base64.getEncoder().encodeToString(bStream.toByteArray()));
    }


    /**
     * Increase total number of loss by 1.
     *
     * @return true if the number of loss was increased successfully
     */
    public boolean increaseLoss() {
        try {
            Database db = new Database();
            return db.increaseLoss(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Increase total number of wins.
     *
     * @return true if the number of wins was increased successfully
     */
    public boolean increaseWins() {
        try {
            Database db = new Database();
            return db.increaseWins(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Save users current game settings.
     * <p>
     * This method will serialize the settings object into
     * a Base64 string and store it in the database
     *
     * @param s the {Settings} object
     *
     * @return the boolean
     */
    public boolean saveSettings(Settings s) throws IOException, ClassNotFoundException, SQLException, DataSavingException, UserNotFoundException {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutputStream oStream = new ObjectOutputStream(bStream);
        oStream.writeObject(s);
        oStream.close();
        Database db = new Database();
        return db.saveSettings(username, Base64.getEncoder().encodeToString(bStream.toByteArray()));
    }

    /**
     * Load user's settings
     *
     * @return {Settings} object
     */
    public Settings loadSettings() throws UserNotFoundException, SQLException, ClassNotFoundException, DataLoadingException, IOException {
        Database db = new Database();
        byte[] settingsData = Base64.getDecoder().decode(db.loadSettings(username));
        ObjectInputStream oStream = new ObjectInputStream(new ByteArrayInputStream(settingsData));
        Settings s = (Settings) oStream.readObject();
        oStream.close();
        return s;
    }

    /**
     * Load user's game.
     *
     * @return the {Game} object
     */
    public Game loadGame() {
        try {
            Database db = new Database();
            byte[] gameData = Base64.getDecoder().decode(db.loadGame(username));
            ObjectInputStream oStream = new ObjectInputStream(new ByteArrayInputStream(gameData));
            Game g = (Game) oStream.readObject();
            oStream.close();
            return g;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
