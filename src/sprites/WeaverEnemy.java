package sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Input;

/**
 * Class: WeaverEnemy.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Creates the weaver enemy (green) sprite.  
 * Overrides the moveToPlayer method to move the sprites faster.
 * 
 */
public class WeaverEnemy extends Enemy
{
	private static BufferedImage weaverImg;
	private double speed = 5;
	
	/**
	 * @param x
	 * @param y
	 * 2 param constructor
	 * 
	 * Supers the coordinates.
	 * Loads the image for WeaverEnemy.
	 */
	public WeaverEnemy(int x, int y)
	{
		super(x,y);
		try 
		{
			weaverImg = ImageIO.read(WeaverEnemy.class.getResource("/assets/weaver.png"));
		} catch (Exception e2) 
		{
		}
	}
	
	/**
	 * @param g
	 * @param e
	 * 
	 * Draws the image on to the screen.
	 */
	public static void createWeaver(Graphics g, Enemy e)
	{
		g.drawImage(weaverImg, (int)e.getX(), (int)e.getY(), null);
	}
	
	/* (non-Javadoc)
	 * @see sprites.Enemy#tick(main.Input)
	 * 
	 * Overrides the method from Enemy class.
	 * Calls the methods: moveToPlayer(speed) and avoidBullets().
	 */
	@Override
	public void tick(Input input, boolean pause)
	{
		if(!pause)
		{
			moveToPlayer(speed);
			avoidBullets();
		}
	}
}
