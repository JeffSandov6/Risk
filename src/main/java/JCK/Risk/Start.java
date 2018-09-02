package JCK.Risk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import JCK.Risk.Gameplay.Game;

public class Start {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		System.out.println("Hello, and welcome to Risk!");
		System.out.println("How many players will be playing today?");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int numPlayers = Integer.parseInt(br.readLine());
		
		Game startGame = new Game();
		
		startGame.initializeGame(numPlayers);
	}

}
