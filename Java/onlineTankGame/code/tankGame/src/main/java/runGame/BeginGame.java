/*
 * Name : BeginGame.java
 *
 * Function : To start the game
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/22
 */

package runGame;

import java.awt.EventQueue;
import allUI.*;

public class BeginGame {

	/**
     * @param args the command line arguments
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					// Start the Log in ui
					LoginUI frame = new LoginUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
