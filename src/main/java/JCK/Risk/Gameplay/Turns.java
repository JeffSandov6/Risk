package JCK.Risk.Gameplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import JCK.Risk.CoverageIgnore;
import JCK.Risk.Extras.Subject;
import JCK.Risk.Extras.TelegramGameBot;
import JCK.Risk.Extras.Twitterer;
import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;

//TODO:
//Observer class
//80% test coverage

public class Turns implements Subject {
	
	private Card cards = new Card();
	private TelegramGameBot bot;
	private boolean skip = false;
	private Twitterer twitterInstance;
	private int battlesWon;

	
	@CoverageIgnore
	public Turns(TelegramGameBot bot) {
		this.bot = bot;
	}
	
	@Override
	public void notifyObservers(String playerName, ArrayList<Player> players) {
		
		for(Player player : players) {
			player.update(playerName);
		}
	}
	
	@CoverageIgnore
	public Turns(Game game, TelegramGameBot bot) throws IOException, InterruptedException, TelegramApiException {
		this.bot = bot;
		int playerTurnCount = 0;
		bot.sendMessageToChat("initialized the turns");
		int additionalUnits = 0;
		
		twitterInstance.grabPlayerInfo(game.getPlayersArray());
		
		while (game.getPlayersArray().size() != 1) {
			battlesWon = 0;
			additionalUnits = 0;

			skip = false;
			
			Undo undo = new Undo(game.getPlayersArray(), game.getContinentArray());

			Player player = game.getPlayersArray().get(playerTurnCount);
			
			bot.sendMessageToChat("It is " + player.getName() + "'s turn.");
			bot.sendMessageToChat("PURCHASE PHASE");
			// first ask if the user wants to purchase credits
			purchasePhase(player, game);
			if(skip == true) {
				bot.sendMessageToChat("user timeout, we will skip to the next player's turn");
				continue;
			}
			
			
			//begin card phase
			bot.sendMessageToChat("CARD PHASE");
			bot.sendMessageToChat("The next set turned in will grant " + cards.getNextSetValue() + "additional units");
			additionalUnits = cards.checkCards(player);
			
			// place new soldiers phase
			bot.sendMessageToChat("PLACE  NEW ARMIES PHASE");
			additionalUnits+= getExtraArmiesForContinentsOwned(player, game.getContinentArray());
			additionalUnits+= getExtraArmiesForTerrsOwned(player);
			
			placeNewSoldiers(game, player, additionalUnits);
			if(skip == true) {
				bot.sendMessageToChat("user timeout, we will skip to the next player's turn");
				continue;
			}
			
			
			// start battle phase
			bot.sendMessageToChat("BATTLE PHASE");
			boolean playerWonABattle = attackingPhaseFor(player, game.getContinentArray(), game);

			playerTurnCount = playerTurnCount + numLeftSidePlayersEliminated(game, player);
			
			if(playerWonABattle == true)
			{
				String cardWon = cards.getCard();
				bot.sendMessageToChat("For winning at least 1 battle, you win card of type " + cardWon);
				player.addCardToList(cardWon);
			}
			
			if(skip == true) {
				bot.sendMessageToChat("user timeout, we will skip to the next player's turn");
				continue;
			}
			
			twitterInstance.postNumberTerrsConquered(player.getName(), battlesWon);
			
			
			// fortify territories phase
			bot.sendMessageToChat("FORTIFY PHASE");
			fortifyTerritory(player, game.getContinentArray());
			
			if(skip == true) {
				bot.sendMessageToChat("user timeout, we will skip to the next player's turn");
				continue;
			}
			
			
			// if the player chooses to undo their turn -- returns to the beginning of the loop without
			// finishing loop
			if (undoTurn(game, undo, player)) {
				continue;
			}
			
			playerTurnCount++;
			playerTurnCount %= game.getPlayersArray().size();
			
			bot.sendMessageToChat("END OF TURN");
		}
		
		twitterInstance.postFinalTweet();
		bot.sendMessageToChat("THE WINNER OF THE GAME IS " + game.getPlayersArray().get(0).getName());
		bot.gameFinished();
		
		
	}
	
	
	public boolean checkIfUserResponded(String userResponse) {
		
		if(Objects.equals(userResponse, "empty")) {
			skip = true;
			return false;
		}
		
		return true;
	}
	
	
	
