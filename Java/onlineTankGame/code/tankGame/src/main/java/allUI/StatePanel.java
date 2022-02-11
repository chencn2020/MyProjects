/*
 * Name : StatePanel.java
 *
 * Function : To show the score and left time
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package allUI;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import getElements.DrawMap;

/**
 * This class is used to show the score and left time
 * @author Zevin
 *
 */
public class StatePanel implements Runnable {

	private JPanel scorePanel;
	private static JTextField playerOneScoreField;
	private static JTextField playerTwoScoreField;
	private static JTextField timeField;
	private static int hp = 3;
	private int gameTime = 180; // 3min
	private int min = 0, sec = 0;
	private static boolean gameState = true;
	private static JLabel HPLabel1;
	private static JLabel HPLabel2;
	private static JLabel HPLabel3;
	private  BufferedReader serverIn;
	private PrintStream serverOut;

	private String hpImag = "img\\hp.png";
	
	/**
	 * Set the state panel to show the score and left time
	 * @param serverIn
	 * @param serverOut
	 */
	public StatePanel(BufferedReader serverIn, PrintStream serverOut) {
		this.serverIn = serverIn;
		this.serverOut = serverOut;

		// Set the panel
		scorePanel = new JPanel();
		scorePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		scorePanel.setBounds(10, 10, 230, 280);
		scorePanel.setLayout(null);

		// Set the competitor's score
		JLabel playerOneScore = new JLabel("\u5bf9\u624b");
		playerOneScore.setFont(new Font("宋体", Font.BOLD, 20));
		playerOneScore.setBounds(10, 50, 65, 30);
		scorePanel.add(playerOneScore);

		// set the score
		JLabel scoreLabel = new JLabel("\u5F97\u5206");
		scoreLabel.setFont(new Font("宋体", Font.BOLD, 20));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setBounds(10, 10, 210, 29);
		scorePanel.add(scoreLabel);

		// Set the competitor's score field
		playerOneScoreField = new JTextField("0");
		playerOneScoreField.setFont(new Font("Arial", Font.BOLD, 15));
		playerOneScoreField.setEditable(false);
		playerOneScoreField.setBounds(116, 51, 100, 30);
		scorePanel.add(playerOneScoreField);
		playerOneScoreField.setColumns(10);

		// Set your score label
		JLabel label = new JLabel("\u4f60");
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 111, 65, 30);
		scorePanel.add(label);

		// Set your score field
		playerTwoScoreField = new JTextField("0");
		playerTwoScoreField.setFont(new Font("Arial", Font.BOLD, 15));
		playerTwoScoreField.setEditable(false);
		playerTwoScoreField.setBounds(116, 112, 100, 30);
		scorePanel.add(playerTwoScoreField);
		playerTwoScoreField.setColumns(10);

		// Set your left life label
		JLabel lifeLabel = new JLabel("\u5269\u4F59\u8840\u91CF");
		lifeLabel.setIcon(new ImageIcon("img\\life.png"));
		lifeLabel.setFont(new Font("宋体", Font.BOLD, 20));
		lifeLabel.setBounds(10, 166, 112, 24);
		scorePanel.add(lifeLabel);

		// Set three lives
		HPLabel1 = new JLabel("");
		HPLabel1.setIcon(new ImageIcon(hpImag));
		HPLabel1.setBounds(132, 166, 25, 22);
		scorePanel.add(HPLabel1);
		
		HPLabel2 = new JLabel("");
		HPLabel2.setIcon(new ImageIcon(hpImag));
		HPLabel2.setBounds(162, 166, 25, 22);
		scorePanel.add(HPLabel2);
		
		 HPLabel3 = new JLabel("");
		HPLabel3.setIcon(new ImageIcon(hpImag));
		HPLabel3.setBounds(192, 166, 25, 22);
		scorePanel.add(HPLabel3);

