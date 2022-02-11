/*
 * Name : Focus.java
 *
 * Function : To focus on different panels and screens
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/22
 */

package actions;

import javax.swing.JComponent;
import getElements.DrawMap;

/**
 * This class is used to focus on different panels and screens
 * @author Zevin
 *
 */
public class Focus implements Runnable{

	JComponent who;
	JComponent defaultWho;
	
	/** Set the singleton */
	private static Focus focusOnWho;

	private Focus() {
	}

	public static synchronized Focus getInstance() {
		if (focusOnWho == null) {
			focusOnWho = new Focus();
		}
		return focusOnWho;
	}
	
	/** Set focus object */
	public void setFocus(JComponent j) {
		if(j instanceof DrawMap) {
			defaultWho = j;
		}
		who = j;
	}
	
	/** Set the default focus object */
	public void setDefault() {
		who = defaultWho;
	}
	
	/** Get the focus object */
	private JComponent getWho() {
		return who;
	}
	
	/** Focus the object */
	public void run() {
		while(true) {
			getWho().requestFocus();
		}
		
	}

	
}
