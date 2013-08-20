package sprites;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Input;

/**
 * Class: Explosion.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Draws explosion graphics.
 * 
 */
public class Explosion extends Player
{
	private static BufferedImage explosionImg;

	/**
	 * @param x
	 * @param y
	 * 2 param constructor
	 * 
	 * Supers the coordinates.
	 * Loads the image for the explosion.
	 */
	public Explosion(int x, int y)
	{
		super(x, y);
		try
		{
			explosionImg = ImageIO.read(Explosion.class.getResource("/assets/explosion.gif"));
		} catch (IOException ex)
		{
		}
	}
	
	/**
	 * Draws the explosion.
	 * @param g
	 * @param explosion
	 */
	public static void createExplosion(Graphics g, Explosion explosion)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(explosionImg, (int) explosion.getX(), (int) explosion.getY(), null);
	}
	
	/**
	 * Returns a new explosion at the player's coordinates.
	 * @param p
	 * @return
	 */
	public static Explosion destroyPlayer(Player p)
	{
		return new Explosion((int)p.getX(), (int)p.getY());
	}

	/* (non-Javadoc)
	 * @see sprites.Sprite#tick(main.Input)
	 * 
	 * Movement for the player sprite using the Input class.
	 */
	@Override
	public void tick(Input input, boolean pause){}
}
