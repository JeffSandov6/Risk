package JCK.Risk.Players;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class Player {
	
	public String name;
	public Integer turnValue;
	
	//TODO: I DONT THINK WE NEED THIS
	public int rollValue;
	//private int soldiersToPlace = 0;
	
	private List<String> listOfTerritoriesOwned;
	private List<String> continentsOwned;    //TODO: NOT NEEDED
	public ArrayList<String> listOfCards = new ArrayList<String>();
	
	
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

	//TODO: I DONT THINK WE NEED THIS
//	public int getSoldiersToPlace() {
//		return soldiersToPlace;
//	}
//
//	public void setSoldiersToPlace(int soldiersToPlace) {
//		this.soldiersToPlace = soldiersToPlace;
//	}

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

	public void createPlayer(String playerName, Integer turnRollValue) {
		this.name = playerName;
		this.turnValue = turnRollValue;
		listOfTerritoriesOwned = new ArrayList<String>();
		
	}
}

