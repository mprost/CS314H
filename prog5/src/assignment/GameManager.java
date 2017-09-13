package assignment;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.io.*;
import static java.lang.System.*;

public class GameManager implements BoggleGame {
   //Represents the search strategy that getAllWords implements.
   private SearchTactic currentTactic;
   
   //Represents the last scored word in the game.
   private String lastWord;
   //Represents the points on the board where the last scored word was found.
   private List<Point> lastPath;
   
   //Board containing all of the playable letters as they are arranged.
   private char[][] gameBoard;
   //Array containing the scores of players.
   private int[] scores;
   
   //The dictionary that the GameManager references for checking words.
   private BoggleDictionary dictionary;
   //A map of all valid words in the board as found by the getAllWords search.
   //Stores unguessed words as well as words found by players.
   private HashMap<Integer, Collection<String>> allWords;
   
   
   //Initializes the game.
   public void newGame(int size, int numPlayers, String cubeFile, 
         BoggleDictionary dict) {
      //Tracks the validity of the parameters.
      boolean error = false;
      //Checks if the file exists.
      File file = new File(cubeFile);
      if(!file.exists()) {
         error = true;
         err.println("Error: The file \""+ cubeFile +"\" could not be found.");
      }
      //Checks that the size is valid.
      if(size <= 0) {
         error = true;
         err.println("Error: Size must be > 0.");
      }
      //Checks that the number of players is valid.
      if(numPlayers <= 0) {
         error = true;
         err.println("Error: Number of Players must be > 0.");
      }
      //Checks that the dictionary has been initialized.
      if(dict == null) {
         error = true;
         err.println("Error: Dictionary not initialized.");
      }
      
      //Initializes the letter cubes.
      String[] cubes = new String[size * size];
      //If the source file contains less than k characters, the program will
      //terminate, and an error message will be sent.
      if(!error) {
         try{
            Scanner scan = new Scanner(new File(cubeFile));
            //Iterates through the cubes.
            for(int index = 0; index < size * size; index++) {
               //Changes the cubes to lower case for consistency.
               cubes[index] = scan.nextLine().toLowerCase();
            }
            scan.close();
         }
         //Checks if the file could not be read due to insufficient number of 
         //cubes present.
         catch(Exception e) {
            error = true;
            err.println("Error: Wrong number of cubes in \"" + cubeFile + "\".");
         }
      }
      
      if(!error) {
         //Initializes the board properties.
         gameBoard = new char[size][size];
         scores = new int[numPlayers + 1];
         dictionary = dict;
         currentTactic = SearchTactic.SEARCH_DICT;
         
         //Counts the number of cubes in the board.
         int count = cubes.length;
         //Randomizes the order of the cubes.
         while(count-- > 1) {
            //Randomly selects a cube.
            int index = (int)(Math.random() * count);
            //Moves selected cube to the edge of the array to eliminate from
            //consideration without shifting every following element.
            String temp = cubes[index];
            cubes[index] = cubes[count - 1];
            cubes[count - 1] = temp;
         }
         
         //Loads the gameBoard.
         for(int r = 0; r < gameBoard.length; r++) {
            for(int c = 0; c < gameBoard[r].length; c++) {
               //Increments the cube as the board matrix is traced.
               String cube = cubes[r * gameBoard[r].length + c];
               //Selects a random letter from the cube.
               gameBoard[r][c] = cube.charAt((int)(Math.random() * 
                     cube.length()));
            }
         }
         //Loads all words from the dictionary.
         allWords = new HashMap<Integer, Collection<String>>();
         allWords.put(-1, (HashSet<String>) getAllWords());
         //Adds the combined scores of all words to the potential machine 
         //score.
         for(String word : allWords.get(-1)) {
            scores[scores.length - 1] += (word.length() - 3);
         }
      }
   }
   
   //Returns a copy of the game board.
   public char[][] getBoard() {
      //Checks if the gameBoard has been successfully initialized.
      if(gameBoard == null) {
         err.println("Error: game board was not initialized.");
         return new char[0][0];
      }
      else {
         //Creates a copy of the actual game board.
         char[][] returnBoard = new char[gameBoard.length][];
         for(int r = 0; r < gameBoard.length; r++) {
            //Copies each row of the original board into the returned copy.
            returnBoard[r] = Arrays.copyOf(gameBoard[r], gameBoard[r].length);
         }
         //Returns a copy of the game board to avoid allowing modification by the 
         //user.
         return returnBoard;
      }
   }
   
