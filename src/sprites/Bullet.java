package sprites;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Input;

/**
 * Class: Bullet.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Creates and moves bullets.
 */

public class Bullet extends Sprite 
{
	public double x2, y2;
	public double xV;
	public double yV;
	public double theta;
	public double speed;
	private static BufferedImage bulletImg;
	
	/**
	 * @param x
	 * @param y
	 * @param theta
	 * @param speed
	 * Four param constructor
	 * 
	 * Sets the coordinates of the bullet, the angle, and the velocity.
	 * Loads the image for the bullet.
	 */
	public Bullet(double x, double y, double theta, double speed) 
	{
		super(x, y);
		this.theta = theta;
		this.speed = speed;
		x2 = x;
		y2 = y;
		yV = Math.sin(theta) * speed;
	    xV = Math.cos(theta) * speed;
	    
	    try
		{
			bulletImg = ImageIO.read(Bullet.class.getResource("/assets/bullet.gif"));
		} catch (IOException ex)
		{
		}
	}
	
	/**
	 * @param g
	 * @param a
	 * 
	 * Draws the bullet on the screen.
	 */
	public static void createBullet(Graphics g, Bullet a)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bulletImg, (int)a.getX(), (int)a.getY(), null);
	}
	
	/* (non-Javadoc)
	 * @see sprites.Sprite#tick(main.Input)
	 * 
	 * Updates the bullet's coordinates every tick.
	 */
	public void tick(Input input, boolean pause) 
	{
		if(!pause)
		{
			setY(y2);
			setX(x2);
			x2 = getX() + this.xV;
			y2 = getY() + this.yV;
		}
	}
}
