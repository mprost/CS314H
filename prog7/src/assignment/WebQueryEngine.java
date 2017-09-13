package assignment;
import java.net.URL;
import java.util.*;

public class WebQueryEngine {
   //Store all pages from the index
   private Set<Page> allPages;
   //Web index provided
   private WebIndex index;
   /**
    * Returns a WebQueryEngine that uses the given Index to constructe answers to queries.
    *
    * @param index   The WebIndex this WebQueryEngine should use
    * @return       A WebQueryEngine ready to be queried
    */
   public static WebQueryEngine fromIndex(WebIndex index) {
      WebQueryEngine engine = new WebQueryEngine();
      engine.setPages(index);
      return engine;
   }
   
   //Initializes the collection of pages from the index
   private void setPages(WebIndex index) {
      this.index = index;
      allPages = index.getAllPages();
   }
   
   //Finds the mathematical union between the set of pages that match query
   //one and query two
   public Set<Page> union(Set<Page> one, Set<Page> two) {
      HashSet<Page> ret = new HashSet<Page>();
      for(Page page: one) {
         ret.add(page);
      }
      for(Page page: two) {
         ret.add(page);
      }
      return ret;
   }
   
   //Finds the mathematical intersection between the set of pages that match
   //query one and query two
   public Set<Page> intersection(Set<Page> one, Set<Page> two) {
      HashSet<Page> ret = new HashSet<Page>();
      for(Page page: one) {
         if(two.contains(page)){
            ret.add(page);
         }
      }
      return ret;
   }
   
   public Set<Page> not(Set<Page> one) {
      HashSet<Page> ret = new HashSet<Page>();
      for(Page page: one) {
         if(!allPages.contains(page)){
            ret.add(page);
         }
      }
      return ret;
   }
   
   
   public Set<Page> match(String word) {
      if(word.length() == 0) {
         return new HashSet<Page>();
      }
      return index.getPages(word);
   }
   
   public Set<Page> match(String[] querySequence) {
      if(querySequence.length == 0) {
         return new HashSet<Page>();
      }
      if(querySequence.length == 1) {
         return match(querySequence[0]);
      }
      HashSet<Page> matchingPages = new HashSet<Page>();
      //for(String word: querySequence) {
      Set<Page> pages = new HashSet<Page>();
      pages = index.getPages(querySequence[0]);
      for(Page page: pages) {
         for(Integer i: index.getIndices(querySequence[0], page)) {
            for(int count = 0; count < querySequence.length; count++) {
               if(!index.getIndices(querySequence[count], page).contains(i 
                     + count)) {
                  break;
               }
               if(count == querySequence.length - 1) {
                   matchingPages.add(page);
               }
            }
         }
      }
      //if(page.contains(querySequence)) {
      //   matchingPages.add(page);
      //}
      //}
      return matchingPages;
   }

   /**
    * Returns a Set of URLs (as Strings) of web pages satisfying
    * the query expression.
    *
    * @param query   a query expression
    * @return   a Set of web pages satisfying the query
    */
   
   //Finds all of the pages that satisfy the query
   public Set<Page> query(String q) {
      String querySequence = q.toLowerCase();
      //Optimization to remove excess !'s
      while(q.matches(".*!!.*")) {
         q.replaceAll("!!", "");
      }
      String[] ops = new String[]{" & ", " | ", "!"};
      WebQuery root = null;
      while(querySequence.length() > 0) {
         WebQuery queryOne = new WebQuery();
         WebQuery operator = new WebQuery();
         WebQuery queryTwo = new WebQuery();
         //String operator = "";
         queryOne = nextQuery(querySequence);
         querySequence = querySequence.substring(queryOne.getBody().length());
         if(root != null) {
            root.setParent(queryOne);
            queryOne.setLeft(root);
         }
         root = queryOne;
         if(querySequence.length() > 3) {
            operator = nextOperator(querySequence);
            querySequence = querySequence.substring(operator.getBody().length());
            operator.setLeft(root);
            root.setParent(operator);
            root = operator;
         }
         if(querySequence.length() > 3) { 
            queryTwo = nextQuery(querySequence);
            querySequence = querySequence.substring(queryTwo.getBody().length());
            root.setRight(queryTwo);
            queryTwo.setParent(root);
         }
         //if(root != null) {
         //   queryOne.setLeft(root);
         //   root.setParent(queryOne);
         //}
         //operator.setLeft(queryOne);
         //operator.setRight(queryTwo);
         //queryOne.setParent(operator);
         //queryTwo.setParent(operator);
         //root = operator;
      }
      //System.out.println(root.body);
      return root.matchingPages();
   }
   
   public WebQuery nextQuery(String querySequence) {
      String query = "";
      if(querySequence.charAt(0) == '(') {
         int level = 0;
         for(int count = 0; count < querySequence.length(); count++) {
            if(querySequence.charAt(count) == '(') {
               level++;
            }
            else if(querySequence.charAt(count) == ')') {
               level--;
            }
            if(level == 0) {
               query = querySequence.substring(0, count + 1);
            }
         }
      }
      else if(querySequence.charAt(0) == '"') {
         query = querySequence.substring(0, 
               querySequence.substring(1).indexOf('"') + 2);
      }
      else {
         //System.out.println(query);
         if(querySequence.indexOf(' ') == -1) {
            return new WebQuery(querySequence);
         }
         query = querySequence.substring(0, querySequence.indexOf(' '));
      }
      return new WebQuery(query);
   }
   
