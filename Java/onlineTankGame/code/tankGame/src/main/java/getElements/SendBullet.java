/*
 * Name : SendBullet.java
 *
 * Function : To send the bullet and judge which block will the bullet hits
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package getElements;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.PrintStream;
import javax.swing.ImageIcon;
import actions.Judge;
import music.PlayMusic;

/**
 * This class is used to send the bullet and judge which block will the bullet
 * hits
 * 
 * @author Zevin
 *
 */
public class SendBullet implements Runnable {

	/** Set the bullet position */
	private int x, y, width, height;

	/** Set the bullet image */
	private Image image;

	/** Set the direction */
	private String dir = "U";
	private String num;
	private String path;
	private DrawMap map;
	private boolean isBullet = false;
	private BufferedReader serverIn;
	private PrintStream serverOut;
	private String type;

	/** Initialize the values */
	public SendBullet(int x, int y, String dir, BufferedReader serverIn, PrintStream serverOut, String type) {
		new PlayMusic().sendBullet();
		map = DrawMap.getInstance();
		path = "img\\bullet.gif";

		this.type = type;
		isBullet = true;
		this.dir = dir;
		this.image = new ImageIcon(path).getImage();
		this.x = x;
		this.y = y;
		this.width = 30;
		this.height = 30;

		this.serverIn = serverIn;
		this.serverOut = serverOut;

	}

	/** Judge whether it is bullet */
	public boolean getState() {
		return isBullet;
	}

	/** Set the bullet's direction */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/** Set the x position */
	public void setX(int x) {
		this.x = x;
	}

	/** Set the y position */
	public void setY(int y) {
		this.y = y;
	}

	/** Get the x position */
	public int getX() {
		return x;
	}

	/** Get the y position */
	public int getY() {
		return y;
	}

	/** Get the width of the bullet */
	public int getWidth() {
		return width;
	}

	/** Get the height of the bullet */
	public int getHeight() {
		return height;
	}

	/** Get the image of the bullet */
	public Image getImage() {
		return image;
	}

