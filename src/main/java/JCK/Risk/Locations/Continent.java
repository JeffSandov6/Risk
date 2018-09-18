package JCK.Risk.Locations;
import JCK.Risk.Locations.Territory;
import java.util.HashMap;
import java.util.List;

public class Continent {

	String continentName;
	HashMap<String, Territory> listOfTerritories;
	
	public Continent(String continentName)
	{
		this.continentName = continentName;
		listOfTerritories = new HashMap<String, Territory>();
		
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
		
	
}
