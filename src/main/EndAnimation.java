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
import java.util.Random;

import javax.imageio.ImageIO;

import sprites.Background;
import sprites.Enemy;
import sprites.Explosion;
import sprites.GruntEnemy;
import sprites.Player;
import sprites.WeaverEnemy;

/**
 * Class: EndAnimation.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Runs the win/lose animations
 */
public class EndAnimation extends Canvas implements KeyListener
{
	private static final long serialVersionUID = 1L;
	public static Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	public static final int GAME_WIDTH = d.width;
	public static final int GAME_HEIGHT = d.height;
	private Player player;

	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	private Explosion explode;
	private Background background = new Background(-1*(3000-GAME_WIDTH)/2, -1*(2000-GAME_HEIGHT)/2);
	private Input input = new Input();
	private BufferedImage bgImage;
	private Thread theAnimation;
	private Graphics g;
	private Graphics g2;
	private Graphics g3;

	private boolean canExplode = true;
	private boolean explosionCheck = true;
	private boolean endCheck;
	private boolean gameWon,gameLost;

	private int score;

	private javax.swing.Timer explosionTimer, gameMenuTimer;

	/**
	 * 3 param constructor
	 * @param score
	 * @param gameWon
	 * @param gameLost
	 */
	public EndAnimation(int score, boolean gameWon, boolean gameLost)
	{
		this.score = score;
		this.gameWon = gameWon;
		this.gameLost = gameLost;
		player  = new Player(GAME_WIDTH/2, GAME_HEIGHT/2);
		addKeyListener(this);
	}

	/**
	 * Draws a player while it is still alive
	 */
	public void processPlayer()
	{
		if(canExplode)
		{
			Player.createPlayer(g, player);
		}
	}

	/**
	 * returns the angle between the enemy and the player
	 * @param e
	 * @return
	 */
	public double getEnemyAngle(Enemy e)
	{	
		double enemyX = e.getX();
		double enemyY = e.getY();
		double newX = (player.getX() + (Player.getPlayerImgWidth() / 2)) - enemyX;
		double newY = (player.getY() + (Player.getPlayerImgHeight() / 2)) - enemyY;
		double eTheta = Math.atan2(newY, newX);
		return eTheta;
	}

	/**
	 * Draws and moves the enemies towards the player
	 */
	public void processEnemies()
	{
		for (int i = 0; i < enemies.size(); i++)
		{
			enemies.get(i).setTheta(getEnemyAngle(enemies.get(i)));
			enemies.get(i).moveToPlayer(3);
			if(Math.abs(enemies.get(i).getX() - GAME_WIDTH/2) < 10 && Math.abs(enemies.get(i).getY() - GAME_HEIGHT/2) < 10 && canExplode)
			{
				canExplode = false;
				explosionTimer = new javax.swing.Timer(1000, new ExplosionTimerClass());
				explosionTimer.start();
				explode = new Explosion(GAME_WIDTH/2-10, GAME_HEIGHT/2-15);
			}
			GruntEnemy.createGrunt(g, enemies.get(i));
		}
		try
		{
			Thread.sleep(1);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Draws and moves the enemies away from the player
	 */
	public void processEnemiesWin()
	{
		for (int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i).getX() > GAME_WIDTH/2)
				enemies.get(i).setX(enemies.get(i).getX()+1);
			else
				enemies.get(i).setX(enemies.get(i).getX()-1);
			if(enemies.get(i).getY() > GAME_HEIGHT/2)
				enemies.get(i).setY(enemies.get(i).getY()+1);
			else
				enemies.get(i).setY(enemies.get(i).getY()-1);
			if(enemies.get(i).getClass().equals(GruntEnemy.class))
				GruntEnemy.createGrunt(g, enemies.get(i));
			else
				WeaverEnemy.createWeaver(g, enemies.get(i));
		}
		try
		{
			Thread.sleep(1);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Draws an explosion
	 */
	public void processExplosions()
	{
		if(!explode.equals(null) && explosionCheck)
		{
			Explosion.createExplosion(g, explode);
		}
	}

	/**
	 * Creates and adds enemies to an ArrayList for the losing animation
	 */
	public void createEnemies()
	{
		int widthSpacing = GAME_WIDTH / 10; 
		int heightSpacing = GAME_HEIGHT / 10; 
		int counter = 0;
		for (int i = 0; i < 40; i++)
		{
			counter++;
			if(i % 4 == 0)
				enemies.add(new GruntEnemy(counter / 4 * widthSpacing, 0));
			else if(i % 4 == 1)
				enemies.add(new GruntEnemy(GAME_WIDTH - 40, counter / 4 * heightSpacing));
			else if(i % 4 == 2)
				enemies.add(new GruntEnemy(counter / 4 * widthSpacing + 50, GAME_HEIGHT - 70));
			else if(i % 4 == 3)
				enemies.add(new GruntEnemy(0, GAME_HEIGHT - counter / 4 * heightSpacing));
		}
	}

	/**
	 * Creates and adds enemies to an ArrayList for the winning animation
	 */
	public void createEnemiesWin()
	{
		int spawnWidth = 100;
		int spawnHeight = 100;
		Random r = new Random();
		double[] xy = new double[]{GAME_WIDTH/2,GAME_HEIGHT/2};
		for (int i = 0; i < 50; i++)
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

	/**
	 * The losing animation game loop
	 */
	private void loseRender()
	{
		if(input.buttons[Input.ESC])
		{
			input.buttons[Input.ESC] = false;
			GameRender.gr.pause(score);
		}
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

			if (endCheck)
			{
				f = new Font("SansSerif", Font.BOLD, 50);
				g2.setColor(Color.RED);
				g2.setFont(f);
				g2.drawString("YOU LOSE, NOOB!", (GAME_WIDTH / 2) - 250, GAME_HEIGHT / 2);
				g2.drawString("Your Score: " + score, GAME_WIDTH/2 - 250, GAME_HEIGHT/2 + 100);
			}

			processPlayer();
			processEnemies();
			processExplosions();

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

	/**
	 * The winning animation game loop
	 */
	private void winRender()
	{
		if(input.buttons[Input.ESC])
		{
			input.buttons[Input.ESC] = false;
			GameRender.gr.pause(score);
		}
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

			processPlayer();
			processEnemiesWin();

			f = new Font("SansSerif", Font.BOLD, 50);
			g2.setColor(Color.RED);
			g2.setFont(f);
			g2.drawString("YOU WIN! THE SHAPES ARE RETREATING!", (GAME_WIDTH / 2) - 250, GAME_HEIGHT / 2);
			g2.drawString("Your Score: " + score, GAME_WIDTH/2 - 250, GAME_HEIGHT/2 + 100);

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
		if(gameLost)
		{
			createEnemies();
		}
		else
		{
			createEnemiesWin();
		}
		while (gameLost)
		{
			loseRender();
		}
		while (gameWon)
		{
			winRender();
		}
	}


	/**
	 * Timer to turn off the explosion.
	 */
	class ExplosionTimerClass implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			explosionCheck = false;
			endCheck = true;
			explosionTimer.stop();
		}
	}
	
	class MenuTimerClasee implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			GameRender.gr.showMenu();
			explosionTimer.stop();
		}
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

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		input.set(e.getKeyCode(), true);
	}
}
