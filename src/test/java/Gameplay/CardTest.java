package Gameplay;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import JCK.Risk.Gameplay.Card;
import JCK.Risk.Players.Player;

public class CardTest {
	Card card;

	@Before
	public void setup() {
		card = new Card();
	}
	@Test
	public void createTest() {		
		card.initializeCards();
		List<Integer> cards = card.cardsArray;

		Assert.assertEquals(14,(int) cards.get(0));
		Assert.assertEquals(14, (int)cards.get(1));
		Assert.assertEquals(14, (int)cards.get(2));
		Assert.assertEquals(2, (int)cards.get(3));
	}

	@Test
	public void getCardTypeTest() {
		Assert.assertEquals("Infantry", card.getCardType(0));
		Assert.assertEquals("Cavalry", card.getCardType(1));
		Assert.assertEquals("Artillery", card.getCardType(2));
		Assert.assertEquals("Wild", card.getCardType(3));

	}

	@Test
	public void checkCardsTest() {
		Player testPlayer = new Player();
		testPlayer.addCardToList("Artillery");
		try {
			Assert.assertEquals(card.checkCards(testPlayer), 0);
		} catch (IOException e) {
			return;
		}
	}
	
	@Test
	public void getCardIndexTest() {
		Assert.assertEquals(0, card.getCardIndex("infantry"));
		Assert.assertEquals(1, card.getCardIndex("cavalry"));
		Assert.assertEquals(2, card.getCardIndex("artillery"));
		Assert.assertEquals(3, card.getCardIndex("wild"));

	}
	
	@Test
	public void getNextSetValueTest() {
		card.setsTurnedIn = 0;
		Assert.assertEquals(4, card.getNextSetValue());
		
		card.setsTurnedIn = 1;
		Assert.assertEquals(6, card.getNextSetValue());
		
		card.setsTurnedIn = 2;
		Assert.assertEquals(8, card.getNextSetValue());
		
		card.setsTurnedIn = 3;
		Assert.assertEquals(10, card.getNextSetValue());
		
		card.setsTurnedIn = 4;
		Assert.assertEquals(12, card.getNextSetValue());
		
		card.setsTurnedIn = 5;
		Assert.assertEquals(5 * (5-2), card.getNextSetValue());
	}

}