	@CoverageIgnore
	private void purchasePhase(Player player, Game game) throws InterruptedException, TelegramApiException {
		bot.sendMessageToChat("Would you like to purchase credits?");
		bot.sendMessageToChat("You currently have " + player.getCurrentCredit() + " credits.");
		String userInput = bot.sendMessageGetResponse("Please answer 'yes' or 'no'");

		if (!checkIfUserResponded(userInput)) {
			return;
		}
		
		if (userInput.toLowerCase().equals("yes")) {
			// ask how many credits they want to buy
			userInput = bot.sendMessageGetResponse("How many credits would you like to buy?");
			player.addCredit(Integer.parseInt(userInput));
		} 
		
		// now ask if they want to purchase anything
		bot.sendMessageToChat(player.getName() + ", would you like to purchase any of the following with your credits?");
		bot.sendMessageToChat("You currently have " + player.getCurrentCredit() + " credits.");
		bot.sendMessageToChat("Action\t\tCost");
		bot.sendMessageToChat("Undo\t\t5");
		bot.sendMessageToChat("Card\t\t3");
		bot.sendMessageToChat("Transfer\t\t");
		userInput = bot.sendMessageGetResponse("None\t\t0");
		
		if (!checkIfUserResponded(userInput)) {
			return;
		}
		
		if (userInput.toLowerCase().equals("undo") && player.getCurrentCredit() >= 5) {
			player.purchaseUndoAction();
			player.useCredit(5);
		} else if (userInput.toLowerCase().equals("card") && player.getCurrentCredit() >= 3) {
			//ask what kind of card they want to purchase
			bot.sendMessageToChat("What type of card would you like out of the ones available?");
			bot.sendMessageToChat("Infantry - " + cards.getCardsArray().get(0));
			bot.sendMessageToChat("Cavalry - " + cards.getCardsArray().get(1));
			bot.sendMessageToChat("Artillery - " + cards.getCardsArray().get(2));
			userInput = bot.sendMessageGetResponse("Wild - " + cards.getCardsArray().get(3));
			
			if (!checkIfUserResponded(userInput)) {
				return;
			}
			
			int cardIndex = cards.getCardIndex(userInput.toLowerCase());
			
			// if the card inputted is invalid then just return
			if (cardIndex == -1) {
				return;
			}
			//if it is a good index, then check if the num of cards at the index is > 0
			int numberOfCardsAvailable = cards.getCardsArray().get(cardIndex);
			if (numberOfCardsAvailable > 0) {
				player.purchaseCard("userInput");
				player.useCredit(3);
				bot.sendMessageToChat("You have purchased a " + userInput + "card. Your credit is now at " + player.getCurrentCredit());
			} else {
				bot.sendMessageToChat("There are no " + userInput + " cards available.");
			}
		} else if (userInput.toLowerCase().equals("transfer")) {
			// checks the transfer case
			bot.sendMessageToChat("Who would you like to transfer the credit to?");
			List<Player> players = game.getPlayersArray();
			// list out possible players to transfer credit to
			for (Player otherPlayer : players) {
				if (otherPlayer.getName().equals(player.getName())) {
					continue;
				} else {
					bot.sendMessageToChat(otherPlayer.getName());
				}
			}
			userInput = bot.sendMessageGetResponse("Enter a username");
			
			if (!checkIfUserResponded(userInput)) {
				return;
			}
			
			for (Player otherPlayer : players) {
				//check the input to the otherPlayer to see if it matches
				// if it doesnt keep searching
				if (otherPlayer.getName().equals(player.getName())) {
					continue;
				} else {
					// if it does then ask how many credits to transfer
					userInput = bot.sendMessageGetResponse("How many credits would you like to transfer to " + otherPlayer.getName() + "?");
					
					if (!checkIfUserResponded(userInput)) {
						return;
					}
					
					int transferredCredit = Integer.parseInt(userInput);
					// check if the credits to be transferred are valid 
					if (transferredCredit < 0 || transferredCredit > player.getCurrentCredit()) {
						bot.sendMessageToChat("Invalid amount of credits entered.");
					} else {
						// if the transferredcredits are valid; i add the players credit onto the otherPlayers credit
						player.useCredit(transferredCredit);
						otherPlayer.addCredit(transferredCredit);
					}
					return;
					
				}
			}
		} else {
			return;
		}
	}
	/**
	 * prompts the player if they want to undo their action and returns true or false depending
	 * on whether they choose to undo or not
	 * @param game object
	 * @param undo instance of the class Undo
	 * @param player current player
	 * @return true or false, in regards to whether the player wants to undo or not
	 * @throws TelegramApiException 
	 * @throws InterruptedException 
	 */
	@CoverageIgnore
	public boolean undoTurn(Game game, Undo undo, Player player) throws InterruptedException, TelegramApiException {
		// checks whether or not the current player has an undo action available 
		bot.sendMessageToChat("You currently have " + player.getUndoActionsAvailable() + " undo actions available.");
		
		if (player.getUndoActionsAvailable() <= 0) {
			bot.sendMessageToChat("You cannot use any undo actions because you do not have any.");
			return false;
		}
		
		// keeps proceeding if the user has more than 0 undoActionsAvailable
		String userInput = bot.sendMessageGetResponse("Would you like to undo the last turn? Yes or no?").toLowerCase();
		if (!checkIfUserResponded(userInput)) {
			return false;
		}
		
		if (userInput.equals("yes")) {
			if (undo != null) {
				bot.sendMessageToChat("Undoing to the beginning of your turn.");
				
				//sets the continent array and players array to the previous state
				game.setContinentArray((ArrayList<Continent>) undo.getPastContinent());
				game.setPlayersArray((ArrayList<Player>) undo.getPastPlayers());
				player.useUndoAction();
				
				return true;
			} else {
				bot.sendMessageToChat("Cannot undo any turns because no turns have occurred.");
				return false;
			}
		}
		return false;
	}
	
	
	@CoverageIgnore
	private void fortifyTerritory(Player player, ArrayList<Continent> continentArray) throws IOException, TelegramApiException, InterruptedException
	{
		String userInput = bot.
				sendMessageGetResponse("You may choose to move units from 1 owned territory to another, type yes if you want to").
				toLowerCase();
		
		if (!checkIfUserResponded(userInput)) {
			return;
		}
		
		if(!Objects.equals(userInput, "yes"))
		{
			bot.sendMessageToChat("Okay, we have reached the end of the fortify phase");
			return;
		}
		
		displayPlayerTerrs(player, continentArray, "fortify");
		
		
		userInput = bot.sendMessageGetResponse("Enter the territory you want to move units from"
				+ " (has to have more than 1 unit, else we skip this phase)");
		if (!checkIfUserResponded(userInput)) {
			return;
		}

		while (!playerOwnsTerritory(userInput, player, continentArray)) {
			userInput = bot.sendMessageGetResponse("Please enter a territory you own: ");
			if (!checkIfUserResponded(userInput)) {
				return;
			}
		}
		
		Territory donatingTerr = getTerritoryObject(userInput, continentArray);
		
		if(donatingTerr.getSoldierCount() < 2)
		{
			bot.sendMessageToChat("There's only 1 unit here, we are skipping this phase");
			return;
		}
		
		
		String newInput = bot.sendMessageGetResponse("Choose the territory you want to move units to");
		if (!checkIfUserResponded(newInput)) {
			return;
		}
		
		while (!playerOwnsTerritory(newInput, player, continentArray) || ( Objects.equals(newInput, userInput) == true )) {
			
			newInput = bot.sendMessageGetResponse("Please enter a territory you own: ");
			if (!checkIfUserResponded(userInput)) {
				return;
			}			
		}
		
		Territory receivingTerr = getTerritoryObject(newInput, continentArray);
		migrateUnitsFromOldToNewTerritory(donatingTerr, receivingTerr);
		
		bot.sendMessageToChat("We have reached the end of the fortify phase");

	}
	
	
	//if a player to the left side of array is eliminated, it affects out player
	//turn count formula
	@CoverageIgnore
	private int numLeftSidePlayersEliminated(Game game, Player currentPlayersTurn)
	{
		ArrayList<Player> playersArray = game.getPlayersArray();
		int leftSideEliminated = 0;
		
		for(int i = 0; i < playersArray.size(); i++)
		{
			Player thisPlayer = playersArray.get(i);
			
			if(thisPlayer.getTerritoriesOwned().size() == 0)
			{
				if(thisPlayer.getRollValue() < currentPlayersTurn.getRollValue()) //if hes to the left of winning player
				{
					leftSideEliminated++;
				}
				bot.sendMessageToChat(thisPlayer.getName() + " has no more territories and has thus been eliminated");
				game.getPlayersArray().remove(i);
				
				//TODO: TRANSFER OVER THE CARDS TO THE WINNER PLAYER WHO ELIMINATED THEM
			}
		}
		return leftSideEliminated;
	}
	
	
	public int getExtraArmiesForContinentsOwned(Player player, ArrayList<Continent> continentArray)
	{
		int additionalUnits = 0;
		for(int i = 0; i < continentArray.size(); i++)
		{
			if (continentArray.get(i).playerOwnsContinent(player)) //if he owns it
			{
				additionalUnits = additionalUnits + continentArray.get(i).getContinentValue();
			}
		}
		return additionalUnits;
	}
	
	
	public int getExtraArmiesForTerrsOwned(Player player)
	{
		
		if(player.getTerritoriesOwned().size() <= 11)
		{
			return 3;
		}
		
		int additionalUnits = player.getTerritoriesOwned().size() / 3;
		
		return additionalUnits;
	}
	
	
//	/**
//	 * Places the soldiers that the player has onto territories that the player owns
//	 * @param game
//	 * @param player
//	 */
	@CoverageIgnore
	private void placeNewSoldiers(Game game, Player player, int numUnitsAvailable) throws InterruptedException, TelegramApiException {
		ArrayList<Continent> continentArray = game.getContinentArray();
		
		while (numUnitsAvailable > 0) {
			// asks the user which territory to place soldier onto
			game.displayWorld();
			String userInput = bot.sendMessageGetResponse(player.getName() + ", you have " + numUnitsAvailable + " soldiers to place. Choose a territory you own to place: ");
			
			if (!checkIfUserResponded(userInput)) {
				return;
			}
			
			while (!playerOwnsTerritory(userInput, player, continentArray)) {
				userInput = bot.sendMessageGetResponse("Please enter a territory you own: ");
				
				if (!checkIfUserResponded(userInput)) {
					return;
				}
			}
			
			// asks the user how many soldier to place on the territory defined in the variable userInput
			
			String numSoldierResponse = bot.sendMessageGetResponse("How many soldier do you want to place on " + userInput + "?");
			if (!checkIfUserResponded(numSoldierResponse)) {
				return;
			}
			
			int soldiersToPlace = Integer.parseInt(numSoldierResponse);
			
			while (soldiersToPlace > numUnitsAvailable || soldiersToPlace < 0) {
				
				numSoldierResponse = bot.sendMessageGetResponse("Enter an amount less than or equal to the amount you have to place.");
				if (!checkIfUserResponded(numSoldierResponse)) {
					return;
				}
				
				soldiersToPlace = Integer.parseInt(numSoldierResponse);
			}
			
			// places the amount of soldiers onto the territory the user defines
			for (int i = 0; i < continentArray.size(); i++) {
				if (continentArray.get(i).getTerritory(userInput) != null) {
					HashMap<String, Territory> listOfTerritories = continentArray.get(i).getListOfTerritories();
					listOfTerritories.put(userInput, listOfTerritories.get(userInput).addSoldiers(soldiersToPlace));
				}
			}
			// subtracts the number of soldiers the player can place by the number of soldiers just placed
			numUnitsAvailable = numUnitsAvailable - soldiersToPlace;
		}
	}
	
	
//	/**
//	 * Checks if the player owns all the territory
//	 * @param userInput
//	 * @param player
//	 * @param game
//	 * @return
//	 */
	public boolean playerOwnsTerritory(String userInput, Player player, ArrayList<Continent> continentArray) {
		for (int i = 0; i < continentArray.size(); i++) {
			if (continentArray.get(i).getTerritory(userInput) != null) {
				if (player.getName().equals(continentArray.get(i).getTerritory(userInput).getOwner())) {
					return true;
				}
			}
		}
		return false;
	}

