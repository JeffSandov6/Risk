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

		testContinent.addTerritory("TestTerritory", testAdjacencies);
		testContinent.addTerritory("TestTerritory2", testAdjacencies2);

		testContinent.getListOfTerritories().get("TestTerritory").setOwner(testPlayer1.getName());
		testContinent.getListOfTerritories().get("TestTerritory2").setOwner(testPlayer2.getName());
		game.continentArray.add(testContinent);

	}
	@Test
	public void createTest() {

	}
}
