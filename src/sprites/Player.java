package sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;
import main.Input;

/**
 * Class: Player.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Creates the player sprite.  Assigns input to the player.
 * 
 */
public class Player extends Sprite
{
	private int velocity = 4;
	private static BufferedImage playerImg;
	private static boolean pause;

	/**
	 * @param x
	 * @param y
	 * 2 param constructor
	 * 
	 * Supers the coordinates.
	 * Loads the image for the Player.
	 */
	public Player(int x, int y)
	{
		super(x, y);
		try
		{
			playerImg = ImageIO.read(Player.class.getResource("/assets/player.png"));
		} catch (IOException ex)
		{
		}
	}
	
	/**
	 * @return
	 * Getter for the width of the player sprite image.
	 */
	public static int getPlayerImgWidth()
	{
		return (playerImg.getWidth());
	}
	
	/**
	 * @return
	 * Getter for the height of the player sprite image.
	 */
	public static int getPlayerImgHeight()
	{
		return (playerImg.getHeight());
	}
	
	/**
	 * @param g
	 * @param theta
	 * @param player
	 * @param vincible
	 * 
	 * Draws the player on to the screen.
	 * Rotates the player based on the passed value of theta.
	 * If the player has just died, set up a white circle surrounding
	 * the player sprite. This is a visual shield.
	 */
	public static void createPlayer(Graphics g, Double theta, Player player, boolean vincible)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(Game.rotate(playerImg, Math.toDegrees(theta - 29.8), pause), (int) player.getX(), (int)player.getY(), null);
		if (!vincible)
		{
			g2d.setColor(Color.WHITE);
			g2d.drawOval((int) player.getX()-5, (int) player.getY() - 5, 50, 50);
		}
	}
	
	/**
	 * Draws the player on to the screen.
	 * @param g
	 * @param player
	 */
	public static void createPlayer(Graphics g, Player player)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(playerImg, (int) player.getX(), (int) player.getY(), null);
	}

	/* (non-Javadoc)
	 * @see sprites.Sprite#tick(main.Input)
	 * 
	 * Movement for the player sprite using the Input class.
	 */
	@Override
	public void tick(Input input, boolean pause)
	{
		Player.pause = pause;
		if(!pause)
		{
		if (input.buttons[Input.LEFT] && getX() > 0)
			setX(getX() - velocity);
		if (input.buttons[Input.RIGHT] && getX() < Game.GAME_WIDTH - 40)
			setX(getX() + velocity);
		if (input.buttons[Input.UP] && getY() > 0)
			setY(getY() - velocity);
		if (input.buttons[Input.DOWN] && getY() < Game.GAME_HEIGHT - 40)
			setY(getY() + velocity);
		}
	}
}
