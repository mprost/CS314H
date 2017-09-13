package assignment;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class GameDictionaryTest {
	
	@Test
	public void LoadDictionary() {
		GameDictionary myDict = new GameDictionary();
	   
	   myDict.loadDictionary("simple.txt");
	   
	   Iterator<String> it = myDict.iterator();
	   HashSet<String> dictWords = new HashSet<String>();
	   while(it.hasNext()) {
	      dictWords.add(it.next());
	   }
	   
	   HashSet<String> words = new HashSet<String>();
	   words.add("matt");
	   words.add("rohit");
	   words.add("calvin");
	   words.add("cats");
	   words.add("dogs");
	   
	   assertEquals(words, dictWords);
	}
	
	@Test
	//Checks if the dictionary loads the correct number of words.
	public void testGetSize() {
		GameDictionary myDict = new GameDictionary();
		
		myDict.loadDictionary("simple.txt");
		int count1 = myDict.getSize();
		assertEquals(5, count1);
		
		myDict.loadDictionary("words.txt");
		int count2 = myDict.getSize();
		assertEquals(113809, count2);
	}

	@Test
	public void testIsPrefix() {
		GameDictionary myDict = new GameDictionary();
		myDict.loadDictionary("words.txt");
		boolean prefix1 = myDict.isPrefix("");
		boolean prefix2 = myDict.isPrefix("a");
		boolean prefix3 = myDict.isPrefix("ab");
		boolean prefix4 = myDict.isPrefix("abab");
		assertEquals(true, prefix1);
		assertEquals(true, prefix2);
		assertEquals(true, prefix3);
		assertEquals(false, prefix4);
	}

	@Test
	public void testContains() {
		GameDictionary myDict = new GameDictionary();
		myDict.loadDictionary("words.txt");
		boolean contains1 = myDict.contains("");
		boolean contains2 = myDict.contains("a");
		
		//Only works for words with length greater than or equal to 4
		boolean contains3 = myDict.contains("abstract");
		boolean contains4 = myDict.contains("board");
		assertEquals(false, contains1);
		assertEquals(false, contains2);
		assertEquals(true, contains3);
		assertEquals(true, contains4);
	}

}
