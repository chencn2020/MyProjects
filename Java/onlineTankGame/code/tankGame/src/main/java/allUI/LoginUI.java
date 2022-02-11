/*
 * Name : LoginUI.java
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
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.border.EmptyBorder;

/**
 * This class is used to ask the player to log in the game
 * @author Zevin
 *
 */
public class LoginUI extends JFrame {

	private JPanel contentPane;
	private JTextField userNametextField;
	private JPasswordField passwordField;
	private JButton cancelButton;
	private JButton signUpButton;
	private static BufferedReader serverIn;
	private static PrintStream serverOut;
	private String playerNum;

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public LoginUI() throws UnknownHostException, IOException {
		final JFrame frame = new JFrame();
		
		// Connect with the server
		final Socket server = new Socket("127.0.0.1", 15319);
		
		// Used to test
//		Scanner scan = new Scanner(System.in);
//		System.out.println("输入IP：");
//		String ip = scan.nextLine();
//		final Socket server = new Socket(ip, 15319);
		
		serverIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
		serverOut = new PrintStream(server.getOutputStream());

		// Set the frame
		frame.setResizable(false);
		frame.setTitle("\u6B22\u8FCE\u6765\u5230\u5766\u514B\u5927\u6218");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img\\LoginTank.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 409, 223);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		// Set the user name label
		JLabel userNameLabel = new JLabel("\u7528\u6237\u540D");
		userNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userNameLabel.setFont(new Font("宋体", Font.BOLD, 20));
		userNameLabel.setBounds(10, 10, 65, 45);
		contentPane.add(userNameLabel);

		// Set the user name text field
		userNametextField = new JTextField();
		userNametextField.setBounds(92, 17, 160, 35);
		contentPane.add(userNametextField);
		userNametextField.setColumns(10);

		// Set the password label
		JLabel passwordLabel = new JLabel("\u5BC6\u7801");
		passwordLabel.setFont(new Font("宋体", Font.BOLD, 20));
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setBounds(20, 65, 54, 47);
		contentPane.add(passwordLabel);

		// Set the password text field
		passwordField = new JPasswordField();
		passwordField.setBounds(92, 77, 160, 35);
		contentPane.add(passwordField);

		// Set the confirm button
		JButton comfirmButton = new JButton("\u767B\u5F55");
		comfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					// Judge whether the information is valid
					if (userNametextField.getText().equals("") || new String(passwordField.getPassword()).equals("")) {
						new NoticeUI("Warning", "请输入正确的信息", "YES");
					} else {
						
						// Send the log in message to the server
						serverOut.println("logIn," + userNametextField.getText() + ","
								+ new String(passwordField.getPassword()) + ",end123");
						
						// Get the result
						String tem = serverIn.readLine();
						if (tem.endsWith("true")) {
							
							// If the password and user name is valid, the game ui will be shown
							playerNum = tem.split(",")[0];

							MainUI main;
							main = new MainUI(serverIn, serverOut, server, playerNum, tem.split(",")[1]);
							main.setVisible(true);
							Thread thread = new Thread(main);
							thread.start();
							
							// Close the log in ui
							frame.dispose();
						} else {
							
							// If the password or the user name is wrong, notice the player
							NoticeUI notice = new NoticeUI("Warning", "用户名或密码错误", "YES");
						}
					}

				} catch (Exception e1) {
					NoticeUI notice = new NoticeUI("Warning", "请检查网络连接", "YES");
					e1.printStackTrace();
				}

			}
		});

		comfirmButton.setFont(new Font("宋体", Font.BOLD, 20));
		comfirmButton.setBounds(76, 138, 100, 35);
		contentPane.add(comfirmButton);

		// Set the cancel button
		cancelButton = new JButton("\u53D6\u6D88");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		cancelButton.setFont(new Font("宋体", Font.BOLD, 20));
		cancelButton.setBounds(206, 138, 100, 35);
		contentPane.add(cancelButton);

		// Set the forget password button
		JButton forgetPasswordButton = new JButton("\u5FD8\u8BB0\u5BC6\u7801");
		forgetPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ForgetUI forget = new ForgetUI(serverIn, serverOut);
			}
		});
		forgetPasswordButton.setFont(new Font("宋体", Font.BOLD, 15));
		forgetPasswordButton.setBounds(273, 85, 110, 27);
		contentPane.add(forgetPasswordButton);

		// Set the sign up button
		signUpButton = new JButton("\u6CE8\u518C\u8D26\u53F7");
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp sign = new SignUp(serverIn, serverOut);
			}
		});
		signUpButton.setFont(new Font("宋体", Font.BOLD, 15));
		signUpButton.setBounds(273, 21, 110, 27);
		contentPane.add(signUpButton);

		frame.setVisible(true);
	}
}
