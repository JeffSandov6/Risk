package JCK.Risk.Players;

import java.util.ArrayList;


public class Player {
	
	public String name;
	public int rollValue;
	public Integer turnValue;
	
	ArrayList<Integer> listOfCards = new ArrayList<Integer>();
	
	public String getName() {
		return name;
	}
	
	public Integer getTurnValue() {
		return turnValue;
	}
	
	public void createPlayer(String playerName, Integer turnRollValue)
	{
		this.name = playerName;
		this.turnValue = turnRollValue;
		
		
	}
	
	
	
}
