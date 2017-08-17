package pl.szymonmazanik.snake.components;

import java.awt.Color;
import java.util.prefs.Preferences;

/**
 * Access you to preferences. 
 * Contains getters and setters so you can easily read and write preferences. 
 */
public class SnakePrefs{

	private Preferences pref;
	private int musicVol, gameVol;
	private boolean musicSwitch;
	private int snakeColor;
	
	/**
	 * Reads current preferences, so you can access them using getters.
	 */
	public SnakePrefs(){
		pref = Preferences.userRoot().node("JavaSnakePreferences"); // Gets the root 
		
		// Gets the values and assigns them to variables 
		gameVol = pref.getInt("gameVol", 0);
		musicVol = pref.getInt("musicVol", 0);
		musicSwitch = pref.getBoolean("musicBool", true);
		snakeColor = pref.getInt("snakeColor", 3);
	}
	
	/**
	 * Converts int (slider value) to the actual color.
	 * @param id of the Radio Button
	 * @return The actual color 
	 */
	public Color convertColorIdToSnakeColor(int id){
		/*
		 * 1 - Blue
		 * 2 - Red
		 * 3 - Gray 
		 * any other number - black
		 */
		 switch(id){
		 case 1:
			 return Color.BLUE;			 
		 case 2: 
			 return Color.RED;			
		 case 3: 
			 return Color.GRAY;			
		 default:
			 return Color.BLACK;		 
		 }
	}

	/**
	 * Gets the music volume as an slider value.
	 * @return music volume
	 */
	public int getMusicVol() {
		return musicVol;
	}

	/**
	 * Sets the music volume.
	 * @param musicVol value from slider.
	 */
	public void setMusicVol(int musicVol) {
		this.musicVol = musicVol;
		pref.putInt("musicVol", musicVol); // Puts the music volume value 
	}


	/**
	 * Gets the game volume as an slider value.
	 * @return game volume
	 */
	public int getGameVol() {
		return gameVol;
	}

	/**
	 * Sets the game volume.
	 * @param gameVol value from slider.
	 */
	public void setGameVol(int gameVol) {
		this.gameVol = gameVol;
		pref.putInt("gameVol", gameVol); // Puts the game volume value 
	}

	/**
	 * Checks if music should be turned on or off
	 * @return music boolean status
	 */
	public boolean isMusicSwitch() {
		return musicSwitch;
	}

	/**
	 * Sets the music status.
	 * @param musicSwitch true or false - depends if the music should be played or not.  
	 */
	public void setMusicSwitch(boolean musicSwitch) {
		this.musicSwitch = musicSwitch;
		pref.putBoolean("musicBool", musicSwitch); // Puts the game volume value (true or false)
	}

	/**
	 * Gets the snake color ID.
	 * @return snake color ID.
	 */
	public int getSnakeColor() {
		return snakeColor;
	}

	/**
	 * Sets the snake color ID
	 * @param snakeColor chosen Radio Box in Settings
	 */
	public void setSnakeColor(int snakeColor) {
		this.snakeColor = snakeColor;
		pref.putInt("snakeColor", snakeColor); // Puts the snake color id  
	}	
}
