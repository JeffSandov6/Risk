package JCK.Risk.Players;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;


public class Player implements Observer {
	
	private String name;
	private Integer turnValue;
	private int rollValue;
	private int undoActionsAvailable;
	private int currentCredit;
	
	private List<String> listOfTerritoriesOwned;
	private List<String> continentsOwned;
	private ArrayList<String> listOfCards;
	
	private int totalTerritoriesConquered;
	
	public Player() {
		listOfCards = new ArrayList<String>();
	}
	
	public Player(Player playerToCopy) {
		this.name = playerToCopy.name;
		this.turnValue = playerToCopy.turnValue;
		this.rollValue = playerToCopy.rollValue;
		this.listOfTerritoriesOwned = new ArrayList<String>();
		this.continentsOwned = new ArrayList<String>();
		for (int i = 0; i < playerToCopy.listOfTerritoriesOwned.size(); i++) {
			this.listOfTerritoriesOwned.add(playerToCopy.listOfTerritoriesOwned.get(i));
		}

		for (int i = 0; i < playerToCopy.continentsOwned.size(); i++) {
			this.continentsOwned.add(playerToCopy.continentsOwned.get(i));
		}

		for (int i = 0; i < playerToCopy.listOfCards.size(); i++) {
			this.listOfCards.add(playerToCopy.listOfCards.get(i));
		}
	}
	
	
	@Override
	public void update(String playerName) {
		if(Objects.equals(playerName, getName()))
		{
			System.out.print(getName() + " , you are being attacked!");
		}
	}
	
	
	public void setRollValue(int rollValue) {
		this.rollValue = rollValue;
	}
	
	public int getRollValue() {
		return this.rollValue;
	}
	public void addCardToList(String cardType)
	{
		listOfCards.add(cardType);
		Collections.sort(listOfCards);
	}
	
	public ArrayList<String> getListOfCards() {
		return listOfCards;
	}

	public List<String> getTerritoriesOwned() {
		return listOfTerritoriesOwned;
	}

	public void addTerritoryOwned(String territory) {
		listOfTerritoriesOwned.add(territory);
	}

	public void removeTerritoryOwned(String territory) {
		listOfTerritoriesOwned.remove(territory);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getTurnValue() {
		return turnValue;
	}
	
	public void setTurnValue(Integer turnValue) {
		this.turnValue = turnValue;
	}

	//TODO: this array is never used, we can remove below 2 functions
	public List<String> getContinentsOwned() {
		return continentsOwned;
	}

	public void addContinentOwned(String continent) {
		continentsOwned.add(continent);
	}

	public void removeContinentOwned(String continent) {
		continentsOwned.remove(continent);
	}
	
	/*
	 * returns the number of undo actions the player has
	 */
	public int getUndoActionsAvailable() {
		return undoActionsAvailable;
	}
	
	/*
	 * uses an undo action from the player 
	 */
	public void useUndoAction() {
		undoActionsAvailable--;
	}
	
	/*
	 * Adds to the credit of the current player
	 */
	public void addCredit(int credit) {
		currentCredit += credit;
	}
	
	/*
	 * gets the players current credit amount
	 */
	public int getCurrentCredit() {
		return currentCredit;
	}
	
	/*
	 * Has the current player buy one undo action
	 */
	public void purchaseUndoAction() {
		undoActionsAvailable++;
	}
	
	/*
	 * Purchases a card and adds it to the listOfCards
	 */
	public void purchaseCard(String card) {
		listOfCards.add(card);
	}
	
	public void useCredit(int cost) {
		currentCredit -= cost;
	}
	public void createPlayer(String playerName, Integer turnRollValue) {
		this.name = playerName;
		this.turnValue = turnRollValue;
		listOfTerritoriesOwned = new ArrayList<String>();
		continentsOwned = new ArrayList<String>();
		undoActionsAvailable = 0;
		currentCredit = 0;
		
	}
}

