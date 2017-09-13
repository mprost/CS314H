package assignment;
import java.util.Iterator;

public interface Treap<K extends Comparable<K>, V> extends Iterable<K> {

    static final int MAX_PRIORITY = 65535;

    /**
     * Retrieves the value associated with a key in this dictionary.
     * If the key is null or the key is not present in this
     * dictionary, this method returns null.
     *
     * @param key   the key whose associated value
     *              should be retrieved
     * @return      the value associated with the key, or
     *              null if the key is null or the key is not in
     *              this treap
     */
    V lookup(K key);

    /**
     * Adds a key-value pair to this dictionary.  Any old value
     * associated with the key is lost.  If the key or the value is
     * null, the pair is not added to the dictionary.
     *
     * @param key      the key to add to this dictionary
     * @param value    the value to associate with the key
     */
    void insert(K key, V value);

    /**
     * Removes a key from this dictionary.  If the key is not present
     * in this dictionary, this method does nothing.  Returns the
     * value associated with the removed key, or null if the key
     * is not present.
     *
     * @param key      the key to remove
     * @return         the associated with the removed key, or null
     */
    V remove(K key);

    /**
     * Splits this treap into two treaps.  The left treap should contain
     * values less than the key, while the right treap should contain
     * values greater than or equal to the key.
     *
     * @param key    the key to split the treap with
     * @return       the left treap in index 0, the right in index 1
     */
    Treap<K, V> [] split(K key);

    /**
     * Joins this treap with another treap.  If the other treap is not
     * an instance of the implementing class, both treaps are unmodified.
     * At the end of the join, this treap will contain the result.
     * This method may destructively modify both treaps.
     *
     * @param t    the treap to join with
     */
    void join(Treap<K, V> t);

    /**
     * Melds this treap with another treap.  If the other treap is not
     * an instance of the implementing class, both treaps are unmodified.
     * At the end of the meld, this treap will contain the result.  This
     * method may destructively modify both treaps.
     *
     * @param t    the treap to meld with.  t may be modified.
     */
    void meld(Treap<K, V> t) throws UnsupportedOperationException;

    /**
     * Removes elements from another treap from this treap.  If the
     * other treap is not an instance of the implementing class,
     * both treaps are unmodified.  At the end of the difference,
     * this treap will contain no keys that were in the other
     * treap.  This method may destructively modify both treaps.
     *
     * @param t   a treap containing elements to remove from this
     *            treap.  t may be destructively modified.
     */
    void difference(Treap<K, V> t) throws UnsupportedOperationException;


    /**
     * Build a human-readable version of the treap.
     * Each node in the treap will be represented as
     *
     *     [priority] <key, value>\n
     *
     * Subtreaps are indented one tab over from their parent for
     * printing.  This method prints out the string representations
     * of key and value using the object's toString(). Treaps should
     * be printed in pre-order traversal fashion.
     */
    String toString();


    /**
     * @return a fresh iterator that points to the first element in
     * this Treap and iterates in sorted order.
     */
    Iterator<K> iterator();

    /**
     * Returns the balance factor of the treap.  The balance factor
     * is the height of the treap divided by the minimum possible
     * height of a treap of this size.  A perfectly balanced treap
     * has a balance factor of 1.0.  If this treap does not support
     * balance statistics, throw an exception.
     */
    double balanceFactor() throws UnsupportedOperationException;
}
