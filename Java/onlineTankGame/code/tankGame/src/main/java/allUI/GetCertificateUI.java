/*
 * Name : GetCertificateUI.java
 *
 * Function : To send the certificate to the player's mail
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package allUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.EmptyBorder;
import actions.Focus;

/**
 * This class is used to send the certificate to the player's mail
 * @author Zevin
 *
 */
public class GetCertificateUI extends JFrame {

	/** Set the panel */
	private JPanel contentPane;
	private JTextField mailTextField;
	private Focus f = Focus.getInstance();


	/**
	 * Create the frame.
	 */
	public GetCertificateUI(final BufferedReader serverIn, final PrintStream serverOut) {
		
		// Set the frame
		setTitle("\u83B7\u53D6\u8BC1\u4E66");
		
		// Add window listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				f.setDefault();
				dispose();
			}

		});
		setResizable(false);
		setAlwaysOnTop(true);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\LoginTank.png"));
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// Set the information label
		JLabel label = new JLabel(
				"\u8BF7\u8F93\u5165\u4F60\u7684\u90AE\u7BB1\u5730\u5740\uFF0C\u6211\u4EEC\u4F1A\u5C06\u8BC1\u4E66\u53D1\u9001\u7ED9\u4F60");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("宋体", Font.BOLD, 20));
		contentPane.add(label, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Set the confirm button
		JButton confirmButton = new JButton("\u786E\u5B9A");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverOut.println("getCertificate," + mailTextField.getText() + ",end123");
				dispose();
			}
		});
		confirmButton.setFont(new Font("宋体", Font.BOLD, 20));
		panel.add(confirmButton);

		// Set the cancel button
		JButton cancelButton = new JButton("\u53D6\u6D88");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.setDefault();
				dispose();
			}
		});
		cancelButton.setFont(new Font("宋体", Font.BOLD, 20));
		panel.add(cancelButton);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		// Ask the player to enter the mail
		mailTextField = new JTextField();
		mailTextField.setBounds(170, 69, 228, 26);
		panel_1.add(mailTextField);
		mailTextField.setColumns(10);

		JLabel mailAddress = new JLabel("\u90AE\u7BB1\u5730\u5740");
		mailAddress.setHorizontalAlignment(SwingConstants.CENTER);
		mailAddress.setFont(new Font("宋体", Font.BOLD, 20));
		mailAddress.setBounds(44, 69, 107, 20);
		panel_1.add(mailAddress);
		
		setVisible(true);
		
		f.setFocus(mailTextField);
	}
}
