import javax.xml.crypto.Data;
import java.io.*;
import java.sql.SQLException;
import java.util.Base64;

public class User implements Serializable {

    private String name;
    private String address;
    private String username;
    private String password;
    private int win;
    private int loss;

    public static User login(String username, String password) throws ClassNotFoundException, SQLException, WrongPasswordException, UserNotFoundException {
        Database db = new Database();
        return db.login(username, password);
    }

    public static boolean register(String name,String address,String username, String password) throws ClassNotFoundException, SQLException, UsernameAlreadyExistsException {
        Database db = new Database();
        return db.register(name,address,username,password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean saveGame(Game g,Settings s) {
        try {
            Database db = new Database();
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutputStream oStream = new ObjectOutputStream(bStream);
            oStream.writeObject(g);
            oStream.flush();
            oStream.close();
            db.saveGame(username, Base64.getEncoder().encodeToString(bStream.toByteArray()));

            bStream = new ByteArrayOutputStream();
            oStream = new ObjectOutputStream(bStream);
            oStream.writeObject(s);
            oStream.flush();
            oStream.close();
            db.saveSetings(username, Base64.getEncoder().encodeToString(bStream.toByteArray()));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean increaseLoss(){
        try {
            Database db = new Database();
            return db.increaseLoss(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean increaseWins(){
        try {
            Database db = new Database();
            return db.increaseWins(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean saveSettings(Settings s) {
        try {
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutputStream oStream = new ObjectOutputStream(bStream);
            oStream.writeObject(s);
            oStream.close();
            Database db = new Database();
            return db.saveSetings(username, Base64.getEncoder().encodeToString(bStream.toByteArray()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Settings loadSettings() {
        try {
            Database db = new Database();
            byte[] settingsData = Base64.getDecoder().decode(db.loadSettings(username));
            ObjectInputStream oStream = new ObjectInputStream(new ByteArrayInputStream(settingsData));
            Settings s = (Settings) oStream.readObject();
            oStream.close();
            return s;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

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
