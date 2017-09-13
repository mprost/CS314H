package assignment;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

/**
 * The Page class holds anything that the QueryEngine returns to the server.
 * The field and method we provided here is the bare minimum requirement
 * to be a Page, feel free to add anything you want as long as you don't
 * break the getURL method.
 */
public class Page implements Serializable {
   private URL url;

   public Page(URL url) {
      this.url = url;
   }

   public URL getURL() { 
      return url; 
   }
 
   public boolean equals(Object o) {
      Page p = (Page) o;
      return p.getURL().equals(url);
   }
   
   public String toString() {
      return url.toString();
   }
}



