package JCK.Risk.Gameplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;

public class Turns {
	public Turns(Game game) {
		int playerTurnCount = 0;
		while (game.getPlayersArray().size() != 1) {
			Player player = game.getPlayersArray().get(playerTurnCount);
			System.out.println(playerTurnCount);
			System.out.println(player.getName());
			getNewSoldiers(player, game.getContinentArray());
			placeNewSoldiers(game, player);
			playerTurnCount++;
			playerTurnCount %= game.getPlayersArray().size();
		}
	}
	
	/**
	 * Method used to get the amount of soldiers that the player gets every turns based on
	 * the amount of territories and continents the player owns
	 * @param player
	 * @param continentArray
	 */
	public void getNewSoldiers(Player player, List<Continent> continentArray) {
		player.setSoldiersToPlace(player.getSoldiersToPlace() + (int) player.getTerritoriesOwned().size()/3);
		for (int i = 0; i < continentArray.size(); i++) {
			if (continentArray.get(i).playerOwnsContinent(player)) {
				player.setSoldiersToPlace(player.getSoldiersToPlace() + continentArray.get(i).getContinentValue());
			}
		}
	}
	/**
	 * Places the soldiers that the player has onto territories that the player owns
	 * @param game
	 * @param player
	 */
	public void placeNewSoldiers(Game game, Player player) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<Continent> continentArray = game.getContinentArray();
		while (player.getSoldiersToPlace() > 0) {
			try {
				// asks the user which territory to place soldier onto
				game.displayWorld();
				System.out.println(player.getName() + ", you have " + player.getSoldiersToPlace() + " soldiers to place. Choose a territory you own to place: ");
				String userInput = br.readLine();
				while (!playerOwnsTerritory(userInput, player, continentArray)) {
					System.out.println("Please enter a territory you own: ");
					userInput = br.readLine();
				}
				
				// asks the user how many soldier to place on the territory defined in the variable userInput
				System.out.println("How many soldier do you want to place on " + userInput + "?");
				int soldiersToPlace = Integer.parseInt(br.readLine());
				while (soldiersToPlace > player.getSoldiersToPlace() || soldiersToPlace < 0) {
					System.out.println("Enter an amount less than or equal to the amount you have to place.");
					soldiersToPlace = Integer.parseInt(br.readLine());
				}
				
				// places the amount of soldiers onto the territory the user defines
				for (int i = 0; i < continentArray.size(); i++) {
					if (continentArray.get(i).getTerritory(userInput) != null) {
						HashMap<String, Territory> listOfTerritories = continentArray.get(i).getListOfTerritories();
						listOfTerritories.put(userInput, listOfTerritories.get(userInput).addSoldiers(soldiersToPlace));
					}
				}
				
				// subtracts the number of soldiers the player can place by the number of soldiers just placed
				player.setSoldiersToPlace(player.getSoldiersToPlace() - soldiersToPlace);
			} catch (IOException e) {
				System.out.println("You have entered an invalid input. Please try again.");
			}
		}
	}
	
	/**
	 * Checks if the player owns all of the territories in the continen
	 * @param userInput
	 * @param player
	 * @param game
	 * @return
	 */
	public boolean playerOwnsTerritory(String userInput, Player player, List<Continent> continentArray) {
		for (int i = 0; i < continentArray.size(); i++) {
			if (continentArray.get(i).getTerritory(userInput) != null) {
				if (player.getName().equals(continentArray.get(i).getTerritory(userInput).getOwner())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void beginBattle(Territory defendingTerr, Territory attackingTerr)
	{
		String attackerName = attackingTerr.getOwner();
		String defenderName = defendingTerr.getOwner();
		
		//int maxAttackingSoldiers = attackingTerr.numSoldiersHere - 1;
		//int maxDefendingSoldiers = defendingTerr.numSoldiersHere - 1;
		
		while(attackingTerr.numSoldiersHere != 1 || defendingTerr.numSoldiersHere != 0)
		{
			int attackingSoldiers = this.numAttackers(attackingTerr);
			int defendingSoldiers = this.numDefenders(defendingTerr);
			
			System.out.println(attackerName + " your rolls are "); 
			Integer[] attackerRolls = this.getRollsSorted(attackingSoldiers);
			
			System.out.println(defenderName + " your rolls are "); 
			Integer[] defenderRolls = this.getRollsSorted(defendingSoldiers);
			
			//TODO: battle sequence
			
			int i = 0;
			
			System.out.println("The results are:");
			while(i < defenderRolls.length && i < attackerRolls.length)
			{
				System.out.print("The winner for the roll " + (i+1)  + " is ");
				
				if(attackerRolls[i] > defenderRolls[i])
				{
					System.out.println(attackerName);
					defendingTerr.numSoldiersHere -= 1;
				}
				else
				{
					System.out.println(defenderName);
					attackingTerr.numSoldiersHere -= 1;

				}
				
				i++;
				
			}
			
			System.out.println(attackerName + " remaining soldiers are " + attackingTerr.numSoldiersHere);
			System.out.println(defenderName + " remaining soldiers are " + defendingTerr.numSoldiersHere);
			
			if(attackingTerr.numSoldiersHere == 1 || defendingTerr.numSoldiersHere == 0)
			{
				break;
			}
		}
		
		
		System.out.print("The winner of this battle is: ");
		if(attackingTerr.numSoldiersHere == 1)
		{
			System.out.println(defenderName);
		}
		else
		{
			System.out.println(attackerName);
			System.out.println(defendingTerr.territoryName + " is now yours");
		}
		

	}
	
	
	public Integer[] getRollsSorted(int numberOfRolls)
	{
		Integer[] rollsArray = new Integer[numberOfRolls];
		
		for(int i = 0; i < rollsArray.length; i++)
		{
			rollsArray[i] = Dice.roll();
			System.out.println(rollsArray[i]);
			
		}
		
		
		Arrays.sort(rollsArray, Collections.reverseOrder());
		
		return rollsArray;
	}
	
	public int numAttackers(Territory attackingTerr)
	{
		if(attackingTerr.numSoldiersHere > 3)
		{
			return 3;
		}
		else if(attackingTerr.numSoldiersHere == 3)
		{
			return 2;
		}
		else  //if == 2
		{
			return 1;
		}
		
	}
	
	public int numDefenders(Territory defendingTerr)
	{
		if(defendingTerr.numSoldiersHere >= 2)
		{
			return 2;
		}
		else
		{
			return 1;
		}
		
	}
	
	public void attackTerritory(String territoryName, Continent currContinent)
	{
		Territory terrBeingAttacked = currContinent.getTerritory(territoryName);
		
		
	}
}
