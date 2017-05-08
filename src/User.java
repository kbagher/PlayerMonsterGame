
public class User {

	private String username;
	private String password;
	private int win;
	private int loss;

	/*public User(String username, String password, int win, int loss) {
		super();
		this.username = username;
		this.password = password;
		this.win = win;
		this.loss = loss;
	}*/

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
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

}
