package JCK.Risk.Gameplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import JCK.Risk.Players.Player;

public class Card {

	
	
	Random randNumber = new Random();

	
	public static ArrayList<Integer> cardsArray = new ArrayList<Integer>();
	public int setsTurnedIn = 0;  //TODO: UPDATE THIS MANUALLY AFTER SET TURN IN
	

	
	public void initializeCards() {
		
		cardsArray.add(14);   //value 0 will represent infantry cards 
		cardsArray.add(14);   //value 1 will represent cavalry cards  
		cardsArray.add(14);   //value 2 will represent artillery cards 
		cardsArray.add(2);    //value 3 will represent wild cards 

	}
	
	
	
	public String getCard() {
		
		int cardNumber = randNumber.nextInt(4);
		
		
		//TODO check if there are no cards left at all, of any type
		
		
		//if there are no more of this card type, call the func again
		if(cardsArray.get(cardNumber) == 0)
		{
			return getCard();
		}
		
		
		//update the amount left of this card by subtracting by 1
		int remainingCards = cardsArray.get(cardNumber) - 1;
		cardsArray.set(cardNumber, remainingCards);
		
		String cardType = getCardType(cardNumber);
		
		return cardType;
		
	}
	
	
	
	public String getCardType(int cardNumber)
	{
		switch(cardNumber)
		{
		case 0:
			return "Infantry";
		case 1:
			return "Cavalry";
		case 2:
			return "Artillery";
		default:
			return "Wild";
		}
			
	}
	
	public int checkCards(Player player) throws IOException
	{
		ArrayList<String> cardsOwned = player.getListOfCards();
		
		if(cardsOwned.size() == 0)
		{
			System.out.println("You currently have no cards");
			return 0;
		}
		
		System.out.println("These are the cards that you currently own:");
		System.out.println(cardsOwned);
		
		
		if(cardsOwned.size() < 3)
		{
			System.out.println("You can't turn any in right now");
			return 0;
		}
		
		 
		int extraUnits = checkCardPossibilities(player);
		
		if(extraUnits == 0)
		{
			System.out.println("You can't turn any in right now");
			return 0;
		}
		else if(extraUnits == -1)
		{
			System.out.println("Okay, you're not turning any in");
			return 0;
		}
		
		
		return extraUnits;
	}
	
	
	
	
	public int checkCardPossibilities(Player player) throws IOException
	{
		ArrayList<String> cardsOwned = player.getListOfCards();
		int currCountOfSameCards = 1;
		int maxNumSameCards = 1;
		int totalDiffCards = 1;
		
		String prevCard = cardsOwned.get(0); //get the first card
		for(int i = 1; i < cardsOwned.size(); i++)  //this array is sorted alphabetically
		{
			String currCard = cardsOwned.get(i);
			
			if(Objects.equals(prevCard, currCard))  //if this card is the same as previous
			{
				currCountOfSameCards++;
			}
			else
			{
				totalDiffCards++;
				prevCard = currCard;
				if(currCountOfSameCards > maxNumSameCards)
				{
					maxNumSameCards = currCountOfSameCards;
				}
				currCountOfSameCards = 1;
			}
		}
		
		if(currCountOfSameCards > maxNumSameCards)
		{
			maxNumSameCards = currCountOfSameCards;
		}
		

		
		if( (maxNumSameCards < 3) && (totalDiffCards < 3) )  //no combos available
		{
			return 0;
		}
		
		
		
		if(cardsOwned.size() > 4)  //maximum allowed to hold is 4
		{
			return maxCardAmountReached(player, maxNumSameCards, totalDiffCards);	
		}
		
		
		System.out.println("Do you want to turn in a set, yes or no?");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String option = br.readLine();
		option = option.toLowerCase();
		
		if(Objects.equals("yes", option))  //if input is yes
		{
			return turnInCards(player, maxNumSameCards, totalDiffCards);
			
		}
		
		return -1; //player chose no
	}
	
	
	
