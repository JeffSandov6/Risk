package JCK.Risk.Locations;

import java.util.ArrayList;
import java.util.List;

public class Territory {

	public String territoryName;
	private String ownedBy = "nobody";
	public int numSoldiersHere = 0;
	private List<String> adjacencies;

	/**
	 * Constructor for the territory to have adjacencies
	 * @param territoryName
	 * @param adjacencies
	 */
	public Territory(String territoryName, List<String> adjacencies) {
		this.territoryName = territoryName;
		this.adjacencies = adjacencies;
	}
	
	public Territory(Territory territoryToCopy) {
		this.territoryName = territoryToCopy.territoryName;
		this.ownedBy = territoryToCopy.ownedBy;
		this.numSoldiersHere = territoryToCopy.numSoldiersHere;
		adjacencies = new ArrayList<String>();
		for (int i = 0; i < territoryToCopy.adjacencies.size(); i++) {
			this.adjacencies.add(territoryToCopy.adjacencies.get(i));
		}
		
	}
	
	/**
	 * Helper method for getting who the owner is
	*/
	public String getOwner() {
		return this.ownedBy;
	}
	
	public void setOwner(String name) {
		this.ownedBy = name;
	}
	/**
	 * Helper method for getting what the territory name is
	*/
	public String getTerritoryName() {
		return this.territoryName;
	}
	/**
	 * Helper method for returning the List of adjacencies
	*/
	public List<String> getAdjacencies() {
		return adjacencies;
	}
	/**
	 * Helper method for returning the number of soldiers at this territory
	*/
	public int getSoldierCount() {
		return numSoldiersHere;
	}
	/**
	 * Adds to the number of soldiers
	 * @param numSoldiers
	 * @return
	 */
	public Territory addSoldiers(int numSoldiers) {
		numSoldiersHere += numSoldiers;
		return this;
	}
}
