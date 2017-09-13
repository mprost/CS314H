package assignment;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.ArrayList;

import org.junit.Test;

import assignment.*;
import assignment.BoggleGame.SearchTactic;

public class GameManagerTest {

	private char[][] gameBoard;
	private int[] scores;
	
   int size = 4;
   int numPlayers = 1;
   String cubeFile = "cubes.txt";
	
	@Test
	public void testNewGame() {
	   GameManager myGame = new GameManager();
      GameDictionary myDict = new GameDictionary();
      
     //Loads dictionary, initalizes game, and sets game up.
     myDict.loadDictionary("words.txt");
     myGame.newGame(size, numPlayers, cubeFile, myDict);
      
      assertEquals(1,1);
	}

	//Checks whether the accessor method returns the correct board.
	@Test
	public void testGetBoard() {
		GameManager myGame = new GameManager();
		GameDictionary myDict = new GameDictionary();
		
		//Loads dictionary, initalizes game, and sets game up.
		myDict.loadDictionary("words.txt");
	   myGame.newGame(size, numPlayers, cubeFile, myDict);
	   myGame.setGame(new char[][]{"hole".toCharArray(), "wats".toCharArray(), 
	       "usey".toCharArray(), "oeho".toCharArray()});
	   myGame.setSearchTactic(SearchTactic.SEARCH_BOARD);
	   int currentPlayer = 0;
	   gameBoard = new char[4][];
	   gameBoard[0] = "hole".toCharArray();
	   gameBoard[1] = "wats".toCharArray();
	   gameBoard[2] = "usey".toCharArray();
	   gameBoard[3] = "oeho".toCharArray();
	   
		//Compares the expected board and the board returned from the method.
		char[][] myBoard = myGame.getBoard();
		assertEquals(gameBoard.length, myBoard.length);
		for (int r = 0; r < gameBoard.length; r++) {
			assertArrayEquals(gameBoard[r], myBoard[r]);
		}
	}

	//Checks different possible words to see if the correct point values
	//are assigned to each word.
	@Test
	public void testAddWord() {
		GameManager myGame = new GameManager();
		GameDictionary myDict = new GameDictionary();
		
		//Loads dictionary, initalizes game, and sets game up.
		myDict.loadDictionary("words.txt");
	   myGame.newGame(size, numPlayers, cubeFile, myDict);
	   myGame.setGame(new char[][]{"hole".toCharArray(), "wats".toCharArray(), 
	       "usey".toCharArray(), "oeho".toCharArray()});
	   myGame.setSearchTactic(SearchTactic.SEARCH_BOARD);
	   int currentPlayer = 0;
	   gameBoard = myGame.getBoard();
		
		//First two scores make sure invalid words are given a score of 0.
		int score1 = myGame.addWord("", 0);
		int score2 = myGame.addWord("a", 0);
		
		//Third and fourth scores make sure the score is 3 less than the 
		//word's length because both are valid words in the given board.
		int score3 = myGame.addWord("hole", 0);
		int score4 = myGame.addWord("holes", 0);
		
		assertEquals(0, score1);
		assertEquals(0, score2);
		assertEquals(1, score3);
		assertEquals(2, score4);
	}

	//Checks if the last word has the correct list of points associated with it
	@Test
	public void testGetLastAddedWord() {
		GameManager myGame = new GameManager();
		GameDictionary myDict = new GameDictionary();
		
		//Loads dictionary, initalizes game, and sets game up.
		myDict.loadDictionary("words.txt");
	   myGame.newGame(size, numPlayers, cubeFile, myDict);
	   myGame.setGame(new char[][]{"hole".toCharArray(), "wats".toCharArray(), 
	       "usey".toCharArray(), "oeho".toCharArray()});
	   myGame.setSearchTactic(SearchTactic.SEARCH_BOARD);
	   int currentPlayer = 0;
	   gameBoard = myGame.getBoard();
	   
	   myGame.addWord("hole", 0);
	   
	   //Compares expected list of points to list of points returned 
	   //from method.
	   List<Point> path = myGame.getLastAddedWord();
	   List<Point> myPath = new ArrayList<Point>();
	   myPath.add(new Point(0, 0));
	   myPath.add(new Point(0, 1));
	   myPath.add(new Point(0, 2));
	   myPath.add(new Point(0, 3));
	   
	   assertEquals(myPath, path);
	}

