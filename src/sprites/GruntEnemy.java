package sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Input;

/**
 * Class: GruntEnemy.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Creates the grunt enemy (blue) sprite.
 * 
 */
public class GruntEnemy extends Enemy
{
	private static BufferedImage gruntImg;
	public static double speed = 4;
	
	/**
	 * @param x
	 * @param y
	 * Two param constructor
	 * 
	 * Sets the coordinates of the GruntEnemy.
	 * Loads the image for the GruntEnemy.
	 */
	public GruntEnemy(int x, int y)
	{
		super(x, y);
		try
		{
			gruntImg = ImageIO.read(GruntEnemy.class.getResource("/assets/grunt.png"));
		} catch (Exception e2)
		{
		}
	}

	/**
	 * @param g
	 * @param e
	 * Draws the enemy on to the screen.
	 */
	public static void createGrunt(Graphics g, Enemy e)
	{
		g.drawImage(gruntImg, (int) e.getX(), (int) e.getY(), null);
	}

	/* (non-Javadoc)
	 * @see sprites.Enemy#tick(main.Input)
	 * 
	 * Calls the moveToPlayer() method every tick.
	 */
	@Override
	public void tick(Input input, boolean pause)
	{
		if(!pause)
		{
			moveToPlayer(speed);
		}
	}
}
