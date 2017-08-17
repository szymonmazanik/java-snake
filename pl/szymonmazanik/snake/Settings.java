package pl.szymonmazanik.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pl.szymonmazanik.snake.components.Music;
import pl.szymonmazanik.snake.components.ScoreDB;
import pl.szymonmazanik.snake.components.SnakeButton;
import pl.szymonmazanik.snake.components.SnakePrefs;

/**
 * Settings allows you to write and read user's preferences. 
 * @see SnakePrefs
 */
public class Settings extends JComponent {
	// Creates the ui objects
	private JLabel title = new JLabel("Settings");
	private JSlider gameSlider = new JSlider(JSlider.HORIZONTAL, 0, 10 , 0);
	private JSlider musicSlider = new JSlider(JSlider.HORIZONTAL, 0, 10 , 0);
	private JButton restartScore = new SnakeButton("Restart scoreboard");
	private JButton musicOn = new SnakeButton("Turn music on");
	private JButton musicOff = new SnakeButton("Turn music off");
	private JButton exit = new SnakeButton("Exit");
	private JButton ok = new SnakeButton("OK");
	
	private JRadioButton blueRadio = new JRadioButton("Blue");
	private JRadioButton redRadio = new JRadioButton("Red");
	private JRadioButton grayRadio = new JRadioButton("Gray");
	private JRadioButton blackRadio = new JRadioButton("Black");
	private ButtonGroup bG = new ButtonGroup();
	
	private Graphics2D g2;
	
	private boolean music;
	private int snakeColor;
	private Music musicInstance = Music.getInstance(); // Gets the music instance 
	
	private SnakePrefs pref = new SnakePrefs(); // Loads the SnakePrefs object
	
	/**
	 * Constructor loads basic values and adds listeners
	 */
	public Settings(){
		
		 // Adds radio buttons to the ButtonGroup
		 bG.add(blueRadio);
		 bG.add(redRadio);
		 bG.add(grayRadio);
		 bG.add(blackRadio);
		 
		 setDefSettings(); // Sets current settings to the ui
		 
		 restartScore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ScoreDB db = new ScoreDB(); // Creates database object
				db.clearTable(); // Clears database
				db.close(); // Closes database
			}
		});
		 
		 musicOn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchMusicButtons(false); // Switches buttons 
				musicInstance.playMusic(); // Plays music
			}
		});
		 
		 musicOff.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchMusicButtons(true); // Switches buttons
				musicInstance.stopMusic(); // Stops music
			}
		});
		 
		 musicSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int vol = musicSlider.getValue();	// Gets the slider value			
				musicInstance.changeVol(vol);		// Changes the vol
			}
		});
		 
		 
		// Adds the listeners
		RadioButtonActionListner listner = new RadioButtonActionListner();
		blueRadio.addActionListener(listner);
		redRadio.addActionListener(listner);
		grayRadio.addActionListener(listner);
		blackRadio.addActionListener(listner);
		 
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Sets preferences
				pref.setGameVol(gameSlider.getValue());		
				pref.setMusicVol(musicSlider.getValue());
				pref.setMusicSwitch(music);
				pref.setSnakeColor(snakeColor);
				Snake.changePanel("menu", Settings.this); // Switches component
			}
		});
		
		 exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					setDefSettings(); // Restarts ui back to old preferences 
					Snake.changePanel("menu", Settings.this); // Switches component
			}
		});		 
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {

		g2 = (Graphics2D) g; 
		
		title.setBounds(270,10,300,70); // Sets title position	
		
		//Draws volume strings
		g.drawString("Game volume", 70, 112);
	    g.drawString("Music volume", 70, 162);
		
	    // Sets slider's background and position
		gameSlider.setBackground(new Color(225, 183, 115));
		gameSlider.setBounds(200,100,500,20);
		musicSlider.setBackground(new Color(225, 183, 115));
		musicSlider.setBounds(200,150,500,20);
		
		// Sets buttons position
		restartScore.setBounds(100, 240, 250, 50);
		musicOn.setBounds(400, 240, 250, 50);
		musicOff.setBounds(400, 240, 250, 50);
		
		 
		//Sets up whole color chooser 
		g2.drawString("Snake Color:", 200, 325);
		blueRadio.setBounds(200, 340, 100, 15);
    	blueRadio.setOpaque(false);
		redRadio.setBounds(200, 360, 100, 15);
		redRadio.setOpaque(false);
		grayRadio.setBounds(200, 380, 100, 15);
		grayRadio.setOpaque(false);
		blackRadio.setBounds(200, 400, 100, 15);
		blackRadio.setOpaque(false);
		
    	g2.setPaint(pref.convertColorIdToSnakeColor(snakeColor)); // Sets the snake colour 
		 
		
		g2.fillRect(500, 370, 10, 100); // Draws the snake
		
		// Sets buttons position
		exit.setBounds(400, 540, 200, 40);
		ok.setBounds(180, 540, 200, 40);
		
		title.setFont(Snake.font.deriveFont(Font.PLAIN,60)); // Sets title font
	    
		// Adds elemements to the ui     	 
		add(title);
		add(gameSlider);
		add(musicSlider);
		add(restartScore);
		add(musicOff);
		add(musicOn);
		add(blueRadio);
		add(redRadio);
		add(grayRadio);
		add(blackRadio);
		add(exit);
		add(ok);
	}
	
	/**
	 * Sets up the elements on ui taken from SnakePrefs' object 
	 */
	private void setDefSettings(){
		 gameSlider.setValue(pref.getGameVol()); // Sets the gameSlider value
		 musicSlider.setValue(pref.getMusicVol()); // Sets the gameSlider value
		 music = pref.isMusicSwitch(); // Checks if user turned music off 
		 
		 // Switches the buttons according to music boolean
		 if(music) switchMusicButtons(false); 
		 else switchMusicButtons(true);	
		 
		 snakeColor = pref.getSnakeColor(); // Gets snake color id
		 
		 // Sets the proper radio button  
		 switch(snakeColor){
		 case 1:
			 blueRadio.setSelected(true); break;
		 case 2: 
			 redRadio.setSelected(true); break;
		 case 3: 
			 grayRadio.setSelected(true); break;
		 default:
			 blackRadio.setSelected(true);			 
		 }
	}
	
	/**
	 * Switches the "turn music" on and off buttons. 
	 * @param b defines which button should be shown and which should be hidden.
	 */
	 private void switchMusicButtons(boolean b){
		 if(b){
			 musicOff.setVisible(false); // hides the musicOff button
			 musicOn.setVisible(true); // shows the musicOn button
			 music = false; // Sets music boolean to false
		 }
		 else{
			 musicOff.setVisible(true); // shows the musicOff button
			 musicOn.setVisible(false); // hides musicOn button 
			 music = true; // Sets music boolean to true
		 }
	 }
	 
			/**
			 * This action listeners is assigned to Radio Buttons. 
			 * actionPerformed is called every Radio Button shift. 
			 */
			private class RadioButtonActionListner implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {
					// Each color has it's own id
					if(blueRadio.isSelected()) snakeColor = 1;
					else if(redRadio.isSelected()) snakeColor = 2;
					else if(grayRadio.isSelected()) snakeColor = 3;
					else snakeColor = 4;
					repaint(); // Repaint to change the colour 					
				}
				
			}
}


