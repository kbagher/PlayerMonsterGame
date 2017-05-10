import java.sql.*;
import java.util.ArrayList;

public class Database {

    Statement stmt = null;
    Connection conn = null;

    private static Database databaseInstance = new Database();

    public static Database getInstance() {
        return databaseInstance;
    }

    private Database(){}

    private Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:./Database.db");
            conn.setAutoCommit(false);
            System.out.println("connected to database successfully");

            return conn;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }

    }

    public User login(String username, String password) {
        User user = new User();
        try {

            conn = getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User where username='" + username + "' AND Password = '" +password+"';");

            if (rs.next()) {
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setLoss(rs.getInt("Loss"));
                user.setWin(rs.getInt("Win"));
                stmt.close();
                conn.close();
                return user;
            } else {
                stmt.close();
                conn.close();
                return null;
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }

    }

    public boolean register(String username, String password) {
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "INSERT INTO User (username, password) " + "VALUES ('" + username + "','" + password + "');";
            int i = stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            conn.close();
            if (i > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;

        }

    }

    public ArrayList<User> getResult() {
        User user = new User();
        ArrayList<User> users = new ArrayList<User>();

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User ORDER BY Win DESC;");

            while (rs.next()) {

                user.setUsername(rs.getString("Username"));
                user.setLoss(rs.getInt("Loss"));
                user.setWin(rs.getInt("Win"));
                users.add(user);
                System.out.println(user.getUsername());
                System.out.println(user.getWin());
                System.out.println(user.getLoss());

            }
            rs.close();
            stmt.close();
            conn.close();
            return users;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }

    }

    public boolean saveGame(String username, String dataGame) {

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "UPDATE User SET GameData='" + dataGame + "' WHERE Username ='" + username + "';";
            int i = stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            conn.close();
            if (i > 0) {

                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }

    }

    public String loadGame(String Username) {

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT GameData FROM User WHERE Username='" + Username + "';");
            if (rs.next()) {
                String temp = rs.getString("GameData");
                rs.close();
                stmt.close();
                conn.close();
                return temp;
            }
            rs.close();
            stmt.close();
            conn.close();
            return null;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }

    }

    private boolean increaseFiled(String usernmame, String fieldName) throws Exception {
        conn = getConnection();
        stmt = conn.createStatement();
        String sql = "UPDATE User SET " + fieldName + " = " + fieldName + " +1 WHERE Username='" + usernmame + "';";
        int i = stmt.executeUpdate(sql);

        stmt.close();
        conn.commit();
        conn.close();
        if (i > 0) {

            return true;
        }
        return false;
    }

    public boolean increaseLoss(String usernmame) {
        try {
            return increaseFiled(usernmame, "Loss");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public boolean increaseWins(String usernmame) {
        try {
            return increaseFiled(usernmame, "Win");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public String loadSettings(String Username) {

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Settings FROM User WHERE Username='" + Username + "';");
            if (rs.next()) {
                String temp = rs.getString("Settings");
                rs.close();
                stmt.close();
                conn.close();
                return temp;
            }
            rs.close();
            stmt.close();
            conn.close();
            return null;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }

    }


    public boolean saveSetings(String usernmame,String settings) {
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "UPDATE User SET Settings = '" + settings + "' WHERE Username= '" + usernmame + "';";
            int i = stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            conn.close();
            if (i > 0) {

                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }
}
