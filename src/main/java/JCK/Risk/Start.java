package JCK.Risk;

import java.io.IOException;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import JCK.Risk.Gameplay.Card;
import JCK.Risk.Gameplay.Game;
import JCK.Risk.Gameplay.Turns;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Start {	
	
	static String consumerKeyStr = "VZe14TMsVqwjhH6qVmWAAT9x8";
	static String consumerSecretStr = "0JWgAeWTL4A2aYSkG3iILe7xnCXHfThlCSmTlp3NRoz9sAnllH";
	static String accessTokenStr = "727691720939298816-WbCZKP32aWY1me35bseLJLBji1sq2aK";
	static String accessTokenSecretStr = "r1dHS7dVlQ0NF4rLQCluGMlza0zRO2GVmTqToRA2EZ2g0";
	
	
	@CoverageIgnore
	
	public static void main(String[] args) //throws IOException, InterruptedException, TelegramApiException {
		
	{
		try {
			Twitter twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
			AccessToken accessToken = new AccessToken(accessTokenStr,
					accessTokenSecretStr);

			twitter.setOAuthAccessToken(accessToken);

			twitter.updateStatus("twitter dev");

			System.out.println("Successfully updated the status in Twitter.");
		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}
}
		
		/*
		ApiContextInitializer.init();
		TelegramBotsApi botsApi = new TelegramBotsApi();
		botsApi.registerBot(new TelegramGameBot());

		TelegramGameBot bot = new TelegramGameBot();
		
		bot.sendMessageToChat("Hello and welcome to Risk");
		String response = bot.sendMessageGetResponse("How many players will be playing today?");
		
		//TODO, get the bot to only accept values from 1 to 6
		int numPlayers = Integer.parseInt(response);
				
		Game startGame = new Game();
		Card cards = new Card();
		// initialize the players as well as continents
		startGame.initializeGame(numPlayers, bot);
		startGame.initializeContinents();
        

		
		
		cards.initializeCards(bot);
		
		// initializes the territories at the start by assigning them to each player based on choice
		startGame.chooseInitialTerritories();

		Turns turns = new Turns(startGame, bot);
		
		
	}
*/