	public int turnInCards(Player player, int numSameCards, int numDiffCard)
	{
		ArrayList<String> cardsOwned = player.getListOfCards();

		
		if(numSameCards >= 3)
		{
			System.out.println("We're turning in 3 of your cards of same type");
			cardsOwned = turnInSameCards(cardsOwned);
			
		}
		else   //numDiffCards >= 3
		{
			cardsOwned = turnInDiffCards(cardsOwned);
			System.out.println("We're turning in 3 different cards");

		}
		
		int additionalUnits = getNextSetValue();
		setsTurnedIn++;	
		
		
		return additionalUnits;
	}
	
	
		
	public int maxCardAmountReached(Player player, int numSameCards, int numDiffCards) throws IOException
	{
		ArrayList<String> cardsOwned = player.getListOfCards();
		int additionalUnits = 0;
		
		while(cardsOwned.size() > 4)
		{
			System.out.println("You have more than 4 cards so you must turn some in now");

			System.out.println(numSameCards + " h " + numDiffCards);
			if(numSameCards >= 3 && numDiffCards >= 3)
			{
				System.out.println("Type '1' to turn in 3 of the same cards, or '2' to turn in 3 different cards, else we'll randomly choose for you");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String option = br.readLine();
				
				if(Objects.equals("1", option))
				{
					System.out.println("Okay, we're turning in 3 of the same cards");
					cardsOwned = turnInSameCards(cardsOwned);
					
				}
				else
				{
					System.out.println("Okay, we're turning in 3 different cards");
					cardsOwned = turnInDiffCards(cardsOwned);
				}
			}
			else if(numSameCards >= 3)
			{
				System.out.println("We're turning in 3 of your cards of same type");
				cardsOwned = turnInSameCards(cardsOwned);
				
			}
			else //num diff cards > 3
			{
				System.out.println("We're turning in 3 different cards");

				cardsOwned = turnInDiffCards(cardsOwned);
			}
			
			additionalUnits = additionalUnits + getNextSetValue();
			setsTurnedIn++;
			
			
		}
		
		
		return additionalUnits;
	}
	

	
	public ArrayList<String> turnInDiffCards(ArrayList<String> cardList)
	{
		int diffCardPosition[] = new int[3]; // = new ArrayList<Integer>();
		//diffCardPositions.add(0);
		diffCardPosition[0] = 0;
		
		String firstCard = cardList.get(0);
		String secondCard = "";
		for(int i = 0; i < cardList.size(); i++)
		{
			if(Objects.equals(firstCard, cardList.get(i)))
			{
				continue;
			}
			
			
			if(Objects.equals(secondCard, "")) //if second card is still an empty string
			{
				secondCard = cardList.get(i);
				diffCardPosition[1] = i;

				//diffCardPositions.add(i);
				continue;
			}
			else if(Objects.equals(secondCard, cardList.get(i))) //if our card is already in our diffcardPositions
			{
				continue;
			}
			else
			{
				diffCardPosition[2] = i;

				//diffCardPositions.add(i);
				break;
			}
			
		}
		
		cardList.remove(diffCardPosition[2]);
		cardList.remove(diffCardPosition[1]);
		cardList.remove(diffCardPosition[0]);

//		cardList.remove(diffCardPosition.get(1));
//		cardList.remove(diffCardPosition.get(0));

		
		return cardList;
	}
	
	
	
	
	
	public ArrayList<String> turnInSameCards(ArrayList<String> cardList)
	{
		int numSameCards = 1;
		String prevCard = cardList.get(0);
		int lastPosition = 0;
		for(int i = 1; i < cardList.size(); i++)
		{
			if(Objects.equals(prevCard, cardList.get(i)))
			{
				numSameCards++;
				if(numSameCards == 3)
				{
					lastPosition = i;
					break;
				}
			}
			else
			{
				numSameCards = 1;
			}

		}
				
		cardList.remove(lastPosition);
		cardList.remove(lastPosition - 1);
		cardList.remove(lastPosition - 2);
		
		
		return cardList;
		
	}
	
	
	public int getNextSetValue()
	{
		switch(setsTurnedIn)
		{
		case 0:
			return 4;
		case 1:
			return 6;
		case 2:
			return 8;
		case 3:
			return 10;
		case 4:
			return 12;
		default:
			return 5 * (setsTurnedIn - 2);
				
		}
	}

}
