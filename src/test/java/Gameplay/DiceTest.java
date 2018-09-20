package Gameplay;
import org.junit.Assert;
import org.junit.Test;

import JCK.Risk.Gameplay.Dice;

import java.util.Random;

public class DiceTest {
	@Test
	public void rollTest() {
		Random random = new Random();
		Dice dice = new Dice();
		int diceValue = dice.roll();
		Assert.assertEquals(diceValue, dice.getDiceValue());

	}
}
