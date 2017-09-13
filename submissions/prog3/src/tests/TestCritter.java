package assignment;
import java.util.*;
import static java.lang.System.*;

public class TestCritter implements Critter {
   //These instance variables give us the ability to manipulate the TestCritter
   //to operate with predictable outcomes.
   private int cellContent;
   private List<String> code;
   private int nextCodeLine;
   private int offAngle;
   private int[] reg;
   
   //This constructor initializes all instance variables.
   public TestCritter() {
      setCellContent(0);
      ArrayList<String> list = new ArrayList<String>();
      list.add("go 1");
      setCode(list);
      nextCodeLine = 1;
      setOffAngle(0);
      reg = new int[10];
      for(int n = 0; n < reg.length; n++) {
         reg[n] = 0;
      }
   }
   
   //Many of these methods have no implementation because this Critter will be
   //used for testing purposes only, not simulations.
   public void eat() {
      
   }
   
   public int getCellContent(int bearing) {
      return cellContent;
   }
   
   public List<String> getCode() {
      return code;
   }
   
   public int getNextCodeLine() {
      return nextCodeLine;
   }
   
   public int getOffAngle(int bearing) {
      return offAngle;
   }
   
   public int getReg(int n) {
      return reg[n - 1];
   }
   
   public void hop() {
      
   }
   
   public boolean ifRandom() {
      return Math.random() > 0.5;
   }
   
   public void infect() {
      
   }
   
   public void infect(int n) {
      
   }
   
   public void left() {
      
   }
   
   public void right() {
      
   }
   
   public void setNextCodeLine(int n) {
      nextCodeLine = n;
      out.println("Line " + n + ": " + code.get(n - 1));
   }
   
   public void setReg(int n, int value) {
      out.println("Register " + n + " = " + value);
      reg[n - 1] = value;
   }
   
   //These are mutator methods that were not implemented in the Critter
   //interface. These allow us to manipulate the instance variables that we
   //have created, so that we can execute the TestCritter in a controlled
   //environment.
   public void setCellContent( int n) { 
      cellContent = n;
   }
   
   public void setCode(List s) {
      code = s;
   }
   
   public void setOffAngle(int n) {
      offAngle = n;
   }
}
