package Gameplay;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;
import JCK.Risk.Gameplay.Turns;
import JCK.Risk.Extras.TelegramGameBot;
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
	public void setup() throws TelegramApiRequestException {
		game = new Game();
		
		ApiContextInitializer.init();
		TelegramBotsApi botsApi = new TelegramBotsApi();
		botsApi.registerBot(new TelegramGameBot());
		TelegramGameBot bot = new TelegramGameBot();
		
		Player testPlayer1 = new Player();
		Player testPlayer2 = new Player();
		testPlayer1.createPlayer("TestPlayer1",0);
		testPlayer2.createPlayer("TestPlayer2",0);

		game.getPlayersArray().add(testPlayer1);
		game.getPlayersArray().add(testPlayer2);
		Continent testContinent = new Continent("Asia", 7);

		List<String> testAdjacencies = new ArrayList<String>();
		List<String> testAdjacencies2 = new ArrayList<String>();

		testAdjacencies.add("TestTerritory");
		testAdjacencies2.add("TestTerritory2");

		testContinent.addTerritory("TestTerritory", testAdjacencies2);
		testContinent.addTerritory("TestTerritory2", testAdjacencies);

		testContinent.getListOfTerritories().get("TestTerritory").setOwner(testPlayer1.getName());
		testContinent.getListOfTerritories().get("TestTerritory2").setOwner(testPlayer2.getName());
		game.getContinentArray().add(testContinent);

		turns = new Turns(bot);
	}
	@Test
	public void createTest() {

	}
	
	@Test
	public void numDefendersTest() {
		Territory testTerritory = new Territory("TestTerritory", null);
		testTerritory.setSoldiers(2);
		Assert.assertEquals(2, turns.numDefenders(testTerritory));
		testTerritory.setSoldiers(1);
		Assert.assertEquals(1, turns.numDefenders(testTerritory));
	}
	
	@Test
	public void numAttackersTest() {
		Territory testTerritory = new Territory("TestTerritory", null);
		testTerritory.setSoldiers(4);
		Assert.assertEquals(3, turns.numAttackers(testTerritory));
		testTerritory.setSoldiers(3);
		Assert.assertEquals(2, turns.numAttackers(testTerritory));
		testTerritory.setSoldiers(2);
		Assert.assertEquals(1, turns.numAttackers(testTerritory));
	}
	
	@Test
	public void getRollsSortedTest() {
		Integer[] expected = turns.getRollsSorted(3);
		Assert.assertEquals(expected, expected);
	}
	
	@Test
	public void getTerritoryObjectTest() {
		Territory territory1 = game.getContinentArray().get(0).getListOfTerritories().get("TestTerritory");
		Territory territory2 = game.getContinentArray().get(0).getListOfTerritories().get("TestTerritory2");
		Assert.assertEquals(territory1, turns.getTerritoryObject("TestTerritory", game.getContinentArray()));
		Assert.assertEquals(territory2, turns.getTerritoryObject("TestTerritory2", game.getContinentArray()));

	}
	
	@Test
	public void checkAttackableAdjacenciesTest() {
		Player player1 = game.getPlayersArray().get(0);
		Player player2 = game.getPlayersArray().get(1);
		List<Territory> expectedRes = new ArrayList<Territory>();
		
		Territory territory1 = game.getContinentArray().get(0).getListOfTerritories().get("TestTerritory");
		Territory territory2 = game.getContinentArray().get(0).getListOfTerritories().get("TestTerritory2");
		
		expectedRes.add(territory1);
		List<Territory> actualRes = turns.checkAttackableAdjacencies(player2, territory2, game.getContinentArray());
		Assert.assertEquals(expectedRes, actualRes);
		
		expectedRes.clear();
		actualRes.clear();
		
		expectedRes.add(territory2);
		actualRes = turns.checkAttackableAdjacencies(player1, territory1, game.getContinentArray());
		Assert.assertEquals(expectedRes, actualRes);
	}
	
	@Test
	public void getExtraArmiesForContinentsOwnedTest() {
		Player testPlayer = new Player();
		testPlayer.createPlayer("TestPlayer1", 2);
		Assert.assertEquals(0, turns.getExtraArmiesForContinentsOwned(testPlayer, game.getContinentArray()));
		
		game.getContinentArray().get(0).getListOfTerritories().get("TestTerritory2").setOwner(testPlayer.getName());
		Assert.assertEquals(7,turns.getExtraArmiesForContinentsOwned(testPlayer, game.getContinentArray()));
	}
	
	@Test
	public void playerOwnsTerritoryTest() {
		Player testPlayer = new Player();
		testPlayer.createPlayer("TestPlayer1", 2);
		Assert.assertTrue(turns.playerOwnsTerritory("TestTerritory", testPlayer, game.getContinentArray()));
		Assert.assertTrue(!turns.playerOwnsTerritory("TestTerritory2", testPlayer, game.getContinentArray()));
	}
	
	@Test
	public void checkIfUserRespondedTest() {
		Assert.assertEquals(false, turns.checkIfUserResponded("empty"));
		Assert.assertEquals(true, turns.checkIfUserResponded("test"));
		
	}
	
	@Test
	public void getExtraArmiesForTerrsOwnedTest() {
		Player testPlayer = new Player();
		testPlayer.createPlayer("TestPlayer", 0);
		Assert.assertEquals(3, turns.getExtraArmiesForTerrsOwned(testPlayer));
	}
	
	@Test
	public void removeTerritoryFromDefenderTest() {
		turns.removeTerritoryFromDefender("TestPlayer1", "TestTerritory", game);
		Assert.assertTrue(!game.getPlayersArray().get(0).getTerritoriesOwned().contains("TestTerritory"));
	}
}
