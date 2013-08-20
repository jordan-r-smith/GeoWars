package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import sprites.Background;
import sprites.BubbleText;
import sprites.Enemy;
import sprites.Explosion;
import sprites.GruntEnemy;
import sprites.Player;
import sprites.WeaverEnemy;

/**
 * Class: StartAnimation.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Runs the start animation code.
 */
public class StartAnimation extends Canvas implements KeyListener
{
	private static final long serialVersionUID = 1L;
	public static Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	public static final int GAME_WIDTH = d.width;
	public static final int GAME_HEIGHT = d.height;

	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<BubbleText> bubbles = new ArrayList<BubbleText>();
	private ArrayList<Explosion> explosions = new ArrayList<Explosion>();

	private Input input = new Input();
	private Background background = new Background(-1*(3000-GAME_WIDTH)/2, -1*(2000-GAME_HEIGHT)/2);
	private BufferedImage bgImage;
	private Thread theAnimation;
	private Graphics g;
	private Graphics g2;
	private Graphics g3;
	
	private boolean fullWidth;
	private boolean line1, line2, line3, line4;
	private boolean line1Check = true;
	private boolean line2Check = true;
	private boolean line3Check = true;
	private boolean line4Check = true;
	private boolean movePlayerUpCheck;
	private boolean moveEnemiesCheck;

	private javax.swing.Timer line1timer, line2timer, line3timer, line4timer;
	

	/**
	 * Generates a line of players the full width of the screen.
	 */
	public StartAnimation()
	{
		for (int i = 0; !fullWidth; i++)
		{
			if (i * 50 + 40 < GAME_WIDTH)
				players.add(new Player(i * 50, GAME_HEIGHT + 100));
			else
				fullWidth = true;
		}
	}

	/**
	 * Moves the players half way up the screen.
	 */
	public void movePlayer()
	{
		for (int i = 0; i < players.size(); i++)
		{
			Player.createPlayer(g, players.get(i));

			if (players.get(i).getY() > GAME_HEIGHT / 2)
				players.get(i).setY(players.get(i).getY() - 1);
			else
			{
				line1 = true;
			}
			if (enemies.get(0).getY() == players.get(i).getY())
			{
				players.remove(i);
			}
		}
	}

	/**
	 * Moves the middle player up.
	 */
	public void movePlayerUp()
	{
		if (movePlayerUpCheck)
		{
			Player.createPlayer(g,players.get(players.size() / 2));
			if (players.get(players.size() / 2).getY() > (double) GAME_HEIGHT / 4.0)
			{
				players.get(players.size() / 2).setY(players.get(players.size() / 2).getY() - 1);
			}
		}
	}

	/**
	 * Creates a series of timers that display bubble text images.
	 */
	public void scriptBubbles()
	{
		if (line4)
		{
			line4 = false;
			moveEnemiesCheck = true;
			bubbles.get(0).createText(g2);
			if (line4Check)
			{
				line4timer = new javax.swing.Timer(3000, new Line4TimerClass());
				line4timer.start();
				line4Check = false;
			}
		}

		if (line1)
		{
			bubbles.get(0).createText(g2);
			if (line1Check)
			{
				line1timer = new javax.swing.Timer(3000, new Line1TimerClass());
				line1timer.start();
				line1Check = false;
			}
		}

		if (line2)
		{
			bubbles.get(0).createText(g2);
			if (line2Check)
			{
				line2Check = false;
				line2timer = new javax.swing.Timer(3000, new Line2TimerClass());
				line2timer.start();
			}
		}

		if (line3)
		{
			bubbles.get(0).createText(g2);
			if (line3Check)
			{
				movePlayerUpCheck = true;
				line3Check = false;
				line3timer = new javax.swing.Timer(3000, new Line3TimerClass());
				line3timer.start();
				for (int i = 0; i < players.size() - 1; i++)
				{
					explosions.add(Explosion.destroyPlayer(players.get(i)));
				}
			}
		}
	}

