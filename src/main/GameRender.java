package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Class: GameRender.java
 * @author Jordan Smith
 * @author Kyle Dornblaser 
 * 
 * This class – Creates the frame for the game and all the different panels
 * 
 */
public class GameRender
{
	public static Game game;
	public static GameRender gr;
	public static CardLayout cardLayout = new CardLayout();

	private static final JFrame frame = new JFrame("Geo Wars");
	private static JPanel startScreen;
	private static Image cursor;
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	private int score = 0;
	private JPanel pauseMenu;

	/**
	 * 0 param constructor Sets up the Frame and JPanels
	 */
	public GameRender()
	{	
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setResizable(false); 
		frame.setVisible(true);
		Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(d);
		frame.setLayout(cardLayout);

		Point hotSpot = new Point(0, 0);
		Cursor pointer = toolkit.createCustomCursor(cursor, hotSpot, "Crosshair");
		frame.setCursor(pointer);
		startScreen = new JPanel();
		startScreen.setLayout(new BoxLayout(startScreen, BoxLayout.Y_AXIS));
		startScreen.setBackground(Color.BLACK);

		JLabel title = new JLabel();
		JButton beginGame = new JButton();
		JButton instructions = new JButton();
		JButton exit = new JButton();

		createLabel(title, "GEOWARS");
		createButton(beginGame, "New Game");
		createButton(instructions, "How to Play");
		createButton(exit, "Exit Game");

		startScreen.add(Box.createRigidArea(new Dimension(0, 100)));
		startScreen.add(title);
		startScreen.add(Box.createRigidArea(new Dimension(0, 50)));
		startScreen.add(beginGame);
		startScreen.add(Box.createRigidArea(new Dimension(0, 20)));
		startScreen.add(instructions);
		startScreen.add(Box.createRigidArea(new Dimension(0, 20)));
		startScreen.add(exit);

		frame.add(startScreen, "one");
		frame.add(createHowTo(), "three");

		cardLayout.show(frame.getContentPane(), "one");

		beginGame.addActionListener(new StartListener());
		instructions.addActionListener(new HowToListener());
		exit.addActionListener(new ExitButtonListener());
	}

	/**
	 * Starts a new game 
	 */
	public void startGame()
	{
		game = new Game();
		game.setBackground(Color.BLACK);
		game.start();
		frame.add(game, "five");
		cardLayout.show(frame.getContentPane(), "five");
	}

	/**
	 * Shows the pause menu
	 * @param score
	 */
	public void pause(int score)
	{
		this.score = score;
		pauseMenu = createPauseMenu();
		frame.add(pauseMenu, "four");
		cardLayout.show(frame.getContentPane(), "four");
	}

	/**
	 * Starts the end animation.
	 * @param score
	 * @param gameWon
	 * @param gameLost
	 */
	public void endGame(int score, boolean gameWon, boolean gameLost)
	{
		EndAnimation ea = new EndAnimation(score, gameWon, gameLost );
		ea.setBackground(Color.BLACK);
		frame.add(ea, "six");
		cardLayout.show(frame.getContentPane(), "six");
		ea.run();
	}

	/**
	 * Creates and returns the JPanel for how to play.
	 * @return 
	 */
	private JPanel createHowTo()
	{
		JPanel ht = new JPanel();
		ht.setLayout(new BoxLayout(ht, BoxLayout.Y_AXIS));
		ht.setBackground(Color.BLACK);

		JLabel hsLabel = new JLabel("HOW TO PLAY");
		hsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		hsLabel.setFont(new Font("SansSerif", Font.BOLD, 80));
		hsLabel.setForeground(Color.RED);
		hsLabel.setBackground(Color.BLACK);

		ht.add(Box.createRigidArea(new Dimension(0, 100)));
		ht.add(hsLabel);
		ht.add(Box.createRigidArea(new Dimension(0, 30)));

		JLabel wasd = new JLabel("Press W,A,S,D for movement.");
		JLabel mouse = new JLabel("Move your mouse for aiming.");
		JLabel fps = new JLabel("Press F for FPS toggle.");
		JLabel escape = new JLabel("Press ESC for pause menu.");
		JLabel objective = new JLabel("Objective: Shoot everything!");

		ht.add(wasd);
		ht.add(fps);
		ht.add(escape);
		ht.add(mouse);
		ht.add(Box.createRigidArea(new Dimension(0, 30)));
		ht.add(objective);

		wasd.setAlignmentX(Component.CENTER_ALIGNMENT);
		wasd.setForeground(Color.WHITE);
		wasd.setFont(new Font("SansSerif", Font.BOLD, 30));

		mouse.setAlignmentX(Component.CENTER_ALIGNMENT);
		mouse.setForeground(Color.WHITE);
		mouse.setFont(new Font("SansSerif", Font.BOLD, 30));

		fps.setAlignmentX(Component.CENTER_ALIGNMENT);
		fps.setForeground(Color.WHITE);
		fps.setFont(new Font("SansSerif", Font.BOLD, 30));

		escape.setAlignmentX(Component.CENTER_ALIGNMENT);
		escape.setForeground(Color.WHITE);
		escape.setFont(new Font("SansSerif", Font.BOLD, 30));

		objective.setAlignmentX(Component.CENTER_ALIGNMENT);
		objective.setForeground(Color.WHITE);
		objective.setFont(new Font("SansSerif", Font.BOLD, 30));

		ht.add(Box.createRigidArea(new Dimension(0, 100)));
		JButton homebutton = new JButton();
		createButton(homebutton, "Back to Startscreen");
		ht.add(homebutton);
		homebutton.addActionListener(new HomeButtonListener());

		return ht;
	}

