package JCK.Risk.Gameplay;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import JCK.Risk.Locations.Territory;
import JCK.Risk.Locations.Continent;
import JCK.Risk.Players.Player;

/*
 * Holds the past state of the game at the beginning of the players turn
 */
public class Undo {
	private List<Player> pastPlayers;
	private List<Continent> pastContinent;
	private HashMap<String, Territory> pastListOfTerritories;
	
	/**
	 * Has to make a deep copy of pastPlayers as well as pastContinents instead of normal
	 * shallow copy so it can stand alone
	 * @param pastPlayers
	 * @param pastContinent
	 */
	public Undo(List<Player> pastPlayers, List<Continent> pastContinent) {
		this.pastPlayers = new ArrayList<Player>(pastPlayers);
		this.pastContinent = new ArrayList<Continent>();
		
		for (int i = 0; i < pastContinent.size(); i++) {
			this.pastContinent.add(new Continent(pastContinent.get(i)));
		}
		
		for (int i = 0; i < pastContinent.size(); i++) {
			this.pastContinent.get(i).setListOfTerritories(new HashMap<String, Territory>());
			for (String key : pastContinent.get(i).getListOfTerritories().keySet()) {
				this.pastContinent.get(i).getListOfTerritories().put(key, new Territory(pastContinent.get(i).getListOfTerritories().get(key)));
			}
		}

	}

	public List<Player> getPastPlayers() {
		return this.pastPlayers;
	}

	public HashMap<String, Territory> getPastListOfTerritories() {
		return this.pastListOfTerritories;
	}

	public List<Continent> getPastContinent() {
		return this.pastContinent;
	}
}
