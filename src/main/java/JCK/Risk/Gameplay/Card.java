package JCK.Risk.Gameplay;

import java.util.ArrayList;
import java.util.Random;

public class Card {

	
	
	Random randNumber = new Random();

	
	public static ArrayList<Integer> cardsArray = new ArrayList<Integer>();
	

	
	public void initializeCards() {
		
		cardsArray.add(14);   //value 0 will represent infantry cards 
		cardsArray.add(14);   //value 1 will represent cavalry cards  
		cardsArray.add(14);   //value 2 will represent artillery cards 
		cardsArray.add(2);    //value 3 will represent wild cards 

	}
	
	
	
	public Integer getCard() {
		
		int cardNumber = randNumber.nextInt(4);
		
		
		//TODO check if there are no cards left at all, of any type
		
		
		//if there are no more of this card type, call the func again
		if(cardsArray.get(cardNumber) == 0)
		{
			return getCard();
		}
		
		
		//if there are more cards left, of this type, return it
		
		
		//update the amount left of this card by subtracting by 1
		int remainingCards = cardsArray.get(cardNumber) - 1;
		cardsArray.set(cardNumber, remainingCards);
		
		return cardNumber;
		
		
		
	}
	
	
	

}
