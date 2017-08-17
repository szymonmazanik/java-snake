package pl.szymonmazanik.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import pl.szymonmazanik.snake.components.ScoreDB;
import pl.szymonmazanik.snake.components.SnakeButton;

/**
 * Prints the score. Score is kept in SQLite database. 
 * @see #pl.szymonmazanik.snake.components.ScoreDB
 */
public class Scoreboard extends JComponent {
	
	// Creating objects 
	private JLabel title = new JLabel("Scoreboard");	
	private JButton backButton = new SnakeButton("Back");	
	private ArrayList<ArrayList<String>> nodes; // nodes will be used for printing the score
	
	/**
	 * Constructor loads data from the data base and sets up the listener 
	 */
	public Scoreboard() {
		ScoreDB db = new ScoreDB(); // Creates the ScoreDB object
		nodes = db.seclectScores(); // Gets nodes from db 
		db.close(); // Closes the data base
		
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Snake.changePanel("menu", Scoreboard.this); // Switches the JComponent in JPanel
			}
		});
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {		
		// Sets the title font and position
		title.setFont(Snake.font.deriveFont(Font.PLAIN,60)); 
		title.setBounds(240,10,300,50);
			
		Graphics2D g2d = (Graphics2D) g; // Graphics2D draws the table

			  // Draws the lines
		 	  g2d.setColor(Color.black);
		      g2d.drawLine(150,140,650,140);
		      g2d.drawLine(200, 110, 200, 450);
		      g2d.drawLine(480, 110, 480, 450);
		      g2d.drawLine(580, 110, 580, 450);  
		      
		     // Draws the strings
			 g2d.drawString("Rank", 160, 135);
			 g2d.drawString("Name", 320, 135);
			 g2d.drawString("Score", 510, 135);
			 g2d.drawString("Date", 600, 135);
		      
		  // Prints the names, scores and dates.	 
	     for(int i = 0; i < 15; i++){ // i < 15 because I want to print 15 best scores
	    	 
	      /**
	       * I'm catching IndexOutOfBoundsException	just in case...
	       */
	      try{
	    	  
	    	  if(i < nodes.size()){ // Checks if i isn't bigger than nodes size
	    	  g2d.drawString(i+1 + "", 170, 160 + ((i+i) * 10)); // Draws the position
	    	  g2d.drawString(nodes.get(i).get(0), 210, 160 + ((i+i) * 10)); // Draws the name
	    	  g2d.drawString(nodes.get(i).get(1), 490, 160 + ((i+i) * 10)); // Draws the score	    	  
	    	  g2d.drawString(nodes.get(i).get(2), 600, 160 + ((i+i) * 10)); // Draws the date
	    	  } else break; // If i is bigger than nodes size, break the loop
	    	  
    	  } catch (IndexOutOfBoundsException e){ // Catches IndexOutOfBoundsException in case "if" dosn't do it's job
    		  break; // In case of exception, breaks the loop
    	  }
	     
	     }
	    
	    backButton.setBounds(300, 500, 200, 40); // Sets the button position
	    
	    //Adds the components to ui
	    add(title);
		add(backButton);
		
	}
}