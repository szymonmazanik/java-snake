package pl.szymonmazanik.snake.components;

import java.awt.Color;
import java.awt.Graphics;


/**
 * Apples creates apples in game.
 * I've used getters and setter here. It provides easy access to fields. 
 * @see Game
 */
public class Apples {
	private int x, y, width, height; 
	/**
	 * Creates coordinates of apple
	 * @param x coordinate
	 * @param y coordinate
	 * @param tile size 
	 */
	public Apples(int x, int y, int tile){
		this.x = x;
		this.y = y;
		width = tile; 
		height = tile;
	}

	/**
	 * Draws the apple.
	 * @param g Graphics class is passed from paintComponent method
	 */
	public void draw(Graphics g){
		g.setColor(Color.RED);
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

	/**
	 * Gets height.
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets height. 
	 * @param height of the tile 
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}
