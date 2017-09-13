package test;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Test;

import assignment.CrawlingMarkupHandler;
import assignment.Page;
import assignment.WebIndex;
import assignment.WebQueryEngine;

public class WebIndexTest {

   @Test
   public void containsTest() {
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         index.add("two", p, 2);
         index.add("three", p, 3);
         
         for(Page page: index.getAllPages()) {
            assertEquals(index.contains("one", page), true);
            assertEquals(index.contains("two", page), true);
            assertEquals(index.contains("three", page), true);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         assertEquals(true, false);
      }
   }
   
   @Test
   public void mutliContainsTest() {
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         
         for(Page page: index.getAllPages()) {
            assertEquals(index.contains(new String[]{"one"}, 
                  page), true);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         assertEquals(true, false);
      }
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         index.add("two", p, 2);
         index.add("three", p, 3);
         
         for(Page page: index.getAllPages()) {
            assertEquals(index.contains(new String[]{"one", "two", "three"}, 
                  page), true);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         assertEquals(true, false);
      }
   }

   @Test
   public void getPagesTest() {
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         index.add("two", p, 2);
         index.add("three", p, 3);
         
         for(Page page: index.getPages("one")) {
            assertEquals(page, p);
         }
         for(Page page: index.getPages("two")) {
            assertEquals(page, p);
         }
         for(Page page: index.getPages("three")) {
            assertEquals(page, p);
         }
         for(Page page: index.getPages("four")) {
            assertEquals(true, false);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         assertEquals(true, false);
      }
      try{
         WebIndex index = new WebIndex();
         
         URL url1 = new URL("http://onetwo.com");
         Page p1 = new Page(url1);
         index.add("one", p1, 0);
         index.add("two", p1, 1);
         
         URL url2 = new URL("http://threefour.com");
         Page p2 = new Page(url2);
         index.add("three", p2, 0);
         index.add("four", p2, 1);
         
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
         e.printStackTrace();
         assertEquals(true, false);
      }
   }
   
   @Test
   public void getIndicesTest() {
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         index.add("two", p, 2);
         index.add("three", p, 3);
         
         for(Page page: index.getPages("one")) {
            for(int i:index.getIndices("one", page) ) {
               assertEquals(i, 1);
            }
         }
         for(Page page: index.getPages("two")) {
            for(int i:index.getIndices("two", page) ) {
               assertEquals(i, 2);
            }
         }
         for(Page page: index.getPages("three")) {
            for(int i:index.getIndices("three", page) ) {
               assertEquals(i, 3);
            }
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         assertEquals(true, false);
      }
      try {
         WebIndex index = new WebIndex();
         
         URL url1 = new URL("http://onetwo.com");
         Page p1 = new Page(url1);
         index.add("one", p1, 0);
         index.add("two", p1, 1);
         
         URL url2 = new URL("http://threefour.com");
         Page p2 = new Page(url2);
         index.add("three", p2, 0);
         index.add("four", p2, 1);
         
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
         e.printStackTrace();
         assertEquals(true, false);
      }
      
   }
   
   @Test
   public void getAllPagesTest() {
      try {
         WebIndex index = new WebIndex();
         
         URL url = new URL("http://numbers.com");
         Page p = new Page(url);
         index.add("one", p, 1);
         index.add("two", p, 2);
         index.add("three", p, 3);
         
         for(Page page: index.getAllPages()) {
            assertEquals(p, page);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         assertEquals(true, false);
      }
      try {
         WebIndex index = new WebIndex();
         
         URL url1 = new URL("http://onetwo.com");
         Page p1 = new Page(url1);
         index.add("one", p1, 0);
         index.add("two", p1, 1);
         
         URL url2 = new URL("http://threefour.com");
         Page p2 = new Page(url2);
         index.add("three", p2, 0);
         index.add("four", p2, 1);
         
         assertEquals(index.getAllPages().size(), 2);
         for(Page page: index.getAllPages()) {
            assertEquals((p1.equals(page) || p2.equals(page)), true);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
         assertEquals(true, false);
      }
   }
}
