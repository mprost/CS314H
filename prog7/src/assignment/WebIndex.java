package assignment;
import java.util.*;
import java.io.*;
import java.net.*;

public class WebIndex extends Index {
    private static final long serialVersionUID = 1L;
    
    //The wordMap stores all of the information about what words appear where
    //in the index.
    private HashMap<String, HashMap<Page, HashSet<Integer>>> wordMap;
    //Stores a copy of each page in the index
    private HashSet<Page> pageSet;
    
    public WebIndex() {
       pageSet = new HashSet<Page>();
       wordMap = new HashMap<String, HashMap<Page, HashSet<Integer>>>();
    }
    
    //Stores the word in the page.
    public void add(String word, Page page, Integer spot) {
       //Checks if the word is already stored in the page.
       if(!wordMap.containsKey(word)) {
          //Adds a reference to the word in the page.
          wordMap.put(word, new HashMap<Page, HashSet<Integer>>());
       }
       pageSet.add(page);
       wordMap.get(word).put(page, new HashSet<Integer>());
       //Stores the index of the word.
       wordMap.get(word).get(page).add(spot);
    }
    
    //Checks if the page contains a specific word
    public boolean contains(String word, Page page) {
       //Checks if the words list has the key word
       return wordMap.containsKey(word);
       
    }
    
    //Checks if the page contains a specific sequence of adjacent wordMap
    public boolean contains(String[] wordSequence, Page page) {
       //Returns an empty set in the case of an empty query.
       if(wordSequence.length == 0) {
          return false;
       }
       //Returns an empty set if any of the words are not present in the page
       for(String word: wordSequence) {
          if(!wordMap.containsKey(word)) {
             return false;
          }
       }
       //Iterates through all of the spots where the first word in the sequence 
       //is present
       for(Integer start: wordMap.get(wordSequence[0]).get(page)) {
          for(int spot = 0; spot < wordSequence.length; spot++) {
             if(!wordMap.get(wordSequence[spot]).containsKey(page) || 
                   !wordMap.get(wordSequence[spot]).get(page).contains(start 
                         + spot)) {
                break;
             }
             if(spot == wordSequence.length - 1) {
                return true;
             }
          }
       }
       return false;
    }
    
    //Returns  a set of all pages that contain a specific word
    public Set<Page> getPages(String word) {
       //Returns empty set if the word does not appear in the entire wordMap
       if(wordMap.get(word) == null) {
          return new HashSet<Page>();
       }
       //Returns the set of pages mapped to the word
       Set<Page> pages = wordMap.get(word).keySet();
       return pages;
    }
    
    //Returns the set of places where a word appears in a specific page
    public Set<Integer> getIndices(String word, Page page) {
       //Returns empty set if the word does not appear in the entire wordMap
       if(wordMap.get(word) == null) {
          return new HashSet<Integer>();
       }
       //Returns empty set if the word does not appear in the specified page
       if(wordMap.get(word).get(page) == null) {
          return new HashSet<Integer>();
       }
       //Returns the set of indices that the word appears in
       return wordMap.get(word).get(page);
    }
    
    //Returns all pages
    public Set<Page> getAllPages() {
       return pageSet;
    }
}


