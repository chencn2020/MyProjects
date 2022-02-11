/*
 * Name : MessageShowUI.java
 *
 * Function : To show the message
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
import javax.swing.border.EmptyBorder;
import actions.Focus;

/**
 * This class is used to show the message
 * @author Zevin
 *
 */
public class MessageShowUI extends JFrame {

	private JPanel contentPane;
	private Focus f = Focus.getInstance();

	/**
	 * Create the frame.
	 */
	public MessageShowUI(String type, String message) {

		// Set the window listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				f.setDefault();
				dispose();
			}
		});
		
		// Set the frame
		setAlwaysOnTop(true);
		setTitle(type);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setResizable(false);

		// The message will be shown in this area
		JTextArea textArea = new JTextArea();
		textArea.setText(message);
		textArea.setEditable(false);
		contentPane.add(textArea, BorderLayout.CENTER);

		// Set the confirm button
		JButton button = new JButton("\u786E\u5B9A");
		button.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				f.setDefault();
				dispose();
			}

		});
		button.setFont(new Font("ËÎÌו", Font.BOLD, 15));
		contentPane.add(button, BorderLayout.SOUTH);
		f.setFocus(textArea);
		setVisible(true);
	}

}
