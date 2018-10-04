package Players;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import JCK.Risk.Players.Player;

import java.util.Random;

public class PlayerTest {
	private Player player;
	Random random = new Random();

	@Before
	public void setup() {
		player = new Player();
	}
	@Test
	public void createPlayerTest() {
		
		String randomPlayer = "TestPlayer";
		Integer randomTurnRollValue = random.nextInt(6) + 1;
		

		player.createPlayer(randomPlayer, randomTurnRollValue);
		
		Assert.assertEquals(randomPlayer, player.getName());
		Assert.assertEquals(randomTurnRollValue, player.getTurnValue());
	}

	@Test
	public void addTerritoryOwnedTest() {
		player.createPlayer("TestPlayer", random.nextInt(6) + 1);
		player.addTerritoryOwned("NewTestTerritory");
		Assert.assertTrue(player.getTerritoriesOwned().contains("NewTestTerritory"));
	}

	@Test
	public void removeTerritoryOwnedTest() {
		player.createPlayer("TestPlayer", random.nextInt(6) + 1);
		player.addTerritoryOwned("NewTestTerritory");
		Assert.assertTrue(player.getTerritoriesOwned().contains("NewTestTerritory"));
		player.removeTerritoryOwned("NewTestTerritory");
		Assert.assertTrue(!player.getTerritoriesOwned().contains("NewTestTerritory"));
	}

	@Test
	public void addCardToListTest() {
		List<String> testCardList = new ArrayList<String>();
		testCardList.add("Infantry");
		testCardList.add("Cavalry");
		testCardList.add("Wild");
		Collections.sort(testCardList);
		player.addCardToList("Cavalry");
		player.addCardToList("Wild");
		player.addCardToList("Infantry");
		Assert.assertEquals(testCardList, player.getListOfCards());
	}

	@Test
	public void addContinentOwnedTest() {
		player.createPlayer("TestPlayer", random.nextInt(6) + 1);
		player.addContinentOwned("NewTestContinent");
		Assert.assertTrue(player.getContinentsOwned().contains("NewTestContinent"));
	}

	@Test
	public void removeContinentOwnedTest() {
		player.createPlayer("TestPlayer", random.nextInt(6) + 1);
		player.addContinentOwned("NewTestContinent");
		Assert.assertTrue(player.getContinentsOwned().contains("NewTestContinent"));
		player.removeContinentOwned("NewTestContinent");
		Assert.assertTrue(!player.getContinentsOwned().contains("NewTestContinent"));
	}
}
