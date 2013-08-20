package sprites;

import java.util.ArrayList;
import java.util.Random;

import main.Game;
import main.GameRender;
import main.Input;

/**
 * Class: Enemy.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Superclass for the enemy sprites.  Creates movement functions.
 * 
 */

public class Enemy extends Sprite
{
	protected Random r = new Random();
	private int velocity = 2;
	private int jump;
	public double x2, y2;
	public double xV;
	public double yV;
	public double theta;

	/**
	 * @param x
	 * @param y
	 * Two param constructor
	 * 
	 * Supers the coordinates.
	 */
	public Enemy(double x, double y)
	{
		super(x, y);
	}
	
	/**
	 * Set theta to the passed value.
	 * @param theta
	 */
	public void setTheta(double theta)
	{
		this.theta = theta;
	}

	/**
	 * Moves the enemy to player at the passed speed value.
	 * @param speed
	 */
	public void moveToPlayer(double speed)
	{
		yV = Math.sin(theta) * speed;
	    xV = Math.cos(theta) * speed;
		setY(getY() + yV);
		setX(getX() + xV);
	}
	
	/**
	 * Moves the Enemy to the player.
	 */
	public void moveToPlayer1()
	{
		int tempX = 0;
		int tempY = 0;
		double[] xy = GameRender.game.getPlayerLocation();
		if (getX() < xy[0])
		{
			tempX = (int)getX() + velocity;
			setX(tempX);
		}
		else
		{
			tempX = (int)getX() - velocity;
			setX(tempX);
		}
		if (getY() < xy[1])
		{
			tempY = (int)getY() + velocity;
			setY(tempY);
		}
		else
		{
			tempY = (int)getY() - velocity;
			setY(tempY);
		}
	}

	/**
	 * Lets the enemy dodge bullets as they are coming towards it.
	 */
	public void avoidBullets()
	{
		ArrayList<Bullet> bullets = GameRender.game.getBulletArray();
		double[] xy = GameRender.game.getPlayerLocation();
		for (int i = 0; i < bullets.size(); i++)
		{
			if(r.nextInt(3) == 0)
				jump = 5;
			else if (r.nextInt(3) == 1)
				jump = 10;
			else if (r.nextInt(3) == 2)
				jump = 15;
			
			Bullet a = bullets.get(i);
			if (!a.isRemoved())
			{
				int tempX = 0;
				int tempY = 0;

				if ((Math.abs(getX() - xy[0]) > 75 || Math.abs(getY() - xy[1]) > 75) && 
						Math.abs(getX() - a.getX()) < 100 && Math.abs(getY() - a.getY()) < 100)
				{
					if(getX() < a.getX()  && getX() > 4)
					{
						tempX = (int)getX() - jump;
						setX(tempX);
					}
					else if (getX() < Game.GAME_WIDTH-40)
					{
						tempX = (int)getX() + jump;
						setX(tempX);
					}
					if(getY() < a.getY() && getY() > 4)
					{
						tempY = (int)getY() - jump;
						setY(tempY);
					}
					else if(getY() < Game.GAME_HEIGHT-40)
					{
						tempY = (int)getY() + jump;
						setY(tempY);
					}
				}

			}
		}
	}

	/* (non-Javadoc)
	 * @see sprites.Sprite#tick(main.Input)
	 */
	@Override
	public void tick(Input input, boolean pause) {}
}