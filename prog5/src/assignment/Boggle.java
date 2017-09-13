package assignment;
import java.util.List;
import java.util.*;
import java.awt.*;
import java.io.*;
import static java.lang.System.*;

public class Boggle {
   
   public static void main(String[] args) {
      //Initializes the BoggleGame and the BoggleDictionary.
      GameManager game = new GameManager();
      GameDictionary dictionary = new GameDictionary();
      
      Scanner keyBoard = new Scanner(in);
      //Instantiates the intializer parameters.
      String wordFile = "";
      int size = -1;
      int numPlayers = -1;
      String cubeFile = "";
      String next = "";
      
      boolean error = false;
      //Tests that a valid Integer was given for the board size.
      do {
         error = false;
         try {
            out.println("How wide do you want the board?");
            next = keyBoard.next();
            size = Integer.parseInt(next);
            if(size <= 0) {
               error = true;
               err.println("Error: size must be > 0.");
            }
         }
         catch(Exception e) {
            error = true;
            err.println("Error: " + next + " not a valid integer.");
         }
         
      } while(error);
      //Tests that a valid Integer was given for the number of players.
      do {
         error = false;
         try {
            out.println("How many players are playing?");
            next = keyBoard.next();
            numPlayers = Integer.parseInt(next);
            if(numPlayers <= 0) {
               error = true;
               err.println("Error: numPlayers must be > 0.");
            }
         }
         catch(Exception e) {
            error = true;
            err.println("Error: " + next + " not a valid integer.");
         }
         
      } while(error);
      //Tests that a valid String was read.
      do {
         error = false;
         try {
            out.println("What is the cubeFile?");
            cubeFile = keyBoard.next();
         }
         catch(Exception e) {
            error = true;
            err.println("Error reading in the String.");
         }
      } while(error);
      //Tests that a valid String was read
      do {
         error = false;
         try{
            out.println("What is the file name for the dictionary?");
            wordFile = keyBoard.next();
         }
         catch(Exception e) {
            error = true;
            err.println("Error reading in the String.");
         }
      } while(error);
      
      //Initializes the boggle game.
      dictionary.loadDictionary(wordFile);
      game.newGame(size, numPlayers, cubeFile, dictionary); 
      
      
      out.println("Time to play!");
      
      out.println("To give up, type q");
      
      int currentPlayer = 0;
      
      char[][] board = game.getBoard();
      out.println();
      printBoard(board, new LinkedList<Point>());
      out.println("Player 1's turn.");
      //Accounts for end of previous line.
      keyBoard.nextLine();
      int lastWord = 0;
      List<Point> lastPath = new LinkedList<Point>();
      //Defines outerloop and reads until the process is ended.
      outerloop:
      while(keyBoard.hasNext()) {
         String s = keyBoard.nextLine();
         //Ends the game.
         if(s.equals("q")) {
            //Displays scores
            out.println("Final Scores: ");
            int[] scores = game.getScores();
            //Displays player scores.
            currentPlayer = 1;
            for(int index = 0; index < scores.length - 1; index++) {
               if(scores[index] == 1) {
                  out.println("Player " + currentPlayer + ": " + scores[index] 
                        + " point");
               }
               else {
                  out.println("Player " + currentPlayer + ": " + scores[index] 
                        + " points");
               }
               currentPlayer++;
            }
            //Displays machine scores.
            if(scores[scores.length - 1] == 1) {
               out.println("Machine: " + scores[scores.length - 1] + " point");
            }
            else {
               out.println("Machine: " + scores[scores.length - 1] + 
                     " points");
            }
            
            //Increments to next player.
            currentPlayer++;
            //Displays missed words.
            out.println();
            out.println("These are the words you missed.");
            out.println(game.getMissedWords());
            //Prompts the user to begin a new game.
            out.println("\nDo you want to play again? (y/n)");
            boolean goOn = false;
            //Checks user response.
            while(!goOn) {
               //Ends the game in the case of a negative response.
               String response = keyBoard.nextLine();
               if(response.equals("n")) {
                  break outerloop;
               }
               //Reprompts the user after indecipherable response.
               else if(!response.equals("y")) {
                  out.println("Sorry, I didn't get that.");
               }
               //Begins a new game.
               else {
                  goOn = true;
                  //Reinitializes the board.
                  game.newGame(size, numPlayers, cubeFile, dictionary);
                  printBoard(game.getBoard(), new LinkedList<Point>());
                  currentPlayer = 0;
                  out.println("Player " + (int)(currentPlayer + 1) + 
                        "\'s turn.");
                  continue outerloop;
               }
            }
         }
         //Attempts to score user word.
         lastWord = game.addWord(s, currentPlayer);
         //Increments to next player.
         currentPlayer++;
         currentPlayer %= numPlayers;
         
         board = game.getBoard();
         out.println();
         //Announces 0 points scored and next player's turn.
         if(lastWord == 0) {
            out.println("Not a valid word.\n");
            printBoard(board, new LinkedList<Point>());
            out.println("Player " + (int)(currentPlayer + 1) + "\'s turn.");
         }
         //Announces points scored and next player's turn.
         else {
            if(lastWord == 1) {
               out.println(lastWord +  " point!\n");
            }
            else {
               out.println(lastWord + " points!\n");
            }
            lastPath = game.getLastAddedWord();
            printBoard(board, lastPath);
            out.println("Player " + (int)(currentPlayer + 1) + "\'s turn.");
         }
         
      }
      //Signals end of the game.
      out.println("Game Terminated.");
      
   }
   
   //Helper method prints out current state of the board and highlights last 
   //word added.
   public static void printBoard(char[][] board, List<Point> lastPath) {
      //Checks every coordinate in the board.
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[r].length; c++) {
            boolean traced = false;
            //Checks if coordinate has been added to path.
            for(Point p : lastPath) {
               if((int)p.getX() == r && (int)p.getY() == c) {
                  traced = true;
               }
            }
            //Prints non-traced letters.
            if(!traced) {
               out.print(board[r][c] + " ");
            }
            //Capitalizes traced letters.
            else {
               out.print((char)(board[r][c] - 32) + " ");
            }
         }
         out.println();
      }
      out.println();
   }
   
}
