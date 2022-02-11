/*
 * Name : DrawMap.java
 *
 * Function : To draw the game map
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package getElements;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import actions.*;

/**
 * This class is used to draw the game map
 * 
 * @author Zevin
 *
 */
public class DrawMap extends JPanel {

	/** Set the different elements image */
	private Image wallImage = new ImageIcon("img\\walls.gif").getImage();
	private Image steelImage = new ImageIcon("img\\steels.gif").getImage();
	private Image waterImage = new ImageIcon("img\\water.gif").getImage();
	private Image homeImage = new ImageIcon("img\\home.jpg").getImage();

	/** Get two tanks */
	private TankMove p1 = new TankMove("1", "D");
	private TankMove p2 = new TankMove("2", "U");

	/** Store the message whether the game is over */
	private static boolean isOver = false;

	/** Used to send bullets */
	private SendBullet bu;
	private Graphics g;
	
	/** Start the Repaint class */
	private static Repaint re;
	private static Thread reThread;
	
	/** Store the map information */
	private static String[][] maps = new String[20][25];
	
	/** Set the tank direction */
	private String[] headDir = new String[] { "U", "R", "D", "L" };

	/** Set the input and output stream */
	private static BufferedReader serverIn;
	private static PrintStream serverOut;
	
	/** Set the focus boject */
	private Focus f = Focus.getInstance();

	private String player = "";
	private static DrawMap allMap;

	/** Set the winner */
	private static int winner;

