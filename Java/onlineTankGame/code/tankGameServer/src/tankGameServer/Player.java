/*
 * Name : Player.java
 *
 * Function : To define the player class
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package tankGameServer;

import java.io.*;

/**
 * This class is used to define a player and get the order
 * 
 * @author Zevin
 *
 */
public class Player implements Runnable {

	/** Set the input and output stream */
	private final BufferedReader bufPlayer;
	private final PrintStream printPlayer;

	/** Used to send message to other player */
	private BufferedReader bufPlayerOther;
	private PrintStream printPlayerOther;

	/** Set the player name */
	private String playerName = "";
	private int num;

	/** Set the score */
	private int myScore = 0, otherScore = 0;

	/**
	 * Set the input and output stream
	 * 
	 * @param bufPlayer
	 * @param printPlayer
	 * @param num
	 */
	public Player(BufferedReader bufPlayer, PrintStream printPlayer, int num) {
		this.bufPlayer = bufPlayer;
		this.printPlayer = printPlayer;
		this.num = num;
	}

	/**
	 * Set the other player's input and output stream
	 * 
	 * @param bufPlayer
	 * @param printPlayer
	 */
	public void setStream(BufferedReader bufPlayer, PrintStream printPlayer) {
		this.bufPlayerOther = bufPlayer;
		this.printPlayerOther = printPlayer;
	}

	@Override
	public void run() {
		String order;
		while (true) {
			try {

				// Get the order from the player and process it
				order = bufPlayer.readLine();

				if (order.startsWith("singUp")) {

					System.out.println("This is order from " + playerName + " : Ask to sing up");

					// Print the result to the player
					printPlayer.println(TankGameServer.singUp(order));
				} else if (order.startsWith("logIn")) {

					// Log in
					System.out.println("This is order from " + playerName + " : Ask to log in");
					playerName = order.split(",")[1];
					boolean isTrue = TankGameServer.judgePassword(order.split(",")[1], order.split(",")[2], "logIn");
					printPlayer.println(num + "," + order.split(",")[1] + "," + isTrue);

					// Judge whether it is successful
					if (isTrue) {
						System.out.println("Server: log in successfully");
						printPlayer.println(TankGameServer.map);
						if (num == 1) {

							System.out.println("Server: " + playerName + " please wait a moment.");
							printPlayer.println("othersWaiting");
						} else if (num == 2) {

							System.out.println("Server: Ready");
							printPlayerOther.println("competitorOK");
							printPlayer.println("competitorOK");

							Thread.sleep(4000);
							System.out.println("Server: Begin the game");
							printPlayerOther.println("Begin");
							printPlayer.println("Begin");

						}
					}

				} else if (order.startsWith("otherPlayerName")) {

					// Get other player's name
					System.out.println("This is order from " + playerName + " : Ask to get other player's name ");
					printPlayerOther.println("otherPlayerName," + playerName + ",end123");
				} else if (order.startsWith("forget")) {

					// Reset the password
					System.out.println("This is order from " + playerName + " : Ask to reset the password");
					String pNum = order.split(",")[2];
					boolean isTrue = TankGameServer.judgePassword(order.split(",")[1], order.split(",")[2], "forget");
					printPlayer.println(isTrue);

					// Judge whether it is successful
					if (isTrue) {

						System.out.println("Server: Reset the password successfully");
						order = bufPlayer.readLine();
						printPlayer.println(TankGameServer.forget(order, pNum));
					}
				} else if (order.startsWith("sendMail")) {

					// Send the mail
					System.out.println("This is order from " + playerName + " : Ask to send the mail");
					printPlayer.println("waiting");

					System.out.println("Server: Please waiting");
					
					SendMail send = new SendMail();
					String tem;
					String s = "";
					while (!(tem = bufPlayer.readLine()).equals("end123")) {
						s += tem + "\n";
					}
					send.sendEmail(s, "User");

					System.out.println("Server: Sending successfully");
					printPlayer.println("sendMail,true");
				} else if (order.startsWith("userInformation")) {

					// Get the user information
					System.out.println("This is order from " + playerName + " : Ask to get user information");
					printPlayer.println("userInformation," + TankGameServer.userMessage(playerName));
				} else if (order.startsWith("gameInformation")) {

					// Get the game information
					System.out.println("This is order from " + playerName + " : Ask to get game information");
					printPlayer.println("gameInformation," + TankGameServer.gameMessage("gameInformation", playerName));
				} else if (order.startsWith("highestScore")) {

					// Get the player's highest score
					System.out.println("This is order from " + playerName + " : Ask to get the player's highest score");
					printPlayer.println("highestScore," + TankGameServer.gameMessage("highestScore", playerName));
				} else if (order.startsWith("most")) {

					// Get the highest score from the sql
					System.out.println(
							"This is order from " + playerName + " : Ask to get the highest score from the sql");
					printPlayer.println("most," + TankGameServer.gameMessage("most", playerName));
				} else if (order.startsWith("getCertificate")) {

					// Get the Certificate
					System.out.println("This is order from " + playerName + " : Ask to get the cetificate");
					printPlayer.println("waiting");

					System.out.println("Server: Please waiting a moment");
					printPlayer.println("getCertificate," + TankGameServer.getPDF(order, playerName));
				} else if (order.startsWith("chat")) {

					// To chat with others
					System.out.println("This is order from " + playerName + " : Ask to chat with others");
					printPlayer.println("chat," + playerName + ": " + order.split(",")[1]);
					printPlayerOther.println("chat," + playerName + ": " + order.split(",")[1]);
				} else if (order.startsWith("Move")) {

					// Send a bullet
					if (order.split(",")[1].equals("J")) {

						System.out.println("This is order from " + playerName + " : Ask to send a bullet");
						printPlayerOther.println("Move,J");

					} else {

						// Move the tank
						System.out.println("This is order from " + playerName + " : Ask to movie");
						printPlayerOther.println("Move," + order.split(",")[1] + "," + order.split(",")[2] + ","
								+ order.split(",")[3] + "," + order.split(",")[4] + "," + order.split(",")[5]);
					}
				} else if (order.equals("subHpOther")) {

					// Subtract the other's hp
					System.out.println("This is order from " + playerName + " : Ask to subtract other's hp ");
					printPlayerOther.println("subHp");
				} else if (order.equals("subHp")) {

					// Subtract the player's hp
					System.out.println("This is order from " + playerName + " : Ask to subtract hp ");
					printPlayer.println("subHp");
				} else if (order.startsWith("addScore")) {

					// Add the score
					if (order.split(",")[2].equals("me")) {

						System.out.println("This is order from " + playerName + " : Ask to add his score");
						myScore = Integer.valueOf(order.split(",")[1]);
					} else {

						System.out.println("This is order from " + playerName + " : Ask to add others' score");
						otherScore = Integer.valueOf(order.split(",")[1]);
					}

					printPlayer.println("myScore," + myScore);
					printPlayer.println("otherScore," + otherScore);

					printPlayerOther.println("otherScore," + myScore);
					printPlayerOther.println("myScore," + otherScore);
				} else if (order.startsWith("Surrender")) {

					// The player ask to surrender
					System.out.println("This is order from " + playerName + " : Ask to surrender");
					printPlayerOther.println("competitorSurrenders");
				} else if (order.startsWith("Over")) {

					// The game is over
					System.out.println("Server: The game is over");
					TankGameServer.record(order);
				} else if (order.startsWith("youLose")) {

					// The player is lose
					System.out.println("Server: " + playerName + " is lose");
					printPlayerOther.println("youWin");
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Get the player name
	 * 
	 * @return String
	 */
	public String getName() {
		return playerName;
	}
}
