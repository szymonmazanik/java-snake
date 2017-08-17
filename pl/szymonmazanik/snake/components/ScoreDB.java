package pl.szymonmazanik.snake.components;
import java.sql.*;
import java.util.ArrayList;

public class ScoreDB {
	
	private Connection c;
	private DatabaseMetaData dbm;
	Statement stm;
	
	private boolean ready = false;

	/**
	 * Makes an connection to the database. 
	 */
	public ScoreDB(){
		c = null;		
		try {
		      Class.forName("org.sqlite.JDBC"); // Causes the class named JDBC to be dynamically loaded (at runtime).
		      c = DriverManager.getConnection("jdbc:sqlite:score.db");	// Connects to the score.db file
		  	  stm = c.createStatement(); // Creates statement we can work with
		  	  
		  	  // If c isn't null we can proceed to browse database 
		      if(c != null){			    	
						dbm = c.getMetaData(); // Gets meta data
						ResultSet tables = dbm.getTables(null, null, "SCORETABLE", null); // Selects table 
						if (tables.next())	ready = true; 	// If table exist database is ready to work with				
						else {						 		// But if not we need to create new table
						  // Creates sql statement 
						  String sql = "CREATE TABLE SCORETABLE " +
				                   "(ID INTEGER PRIMARY KEY   AUTOINCREMENT," +
				                   " NAME           TEXT    NOT NULL, " + 
				                   " SCORE          INT     NOT NULL, " + 
				                   " DATE          	TEXT    NOT NULL) "; 
						  stm.executeUpdate(sql); // Executes the statement
						  ready = true;			  // Makes database ready to work 
						}
		      }
				
		    } catch (SQLException | ClassNotFoundException e) {
		    	System.out.println("Exception occurred while connectiong to the database:");
		    	e.printStackTrace();
		    	ready = false; // Makes database not ready
		    }
		
	 }
	
	/**
	 * Inserts score to the database
	 * @param name  player's name
	 * @param score gained points
	 * @param date  when it happend 
	 */
	public void insertScore(String name, int score, String date){
	  if(ready){
		try {
			// Creates SQL statement
			String sql = "INSERT INTO SCORETABLE (NAME,SCORE,DATE) " +
	            "VALUES (" +
				"'" + name + "','" + score + "','" + date + "');"; 	
			
			stm.executeUpdate(sql); // Executes the statement
		} catch (SQLException e) {
	    	System.out.println("Exception occurred while inserting score to the database:");
	    	e.printStackTrace();
		}
	  }			
	}
	
	/**
	 * Deletes all data from the table
	 */
	public void clearTable(){
	  if(ready){
		try{
			String sql = "DELETE FROM SCORETABLE"; // Creates SQL statement
			stm.executeUpdate(sql); // Executes the statement
		} catch (SQLException e){
	    	System.out.println("Exception occurred while clearing table:");
	    	e.printStackTrace();
		}
	  }
	}
	
	/**
	 * Gets scores as an ArrayList of ArrayList<String> 
	 * @return The list of scores, names and dates
	 */
	public ArrayList<ArrayList<String>> seclectScores(){
	  if(ready){
		ArrayList<ArrayList<String>> nodes = new ArrayList<ArrayList<String>>(); // Creates ArrayList object 
		
		try{		
		String sql = "SELECT * FROM SCORETABLE ORDER BY SCORE DESC;"; // Creates SQL statement 
		ResultSet rs = stm.executeQuery(sql); // Executes statement and saves the result into rs 
		while(rs.next()){ // Loops the result set
			ArrayList<String> i = new ArrayList<String>(); // Creates new ArrayList
			
			// Gets data into strings
			String name = rs.getString("NAME"); 
			String score = new Integer(rs.getInt("SCORE")).toString();
			String date = rs.getString("DATE");
			
			// Adds data to the list 
			i.add(name);
			i.add(score);
			i.add(date);
			
			nodes.add(i); // Adds list to the list 
		}				
		
		return nodes; // Returns "nodes"  
		
		} catch (SQLException e){
	    	System.out.println("Exception occurred while reading results from table:");
	    	e.printStackTrace();
			return null;
		}		
	  }
	  return null; // If connection is not ready, then returns null 
	}
	
	/**
	 *  Closes the database connection
	 */
	public void close(){
		try {
			if (ready) c.close(); // Closes the connection
		} catch (SQLException e) {
	    	System.out.println("Exception occurred while closing the database:");
	    	e.printStackTrace();
	    	c = null;
		}
	}
	
	
	
}
