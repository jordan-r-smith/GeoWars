package sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Game;
import main.Input;

/**
 * Class: PinwheelEnemy.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Creates the pinwheel enemy (purple) sprite and calculates the velocity.
 * 
 */
public class PinwheelEnemy extends Enemy
{

	private int path = r.nextInt(8);
	private int velocity = 2;
	private static BufferedImage pinwheelImg;

	/**
	 * @param x
	 * @param y
	 * 2 param constructor
	 * 
	 * Supers the coordinates.
	 * Loads the image for the PinwheelEnemy.
	 */
	public PinwheelEnemy(int x, int y)
	{
		super(x,y);
		try 
		{
			pinwheelImg = ImageIO.read(PinwheelEnemy.class.getResource("/assets/pinwheel.png"));

		} catch (Exception e2) 
		{	
		}
	}

	/**
	 * @param g
	 * @param e
	 * 
	 * Draws the sprite on to the screen.
	 */
	public static void createPinwheel(Graphics g, Enemy e)
	{
		g.drawImage(pinwheelImg, (int)e.getX(), (int)e.getY(), null);
	}

	/* (non-Javadoc)
	 * @see sprites.Enemy#tick(main.Input)
	 * 
	 * Creates random directions that the sprite will move along.
	 * Creates boundaries that the sprite will bounce off of.
	 */
	@Override
	public void tick(Input input, boolean pause)
	{
		if(!pause)
		{
			int tempX, tempY;
			if(path == 0) //SE
			{
				if(getX() >= Game.GAME_WIDTH-40)
				{
					path = 2;
				}
				else if(getY() >= Game.GAME_HEIGHT-40)
				{
					path = 1;
				}
				else
				{
					tempX = (int) (getX() + velocity);
					tempY = (int) (getY() + velocity);
					setX(tempX);
					setY(tempY);
				}
			}
			else if (path == 1) //NE
			{
				if(getX() >= Game.GAME_WIDTH-40)
				{
					path = 3;
				}
				else if(getY() <= 0)
				{
					path = 0;
				}
				else
				{
					tempX = (int) (getX() + velocity);
					tempY = (int) (getY() - velocity);
					setX(tempX);
					setY(tempY);
				}
			}
			else if (path == 2) //SW
			{
				if(getX() <= 0)
				{
					path = 0;
				}
				else if(getY() >= Game.GAME_HEIGHT-40)
				{
					path = 3;
				}
				else
				{
					tempX = (int) (getX() - velocity);
					tempY = (int) (getY() + velocity);
					setX(tempX);
					setY(tempY);
				}
			}
			else if (path == 3) //NW
			{
				if(getX() <= 0)
				{
					path = 0;
				}
				else if(getY() <= 0)
				{
					path = 2;
				}
				else
				{
					tempX = (int) (getX() - velocity);
					tempY = (int) (getY() - velocity);
					setX(tempX);
					setY(tempY);
				}
			}
			else if (path == 4) //N
			{
				if(getY() <= 0)
				{
					path = 6;
				}
				else
				{
					tempY = (int) (getY() - velocity);
					setY(tempY);
				}
			}
			else if (path == 5) //E
			{
				if( getX() >= Game.GAME_WIDTH-40)
				{
					path = 7;
				}
				else
				{
					tempX = (int) (getX() + velocity);
					setX(tempX);
				}
			}
			else if (path == 6) //S
			{
				if (getY() >= Game.GAME_HEIGHT-40)
				{
					path = 4;
				}
				else
				{
					tempY = (int) (getY() + velocity);
					setY(tempY);
				}
			}
			else if (path == 7) //W
			{
				if(getX() <= 0)
				{
					path = 5;
				}
				else
				{
					tempX = (int) (getX() - velocity);
					setX(tempX);
				}
			}
		}
	}
}