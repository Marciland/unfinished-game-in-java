package launcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import database.MySQL;
import test.MainGameLoop;

public class Launcher {

	private Window window;
	private static MySQL sql = new MySQL();
	protected static ArrayList<String> usernames = new ArrayList<String>();
	protected static ArrayList<String> emails = new ArrayList<String>();
	protected static ArrayList<String> passwords = new ArrayList<String>();
	protected final String title =  "This is u - Launcher v. " + MainGameLoop.version;
	private boolean startGame = false;

	public Launcher() {

		try {
			getUsers();
		} catch (SQLException ignored) {
			System.out.println(ignored);
		}
		window = new Window(title);
		while(!Login.getSuccess()) {
			System.out.println(Login.getSuccess());
			//update serverList status?
			//update news/events
		}
		window.login();
	}
	private static void getUsers() throws SQLException {
		sql.connect("login");
		sql.executeQuery("SELECT username, password, email FROM users;");
		ResultSet rs = sql.getResultSet();
		usernames.clear();
		passwords.clear();
		emails.clear();
		while(rs.next()) {
			usernames.add(rs.getString("username"));	
			passwords.add(rs.getString("password"));
			emails.add(rs.getString("email"));
		}
		rs.close();
		MySQL.disconnect();
	}

	protected static int newUser(String username, String email) {
		int result = 0;
		try {
			getUsers();
		} catch (SQLException ignored) {
			System.out.println(ignored);
		}

		if(usernames.contains(username) || emails.contains(email)) {
			if(usernames.contains(username)) {
				result = 1;
				return result;	
			}else if(emails.contains(email)) {
				result = 2;
				return result;
			}
		}else {
			result = 3;
			return result;
		}
		return result;
	}

	public void close() {
		window.dispose();
	}

	public boolean getStartGame() {
		return startGame;
	}
}