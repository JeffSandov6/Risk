package Locations;
import org.junit.Assert;
import org.junit.Test;

import JCK.Risk.Locations.Continent;

public class ContinentTest {
	@Test
	public void createTest() {		
		String testContinentName = "TestTerritory";
		Continent continent = new Continent(testContinentName);
		Assert.assertEquals(testContinentName, continent.getContinentName());
	}
}
