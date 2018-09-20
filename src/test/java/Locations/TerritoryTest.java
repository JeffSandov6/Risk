package Locations;
import org.junit.Assert;
import org.junit.Test;

import JCK.Risk.Locations.Territory;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class TerritoryTest {
	@Test
	public void createTest() {
		Random random = new Random();
		
		String testTerritoryName = "TestTerritory";
		List<String> testAdjacencies = new ArrayList<String>();
		testAdjacencies.add("TestAdjacency1");
		testAdjacencies.add("TestAdjacency2");
		testAdjacencies.add("TestAdjacency3");

		Territory territory = new Territory(testTerritoryName, testAdjacencies);
		
		Assert.assertEquals(testTerritoryName, territory.getTerritoryName());
		Assert.assertEquals(testAdjacencies, territory.getAdjacencies());
	}
}
