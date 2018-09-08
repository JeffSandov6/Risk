package JCK.Risk.Locations;

public class Territory {

	String territoryName;
	String ownedBy = "nobody";
	int numSoldiersHere = 0;
	public Territory(String territoryName) {
		this.territoryName = territoryName;

	}
	/*
	 * Helper method for getting who the owner is
	 */
	public String getOwner() {
		return this.ownedBy;
	}
}
