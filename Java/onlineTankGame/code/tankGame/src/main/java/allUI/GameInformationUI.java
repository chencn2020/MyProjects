/*
 * Name : GameInformationUI.java
 *
 * Function : To show the game information
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package allUI;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import actions.Focus;

/**
 * This class is used to show the game information
 * @author Zevin
 *
 */
public class GameInformationUI extends JFrame {

	/** Set the panel */
	private JPanel contentPane;
	private Focus f = Focus.getInstance();

	/**
	 * Create the frame.
	 */
	public GameInformationUI(String content) {

		// Add the window listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				f.setDefault();
				dispose();
			}
		});
		
		// Set the frame's information
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("玩家信息");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 477, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Set the confirm button
		JButton button = new JButton("\u786E\u5B9A");
		button.setBounds(0, 302, 468, 33);
		button.setFont(new Font("宋体", Font.BOLD, 20));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.setDefault();
				dispose();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(button);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 468, 20);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		// Set the user name label
		JLabel user1 = new JLabel("\u73A9\u5BB6\u7528\u6237\u540D");
		user1.setFont(new Font("宋体", Font.BOLD, 15));
		panel.add(user1);

		// Set the space
		JLabel lblNewLabel = new JLabel("  ");
		panel.add(lblNewLabel);

		// Set the other user name label
		JLabel user2 = new JLabel("\u5BF9\u624B\u7528\u6237\u540D");
		user2.setFont(new Font("宋体", Font.BOLD, 15));
		panel.add(user2);

		// Set the space
		JLabel label_5 = new JLabel("  ");
		panel.add(label_5);

		// Set the score1 label
		JLabel socre1 = new JLabel("\u73A9\u5BB6\u5206\u6570");
		socre1.setFont(new Font("宋体", Font.BOLD, 15));
		panel.add(socre1);

		// Set the space
		JLabel label_6 = new JLabel("  ");
		panel.add(label_6);

		// Set the score2 label
		JLabel score2 = new JLabel("\u5BF9\u624B\u5206\u6570");
		score2.setFont(new Font("宋体", Font.BOLD, 15));
		panel.add(score2);

		// Set the space
		JLabel label_7 = new JLabel("  ");
		panel.add(label_7);

		// Set the game time label
		JLabel gameTime = new JLabel("\u6E38\u620F\u65F6\u95F4");
		gameTime.setFont(new Font("宋体", Font.BOLD, 15));
		panel.add(gameTime);

		// Set the scroll panel
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(0, 20, 468, 292);
		contentPane.add(scrollPane);
		
		// Split the game information form the server by "end123"
		String[] game = content.split("end123");
		String tem = "";
		
		// Output the information in a specific way
		for (int i = 0; i < game.length; i++) {
			String s = String.format("%-25s%-25s%-20s%-20s%-10s", game[i].split(",")[0], game[i].split(",")[1],
					game[i].split(",")[2], game[i].split(",")[3], game[i].split(",")[4]);
			tem += s + ",";
		}

		// Set the information
		String[] data = tem.split(",");
		JList<String> list = new JList<String>(data);

		// Add the information to the scrollPanel
		scrollPane.setViewportView(list);
		f.setFocus(scrollPane);

		setVisible(true);
	}
}
