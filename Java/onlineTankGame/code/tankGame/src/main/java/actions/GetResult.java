/*
 * Name : GetResult.java
 *
 * Function : To get the result form the server
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/22
 */

package actions;

import java.io.*;
import allUI.*;
import getElements.*;

/**
 * This class is used to get the result form the server
 * 
 * @author Zevin
 *
 */
public class GetResult implements Runnable {

	/** Set some values */
	private BufferedReader serverIn;
	private PrintStream serverOut;
	private ChatPanel chatPanel;
	private DrawMap drawMap;

	/** Set the tank directions */
	private String[] headDir = new String[] { "U", "R", "D", "L" };
	private NoticeUI waitOther = null;
	private boolean allowMove = false;
	private static String otherPlayerName;

	/** Set the input and output stream */
	public GetResult(BufferedReader serverIn, PrintStream serverOut, ChatPanel chatPanel) {
		this.serverIn = serverIn;
		this.serverOut = serverOut;
		this.chatPanel = chatPanel;
		drawMap = DrawMap.getInstance();
	}

	/** Get the result from the server */
	public void run() {

		String result;
		NoticeUI wait = null;
		while (true) {
			try {

				// Get the information
				result = serverIn.readLine();
				if (result.startsWith("userInformation")) {

					// Get the user information
					String[] s = result.split(",");
					String tem = "用户名: " + s[1] + "\n注册时间: " + s[2] + "\n游戏次数: " + s[3] + "\n注册手机号: " + s[4];
					new MessageShowUI("玩家信息", tem);

				} else if (result.startsWith("sendMail")) {

					// Output the result of sending mails
					wait.dispose();
					if (result.split(",")[1].equals("true")) {
						new NoticeUI("Thanks", "感谢你的建议", "YES");
					} else {
						new NoticeUI("Busy", "服务器繁忙，请稍后再试", "YES");
					}

				} else if (result.startsWith("gameInformation")) {

					// Get all game information about this player
					new GameInformationUI(result.substring("gameInformation,".length(), result.length()));

				} else if (result.startsWith("highestScore")) {

					// Get the highest score of this player
					new NoticeUI("Highest Score", "你的最高分: " + result.split(",")[1], "YES");

				} else if (result.startsWith("most")) {

					// Get the highest score from the sql
					new NoticeUI("Highest Score", "全服最高分： " + result.split(",")[2] + "\n" + "大佬用户名： "
							+ result.split(",")[1] + "\n" + "记录创建时间： " + result.split(",")[3] + "\n", "YES");

				} else if (result.startsWith("getCertificate")) {

					// Get the result of getting certificate
					wait.dispose();
					if (result.split(",")[1].equals("true")) {
						new NoticeUI("Success", "证书已发送，请注意查收", "YES");
					} else {
						new NoticeUI("Busy", "服务器繁忙，请稍后再试", "YES");
					}

				} else if (result.startsWith("chat")) {

					// Get the chat information and set the information to the chat panel
					chatPanel.setChatArea(result.substring("chat,".length(), result.length()));

				} else if (result.startsWith("waiting")) {

					// Tell the player please waiting for a moment
					wait = new NoticeUI("Waiting", "正在发送中，请稍后", "NO");

				} else if (result.startsWith("Move") && allowMove) {

					// Get the competitor's moving information
					OtherMove(result);

				} else if (result.startsWith("MeMove") && allowMove) {

					// Get the player's moving information
					MeMove(result);

				} else if (result.equals("subHp")) {

					// Substance the hp
					StatePanel.subHP();

				} else if (result.startsWith("myScore") || result.startsWith("otherScore")) {

					// Add the score
					if (result.startsWith("myScore")) {
						StatePanel.addMyScore(result.split(",")[1]);
					} else {
						StatePanel.addOtherScore(result.split(",")[1]);
					}

				} else if (result.startsWith("competitorSurrenders")) {

					// Set the winner if your competitor surrenders
					drawMap.setIsOver();
					drawMap.setWinner(2);
					new NoticeUI("Congratulations", "对方投降了，你赢了！", "YES");

				} else if (result.startsWith("othersWaiting")) {

					// The game will not begin until other player is connected
					waitOther = new NoticeUI("Waiting", "等待其他玩家连接", "NO");

				} else if (result.startsWith("competitorOK")) {

					// If the other player is connected, the game will start in 3 seconds
					if (waitOther != null) {
						waitOther.dispose();
					}

					waitOther = new NoticeUI("Ready", "准备 3", "NO");
					Thread.sleep(1000);
					waitOther.dispose();
					waitOther = new NoticeUI("Ready", "准备2", "NO");
					Thread.sleep(1000);
					waitOther.dispose();
					waitOther = new NoticeUI("Ready", "准备 1", "NO");
					Thread.sleep(1000);
					waitOther.dispose();
					waitOther = new NoticeUI("Ready", "开始游戏", "NO");
					Thread.sleep(1000);
					waitOther.dispose();
					result = serverIn.readLine();
					allowMove = true;

					Thread stateThread = new Thread(MainUI.getStatePanel());
					stateThread.start();

				} else if (result.startsWith("otherPlayerName")) {

					// Get the competitor's name
					otherPlayerName = result.split(",")[1];

				} else if (result.equals("youWin")) {

					// Set you're the winner
					DrawMap.setIsOver();
					DrawMap.setWinner(2);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Set the tank moving
	 * 
	 * @param result
	 */
	private void MeMove(String result) {

		if (result.split(",")[1].equals("W")) {

			// Set the direction
			drawMap.getTank2().setImage(headDir[0]);

			// Judge
			if ((drawMap.getTank2().getY() / 30 - 1) > 0 && drawMap
					.getBlock(drawMap.getTank2().getY() / 30 - 1, drawMap.getTank2().getX() / 30).equals("0")) {

				drawMap.setMap(drawMap.getTank2().getY() / 30, drawMap.getTank2().getX() / 30, "0");
				drawMap.setMap(drawMap.getTank2().getY() / 30 - 1, drawMap.getTank2().getX() / 30, "6");
			}

		} else if (result.split(",")[1].equals("A")) {

			// Set the direction
			drawMap.getTank2().setImage(headDir[3]);

			// Judge
			if ((drawMap.getTank2().getX() / 30 - 1) > 0 && drawMap
					.getBlock(drawMap.getTank2().getY() / 30, drawMap.getTank2().getX() / 30 - 1).equals("0")) {

				drawMap.setMap(drawMap.getTank2().getY() / 30, drawMap.getTank2().getX() / 30, "0");
				drawMap.setMap(drawMap.getTank2().getY() / 30, drawMap.getTank2().getX() / 30 - 1, "6");
			}

		} else if (result.split(",")[1].equals("S")) {

			// Set the direction
			drawMap.getTank2().setImage(headDir[2]);

			// Judge
			if ((drawMap.getTank2().getY() / 30 + 1) > 0 && drawMap
					.getBlock(drawMap.getTank2().getY() / 30 + 1, drawMap.getTank2().getX() / 30).equals("0")) {

				drawMap.setMap(drawMap.getTank2().getY() / 30, drawMap.getTank2().getX() / 30, "0");
				drawMap.setMap(drawMap.getTank2().getY() / 30 + 1, drawMap.getTank2().getX() / 30, "6");
			}

		} else if (result.split(",")[1].equals("D")) {

			// Set the direction
			drawMap.getTank2().setImage(headDir[1]);

			// Judge
			if ((drawMap.getTank2().getX() / 30 + 1) > 0 && drawMap
					.getBlock(drawMap.getTank2().getY() / 30, drawMap.getTank2().getX() / 30 + 1).equals("0")) {

				drawMap.setMap(drawMap.getTank2().getY() / 30, drawMap.getTank2().getX() / 30, "0");
				drawMap.setMap(drawMap.getTank2().getY() / 30, drawMap.getTank2().getX() / 30 + 1, "6");
			}

		} else if (result.split(",")[1].equals("J")) {

			// Send bullets
			SendBullet bu = new SendBullet(drawMap.getTank2().getX(), drawMap.getTank2().getY(),
					drawMap.getTank2().getDir(), serverIn, serverOut, "score");
			Thread buThread = new Thread(bu);
			buThread.start();
		}

	}

	/**
	 * Set another tank moving
	 * 
	 * @param result
	 */
	public void OtherMove(String result) {

		// Send bullets
		if (result.split(",")[1].equals("J")) {

			SendBullet bu = new SendBullet(drawMap.getTank().getX(), drawMap.getTank().getY(),
					drawMap.getTank().getDir(), serverIn, serverOut, "noScore");
			Thread buThread = new Thread(bu);
			buThread.start();
		} else {

			// Set the directions
			drawMap.getTank().setImage(result.split(",")[5]);

			// Judge and move
			if (Integer.parseInt(result.split(",")[1]) != 0 || Integer.parseInt(result.split(",")[2]) != 0
					|| Integer.parseInt(result.split(",")[3]) != 0 || Integer.parseInt(result.split(",")[4]) != 0) {

				drawMap.setMap(Integer.parseInt(result.split(",")[1]), Integer.parseInt(result.split(",")[2]), "0");
				drawMap.setMap(Integer.parseInt(result.split(",")[3]), Integer.parseInt(result.split(",")[4]), "5");
			}
		}
	}

	// Get your competitor's name
	public static String getOtherName() {
		return otherPlayerName;
	}

}
