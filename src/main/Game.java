package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import sprites.Background;
import sprites.Bullet;
import sprites.Enemy;
import sprites.GruntEnemy;
import sprites.PinwheelEnemy;
import sprites.Player;
import sprites.WeaverEnemy;

/**
 * Class: Game.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Runs the game code
 */
public class Game extends Canvas implements Runnable, KeyListener
{
	private static final long serialVersionUID = 1L;
	public static Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	public static final int GAME_WIDTH = d.width;
	public static final int GAME_HEIGHT = d.height;
	private int score = 0;
	private int lives = 3;

	protected Input input = new Input();
	private Thread theGame;
	private Player player;
	private Background background = new Background(-1*(3000-GAME_WIDTH)/2, -1*(2000-GAME_HEIGHT)/2);
	private BufferedImage bgImage;
	private Wave wave1, wave2, wave3, wave4, wave5, wave6, wave7;
	
	private BufferStrategy bs;
	private Graphics g;
	private Graphics g1;
	private Graphics g2;

	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	private javax.swing.Timer enemySpawn = new javax.swing.Timer(3000, new SpawnTimer());
	private javax.swing.Timer bulletSpawn = new javax.swing.Timer(200, new BulletTimer());
	private javax.swing.Timer enemyRespawn,vincibleTimer,newWave;

	protected boolean gameOver = false;
	private boolean vincible,waveSpawn,pause,fpsCheck;
	private boolean firstMusic = true;
	private boolean readyForSpawn = true;
	private boolean loseCheck = true;
	private boolean winCheck = true;
	private boolean gameCheck = true;
	private boolean startAnimationCheck = true;
	public boolean gameLost,gameWon;
	private boolean firstShotCheck = true;

	private long now;
	private int framesCount = 0;
	private int framesCountAvg = 0;
	private long framesTimer = 0;

	private long vincibleTimerStartTime,pauseTime;
	private String tempWave = "1";
	private Clip music,bulletSound;
	private double theta;

	/**
	 * 0 param constructor
	 * Generates a new Player and adds a keylistener
	 */
	public Game()
	{
		player = new Player(GAME_WIDTH / 2, GAME_HEIGHT / 2);
		addKeyListener(this);
	}

	/**
	 * Generates the next wave when the current one is completed
	 */
	public void generateWaves()
	{
		if(!pause)
		{
			if(enemies.isEmpty()  && (getCurrentWave() == null || getCurrentWave().getEnemies().isEmpty()))
			{
				if(wave1 == null)
				{
					wave1 = new Wave(1, getPlayerLocation());
				}
				else if(wave2 == null)
				{
					wave2 = new Wave(2, getPlayerLocation());
					waveSpawn = false;
				}
				else if(wave3 == null)
				{
					wave3 = new Wave(3, getPlayerLocation());
					waveSpawn = false;
				}
				else if(wave4 == null)
				{
					wave4 = new Wave(4, getPlayerLocation());
					waveSpawn = false;
				}
				else if(wave5 == null)
				{
					wave5 = new Wave(5, getPlayerLocation());
					waveSpawn = false;
				}
				else if(wave6 == null)
				{
					wave6 = new Wave(6, getPlayerLocation());
					waveSpawn = false;
				}
				else if(wave7 == null)
				{
					wave7 = new Wave(7, getPlayerLocation());
					waveSpawn = false;
				}
			}
		}
	}

	/**
	 * @param update
	 * Updates the score
	 */
	public void updateScore(int update)
	{
		score += update;
	}

	/**
	 * Decrements the lives, calls killAll method, and makes the player invincible for two seconds
	 */
	public void updateLives()
	{
		lives--;
		killAll();
		vincible = false;
		vincibleTimer = new javax.swing.Timer(4000, new VincibleTimer());
		vincibleTimer.start();
	}
	
	/**
	 * Returns if game is over.
	 * @return
	 */
	public boolean getGameStatus()
	{
		return gameOver;
	}

