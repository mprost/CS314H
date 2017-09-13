package assignment;
import java.util.*;
import static java.lang.System.*;
import java.io.*;

public class GameDictionary implements BoggleDictionary {
   
   //These wordMaps store Strings by organizing them by length and accessing
   //them in constant time.
   private HashMap<Integer, HashSet<String>> words;
   private HashMap<Integer, HashSet<String>> prefixes;

   //This Iterator helps cycles the DictionaryIterator through the wordMap.
   private Iterator<Integer> iteratorFinder;
   private HashMap<Integer, Iterator<String>> those;
   
   //Returns an instance of a dictionary iterator to parse through the word 
   //map.
   public Iterator<String> iterator() {
      //Creates Iterators for all sequences in the wordMap.
      for(Integer i : words.keySet()) {
         those.put(i, words.get(i).iterator());
      }
      //Initializes the Iterator finder to iterate through each of the 
      //sequences.
      iteratorFinder = those.keySet().iterator();
      return new DictionaryIterator();
   }
   
   //Initializes the dictionary by reading in text from a file.
   public void loadDictionary(String fileName) {
      //Checks if the file exists.
      File f = new File(fileName);
      if(!f.exists()) {
         err.println("Error: The file \""+ fileName +"\" could not be found.");
      }
      
      else
      try {
         //Parses through file with BufferedReader because of the large input.
         BufferedReader reader = new BufferedReader(new FileReader(fileName));
         //Initializes the current word.
         String word = "";
         //Initializes the wordMaps.
         words = new HashMap<Integer, HashSet<String>>();
         prefixes = new HashMap<Integer, HashSet<String>>();
         those = new HashMap<Integer, Iterator<String>>();
         char letter = ' ';
         boolean valid = true;
         
         //Parses through the file.
         while(reader.ready()) {
            //Reads the next char of the file.
            letter = (char) reader.read();
            
            //Checks if the char is a nextLine character of a valid letter in
            //the English alphabet.
            if(letter != '\n' && ((letter < 65 || letter > 90) && (letter < 97 
                  || letter > 122))) {
               err.println("Error: Invalid character found in \"" + fileName +
                     "\": " + letter);
               //Marks the word as invalid.
               valid = false;
            }
            
            //Checks if an entire word has been collected.
            if(letter == (int)'\n') {
               //Only adds valid words to the wordMap.
               if(valid) {
                  //Creates a new sequence for words of the specified length.
                  if(words.get(word.length()) == null) {
                     words.put(word.length(), new HashSet<String>());
                  }
                  //Adds the lowercase word to its proper sequence.
                  words.get(word.length()).add(word.toLowerCase());
               }
               //Clears the word.
               word = "";
            }
            //Adds to the word if it is not complete.
            else {
               //Concatenates the incomplete word and the next letter in the
               //file.
               word += letter;
               
               //Adds the last word in the file to the wordMap.
               if(!reader.ready()) {
                //Creates a new sequence for words of the specified length.
                  if(words.get(word.length()) == null) {
                     words.put(word.length(), new HashSet<String>());
                  }
                  //Adds the last word to the wordMap.
                  words.get(word.length()).add(word);
               }
            }
            
            //Searches for nonempty prefixes.
            if(word.length() > 1) {
               //Creates a new sequence for prefixes of the specified length.
               if(prefixes.get(word.length() - 1) == null) {
                  prefixes.put(word.length() - 1, new HashSet<String>());
               }
               //Stores prefix of word into the wordMap.
               prefixes.get(word.length() - 1).add(word.substring(0, 
                     word.length() - 1));
            }
         }
         reader.close();
      }
      
      catch (Exception e) {
         err.println("Error File \"" + fileName + "\" could not be read.");
      }
   }
   
   
   //Checks if any words in the wordMap begin with a certain substring.
   public boolean isPrefix(String prefix) {
      //All words begin with the empty String.
      if(prefix.length() == 0) {
         return true;
      }
      //Checks if there are prefixes of the proper length that exist.
      if(prefixes.get(prefix.length()) == null) {
         return false;
      }
      //Checks if the prefix is present in the prefix wordMap.
      return prefixes.get(prefix.length()).contains(prefix);
   }
   
   
   //Checks if a word is stored in the dictionary.
   public boolean contains(String word) {
      //Returns false if the wordMap contains no words of a matching length.
      if(words.get(word.length()) == null) {
         return false;
      }
      //Checks if a word appears in its expected sequence in the wordMap.
      return words.get(word.length()).contains(word);
   }
   
   
   //Finds  the number of elements stored in the wordMap.
   public int getSize() {
      int count = 0;
      //Iterates through each sequence in the wordMap.
      for(Integer i : words.keySet()) {
         //Finds the number of words in each sequence.
         count += words.get(i).size();
      }
      return count;
   }
   
   
   
   //This Iterator returns Strings stored in the GameDictionary's wordMap.
   public class DictionaryIterator implements Iterator<String> {
      
      //This Iterator parses through a particular sequence in the wordMap.
      private Iterator<String> currentIterator;
      
      //Returns the next String in the wordMap.
      public String next() {
         //Checks if there are subsequent Strings in the wordMap.
         if(hasNext()) {
            return currentIterator.next();
         }
         //Returns empty String if there are not any subsequent Strings in the
         //wordMap.
         return "";
      }
      
      //Checks if there are subsequent words in the wordMap.
      public boolean hasNext() {
         
         //Checks if there are anymore Strings in the current sequence.
         while((currentIterator == null || !currentIterator.hasNext()) && 
               iteratorFinder.hasNext()) {
            //Sets the currentIterator to the next sequence in the wordMap.
            currentIterator = those.get(iteratorFinder.next());
         }
         
         //Checks if there are anymore Strings in the current sequence.
         if(currentIterator != null) {
            return currentIterator.hasNext();
         }
         //Returns false because the iterator was not initialized. 
         //(empty dictionary)
         else
            return false;
      }
      
      //Does nothing. This method's functionality was removed because 
      //DictionaryIterators are not meant to interact with the dictionary in
      //this way.
      public void remove() {
      }
   }
}

