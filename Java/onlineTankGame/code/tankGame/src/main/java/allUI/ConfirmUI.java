/*
 * Name : ConfirmUI.java
 *
 * Function : To ask the player to confirm some choice
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package allUI;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import actions.Focus;
import javax.swing.border.EmptyBorder;
import getElements.DrawMap;

/**
 * This class is used to ask the player to confirm some choice
 * @author Zevin
 *
 */
public class ConfirmUI extends JFrame {

	/** Set the panel */
	private JPanel contentPane;
	private Focus f = Focus.getInstance();

	/**
	 * Create the frame.
	 * 
	 * @param drawMap
	 */
	public ConfirmUI(final String type, String content, final DrawMap drawMap) {
		
		// Set the title
		if (type.equals("Surrender")) {
			setTitle("投降");
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\LoginTank.png"));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// Set the window listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				f.setDefault();
				dispose();
			}

		});

		setResizable(false);
		setAlwaysOnTop(true);

		// Set the size
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JLabel label = new JLabel(content);
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		// Set the confirm button
		JButton confirmButton = new JButton("\u786E\u5B9A");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// If the player surrendered, the player will lose
				if (type.equals("Surrender")) {
					f.setDefault();
					drawMap.sendMessage("Surrender");
					drawMap.setIsOver();
					drawMap.setWinner(1);
					dispose();
				}
			}
		});
		confirmButton.setFont(new Font("宋体", Font.BOLD, 20));
		panel.add(confirmButton);

		// Set the cancel button
		JButton cancelButton = new JButton("\u518D\u60F3\u60F3");
		cancelButton.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				f.setDefault();
				dispose();
			}

		});
		cancelButton.setFont(new Font("宋体", Font.BOLD, 20));
		panel.add(cancelButton);

		f.setFocus(contentPane);
		setAlwaysOnTop(true);
		setVisible(true);
	}

}
