/*
 * Name : TankGameServer.java
 *
 * Function : This is the tank game server
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package tankGameServer;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * This is the tank game server
 * 
 * @author Zevin
 *
 */
public class TankGameServer implements Runnable {

	/** Set the server */
	private static ServerSocket server;

	/** There're two players in each game */
	private static Socket player1;
	private static Socket player2;

	/** Set the input and output stream */
	private static BufferedReader bufPlayer1 = null;
	private static BufferedReader bufPlayer2 = null;
	private static PrintStream printPlayer1;
	private static PrintStream printPlayer2;

	/** Set the map path */
	private static String fileName = "MAP.txt";
	static String map = "";

	/** Used to connect the sql */
	private static Connection conn;
	private static String player1UserName, player2UserName;
	private static Statement stat;
	static int index = 0;

	/** Set two players */
	private static Player playerOne;
	private static Player playerTwo;
	private static Socket player;

	/** Set the singleton */
	private static TankGameServer gameServer;

	private TankGameServer() {

		File file = new File(fileName);
		try {

			// Read the map information from the map file
			BufferedReader buf = new BufferedReader(new FileReader(file));
			String s = "";
			int i = 0;
			while ((s = buf.readLine()) != null) {
				map += s + "\n";
				i++;
			}
			map += "end123";

			// Connect the sql
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tankGame?useSSL=false&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "root", "123456");
			stat = conn.createStatement();
			DatabaseMetaData dbMetaData = conn.getMetaData();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static synchronized TankGameServer getInstance() {
		if (gameServer == null) {
			gameServer = new TankGameServer();
		}
		return gameServer;
	}

	/**
	 * Get the PDF file
	 * 
	 * @param content
	 * @return boolean
	 */
	public static boolean getPDF(String content, String name) {

		try {

			// Get the message from the "rankinformation" form in the sql
			String sql = "SELECT UserName, Score, Time, MAX FROM rankinformation;";
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			String tem = "";
			while (rs.next()) {
				if (rs.getString(1).equals(name)) {
					long l = System.currentTimeMillis();
					Date time = new Date(l);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyƒÍMM‘¬dd»’");

					// Set the content
					tem += name + "," + rs.getString(4) + "," + sdf.format(time) + ",end123";
					break;
				}
			}

			// Get the PDF file
			new GetPDF(tem);

			// Send the PDF file to the player
			SendMail send = new SendMail();
			send.sendEmail(content.split(",")[1], "Server");

			// If there is no exception, return true
			return true;
		} catch (Exception e) {

			// If there is some exceotions, return false
			return false;
		}

	}

	/**
	 * Get the game information
	 * 
	 * @param type
	 * @param playerName
	 * @return String
	 * @throws SQLException
	 */
	public static String gameMessage(String type, String playerName) throws SQLException {

		String s = "";

		// Different types means different ui
		if (type.equals("gameInformation")) {

			// Get the information from the sql
			String sql = "SELECT UserName, Competitor, YourScore, CompetitorScore, GameTime FROM record;";
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if (playerName.equals(rs.getString(1))) {
					s += rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getInt(4) + ","
							+ rs.getString(5) + "end123";
				}
			}
		} else if (type.equals("highestScore")) {

			// Get the information from the sql
			String sql = "SELECT UserName, Competitor, YourScore, CompetitorScore, GameTime FROM record;";
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();

			boolean isFirst = false;

			// Find the player's highest score
			while (rs.next()) {
				if (!isFirst && playerName.equals(rs.getString(1))) {
					s = rs.getString(3);
					isFirst = true;
				} else if (playerName.equals(rs.getString(1))) {
					if (Integer.valueOf(s) < Integer.valueOf(rs.getString(3))) {
						s = rs.getString(3);
					}
				}
			}

		} else if (type.equals("most")) {

			// Get the information from the sql
			String sql = "SELECT UserName, Score, Time, MAX FROM rankinformation;";
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();

			boolean isFirst = false;
			String tem = "";

			// Find the highest score in the sql
			while (rs.next()) {

				if (!isFirst) {
					s = rs.getString(2);
					tem = rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + ",end123";
					isFirst = true;
				} else {
					if (Integer.valueOf(s) < Integer.valueOf(rs.getString(2))) {
						
						s = rs.getString(2);
						tem = rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + ",end123";
					}
				}
			}
			s = tem;
		}
		return s;
	}

	/**
	 * Get the user information
	 * 
	 * @param playerUserName
	 * @return String
	 * @throws SQLException
	 */
	public static String userMessage(String playerUserName) throws SQLException {

		// Get the information from the sql
		String sql = "SELECT UserName, Password, TimeCreated, Number, PhoneNumber FROM userInformation;";
		PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		String s = "";

		// Find the user information
		while (rs.next()) {
			if (playerUserName.equals(rs.getString(1))) {
				s += rs.getString(1) + "," + rs.getString(3) + "," + rs.getInt(4) + "," + rs.getString(5) + ",end123";
				break;
			}

		}
		return s;
	}

	/**
	 * Reset the password
	 * 
	 * @param order
	 * @param pNum
	 * @return boolean
	 */
	public static boolean forget(String order, String pNum) {

		// Get the information from the sql
		String sql1 = "Update userInformation set Password=? where PhoneNumber=?";
		PreparedStatement presta;
		try {

			// Reset the password
			presta = conn.prepareStatement(sql1);
			presta.setString(2, pNum);
			presta.setString(1, order.split(",")[1]);
			presta.execute();

			// If there is no exception, return true
			return true;
		} catch (SQLException e) {

			// If there is some exceotions, return false
			return false;
		}
	}

