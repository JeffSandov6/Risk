package JCK.Risk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import JCK.Risk.Gameplay.Card;
import JCK.Risk.Gameplay.Game;
import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;

public class Start {

	public static void main(String[] args) throws IOException {
		
		
		
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
		for (int i = 0; i < continentArray.size(); i++) {
			continentArray.get(i).displayContinent();
		}
		cards.initializeCards();
		
	}

}
