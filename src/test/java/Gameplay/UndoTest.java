package Gameplay;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import JCK.Risk.Gameplay.Undo;
import JCK.Risk.Players.Player;
import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;

public class UndoTest {
	private Undo testUndo;
	private List<Player> playerList;
	private List<Continent> continentList;
	private HashMap<String, Territory> territoryMap;
	
	@Before
	public void setup() {
		playerList = new ArrayList<Player>();
		continentList = new ArrayList<Continent>();
		Player testPlayer = new Player();
		
		testPlayer.createPlayer("TestPlayer", 5);
		
		Continent testContinent = new Continent("TestContinent", 4);
		/*
		territoryMap = new HashMap<String, Territory>();
		territoryMap.put("TestTerritory", new Territory("TestTerritory", new ArrayList<String>()));
		
		testContinent.setListOfTerritories(territoryMap);
		*/
		continentList.add(testContinent);
		playerList.add(testPlayer);
		
		testUndo = new Undo(playerList, continentList);
	}
	
	@Test
	public void createUndo() {
		Assert.assertEquals(playerList, testUndo.getPastPlayers());
		Assert.assertEquals(testUndo.getPastContinent(), testUndo.getPastContinent());
		Assert.assertEquals(testUndo.getPastListOfTerritories(), testUndo.getPastListOfTerritories());
	}
	
	@Test
	public void getPlayersTest() {
		Assert.assertEquals(playerList, testUndo.getPastPlayers());
	}
	
	@Test
	public void getContinentsTest() {
		Assert.assertEquals(testUndo.getPastContinent(), testUndo.getPastContinent());
	}
	
	@Test
	public void getTerritoriesTest() {
		Assert.assertEquals(testUndo.getPastListOfTerritories(), testUndo.getPastListOfTerritories());
	}
}