   public WebQuery nextOperator(String querySequence) {
      if(querySequence.matches(" & .*")) {
         return new WebQuery(" & ");
      }
      else if(querySequence.matches(" | .*")) {
         return new WebQuery(" | ");
      }
      else {
         return new WebQuery(" & ");
      }
   }
   
   //This helper class creates a tree of the queries to help parse compound 
   //queries.
   public class WebQuery {
      //Stores the sequence of queries (many queries only have a sequence of 
      //length 1.)
      private String[] querySequence;
      //This is the Set of pages matching the specific query.
      private Set<Page> matchingPages;
      //This is the String representation of the query.
      private String body;
      
      //This is the query's parent.
      WebQuery parent;
      //These are the query's children.
      WebQuery left;
      WebQuery right;
      
      //Initializes instance variables.
      public WebQuery() {
         body = "";
         matchingPages = new HashSet<Page>();
         querySequence = new String[0];
      }
      
      //Stores a query.
      public WebQuery(String query) {
         body = query;
         matchingPages = new HashSet<Page>();
         querySequence = new String[0];
         //Handles "query" case
         if(body.indexOf('"') >= 0) {
            //querySequence = body.substring(1, body.length() - 1).split(" ");
            //matchingPages = index.sequence(querySequence);
            /*querySequence = body.substring(1, body.length() - 1).split(" ");
            if(querySequence.length == 1) {
               //System.out.println(querySequence[0]);
               //System.out.println();
               matchingPages = index.getIndices(querySequence[0]).keySet();
            }
            else {
               for(Page page: index.getIndices(querySequence[0]).keySet()) {//System.out.println("!" + index.getIndices(querySequence[0]).get(page));
                  for(Integer start: 
                     index.getIndices(querySequence[0]).get(page)) {
                     for(int count = 1; count < querySequence.length; count++) 
                     {
                        //System.out.println("!" + index.getIndices(querySequence[count]).get(page));
                        if(index.getIndices(querySequence[count]).get(page) == 
                              null || !index.getIndices(querySequence[count])
                              .get(page).contains(start + count)) {
                           break;
                        }
                        if(count == querySequence.length - 1) {
                           matchingPages.add(page);
                        }
                     }
                  }
               }
            }
            /*HashSet<HashMap<Page, HashSet<Integer>>> pages = 
                  new HashSet<HashMap<Page, HashSet<Integer>>>();
            for(String word: querySequence) {
               pages.add(index.getIndices(word));
            }
            HashSet<HashMap<Page, HashSet<Integer>>> matchingIndices = 
                  new HashSet<HashMap<Page, HashSet<Integer>>>();
            matchingIndices.add(index.getIndices(querySequence[0]));
            for(HashMap<Page,HashSet<Integer>> page: pages) {
               if(page.get())
            }
            for(Page page: allPages) {
               if(page.contains(querySequence)) {
                  matchingPages.add(page);
               }
            }*/
            //System.out.println(body);
         }
         //Handles (|) and (&) cases
         else if(body.length() > 0 && body.charAt(0) == '(') {
            //Removes the outermost parenthesis. 
            body = body.substring(1, body.length() - 1);
            //Initializes new queries.
            WebQuery queryOne = new WebQuery();
            WebQuery operator = new WebQuery();
            WebQuery queryTwo = new WebQuery();
            
            //Creates subtree where the node is the operator and its children
            //are the two operands
            queryOne = nextQuery(body);
            body = body.substring(queryOne.getBody().length());
            operator = nextOperator(body);
            body = body.substring(operator.getBody().length());
            queryTwo = nextQuery(body);
            body = body.substring(queryTwo.getBody().length());
            
            operator.setLeft(queryOne);
            operator.setRight(queryTwo);
            queryOne.setParent(operator);
            queryTwo.setParent(operator);
            
            operator.setParent(this);
            setRight(operator);
            //Handles the ( & ) case
            if(operator.equals(" & ")) {
               matchingPages = intersection(queryOne.matchingPages(), 
                     queryTwo.matchingPages());
            }
            //Handles the ( | ) case
            else if(operator.equals(" | ")) {
               matchingPages = union(queryOne.matchingPages(), 
                     queryTwo.matchingPages());
            }
         }
         //Handles the ! case
         else if(body.length() > 1 && body.charAt(0) == '!') {
            Set<Page> contains = index.getPages(body.substring(1));
            for(Page page: allPages) {
               if(!contains.contains(page)) {
                  matchingPages.add(page);
               }
            }
         }
         else {
            matchingPages = index.getPages(body);
         }
      }
      
      public void setLeft(WebQuery l) {
         left = l;
      }
      
      public void setRight(WebQuery r) {
         right = r;
      }
      
      public void setParent(WebQuery p) {
         parent = p;
      }
      
      public String getBody() {
         return body;
      }
      
      public Set<Page> matchingPages() {
         return matchingPages;
      }
   }
}
