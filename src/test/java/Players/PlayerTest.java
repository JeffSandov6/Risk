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
	
	@Test
	public void setNameTest() {
		Player testPlayer = new Player();
		testPlayer.setName("TestPlayer");
		Assert.assertTrue(testPlayer.getName().equals("TestPlayer"));
	}
	
	@Test
	public void getNameTest() {
		Player testPlayer = new Player();
		testPlayer.setName("TestPlayer");
		Assert.assertTrue(testPlayer.getName().equals("TestPlayer"));
	}
	
	@Test
	public void purchaseUndoActionTest() {
		player.purchaseUndoAction();
		Assert.assertEquals(player.getUndoActionsAvailable(), 1);
	}
	
	@Test
	public void getUndoActionsAvailableTest() {
		player.purchaseUndoAction();
		Assert.assertEquals(1, player.getUndoActionsAvailable());
	}
	
	@Test
	public void addCreditTest() {
		player.addCredit(30);
		Assert.assertEquals(30, player.getCurrentCredit());
	}
	
	@Test
	public void getCurrentCreditTest() {
		player.addCredit(20);
		Assert.assertEquals(20, player.getCurrentCredit());
	}
	
	@Test
	public void useCreditTest() {
		player.useCredit(14);
		Assert.assertEquals(-14, player.getCurrentCredit());
	}
	
	@Test
	public void purchaseCardTest() {
		player.purchaseCard("Infantry");
		Assert.assertEquals("Infantry", player.getListOfCards().get(0));
	}
}
