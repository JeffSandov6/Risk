package Gameplay;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import JCK.Risk.Gameplay.Game;
import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Players.Player;

public class GameTest {
	private Game game;
	@Before
	public void createGameTest() {
		game = new Game();
	}
	
	@Test
	public void areTerritoriesFilledTest() {
		ArrayList<Continent> continentsArray = new ArrayList<Continent>();
		Territory t = new Territory("TestTerritory", null);
		t.setOwner("Player");
		for (int i = 0; i < 2; i++) {
			continentsArray.add(new Continent("TestContinent" + i, i));
			continentsArray.get(i).getListOfTerritories().put(t.getTerritoryName(), t);
		}
		game.setContinentArray(continentsArray);
		Assert.assertEquals(true, game.areTerritoriesFilled());
		Territory newT = new Territory("TestTerritory", null);
		newT.setOwner("nobody");
		continentsArray.get(0).getListOfTerritories().put(newT.getTerritoryName(), newT);
		Assert.assertEquals(false, game.areTerritoriesFilled());
	}
	
	@Test
	public void isEmptyTerritoryTest() {
		ArrayList<Continent> continentList = new ArrayList<Continent>();
		Continent continent = new Continent("TestContinent", 2);
		HashMap<String, Territory> territoryMap = new HashMap<String, Territory>();
		territoryMap.put("TestTerritory1", new Territory("TestTerritory1",null));
		territoryMap.put("TestTerritory2", new Territory("TestTerritory2",null));
		territoryMap.get("TestTerritory1").setOwner("TestPlayer1");
		
		continent.setListOfTerritories(territoryMap);
		continentList.add(continent);
		game.setContinentArray(continentList);
		
		Assert.assertEquals(false, game.isEmptyTerritory("TestTerritory1"));
		Assert.assertEquals(true, game.isEmptyTerritory("TestTerritory2"));
	}
	@Test
	public void assignTerritoryTest() {
		ArrayList<Continent> continentList = new ArrayList<Continent>();
		Continent continent = new Continent("TestContinent", 2);
		HashMap<String, Territory> territoryMap = new HashMap<String, Territory>();
		Player testPlayer = new Player();
		
		testPlayer.setName("TestPlayer1");
		territoryMap.put("TestTerritory1", new Territory("TestTerritory1",null));
		territoryMap.put("TestTerritory2", new Territory("TestTerritory2",null));
		territoryMap.get("TestTerritory1").setOwner("nobody");
		
		continent.setListOfTerritories(territoryMap);
		continentList.add(continent);
		game.setContinentArray(continentList);
		
		game.assignTerritory(testPlayer, "TestTerritory1", 3);
		Assert.assertTrue(game.getContinentArray().get(0).getListOfTerritories().get("TestTerritory1").getOwner().equals("TestPlayer1"));
	}
}
