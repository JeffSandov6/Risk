package JCK.Risk.Locations;
import JCK.Risk.Locations.Territory;
import java.util.HashMap;

public class Continent {

	String continentName;
	HashMap<String, Territory> listOfTerritories;
	
	public Continent(String continentName)
	{
		this.continentName = continentName;
		listOfTerritories = new HashMap<String, Territory>();
		
	}
	
	
		
	
}