   //Attempts to submit a word and modifies the users' scores.
   public int addWord(String word, int player) {
      //Checks if the board was successfully initialized.
      if(scores == null) {
         err.println("Error: game board was not initialized.");
         return 0;
      }
      //Checks that a valid player number is called.
      if(player < 0 || player > scores.length) {
         err.println("Error: Player number must be >= 0 and < numPlayers.");
         return 0;
      }
      //Changes the word to lower case for consistency.
      String currentWord = word.toLowerCase();
      //Returns 0 if the word is not long enough to produce a point-scoring
      //word.
      if(currentWord.length() < 4) {
         return 0;
      }
      //Returns 0 if the word does not appear in the set of valid, unguessed
      //words.
      if(!allWords.get(-1).contains(currentWord)) {
         return 0;
      }
      
      //Otherwise the word is valid and unguessed.
      //Stores the current word for UI access.
      lastWord = currentWord;
      //Modifies the score of the player.
      scores[player] += (currentWord.length() - 3);
      //Removes the word from the set of unguessed words.
      allWords.get(-1).remove(currentWord);
      //Creates a set to store the current player's scored words. 
      if(allWords.get(player) == null) {
         allWords.put(player, new HashSet<String>());
      }
      //Adds the scored word to the set of words scored by the player.
      allWords.get(player).add(currentWord);
      //Subtracts the point value from the potential machine score.
      scores[scores.length - 1] -= (currentWord.length() - 3);
      
      return currentWord.length() - 3;
   }
   
   //Returns the coordinates of the last word successfully scored.
   public List<Point> getLastAddedWord() {
      //Returns empty list if there have been no successfully scored words.
      if(lastWord == null) {
         return new LinkedList<Point>();
      }
      //Tracks if the board finds a solution.
      boolean solved = false;
      //Loops through all characters in the board to find the solution to the 
      //last added word.
      for(int r = 0; r < gameBoard.length; r++) {
         for(int c = 0; c < gameBoard[r].length && !solved; c++) {
            //Checks if a solution exists starting in every location on the 
            //board.
            solved = findString(lastWord, 0, r, c, new LinkedList<Point>(), new
                  HashSet<String>());
         }
      }
      return lastPath;
   }
   
   //Modifies the game state to match that of a given board.
   public void setGame(char[][] board) {
      int row = board.length;
      //Resets the game board.
      gameBoard = new char[row][];
      //Copies each row from the parameter into the game board.
      for(int r = 0; r < row; r++) {
         gameBoard[r] = Arrays.copyOf(board[r], board[r].length);
      }
      //Reinitializes the list of valid words based on what is in the new 
      //board.
      allWords = new HashMap<Integer, Collection<String>>();
      allWords.put(-1, (HashSet<String>) getAllWords());
   }
   
   //Returns a collection of all valid words in a given game state.
   public Collection<String> getAllWords() {
      //Stores the words in a HashSet for quick access.
      HashSet<String> words = new HashSet<String>();
      
      //Implements board search strategy.
      if(currentTactic == SearchTactic.SEARCH_BOARD) {
         //Searches at every point on the board.
         for(int r = 0; r < gameBoard.length; r++) {
            for(int c = 0; c < gameBoard[r].length; c++) {
               //Builds words beginning with every letter on the board.
               buildString("", r, c, 
                     new boolean[gameBoard.length][gameBoard[r].length], 
                     words);
            }
         }
      }
      
      //Implements dictionary search strategy.
      else {
         //Stores a set of valid letters for optimization.
         HashSet<Character> letters = (HashSet<Character>) getValidLetters();
         //Creates a dictionary Iterator.
         Iterator<String> it = dictionary.iterator();
         //Iterates through the dictionary wordMap.
         while(it.hasNext()) {
            String word = it.next();
            //Checks if the word is small enough to exist in the board and 
            //large enough to be scorable.
            if(word.length() <= gameBoard.length * gameBoard[0].length &&
                  word.length() >= 4) {
               boolean valid = true;
               //Checks the validity of all letters in the word.
               for(int index = 0; index < word.length() && valid; index++) {
                  //Marks the word as invalid if it contains a letter that is 
                  //not present in the board.
                  if(!letters.contains(word.charAt(index))) {
                     valid = false;
                  }
               }
               //Optimized to only search for valid words in the board.
               if(valid) {
                  boolean solved = false;
                  //Searches through the board until an instance of the word is
                  //found.
                  for(int r = 0; r < gameBoard.length; r++) {
                     for(int c = 0; c < gameBoard[r].length && !solved; c++) {
                        solved = findString(word, 0, r, c, new 
                              LinkedList<Point>(), words);
                     }
                  }
               }
            }
         }
      }
      return words;
   }
   
