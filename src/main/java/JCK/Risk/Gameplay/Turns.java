package JCK.Risk.Gameplay;

import java.util.Arrays;
import java.util.Collections;

import JCK.Risk.Locations.Continent;
import JCK.Risk.Locations.Territory;

public class Turns {
	
	public void initialSetup(int numPlayers)
	{
		
		
	}
	
	
	public void beginBattle(Territory defendingTerr, Territory attackingTerr)
	{
		String attackerName = attackingTerr.getOwner();
		String defenderName = defendingTerr.getOwner();
		
		//int maxAttackingSoldiers = attackingTerr.numSoldiersHere - 1;
		//int maxDefendingSoldiers = defendingTerr.numSoldiersHere - 1;
		
		while(attackingTerr.numSoldiersHere != 1 || defendingTerr.numSoldiersHere != 0)
		{
			int attackingSoldiers = this.numAttackers(attackingTerr);
			int defendingSoldiers = this.numDefenders(defendingTerr);
			
			System.out.println(attackerName + " your rolls are "); 
			Integer[] attackerRolls = this.getRollsSorted(attackingSoldiers);
			
			System.out.println(defenderName + " your rolls are "); 
			Integer[] defenderRolls = this.getRollsSorted(defendingSoldiers);
			
			//TODO: battle sequence
			
			int i = 0;
			
			System.out.println("The results are:");
			while(i < defenderRolls.length && i < attackerRolls.length)
			{
				System.out.print("The winner for the roll " + (i+1)  + " is ");
				
				if(attackerRolls[i] > defenderRolls[i])
				{
					System.out.println(attackerName);
					defendingTerr.numSoldiersHere -= 1;
				}
				else
				{
					System.out.println(defenderName);
					attackingTerr.numSoldiersHere -= 1;

				}
				
				i++;
				
			}
			
			System.out.println(attackerName + " remaining soldiers are " + attackingTerr.numSoldiersHere);
			System.out.println(defenderName + " remaining soldiers are " + defendingTerr.numSoldiersHere);
			
			if(attackingTerr.numSoldiersHere == 1 || defendingTerr.numSoldiersHere == 0)
			{
				break;
			}
		}
		
		
		System.out.print("The winner of this battle is: ");
		if(attackingTerr.numSoldiersHere == 1)
		{
			System.out.println(defenderName);
		}
		else
		{
			System.out.println(attackerName);
			System.out.println(defendingTerr.territoryName + " is now yours");
		}
		

	}
	
	
	public Integer[] getRollsSorted(int numberOfRolls)
	{
		Integer[] rollsArray = new Integer[numberOfRolls];
		
		for(int i = 0; i < rollsArray.length; i++)
		{
			rollsArray[i] = Dice.roll();
			System.out.println(rollsArray[i]);
			
		}
		
		
		Arrays.sort(rollsArray, Collections.reverseOrder());
		
		return rollsArray;
	}
	
	public int numAttackers(Territory attackingTerr)
	{
		if(attackingTerr.numSoldiersHere > 3)
		{
			return 3;
		}
		else if(attackingTerr.numSoldiersHere == 3)
		{
			return 2;
		}
		else  //if == 2
		{
			return 1;
		}
		
	}
	
	public int numDefenders(Territory defendingTerr)
	{
		if(defendingTerr.numSoldiersHere >= 2)
		{
			return 2;
		}
		else
		{
			return 1;
		}
		
	}

	
	
	
	
	
	public void attackTerritory(String territoryName, Continent currContinent)
	{
		Territory terrBeingAttacked = currContinent.getTerritory(territoryName);
		
		
	}
}
