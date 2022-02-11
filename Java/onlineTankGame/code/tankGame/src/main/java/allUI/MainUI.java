/*
 * Name : MainUI.java
 *
 * Function : To ask the player to log in the game
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package allUI;

import javax.swing.*;
import actions.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import getElements.DrawMap;
import music.PlayMusic;
import javax.swing.border.EmptyBorder;

/**
 * This class is used to ask the player to log in the game
 * 
 * @author Zevin
 *
 */
public class MainUI extends JFrame implements Runnable {

	private JPanel contentPane;
	private static StatePanel panel;
	private ChatPanel chatPanel;
	private DrawMap drawMap;
	private JLabel gameOverLabel, defeatLabel, victoryLabel;
	private String playerNum;
	private String playerName;
	private BufferedReader serverIn;
	private PrintStream serverOut;

	/**
	 * Create the frame.
	 * 
	 * @param playerNum
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public MainUI(final BufferedReader serverIn, final PrintStream serverOut, Socket server, String playerNum,
			String playerName) throws Exception {

		// Set the input and output stream
		this.serverIn = serverIn;
		this.serverOut = serverOut;
		this.playerName = playerName;
		this.playerNum = playerNum;

		BufferedReader chatServerIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
		PrintStream chatServerOut = new PrintStream(server.getOutputStream());

		// Play the music
		new PlayMusic().start();

		// Set the frame
		setResizable(false);
		setAlwaysOnTop(true);

		setTitle("\u5766\u514B\u5927\u6218");
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\LoginTank.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 20, 1020, 667);
		setResizable(false);

		// Set menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		menuBar.setFont(new Font("Arial", Font.BOLD, 15));
		setJMenuBar(menuBar);

		// Set the elements in the menu bar
		JMenu userMessageMenu = new JMenu("\u7528\u6237");
		menuBar.add(userMessageMenu);

		// Get the user information
		JMenuItem fundationMenuItem = new JMenuItem("\u57FA\u672C\u4FE1\u606F");
		fundationMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverOut.println("userInformation");
			}
		});
		userMessageMenu.add(fundationMenuItem);

		// Get the game information
		JMenuItem gameRecordMenuItem = new JMenuItem("\u6E38\u620F\u8BB0\u5F55");
		gameRecordMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverOut.println("gameInformation");
			}
		});
		userMessageMenu.add(gameRecordMenuItem);

		// Get the player's highest score
		JMenuItem highestScoreMenuItem = new JMenuItem("\u6700\u9AD8\u5206");
		userMessageMenu.add(highestScoreMenuItem);
		highestScoreMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverOut.println("highestScore");
			}
		});

		JMenu rankMessageMenu = new JMenu("\u6392\u540D");
		menuBar.add(rankMessageMenu);

		// Get the highest score in the sql
		JMenuItem superiorRankMenuItem = new JMenuItem("\u533A\u7EA7\u5927\u4F6C");
		superiorRankMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverOut.println("most");
			}
		});
		rankMessageMenu.add(superiorRankMenuItem);

		JMenu helpMessageMenu = new JMenu("\u5E2E\u52A9");
		menuBar.add(helpMessageMenu);

		// Get the game rule
		JMenuItem gameRuleMenuItem = new JMenuItem("\u6E38\u620F\u89C4\u5219");
		gameRuleMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NoticeUI("Game Rule", "RULES", "YES");
			}
		});
		helpMessageMenu.add(gameRuleMenuItem);

		// Set the contract ui
		JMenuItem contractUsMenuItem = new JMenuItem("\u8054\u7CFB\u6211\u4EEC");
		contractUsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendMailUI send = new SendMailUI(serverIn, serverOut);
				send.setVisible(true);
			}

		});
		helpMessageMenu.add(contractUsMenuItem);

		// Set the tool bar
		JToolBar toolBar = new JToolBar();
		menuBar.add(toolBar);

		// Set the surrender button
		JButton surrenderButton = new JButton("\u6295\u964D");
		surrenderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConfirmUI("Surrender", "你确定要投降吗？", drawMap);
			}
		});
		toolBar.add(surrenderButton);

		// Set the get certificate button
		JButton certificateButton = new JButton("\u83B7\u53D6\u8BC1\u4E66");
		certificateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GetCertificateUI get = new GetCertificateUI(serverIn, serverOut);
			}
		});
		toolBar.add(certificateButton);

		// This panel is used to add the map
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Get the map
		drawMap = DrawMap.getInstance();
		drawMap.setBuf(serverIn);
		drawMap.setPrint(serverOut);
		drawMap.setPlayer(playerNum);
		drawMap.getMap();

		// Ask the key listener to focus on the panel
		Focus f = Focus.getInstance();
		f.setFocus(drawMap);
		new Thread(f).start();

		drawMap.setBounds(0, 0, 750, 600);
		getContentPane().add(drawMap);

		// Set the sate panel to show the score and left time
		JPanel statePanel = new JPanel();
		statePanel.setBounds(755, 0, 250, 600);
		statePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		contentPane.add(statePanel);
		statePanel.setLayout(null);

		panel = new StatePanel(serverIn, serverOut);
		chatPanel = new ChatPanel(chatServerIn, chatServerOut);
		statePanel.add(panel.getPanel());
		statePanel.add(chatPanel.getPanel());

		// Set the game over label
		gameOverLabel = new JLabel();
		gameOverLabel.setIcon(new ImageIcon("img\\game_over.png"));
		gameOverLabel.setBounds(201, 0, 321, 167);
		gameOverLabel.setVisible(false);
		contentPane.add(gameOverLabel);

		// Set the defeat label
		defeatLabel = new JLabel("");
		defeatLabel.setIcon(new ImageIcon("img\\defeat.jpg"));
		defeatLabel.setBounds(201, 172, 321, 213);
		defeatLabel.setVisible(false);
		contentPane.add(defeatLabel);

		// Set the voctory label
		victoryLabel = new JLabel("");
		victoryLabel.setIcon(new ImageIcon("img\\victory.jpg"));
		victoryLabel.setBounds(201, 172, 382, 354);
		victoryLabel.setVisible(false);
		contentPane.add(victoryLabel);

		// Start the GetResult thread
		GetResult get = new GetResult(serverIn, serverOut, chatPanel);
		Thread getThread = new Thread(get);
		getThread.start();

	}

	/** 
	 * Get the state panel
	 * @return StatePanel
	 */
	public static StatePanel getStatePanel() {
		return panel;
	}

	public void run() {
		try {
			while (true) {
				System.out.print("");
				
				// Judge whether the game is over
				if (drawMap.getIsOver()) {
					
					// If the game is over, make the map invisible and make the game over label visible
					drawMap.setVisible(false);
					gameOverLabel.setVisible(true);

					// Judge who wins
					if (drawMap.whoWins() == 2) {
						victoryLabel.setVisible(true);
					} else {
						defeatLabel.setVisible(true);
						serverOut.println("youLose");
					}
					
					// Play the game over music
					new PlayMusic().gameOver();
					serverOut.println("otherPlayerName");
					Thread.sleep(100);

					// Send the game message to the server and store it to the sql
					serverOut.println("Over," + playerName + "," + GetResult.getOtherName() + ","
							+ StatePanel.getScore().split(",")[0] + "," + StatePanel.getScore().split(",")[1]
							+ ",end123");
					break;
				}

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
