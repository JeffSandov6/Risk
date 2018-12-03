package JCK.Risk.Extras;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import JCK.Risk.CoverageIgnore;
import JCK.Risk.Players.Player;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Twitterer {
	
    private Twitter twitterInstance;
    private HashMap<String, Integer> playerMap;
   
    @CoverageIgnore
    public Twitterer()
    {    	
    	makeTwitterConnection();  	
    }
    
    @CoverageIgnore
    public HashMap<String, Integer> getPlayerMap() {
    	return this.playerMap;
    }
    
    @CoverageIgnore
    public void postNumberTerrsConquered(String name, int numConquered) {
    	String post = name + " conquered " + numConquered + " this turn";
    	playerMap.put(name, playerMap.get(name) + numConquered); //update value
    	tweetOut(post);
    }
    
    @CoverageIgnore
    public void postFinalTweet() {
    	String finalStats = "The final stats are:\n";
    	for(Map.Entry<String, Integer> item : playerMap.entrySet()) {
    		String name = item.getKey();
    		int value = item.getValue();
    		
    		finalStats += name + " conquered " + value + "territories\n";
    	}
    	tweetOut(finalStats);
    }
    
    @CoverageIgnore
    public void grabPlayerInfo(ArrayList<Player> playersArray) {
    	
    	playerMap = new HashMap<>();
    	for(Player player : playersArray) {
    		String currPlayer = player.getName();
    		playerMap.put(currPlayer, 0);
    		
    	}
    }
    
    @CoverageIgnore
    public void tweetOut(String message) 
    {
  	  try {
		twitterInstance.updateStatus(message);
  	  } catch (TwitterException e) {
		e.printStackTrace();
  	  }
  			
    }
    
    @CoverageIgnore
    public void makeTwitterConnection() {
    	Properties keys = grabApiProps();
    	AccessToken accessToken = new AccessToken(
    			keys.getProperty("twitAccess"),
    			keys.getProperty("twitAccessSecret"));
    			
    	twitterInstance = TwitterFactory.getSingleton();
    	twitterInstance.setOAuthConsumer(
    			keys.getProperty("twitConsumer"),
    			keys.getProperty("twitConsumerSecret"));
    	
    	twitterInstance.setOAuthAccessToken(accessToken);
    	
    }
    
    @CoverageIgnore
    public Properties grabApiProps() {
    	Properties sysProp = new Properties();
    	FileInputStream in;
		try {
			in = new FileInputStream("secret_JCK_.prop");
			sysProp.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return sysProp;
    	
    }
	
}
