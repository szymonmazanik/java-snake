package pl.szymonmazanik.snake.components;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Provides the buttons in the game design style.
 */
public class SnakeButton  extends JButton{
	/**
	 * Sets up the button
	 * @param s text that appears on the button
	 */
	public SnakeButton(String s){
    super(s);
    
    // Styling the button
    setFocusPainted(false);
    setBorderPainted(false);
    setBackground(new Color(225, 124, 0));
    setForeground(Color.BLACK);    
	}
 }