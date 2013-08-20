package sprites;

import main.Input;

/**
 * Class: Sprite.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Super class for all sprites.
 * 
 */
public abstract class Sprite
{
	private double x;
	private double y;
	private boolean removed;

	/**
	 * @param x
	 * @param y
	 * 2 param constructor
	 * 
	 * Assigns the passed values.
	 * Assigns false to the boolean, removed.
	 */
	public Sprite(double x, double y)
	{
		this.x = x;
		this.y = y;
		removed = false;
	}

	/**
	 * @return
	 * Getter that returns x.
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * @return
	 * Getter that returns y.
	 */
	public double getY()
	{
		return y;
	}

	/**
	 * @param x
	 * Setter than sets x.
	 */
	public void setX(double x)
	{
		this.x = x;
	}

	/**
	 * @param y
	 * Setter that sets y.
	 */
	public void setY(double y)
	{
		this.y = y;
	}

	/**
	 * @return
	 * Method that returns the value of the boolean, removed.
	 */
	public boolean isRemoved()
	{
		return removed;
	}

	/**
	 * Method that sets the value of the boolean, removed, to true.
	 */
	public void remove()
	{
		removed = true;
	}

	/*
	 * Each sprite will change its state in every "tick".
	 */
	public abstract void tick(Input input,boolean pause);
}
