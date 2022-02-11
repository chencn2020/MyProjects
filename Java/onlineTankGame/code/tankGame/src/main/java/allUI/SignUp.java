/*
 * Name : SignUp.java
 *
 * Function : To make it possible to player to sign up a new account
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
import java.io.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

/**
 * This class is used to make it possible to player to sign up a new account
 * 
 * @author Zevin
 *
 */
public class SignUp extends JFrame {

	private JPanel contentPane;

	/** Set the field */
	private JTextField userNameTextField;
	private JPasswordField passwordField;
	private JPasswordField password2Field;
	private JTextField phoneNumTextField;

	/** Set the input and output stream */
	private static BufferedReader serverIn;
	private static PrintStream serverOut;

	private String tankImg = "img\\LoginTank.png";

	/**
	 * Create the frame.
	 */
	public SignUp(final BufferedReader serverIn, final PrintStream serverOut) {

		// Set the input and output stream
		this.serverIn = serverIn;
		this.serverOut = serverOut;

		// Set the frame
		final JFrame frame = new JFrame();
		frame.setTitle("\u6CE8\u518C\u8D26\u53F7");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(tankImg));
		frame.setResizable(false);
		frame.setBounds(100, 100, 367, 324);
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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// Set the user name label
		JLabel userNameLabel = new JLabel("\u8BF7\u8F93\u5165\u7528\u6237\u540D");
		userNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userNameLabel.setFont(new Font("宋体", Font.BOLD, 20));
		userNameLabel.setBounds(17, 20, 132, 31);
		contentPane.add(userNameLabel);

		// Set the user name text field
		userNameTextField = new JTextField();
		userNameTextField.setBounds(186, 20, 142, 31);
		contentPane.add(userNameTextField);
		userNameTextField.setColumns(10);

		// Set the password label
		JLabel passwordLabel = new JLabel("\u8BF7\u8F93\u5165\u5BC6\u7801");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setFont(new Font("宋体", Font.BOLD, 20));
		passwordLabel.setBounds(34, 125, 115, 31);
		contentPane.add(passwordLabel);

		// Set the password text field
		passwordField = new JPasswordField();
		passwordField.setBounds(184, 127, 144, 31);
		contentPane.add(passwordField);

		// Set the input password again label
		JLabel password2Label = new JLabel("\u8BF7\u786E\u8BA4\u5BC6\u7801");
		password2Label.setHorizontalAlignment(SwingConstants.RIGHT);
		password2Label.setFont(new Font("宋体", Font.BOLD, 20));
		password2Label.setBounds(34, 183, 115, 31);
		contentPane.add(password2Label);

		// Set the input password again text field
		password2Field = new JPasswordField();
		password2Field.setBounds(186, 185, 142, 31);
		contentPane.add(password2Field);

		// Set the phone number label
		final JLabel phoneNumLabel = new JLabel("\u8BF7\u8F93\u5165\u624B\u673A\u53F7");
		phoneNumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneNumLabel.setFont(new Font("宋体", Font.BOLD, 20));
		phoneNumLabel.setBounds(10, 73, 139, 31);
		contentPane.add(phoneNumLabel);

		// Set the confirm button
		JButton button = new JButton("\u6CE8\u518C");
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				// Judge whether the password is valid
				if (new String(passwordField.getPassword()).equals(new String(password2Field.getPassword()))) {
					if (userNameTextField.getText().equals("") || phoneNumLabel.getText().equals("")) {
						new NoticeUI("Warning", "请输入正确的信息", "YES");
					} else {

						// Judge whether the phone number is valid
						if (phoneNumTextField.getText().length() != 11) {
							new NoticeUI("Warning", "请输入正确的手机号", "YES");
						} else {
							
							// Send the sing up order to the server
							serverOut.println("singUp," + userNameTextField.getText() + ","
									+ new String(passwordField.getPassword()) + "," + phoneNumTextField.getText()
									+ ",end123");
							try {
								
								// Get the result from the server
								String s = serverIn.readLine();
								if (s.equals("true")) {
									
									new NoticeUI("Success", "注册成功，祝你游戏愉快", "YES");
									passwordField.setText("");
									password2Field.setText("");
									userNameTextField.setText("");
									phoneNumTextField.setText("");
								} else {
									new NoticeUI("Warning", "服务器忙，请稍后.", "YES");
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				} else {
					new NoticeUI("Error", "两次密码不同", "YES");
				}
			}
		});
		button.setFont(new Font("宋体", Font.BOLD, 20));
		button.setBounds(34, 241, 100, 31);
		contentPane.add(button);

		// Set the cancel button
		JButton cancelButton = new JButton("\u53D6\u6D88");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		cancelButton.setFont(new Font("宋体", Font.BOLD, 20));
		cancelButton.setBounds(224, 241, 100, 31);
		contentPane.add(cancelButton);

		// Set the phone number text field
		phoneNumTextField = new JTextField();
		phoneNumTextField.setBounds(186, 75, 142, 31);
		contentPane.add(phoneNumTextField);
		phoneNumTextField.setColumns(10);

		frame.setVisible(true);
	}
}
