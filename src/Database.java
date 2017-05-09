import java.sql.*;
import java.util.ArrayList;
public class Database {

	Statement stmt = null;
	Connection conn = null;

	public Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(
					"jdbc:sqlite:C:\\Users\\khalidLAB\\Desktop\\RMIT\\Semester 1\\FP\\Assignment 2\\bin\\PlayerDB.db");
			conn.setAutoCommit(false);
			System.out.println("Database created successfully");

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
			ResultSet rs = stmt.executeQuery("SELECT * FROM UserDB where username='" + username + "';");

			if (rs.next()) {
				if (rs.getString("Password").equals(password)) {
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
					return new User();
				}
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
			String sql = "INSERT INTO UserDB (username, password) " + "VALUES ('" + username + "','" + password + "');";
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM UserDB ORDER BY Win DESC;");

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
			String sql = "UPDATE UserDB SET Game='" + dataGame + "' WHERE Username ='" + username + "';";
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
			ResultSet rs = stmt.executeQuery("SELECT GameData FROM UserDB WHERE Username='" + Username + "';");
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

	public boolean saveSeting(String usernmame) {
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql = "UPDATE UserDB SET" + " CALORIES_INITIAL_VALUE= " + Settings.getCaloriesInitialValue()
					+ ", GAME_SPEED=" + Settings.getGameSpeed() + ", NOUGAT_CALORIES=" + Settings.getNougatCalories()
					+ ", STEP_CALORIES=" + Settings.getStepCalories() + ", TIME_ALLOWED=" + Settings.getTimeAllowed()
					+ ", TRAP_EFFECT_DURATION=" + Settings.getTrapEffectDuration() + ",TRAP_REQUIRED_ENERGY="
					+ Settings.getTrapRequiredEnergy() + " WHERE Username='" + usernmame + "';";
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
