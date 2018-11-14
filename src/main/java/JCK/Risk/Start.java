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

import JCK.Risk.Gameplay.Card;
import JCK.Risk.Gameplay.Game;
import JCK.Risk.Gameplay.Turns;
import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;

public class Start {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		

//		ApiContextInitializer.init();
//		
//		//instantiate telegram bots API
//		TelegramBotsApi botsApi = new TelegramBotsApi();
//		
//		//register our bot
//		try {
//			botsApi.registerBot(new TelegramBotTest());
//			
//		} 
//		catch (TelegramApiException e)
//		{
//			System.out.println("Bot was not able to be registered");
//			e.printStackTrace();
//		}
//		
//		
//		TelegramBotTest test = new TelegramBotTest();
//		test.sendMessageToChat("Hello and welcome to Risk");
//		test.sendMessageToChat("How many players will be playing today?");
//		
//		
//		Thread.sleep(30000);
//		
//		String response = test.getResponse();
//				
		
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

		Turns turns = new Turns(startGame);
		
		//turns.beginBattle(Siberia, India);
		
	}

}