   //Searches for words, implementing the dictionary search strategy.
   private boolean findString(String word, int index, int row, int col, 
         List<Point> path, Collection<String> words) {
      //Checks if the searched index is not in the bounds of the board.
      if(row < 0 || col < 0 || row >= gameBoard.length || col >= 
            gameBoard[row].length) {
         return false;
      }
      //Checks if the point has been previously added to the path.
      for(Point p : path) {
         if((int) p.getX() == row && (int) p.getY() == col) {
            return false;
         }
      }
      //Checks if the letter at the coordinate is the expected next letter in
      //the word.
      if(gameBoard[row][col] != word.charAt(index)) {
         return false;
      }
      //Creates a new instance of the path.
      List<Point> newPath = new LinkedList<Point>();
      //Copies the old path into the new list.
      for(Point p : path) {
         newPath.add(new Point((int)p.getX(), (int)p.getY()));
      }
      //Adds the current point in the path.
      newPath.add(new Point(row, col));
      
      //Checks if there are no more subsequent letters in the word.
      if(word.substring(index + 1).equals("")) {
         //Adds the word to the Collection of valid words in the board.
         words.add(word);
         //Marks the path of the added word for implementation in
         //getLastAddedWord.
         lastPath = newPath;
         return true;
      }
      
      //Recursively searches for the words in the neighboring cells.
         //Searches from left to right in the cells above the current position.
      return findString(word, index + 1, row - 1, col - 1, newPath, words) ||
            findString(word, index + 1, row - 1, col, newPath, words) ||
            findString(word, index + 1, row - 1, col + 1, newPath, words) ||
            //Searches left and right of the current position.
            findString(word, index + 1, row, col - 1, newPath, words) ||
            findString(word, index + 1, row, col + 1, newPath, words) ||
            //Searches from left to right in the cells below the current 
            //position.
            findString(word, index + 1, row + 1, col - 1, newPath, words) ||
            findString(word, index + 1, row + 1, col, newPath, words) ||
            findString(word, index + 1, row + 1, col + 1, newPath, words);
   }
   
   //Searches for words, implementing the board search strategy.
   private void buildString(String word, int row, int col, boolean[][] path, 
         Collection<String> words) {
      //Checks if the searched index is not in the bounds of the board.
      if(row < 0 || col < 0 || row >= gameBoard.length || col >= 
            gameBoard[row].length || path[row][col]) {
         return;
      }
      //Checks if the current word is a valid scorable word.
      if(word.length() >= 4 && dictionary.contains(word)) {
         words.add(word);
      }
      //Checks if any dictionary words begin with this substring. 
      if(!dictionary.isPrefix(word)) {
         return;
      }
      
      //Builds a new path.
      boolean[][] newPath = new boolean[path.length][];
      //Copies the old path.
      for(int r = 0; r < path.length; r++) {
         newPath[r] = Arrays.copyOf(path[r], path[r].length);
      }
      //Marks the current spot on the path.
      newPath[row][col] = true;
      //Recursively builds words in all adjacent points.
      for(int r = row - 1; r <= row + 1; r++) {
         for(int c = col - 1; c <= col + 1; c++) {
            //Skips over the current position
            if(r != row || c != col) {
               //Checks that the target point is in the bounds of the game 
               //board.
               if(r >= 0 && c >= 0 && r < gameBoard.length && c <
            gameBoard[r].length){
                  buildString(word + gameBoard[r][c], r, c, newPath, words);
               }
               
            }
         }
      }
   }
   
   //Helper method that lists all characters in the board.
   private Collection<Character> getValidLetters() {
      //Stores in a HashSet to remove duplicates and for time efficiency.
      Collection<Character> letters = new HashSet<Character>();
      //Loops through the gameBoard to find all characters.
      for(int r = 0; r < gameBoard.length; r++) {
         for(int c = 0; c < gameBoard[r].length; c++) {
            letters.add(gameBoard[r][c]);
         }
      }
      return letters;
   }
   
   //Changes the game's active search tactic.
   public void setSearchTactic(SearchTactic tactic) {
      currentTactic = tactic;
   }
   
   //Returns the stored player scores.
   public int[] getScores() {
      //Checks if the game board was initialized.
      if(scores == null) {
         return new int[1];
      }
      return scores;
   }
   
   //Returns a sorted list of words that the player(s) did not guess.
   public Collection<String> getMissedWords() {
      //Checks if the board was successfully initialized.
      if(allWords == null) {
         return new ArrayList<String>();
      }
      //Stores words in a TreeSet for alphabetical sorting.
      TreeSet<String> missedWords = new TreeSet<String>();
      //Parses through all unguessed words in the game.
      for(String word : allWords.get(-1)) {
         missedWords.add(word);
      }
      return missedWords;
   }
}
