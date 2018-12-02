package Locations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import JCK.Risk.Locations.Territory;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class TerritoryTest {

	private Territory territory;
	@Before
	public void setup() {
		String testTerritoryName = "TestTerritory";
		List<String> testAdjacencies = new ArrayList<String>();
		testAdjacencies.add("TestAdjacency1");
		testAdjacencies.add("TestAdjacency2");
		testAdjacencies.add("TestAdjacency3");

		territory = new Territory(testTerritoryName, testAdjacencies);
	}

	@Test
	public void createTest() {
		String testTerritoryName = "TestTerritory";
		List<String> testAdjacencies = new ArrayList<String>();
		testAdjacencies.add("TestAdjacency1");
		testAdjacencies.add("TestAdjacency2");
		testAdjacencies.add("TestAdjacency3");

		Assert.assertEquals(testTerritoryName, territory.getTerritoryName());
		Assert.assertEquals(testAdjacencies, territory.getAdjacencies());
	}

	@Test 
	public void deepCopyTest() {
		Territory testTerritory = new Territory("TestTerritory", new ArrayList<String>());
		testTerritory.setOwner("TestPlayer1");
		testTerritory.setSoldiers(3);
		testTerritory.getAdjacencies().add("TestAdjacency1");
		testTerritory.getAdjacencies().add("TestAdjacency2");

		Territory copyTerritory = new Territory(testTerritory);
		Assert.assertEquals("TestPlayer1", copyTerritory.getOwner());
		Assert.assertEquals(3, copyTerritory.getSoldierCount());
		Assert.assertEquals("TestTerritory", copyTerritory.getTerritoryName());
		Assert.assertEquals(testTerritory.getAdjacencies(), copyTerritory.getAdjacencies());
		
	}
	
	@Test
	public void addSoldiers() {
		Random random = new Random();
		int numberOfSoldiers = random.nextInt(10);
		int finalNumberSoldiers = numberOfSoldiers + territory.getSoldierCount();
		territory.addSoldiers(numberOfSoldiers);
		Assert.assertEquals(finalNumberSoldiers, territory.getSoldierCount());
	}
}
