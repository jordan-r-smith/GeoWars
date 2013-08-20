package sprites;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Input;

/**
 * Class: BubbleText.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Draws the bubble text images.
 */
public class BubbleText extends Sprite
{
	private BufferedImage bubbleImg;
	private double x, y;
	
	/**
	 * 0 param constructor.
	 */
	public BubbleText()
	{
		super(0, 0);
	}
	
	/**
	 * 3 param constructor. Loads Images.
	 * @param x
	 * @param y
	 * @param img
	 */
	public BubbleText(double x, double y, String img) 
	{
		super(x, y);
	    this.x = x;
	    this.y = y;
	    try
	    {
			bubbleImg = ImageIO.read(Bullet.class.getResource("/assets/" + img));
		} catch (IOException ex)
		{
		}
	}
	
	/**
	 * Draws the bubble on the screen.
	 * @param g
	 * @param a
	 */
	public void createText(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bubbleImg, (int) x, (int) y, null);
	}

	/* (non-Javadoc)
	 * @see sprites.Sprite#tick(main.Input, boolean)
	 */
	@Override
	public void tick(Input input, boolean pause) {}
}