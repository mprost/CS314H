package assignment;
/**
 * This interface defines the basic operations of a dictionary (collection of
 * words) for use with a game.
 */

public interface BoggleDictionary extends Iterable<String> {
    /**
     * Adds words from a file to this dictionary.  The file should contain
     * one word per line, with the words in ascending lexicographic order.
     *
     * @param filename    the String filename
     */
    void loadDictionary(String filename);

    /**
     * Tests whether a String is the prefix of some word in this
     * dictionary.
     *
     * @param prefix    the String prefix to test
     * @return          <code>true</code> if the String is the prefix
     *                  of some word in this dictionary,
     *                  <code>false</code> otherwise
     */
    boolean isPrefix(String prefix);

    /**
     * Tests whether a String is a word in this dictionary.
     *
     * @param word    the String word to test
     * @return        <code>true</code> if the String is a word in this
     *                dictionary, <code>false</code> otherwise
     */
    boolean contains(String word);
}
