package JCK.Risk.Gameplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import JCK.Risk.Players.Player;

public class Game {
	
	int numberOfPlayers;
	
	//this is the array list that will be used for the turns
	//TODO: this should be in the class of turns
	ArrayList<Player> playersArray = new ArrayList<Player>();
	

	
	public void initializeGame(int numPlayers) throws IOException 
	{
		
		this.numberOfPlayers = numPlayers;
		
		

		//XXX this is what we're gonna use for the die values
		//Random die1 = new Random(numPlayers);
		//System.out.println(die1.nextInt(6));

		Random randNumber = new Random();
		
		//this loop will initialize the players
		for(int i = 1; i <= numPlayers; i++)
		{
			
			
			
			
			System.out.println("What's the name of player #" + i + "?");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			String playerName = br.readLine();
			
			
			int turnValue = randNumber.nextInt(1000); //random number from 0 to 999


			
			
			Player newPlayer = new Player();
			
			newPlayer.createPlayer(playerName, turnValue);
			
			
			playersArray.add(newPlayer);
			
			//add this new player to the array
			//then we're going to sort the array based on that value
			
			
			
		}
		
		//sort the array here by the turnValue
		Collections.sort(playersArray, Game.COMPARE_BY_VALUE);
		
		
		
		
		System.out.println("The following is turn order:");
		
		//TODO do we need to set the roll value like below?
		for(int i = 0; i < playersArray.size(); i++)
		{
			playersArray.get(i).rollValue = i + 1; //sets the roll value if we need to set it
			
			//but as of now a circular array type of setting for turns is enough
			System.out.println((i + 1) + ". " + playersArray.get(i).name);
			
		}
		
		
	}
	
	
	
	public ArrayList<Player> getPlayersArray() {
		
		return this.playersArray;
	}
	
	
	
	public static Comparator<Player> COMPARE_BY_VALUE = new Comparator<Player>() {
		public int compare(Player one, Player other) {
			
			
			
			return one.turnValue.compareTo(other.turnValue);
		}
		
	};
		
	
	
	
	
	public void initializeContinents()
	{
		//Continent southAmerica = new southAmerica();
		
	}
	
	

}
