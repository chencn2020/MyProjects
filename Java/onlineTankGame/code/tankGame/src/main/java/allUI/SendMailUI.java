/*
 * Name : SendMailUI.java
 *
 * Function : To make it possible to player to send the suggestion
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
import java.io.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import actions.Focus;

/**
 * This class is used to make it possible to player to send the suggestion
 * 
 * @author Zevin
 *
 */
public class SendMailUI extends JFrame {

	private JPanel contentPane;
	private Focus f = Focus.getInstance();

	/**
	 * Create the frame.
	 */
	public SendMailUI(final BufferedReader serverIn, final PrintStream serverOut) {
		
		// Set the window listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				f.setDefault();
				dispose();
			}
		});

		// Set the frame
		setAlwaysOnTop(true);
		setTitle("\u8054\u7CFB\u6211\u4EEC");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 517, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// Set the notice label
		JLabel label = new JLabel(
				"\u5982\u679C\u4F60\u6709\u4EC0\u4E48\u597D\u7684\u610F\u89C1\u6216\u8005\u5EFA\u8BAE\uFF0C\u8BF7\u544A\u8BC9\u6211\u4EEC");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("宋体", Font.BOLD, 15));
		contentPane.add(label, BorderLayout.NORTH);

		// Set the text area
		final JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("宋体", Font.BOLD, 15));
		contentPane.add(textArea, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Set the send button
		JButton sendButton = new JButton("\u53D1\u9001");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverOut.println("sendMail\n" + textArea.getText() + "\nend123");
				System.out.println(textArea.getText());
				dispose();
			}
		});
		sendButton.setFont(new Font("宋体", Font.BOLD, 15));
		panel.add(sendButton);

		// Set the cancel button
		JButton cancelButton = new JButton("\u53D6\u6D88");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.setDefault();
				dispose();
			}
		});
		cancelButton.setFont(new Font("宋体", Font.BOLD, 15));
		panel.add(cancelButton);

		f.setFocus(textArea);
	}

}
