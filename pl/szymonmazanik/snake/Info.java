package pl.szymonmazanik.snake;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import pl.szymonmazanik.snake.components.SnakeButton;
/**
 * Info class is responsible for printing the about component 
 */
public class Info extends JComponent {

  // Creating variables and objects
  private JLabel info = new JLabel("About");
  private SnakeButton button = new SnakeButton("Back");
  private String text;
  private JTextArea pane = new JTextArea();

  /**
   * Constructor builds up the string and adds listener 
   */
	public Info(){
		//Builds the string
    text =  " Java Snake 1.0 \n\n" 
          + " This game is Open Source."
          + "Take a look at simple guide through the code on my web site.\n"
          + " Source code available at http://it-tips.org/java-snake/ \n\n" 
          + " Created by: Szymon Mazanik | http://szymonmazanik.pl/ | @smazanik \n\n"
          + " Crunch III sound by David Young – http://flashkit.com/ \n" 
          + " THE ANT HILL GANG GOES WEST music - http://soundimage.org/ \n" 
          + " Little Bird font by Benjamin Wyler - http://www.dafont.com \n";
	
		    button.addActionListener(new ActionListener(){
		    @Override
			public void actionPerformed(ActionEvent e) {
				Snake.changePanel("menu", Info.this); // Changes JComponent in JPanel
				}
			});
		}
	
	
	@Override
	protected void paintComponent(Graphics g){
		info.setFont(Snake.font.deriveFont(Font.PLAIN,60)); // Sets the font for the title
		info.setBounds(320,10,300,70); // Sets the title position
	
		
		pane.setText(text);	// Sets text to the JTextArea	
		pane.setEditable(false); // You can't edit this text area
		pane.setBounds(150, 100, 500, 350); //  Sets the area position
		
	    button.setBounds(300, 500, 200, 40); //  Sets the button position
	    
	    //Adds elements to ui
		add(info);
		add(pane);
		add(button);
	}
}