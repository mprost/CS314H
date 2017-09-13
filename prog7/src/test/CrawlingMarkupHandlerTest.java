package test;

import static org.junit.Assert.*;
import java.util.*;
import java.net.URL;

import org.junit.Test;

import assignment.*;
import org.junit.Test;

public class CrawlingMarkupHandlerTest {

   @Test
   public void handleTextTest() {
      try{
         CrawlingMarkupHandler crawler = new CrawlingMarkupHandler();
         String read = "one two three";

         URL url = new URL("http://numbers.com");
         crawler.addNextURL(url);
         
         crawler.handleText(read.toCharArray(), 0, read.length(), 0, 0);
         WebIndex index = (WebIndex) crawler.getIndex();
         
         for(Page page: index.getAllPages()) {
            assertEquals(index.contains("one", page), true);
            assertEquals(index.contains("two", page), true);
            assertEquals(index.contains("three", page), true);
            assertEquals(index.contains("four", page), false);
         }
      }
      catch(Exception e) {
         assertEquals(true, false);
      }
      try{
         CrawlingMarkupHandler crawler = new CrawlingMarkupHandler();
         
         String read1 = "one two";
         URL url1 = new URL("http://onetwo.com");
         crawler.addNextURL(url1);
         crawler.handleText(read1.toCharArray(), 0, read1.length(), 0, 0);
         
         String read2 = "three four";
         URL url2 = new URL("http://threefour.com");
         crawler.addNextURL(url2);
         crawler.handleText(read2.toCharArray(), 0, read2.length(), 0, 0);
         
         WebIndex index = (WebIndex) crawler.getIndex();
         
         for(Page page: index.getAllPages()) {
            assertEquals(index.contains("one", page) && 
                  index.contains("two", page) || 
                  index.contains("three", page) && 
                  index.contains("four", page), true);
         }
      }
      catch(Exception e) {
         assertEquals(true, false);
      }
   }
   
   @Test
   public void pagesTest() {
      try{
         CrawlingMarkupHandler crawler = new CrawlingMarkupHandler();
         String read = "one two three";
         
         URL url = new URL("http://numbers.com");
         crawler.addNextURL(url);
         
         crawler.handleText(read.toCharArray(), 0, read.length(), 0, 0);
         WebIndex index = (WebIndex) crawler.getIndex();
         
         for(Page page: index.getPages("one")) {
            assertEquals(page.toString(), url.toString());
         }
         for(Page page: index.getPages("two")) {
            assertEquals(page.toString(), url.toString());
         }
         for(Page page: index.getPages("three")) {
            assertEquals(page.toString(), url.toString());
         }
         for(Page page: index.getPages("four")) {
            assertEquals(true, false);
         }
      }
      catch(Exception e) {
         assertEquals(true, false);
      }
      try{
         CrawlingMarkupHandler crawler = new CrawlingMarkupHandler();
         
         String read1 = "one two";
         URL url1 = new URL("http://onetwo.com");
         crawler.addNextURL(url1);
         crawler.handleText(read1.toCharArray(), 0, read1.length(), 0, 0);
         
         String read2 = "three four";
         URL url2 = new URL("http://threefour.com");
         crawler.addNextURL(url2);
         crawler.handleText(read2.toCharArray(), 0, read2.length(), 0, 0);
         
         WebIndex index = (WebIndex) crawler.getIndex();
         
         for(Page page: index.getPages("one")) {
            assertEquals(page.toString(), url1.toString());
         }
         for(Page page: index.getPages("two")) {
            assertEquals(page.toString(), url1.toString());
         }
         for(Page page: index.getPages("three")) {
            assertEquals(page.toString(), url2.toString());
         }
         for(Page page: index.getPages("four")) {
            assertEquals(page.toString(), url2.toString());
         }
      }
      catch(Exception e) {
         assertEquals(true, false);
      }
      try{
         CrawlingMarkupHandler crawler = new CrawlingMarkupHandler();
         
         String read1 = "one two three";
         URL url1 = new URL("http://onetwothree.com");
         crawler.addNextURL(url1);
         crawler.handleText(read1.toCharArray(), 0, read1.length(), 0, 0);
         
         String read2 = "three four";
         URL url2 = new URL("http://threefour.com");
         crawler.addNextURL(url2);
         crawler.handleText(read2.toCharArray(), 0, read2.length(), 0, 0);
         
         WebIndex index = (WebIndex) crawler.getIndex();
         
         for(Page page: index.getPages("one")) {
            assertEquals(page.toString(), url1.toString());
         }
         for(Page page: index.getPages("two")) {
            assertEquals(page.toString(), url1.toString());
         }
         assertEquals(index.getPages("three").size(), 2);
         for(Page page: index.getPages("four")) {
            assertEquals(page.toString(), url2.toString());
         }
      }
      catch(Exception e) {
         assertEquals(true, false);
      }
   }
   
