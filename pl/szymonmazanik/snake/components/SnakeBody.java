package pl.szymonmazanik.snake.components;

import java.awt.Graphics;

/**
 * Creates snake parts 
 */
public class SnakeBody {

	private int x, y, width, height; 
	
	/**
	 * Creates coordinates of snake body 
	 * @param x coordinate
	 * @param y coordinate
	 * @param tile size 
	 */
	public SnakeBody(int x, int y, int tile){
		this.x = x;
		this.y = y;
		width = tile;
		height = tile;
	}
	
	/**
	 * Draws the snake body.
	 * @param g Graphics class is passed from paintComponent method
	 */
	public void draw(Graphics g){	
		g.fillRect(x * width, y * height, width, height);
	}

	/**
	 * Gets X coordinate.
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets X coordinate.
	 * @param x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Gets Y coordinate.
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets Y coordinate.
	 * @param y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
}