	@CoverageIgnore
	private boolean attackingPhaseFor(Player player, ArrayList<Continent> continentArray, Game game) throws IOException, InterruptedException, TelegramApiException
	{
		boolean wonAtLeast1Battle = false;
		displayPlayerTerrs(player, continentArray, "attack");		
		String option = bot.sendMessageGetResponse("Do you want to make an attack, yes or no?").toLowerCase();
		
		if (!checkIfUserResponded(option)) {
			return wonAtLeast1Battle;
		}
				
		while(Objects.equals("yes", option))
		{
			String attackingTerr = bot.sendMessageGetResponse("Okay, type in the name of the territory you want to attack from");
			if (!checkIfUserResponded(attackingTerr)) {
				return wonAtLeast1Battle;
			}
			
			while (!playerOwnsTerritory(attackingTerr, player, continentArray)) {
				attackingTerr = bot.sendMessageGetResponse("Please enter a territory you own:");
				if (!checkIfUserResponded(attackingTerr)) {
					return wonAtLeast1Battle;
				}
			}
			
			Territory attackingTerritory = getTerritoryObject(attackingTerr, continentArray);
			
			if(attackingTerritory.getSoldierCount() < 2)
			{
				bot.sendMessageToChat("You don't have enough soldiers to attack from here, sorry!");
				option = bot.sendMessageGetResponse("Do you want to make another attack, yes or no?").toLowerCase();
				if (!checkIfUserResponded(option)) {
					return wonAtLeast1Battle;
				}
				continue;
			}
			
			
			bot.sendMessageToChat("The territories you can attack from here are");
			ArrayList<Territory> attackableAdjs = checkAttackableAdjacencies(player, attackingTerritory, continentArray);
			if( attackableAdjs.size() == 0)
			{
				bot.sendMessageToChat("NONE, YOU OWN THEM ALL!");
				option = bot.sendMessageGetResponse("Do you want to make another attack, yes or no?").toLowerCase();
				
				if (!checkIfUserResponded(option)) {
					return wonAtLeast1Battle;
				}
				continue;
			}
			
			
			String defendingTerr = bot.sendMessageGetResponse("Type the name of the territory you want to attack. If you don't want to attack one, type anything else.");
			Territory defendingTerritory = getTerritoryObject(defendingTerr, continentArray);
			
			if(Objects.equals(defendingTerritory, null))
			{
				bot.sendMessageToChat("Okay, you chose not to attack");
				option = bot.sendMessageGetResponse("Do you want to make another attack, yes or no?").toLowerCase();
				
				if (!checkIfUserResponded(option)) {
					return wonAtLeast1Battle;
				}
				continue;
			}
			
			String defTerrOwner = defendingTerritory.getOwner();
			bot.sendMessageToChat(defTerrOwner + ", your territory " + defendingTerr + " is being attacked!");
			this.notifyObservers(defTerrOwner, game.getPlayersArray());

			
			//reaching here means we have a valid defending & attacking terr
			String winnerOfBattle = beginBattle(defendingTerritory, attackingTerritory);
			
			if(Objects.equals(player.getName(), winnerOfBattle)) //if attacker won
			{
				battlesWon++;
				wonAtLeast1Battle = true;
				String losingDefender = defendingTerritory.getOwner();
			
				defendingTerritory.setOwner(player.getName());	
				player.addTerritoryOwned(defendingTerr);
				
				removeTerritoryFromDefender(losingDefender, defendingTerr, game);
				migrateUnitsFromOldToNewTerritory(attackingTerritory, defendingTerritory);
			}
			
			
			option = bot.sendMessageGetResponse("\nDo you want to make another attack, yes or no?").toLowerCase();
			if (!checkIfUserResponded(option)) {
				return wonAtLeast1Battle;
			}
		}

		bot.sendMessageToChat("Okay, the attack phase is over");
		return wonAtLeast1Battle;
	}
	
	
	