   @Test
   public void indicesTest() {
      try{
         CrawlingMarkupHandler crawler = new CrawlingMarkupHandler();
         String read = "one two three";
         
         URL url = new URL("http://numbers.com");
         crawler.addNextURL(url);
         
         crawler.handleText(read.toCharArray(), 0, read.length(), 0, 0);
         WebIndex index = (WebIndex) crawler.getIndex();
         
         for(Page page: index.getPages("one")) {
            for(int i:index.getIndices("one", page) ) {
               assertEquals(i, 0);
            }
         }
         for(Page page: index.getPages("two")) {
            for(int i:index.getIndices("two", page) ) {
               assertEquals(i, 1);
            }
         }
         for(Page page: index.getPages("three")) {
            for(int i:index.getIndices("three", page) ) {
               assertEquals(i, 2);
            }
         }
      }
      catch(Exception e) {
         assertEquals(true, false);
      }
      try{
         CrawlingMarkupHandler crawler = new CrawlingMarkupHandler();
         
         String read1 = "one two";
         URL url1 = new URL("http://onetwo.com");
         crawler.addNextURL(url1);
         crawler.handleText(read1.toCharArray(), 0, read1.length(), 0, 0);
         
         crawler.handleDocumentStart(0, 0, 0);
         
         String read2 = "three four";
         URL url2 = new URL("http://threefour.com");
         crawler.addNextURL(url2);
         crawler.handleText(read2.toCharArray(), 0, read2.length(), 0, 0);
         
         WebIndex index = (WebIndex) crawler.getIndex();
         
         for(Page page: index.getPages("one")) {
            for(int i:index.getIndices("one", page) ) {
               assertEquals(i, 0);
            }
         }
         for(Page page: index.getPages("two")) {
            for(int i:index.getIndices("two", page) ) {
               assertEquals(i, 1);
            }
         }
         for(Page page: index.getPages("three")) {
            for(int i:index.getIndices("three", page) ) {
               assertEquals(i, 0);
            }
         }
         for(Page page: index.getPages("four")) {
            for(int i:index.getIndices("four", page) ) {
               assertEquals(i, 1);
            }
         }
      }
      catch(Exception e) {
         assertEquals(true, false);
      }
   }
   
   @Test
   public void handleOpenElementTest() {
      try {
         LinkedList<URL> urls = new LinkedList<URL>();
         
         URL one = new URL("http://one.com/");
         URL two = new URL("http://two.com/");
         URL three = new URL("http://three.com/");
         URL four = new URL("http://four.com/");
         
         urls.add(one);
         urls.add(two);
         urls.add(three);
         urls.add(four);
         
         CrawlingMarkupHandler crawler = new CrawlingMarkupHandler();
         crawler.addNextURL(one);
         URL oneTest = new URL("http://one.com/?searchtag");
         HashMap<String, String> oneMap = new HashMap<String, String>();
         oneMap.put("HREF", oneTest.toString());
         crawler.handleOpenElement("one", oneMap, 0, 0);
         
         crawler.addNextURL(two);
         URL twoTest = new URL("http://two.com/#searchtag");
         HashMap<String, String> twoMap = new HashMap<String, String>();
         twoMap.put("HREF", twoTest.toString());
         crawler.handleOpenElement("two", twoMap, 0, 0);
         
         crawler.addNextURL(three);
         URL threeTest = new URL("http://three.com/");
         HashMap<String, String> threeMap = new HashMap<String, String>();
         threeMap.put("href", threeTest.toString());
         crawler.handleOpenElement("three", threeMap, 0, 0);
         
         crawler.addNextURL(four);
         URL fourTest = new URL("http://four.com/");
         HashMap<String, String> fourMap = new HashMap<String, String>();
         fourMap.put("badTag", fourTest.toString());
         crawler.handleOpenElement("four", fourMap, 0, 0);
         
         List<URL> check = crawler.newURLs();
         //The "+ 1" is because the fourth URL is designed to fail
         assertEquals(urls.size(), check.size() + 1);
         
         for(int i = 0; i < check.size(); i++) {
            assertEquals(urls.get(i), check.get(i));
         }
      }
      catch(Exception e) {
         assertEquals(true, false);
      }
   }

}
