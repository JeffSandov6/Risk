package JCK.Risk.Gameplay;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;

public class Game {
	
	int numberOfPlayers;
	
	//this is the array list that will be used for the turns
	//TODO: this should be in the class of turns
	ArrayList<Player> playersArray = new ArrayList<Player>();
	ArrayList<Continent> continentArray = new ArrayList<Continent>();

	
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
	
	public ArrayList<Continent> getContinentArray() {
		return this.continentArray;
	}
	
	public static Comparator<Player> COMPARE_BY_VALUE = new Comparator<Player>() {
		public int compare(Player one, Player other) {
			
			
			
			return one.turnValue.compareTo(other.turnValue);
		}
		
	};
		
	/**
	* Initializes the continents by reading a .txt file and scanning through it
	*/
	public void initializeContinents()
	{
		try {
			FileReader file = new FileReader("src/main/java/JCK/Risk/listOfTerritories.txt");
			BufferedReader br = new BufferedReader(file);
			String line;
			// begins reading the file
			while ((line = br.readLine()) != null) {
				int indexOfContinent = line.indexOf(':');
				String continentName = line.substring(0, indexOfContinent);
				Continent newContinent = new Continent(continentName);
				line = line.substring(indexOfContinent+1);
				int indexOfComma = line.indexOf(',');
				
				while (indexOfComma != -1) {
					String territoryName = line.substring(0, indexOfComma);
					newContinent.addTerritory(territoryName, initializeAdjacencies(territoryName));
					line = line.substring(indexOfComma + 1);
					indexOfComma = line.indexOf(',');
				}
				// gets the last element from the line
				String territoryName = line;
				newContinent.addTerritory(territoryName, initializeAdjacencies(territoryName));
				continentArray.add(newContinent);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private Object initializeTerritories(String territoryName) {
		// TODO Auto-generated method stub
		return null;
	}



	/**
	* Initialize the territories adjacency list
	*/
	public List<String> initializeAdjacencies(String territoryName) {
		List<String> adjacentTerritories = new ArrayList<String>();
		try {
			// reads the file and scans through looking for ':' for current territory
			FileReader file = new FileReader("src/main/java/JCK/Risk/adjacencies.txt");
			BufferedReader br = new BufferedReader(file);
			String line;
			
			while ((line = br.readLine()) != null) {
				int indexOfTerritory = line.indexOf(':');
				String newTerritoryName = line.substring(0, indexOfTerritory);
				line = line.substring(indexOfTerritory+1);
				int indexOfComma = line.indexOf(',');
				// checks for each of the commas to get the adjacencies
				if (newTerritoryName.equals(territoryName)) {
					while (indexOfComma != -1) {
						String adjacentTerritory = line.substring(0, indexOfComma);
						adjacentTerritories.add(adjacentTerritory);
						line = line.substring(indexOfComma+1);
						indexOfComma = line.indexOf(',');
					}
					
					String adjacentTerritory = line;
					adjacentTerritories.add(adjacentTerritory);
					return adjacentTerritories;
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return adjacentTerritories;
	}
}