	/** Let the bullet run */
	public void run() {

		int blockX = 0, blockY = 0;
		Judge judge = Judge.getInstance();

		boolean isWall = false;

		// Judge the direction
		if (dir.equals("U")) {
			blockX = this.x;
			blockY = this.y - 30;
		} else if (dir.equals("D")) {
			blockX = this.x;
			blockY = this.y + 30;
		} else if (dir.equals("R")) {
			blockX = this.x + 30;
			blockY = this.y;
		} else if (dir.equals("L")) {
			blockX = this.x - 30;
			blockY = this.y;
		}

		// Judge which block it is when the bullet was firstly sent
		if (map.getBlock(blockY / 30, blockX / 30).equals("1")) {

			// Play the music
			new PlayMusic().blocksBeaten();
			isWall = true;

			// Each block will make you earn 10 pints
			if (type.equals("score")) {
				serverOut.println("addScore,10,me,end123");
			}

			map.setMap(blockY / 30, blockX / 30, "0");
			map.getGraphics().clearRect(blockX, blockY, 30, 30);
		} else if (map.getBlock(blockY / 30, blockX / 30).equals("4")) {

			// Your competitor's home is distorted. And you win.
			map.setIsOver();
			map.setWinner(2);
			isWall = true;
		} else if (map.getBlock(blockY / 30, blockX / 30).equals("5")) {

			// Play the music
			new PlayMusic().tanksBeaten();

			// The hp will substance when you hit other's tank
			serverOut.println("subHpOther");

			// You will gain 50 points
			if (type.equals("score")) {
				serverOut.println("addScore,50,me,end123");
			}

			map.setMap(blockY / 30, blockX / 30, "0");
			map.getGraphics().clearRect(blockX, blockY, 30, 30);
			map.setMap(map.getTank().getOY(), map.getTank().getOX(), "5");
			isWall = true;
		} else if (map.getBlock(blockY / 30, blockX / 30).equals("6")) {
			
			// Play the music
			new PlayMusic().tanksBeaten();
			
			// The hp will substance when you hit other's tank
			serverOut.println("subHP");
			
			// Your competitor will gain 50 points
			if (type.equals("score")) {
				serverOut.println("addScore,50,other,end123");
			}

			map.setMap(blockY / 30, blockX / 30, "0");
			map.getGraphics().clearRect(blockX, blockY, 30, 30);
			map.setMap(map.getTank2().getOY(), map.getTank2().getOX(), "6");
			isWall = true;
		} else if (map.getBlock(blockY / 30, blockX / 30).equals("7")) {
			
			// Your home is distorted. And you lose.
			map.setIsOver();
			map.setWinner(1);
			isWall = true;
		}

		if (map.getBlock(blockY / 30, blockX / 30).equals("2") || map.getBlock(blockY / 30, blockX / 30).equals("3")) {
			
			// Number 2 and 3 mean this block will not be broken
			isWall = true;
		}

		while (isWall == false) {
			
			// Judge whether this game is over
			if (judge.getIsOver()) {
				break;
			}
			try {

				// Get the direction
				if (dir.equals("U")) {
					y -= 30;
					blockX = this.x;
					blockY = this.y - 30;
					map.getGraphics().drawImage(image, x, y, 30, 30, null);
				} else if (dir.equals("D")) {
					y += 30;
					blockX = this.x;
					blockY = this.y + 30;
					map.getGraphics().drawImage(image, x, y, 30, 30, null);
				} else if (dir.equals("R")) {
					x += 30;
					blockX = this.x + 30;
					blockY = this.y;
					map.getGraphics().drawImage(image, x, y, 30, 30, null);
				} else if (dir.equals("L")) {
					x -= 30;
					blockX = this.x - 30;
					blockY = this.y;
					map.getGraphics().drawImage(image, x, y, 30, 30, null);
				}
				
				// The bullet will move forward per 100 ms
				Thread.sleep(100);

				if (map.getBlock(blockY / 30, blockX / 30).equals("1")) {
					new PlayMusic().blocksBeaten();

					map.setMap(y / 30, x / 30, "0");
					map.setMap(blockY / 30, blockX / 30, "0");
					map.getGraphics().clearRect(x, y, 30, 30);
					map.getGraphics().clearRect(blockX, blockY, 30, 30);

					// Get the 10 pionts
					if (type.equals("score")) {
						serverOut.println("addScore,10,me,end123");
					}

					break;
				} else if (map.getBlock(blockY / 30, blockX / 30).equals("2")) {
					
					// This block can't be broken
					map.setMap(y / 30, x / 30, "0");
					map.getGraphics().clearRect(x, y, 30, 30);
					break;
				} else if (map.getBlock(blockY / 30, blockX / 30).equals("4")) {
					
					// Set the winner
					map.setIsOver();
					map.setWinner(2);

					map.setMap(y / 30, x / 30, "0");
					map.setMap(blockY / 30, blockX / 30, "0");
					map.getGraphics().clearRect(x, y, 30, 30);
					map.getGraphics().clearRect(blockX, blockY, 30, 30);
					break;
				} else if (map.getBlock(blockY / 30, blockX / 30).equals("5")) {
					
					// Play the music
					new PlayMusic().tanksBeaten();

					// Add the score and substance the other's hp
					serverOut.println("subHpOther");
					if (type.equals("score")) {
						serverOut.println("addScore,50,me,end123");
					}

					map.setMap(blockY / 30, blockX / 30, "0");
					map.getGraphics().clearRect(blockX, blockY, 30, 30);
					map.setMap(map.getTank().getOY(), map.getTank().getOX(), "5");

					break;
				} else if (map.getBlock(blockY / 30, blockX / 30).equals("6")) {
					
					// Play the music
					new PlayMusic().tanksBeaten();

					// Add the score and substance your hp
					serverOut.println("subHP");
					if (type.equals("score")) {
						serverOut.println("addScore,50,other,end123");
					}

					map.setMap(blockY / 30, blockX / 30, "0");
					map.getGraphics().clearRect(blockX, blockY, 30, 30);
					map.setMap(map.getTank2().getOY(), map.getTank2().getOX(), "6");

					break;
				} else if (map.getBlock(blockY / 30, blockX / 30).equals("7")) {
					
					// Set the winner
					map.setIsOver();
					map.setWinner(1);

					map.setMap(y / 30, x / 30, "0");
					map.setMap(blockY / 30, blockX / 30, "0");
					map.getGraphics().clearRect(x, y, 30, 30);
					map.getGraphics().clearRect(blockX, blockY, 30, 30);
					break;
				}

				// The bullet can cross the river(Number 3 block)
				if (map.getBlock(blockY / 30, blockX / 30).equals("3")) {
					do {
						if (dir.equals("U")) {
							y -= 30;
							blockX = this.x;
							blockY = this.y - 30;
						} else if (dir.equals("D")) {
							y += 30;
							blockX = this.x;
							blockY = this.y + 30;
						} else if (dir.equals("R")) {
							x += 30;
							blockX = this.x + 30;
							blockY = this.y;
						} else if (dir.equals("L")) {
							x -= 30;
							blockX = this.x - 30;
							blockY = this.y;
						}
					} while (map.getBlock(blockY / 30, blockX / 30).equals("3") == false);
					if (dir.equals("U")) {
						y -= 30;
						blockX = this.x;
						blockY = this.y - 30;
					} else if (dir.equals("D")) {
						y += 30;
						blockX = this.x;
						blockY = this.y + 30;
					} else if (dir.equals("R")) {
						x += 30;
						blockX = this.x + 30;
						blockY = this.y;
					} else if (dir.equals("L")) {
						x -= 30;
						blockX = this.x - 30;
						blockY = this.y;
					}
				} else {
					map.setMap(y / 30, x / 30, "0");
					map.getGraphics().clearRect(x, y, 30, 30);
				}

			} catch (InterruptedException e) {
			}
		}

	}
}
