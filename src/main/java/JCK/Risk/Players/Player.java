package JCK.Risk.Players;

import java.util.List;
import java.util.ArrayList;


public class Player {
	
	public String name;
	public int rollValue;
	public Integer turnValue;
	private int soldiersToPlace = 0;
	private List<String> listOfTerritoriesOwned;
	private List<String> continentsOwned;
	ArrayList<Integer> listOfCards = new ArrayList<Integer>();
	
	public List<String> getTerritoriesOwned() {
		return listOfTerritoriesOwned;
	}

	public void addTerritoryOwned(String territory) {
		listOfTerritoriesOwned.add(territory);
	}

	public void removeTerritoryOwned(String territory) {
		listOfTerritoriesOwned.remove(territory);
	}

	public int getSoldiersToPlace() {
		return soldiersToPlace;
	}

	public void setSoldiersToPlace(int soldiersToPlace) {
		this.soldiersToPlace = soldiersToPlace;
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

