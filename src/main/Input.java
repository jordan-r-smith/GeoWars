package main;

import java.awt.event.KeyEvent;

/**
 * Class: Input.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Detects input and Sets the buttons pressed in an array
 * 
 */

public class Input {

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int ESC = 4;
	public static final int F = 5;
	
	public boolean[] buttons = new boolean[6];

	/**
	 * Sets the boolean[] to the int that occurs when either W, A, S, D, F, ESC are pressed.
	 * @param key
	 * @param down
	 */
	public void set(int key, boolean down) 
	{
		int button = -1;
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
			button = 0;
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
			button = 1;
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
			button = 2;
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
			button = 3;
		if (key == KeyEvent.VK_ESCAPE)
			button = 4;
		if (key == KeyEvent.VK_F)
			button = 5;

		if (button >= 0)
			buttons[button] = down;
	}
}
