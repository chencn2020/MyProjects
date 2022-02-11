/*
 * Name : Repaint.java
 *
 * Function : To repaint the panel
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/22
 */

package actions;

import getElements.DrawMap;

/**
 * This class is used to repaint the panel
 * 
 * @author Zevin
 *
 */
public class Repaint implements Runnable {

	private DrawMap map;

	/** Get the map panel */
	public void getMap() {
		map = DrawMap.getInstance();
	}

	/** Set the repaint thread */
	public void run() {
		while (true) {
			try {

				// This panel will be repainted in each 100 ms
				Thread.sleep(100);
				map.repaint();
			} catch (InterruptedException e) {
			}

		}

	}

}
