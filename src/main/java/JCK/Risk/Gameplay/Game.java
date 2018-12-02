package JCK.Risk.Gameplay;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Random;

import JCK.Risk.CoverageIgnore;
import JCK.Risk.TelegramGameBot;
import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;

public class Game {
	
	private int numberOfPlayers;
	private TelegramGameBot bot;
	
	//this is the array list that will be used for the turns
	//TODO: this should be in the class of turns
	private ArrayList<Player> playersArray;
	private ArrayList<Continent> continentArray;
	
	public Game() {
		playersArray = new ArrayList<Player>();
		continentArray = new ArrayList<Continent>();
		numberOfPlayers = 0;
	}
	

	@CoverageIgnore
	public void initializeGame(int numPlayers, TelegramGameBot bot) throws InterruptedException, TelegramApiException 
	{
		this.bot = bot;
		this.numberOfPlayers = numPlayers;

		Random randNumber = new Random();
		
		//this loop will initialize the players
		for(int i = 1; i <= numPlayers; i++)
		{
			
			String playerName = bot.sendMessageGetResponse("What's the name of player #" + i + "?");
			
			int turnValue = randNumber.nextInt(1000); //random number from 0 to 999
			Player newPlayer = new Player();
			newPlayer.createPlayer(playerName, turnValue);
			
			playersArray.add(newPlayer);
			//add this new player to the array
			//then we're going to sort the array based on that value
		}
		
		//sort the array here by the turnValue
		Collections.sort(playersArray, Game.COMPARE_BY_VALUE);
		bot.sendMessageToChat("The following is the turn order:");
		
		for(int i = 0; i < playersArray.size(); i++)
		{
			playersArray.get(i).setRollValue(i+1); //sets the roll value if we need to set it
			bot.sendMessageToChat((i + 1) + "." + playersArray.get(i).getName());
//			System.out.println((i + 1) + ". " + playersArray.get(i).name);
		}
	}
	
	
	
	public ArrayList<Player> getPlayersArray() {
		
		return this.playersArray;
	}
	
	public void setPlayersArray(ArrayList<Player> playersArray) {
		this.playersArray = playersArray;
	}
	
	public ArrayList<Continent> getContinentArray() {
		return this.continentArray;
	}
	
	public void setContinentArray(ArrayList<Continent> continentArray) {
		this.continentArray = continentArray;
	}
	
	public static Comparator<Player> COMPARE_BY_VALUE = new Comparator<Player>() {
		public int compare(Player one, Player other) {

			return one.getTurnValue().compareTo(other.getTurnValue());
		}
		
	};
		
	/**
	* Initializes the continents by reading a .txt file and scanning through it
	*/
	@CoverageIgnore
	public void initializeContinents()
	{
		try {
			FileReader file = new FileReader("src/main/java/JCK/Risk/listOfTerritories.txt");
			BufferedReader br = new BufferedReader(file);
			String line;
			// begins reading the file
			while ((line = br.readLine()) != null) {
				int indexOfContinent = line.indexOf('-');
				String continentName = line.substring(0, indexOfContinent);
				line = line.substring(indexOfContinent+1);
				int indexOfContinentValue = line.indexOf(':');
				int continentValue = Integer.parseInt(line.substring(0, indexOfContinentValue));
				Continent newContinent = new Continent(continentName, continentValue);
				line = line.substring(indexOfContinentValue+1);
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
	
	
	
	/*
	* Initializes all the territories by giving each player in the player array a territory and assigning 1 soldier to each territory.
	*/
	@CoverageIgnore
	public void chooseInitialTerritories() throws IOException, InterruptedException, TelegramApiException {
		/**
		 * I want to initialize all territories by giving each player in player array a turn to add pieces onto
		 * a territory that is unowned
		 */
		// first I want to iterate through the player array each iteration of while loop represents a turn
		int playerTurnCount = 0;
		
		while (!areTerritoriesFilled()) {
			displayWorld();
			String userInput;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println(playersArray.get(playerTurnCount).getName() + ", enter a territory you'd like to control: ");
			bot.sendMessageToChat(playersArray.get(playerTurnCount).getName() + " enter a territory you'd like to control:");
			
			
			// keeps checking if the userinput is valid; if it is valid then ends the loop; otherwise keeps asking for a valid territory
//			while (!isEmptyTerritory(userInput = br.readLine())) {
//				System.out.println(playersArray.get(playerTurnCount).getName() + ", enter a valid territory: ");
//				
//			}
			while (!isEmptyTerritory(userInput = bot.sendMessageGetResponse(""))) {
				//System.out.println(playersArray.get(playerTurnCount).getName() + ", enter a valid territory: ");
				bot.sendMessageToChat(playersArray.get(playerTurnCount).getName() + ", enter a valid territory: ");

				
			}
			// assigns the territories if it passes the condition that it is a valid territory
			assignTerritory(playersArray.get(playerTurnCount), userInput, 1);
			playersArray.get(playerTurnCount).addTerritoryOwned(userInput);
			playerTurnCount++;
			playerTurnCount %= playersArray.size();
		}

		
		//AFTER HERE, WE STILL USE THE SAME PLAYERTURNCOUNT VARIABLE
		
		//TODO: AFTER WE RUN OUT OF TERRITORIES, WE START TO 
		//REINFORCE MORE UNITS TO ALREADY OWNED TERRITORIES

	}



	
	//Initialize the territories adjacency list
	@CoverageIgnore
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

	
	//Display all territories as well as their continents
	public void displayWorld() {
		for (int i = 0; i < continentArray.size(); i++) {
			continentArray.get(i).displayContinent(bot);
		}
	}

	
	//Given a territoryName checks if the territory is valid for unit placement at the start of the game
	public boolean isEmptyTerritory(String territoryName) {
		for (int i = 0; i < continentArray.size(); i++) {
			if (continentArray.get(i).getTerritory(territoryName) != null) {
				if (continentArray.get(i).getTerritory(territoryName).getOwner() == "nobody") {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	//Assigns territories the number of soldiers based on the player name :: should only be used for initialization
	public void assignTerritory(Player player, String territoryName, int numSoldiers) {
		for (int i = 0; i < continentArray.size(); i++) {
			if (continentArray.get(i).getTerritory(territoryName) != null) {
				if (continentArray.get(i).getTerritory(territoryName).getOwner() == "nobody") {
					Territory territory = continentArray.get(i).getTerritory(territoryName);
					territory.setOwner(player.getName());
					territory.addSoldiers(numSoldiers);
					continentArray.get(i).getListOfTerritories().put(territoryName, territory);
				} else {
					bot.sendMessageToChat("Territory name: " + territoryName + " is invalid.");
				}
			}
		}
	}
	
	//Checks if all of the territories are taken by players.
	public boolean areTerritoriesFilled() {
		for (int i = 0; i < continentArray.size(); i++) {
			Continent continent = continentArray.get(i);
			HashMap<String, Territory> listOfTerritories = continent.getListOfTerritories();
			for (String territoryName : listOfTerritories.keySet()) {
				if (listOfTerritories.get(territoryName).getOwner() == "nobody") {
					return false;
				}
			}
		}
		return true;
	}
}
