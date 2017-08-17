package pl.szymonmazanik.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import pl.szymonmazanik.snake.components.Apples;
import pl.szymonmazanik.snake.components.ScoreDB;
import pl.szymonmazanik.snake.components.SnakeBody;
import pl.szymonmazanik.snake.components.SnakeButton;
import pl.szymonmazanik.snake.components.SnakePrefs;

 /** 
 * The main class of the game screen
 */
public class Game extends JComponent implements Runnable {

  private Thread thread;
  private boolean run = false;

  private Random r = new Random();

  private boolean right = true;
  private boolean left = false;
  private boolean up = false;
  private boolean down = false;
  
  private SnakeBody b;
  private ArrayList<SnakeBody> snake = new ArrayList<SnakeBody>();
  private Apples apple;
  private ArrayList<Apples> apples = new ArrayList<Apples>();

  private int x = 10;
  private int y = 10;
  private int size = 5;
  private int ticks = 0;

  private SnakeButton exit = new SnakeButton("Exit");
  private int currentScore;
  private JLabel scoreLabel = new JLabel(currentScore + " points");

  private JLabel youScored = new JLabel();
  private JTextField field = new JTextField("What's your name?");
  private SnakeButton submitScore = new SnakeButton("Submit");

  private boolean scorePanel = false;

  private Color snakeColor;
  private SnakePrefs pref = new SnakePrefs();

  private CrunchPlayer player = new CrunchPlayer();

  private KAdapter key = new KAdapter();

