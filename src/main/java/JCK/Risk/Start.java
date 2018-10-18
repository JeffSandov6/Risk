package JCK.Risk;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import JCK.Risk.Gameplay.Card;
import JCK.Risk.Gameplay.Game;
import JCK.Risk.Gameplay.Turns;
import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import JCK.Risk.Gameplay.Card;
import JCK.Risk.Gameplay.Game;
import JCK.Risk.Gameplay.Turns;
import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;
public class Start {
	

	//private static PrintStream consolePrint;
	public static void main(String[] args) throws IOException, TwitterException {
		//System.setProperty("java.net.useSystemProxies", "true");

		/*ConfigurationBuilder ConfigurationBuilder = new ConfigurationBuilder();
		ConfigurationBuilder.setDebugEnabled(true).setOAuthConsumerKey("VZe14TMsVqwjhH6qVmWAAT9x8")
		.setOAuthConsumerSecret("0JWgAeWTL4A2aYSkG3iILe7xnCXHfThlCSmTlp3NRoz9sAnllH")
		.setOAuthAccessToken("727691720939298816-WbCZKP32aWY1me35bseLJLBji1sq2aK")
		.setOAuthAccessTokenSecret("r1dHS7dVlQ0NF4rLQCluGMlza0zRO2GVmTqToRA2EZ2g0");
		*/
		//System.setProperty("java.net.useSystemProxies", "true");
/*
		TwitterFactory tf = new TwitterFactory(ConfigurationBuilder.build());
		twitter4j.Twitter twitter = tf.getInstance();
	   
		List<Status>status = twitter.getHomeTimeline();
		for(Status s : status) {
			System.out.println(s.getUser().getName() +"    "+ s.getText());
		}	
	
		
		Scanner input = new Scanner(System.in);
		twitterer tObject = new twitterer(consolePrint);
		tObject.tweetOut("Testing twitter4j API for Java");
		*/
		twitterer bigBird = new twitterer(consolePrint);		
		String message = "Testing twitter4j API for Java";
		bigBird.tweetOut(message);
		System.out.println("Hello, and welcome to Risk!");
		System.out.println("How many players will be playing today?");
		
		//TODO, get the bufferedreader to only accept values from 1 to 6
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int numPlayers = Integer.parseInt(br.readLine());
		
		Game startGame = new Game();
		Card cards = new Card();
		
		// initialize the players as well as continents
		startGame.initializeGame(numPlayers);
		startGame.initializeContinents();
		
		ArrayList<Player> playersArray = startGame.getPlayersArray();
		ArrayList<Continent> continentArray = startGame.getContinentArray();
		
		System.out.println();
		
		cards.initializeCards();
		
		// initializes the territories at the start by assigning them to each player based on choice
		startGame.chooseInitialTerritories();
		
		/*
		Territory Siberia = continentArray.get(4).getTerritory("Siberia");
		Territory India = continentArray.get(4).getTerritory("India");
		
		Siberia.numSoldiersHere = 12;
		India.numSoldiersHere = 15;
		
		Siberia.setOwner("john");
		India.setOwner("Jeff");
		*/
		Turns turns = new Turns(startGame);
		
		//turns.beginBattle(Siberia, India);
		
}
}