	public Territory getTerritoryObject(String territoryName, ArrayList<Continent> continentArray)
	{
		for(int i = 0; i < continentArray.size(); i++)
		{
			Territory currTerritory = continentArray.get(i).getTerritory(territoryName);
			
			if(currTerritory != null)
			{
				return currTerritory;
			}
		}
		
		return null;
	}
	
	
		
	public ArrayList<Territory> checkAttackableAdjacencies(Player player, Territory attackingTerr, ArrayList<Continent> continentArray)
	{
		List<String> adjacensies = attackingTerr.getAdjacencies();
		ArrayList<Territory> attackables = new ArrayList<Territory>();
		
		for(int i = 0; i < adjacensies.size(); i++)
		{
			Territory adjTerr = getTerritoryObject(adjacensies.get(i), continentArray);
			
			if(Objects.equals(adjTerr.getOwner(), player.getName()))  //if attacker owns it
			{
				continue;
			}
			
			bot.sendMessageToChat(adjTerr.getTerritoryName() + " which has " + adjTerr.getSoldierCount() + " soldier(s) on it");
			attackables.add(adjTerr);
			
		}
		return attackables;
	}
	
	@CoverageIgnore
	private void displayPlayerTerrs(Player player, ArrayList<Continent> continentArray, String phaseType)
	{
		bot.sendMessageToChat("THESE ARE THE TERRITORIES YOU OWN");
		for(int i = 0; i < player.getTerritoriesOwned().size(); i++)
		{
			String terrName = player.getTerritoriesOwned().get(i);
			bot.sendMessageToChat(i + ". " + terrName);
			
			for(int j = 0; j < continentArray.size(); j++)
			{
				Territory currTerritory = continentArray.get(j).getTerritory(terrName);
				
				if(currTerritory != null)
				{
					bot.sendMessageToChat("the number of soldiers you have here are " + currTerritory.getSoldierCount());
					if(Objects.equals(phaseType, "attack"))
					{
						bot.sendMessageToChat("These are the territories you can attack from here:");
						bot.sendMessageToChat(currTerritory.getAdjacencies().toString());
					}
					break;
				}
			}
		}
		
	}
	
	
	@CoverageIgnore
	private void migrateUnitsFromOldToNewTerritory(Territory donaterTerr, Territory receiverTerr) throws TelegramApiException, InterruptedException
	{
		int currNumSoldiers = donaterTerr.getSoldierCount();

		if(currNumSoldiers == 2)
		{
			bot.sendMessageToChat("You moved 1 soldier to your receiving territory");
		}
		
		
		bot.sendMessageToChat("You must reinforce your receiving territory with at least 1 soldier.");
		bot.sendMessageToChat("You currently have " + currNumSoldiers + " in your donating territory.");
		
		int migratingSoldiers = 1;
		String userResponse = bot.sendMessageGetResponse("Input a valid number, else we will only be moving one to your receiving territory");
		
		try {
			migratingSoldiers = Integer.parseInt(userResponse);
		} catch (NumberFormatException ex) {
			bot.sendMessageToChat("Okay, 1 it is");
			migratingSoldiers = 1;
		}
				
		int numSoldiersStayed = currNumSoldiers - migratingSoldiers;
		
		donaterTerr.setSoldiers(numSoldiersStayed);
		receiverTerr.setSoldiers(migratingSoldiers);
	}
	
	
	public void removeTerritoryFromDefender(String defenderName, String territoryLost, Game game)
	{
		ArrayList<Player> playersArray = game.getPlayersArray();
		
		for(int i = 0; i < playersArray.size(); i++)
		{
			Player currPlayer = playersArray.get(i);
			if(Objects.equals(currPlayer.getName(), defenderName))  //if we have the player object
			{
				currPlayer.removeTerritoryOwned(territoryLost);
				break;
				
			}
		}
	}
	
	
	
