package JCK.Risk;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken; 
import java.io.PrintStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class twitterer {
	private Twitter twitter;
	private PrintStream consolePrint;
	private List<Status>statuses;
	
	public twitterer(PrintStream console)
	{
		twitter = TwitterFactory.getSingleton();
		consolePrint = console;
		statuses = new ArrayList<Status>();
	}
	
	public void tweetOut(String message) throws TwitterException, IOException
	{
		Status status = twitter.updateStatus(message);
		System.out.println(status.getText());
	}
	
	

}
