package pl.szymonmazanik.snake;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pl.szymonmazanik.snake.components.Music;
import pl.szymonmazanik.snake.components.SnakePrefs;
/**
 * Snake is main class. Sets up the ui, fonts, turns music on,
 * sets image icon and contains the changePanel() method which allows you to switch between JComponents
 * @author Szymon Mazanik - IT-Tips.org | szymonmazanik.pl
 */
public class Snake {

  // Creates objects and variables
  private JFrame frame = new JFrame();
  
  // Those vars are static because they are used in static methods bellow. 
  public static JPanel panel = new JPanel();
  public static Menu menuPanel = new Menu();
  public static Info infoPanel = new Info();
  public static Font font;
  
  private GraphicsEnvironment ge;
  private SnakePrefs pref = new SnakePrefs();

	public Snake(){		
		panel.setPreferredSize(new Dimension(800,600)); // There is only one screen resolution available 
		panel.setBackground(new Color(225, 183, 115)); // Sets up background color
		panel.setLayout(new BorderLayout()); // Sets up layout type
		changePanel("menu"); // Loads menu 
		
		
		/**
		 * Registers new font into the JVM 
		 */
		try{
		font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/LittleBird.ttf")); // Loads font file
		font = font.deriveFont(Font.PLAIN, 20); // Sets the default font size
		
		//Registers font
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		ge.registerFont(font);
		
		} catch (IOException | FontFormatException e) {
			System.out.println("Could not load fancy font. Exception occurred:");
			e.printStackTrace();
			font = null;
		}
		
	
		if(pref.isMusicSwitch()){ // Checks if user turned off the music
			Music musicInstance = Music.getInstance(); // Gets the music instance 
			musicInstance.playMusic(); // Plays
		}
		
		//Basic frame setup 
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Java Snake");
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);	
		
		// Sets the icon
		ImageIcon img = new ImageIcon(getClass().getResource("/logo.png")); // getClass and getResource methods are necessary only when you want to run this as runnable .jar. Otherwise passing "clean" path to the resource is fine.
		frame.setIconImage(img.getImage());	
	}
	
	/**
	 * Changes the screen to the desirable JComponent.
	 * Notice that some of the options create new objects and some use the static objects created earlier.
	 * Thats because some of the components require new object every time they are loaded. 
	 *   
	 * @param option  name of the component you want to load.
	 */
	public static void changePanel(String option){
			switch (option){
			case "menu":
				panel.add(menuPanel, BorderLayout.CENTER); break;
			case "game":
				panel.add(new Game(), BorderLayout.CENTER); break;
			case "scoreboard":
				panel.add(new Scoreboard(), BorderLayout.CENTER); break;
			case "settings":
				panel.add(new Settings(), BorderLayout.CENTER); break;
			case "info":
				panel.add(infoPanel, BorderLayout.CENTER); break;
			}
	}
	
	/**
	 * Changes the screen to the desirable JComponent and removes given component. 
	 * @param option name of the component you want to load.
	 * @param comp	 name of the component you want to remove.
	 * @see #changePanel(String)
	 */
	public static void changePanel(String option, JComponent comp){
		panel.remove(comp); // Removes component
		panel.revalidate(); // Revalidates the panel
		changePanel(option); // Sets up the proper component
		panel.repaint(); // Repaints the whole panel 
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Snake(); // Starts the application 
			}
		});
	}
	
}