	/**
	 * @return Current level
	 */
	public String getLevel()
	{
		if(wave1 != null && !wave1.getEnemies().isEmpty())
		{
			tempWave = "1";
		}
		else if(wave2 != null && !wave2.getEnemies().isEmpty())
		{
			tempWave = "2";
		}
		else if(wave3 != null && !wave3.getEnemies().isEmpty())
		{
			tempWave = "3";
		}
		else if(wave4 != null && !wave4.getEnemies().isEmpty())
		{
			tempWave = "4";	
		}
		else if(wave5 != null && !wave5.getEnemies().isEmpty())
		{
			tempWave = "5";
		}
		else if(wave6 != null && !wave6.getEnemies().isEmpty())
		{
			tempWave = "6";	
		}
		else if(wave7 != null && !wave7.getEnemies().isEmpty())
		{
			tempWave = "7";
		}

		return tempWave;	
	}

	/**
	 *	Removes all the enemies and has them respawn in two seconds 
	 */
	public void killAll()
	{
		enemies = new ArrayList<Enemy>();
		enemyRespawn = new javax.swing.Timer(3000, new RespawnTimer());
		enemyRespawn.start();
	}

	/**
	 * @return
	 * Calculates the angle of the mouse in relation to the player
	 */
	public double getMouseAngle()
	{	
		if(!pause)
		{
			Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
			Point p = GameRender.getJFrameLocation();
			double windowX = p.getX();
			double windowY = p.getY();
			double mouseX = mouseLocation.getX();
			double mouseY = mouseLocation.getY();
			double newX = mouseX - (player.getX() + windowX + (Player.getPlayerImgWidth() / 2));
			double newY = mouseY - (player.getY() + windowY + (Player.getPlayerImgHeight() / 2));
			theta = Math.atan2(newY, newX);
		}
		return theta;
	}

	/**
	 * Return the angle between the enemy and the player
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
	 * @return
	 * Packages the players current location in an Array
	 */
	public double[] getPlayerLocation()
	{
		double[] xy = new double[2];
		xy[0] = player.getX();
		xy[1] = player.getY();
		return xy;
	}

	/**
	 * Stops the main render while loop.
	 */
	public void stopRender()
	{
		gameCheck = false;
	}

