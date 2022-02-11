/*
 * Name : ResetPasswordUI.java
 *
 * Function : To reset the password
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
 * This class is used to reset the password
 * @author Zevin
 *
 */
public class ResetPasswordUI extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Create the frame.
	 */
	public ResetPasswordUI(final BufferedReader serverIn, final PrintStream serverOut) {
		
		// Set the title
		final JFrame frame = new JFrame("重设密码");
		
		// Add window listener
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		// Set the frame
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img\\LoginTank.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 298, 201);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		// Set the password label
		JLabel passwordLabel = new JLabel("\u8BF7\u8F93\u5165\u5BC6\u7801");
		passwordLabel.setFont(new Font("宋体", Font.BOLD, 15));
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setBounds(48, 32, 88, 18);
		contentPane.add(passwordLabel);

		// Set the password field
		passwordField = new JPasswordField();
		passwordField.setBounds(164, 31, 88, 21);
		contentPane.add(passwordField);

		// Set the input password again label
		JLabel passwordAgainLabel = new JLabel("\u8BF7\u518D\u6B21\u786E\u8BA4\u5BC6\u7801");
		passwordAgainLabel.setFont(new Font("宋体", Font.BOLD, 15));
		passwordAgainLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordAgainLabel.setBounds(27, 69, 109, 18);
		contentPane.add(passwordAgainLabel);

		// Set the input password again field
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(164, 68, 88, 21);
		contentPane.add(passwordField_1);

		// Set the confirm button
		JButton confirmButton = new JButton("\u786E\u5B9A");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Judge whether the password is valid
				if (new String(passwordField_1.getPassword()).equals(new String(passwordField.getPassword()))) {

					if (new String(passwordField_1.getPassword()).equals("")
							|| new String(passwordField.getPassword()).equals("")) {
						new NoticeUI("Warning", "请输入密码", "YES");
					} else {
						
						// Send the reset order to the server
						serverOut.println("RESET," + new String(passwordField_1.getPassword()) + ",end123");
						try {
							
							// Get the result
							String s = serverIn.readLine();
							if (s.equals("true")) {
								new NoticeUI("Success", "重设成功", "YES");

								frame.dispose();
							} else {
								new NoticeUI("Busy", "服务器繁忙，请稍后重试", "YES");
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					new NoticeUI("Warning", "两次密码不相同", "YES");
				}

			}
		});
		confirmButton.setFont(new Font("宋体", Font.BOLD, 15));
		confirmButton.setBounds(37, 113, 93, 23);
		contentPane.add(confirmButton);

		// Set the cancel button
		JButton cancelButton = new JButton("\u53D6\u6D88");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setFont(new Font("宋体", Font.BOLD, 15));
		cancelButton.setBounds(171, 113, 93, 23);
		contentPane.add(cancelButton);
		frame.setVisible(true);
	}

}