	//TODO: RENAME TO LET USER KNOW DEFENDER IS ON THE LEFT?
	@CoverageIgnore
	private String beginBattle(Territory defendingTerr, Territory attackingTerr) throws InterruptedException, TelegramApiException
	{
		String attackerName = attackingTerr.getOwner();
		String defenderName = defendingTerr.getOwner();
		
		while(attackingTerr.getSoldierCount() != 1 || defendingTerr.getSoldierCount() != 0)
		{
			int attackingSoldiers = this.numAttackers(attackingTerr);
			int defendingSoldiers = this.numDefenders(defendingTerr);
			
			bot.sendMessageToChat(attackerName + " your rolls are "); 
			Integer[] attackerRolls = this.getRollsSorted(attackingSoldiers);
			
			bot.sendMessageToChat(defenderName + " your rolls are "); 
			Integer[] defenderRolls = this.getRollsSorted(defendingSoldiers);
						
			int i = 0;
			bot.sendMessageGetResponse("The results are:");
			while(i < defenderRolls.length && i < attackerRolls.length)
			{
				bot.sendMessageToChat("The winner for the roll " + (i+1)  + " is ");
				
				if(attackerRolls[i] > defenderRolls[i])
				{
					bot.sendMessageToChat(attackerName);
					defendingTerr.setSoldiers(defendingTerr.getSoldierCount() - 1);
				}
				else
				{
					bot.sendMessageToChat(defenderName);
					attackingTerr.setSoldiers(attackingTerr.getSoldierCount() - 1);
				}
				
				i++;
			}
			
			bot.sendMessageToChat(attackerName + " remaining soldiers are " + attackingTerr.getSoldierCount());
			bot.sendMessageToChat(defenderName + " remaining soldiers are " + defendingTerr.getSoldierCount());
			
			if(attackingTerr.getSoldierCount() == 1 || defendingTerr.getSoldierCount() == 0)
			{
				break;
			}
		}
		
		
		bot.sendMessageToChat("The winner of this battle is: ");
		if(attackingTerr.getSoldierCount() == 1)
		{
			bot.sendMessageToChat(defenderName);
			return defenderName;
		}
		else
		{
			bot.sendMessageToChat(attackerName);
			bot.sendMessageToChat(defendingTerr.getTerritoryName() + " is now yours");
			return attackerName;
		}
		

	}
	
	
	public Integer[] getRollsSorted(int numberOfRolls)
	{
		Integer[] rollsArray = new Integer[numberOfRolls];
		for(int i = 0; i < rollsArray.length; i++)
		{
			rollsArray[i] = Dice.roll();
			bot.sendMessageToChat(rollsArray[i].toString());
		}
		Arrays.sort(rollsArray, Collections.reverseOrder());
		return rollsArray;
	}
	
	public int numAttackers(Territory attackingTerr)
	{
		if(attackingTerr.getSoldierCount() > 3)
		{
			return 3;
		}
		else if(attackingTerr.getSoldierCount() == 3)
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
		if(defendingTerr.getSoldierCount() >= 2)
		{
			return 2;
		}
		else
		{
			return 1;
		}
		
	}
}