  /** 
   * Constructor creates environment to start game 
   * and sets up listeners 
   */
  public Game() {
    setFocusable(true);
    addKeyListener(key);

    snakeColor = pref.convertColorIdToSnakeColor(pref.getSnakeColor());

    exit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (run) {
			stop();  // Stops the game
		}
        
        Snake.changePanel("menu", Game.this); // Changes the JComponent in JPanel
      }
      });

		field.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				field.setText(""); // Clearing text field after click
			}
		});

		submitScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = field.getText(); // Gets name from the field
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Creates date format
				Date date = new Date(); // Creates Date object
				ScoreDB db = new ScoreDB(); // and ScoreDB object
				db.insertScore(name, currentScore, dateFormat.format(date)); // Inserts data to database
				db.close(); // Closes database
				Snake.changePanel("menu", Game.this); // switching ui to main menu at the very end
			}
		});
		start();

	}

    /**
     * Painting components in ui
     * @param g Graphics class object use by JVM
     */
	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g; // Graphics2D paints the snake

		g.drawRect(10, 10, 780, 500); // Draws frame
		g.drawString("Score:", 160, 550); // Draws "Score:" string
		
		// Sets up the buttons position
		scoreLabel.setBounds(200, 535, 100, 20); 
		exit.setBounds(500, 540, 200, 40);

		add(exit);
		add(scoreLabel);

		g2.setPaint(snakeColor); // Sets the snake color

		for (int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g2); // Draws snake
		}

		for (int i = 0; i < apples.size(); i++) {
			apples.get(i).draw(g2); // Draws apples
		}

		if (scorePanel) { // After the game is finished
			// Setting up the elements positions
			youScored.setBounds(350, 30, 330, 100); 
			field.setBounds(300, 100, 200, 20);
			submitScore.setBounds(300, 130, 200, 40);
			
			youScored.setText("You've scored " + currentScore + " points."); // Fills the text label
			// Adds elements to ui
			add(submitScore);
			add(youScored);
			add(field);
		}

	}

	/**
	 * tick() runs while the thread is working
	 * @see #run()
	 */
	public void tick() {

		ticks++; // Adds +1 to ticks every time when the method is started

		if (ticks > 300000) { // Defines the game speed

			if (snake.size() == 0) { // If snake does't exist
				// Creates new SnakeBody and adds it to the ui
				b = new SnakeBody(x, y, 10);
				snake.add(b);
			}
			if (apples.size() == 0) { // Each time apple does't exist
				// Creates random positions for apples. +2 Because the frame margin is 20px
				int x = r.nextInt(76) + 2;
				int y = r.nextInt(48) + 2;
				
				apple = new Apples(x, y, 10); // Creates apple object
				apples.add(apple); // and adds it to the Array
			}

			for (int i = 0; i < apples.size(); i++) { 
				if (x == apples.get(i).getX() && y == apples.get(i).getY()) { // Whenever snake hits the apple
					player.play(); // Plays sound
					size++; // Increases snake size
					apples.remove(i); // Apple disappears 
					currentScore += 100; // Adds 100 points to score int
					scoreLabel.setText(currentScore + " points"); // Passes the currentScore int to the label 
				}
			}

			for (int i = 0; i < snake.size(); i++)  
				if (x == snake.get(i).getX() && y == snake.get(i).getY())  // Whenever snake hits himself
					if (i != snake.size() - 1)  // We need to check snake size because it hits itself when it appears
						stop(); // Stops the game
									
			

			if (x < 2 || x > 77 || y < 2 || y > 49) // Whenever snake hits the border
				stop(); // Stops the game
			
			// Checks which boolean is true and moves snake properly 
			if (right) 
				x++;		
			
			if (left) 
				x--;
			
			if (up) 
				y--;
			
			if (down) 
				y++;
					

			b = new SnakeBody(x, y, 10); // Creates new SnakeBody
			snake.add(b); // adds object to array
			
			// Removes the snake last part each time it moves
			if (snake.size() > size) 
				snake.remove(0);
			

			ticks = 0; // Resets the ticks
			
			requestFocusInWindow(); // We need to request focus each time when snake moves
		}
	}

	/**
	 * Starts the game 
	 */
	public void start() {
		run = true; // Whenever run is true game is running
		thread = new Thread(this, "Game Loop"); // Creates new thread
		thread.start(); // Starts thread
	}

	/**
	 * Stops the game
	 */
	public void stop() {
		run = false; // The game is stopped 
		
		// When the game is finished the score panel appears
		scorePanel = true; 
		repaint();
	
		/**
		 * Finishes the thread properly 
		 */
		try {
			thread.join();
		} catch (InterruptedException e) {
			System.out.println("Exception occured while finishing thread:");
			e.printStackTrace();
		}
	}

	/**
	 * Run method from Runnable interface.  
	 */
	@Override
	public void run() {
		while (run) { // When the game is running
			tick(); // Ticks all the time
			repaint(); // Repaint after every tick
		}
	}

	/** 
	 * CrunchPlayer is responsible for playing crunch sound
	 */
	private class CrunchPlayer {

		// Declares required variables
		private File crunchFile;
		private AudioInputStream sound;
		private DataLine.Info info;
		private Clip clip;
		private FloatControl gainControl;

		/**
		 * Constructor prepares an object to play the sound.
		 * It opens and loads the file sets sound volume. 
		 */
		public CrunchPlayer() {
			try {
				int vol = pref.getGameVol(); // Gets the sound volume as an int
				
				URL url = getClass().getResource("/crunch.wav"); // Gets url to the file
				sound = AudioSystem.getAudioInputStream(url); // Converts the file from url into AudioInputStream
				
				// Gets data form the sound input stream and prepares the clip to play
				info = new DataLine.Info(Clip.class, sound.getFormat()); 
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(sound);
				
				//Sets up the sound volume
				gainControl = (FloatControl) clip
						.getControl(FloatControl.Type.MASTER_GAIN);
				float volFloat = (float) -((100 - (10 * vol)) / 4);
				gainControl.setValue(volFloat);
				
			} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
				System.out.println("Exception occured while playing crunch sound:");
				e.printStackTrace();
			}
		}

		/**
		 * Plays the sound
		 */
		public void play() {
			clip.start(); // Starts the clip
			clip.setFramePosition(0); // Sets the frame position to the begin after the clip is played 
		}
	}

	/**
	 * Key Adapter is used to control the snake
	 */
	private class KAdapter implements KeyListener {

		/**
		 * Calls each time key is pressed
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode(); // gets the key code
			
			// Checks the key code and sets up the booleans, so the snake moves properly 
			if ((key == KeyEvent.VK_LEFT) && (!right)) {
				left = true;
				up = false;
				down = false;
			}

			if ((key == KeyEvent.VK_RIGHT) && (!left)) {
				right = true;
				up = false;
				down = false;
			}

			if ((key == KeyEvent.VK_UP) && (!down)) {
				up = true;
				right = false;
				left = false;
			}

			if ((key == KeyEvent.VK_DOWN) && (!up)) {
				down = true;
				right = false;
				left = false;
			}
		}

		
		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {}
	}

}