	/**
	 * Creates two rows of enemies the full width of the screen.
	 */
	public void createEnemies()
	{
		fullWidth = false;
		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; !fullWidth; j++)
			{
				if (j * 50 + 40 < GAME_WIDTH)
				{
					if (j % 2 == 0)
					{
						enemies.add(new GruntEnemy(j * 50, GAME_HEIGHT - 50 * i + 100));
					} else
					{
						enemies.add(new WeaverEnemy(j * 50, GAME_HEIGHT - 50 * i + 100));
					}
				} else
					fullWidth = true;
			}
			fullWidth = false;
		}
	}

	/**
	 * Moves the enemies up to collide with the players.
	 * Draws explosion graphic upon collision.
	 * Starts the game after collision.
	 */
	public void moveEnemies()
	{
		if (moveEnemiesCheck)
		{
			for (int j = 0; j < enemies.size(); j++)
			{
				if (enemies.get(j).getY() > GAME_HEIGHT / 2)
				{
					enemies.get(j).setY(enemies.get(j).getY() - 1);
				} else
				{
					int mid = players.size() / 2;
					for (int k = 0; k < players.size(); k++)
					{
						if (k != mid)
						{
							Explosion.createExplosion(g, explosions.get(k));
						}
					}
				}

				if (enemies.get(j).getClass().equals(GruntEnemy.class))
				{
					GruntEnemy.createGrunt(g3, enemies.get(j));
				} else if (enemies.get(j).getClass().equals(WeaverEnemy.class))
				{
					WeaverEnemy.createWeaver(g3, enemies.get(j));
				}
			}
			if (enemies.get(0).getY() == GAME_HEIGHT / 2)
			{
				moveEnemiesCheck = false;
				GameRender.gr.showGame();
			}
		}
	}

	/**
	 * The start animation loop.
	 */
	private void render()
	{
		try
		{
			BufferStrategy bs = getBufferStrategy();
			if (bs == null)
			{
				createBufferStrategy(3);
				requestFocus();
				return;
			}

			g = bs.getDrawGraphics();
			g2 = bs.getDrawGraphics();
			g3 = bs.getDrawGraphics();

			bs.show();

			Background.createBackground(g, bgImage, background);

			Font f = new Font("SansSerif", Font.BOLD, 20);
			g.setFont(f);
			g.setColor(Color.WHITE);
			g.drawString("Credits:",30, 50);
			g.drawString("Coding by Jordan and Kyle",50, 100);
			g.drawString("Music by 8 Bit Weapon",50, 150);

			if (!enemies.equals(null))
			{
				for (int i = 0; i < enemies.size(); i++)
				{
					if (enemies.get(i).getClass().equals(GruntEnemy.class))
					{
						GruntEnemy.createGrunt(g3, enemies.get(i));

					} else if (enemies.get(i).getClass()
							.equals(WeaverEnemy.class))
					{
						WeaverEnemy.createWeaver(g3, enemies.get(i));
					}
				}
			}

			movePlayer();
			scriptBubbles();
			movePlayerUp();
			moveEnemies();

			g.dispose();
			g2.dispose();
			g3.dispose();

			try
			{
				Thread.sleep(2);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		} catch (Exception ex)
		{
		}
	}

	
	public void run()
	{
		
		try
		{
			bgImage = ImageIO.read(Game.class.getResource("/assets/bg.png"));
		} catch (IOException ex)
		{
		}
		
		bubbles.add(new BubbleText(GAME_WIDTH - 700, GAME_HEIGHT / 2 - 300,"line1.png"));
		bubbles.add(new BubbleText(GAME_WIDTH - 1000, GAME_HEIGHT / 2 - 300,"line2.png"));
		bubbles.add(new BubbleText(GAME_WIDTH / 2, GAME_HEIGHT / 2- GAME_HEIGHT / 4 - 225, "line3.png"));
		bubbles.add(new BubbleText(GAME_WIDTH / 2, GAME_HEIGHT / 2- GAME_HEIGHT / 4 - 225, "line4.png"));
		bubbles.add(new BubbleText());

		createEnemies();

		while (true)
		{
			render();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		input.set(e.getKeyCode(), true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
		input.set(e.getKeyCode(), false);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0)
	{

	}

	/**
	 * Timer for line 1 bubble. Stops showing line 1, starts showing line 2.
	 */
	class Line1TimerClass implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			line1 = false;
			bubbles.remove(0);
			line2 = true;
			line1timer.stop();
		}
	}

	/**
	 * Timer for line 2 bubble. Stops showing line 2, starts showing line 3.
	 */
	class Line2TimerClass implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			line2 = false;
			bubbles.remove(0);
			line3 = true;
			line2timer.stop();
		}
	}

	/**
	 * Timer for line 3 bubble. Stops showing line 3, starts showing line 4.
	 */
	class Line3TimerClass implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			line3 = false;
			bubbles.remove(0);
			line4 = true;
			line3timer.stop();
		}
	}

	/**
	 * Timer for line 4 bubble. Stops showing line 4.
	 */
	class Line4TimerClass implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			line4 = false;
			bubbles.remove(0);
			line4timer.stop();
		}
	}
}