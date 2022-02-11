/*
 * Name : ChatPanel.java
 *
 * Function : To set the chat room panel
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package allUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import actions.Focus;

/**
 * This class is used to set the chat room panel
 * 
 * @author Zevin
 *
 */
public class ChatPanel {

	/** Set the chat panel and some text area */
	private JPanel chatPanel;
	private JTextArea textArea;
	private JTextArea chatArea;
	private JButton sendButton;

	/** Set the input and output stream */
	private static BufferedReader serverIn;
	private static PrintStream serverOut;
	private Focus f = Focus.getInstance();

	/** Initialize the input, output stream and other elements */
	public ChatPanel(BufferedReader serverIn, PrintStream serverOut) {
		this.serverIn = serverIn;
		this.serverOut = serverOut;

		// Set the chat panel
		chatPanel = new JPanel();
		chatPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		chatPanel.setBounds(10, 294, 230, 296);
		chatPanel.setLayout(null);

		// Set the chat area
		chatArea = new JTextArea();
		chatArea.setBounds(5, 5, 220, 246);
		chatPanel.add(chatArea);
		chatArea.setEditable(false);

		// Set the send button
		sendButton = new JButton("\u53D1\u9001");
		sendButton.setBounds(162, 261, 63, 30);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Use the sendMessage method
				sendMessage();

				// Clear the text area
				textArea.setText("");
			}
		});
		chatPanel.add(sendButton);
		sendButton.setFont(new Font("ËÎÌו", Font.BOLD, 12));

		// Set the text area
		textArea = new JTextArea();
		textArea.setBounds(5, 261, 152, 30);
		chatPanel.add(textArea);
		textArea.setColumns(10);

		// Make the text area to be focused
		textArea.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.setFocus(textArea);
			}
		});

		// You can send the message by press the Enter key
		textArea.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (KeyEvent.VK_ENTER == e.getKeyCode()) {
					sendMessage();
					e.consume();
					textArea.setText("");
				}
			}

		});

	}

	/** Send the message */
	private void sendMessage() {
		if (textArea.getText().equals("") == false) {

			// Send the message to the server
			serverOut.println("chat," + textArea.getText() + ",end123");
		}
	}

	/**
	 * Return the panel
	 * 
	 * @return JPanel
	 */
	public JPanel getPanel() {
		return chatPanel;
	}

	/** Set the chat area */
	public void setChatArea(String s) {
		chatArea.append(s + "\n");
	}
}
