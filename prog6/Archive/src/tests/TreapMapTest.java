package assignment;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import static java.lang.System.*;


public class TreapMapTest {

   @Test
   //Checks that the lookup finds all expected values.
   public void testLookup() {
      //Performs a large number of tests to account for inherent randomness.
      for(int x = 0; x < 100 ; x++) {
         //Initializes a new TreapMap.
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         //Checks that expected values are found.
         assertEquals("A", temp.lookup(1));
         assertEquals("B", temp.lookup(2));
         assertEquals("C", temp.lookup(3));
         assertEquals("D", temp.lookup(4));
         assertEquals("E", temp.lookup(5));
         assertEquals("F", temp.lookup(6));
      }
   }
   
   @Test
   //Checks that duplicates are properly inserted into the TreapMap.
   public void testInsert() {
      //Performs a large number of tests to account for inherent randomness.
      for(int x = 0; x < 100 ; x++) {  
         //Initializes a new TreapMap.
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "D");
         //Inserts duplicate key 5 to replace "D" with "E".
         temp.insert(5, "E");
         temp.insert(6, "F");
         //Checks that expected values are found.
         assertEquals("A", temp.lookup(1));
         assertEquals("B", temp.lookup(2));
         assertEquals("C", temp.lookup(3));
         assertEquals("D", temp.lookup(4));
         assertEquals("E", temp.lookup(5));
         assertEquals("F", temp.lookup(6));
         
      }
   }
   
   @Test
   //Checks that values are properly removed from the TreapMap.
   public void testRemove() {
      //Performs a large number of tests to account for inherent randomness.
      for(int x = 0; x < 100 ; x++) {
         //Initializes a new TreapMap.
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         
         //Removes node 3 (middle key value).
         temp.remove(3);
         //Checks that expected values are found.
         assertEquals("A", temp.lookup(1));
         assertEquals("B", temp.lookup(2));
         assertEquals(null, temp.lookup(3));
         assertEquals("D", temp.lookup(4));
         assertEquals("E", temp.lookup(5));
         assertEquals("F", temp.lookup(6));
         
         //Removes node 6 (largest key value).
         temp.remove(6);
         //Checks that expected values are found.
         assertEquals("A", temp.lookup(1));
         assertEquals("B", temp.lookup(2));
         assertEquals(null, temp.lookup(3));
         assertEquals("D", temp.lookup(4));
         assertEquals("E", temp.lookup(5));
         assertEquals(null, temp.lookup(6));
         
         //Removes node 1.
         temp.remove(1);
         //Checks that expected values are found.
         assertEquals(null, temp.lookup(1));
         assertEquals("B", temp.lookup(2));
         assertEquals(null, temp.lookup(3));
         assertEquals("D", temp.lookup(4));
         assertEquals("E", temp.lookup(5));
         assertEquals(null, temp.lookup(6));
         
         //Initializes regex pattern to match one line of the TreapMap 
         //toString().
         String line = "\t*R?L?.[0-9]+. <[1-6], [A-F]>.*\n";
         //Initializes empty compare String and adds the expected number of 
         //lines.
         String compare = "";
         //Adds the number of expected lines of regex pattern to the compare 
         //String.
         for(int i = 0; i < 3; i++) {
            compare += line;
         }
         //Compares the String representation of the TreapMap to the expected
         //regex pattern.
         String toString = temp.toString();
         assertEquals(true, toString.matches(compare));
      }
   }
   
   @Test
   //Checks that TreapMaps are properly split into two.
   public void testSplit() {
      //Performs a large number of tests to account for inherent randomness.
      for(int x = 0; x < 100 ; x++) {
         //Initializes a new TreapMap.
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         
         //Splits the TreapMap into two smaller subtrees.
         //Also tests that splitting about a present key value maintains the 
         //node.
         Treap<Integer,String>[] subtrees = temp.split(4);
         Treap<Integer,String> small = subtrees[0];
         Treap<Integer,String> big = subtrees[1];
         //Checks that expected values are found in the lesser value subtree.
         assertEquals("A", small.lookup(1));
         assertEquals("B", small.lookup(2));
         assertEquals("C", small.lookup(3));
         assertEquals(null, small.lookup(4));
         assertEquals(null, small.lookup(5));
         assertEquals(null, small.lookup(6));
         //Checks that expected values are found in the greater value subtree.
         assertEquals(null, big.lookup(1));
         assertEquals(null, big.lookup(2));
         assertEquals(null, big.lookup(3));
         assertEquals("D", big.lookup(4));
         assertEquals("E", big.lookup(5));
         assertEquals("F", big.lookup(6));
         
         //Initializes regex pattern to match one line of the TreapMap 
         //toString().
         String line = "\t*R?L?.[0-9]+. <[1-6], [A-F]>.*\n";
         //Initializes empty compare String and adds the expected number of 
         //lines.
         String compare = "";
         //Adds the number of expected lines of regex pattern to the compare 
         //String.
         for(int i = 0; i < 3; i++) {
            compare += line;
         }
         //Compares the String representations of the TreapMaps to the expected
         //regex patterns.
         String leftToString = small.toString();
         assertEquals(true, leftToString.matches(compare));
         String rightToString = big.toString();
         assertEquals(true, rightToString.matches(compare));
      }
   }

   @Test
   //Tests that the iterator finds all expected values in the proper order.
   public void testIterator() {
      //Performs a large number of tests to account for inherent randomness.
      for(int x = 0; x < 100; x++) {
         //Initializes a new TreapMap.
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         
         //Initializes an iterator and a list to store values in.
         Iterator<Integer> it = temp.iterator();
         ArrayList<String> list = new ArrayList<String>();
         //Adds found values to the list.
         while(it.hasNext()) {
            list.add(temp.lookup(it.next()));
         }
         
         //Checks that list is the proper size and contains all of the expected
         //elements in the proper order.
         assertEquals(6, list.size());
         assertEquals("A", list.get(0));
         assertEquals("B", list.get(1));
         assertEquals("C", list.get(2));
         assertEquals("D", list.get(3));
         assertEquals("E", list.get(4));
         assertEquals("F", list.get(5));
         
         //Checks that the iterator functions properly on split subtrees.
         Treap<Integer,String>[] subtrees = temp.split(4);
         //Tests that the expected values are found in the left subtree. 
         Treap<Integer,String> small = subtrees[0];
         it = small.iterator();
         //Adds the found values to a list.
         list = new ArrayList<String>();
         while(it.hasNext()) {
            list.add(small.lookup(it.next()));
         }
         //Checks that list is the proper size and contains all of the expected
         //elements in the proper order.
         assertEquals(3, list.size());
         assertEquals("A", list.get(0));
         assertEquals("B", list.get(1));
         assertEquals("C", list.get(2));
         //Tests that the expected values are found in the left subtree. 
         Treap<Integer,String> big = subtrees[1];
         it = big.iterator();
         //Adds the found values to a list.
         list = new ArrayList<String>();
         while(it.hasNext()) {
            list.add(big.lookup(it.next()));
         }
         //Checks that list is the proper size and contains all of the expected
         //elements in the proper order.
         assertEquals(3, list.size());
         assertEquals("D", list.get(0));
         assertEquals("E", list.get(1));
         assertEquals("F", list.get(2));
         
         //Checks that the iterator functions on two trees after being joined.
         //Adds values from the right subtree into the left.
         small.join(big);
         it = small.iterator();
         list = new ArrayList<String>();
         //Adds the found values to the list.
         while(it.hasNext()) {
            list.add(small.lookup(it.next()));
         }
         //Checks that list is the proper size and contains all of the expected
         //elements in the proper order.
         assertEquals(6, list.size());
         assertEquals("A", list.get(0));
         assertEquals("B", list.get(1));
         assertEquals("C", list.get(2));
         assertEquals("D", list.get(3));
         assertEquals("E", list.get(4));
         assertEquals("F", list.get(5));
      }
   }
   
   @Test
   //Tests that two trees are properly joined together.
   public void testJoin() {
    //Performs a large number of tests to account for inherent randomness.
      for(int x = 0; x < 100; x++) {
         //Initializes a new TreapMap with small values.
         TreapMap<Integer,String> small = new TreapMap<Integer, String>();
         small.insert(1, "A");
         small.insert(2, "B");
         small.insert(3, "C");
         //Initializes a new TreapMap with larger values.
         TreapMap<Integer,String> big = new TreapMap<Integer, String>();
         big.insert(4, "D");
         big.insert(5, "E");
         big.insert(6, "F");
         //Adds the nodes from the smaller TreapMap to the larger one.
         big.join(small);
         //Checks that expected values are found in the TreapMap.
         assertEquals("A", big.lookup(1));
         assertEquals("B", big.lookup(2));
         assertEquals("C", big.lookup(3));
         assertEquals("D", big.lookup(4));
         assertEquals("E", big.lookup(5));
         assertEquals("F", big.lookup(6));
         
         //Initializes regex pattern to match one line of the TreapMap 
         //toString().
         String line = "\t*R?L?.[0-9]+. <[1-6], [A-F]>.*\n";
         //Initializes empty compare String and adds the expected number of 
         //lines.
         String compare = "";
         //Compares the String representations of the TreapMaps to the expected
         //regex patterns.
         String toString = big.toString();
         for(int i = 0; i < 6; i++) {
            compare += line;
         }
         assertEquals(true, toString.matches(compare));
      }
      
      //Repeats the tests except with adding nodes from the larger TreapMap 
      //into the smaller one.
      //Performs a large number of tests to account for inherent randomness.
      for(int x = 0; x < 100; x++) {
         //Initializes a new TreapMap with small values.
         TreapMap<Integer,String> small = new TreapMap<Integer, String>();
         small.insert(1, "A");
         small.insert(2, "B");
         small.insert(3, "C");
         //Initializes a new TreapMap with larger values.
         TreapMap<Integer,String> big = new TreapMap<Integer, String>();
         big.insert(4, "D");
         big.insert(5, "E");
         big.insert(6, "F");
         //Adds the nodes from the smaller TreapMap to the larger one.
         small.join(big);
         //Checks that expected values are found in the TreapMap.
         assertEquals("A", small.lookup(1));
         assertEquals("B", small.lookup(2));
         assertEquals("C", small.lookup(3));
         assertEquals("D", small.lookup(4));
         assertEquals("E", small.lookup(5));
         assertEquals("F", small.lookup(6));
         
         //Initializes regex pattern to match one line of the TreapMap 
         //toString().
         String line = "\t*R?L?.[0-9]+. <[1-6], [A-F]>.*\n";
         //Initializes empty compare String and adds the expected number of 
         //lines.
         String compare = "";
         //Compares the String representations of the TreapMaps to the expected
         //regex patterns.
         String toString = small.toString();
         for(int i = 0; i < 6; i++) {
            compare += line;
         }
         assertEquals(true, toString.matches(compare));
      }
   }
   
   @Test
   //Tests that the toString method returns a properly formatted String 
   //representation of the TreapMap when adding normal values.
   public void testToString() {
      //Initializes regex pattern to match one line of the TreapMap 
      //toString().
      String line = "\t*R?L?.[0-9]+. <[1-6], [A-F]>.*\n";
      //Initializes empty compare String and adds the expected number of lines.
      String compare = "";
      for(int i = 0; i < 6; i++) {
         compare += line;
      }
      //Performs a large number of tests to account for inherent randomness.
      for(int x = 0; x < 100; x++) {
         //Initializes a new TreapMap.
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         
         String toString = temp.toString(); 
         //Checks that the toString implements the correct number of lines.
         assertEquals(true, toString.matches(".*\n.*\n.*\n.*\n.*\n.*\n"));
         //Checks that the toString matches the valid regex pattern.
         assertEquals(true, toString.matches(compare));
      }
   }
   
   @Test
   //Checks that edge cases and other errors are properly handled without
   //throwing exceptions and changing the flow.
   public void exceptionTest() {
      //Initializes new TreapMap.
      TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
      //Tests that inserting a null node at the root is handled.
      try {
         temp.insert(null, "!");
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Tests that inserting a null node in a non-empty TreapMap is handled.
      try {
         temp.insert(1, "A");
         temp.insert(null, "B");
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Tests that inserting a null value into a TreapMap is handled.
      try {
         temp.insert(3, null);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Tests that removing a null key from a TreapMap is handled.
      try {
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         temp.remove(null);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Tests that removing a key not present in the TreapMap is handled.
      try {
         temp.remove(-1);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Tests that the proper values were added to the TreapMap.
      Iterator it = temp.iterator();
      assertEquals(temp.lookup((Integer) it.next()), "A");
      assertEquals(temp.lookup((Integer) it.next()), "D");
      assertEquals(temp.lookup((Integer) it.next()), "E");
      assertEquals(temp.lookup((Integer) it.next()), "F");
      assertEquals(it.hasNext(), false);
      //Tests that the TreapMap properly splits around a small key value.
      try {
         Treap<Integer,String>[] subtrees = temp.split(-1);
         //Checks that the left tree is empty.
         Treap<Integer,String> small = subtrees[0];
         it = small.iterator();
         assertEquals(it.hasNext(), false);
         //Checks that the right tree has the proper values.
         Treap<Integer,String> big = subtrees[1];
         it = big.iterator();
         assertEquals(big.lookup((Integer) it.next()), "A");
         assertEquals(big.lookup((Integer) it.next()), "D");
         assertEquals(big.lookup((Integer) it.next()), "E");
         assertEquals(big.lookup((Integer) it.next()), "F");
         assertEquals(it.hasNext(), false);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Checks that the TreapMap splits correctly around a non-present large 
      //value.
      try {
         //Initializes new TreapMap.
         temp = new TreapMap<Integer, String>();
         temp.insert(1, "A");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         Treap<Integer,String>[] subtrees = temp.split(7);
         //Checks that the left tree has the proper values.
         Treap<Integer,String> small = subtrees[0];
         it = small.iterator();
         assertEquals(small.lookup((Integer) it.next()), "A");
         assertEquals(small.lookup((Integer) it.next()), "D");
         assertEquals(small.lookup((Integer) it.next()), "E");
         assertEquals(small.lookup((Integer) it.next()), "F");
         assertEquals(it.hasNext(), false);
         //Checks that the right tree is empty.
         Treap<Integer,String> big = subtrees[1];
         it = big.iterator();
         assertEquals(it.hasNext(), false);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Checks that splitting around a null node is handled.
      try {
         temp.split(null);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Checks that joining with a null Treap is handled.
      try {
         temp.join(null);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Initializes two empty TreapMaps.
      //Also tests that operations work for different types of key-value
      //combinations.
      TreapMap<String, Integer> temp1 = new TreapMap<String, Integer>();
      TreapMap<String, Integer> temp2 = new TreapMap<String, Integer>();
      //Checks that splitting an empty TreapMap is handled.
      try {
         Treap<String, Integer>[] subtrees  = temp1.split("3");
         //Checks that the left tree is empty.
         Treap<String,Integer> small = subtrees[0];
         it = small.iterator();
         assertEquals(it.hasNext(), false);
         //Checks that the right tree is empty.
         Treap<String, Integer> big = subtrees[1];
         it = big.iterator();
         assertEquals(it.hasNext(), false);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Checks that joining two empty TreapMaps is handled.
      try {
         temp1.join(temp2);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Checks that joining 1 empty TreapMap is handled.
      try {
         temp1 = new TreapMap<String, Integer>();
         temp2 = new TreapMap<String, Integer>();
         temp1.insert("1", 1);
         temp1.join(temp2);
         it = temp1.iterator();
         assertEquals((Integer)temp1.lookup((String)it.next()), new Integer(1));
         assertEquals(it.hasNext(), false);
         temp1.remove("1");
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Checks that joining a TreapMap to an empty TreapMap is handled.
      try {
         temp2.insert("1", 1);
         temp1.join(temp2);
         it = temp1.iterator();
         assertEquals((Integer)temp1.lookup((String)it.next()), new Integer(1));
         assertEquals(it.hasNext(), false);
         temp1.remove("1");
         temp2.remove("1");
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Checks that removing a from a single node TreapMap is handled.
      try {
         temp1.remove("1");
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      //Checks that splitting around a single node is handled.
      try {
         temp1.insert("1", 1);
         temp1.split("1");
         temp1.remove("1");
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      

   }
   
   @Test 
   public void extraStuff() {
      TreapMap<Integer,String> temp1 = new TreapMap<Integer, String>();
      temp1.insert(1, "1");
      temp1.balanceFactor();
      
      TreapMap<Integer,String> temp2 = new TreapMap<Integer, String>();
      temp2.insert(2, "2");
      temp1.difference(temp2);
      temp1.meld(temp2);
      
      
   }
}