	/**
	 * Creates and returns the JPanel for the pause menu.
	 * @return
	 */
	public JPanel createPauseMenu()
	{
		JPanel pm = new JPanel();
		pm.setLayout(new BoxLayout(pm, BoxLayout.Y_AXIS));
		pm.setBackground(Color.BLACK);

		JLabel hsLabel = new JLabel("GAME PAUSED");
		hsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		hsLabel.setFont(new Font("SansSerif", Font.BOLD, 80));
		hsLabel.setForeground(Color.RED);
		hsLabel.setBackground(Color.BLACK);

		pm.add(Box.createRigidArea(new Dimension(0, 100)));
		pm.add(hsLabel);
		pm.add(Box.createRigidArea(new Dimension(0, 70)));

		JButton returnGame = new JButton();
		JButton returnMenu = new JButton();
		JButton exitGame = new JButton();

		JLabel scoreLabel = new JLabel("Current Score: " + score);
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setBackground(Color.BLACK);
		scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreLabel.setOpaque(false);

		createButton(returnGame, "Return to Game");
		createButton(returnMenu, "Return to Main Menu");
		createButton(exitGame, "Exit Game");

		if(!game.getGameStatus())
			pm.add(returnGame);

		pm.add(returnMenu);
		pm.add(exitGame);
		pm.add(Box.createRigidArea(new Dimension(0, 100)));
		pm.add(scoreLabel);

		returnGame.addActionListener(new ReturnGameButtonListener());
		returnMenu.addActionListener(new BackHomeButtonListener());
		exitGame.addActionListener(new ExitButtonListener());

		return pm;
	}

	/**
	 * Modifies and returns a JLabel with a different theme.
	 * @param label
	 * @param text
	 * @return 
	 */
	public JLabel createLabel(JLabel label, String text)
	{
		label.setText(text);
		label.setFont(new Font("SansSerif", Font.BOLD, 100));
		label.setForeground(Color.GREEN);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		return label;
	}

	/**
	 * Modifies and returns a JButton with a different theme.
	 * @param button
	 * @param text
	 * @return 
	 */
	public JButton createButton(final JButton button, String text)
	{
		button.setText(text);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("SansSerif", Font.BOLD, 20));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setFocusPainted(false);
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.addMouseListener(new java.awt.event.MouseAdapter()
		{
			public void mouseEntered(java.awt.event.MouseEvent evt)
			{
				button.setForeground(Color.GRAY);
			}

			public void mouseExited(java.awt.event.MouseEvent evt)
			{
				button.setForeground(Color.WHITE);
			}
		});
		return button;
	}

	/**
	 * Returns the location for the JFrame.
	 * @return
	 */
	public static Point getJFrameLocation()
	{
		return frame.getLocation();
	}

	public void startAnimation()
	{
		StartAnimation sa = new StartAnimation();
		sa.setBackground(Color.BLACK);
		frame.add(sa, "two");
		cardLayout.show(frame.getContentPane(), "two");
		sa.run();
	}

	public void showGame()
	{

		cardLayout.show(frame.getContentPane(), "five");
		game.requestFocus();
		//game.realRun();
		game.run();

	}

	public void showMenu()
	{

	}

	/**
	 * The main method of the entire program.
	 * Loads the cursor image.
	 * Creates instance of GameRender.
	 * @param args
	 */
	public static void main(String[] args)
	{
		cursor = toolkit.getImage(GameRender.class.getResource("/assets/crosshair.png"));
		gr = new GameRender();
	}

	/**
	 * Starts and shows the game.
	 */
	public class StartListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//			StartAnimation sa = new StartAnimation();
			//			sa.setBackground(Color.BLACK);
			//			frame.add(sa, "two");
			//			cardLayout.show(frame.getContentPane(), "two");
			//			sa.run();
//			if (game == null)
//			{
				game = new Game();
				game.setBackground(Color.BLACK);
				game.start();
				frame.add(game, "five");
				cardLayout.show(frame.getContentPane(), "five");
//			}
//			else
//			{
//				cardLayout.show(frame.getContentPane(), "five");
//				
//				game.requestFocus();
//				game.restartGame();
//				game.realRun();
//			}
		}
	}

	/**
	 * Shows how to play.
	 */
	public class HowToListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			cardLayout.show(frame.getContentPane(), "three");
		}
	}

	/**
	 * Shows the main menu.
	 */
	public class HomeButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			cardLayout.show(frame.getContentPane(), "one");
		}
	}

	/**
	 * Shows the main menu.
	 */
	public class BackHomeButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			game.stopRender();
			cardLayout.show(frame.getContentPane(), "one");
		}
	}

	/**
	 * Exits the program.
	 */
	public class ExitButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}

	/**
	 * Returns to the game from the pause menu.
	 */
	public class ReturnGameButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			cardLayout.show(frame.getContentPane(), "five");
			frame.remove(pauseMenu);
			game.requestFocus();
			game.resumeGame();
		}
	}
}
