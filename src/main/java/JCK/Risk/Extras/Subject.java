package JCK.Risk.Extras;

import java.util.ArrayList;

import JCK.Risk.Players.Player;

public interface Subject {
	
	public void notifyObservers(String playerName, ArrayList<Player> players);

}
