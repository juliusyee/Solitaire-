// Name: Julius Yee
// USC NetID: 2037-8984-92
// CSCI455 PA2
// Fall 2017

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*
   class SolitaireBoard
   The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
   by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
   for CARD_TOTAL that result in a game that terminates.
   (See comments below next to named constant declarations for more details on this.)
 */

public class SolitaireBoard 
{

   public static final int NUM_FINAL_PILES = 9;
   // number of piles in a final configuration
   // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)
   
   public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
   // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
   // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
   // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES   
   
   /**
      Representation invariant:
      - there are always CARD_TOTAL cards on the board
      - sum of all the pile values is equal to CARD_TOTAL
      - no negative pile numbers in an array element
	  - numPiles cannot be less than 0
    */
   
   //Instance Variables
   private int[] gameBoard;
   private Random generator;
   private int numPiles;
  
 
   /**
    *Constructor
     Creates a solitaire board with the configuration specified in piles.
     piles has the number of cards in the first pile, then the number of cards in the second pile, etc.
     PRE: piles contains a sequence of positive numbers that sum to SolitaireBoard.CARD_TOTAL
     @param ArrayList storing the number of cards in a pile in each element
   */
   public SolitaireBoard(ArrayList<Integer> piles) 
   {
	  gameBoard = new int[CARD_TOTAL];
	  numPiles = 0; 
	  
	  //Fill in the gameBoard array with the number of cards in each pile
	  for (int i = 0; i < piles.size(); i++)
	  {
		  gameBoard[i] = piles.get(i);
		  numPiles++;
	  }
    assert isValidSolitaireBoard();
   }
 
   
   /**
    * Constructor
      Creates a solitaire board with a random initial configuration.
   */
   public SolitaireBoard() 
   {
	   gameBoard = new int[CARD_TOTAL];
	   numPiles = 0;
	   generator = new Random(); 
	   
	   int totalSum = 0;
	   int counter = 0;
	   
	   //the random # of cards per pile can only add to CARD_TOTAL
	   while(totalSum != CARD_TOTAL)
	   {
		   int randomPile = generator.nextInt(CARD_TOTAL - totalSum + 1);
		   if (randomPile != 0)
		   {
			   totalSum += randomPile;
			   gameBoard[counter] = randomPile;
			   counter++;
			   numPiles++;
		   }
	   }
	   assert isValidSolitaireBoard();
   }
  
   
   /**
      Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
      of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
      The old piles that are left will be in the same relative order as before, 
      and the new pile will be at the end.
    */
   public void playRound() 
   {   
	   int newPileSum = numPiles;
	   
	   //Subtract one card from each pile
	   for (int i = 0; i < numPiles; i++)
	   {
		  gameBoard[i]--;
	   }
	   gameBoard[numPiles] = newPileSum;
	   numPiles++;
	   
	   //Take out the empty piles and shift the non-zero piles to the most left
	   int[] temp = Arrays.copyOf(gameBoard,gameBoard.length);
	   int currentIndexTemp = 0;
	   for (int currentIndexGameBoard = 0; currentIndexGameBoard < numPiles;)
	   {
		   if (temp[currentIndexTemp] != 0)
		   {
			   gameBoard[currentIndexGameBoard] = temp[currentIndexTemp];
			   currentIndexGameBoard++;
			   currentIndexTemp++;
		   }
		   else
		   {
			   //We found a pile that just became empty in this round
			   numPiles--;
			   gameBoard[numPiles] = 0;
			   currentIndexTemp++;
		   }
	   }
	   assert isValidSolitaireBoard();
   }
   
   /**
      Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
      piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order.
      @return Returns true iff there are NUM_FINAL_PILES piles that are of sizes 1,2,3,...,NUM_FINAL_PILES in any order
    */
   
   public boolean isDone() 
   {
	   assert isValidSolitaireBoard();
	   
	   //quick if statement to see if we even need to check the contents of the board
	  if (numPiles == NUM_FINAL_PILES)
	  {
		  //We create a copy of the gameBoard and sort it in increasing order
		  //A finished game will result in 1,2,3,...,NUM_FINAL_PILES in temp
		  int[] temp = Arrays.copyOf(gameBoard, numPiles);
		  Arrays.sort(temp);
		  for (int i = 0; i < temp.length; i++)
		  {
			  if (temp[i] != i + 1)
			  {
				  return false;
			  }
		  }
		  return true;
	  }
	  else
	  {
	      return false;
	  }
   }

   
   /**
      Returns current board configuration as a string with the format of
      a space-separated list of numbers with no leading or trailing spaces.
      The numbers represent the number of cards in each non-empty pile.
      @return Returns a string representation of gameBoard (number of cards in each pile 
      with a space in between each value)
    */
   public String configString()
   {
	   //Convert the integer array into a string
	  String output = Arrays.toString(gameBoard);
	  
	  //Replace the brackets, commas, and empty piles with empty characters and trim the spaces
	  output = output.replace("[","");
	  output = output.replace("]","");
	  output = output.replace(",","");
	  output = output.replace(" 0", "");
	  output = output.trim();
	  
	  assert isValidSolitaireBoard();
	  return (output);
   }
   
   
   /**
    * Checks if the solitaire board has CARD_TOTAL
      @return Returns true iff the solitaire board data is in a valid state
      (See representation invariant comment for more details.)
    */
   private boolean isValidSolitaireBoard() 
   {
	   int sumOfPiles = 0;
	   for (int i = 0; i < numPiles; i++)
	   {
		   if (gameBoard[i] < 0)
		   {
			   return false;
		   }
		   sumOfPiles += gameBoard[i];
	   }
	   return(sumOfPiles == CARD_TOTAL ? true:false);
   }
}