	/** Get a singletion */
	private DrawMap() {

		try {
			re = new Repaint();
			this.setBounds(0, 0, 750, 600);
			addListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized DrawMap getInstance() {
		if (allMap == null) {
			allMap = new DrawMap();
		}
		return allMap;
	}

	/** Get the map information from the server */
	public void getMap() {
		String s;
		int index = 0;

		try {
			
			// The map will end with end123
			while ((s = serverIn.readLine()).equals("end123") == false) {

				// Store the map message
				for (int i = 0; i < s.length(); i++) {
					maps[index][i] = String.valueOf(s.charAt(i));
					
					// Store the original x and y position of the tank
					if (String.valueOf(s.charAt(i)).equals("5")) {
						p1.setOriginalXY(i, index);
					} else if (String.valueOf(s.charAt(i)).equals("6")) {
						p2.setOriginalXY(i, index);
					}
				}
				index++;
			}
			
			// Begin repaint the map
			startRepaint();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** Add the key listener */
	public void addListener() {

		// Add mouse click listener
		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				f.setFocus(allMap);
			}

		});

		// Add key listener
		this.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (KeyEvent.VK_W == e.getKeyCode()) {
					
					// Set the direction of the tank
					p2.setImage(headDir[0]);

					// Judge whether the tank can move successfully
					if ((p2.getY() / 30 - 1) > 0 && maps[p2.getY() / 30 - 1][p2.getX() / 30].equals("0")) {

						maps[p2.getY() / 30][p2.getX() / 30] = "0";
						maps[p2.getY() / 30 - 1][p2.getX() / 30] = "6";

						// If the tank is allowed to move, send the message to the server
						serverOut.println("Move" + player + "," + (19 - p2.getY() / 30) + "," + (24 - p2.getX() / 30)
								+ "," + (19 - p2.getY() / 30 + 1) + "," + (24 - p2.getX() / 30) + "," + headDir[2]);
					} else {
						
						// If not, the tank can't move
						serverOut.println("Move" + player + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + headDir[2]);
					}

				} else if (KeyEvent.VK_A == e.getKeyCode()) {

					// Set the direction of the tank
					p2.setImage(headDir[3]);
					
					// Judge whether the tank can move successfully
					if ((p2.getX() / 30 - 1) > 0 && maps[p2.getY() / 30][p2.getX() / 30 - 1].equals("0")) {

						maps[p2.getY() / 30][p2.getX() / 30] = "0";
						maps[p2.getY() / 30][p2.getX() / 30 - 1] = "6";

						// If the tank is allowed to move, send the message to the server
						serverOut.println("Move" + player + "," + (19 - p2.getY() / 30) + "," + (24 - p2.getX() / 30)
								+ "," + (19 - p2.getY() / 30) + "," + (24 - p2.getX() / 30 + 1) + "," + headDir[1]);
					} else {
						
						// If not, the tank can't move
						serverOut.println("Move" + player + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + headDir[1]);
					}

				} else if (KeyEvent.VK_S == e.getKeyCode()) {

					// Set the direction of the tank
					p2.setImage(headDir[2]);
					
					// Judge whether the tank can move successfully
					if ((p2.getY() / 30 + 1) > 0 && maps[p2.getY() / 30 + 1][p2.getX() / 30].equals("0")) {
						maps[p2.getY() / 30][p2.getX() / 30] = "0";
						maps[p2.getY() / 30 + 1][p2.getX() / 30] = "6";

						// If the tank is allowed to move, send the message to the server
						serverOut.println("Move" + player + "," + (19 - p2.getY() / 30) + "," + (24 - p2.getX() / 30)
								+ "," + (19 - p2.getY() / 30 - 1) + "," + (24 - p2.getX() / 30) + "," + headDir[0]);
					} else {
						
						// If not, the tank can't move
						serverOut.println("Move" + player + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + headDir[0]);
					}

				} else if (KeyEvent.VK_D == e.getKeyCode()) {

					// Set the direction of the tank
					p2.setImage(headDir[1]);

					// Judge whether the tank can move successfully
					if ((p2.getX() / 30 + 1) > 0 && maps[p2.getY() / 30][p2.getX() / 30 + 1].equals("0")) {
						maps[p2.getY() / 30][p2.getX() / 30] = "0";
						maps[p2.getY() / 30][p2.getX() / 30 + 1] = "6";

						// If the tank is allowed to move, send the message to the server
						serverOut.println("Move" + player + "," + (19 - p2.getY() / 30) + "," + (24 - p2.getX() / 30)
								+ "," + (19 - p2.getY() / 30) + "," + (24 - p2.getX() / 30 - 1) + "," + headDir[3]);
					} else {
						
						// If not, the tank can't move
						serverOut.println("Move" + player + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + headDir[3]);
					}

				} else if (KeyEvent.VK_J == e.getKeyCode()) {
					
					// Sent the send bullet order to the server
					serverOut.println("Move" + player + ",J");

					// Send the bullet
					SendBullet bu = new SendBullet(p2.getX(), p2.getY(), p2.getDir(), serverIn, serverOut, "score");
					Thread buThread = new Thread(bu);
					buThread.start();
				}

			}
		});
	}

	/** Set the input stream */
	public void setBuf(BufferedReader buf) {
		serverIn = buf;
	}

	/** Set the output stream */
	public void setPrint(PrintStream p) {
		serverOut = p;
	}

	/** Set the player */
	public void setPlayer(String s) {
		player = s;
	}

	/** Set the game is over */
	public static void setIsOver() {
		if (isOver == false) {
			isOver = true;
		}
	}

	/** Return the game information */
	public static boolean getIsOver() {
		return isOver;
	}

	/** Set the winner */
	public static void setWinner(int winner) {
		DrawMap.winner = winner;
	}

	/** Send the message */
	public static void sendMessage(String s) {
		serverOut.println(s);
	}

	/** Get the winner */
	public int whoWins() {
		return winner;
	}

	/** Begin to repaint the map */
	public static void startRepaint() {
		re.getMap();
		reThread = new Thread(re);
		reThread.start();
	}

	/** Set the map information */
	public void setMap(int x, int y, String num) {
		maps[x][y] = num;
	}

	/** Get the Graphics */
	public Graphics getGra() {
		return g;
	}

	/** Get the block information */
	public String getBlock(int i, int j) {
		return maps[i][j];
	}

	/** Get the p1 tank */
	public TankMove getTank() {
		return p1;
	}

	/** Get the p2 tank */
	public TankMove getTank2() {
		return p2;
	}

	/** Draw the map out */
	public void paint(Graphics g) {

		super.paint(g);
		this.g = g;

		try {
			
			// Read the map information and draw the map
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 25; j++) {
					// 1 walls, 2 steels, 3 river, 4 home, 5 P1, 6 P2, 7 bullet
					if (maps[i][j].charAt(0) == '1') {
						g.drawImage(wallImage, 30 * j, 30 * i, 30, 30, null);
					} else if (maps[i][j].charAt(0) == '2') {
						g.drawImage(steelImage, 30 * j, 30 * i, 30, 30, null);
					} else if (maps[i][j].charAt(0) == '3') {
						g.drawImage(waterImage, 30 * j, 30 * i, 30, 30, null);
					} else if (maps[i][j].charAt(0) == '4') {
						g.drawImage(homeImage, 30 * j, 30 * i, 30, 30, null);
					} else if (maps[i][j].charAt(0) == '5') {
						p1.setX(30 * j);
						p1.setY(30 * i);
						g.drawImage(p1.getImage(), p1.getX(), p1.getY(), 30, 30, null);
					} else if (maps[i][j].charAt(0) == '6') {
						p2.setX(30 * j);
						p2.setY(30 * i);
						g.drawImage(p2.getImage(), p2.getX(), p2.getY(), 30, 30, null);

					} else if (maps[i][j].charAt(0) == '7') {
						g.drawImage(homeImage, 30 * j, 30 * i, 30, 30, null);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
