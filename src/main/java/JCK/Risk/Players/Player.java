package JCK.Risk.Players;

import java.util.ArrayList;


public class Player {
	
	String name;
	int rollValue;
	
	
	ArrayList<Integer> listOfCards = new ArrayList<Integer>();
	
	
	
	public void createPlayer(String playerName, int turnRollValue)
	{
		this.name = playerName;
		this.rollValue = turnRollValue;
		
		
	}
	
	
	
	
}
