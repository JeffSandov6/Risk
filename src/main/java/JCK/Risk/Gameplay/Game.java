package JCK.Risk.Gameplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import JCK.Risk.Players.Player;

public class Game {
	
	int numberOfPlayers;
	
	//this is the array list that will be used for the turns
	//TODO: this should be in the class of turns
	ArrayList<Player> circularPlayersArray = new ArrayList<Player>();
	
	public void initializeGame(int numPlayers) throws IOException 
	{
		
		this.numberOfPlayers = numPlayers;
		
		//this loop will initialize the players
		for(int i = 0; i < numPlayers; i++)
		{
			
			
			
			System.out.println("What's the name of player #" + i + "?");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			String playerName = br.readLine();
			
			
			
			//TODO: here we need to roll the dice
			//int rollValue = Dice.getTurns();
			int rollValue = 0;
			System.out.println("Your turn number is");
			
			
			
			
			Player newPlayer = new Player();
			
			newPlayer.createPlayer(playerName, rollValue);
			
		}
		
		
	}
	
	
	
	
	
	public void initializeContinents()
	{
		//Continent southAmerica = new southAmerica();
		
	}
	
	

}
