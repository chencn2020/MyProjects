/*
 * Name : PlayMusic.java
 *
 * Function : To play the specific music in specific time
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package music;

import java.applet.*;
import java.awt.Frame;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is used to play the specific music in specific time
 * @author Zevin
 *
 */
public class PlayMusic extends Frame {
	
	/** Set the music path */
	String gameOverPath = "Music\\gameOver.wav";
	String startPath = "Music\\start.wav";
	String sendBullets = "Music\\sendBullets.wav";
	String blockBeaten = "Music\\blockBeaten.wav";
	String tankBeaten = "Music\\tankBeaten.wav";
	
	
	
	@SuppressWarnings("deprecation")
	
	/** When the tank was beaten, this music will be play */
	public void tanksBeaten() {
		try {
			URL cb;

			File f = new File(tankBeaten);

			cb = f.toURL();

			AudioClip aau;

			aau = Applet.newAudioClip(cb);

			aau.play();// 循环播放 aau.play() 单曲 aau.stop()停止播放

			// aau.loop();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		}
	}
	
	/** When the block was beaten, this music will be play */
	public void blocksBeaten() {
		try {
			URL cb;

			File f = new File(blockBeaten);

			cb = f.toURL();

			AudioClip aau;

			aau = Applet.newAudioClip(cb);

			aau.play();// 循环播放 aau.play() 单曲 aau.stop()停止播放

			// aau.loop();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		}
	}
	
	/** When the bullet was sent, this music will be play */
	public void sendBullet() {
		try {
			URL cb;

			File f = new File(sendBullets);

			cb = f.toURL();

			AudioClip aau;

			aau = Applet.newAudioClip(cb);

			aau.play();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		}
	}
	
	/** When the game was over, this music will be play */
	public void gameOver() {
		try {

			URL cb;

			File f = new File(gameOverPath);

			cb = f.toURL();

			AudioClip aau;

			aau = Applet.newAudioClip(cb);

			aau.play();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		}
	}
	
	/** When the game was start, this music will be play */
	public void start() {
		try {

			URL cb;

			File f = new File(startPath);

			cb = f.toURL();

			AudioClip aau;

			aau = Applet.newAudioClip(cb);

			aau.play();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		}
	}
}