	/**
	 * @param str
	 * Plays a given .wav file
	 */
	protected void playSound(String str)
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Game.class.getResource(("/assets/" + str)));
			Clip clip;

			if (str.equals("bgmusic.wav"))
			{
				music = AudioSystem.getClip();
				music.open(audioInputStream);

				FloatControl volume = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
				//volume.setValue(-20);

				music.start();
				music.loop(Integer.MAX_VALUE);
			}
			else if (str.equals("laser.wav"))
			{
				bulletSound = AudioSystem.getClip();
				bulletSound.open(audioInputStream);

				FloatControl volume = (FloatControl) bulletSound.getControl(FloatControl.Type.MASTER_GAIN);
				volume.setValue(-20);
				bulletSound.start();
				bulletSound.loop(Integer.MAX_VALUE);
			}
			else
			{
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);

				FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volume.setValue(-20);

				clip.start();
			}
		} catch (Exception ex)
		{
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

	/**
	 * @return
	 * Returns the ArrayList of bullets
	 */
	public ArrayList<Bullet> getBulletArray()
	{
		return bullets;
	}

	/**
	 *	If a wave is spawnable and the current wave is not null, spawns the enemies on the screen 
	 */
	private void spawnEnemy()
	{
		if(waveSpawn && getCurrentWave() != null)
			enemies = getCurrentWave().getEnemies();
	}

	/**
	 * @param g
	 * Moves the enemies across the screen and checks for collisions with the player (updates lives)
	 */
	private void processEnemy(Graphics g)
	{
		for (int i = 0; i < enemies.size(); i++)
		{
			if (!enemies.get(i).isRemoved())
			{
				if (enemies.get(i).getClass().equals(PinwheelEnemy.class))
				{
					PinwheelEnemy.createPinwheel(g, enemies.get(i));
				}
				if (enemies.get(i).getClass().equals(GruntEnemy.class))
				{
					enemies.get(i).setTheta(getEnemyAngle(enemies.get(i)));
					GruntEnemy.createGrunt(g, enemies.get(i));
				}
				if (enemies.get(i).getClass().equals(WeaverEnemy.class))
				{
					enemies.get(i).setTheta(getEnemyAngle(enemies.get(i)));
					WeaverEnemy.createWeaver(g, enemies.get(i));
				}

				enemies.get(i).tick(input,pause);

				if ((Math.abs(enemies.get(i).getX() - player.getX()) < 30
						&& Math.abs(enemies.get(i).getY() - player.getY()) < 30) && vincible)
				{
					updateLives();
					player.setX(GAME_WIDTH / 2);
					player.setY(GAME_HEIGHT / 2);
				}
			}
		}
	}

	/**
	 * @return
	 * Returns the current Wave
	 */
	public Wave getCurrentWave()
	{
		if(wave1 != null && !wave1.getEnemies().isEmpty())
		{
			return wave1;
		}
		else if(wave2 != null && !wave2.getEnemies().isEmpty())
		{
			return wave2;	
		}
		else if(wave3 != null && !wave3.getEnemies().isEmpty())
		{
			return wave3;
		}
		else if(wave4 != null && !wave4.getEnemies().isEmpty())
		{
			return wave4;	
		}
		else if(wave5 != null && !wave5.getEnemies().isEmpty())
		{
			return wave5;
		}
		else if(wave6 != null && !wave6.getEnemies().isEmpty())
		{
			return wave6;	
		}
		else if(wave7 != null && !wave7.getEnemies().isEmpty())
		{
			return wave7;
		}
		else
		{
			return null;	
		}
	}

	/**
	 * @param source
	 * @param degrees
	 * @return
	 * Rotates the player to face the mouse
	 * Source: http://www.java-gaming.org/index.php?PHPSESSID=rvpjtfqrrpnugs7u2acu9hncd4&topic=4916.msg44960#msg44960
	 */
	public static Image rotate(Image source, double degrees,boolean pause)
	{
		int width = source.getWidth(null);
		int height = source.getHeight(null);

		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		Image img = gc.createCompatibleImage(width, height,
				Transparency.BITMASK);
		Graphics2D g = (Graphics2D) img.getGraphics();

		g.translate(width / 2, height / 2);
		g.rotate(Math.toRadians(degrees));
		g.drawImage(source, -width / 2, -height / 2, width, height, null);
		return img;
	}

	/**
	 * @param g
	 * Moves the player
	 */
	private void processPlayer(Graphics g)
	{
		Player.createPlayer(g, getMouseAngle(), player, vincible);
		player.tick(input,pause);
	}

	/**
	 * @param g
	 * Moves the bullets and checks for collisions with enemies
	 * Removes bullets when they leave the screen
	 */
	private void processBullets(Graphics g)
	{
		for (int i = 0; i < bullets.size(); i++)
		{
			{
				Bullet a = bullets.get(i);
				if (!a.isRemoved())
				{
					a.tick(input,pause);
					Bullet.createBullet(g, a);
					for (int j = 0; j < enemies.size(); j++)
					{
						if (Math.abs(enemies.get(j).getX() + 20 - a.getX() + 5) < 30
								&& Math.abs(enemies.get(j).getY() + 20 - a.getY() + 5) < 30)
						{
							playSound("pop.wav");
							enemies = getCurrentWave().getEnemies();
							
							if(enemies.get(j).getClass().equals(PinwheelEnemy.class))
								updateScore(25);
							else if(enemies.get(j).getClass().equals(GruntEnemy.class))
								updateScore(50);
							else
								updateScore(100);
							
							a.remove();
							getCurrentWave().removeEnemy(j);
						}
						if (a.getX() < 0 || a.getX() > Game.GAME_WIDTH || a.getY() < 0 || a.getY() > Game.GAME_HEIGHT)
						{
							a.remove();
						}
					}
				} else
				{
					bullets.remove(a);
				}
			}
		}
	}

	/**
	 * Draws and moves background.
	 * @param g
	 */
	private void processBackground(Graphics g)
	{
		Background.createBackground(g, bgImage, background);
		background.tick(input,pause);
	}

	/**
	 * Pauses the game.
	 */
	public void pauseGame()
	{
		pause = true;
		music.stop();
		bulletSpawn.stop();
		bulletSound.stop();
		vincibleTimer.stop();
		GameRender.gr.pause(score);
	}

	/**
	 * Resumes the game.
	 */
	public void resumeGame()
	{
		pause = false;
		firstShotCheck = true;
		music.start();
		bulletSpawn.start();
		vincibleTimer = new javax.swing.Timer((int) (4000 - (pauseTime - vincibleTimerStartTime)), new VincibleTimer());
		vincibleTimer.start();
	}

	/**
	 * Toggles FPS
	 * @param g
	 */
	public void showFPS(Graphics g)
	{
		if(fpsCheck)
		{
			now = System.currentTimeMillis();
			framesCount++;
			if (now - framesTimer > 1000)
			{
				framesTimer = now;
				framesCountAvg = framesCount;
				framesCount = 0;
			}
			g.drawString(framesCountAvg + " FPS", GAME_WIDTH - 100, GAME_HEIGHT - 60);
		}
	}
	
	/**
	 * NON-WORKING CODE!!!
	 * Keeping this around for future.
	 */
	public void restartGame()
	{
		bs = null;
		g = null;
		g1 = null;
		g2 = null;
		
		score = 0;
		lives = 3;
		//input = new Input();
		player = new Player(GAME_WIDTH / 2, GAME_HEIGHT / 2);
		bgImage = null;
		background = new Background(-1*(3000-GAME_WIDTH)/2, -1*(2000-GAME_HEIGHT)/2);
		
		wave1 = null;
		wave2 = null;
		wave3 = null;
		wave4 = null;
		wave5 = null;
		wave6 = null;
		wave7 = null;
		
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();

		//enemySpawn = new javax.swing.Timer(3000, new SpawnTimer());
		//bulletSpawn = new javax.swing.Timer(200, new BulletTimer());

		gameOver = false;
		vincible = false;
		waveSpawn = false;
		pause = false;
		fpsCheck = false;
		gameLost = false;
		gameWon = false;
		
		firstMusic = true;
		readyForSpawn = true;
		loseCheck = true;
		winCheck = true;
		gameCheck = true;
		//startAnimationCheck = true;
		
		
		
		framesCount = 0;
		framesCountAvg = 0;
		framesTimer = 0;

		tempWave = "1";
		run();
	}

	/**
	 *	The main game loop 
	 */
	public void render()
	{
		//System.out.println(Arrays.toString(input.buttons));
		
		
		if(input.buttons[Input.ESC])
		{
			pauseTime = System.currentTimeMillis();
			input.buttons[Input.ESC] = false;
			pauseGame();
		}

		if(input.buttons[Input.F])
		{
			if (fpsCheck)
				fpsCheck = false;
			else
				fpsCheck = true;
			input.buttons[Input.F] = false;
		}

		try
		{
			if (readyForSpawn && enemies.isEmpty())
			{
				readyForSpawn = false;
				newWave = new javax.swing.Timer(2000, new WaveSpawnTimer());
				newWave.start();
			}
			bs = getBufferStrategy();
			if (bs == null)
			{
				createBufferStrategy(3);
				requestFocus();
				return;
			}
			g = bs.getDrawGraphics();
			g1 = bs.getDrawGraphics();
			g2 = bs.getDrawGraphics();
			bs.show();
			
			processBackground(g);

			Font f = new Font("SansSerif", Font.BOLD, 25);
			g2.setColor(Color.WHITE);
			g2.setFont(f);
			g2.drawString("Score: " + score, GAME_WIDTH / 8, 40);
			g2.drawString("Level: " + getLevel(), GAME_WIDTH/2 - 47, 40);
			g2.drawString("Lives: " + lives, GAME_WIDTH * 4 / 5, 40);

			processPlayer(g);
			processBullets(g1);
			showFPS(g2);

			if (lives > 0 && (!waveSpawn || (!enemies.isEmpty() && getCurrentWave() != null)))
			{
				processEnemy(g1);

			}
			else if (lives <= 0 && loseCheck)
			{
				
				loseCheck = false;
				gameLost = true;
				gameOver = true;
				music.stop();
				bulletSpawn.stop();
				bulletSound.stop();
				GameRender.gr.endGame(score, gameLost, gameLost);
				//restartGame();
			}
			else if(getCurrentWave() == null && wave7 != null && winCheck)
			{
				winCheck = false;
				gameWon = true;
				gameOver = true;
				music.stop();
				GameRender.gr.endGame(score, gameWon, gameLost);
			}
			g.dispose();

			try
			{
				Thread.sleep(10);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		} catch (Exception ex)
		{
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		
		if(startAnimationCheck)
		{
			startAnimationCheck = false;
			GameRender.gr.startAnimation();
		}
		
		try
		{
			bgImage = ImageIO.read(Game.class.getResource("/assets/bg.png"));
		} catch (IOException ex)
		{
			System.out.println("problem?");
		}
		
	

		if (firstMusic)
		{
			playSound("bgmusic.wav");	//http://www.last.fm/music/8+Bit+Weapon/_/Chip+On+Your+Shoulder
			firstMusic = false;
		}
		
		

		javax.swing.Timer backgroundMusic = new javax.swing.Timer(201000, new BackgroundMusic());
		backgroundMusic.start();

		bulletSpawn.start();
		//playSound("laser.wav");

		vincibleTimer = new javax.swing.Timer(4000, new VincibleTimer());
		vincibleTimer.start();
		vincibleTimerStartTime = System.currentTimeMillis();

		enemySpawn.start();
		realRun();
	}
	
	public void realRun()
	{
			

		while (gameCheck)
		{	
			render();
		}
	}

	/**
	 * Starts a new thread for the game
	 */
	public void start()
	{
		theGame = new Thread(this);
		theGame.start();
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

	/**
	 * Timer for shooting bullets
	 */
	class BulletTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!gameOver)
			{
				bullets.add(new Bullet(player.getX()
						+ (Player.getPlayerImgWidth() / 2), player.getY()
						+ (Player.getPlayerImgHeight() / 2), getMouseAngle(), 20));
				if(firstShotCheck)
				{
					firstShotCheck = false;
					playSound("laser.wav");
				}
			}
		}
	}

	/**
	 * Timer for initial spawn of enemies
	 */
	class SpawnTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			generateWaves();
			spawnEnemy();
			enemySpawn.stop();
		}
	}

	/**
	 * Timer for respawning enemies
	 */
	class RespawnTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			spawnEnemy();
			enemyRespawn.stop();
		}
	}

	/**
	 * Timer for creating a new wave
	 */
	class WaveSpawnTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			readyForSpawn = true;
			generateWaves();
			waveSpawn = true;
			spawnEnemy();
			newWave.stop();
		}
	}

	/**
	 * Timer for ending invincibility 
	 */
	class VincibleTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			vincible = true;
			vincibleTimer.stop();
		}
	}

	/**
	 * Timer for looping background music
	 */
	class BackgroundMusic implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			playSound("bgmusic.wav");
		}
	}
}