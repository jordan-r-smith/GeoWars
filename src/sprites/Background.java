package sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import main.GameRender;
import main.Input;

/**
 * Class: Background.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Draws and applies input to the background.
 */
public class Background extends Sprite
{
	private int velocity = 1;

	/**
	 * 2 param constructor.
	 * @param x
	 * @param y
	 */
	public Background(double x, double y)	
	{
		super(x,y);
	}

	/**
	 * Draws the background image.
	 * @param g
	 * @param bgImage
	 * @param bg
	 */
	public static void createBackground(Graphics g, BufferedImage bgImage, Background bg)
	{
		g.drawImage(bgImage, (int)bg.getX(), (int)bg.getY(), null);
	}

	/* (non-Javadoc)
	 * @see sprites.Sprite#tick(main.Input, boolean)
	 */
	@Override
	public void tick(Input input, boolean pause)
	{
		if (!pause)
		{
			double[] xy = GameRender.game.getPlayerLocation();

			if (input.buttons[Input.LEFT] && xy[0] > 0)
				setX(getX() + velocity);
			if (input.buttons[Input.RIGHT] && xy[0] < Game.GAME_WIDTH - 70)
				setX(getX() - velocity);
			if (input.buttons[Input.UP] && xy[1] > 0)
				setY(getY() + velocity);
			if (input.buttons[Input.DOWN] && xy[1] < Game.GAME_HEIGHT - 100)
				setY(getY() - velocity);
		}
	}
}
