package Locations;
import org.junit.Assert;
import org.junit.Test;

import JCK.Risk.Locations.Continent;

public class ContinentTest {
	@Test
	public void createTest() {		
		String testContinentName = "TestTerritory";
		int testContinentValue = 5;
		Continent continent = new Continent(testContinentName, testContinentValue);
		Assert.assertEquals(testContinentName, continent.getContinentName());
		Assert.assertEquals(testContinentValue, continent.getContinentValue());
	}
}
