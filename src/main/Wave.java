package main;

import java.util.ArrayList;
import java.util.Random;

import sprites.Enemy;
import sprites.GruntEnemy;
import sprites.PinwheelEnemy;
import sprites.WeaverEnemy;

/**
 * Class: Waves.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Generates waves of enemies.
 */
public class Wave
{
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private Random r = new Random();
	private int spawnWidth = Game.GAME_WIDTH/4;
	private int spawnHeight = Game.GAME_HEIGHT/3;

	/**
	 * @param n
	 * @param xy
	 * Two param constructor
	 * 
	 * n is the wave number
	 * Different waves spawn a different set of enemies
	 * xy is the players coordinates to prevent enemies from spawning too close to the player
	 */
	public Wave(int n, double[] xy)
	{
		if (n == 1)
		{
			for (int i = 0; i < 10; i++)
			{
				int x = r.nextInt(Game.GAME_WIDTH - 100);
				int y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new PinwheelEnemy(x, y));
			}
		}
		else if (n == 2)
		{
			for (int i = 0; i < 10; i++)
			{
				int x = r.nextInt(Game.GAME_WIDTH - 100);
				int y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new GruntEnemy(x, y));
				x = r.nextInt(Game.GAME_WIDTH - 100);
				y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}

				enemies.add(new PinwheelEnemy(x, y));
			}
		}

		else if (n == 3)
		{
			for (int i = 0; i < 10; i++)
			{
				int x = r.nextInt(Game.GAME_WIDTH - 100);
				int y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new GruntEnemy(x, y));

				x = r.nextInt(Game.GAME_WIDTH - 100);
				y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new WeaverEnemy(x, y));

				x = r.nextInt(Game.GAME_WIDTH - 100);
				y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new PinwheelEnemy(x, y));
			}
		}

		else if (n == 4)
		{
			for (int i = 0; i < 15; i++)
			{
				int x = r.nextInt(Game.GAME_WIDTH - 100);
				int y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new GruntEnemy(x, y));

				x = r.nextInt(Game.GAME_WIDTH - 100);
				y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new WeaverEnemy(x, y));

			}
		}

		else if (n == 5)
		{
			for (int i = 0; i < 20; i++)
			{
				int x = r.nextInt(Game.GAME_WIDTH - 100);
				int y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new GruntEnemy(x, y));

				x = r.nextInt(Game.GAME_WIDTH - 100);
				y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new WeaverEnemy(x, y));

			}
		}

		else if (n == 6)
		{
			for (int i = 0; i < 25; i++)
			{
				int x = r.nextInt(Game.GAME_WIDTH - 100);
				int y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new GruntEnemy(x, y));

				x = r.nextInt(Game.GAME_WIDTH - 100);
				y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new WeaverEnemy(x, y));
			}
		}

		else if (n == 7)
		{
			for (int i = 0; i < 30; i++)
			{
				int x = r.nextInt(Game.GAME_WIDTH - 100);
				int y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new GruntEnemy(x, y));

				x = r.nextInt(Game.GAME_WIDTH - 100);
				y = r.nextInt(Game.GAME_HEIGHT - 100);
				while (Math.abs(xy[0] - x) < spawnWidth && Math.abs(xy[1] - y) < spawnHeight)
				{
					x = r.nextInt(Game.GAME_WIDTH - 100);
					y = r.nextInt(Game.GAME_HEIGHT - 100);
				}
				enemies.add(new WeaverEnemy(x, y));
			}
		}
	}

	/**
	 * Returns the ArrayList of enemies for the wave.
	 * @return
	 */
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}

	/**
	 * Removes an enemy from the ArrayList at the given index of j.
	 * @param j
	 */
	public void removeEnemy(int j)
	{
		enemies.remove(j);
	}			
}