		// Set the left time label
		JLabel timeLabel = new JLabel("-----\u5269\u4F59\u65F6\u95F4-----");
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setFont(new Font("宋体", Font.BOLD, 20));
		timeLabel.setBounds(10, 200, 210, 24);
		scorePanel.add(timeLabel);

		// Set the left time field
		timeField = new JTextField();
		timeField.setHorizontalAlignment(SwingConstants.CENTER);
		timeField.setFont(new Font("宋体", Font.BOLD, 25));
		timeField.setEditable(false);
		timeField.setBounds(63, 234, 100, 40);
		scorePanel.add(timeField);
		timeField.setColumns(10);
	}

	/**
	 * Get the scorePanel
	 * @return JPanel
	 */
	public JPanel getPanel() {
		return scorePanel;
	}
	
	/** Subtract the hp */
	public static void subHP() {
		hp--;
		if(hp <= 0) {
			gameState = false;
			DrawMap.setIsOver();
			DrawMap.setWinner(1);
			hp = 0;
		}
		setHP();
	}
	
	/** Set the hp image visible */
	private static void setHP() {
		if(hp == 0) {
			HPLabel1.setVisible(false);
			HPLabel2.setVisible(false);
			HPLabel3.setVisible(false);
		}else if(hp == 1){
			HPLabel1.setVisible(true);
			HPLabel2.setVisible(false);
			HPLabel3.setVisible(false);
		}else if(hp == 2){
			HPLabel1.setVisible(true);
			HPLabel2.setVisible(true);
			HPLabel3.setVisible(false);
		}else if(hp == 3){
			HPLabel1.setVisible(true);
			HPLabel2.setVisible(true);
			HPLabel3.setVisible(true);
		}
	}

	/**
	 * Add the player's score
	 * @param s
	 */
	public static void addMyScore(String s) {

		int score = Integer.parseInt(playerTwoScoreField.getText());
		score += Integer.parseInt(s);
		playerTwoScoreField.setText(String.valueOf(score));
	}
	
	/**
	 * Add the competitor's score
	 * @param s
	 */
	public static void addOtherScore(String s) {

		int score = Integer.parseInt(playerOneScoreField.getText());
		score += Integer.parseInt(s);
		playerOneScoreField.setText(String.valueOf(score));

	}
	
	/** Get the score */
	public static String getScore() {
		return playerTwoScoreField.getText() + "," + playerOneScoreField.getText();
	}

	/** Set the time and subtract the time per second */
	public void run() {
		
		// Set the minute and second
		min = gameTime / 60;
		sec = gameTime % 60;
		
		String time;
		if (sec < 10) {
			time = min + "m0" + sec + "s";
		} else {
			time = min + "m" + sec + "s";
		}

		timeField.setText(time);
		while (true) {
			try {
				Thread.sleep(1000);
				
				// Get whether the game is over
				if(DrawMap.getIsOver()) {
					break;
				}
				
				// Time will subtract one second per second
				gameTime -= 1;
				min = gameTime / 60;
				sec = gameTime % 60;
				
				if (min == 0) {
					if (sec < 10) {
						time = "0" + sec + "s";
					} else {
						time = sec + "s";
					}
				} else {
					if (sec < 10) {
						time = min + "m0" + sec + "s";
					} else {
						time = min + "m" + sec + "s";
					}				
				}
				timeField.setText(time);
				
				// If the time is over and the game is not over
				if (gameTime == 0) {
					if(DrawMap.getIsOver() == false) {
						
						// Set the game is over
						DrawMap.setIsOver();
						
						// Judge who is the winner
						if(playerOneScoreField.getText().compareTo(playerTwoScoreField.getText()) > 0) {
							DrawMap.setWinner(1);
						} else {
							DrawMap.setWinner(2);
						}
					}
					
					// The word will be red when the time is over
					timeField.setForeground(Color.RED);
					timeField.setText("\u65F6\u95F4\u5230");
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			
		}

	}
}
