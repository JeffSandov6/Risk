package JCK.Risk;

       // jar found at http://twitter4j.org/en/index.html
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterException;

import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;

public class twitterer
   {
      private Twitter twitter;
      private PrintStream consolePrint;
      private List<Status> statuses;

     
     
      public twitterer(PrintStream console)
      {
         // Makes an instance of Twitter - this is re-useable and thread safe.
         // Connects to Twitter and performs authorizations.
         twitter = TwitterFactory.getSingleton(); 
         consolePrint = console;
         statuses = new ArrayList<Status>();
      }
   
     
      
      public void tweetOut(String message) throws TwitterException, IOException
      {
    	  Status status = twitter.updateStatus(message);
    	  System.out.print("updated status to: " + status.getText());
    			
      }
   }
   
      
   
    