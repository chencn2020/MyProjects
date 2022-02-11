/*
 * Name : Judge.java
 *
 * Function : To set whether the game is over
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/22
 */


package actions;

/**
 * This class is used to set whether the game is over
 * @author Zevin
 *
 */
public class Judge {

	/** Set the singleton */
	private boolean isOver = false;
	
	private static Judge judge ;
	private Judge() {
	}
	
	public static synchronized Judge getInstance() {
		if (judge == null) {
			judge = new Judge();
		}
		return judge;
	}
	
	/** Set the game is over */
	public void setIsOver() {
		if(isOver == false) {
			isOver = true;
		} 
	}
	
	/** Get the result */
	public boolean getIsOver() {
		return isOver;
	}
}
