package pl.szymonmazanik.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import pl.szymonmazanik.snake.components.SnakeButton;

/**
 * Menu class sets up the main screen. 
 */
public class Menu extends JComponent {
	
	// Creating objects 
	private SnakeButton start = new SnakeButton("Start");
	private SnakeButton scoreboard = new SnakeButton("Scoreboard");
	private SnakeButton settings = new SnakeButton("Settings");
	private SnakeButton info = new SnakeButton("About");	
	private Image logo = new ImageIcon(getClass().getResource("/logo.png")).getImage();
	
	
		/**
		 * Constructor sets up the listeners 
		 * Static changePanel method from Snake class switches the JComponent inside of JPanel
		 */
		public Menu(){
			start.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Snake.changePanel("game", Menu.this);					
				}
			});
			
			scoreboard.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Snake.changePanel("scoreboard", Menu.this);					
				}
			});
			
			settings.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Snake.changePanel("settings", Menu.this);					
				}
			});
			
			info.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Snake.changePanel("info", Menu.this);					
				}
			});
		}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(logo, 320, 10, null); // Draws the logo 
		
		//Sets up the buttons position 
		start.setBounds(300, 260, 200, 40); 
		scoreboard.setBounds(300, 340, 200, 40);
		settings.setBounds(300, 420, 200, 40);
		info.setBounds(300, 500, 200, 40);
		
		// Adds buttons to the ui
		add(start);
		add(scoreboard);
		add(settings);
		add(info);
	}
}