	//Checks if all valid words are returned in the getAllWords method.
	@Test
	public void testGetAllWords() {
		GameManager myGame = new GameManager();
		GameDictionary myDict = new GameDictionary();
		
		//Loads dictionary, initalizes game, and sets game up.
      myDict.loadDictionary("words.txt");
      myGame.newGame(size, numPlayers, cubeFile, myDict);
      myGame.setGame(new char[][]{"fuzz".toCharArray(), "zzzz".toCharArray(), 
         "zzzz".toCharArray(), "zzzz".toCharArray()});
      myGame.setSearchTactic(SearchTactic.SEARCH_BOARD);
      int currentPlayer = 0;
      gameBoard = myGame.getBoard();
      myGame.addWord("fuzz", 0);
     
      //Compares expected string with only string in the Set returned 
      //from the method.
      HashSet<String> words = (HashSet) myGame.getAllWords();
      HashSet<String> myWords = new HashSet<String>();
      myWords.add("fuzz");
     
      assertEquals(myWords, words);
     
      myGame = new GameManager();
      myDict = new GameDictionary();
      
      //Loads dictionary, initalizes game, and sets game up.
      myDict.loadDictionary("simple.txt");
      myGame.newGame(size, numPlayers, cubeFile, myDict);
      myGame.setGame(new char[][]{"matt".toCharArray(), "rohi".toCharArray(), 
         "alvn".toCharArray(), "cats".toCharArray()});
      myGame.setSearchTactic(SearchTactic.SEARCH_BOARD);
      currentPlayer = 0;
      gameBoard = myGame.getBoard();
      myGame.addWord("matt", 0);
      myGame.addWord("rohit", 0);
      myGame.addWord("calvin", 0);
      myGame.addWord("cats", 0);
      //Adds an invalid word.
      myGame.addWord("dogs", 0);
     
      //Compares expected string with only string in the Set returned 
      //from the method.
      words = (HashSet) myGame.getAllWords();
      myWords = new HashSet<String>();
      myWords.add("matt");
      myWords.add("rohit");
      myWords.add("calvin");
      myWords.add("cats");
     
      assertEquals(myWords, words);
	}

	//Checks that the accessor method returns the correct array of integers.
	@Test
	public void testGetScores() {
		GameManager myGame = new GameManager();
		GameDictionary myDict = new GameDictionary();
		
		int[] myScore = myGame.getScores();
		assertEquals(scores, myScore);
	}

	//Checks if all valid words that the player misses are returned in the
	//getMissedWords method.
	@Test
	public void testGetMissedWords() {
		GameManager myGame = new GameManager();
		GameDictionary myDict = new GameDictionary();
		
		//Loads dictionary, initalizes game, and sets game up.
		myDict.loadDictionary("words.txt");
	   myGame.newGame(size, numPlayers, cubeFile, myDict);
	   myGame.setGame(new char[][]{"fuzz".toCharArray(), "zzzz".toCharArray(), 
	       "zzzz".toCharArray(), "zzzz".toCharArray()});
	   myGame.setSearchTactic(SearchTactic.SEARCH_BOARD);
	   int currentPlayer = 0;
	   gameBoard = myGame.getBoard();
	   
	   //Compares expected string with only string in the TreeSet returned 
	   //from the method.
	   TreeSet<String> missed = (TreeSet) myGame.getMissedWords();
	   TreeSet<String> myMissed = new TreeSet<String>();
	   myMissed.add("fuzz");
	   
	   assertEquals(myMissed, missed);
	}

}
