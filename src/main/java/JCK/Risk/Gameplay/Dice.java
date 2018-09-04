package JCK.Risk.Gameplay;
import java.util.Random;

public class Dice {
	private int diceValue = 0;
	public int roll() {
		Random random = new Random();
		diceValue = random.nextInt(6) + 1;
		return diceValue;
	}
}
