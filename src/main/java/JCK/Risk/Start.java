package JCK.Risk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import JCK.Risk.Gameplay.Card;
import JCK.Risk.Gameplay.Game;
import JCK.Risk.Gameplay.Turns;
import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;

public class Start {

	public static void main(String[] args) throws IOException {
		
		
//		Player player = new Player();
//		player.name = "Jeff";
//		
//		ArrayList<String> cards1 = new ArrayList<String>();
//		
//		cards1.add("a");
//		cards1.add("b");
//		cards1.add("c");
//		cards1.add("a");
//
//
//		
//		player.listOfCards = cards1;
//		
//		Collections.sort(cards1);
		
		Card tempCard = new Card();

		
//		int newUnits = tempCard.checkCards(player);
//		
//		System.out.println(cards1);
//		
//		System.out.println(player.listOfCards);

		
		int temp = 1;
		
		while(temp < 10)
		{
			System.out.print("On set " + temp + " you gain ");
			System.out.println(tempCard.getNextSetValue() + "units ");
			tempCard.setsTurnedIn++;
			
			temp++;
		}
		
		
		
		
		
		
		
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
