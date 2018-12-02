package Gameplay;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import JCK.Risk.TelegramGameBot;
import JCK.Risk.Gameplay.Card;
import JCK.Risk.Players.Player;

public class CardTest {
	private Card card;
	

	@Before
	public void setup() throws TelegramApiRequestException {
		card = new Card();
		ApiContextInitializer.init();
		TelegramBotsApi botsApi = new TelegramBotsApi();
		botsApi.registerBot(new TelegramGameBot());

		TelegramGameBot bot = new TelegramGameBot();
		card.initializeCards(bot);
	}
	@Test
	public void createTest() {	
		List<Integer> cards = card.getCardsArray();

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
	public void checkCardsTest() throws InterruptedException, TelegramApiException {
		Player testPlayer = new Player();
		testPlayer.addCardToList("Artillery");
		Assert.assertEquals(card.checkCards(testPlayer), 0);
		
	}
	
	@Test
	public void getCardIndexTest() {
		Assert.assertEquals(0, card.getCardIndex("infantry"));
		Assert.assertEquals(1, card.getCardIndex("cavalry"));
		Assert.assertEquals(2, card.getCardIndex("artillery"));
		Assert.assertEquals(3, card.getCardIndex("wild"));
		Assert.assertEquals(-1, card.getCardIndex("test"));

	}
	
	@Test
	public void getNextSetValueTest() {
		card.setSetsTurnedIn(0);
		Assert.assertEquals(4, card.getNextSetValue());
		
		card.setSetsTurnedIn(1);
		Assert.assertEquals(6, card.getNextSetValue());
		
		card.setSetsTurnedIn(2);
		Assert.assertEquals(8, card.getNextSetValue());
		
		card.setSetsTurnedIn(3);
		Assert.assertEquals(10, card.getNextSetValue());
		
		card.setSetsTurnedIn(4);
		Assert.assertEquals(12, card.getNextSetValue());
		
		card.setSetsTurnedIn(5);
		Assert.assertEquals(5 * (5-2), card.getNextSetValue());
	}

	@Test
	public void getCardTest() {
		card.getCardsArray().set(0, 0);
		card.getCardsArray().set(1, 0);
		card.getCardsArray().set(3, 0);
		Assert.assertEquals(card.getCard(), "Artillery");
	}
	@Test
	public void turnInSameCardsTest() {
		ArrayList<String> cardList = new ArrayList<String>();
		cardList.add("infantry");
		cardList.add("infantry");
		cardList.add("infantry");
		cardList.add("cavalry");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("cavalry");
		Assert.assertEquals(expected, card.turnInSameCards(cardList));
	}
	
	@Test
	public void turnInDiffCardsTest() {
		ArrayList<String> cardList = new ArrayList<String>();
		cardList.add("infantry");
		cardList.add("cavalry");
		cardList.add("artillery");
		cardList.add("cavalry");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("cavalry");
		Assert.assertEquals(expected, card.turnInDiffCards(cardList));
	}
	
	@Test
	public void turnInCardsTest() {
		Player testPlayer = new Player();
		testPlayer.getListOfCards().add("infantry");
		testPlayer.getListOfCards().add("infantry");
		testPlayer.getListOfCards().add("infantry");
		testPlayer.getListOfCards().add("cavalry");
		Assert.assertEquals(4, card.turnInCards(testPlayer, 3, 2));
		
		testPlayer.getListOfCards().clear();
		testPlayer.getListOfCards().add("infantry");
		testPlayer.getListOfCards().add("cavalry");
		testPlayer.getListOfCards().add("wild");
		testPlayer.getListOfCards().add("artillery");
		Assert.assertEquals(6, card.turnInCards(testPlayer, 1, 4));
	}
}
