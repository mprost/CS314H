package assignment;

import java.io.*;
import java.net.*;
import java.util.*;

import org.attoparser.simple.*;
import org.attoparser.config.ParseConfiguration;

public class WebCrawler {

   /**
    * The WebCrawler's main method starts crawling a set of pages.
    * You can change this method as you see fit, as long as it takes
    * URLs as inputs and saves an Index at "index.db".
    */
   public static void main(String[] args) {
      // Basic usage information
      if (args.length == 0) {
         System.out.println("No URLs specified.");
         System.exit(1);
      }

      // We'll throw all of the args into a list
      LinkedList<URL> remaining = new LinkedList<>();
      //HashSet to keep track of all previously searched URLs
      HashSet<URL> searched = new HashSet<URL>();
      //Loads up the collections with URLs from the args
      for (String url : args) {
         try {
            remaining.add(new URL(url));
            searched.add(new URL(url));
         } catch (MalformedURLException e) {
            // Throw this one out
         }
      }

      // Create a parser from the attoparser library
      ISimpleMarkupParser parser = new SimpleMarkupParser(
            ParseConfiguration.htmlConfiguration());

      // We're using the handler we've defined
      CrawlingMarkupHandler handler = new CrawlingMarkupHandler();
      URL next;
      //Initializes count to the number of URLs from args ready to be crawled
      int count = args.length;
      
      //Repeats until all URLs have been crawled
      while (!remaining.isEmpty()) {
         //Removes next URL to be crawled
         next = remaining.remove(0);
         //Gives the next URL to the handler
         handler.addNextURL(next);
         
         try{
            // Parse the next URL's page
            parser.parse(new InputStreamReader(next.openStream()), handler);
         }
         catch(Exception e) {
         }
         // Add any new URLs
         for(URL url: handler.newURLs()) {
            //Checks if the URL has been previously crawled
            if(!searched.contains(url)) {
               //Prepares the URL for crawling and marks it
               remaining.add(url);
               searched.add(url);
            }
         }
      }
      //Saves the index
      try{
         handler.getIndex().save("index.db");
      }
      catch(Exception e) {

      }
   }
}
