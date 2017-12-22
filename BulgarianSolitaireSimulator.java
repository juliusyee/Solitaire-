// Name: Julius Yee
// USC NetID: 2037-8984-92
// CSCI455 PA2
// Fall 2017

import java.util.ArrayList;
import java.util.Scanner;

/**
   Main Program that runs the Bulgarian Solitaire using the SolitaireBoard
   class
   * If command line argument -u is entered, the user must input a valid initial 
   configuration of the cards.
   * If command line argument -s is entered, the user must hit enter to see the
   results of each round of play.
 */

public class BulgarianSolitaireSimulator 
{
   public static void main(String[] args) 
   {
	   //Prompt for command line arguments
      boolean singleStep = false;
      boolean userConfig = false;

      for (int i = 0; i < args.length; i++) 
      {
         if (args[i].equals("-u"))
         {
            userConfig = true;
         }
         else if (args[i].equals("-s")) 
         {
            singleStep = true;
         }
      }
      
      //First, initialize the Solitaire Board either with user input or random
      SolitaireBoard game;
      
      if (userConfig)
      {
    	  ArrayList<Integer> userInput = generateUserInput();
    	  game = new SolitaireBoard(userInput);
      }
      else
      {
    	  //Creates a game board with random initial configuration
    	  game = new SolitaireBoard();
      }
      
      //Determine the way the results of the game will be displayed to the user
      if (singleStep)
      {
    	  printEachTurn(game);
      }
      else
      {
    	  printAllTurns(game);
      }
   }
   
   
    // Private Static Methods
   /**
    * This function checks whether the random generated input of numbers is valid
   @param ArrayList of Integers for the number of cards in each pile
   @return Returns true iff the piles of cards follow the representation 
   invariants of the SolitaireBoard class
   */
   private static boolean isValidInput(ArrayList<Integer> arr)
   {
	   int remaining = SolitaireBoard.CARD_TOTAL;
	   for (int i = 0; i < arr.size(); i++)
	   {
		   if (arr.get(i) <= 0)
		   {
			   return false;
		   }
		   remaining -= arr.get(i);
	   }
	   
	   //After reading all the numbers, check if it adds to CARD_TOTAL
	   if (remaining != 0)
	   {
		   return false;
	   }
	   else
	   {
		   return true;
	   }
   }
   
   /**
    * This function repeatedly prompts the user for the number of cards per pile until
    * a valid input is accepted
   @return Returns an ArrayList of Integers containing the valid input for number
   of cards per pile
   */
   private static ArrayList<Integer> generateUserInput()
   {
	  boolean isValidInput = false;
	  ArrayList<Integer> input = new ArrayList<Integer>();
	  
	  System.out.println("Number of total cards is " + SolitaireBoard.CARD_TOTAL);
 	  System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");
 	  
 	  //Continue to prompt the user until a valid input is given
 	  while (!isValidInput)
 	  {
 		  System.out.println("Please enter a space-separated list of positive integers followed by newline: ");
     	  Scanner in = new Scanner(System.in);
     	  String line = in.nextLine();
     	  
     	  Scanner lineScanner = new Scanner(line);
     	  input.clear();
     	  while (lineScanner.hasNextInt())
     	  {
     		  input.add(lineScanner.nextInt());
     	  }
     	  
     	  if (!lineScanner.hasNext() && isValidInput(input))
     	  {
     		  isValidInput = true;
     	  }
     	  else
     	  {
     		  System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be " + SolitaireBoard.CARD_TOTAL);
     	  }
 	  }
 	  return (input);
   }
   
   /**
    *This function plays one round in Bulgarian Solitaire, printing the board 
    *representation turn by turn, prompting the user to hit enter to see the next round
   @param Accepts an initialized SolitaireBoard object to play the game
   */
   private static void printEachTurn(SolitaireBoard game)
   {
	   System.out.println("Initial Configuration: " + game.configString());
 	  int counter = 1;
 	  while(!game.isDone())
 	  {
 		  Scanner in = new Scanner(System.in);
 		  game.playRound();
 		  System.out.println("[" + counter + "]" + " Current Configuration: " + game.configString());
 		  counter++;
 		  System.out.print("<Type return to continue>");
 		  in.nextLine();
 	  }
 	  System.out.println("Done!");
   }
   
   /**
    *This function plays one round in Bulgarian Solitaire, printing the board 
    *representation for each turn at once, displaying how the game went
   @param Accepts an initialized SolitaireBoard object to play the game
   */
   private static void printAllTurns(SolitaireBoard game)
   {
	   System.out.println("Initial Configuration: " + game.configString());
 	  int counter = 1;
 	  while(!game.isDone())
 	  {
 		  game.playRound();
 		  System.out.println("[" + counter + "]" + " Current Configuration: " + game.configString());
 		  counter++;
 	  }
 	  System.out.println("Done!");
   }
  
}