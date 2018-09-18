package JCK.Risk.Locations;

import java.util.List;

public class Territory {

	private String territoryName;
	private String ownedBy = "nobody";
	private int numSoldiersHere = 0;
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
	/**
	 * Helper method for getting who the owner is
	 */
	public String getOwner() {
		return this.ownedBy;
	}
	/**
	 * Helper method for returning the List of adjacencies
	*/
	public List<String> getAdjacencies() {
		return adjacencies;
	}
}
