/*
 * Name : ForgetUI.java
 *
 * Function : To make it possible to the player to change his password
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */


package allUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.EmptyBorder;

/**
 * This class is used to make it possible to the player to change his password
 * @author Zevin
 *
 */
public class ForgetUI extends JFrame {

	/** Set the panel and text fields */
	private JPanel contentPane;
	private JTextField userNameTextField;
	private JTextField phoneNumTextField;

	/**
	 * Create the frame.
	 */
	public ForgetUI(final BufferedReader serverIn, final PrintStream serverOut) {

		// Add the window listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		setResizable(false);
		setAlwaysOnTop(true);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// Set the JFrame
		final JFrame frame = new JFrame();
		frame.setTitle("\u5FD8\u8BB0\u5BC6\u7801");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img\\LoginTank.png"));
		frame.setBounds(100, 100, 332, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		// Set the window listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});

		// Set the fundamental elements 
		// This is user name lable
		JLabel label = new JLabel("\u8BF7\u8F93\u5165\u7528\u6237\u540D");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(35, 25, 115, 18);
		contentPane.add(label);

		userNameTextField = new JTextField();
		userNameTextField.setBounds(188, 24, 115, 21);
		contentPane.add(userNameTextField);
		userNameTextField.setColumns(10);

		// This is phone number label
		JLabel label_1 = new JLabel("\u8BF7\u8F93\u5165\u6CE8\u518C\u624B\u673A\u53F7");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(10, 65, 140, 18);
		contentPane.add(label_1);

		phoneNumTextField = new JTextField();
		phoneNumTextField.setBounds(188, 64, 115, 21);
		contentPane.add(phoneNumTextField);
		phoneNumTextField.setColumns(10);
		
		// Set the confirm bottom
		JButton confirmButton = new JButton("\u786E\u5B9A");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Judge whether the message is valid
				if (userNameTextField.getText().equals("") || phoneNumTextField.getText().equals("")) {
					new NoticeUI("Warning", "请输入正确的信息", "YES");
				} else {
					serverOut.println("forget," + userNameTextField.getText() + ","
							+ new String(phoneNumTextField.getText()) + ",end123");
					try {
						String s = serverIn.readLine();
						if (s.equals("true")) {
							ResetPasswordUI rePass = new ResetPasswordUI(serverIn, serverOut);
							userNameTextField.setText("");
							phoneNumTextField.setText("");
						} else {
							new NoticeUI("Warning", "请输入正确的信息", "YES");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		confirmButton.setFont(new Font("宋体", Font.BOLD, 15));
		confirmButton.setBounds(44, 118, 93, 23);
		contentPane.add(confirmButton);

		// Set the cancel button
		JButton cancelButton = new JButton("\u53D6\u6D88");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		cancelButton.setFont(new Font("宋体", Font.BOLD, 15));
		cancelButton.setBounds(191, 118, 93, 23);
		contentPane.add(cancelButton);

		frame.setVisible(true);
	}

}