	/**
	 * Set a new account
	 * 
	 * @param s
	 * @return boolean
	 */
	public static boolean singUp(String s) {
		try {

			// Add the information from the sql
			String sql = "INSERT INTO userInformation(UserName,Password,TimeCreated,Number,PhoneNumber) VALUES (?,?,?,?,?)";

			long l = System.currentTimeMillis();
			Date time = new Date(l);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			PreparedStatement presta = conn.prepareStatement(sql);

			// Add a new account
			presta.setString(1, s.split(",")[1]);
			presta.setString(2, s.split(",")[2]);
			presta.setString(3, sdf.format(time));
			presta.setInt(4, 0);
			presta.setString(5, s.split(",")[3]);

			presta.execute();

			// Add the information
			sql = "INSERT INTO rankinformation(UserName, Score) VALUES (?, ?)";

			presta = conn.prepareStatement(sql);

			// Add the value
			presta.setString(1, s.split(",")[1]);
			presta.setInt(2, 0);

			presta.execute();

			// If there is no exception, return true
			return true;
		} catch (Exception e) {

			// If there is some exceptions, return false
			return false;
		}
	}

	/**
	 * Judge whether the password and user name is valid
	 * 
	 * @param userName
	 * @param st
	 * @param type
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean judgePassword(String userName, String st, String type) throws SQLException {

		// Get the information from the sql
		String sql = "SELECT UserName, Password , TimeCreated, Number, PhoneNumber FROM userInformation;";
		PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = ps.executeQuery();
		boolean isFound = false;
		while (rs.next()) {
			if (type.equals("logIn")) {

				// Check the user name and password when you log in
				if (userName.equals(rs.getString(1)) && st.equals(rs.getString(2))) {
					isFound = true;

					// The number of game will add
					String sql1 = "Update userInformation set Number=? where UserName=?";
					PreparedStatement presta = conn.prepareStatement(sql1);

					presta.setString(2, rs.getString(1));
					presta.setInt(1, rs.getInt(4) + 1);
					presta.execute();

					break;
				}
			} else if (type.equals("forget")) {

				// Judge whether the user name and phone number are valid
				if (userName.equals(rs.getString(1)) && st.equals(rs.getString(5))) {
					isFound = true;
					break;
				}
			}
		}

		if (isFound) {

			// If the message is valid, return true
			player1UserName = playerOne.getName();
			return true;
		} else {

			// If the message is invalid, return false
			return false;
		}
	}

	/**
	 * Record the game information when the game is over
	 * 
	 * @param order
	 * @throws SQLException
	 */
	public static void record(String order) throws SQLException {

		// Add the game information
		String sql = "INSERT INTO record(UserName,Competitor,YourScore,CompetitorScore,GameTime) VALUES (?,?,?,?,?)";

		long l = System.currentTimeMillis();
		Date time = new Date(l);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		PreparedStatement presta = conn.prepareStatement(sql);

		presta.setString(1, order.split(",")[1]);
		presta.setString(2, order.split(",")[2]);
		presta.setString(3, order.split(",")[3]);
		presta.setString(4, order.split(",")[4]);
		presta.setString(5, sdf.format(time));

		presta.execute();

		// Reset the rank if it is needed
		String sqlJudge = "SELECT UserName, Score FROM rankinformation;";
		PreparedStatement ps = conn.prepareStatement(sqlJudge, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			if (order.split(",")[1].equals(rs.getString(1))) {
				if (Integer.parseInt(rs.getString(2)) < Integer.parseInt(order.split(",")[3])) {

					// If the new score is higher, replace it
					String sql1 = "Update rankinformation set Score=? where UserName =?";
					PreparedStatement presta1 = conn.prepareStatement(sql1);
					presta1.setString(2, order.split(",")[1]);
					presta1.setInt(1, Integer.parseInt(order.split(",")[3]));
					presta1.execute();

					// Change the date
					sql1 = "Update rankinformation set Time=? where UserName =?";
					presta1 = conn.prepareStatement(sql1);
					presta1.setString(2, order.split(",")[1]);
					presta1.setString(1, sdf.format(time));
					presta1.execute();

					// Reset the rank order by the score in inverted order
					String sqlStr = "SELECT * FROM rankinformation ORDER BY Score desc;";
					ResultSet rs1 = stat.executeQuery(sqlStr);
					int max = 1;
					while (rs1.next()) {

						// Update the rank information
						String update = "Update rankinformation set MAX = ? where UserName = ?";
						PreparedStatement presta11 = conn.prepareStatement(update);

						presta11.setString(2, rs1.getString(1));
						presta11.setInt(1, max);
						max++;

						presta11.execute();
					}
				}

				break;
			}
		}

	}

	/** Override the run method */
	public void run() {
		try {
			if (index == 1) {
				
				// Set the player 1
				player1 = player;
				bufPlayer1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
				printPlayer1 = new PrintStream(player1.getOutputStream());
				playerOne = new Player(bufPlayer1, printPlayer1, 1);
				new Thread(playerOne).start();
				
				System.out.println("Server: Player1 is online.");
			} else {
				
				// Set the player 2
				player2 = player;
				bufPlayer2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
				printPlayer2 = new PrintStream(player2.getOutputStream());
				playerTwo = new Player(bufPlayer2, printPlayer2, 2);
				
				System.out.println("Server: Player2 is online.");

				// Set the each other's stream
				playerTwo.setStream(bufPlayer1, printPlayer1);
				playerOne.setStream(bufPlayer2, printPlayer2);

				new Thread(playerTwo).start();
				
				index = 0;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		try {
			
			// Set the server socket
			server = new ServerSocket(15319);
			System.out.println("Server: Wainting connection~~~");

			while (true) {
				TankGameServer gameServer = TankGameServer.getInstance();
				
				// Connect with the player
				player = server.accept();
				Thread thread = new Thread(gameServer);
				thread.start();
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
