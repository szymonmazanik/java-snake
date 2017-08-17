package pl.szymonmazanik.snake.components;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import pl.szymonmazanik.snake.Settings;

/**
 * Music allows us to play music in background.
 * I've used "Singleton" pattern here. It allows me to pass an instance between different objects.  
 */
public class Music {
	private File f;
	private AudioInputStream sound;
	private DataLine.Info info;
	private Clip clip;
	private FloatControl gainControl;
	private SnakePrefs pref = new SnakePrefs();
	
	private static Music instance = null; // Creating the instance for Singleton
		
	/**
	 * Plays and loops the music. 
	 */
	public void playMusic(){	
		try{				
			int volMusic = pref.getMusicVol(); // Gets the music volume	
			
			URL url = getClass().getResource("/music.wav"); // Gets url to the file
			sound = AudioSystem.getAudioInputStream(url); // Converts the file from url into AudioInputStream
			
		    // load the sound into memory (a Clip)
		    info = new DataLine.Info(Clip.class, sound.getFormat());
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(sound);
		    
		    gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); // Prepares clip to change volume 
		    changeVol(volMusic); // Sets the volume
			
		    //Plays and loops clip
		    clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		 } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e){
				System.out.println("Exception occurred while playing music:");
				e.printStackTrace();
		 }		  
	}
	
	 /**
	  * Stops music
	  */
	public void stopMusic(){
		  clip.stop();
	}
	
	/**
	 * Changes the music volume 
	 * @param vol int which stands for the music volume slider value ( in Settings)
	 * @see Settings
	 */
	public void changeVol(int vol){
		float x = (float) -((100 -  (10 * vol)) / 4); // Simple calculation converts the int to fit float. 
		gainControl.setValue(x); // Changing volume 
	}

	/**
	 * Gets an instance of this class so you can use the same object in different classes.
	 * This operation allows us to work (start / stop / change volume) with background music. 
	 * @return an instance of current Music object. 
	 */
	public static Music getInstance(){
		if (instance == null) instance = new Music(); // If instance is null creates new Music object
		return instance; // Returns the instance of this object. 
	}

}