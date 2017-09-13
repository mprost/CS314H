import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import static java.lang.System.*;
import assignment.*;

public class TreapMapTest {

   @Test
   public void testLookup() {
      for(int x = 0; x < 100 ; x++) {   
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");

         assertEquals(temp.lookup(1), "A");
         assertEquals(temp.lookup(2), "B");
         assertEquals(temp.lookup(3), "C");
         assertEquals(temp.lookup(4), "D");
         assertEquals(temp.lookup(5), "E");
         assertEquals(temp.lookup(6), "F");
         
      }
   }
   
   @Test
   public void testRemove() {
      for(int x = 0; x < 100 ; x++) {
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         //out.println(temp);
         temp.remove(6);
         
         assertEquals(temp.lookup(1), "A");
         assertEquals(temp.lookup(2), "B");
         assertEquals(temp.lookup(3), "C");
         assertEquals(temp.lookup(4), "D");
         assertEquals(temp.lookup(5), "E");
         assertEquals(temp.lookup(6), null);
         //out.println(temp);
      }
      
      String line = "\t*R?L?.[0-9]+. <[1-6], [A-F]>.*\n";
      String compare = "";
      for(int i = 0; i < 5; i++) {
         compare += line;
      }
      for(int x = 0; x < 100; x++) {
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         
         temp.remove(6);
         
         String toString = temp.toString();
         assertEquals(toString.matches(compare), true);
      }
   }
   
   @Test
   public void testSplit() {
      for(int x = 0; x < 100 ; x++) {
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         out.println(temp);
         Treap<Integer,String>[] subtrees = temp.split(4);
         Treap<Integer,String> small = subtrees[0];
         out.println(small);
         Treap<Integer,String> big = subtrees[1];
         out.println(big);
         assertEquals(small.lookup(1), "A");
         assertEquals(small.lookup(2), "B");
         assertEquals(small.lookup(3), "C");
         assertEquals(small.lookup(4), null);
         assertEquals(small.lookup(5), null);
         assertEquals(small.lookup(6), null);
         
         assertEquals(big.lookup(1), null);
         assertEquals(big.lookup(2), null);
         assertEquals(big.lookup(3), null);
         assertEquals(big.lookup(4), "D");
         assertEquals(big.lookup(5), "E");
         assertEquals(big.lookup(6), "F");
      }
      String line = "\t*R?L?.[0-9]+. <[1-6], [A-F]>.*\n";
      String compare = "";
      for(int i = 0; i < 6; i++) {
         compare += line;
      }
      for(int x = 0; x < 100; x++) {
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         
         Treap<Integer,String>[] subtrees = temp.split(4);
         Treap<Integer,String> small = subtrees[0];
   
         Treap<Integer,String> big = subtrees[1];
         String toString = big.toString();
         assertEquals(toString.matches(compare.substring(compare.length()/2)), true);
         toString = small.toString();
         assertEquals(toString.matches(compare.substring(compare.length()/2)), true);
      }
   }

   @Test
   public void testIterator() {
      TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
      
      temp.insert(1, "A");
      temp.insert(2, "B");
      temp.insert(3, "C");
      temp.insert(4, "D");
      temp.insert(5, "E");
      temp.insert(6, "F");

      Iterator<Integer> it = temp.iterator();
      ArrayList<String> list = new ArrayList<String>();
      
      while(it.hasNext()) {
         list.add(temp.lookup(it.next()));
      }
      
      assertEquals(list.size(), 6);
      assertEquals(list.get(0), "A");
      assertEquals(list.get(1), "B");
      assertEquals(list.get(2), "C");
      assertEquals(list.get(3), "D");
      assertEquals(list.get(4), "E");
      assertEquals(list.get(5), "F");
   }
   
   @Test
   public void testJoin() {
      for(int x = 0; x < 100; x++) {
         TreapMap<Integer,String> small = new TreapMap<Integer, String>();
         
         small.insert(1, "A");
         small.insert(2, "B");
         small.insert(3, "C");
         
         TreapMap<Integer,String> big = new TreapMap<Integer, String>();
         //out.println(small);
         big.insert(4, "D");
         big.insert(5, "E");
         big.insert(6, "F");
         //out.println(big);
         big.join(small);
         //out.println(big);
         assertEquals(big.lookup(1), "A");
         assertEquals(big.lookup(2), "B");
         assertEquals(big.lookup(3), "C");
         assertEquals(big.lookup(4), "D");
         assertEquals(big.lookup(5), "E");
         assertEquals(big.lookup(6), "F");
      }
      
      String line = "\t*R?L?.[0-9]+. <[1-6], [A-F]>.*\n";
      String compare = "";
      for(int i = 0; i < 6; i++) {
         compare += line;
      }
      for(int x = 0; x < 100; x++) {
         TreapMap<Integer,String> small = new TreapMap<Integer, String>();
         
         small.insert(1, "A");
         small.insert(2, "B");
         small.insert(3, "C");
         
         TreapMap<Integer,String> big = new TreapMap<Integer, String>();
         big.insert(4, "D");
         big.insert(5, "E");
         big.insert(6, "F");
         big.join(small);
         String toString = big.toString();
         assertEquals(toString.matches(compare), true);
      }
   }
   
   @Test
   public void testToString() {
      String line = "\t*R?L?.[0-9]+. <[1-6], [A-F]>.*\n";
      String compare = "";
      for(int i = 0; i < 6; i++) {
         compare += line;
      }
      for(int x = 0; x < 100; x++) {
         TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
         
         temp.insert(1, "A");
         temp.insert(2, "B");
         temp.insert(3, "C");
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         
         String toString = temp.toString(); 
         
         assertEquals(toString.matches(".*\n.*\n.*\n.*\n.*\n.*\n"), true);
         assertEquals(toString.matches(compare), true);
      }
   }
   
   @Test
   public void exceptionTest() {
      TreapMap<Integer,String> temp = new TreapMap<Integer, String>();
      try {
         temp.insert(1, "A");
         temp.insert(null, "B");
         
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
         temp.insert(3, null);
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         temp.remove(null);
         temp.remove(-1);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
         temp.insert(4, "D");
         temp.insert(5, "E");
         temp.insert(6, "F");
         temp.remove(null);
         temp.remove(-1);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
         temp.remove(-1);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
         temp.split(-1);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
         temp.split(2);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
        // temp.split(null);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
        // temp.join(temp);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
         //temp.join(null);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      
      TreapMap<Integer,String> temp1 = new TreapMap<Integer, String>();
      TreapMap<Integer,String> temp2 = new TreapMap<Integer, String>();
      try {
         temp1.split(3);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
         //temp1.join(temp2);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
         /*temp1.insert(1, "1");
         temp1.join(temp2);
         temp1.remove(1);*/
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
         /*temp2.insert(1, "1");
         temp1.join(temp2);
         temp1.remove(1);
         temp2.remove(1);*/
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
         temp1.remove(1);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      try {
         temp1.insert(1, "1");
         temp1.split(1);
         temp1.remove(1);
      }
      catch(Exception e) {
         assertEquals(false, true);
      }
      
      //out.println(temp1);
   }
   
   @Test 
   public void extraStuff() {
      /*TreapMap<Integer,String> temp1 = new TreapMap<Integer, String>();
      temp1.insert(1, "1");
      temp1.balanceFactor();
      
      TreapMap<Integer,String> temp2 = new TreapMap<Integer, String>();
      temp2.insert(2, "2");
      temp1.difference(temp2);
      temp1.meld(temp2);*/
      
      
   }
}

