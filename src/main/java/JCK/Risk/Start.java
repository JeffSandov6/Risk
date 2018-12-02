package JCK.Risk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import JCK.Risk.Extras.AmazonS3Replay;
import JCK.Risk.Gameplay.Card;
import JCK.Risk.Gameplay.Game;
import JCK.Risk.Gameplay.Turns;

public class Start {
	
	@CoverageIgnore
	public static void main(String[] args) throws InterruptedException, TelegramApiException, IOException {		
		
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

}
