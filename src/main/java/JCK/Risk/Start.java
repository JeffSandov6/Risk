package JCK.Risk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import JCK.Risk.Gameplay.Card;
import JCK.Risk.Gameplay.Game;
import JCK.Risk.Gameplay.Turns;
import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;

public class Start {

	public static void main(String[] args) throws IOException, InterruptedException, TelegramApiException {
		
		
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
		System.out.println("checkPoint");
		// initialize the players as well as continents
		startGame.initializeGame(numPlayers, bot);
		startGame.initializeContinents();
		
		ArrayList<Player> playersArray = startGame.getPlayersArray();
		ArrayList<Continent> continentArray = startGame.getContinentArray();
		
		System.out.println();
		
		cards.initializeCards();
		
		// initializes the territories at the start by assigning them to each player based on choice
		startGame.chooseInitialTerritories();

		Turns turns = new Turns(startGame);
		
		
	}

}
