package assignment;

import java.util.*;
import java.lang.StringBuilder;
import java.net.*;
import org.attoparser.simple.*;

public class CrawlingMarkupHandler extends AbstractSimpleMarkupHandler {
   
   //WebIndex to hold data found through crawling
   private WebIndex index; 
   //Holds all found URLs so they can be crawled 
   private LinkedList<URL> hold;
   
   //Pointer that keeps track of different Pages
   private Page currentPage;
   //Keeps track of each word's index within a URL
   private int spot;

   
   //Initializes instance variables
   public CrawlingMarkupHandler() {
      index = new WebIndex();
      hold = new LinkedList<URL>();
      spot = 0;
   }

   /**
    * This method should return a completed Index after we've parsed things.
    */
   public Index getIndex() {
      return index;
   }

   /**
    * This method is to communicate any URLs we find back to the Crawler.
    */
   public List<URL> newURLs() {
      return hold;
   }

   /**
    * These are some of the methods from AbstractSimpleMarkupHandler.
    * All of its method implementations are NoOps, so we've added some things
    * to do; please remove all the extra printing before you turn in your code.
    *
    * Note: each of these methods defines a line and col param, but you probably
    * don't need those values. You can look at the documentation for the
    * superclass to see all of the handler methods.
    */

   /**
    * Called when the parser first starts reading a document.
    * @param startTimeNanos   the currentPage time (in nanoseconds) when parsing starts
    * @param line                  the line of the document where parsing starts
    * @param col                   the column of the document where parsing starts
    */
   public void handleDocumentStart(long startTimeNanos, int line, int col) {
      //Begins keeping track of a new list of URLs for the next document
      hold = new LinkedList<URL>();
      //Resets the spot tracker for this new Page
      spot = 0;
   }

   /**
    * Called when the parser finishes reading a document.
    * @param endTimeNanos      the currentPage time (in nanoseconds) when parsing ends
    * @param totalTimeNanos   the difference between currentPage times at the start
    *                                    and end of parsing
    * @param line                  the line of the document where parsing ends
    * @param col                   the column of the document where the parsing ends
    */
   public void handleDocumentEnd(long endTimeNanos, long totalTimeNanos, 
         int line, int col) {
   }

   /**
    * Called at the start of any tag.
    * @param elementName the element name (such as "div")
    * @param attributes   the element attributes map, or null if it has no attributes
    * @param line            the line in the document where this elements appears
    * @param col             the column in the document where this element appears
    */
   public void handleOpenElement(String elementName, 
         Map<String, String> attributes, int line, int col) {
      //Checks if the key denotes "HREF" or "href", signaling a possible link
      if(attributes != null && attributes.keySet() != null && 
            (attributes.containsKey("HREF") || attributes.containsKey("href"))) 
      {
         //Checks if the key is "HREF" or if it is "href" and stores the 
         //provided link
         String provided = "";
         if(attributes.containsKey("HREF")) {
            provided = attributes.get("HREF");
         }
         else if(attributes.containsKey("href")) {
            provided = attributes.get("href");
         }
         
         //Removes any queries from the URL
         if(provided.indexOf('?') >= 0) {
            provided = provided.substring(0, provided.indexOf('?'));
         }
         if(provided.indexOf('#') >= 0) {
            provided = provided.substring(0, provided.indexOf('#'));
         }
         //Tries to construct a new URL and add it to the list of URLs to be
         //crawled
         try {
            URL newURL = new URL(currentPage.getURL(), provided);
            hold.add(newURL);
         }
         catch(Exception e) {
         }
      }
   }

   /**
    * Called at the end of any tag.
    * @param elementName the element name (such as "div").
    * @param line            the line in the document where this elements appears.
    * @param col             the column in the document where this element appears.
    */
   public void handleCloseElement(String elementName, int line, int col) {
   }

   /**
    * Called whenever characters are found inside a tag. Note that the parser is not
    * required to return all characters in the tag in a single chunk. Whitespace is
    * also returned as characters.
    * @param ch         buffer containint characters; do not modify this buffer
    * @param start    location of 1st character in ch
    * @param length   number of characters in ch
    */
   public void handleText(char ch[], int start, int length, int line, int col) 
   {
      StringBuilder build = new StringBuilder("");
      int level = 0;
      //Iterates through the provided characters
      for(char character: ch) {
         //Checks for open brace, signaling a tag
         if(character == '<') {
            //Increments the level
            level++;
            //Signals the end of a word token
            if(build.length() != 0) {
               //Adds the new word to the index
               index.add(build.toString(), currentPage, spot++);
               //Resets to build a new word
               build = new StringBuilder("");
            }
         }
         //Checks if the iteration is not part of a tag
         if(level == 0) {
            //Concatenates valid (alphanumeric) characters
            if((character >= 97 && character <= 122) || 
                  (character >= 48 && character <= 57)) {
               build.append(character);
            }
            //Sets upper case characters to lower case
            else if(character >= 65 && character <= 90) {
               build.append((char)(character + 32));
            }
            //Signals the end of a word token
            else if(build.length() != 0) {
               //Adds the new word to the index
               index.add(build.toString(), currentPage, spot++);
               //Resets to build a new word
               build = new StringBuilder("");
            }
         }
         //Decrements the level if the end of one level of tag is found
         else if(character == '>') {
            level--;
         }
      }
      //Adds last word to the index
      if(build.length() != 0) {
         //Adds the new word to the index
         index.add(build.toString(), currentPage, spot++);
         //Resets to build a new word
         build = new StringBuilder("");
      }
   }
   
   //Helper method that allows the new to URL to given by an outside source
   public void addNextURL(URL url) {
      //Wraps new URL in a Page for possible storage in the index
      currentPage = new Page(url);
   }
}


