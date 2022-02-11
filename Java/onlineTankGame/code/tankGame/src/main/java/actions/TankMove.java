/*
 * Name : TankMove.java
 *
 * Function : To set some values about the tank
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/22
 */

package actions;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * This class is used to set some values about the tank
 * @author Zevin
 *
 */
public class TankMove {

	/** Set the position of the tank */
	private int x, y, width, height;
	
	/** Set the original position of the tank */
	private int OX, OY;
	private Image image;
	private String dir = "U";
	private String num;
	private String path;
	
	/** Get the tank and set the direction */
	public TankMove(String i, String dir) {
		num = i;
		
		// Different number means different color of tank
		if (num.equals("1")) {
			path = "img\\p" + i + "tank" + dir + ".gif";
		} else {
			path = "img\\p" + i + "tank" + dir + ".gif";
		}

		this.dir = dir;
		this.image = new ImageIcon(path).getImage();
		this.width = 30;
		this.height = 30;
	}
	
	/** Set the tank direction */
	public void setImage(String dir) {
		path = "img\\p" + num + "tank" + dir + ".gif";
		this.image = new ImageIcon(path).getImage();
		this.dir = dir;
		this.width = 30;
		this.height = 30;
	}

	/**
	 * Get the direction of the tank
	 * @return String
	 */
	public String getDir() {
		return dir;
	}
	
	/** Set the x position */
	public void setX(int x) {
		this.x = x;
	}

	/** Set the y position */
	public void setY(int y) {
		this.y = y;
	}
	
	/** Set the original x and y position */
	public void setOriginalXY(int x, int y) {
		OX = x;
		OY = y;
	}
	
	/** Get the original x position */
	public int getOX() {
		return OX;
	}
	
	/** Get the original y position */
	public int getOY() {
		return OY;
	}

	/** Get the x position */
	public int getX() {
		return x;
	}

	/** Get the y position */
	public int getY() {
		return y;
	}

	/** Get the width of the tank */
	public int getWidth() {
		return width;
	}

	/** Get the height of the tank */
	public int getHeight() {
		return height;
	}

	/** Get the image of the tank */
	public Image getImage() {
		return image;
	}
}
