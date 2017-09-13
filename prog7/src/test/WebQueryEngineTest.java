package test;

import static org.junit.Assert.*;
import java.net.URL;

import org.junit.Test;
import assignment.*;

public class WebQueryEngineTest {

   @Test
   public void singleTest() {
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         index.add("two", p, 2);
         index.add("three", p, 3);
         
         WebQueryEngine engine = WebQueryEngine.fromIndex(index);
         assertEquals(engine.query("one").size(), 1);
         for(Page page: engine.query("one")) {
            assertEquals(p, page);
         }
         assertEquals(engine.query("two").size(), 1);
         for(Page page: engine.query("two")) {
            assertEquals(p, page);
         }
         assertEquals(engine.query("three").size(), 1);
         for(Page page: engine.query("three")) {
            assertEquals(p, page);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         //There was an error with one of the URLs
         assertEquals(true, false);
      }
   }
   
   @Test
   public void querySequenceTest() {
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         
         WebQueryEngine engine = WebQueryEngine.fromIndex(index);
         assertEquals(1, engine.query("\"one\"").size());
         for(Page page: engine.query("\"one\"")) {
            assertEquals(p, page);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         //There was an error with one of the URLs
         assertEquals(true, false);
      }
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         index.add("two", p, 2);
         index.add("three", p, 3);
         
         WebQueryEngine engine = WebQueryEngine.fromIndex(index);
         assertEquals(engine.query("\"one two three\"").size(), 1);
         for(Page page: engine.query("\"one two three\"")) {
            assertEquals(p, page);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         //There was an error with one of the URLs
         assertEquals(true, false);
      }
   }
   
   @Test
   public void implicitAndQueryTest() {
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         index.add("two", p, 2);
         index.add("three", p, 3);
         
         WebQueryEngine engine = WebQueryEngine.fromIndex(index);
         assertEquals(1, engine.query("one two").size());
         for(Page page: engine.query("one two")) {
            assertEquals(p, page);
         }
         assertEquals(1, engine.query("two three").size());
         for(Page page: engine.query("two three")) {
            assertEquals(p, page);
         }
         assertEquals(1, engine.query("three one").size());
         for(Page page: engine.query("three one")) {
            assertEquals(p, page);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         //There was an error with one of the URLs
         assertEquals(true, false);
      }
   }

   @Test
   public void andQueryTest() {
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         index.add("two", p, 2);
         index.add("three", p, 3);
         
         WebQueryEngine engine = WebQueryEngine.fromIndex(index);
         assertEquals(1, engine.query("(one & two)").size());
         for(Page page: engine.query("(one & two)")) {
            assertEquals(p, page);
         }
         assertEquals(1, engine.query("(two & three)").size());
         for(Page page: engine.query("(two & three)")) {
            assertEquals(p, page);
         }
         assertEquals(1, engine.query("(three & one)").size());
         for(Page page: engine.query("(three & one)")) {
            assertEquals(p, page);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         //There was an error with one of the URLs
         assertEquals(true, false);
      }
   }
   
   @Test
   public void orQueryTest() {
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         index.add("two", p, 2);
         index.add("three", p, 3);
         
         WebQueryEngine engine = WebQueryEngine.fromIndex(index);
         assertEquals(1, engine.query("(one | two)").size());
         for(Page page: engine.query("(one | two)")) {
            assertEquals(p, page);
         }
         assertEquals(1, engine.query("(two | three)").size());
         for(Page page: engine.query("(two | three)")) {
            assertEquals(p, page);
         }
         assertEquals(1, engine.query("(three | one)").size());
         for(Page page: engine.query("(three | one)")) {
            assertEquals(p, page);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         //There was an error with one of the URLs
         assertEquals(true, false);
      }
   }
   
   @Test
   public void notQueryTest() {
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         index.add("two", p, 2);
         index.add("three", p, 3);
         
         WebQueryEngine engine = WebQueryEngine.fromIndex(index);
         assertEquals(0, engine.query("!one").size());
         for(Page page: engine.query("!one")) {
            assertEquals(p, page);
         }
         assertEquals(0, engine.query("!two").size());
         for(Page page: engine.query("!two")) {
            assertEquals(p, page);
         }
         assertEquals(0, engine.query("!three").size());
         for(Page page: engine.query("!three")) {
            assertEquals(p, page);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         //There was an error with one of the URLs
         assertEquals(true, false);
      }
   }
}
