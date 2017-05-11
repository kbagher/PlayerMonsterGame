import javax.xml.crypto.Data;
import java.io.*;
import java.util.Base64;

public class User implements Serializable {

    private String username;
    private String password;
    private int win;
    private int loss;

    public static User login(String username, String password) {
        return Database.getInstance().login(username, password);
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


    public boolean saveGame(Game g,Settings s) {
        try {
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutputStream oStream = new ObjectOutputStream(bStream);
            oStream.writeObject(g);
            oStream.flush();
            oStream.close();
            Database.getInstance().saveGame(username, Base64.getEncoder().encodeToString(bStream.toByteArray()));

            bStream = new ByteArrayOutputStream();
            oStream = new ObjectOutputStream(bStream);
            oStream.writeObject(s);
            oStream.flush();
            oStream.close();
            Database.getInstance().saveSetings(username, Base64.getEncoder().encodeToString(bStream.toByteArray()));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean increaseLoss(){
        try {
            return Database.getInstance().increaseLoss(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean increaseWins(){
        try {
            return Database.getInstance().increaseWins(username);
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
            return Database.getInstance().saveSetings(username, Base64.getEncoder().encodeToString(bStream.toByteArray()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Settings loadSettings() {
        try {
            byte[] settingsData = Base64.getDecoder().decode(Database.getInstance().loadSettings(username));
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
            byte[] gameData = Base64.getDecoder().decode(Database.getInstance().loadGame(username));
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
