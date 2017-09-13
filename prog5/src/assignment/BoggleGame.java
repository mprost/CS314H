package assignment;

import java.awt.Point;
import java.util.Collection;
import java.util.List;

/**
 * Boggle game interface
 */

public interface BoggleGame {
    static public enum SearchTactic {
        SEARCH_BOARD,
        SEARCH_DICT
    }

    public static SearchTactic SEARCH_DEFAULT = SearchTactic.SEARCH_BOARD;

    /**
     * Creates a new Boggle game using a size x size board and the
     * cubes specified in the file cubeFile.
     *
     * @param size        The size of the Boggle board
     * @param numPlayers  The number of players,
     *                    Must have at least one player.
     *                    Players are numbered from 0 to numPlayers - 1.
     * @param cubeFile    Filename of a file containing the cubes.
     * @param dict        The Dictionary of valid words.
     */
    void newGame(int size, int numPlayers,
                 String cubeFile, BoggleDictionary dict);


    /**
     * Returns a size x size character array representing the Boggle
     * board, in row-major order.
     */
    char [][] getBoard();


    /**
     * Adds a word to the player's list and returns the point value
     * of the word.  If the word is invalid or the player cannot add
     * the word, it is worth zero points and is not actually added to
     * the player's list.  Player should not be able to add the same
     * word multiple times.
     *
     * @param word      The word to add.
     * @param player    The player who gets credit for the word.
     *
     * @return          The number of points earned by this word.
     */
    int addWord(String word, int player);


    /**
     * Returns a list of Points (with respect to the board array)
     * showing the previous successfully added word.  If there is no
     * previous word, return null.
     *
     * The coordinates are listed by letter, then row, then column.
     * That is, if coords is the return value, then:
     *
     * coords.length is the length of the last word added
     * coords.get(0) is the position of the first letter of the last word
     * coords.get(1) is the position of the second letter...
     * ...and so on
     */
    List<Point> getLastAddedWord();


    /**
     * Sets the game board to the given board (also in row-major
     * order, sets all player scores and lists to zero/empty.  Very
     * useful for debugging or playing a previous game.  The board
     * must be square.  Other game-related parameters (like the
     * dictionary) should be left as is.
     */
    void setGame(char [][] board);


    /**
     * Returns a collection containing all valid words in the current
     * Boggle board. Uses the current search tactic.
     */
    Collection<String> getAllWords();


    /**
     * Sets the search tactic (used by getAllWords()) to the given
     * tactic.  Valid tactics are defined earlier.  The default tactic
     * should be SEARCH_DEFAULT.  An invalid tactic resets to default.
     *
     * @param tactic The search tactic to use
     */
    void setSearchTactic(SearchTactic tactic);

    /**
     * Returns the current scores for all players
     */
    int[] getScores();

}
