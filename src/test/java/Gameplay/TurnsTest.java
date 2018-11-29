package Gameplay;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Gameplay.Turns;
import JCK.Risk.Gameplay.Game;
import JCK.Risk.Players.Player;


import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TurnsTest {
	private Turns turns;
	private Game game;
	@Before
	public void setup() {
		game = new Game();
		Player testPlayer1 = new Player();
		Player testPlayer2 = new Player();
		testPlayer1.createPlayer("TestPlayer1",0);
		testPlayer2.createPlayer("TestPlayer2",0);

		game.playersArray.add(testPlayer1);
		game.playersArray.add(testPlayer2);
		Continent testContinent = new Continent("Asia", 7);

		List<String> testAdjacencies = new ArrayList<String>();
		List<String> testAdjacencies2 = new ArrayList<String>();

		testAdjacencies.add("TestTerritory");
		testAdjacencies2.add("TestTerritory2");

		testContinent.addTerritory("TestTerritory", testAdjacencies2);
		testContinent.addTerritory("TestTerritory2", testAdjacencies);

		testContinent.getListOfTerritories().get("TestTerritory").setOwner(testPlayer1.getName());
		testContinent.getListOfTerritories().get("TestTerritory2").setOwner(testPlayer2.getName());
		game.continentArray.add(testContinent);

		turns = new Turns();
	}
	@Test
	public void createTest() {

	}
	
	@Test
	public void numDefendersTest() {
		Territory testTerritory = new Territory("TestTerritory", null);
		testTerritory.numSoldiersHere = 2;
		Assert.assertEquals(2, turns.numDefenders(testTerritory));
		testTerritory.numSoldiersHere = 1;
		Assert.assertEquals(1, turns.numDefenders(testTerritory));
	}
	
	@Test
	public void numAttackersTest() {
		Territory testTerritory = new Territory("TestTerritory", null);
		testTerritory.numSoldiersHere = 4;
		Assert.assertEquals(3, turns.numAttackers(testTerritory));
		testTerritory.numSoldiersHere = 3;
		Assert.assertEquals(2, turns.numAttackers(testTerritory));
		testTerritory.numSoldiersHere = 2;
		Assert.assertEquals(1, turns.numAttackers(testTerritory));
	}
	
	@Test
	public void getRollsSortedTest() {
		Integer[] expected = turns.getRollsSorted(3);
		Assert.assertEquals(expected, expected);
	}
	
	@Test
	public void getTerritoryObjectTest() {
		Territory territory1 = game.continentArray.get(0).getListOfTerritories().get("TestTerritory");
		Territory territory2 = game.continentArray.get(0).getListOfTerritories().get("TestTerritory2");
		Assert.assertEquals(territory1, turns.getTerritoryObject("TestTerritory", game.continentArray));
		Assert.assertEquals(territory2, turns.getTerritoryObject("TestTerritory2", game.continentArray));

	}
	
	@Test
	public void checkAttackableAdjacenciesTest() {
		Player player1 = game.playersArray.get(0);
		Player player2 = game.playersArray.get(1);
		List<Territory> expectedRes = new ArrayList<Territory>();
		
		Territory territory1 = game.continentArray.get(0).getListOfTerritories().get("TestTerritory");
		Territory territory2 = game.continentArray.get(0).getListOfTerritories().get("TestTerritory2");
		
		expectedRes.add(territory1);
		List<Territory> actualRes = turns.checkAttackableAdjacencies(player2, territory2, game.continentArray);
		Assert.assertEquals(expectedRes, actualRes);
		
		expectedRes.clear();
		actualRes.clear();
		
		expectedRes.add(territory2);
		actualRes = turns.checkAttackableAdjacencies(player1, territory1, game.continentArray);
		Assert.assertEquals(expectedRes, actualRes);
	}
}
