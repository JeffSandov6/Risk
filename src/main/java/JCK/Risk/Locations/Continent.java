package JCK.Risk.Locations;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;

import java.util.HashMap;
import java.util.List;

public class Continent {

	String continentName;
	HashMap<String, Territory> listOfTerritories;
	private int continentValue;
	
	public Continent(String continentName, int continentValue)
	{
		this.continentName = continentName;
		listOfTerritories = new HashMap<String, Territory>();
		this.continentValue = continentValue;
	}
	/*
	 * Adds a territory to the continent's HashMap with its name and corresponding adjacencies
	 */
	public void addTerritory(String territoryName, List<String> adjacencies) {
		this.listOfTerritories.put(territoryName, new Territory(territoryName, adjacencies));
	}
	//TODO format the continents to display them all in a table format
	public void displayContinent() {
		System.out.println(continentName + ": ");
		for (String key : listOfTerritories.keySet()) {
			System.out.println(key + "\t\t\t" + listOfTerritories.get(key).getOwner() + "\t\t\t" + listOfTerritories.get(key).getAdjacencies());
		}
		System.out.println();
	}
	
	public Territory getTerritory(String territoryName)
	{
		return listOfTerritories.get(territoryName);
		
		
	}
	/**
	 * Helper method that returns the HashMap list of territories
	 * @return
	 */
	public HashMap<String, Territory> getListOfTerritories() {
		return listOfTerritories;
	}
	
	public String getContinentName() {
		return continentName;
	}
	
	public int getContinentValue() {
		return continentValue;
	}
	
	/**
	 * Checks if the player owns the entire continent by iterating through the list of territories and checking the owner
	 * @param player
	 * @return
	 */
	public boolean playerOwnsContinent(Player player) {
		for (String key : listOfTerritories.keySet()) {
			if (!listOfTerritories.get(key).getOwner().equals(player.getName())) {
				return false;
			}
		}
		return true;
	}
	

}
