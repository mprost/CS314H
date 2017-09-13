/*

CS314H - Critter Interpreter

Provided here is skeleton code for your interpreter.
Remove this comment when you have finished implementation.

*/
package assignment;

import java.util.*;
import java.io.*;
import static java.lang.System.*;

public class Interpreter implements CritterInterpreter {
   
   //This method converts parameters taken in as Strings into ints.
   public int processParameter(String param, Critter c) {
      //If the number is preceded by a '-' or '+' in the go command, then the
      //parameter is processed as a new code line relative to the current code
      //line.
      if(param.charAt(0) == '-' || param.charAt(0) == '+')
         return c.getNextCodeLine() + Integer.parseInt(param);
      //If the parameter is a register, the parameter is processed as the value
      //stored in that register.
      if(param.charAt(0) == 'r')
         return c.getReg(Integer.parseInt(param.substring(1)));
      return Integer.parseInt(param);
   }
   
   //This method returns an ArrayList with the conditional commands of the 
   //critter language.
   public ArrayList<String> initializeConditionals() {
      ArrayList<String> conditionals = new ArrayList<String>();
      conditionals.add("ifrandom");
      conditionals.add("ifempty");
      conditionals.add("ifally");
      conditionals.add("ifenemy");
      conditionals.add("ifwall");
      conditionals.add("ifangle");
      conditionals.add("iflt");
      conditionals.add("ifeq");
      conditionals.add("ifgt");
      return conditionals;
   }
   
   //This method returns an ArrayList with the mutator commands of the 
   //critter language.
   public ArrayList<String> initializeMutators() {
      ArrayList<String> mutators = new ArrayList<String>();
      mutators.add("write");
      mutators.add("add");
      mutators.add("sub");
      mutators.add("inc");
      mutators.add("dec");
      return mutators;
   }
   
   //This method returns an ArrayList with the action commands of the critter 
   //language.
   public ArrayList<String> initializeActions() {
      ArrayList<String> actions = new ArrayList<String>();
      actions.add("hop");
      actions.add("left");
      actions.add("right");
      actions.add("eat");
      actions.add("infect");
      return actions;
   }
   
   //This creates a HashMap that maps the list of callable methods in the 
   //critter language with the amount of variables they are expected to take.
   public HashMap<Integer, HashSet<String>> initializeNumParameters() {
      HashMap<Integer, HashSet<String>> numParameters = new 
            HashMap<Integer, HashSet<String>>();
      
      HashSet<String> zero = new HashSet<String>();
      zero.add("hop");
      zero.add("left");
      zero.add("right");
      zero.add("infect");
      zero.add("eat");
      numParameters.put(0, zero);
      
      HashSet<String> one = new HashSet<String>();
      one.add("infect");
      one.add("go");
      one.add("ifrandom");
      one.add("inc");
      one.add("dec");
      numParameters.put(1, one);
      
      HashSet<String> two = new HashSet<String>();
      two.add("ifempty");
      two.add("ifally");
      two.add("ifenemy");
      two.add("ifwall");
      two.add("write");
      two.add("add");
      two.add("sub");
      numParameters.put(2, two);
      
      HashSet<String> three = new HashSet<String>();
      three.add("ifangle");
      three.add("iflt");
      three.add("ifgt");
      three.add("ifeq");
      numParameters.put(3, three);
      
      return numParameters;
   }
   
   //This method retrieves the critter's behavior code, executes it, and calls
   //one of the action methods before it returns.
   public void executeCritter(Critter c) {
      //Constructs an ArrayList that contains the behavior methods that can be
      //compared to the next code line in order to determine which methods to
      //call.
      
      ArrayList<String> conditionals = initializeConditionals();
      ArrayList<String> mutators = initializeMutators();
      ArrayList<String> actions = initializeActions();
       
      ArrayList<String> code = (ArrayList<String>) c.getCode();
      int nextCodeLine = c.getNextCodeLine();
      String[] currentCodeLine = code.get(nextCodeLine - 1).split(" ");
       
      //Checking the first word in the line so we know the following
      //words are parameters.
      while(!actions.contains(currentCodeLine[0])) {
         
         //If the code line has more than word, then the code stores the 
         //parameters following the command.
         if (currentCodeLine.length > 1) {
            int[] parameters = new int[currentCodeLine.length - 1];
            for(int index = 1; index < currentCodeLine.length; index++) {
               parameters[index - 1] = 
                     processParameter(currentCodeLine[index], c);
            }
         }
         //This cycles through all of the behavior methods, and executes each
         //of the methods until an action method is found.
         if(conditionals.contains(currentCodeLine[0])) {
            switch(conditionals.indexOf(currentCodeLine[0])) {
               //The first command in the list of conditionals passes in zero
               //parameters.
               case 0: if(getBooleanValue(currentCodeLine[0], c)) {
                  c.setNextCodeLine(processParameter(currentCodeLine[1], c));
               }
               else {
                  c.setNextCodeLine(c.getNextCodeLine() + 1);
               } break;
               //The next commands in the list of conditionals pass in one 
               //parameter.
               case 1: 
               case 2: 
               case 3: 
               case 4: if(getBooleanValue(currentCodeLine[0], 
                     currentCodeLine[1], c)) {
                  c.setNextCodeLine(processParameter(currentCodeLine[2], c));
               }
               else {
                  c.setNextCodeLine(c.getNextCodeLine() + 1);
               } break;
               //The next commands in the list of conditionals pass in two 
               //parameters.
               case 5:
               case 6:
               case 7:
               case 8: if(getBooleanValue(currentCodeLine[0],
                     currentCodeLine[1], currentCodeLine[2], c)) {
                  c.setNextCodeLine(processParameter(currentCodeLine[3], c));
               }
               else {
                  c.setNextCodeLine(c.getNextCodeLine() + 1);
               } break;
            }
         }
         //This changes the values of registers.
         else if(mutators.contains(currentCodeLine[0])) {
            switch(mutators.indexOf(currentCodeLine[0])) {
               //This writes a value to a register.
               case 0: 
                  c.setReg(Integer.parseInt(currentCodeLine[1].substring(1)), 
                     processParameter(currentCodeLine[2], c));
                     c.setNextCodeLine(c.getNextCodeLine() + 1); break;
               //This adds two register values together.
               case 1: 
                  c.setReg(Integer.parseInt(currentCodeLine[1].substring(1)), 
                     processParameter(currentCodeLine[1], c) + 
                     processParameter(currentCodeLine[2], c));
                     c.setNextCodeLine(c.getNextCodeLine() + 1); break;
               //This subtracts two register values.
               case 2: 
                  c.setReg(Integer.parseInt(currentCodeLine[1].substring(1)), 
                     processParameter(currentCodeLine[1], c) - 
                     processParameter(currentCodeLine[2], c));
                     c.setNextCodeLine(c.getNextCodeLine() + 1); break;
               //This increments a register value by 1.
               case 3: 
                  c.setReg(Integer.parseInt(currentCodeLine[1].substring(1)),
                     processParameter(currentCodeLine[1], c) + 1); 
                     c.setNextCodeLine(c.getNextCodeLine() + 1); break;
               //This decrements a register value by 1.
               case 4: 
                  c.setReg(Integer.parseInt(currentCodeLine[1].substring(1)),
                     processParameter(currentCodeLine[1], c) - 1);
                     c.setNextCodeLine(c.getNextCodeLine() + 1); break;
            }
         }
         else if(currentCodeLine[0].equals("go")){
            c.setNextCodeLine(processParameter(currentCodeLine[1], c));
         }
         else {
            err.println("Behavior Method Not Found: " + currentCodeLine[0]);
         }
         //This iterates to the next code line.
         nextCodeLine = c.getNextCodeLine();
         currentCodeLine = code.get(nextCodeLine - 1).split(" ");
      }
      //This calls the final action. It is not called in the loop because the
      //final action revokes the critter's turn.
      switch(actions.indexOf(currentCodeLine[0])) {
         case 0: c.hop(); break;
         case 1: c.left(); break;
         case 2: c.right(); break;
         case 3: c.eat(); break;
         //The infect method can contain either an integer parameter or no
         //parameters. This checks to see if any parameters were given and
         //if so, calls the infect method with that parameter.
         case 4: if(currentCodeLine.length > 1)
               c.infect(processParameter(currentCodeLine[1], c));
               else c.infect(); break;
         default: err.println("Action Method Not Found: " + 
               currentCodeLine[0]);
      }
      nextCodeLine++;
      c.setNextCodeLine(nextCodeLine);
      return;
   }
   
   
   //This method implements the conditional methods of the critter class when
   //the method does not have parameters.
   public boolean getBooleanValue(String method, Critter c) {
      if(method.equals("ifrandom")) {
         return c.ifRandom();
      }
      err.println("Conditional Method Not Found: " + method);
      return true;
   }
   //This method implements the conditional methods of the critter class when
   //the method has one parameter.
   public boolean getBooleanValue(String method, String param, Critter c) {
      int p = 0;
      //This checks that the given parameter is an integer value that can be
      //passed into the method.
      try {
         p = processParameter(param, c);
      }
      catch (Exception e) {
         err.println("Parameter is valid: " + param);
      }
      
      if(method.equals("ifempty")) {
         return c.getCellContent(p) == Critter.EMPTY;
      }
      if(method.equals("ifally")) {
         return c.getCellContent(p) == Critter.ALLY;
      }
      if(method.equals("ifenemy")) {
         return c.getCellContent(p) == Critter.ENEMY;
      }
      if(method.equals("ifwall")) {
         return c.getCellContent(p) == Critter.WALL;
      }
      err.println("Conditional Method Not Found: " + method);
      return true;
   }
   //This method implements the conditional methods of the critter class when
   //the method has two parameters.
   public boolean getBooleanValue(String method, String param1, String param2,
         Critter c) {
      int p1 = 0;
      int p2 = 0;
      //This checks that the given parameters are integer values that can be
      //passed into the method.
      try {
         p1 = processParameter(param1, c);
      }
      catch (Exception e) {
         err.println("Parameter 1 is not valid: " + param1);
      }
      
      try {
         p2 = processParameter(param2, c);
      }
      catch (Exception e) {
         err.println("Parameter 2 is not valid: " + param2);
      }
      
      if(method.equals("ifangle")) {
         return c.getOffAngle(p1) == p2;
      }
      if(method.equals("iflt")) {
         return p1 < p2;
      }
      if(method.equals("ifeq")) {
         return p1 == p2;
      }
      if(method.equals("ifgt")) {
         return p1 > p2;
      }
      err.println("Conditional Method Not Found: " + method);
      return true;
   }
  
   //This method takes in a file, reads it, and returns a
   //new CritterSpecies object.
   public CritterSpecies loadSpecies(String filename) {
      //Declare a new File object and read it using Scanner object.
      File file = new File(filename);
      Scanner cin = new Scanner("");
      try {
         cin = new Scanner(file);
      }
      //Prints error message if file is not found.
      catch (Exception e) {
         err.println("File Not Found.");
         exit(1);
      }
      //Prints error message if file is empty.
      if(!cin.hasNextLine()) {
         err.println("Empty File.");
         exit(1);
      }
      String name = cin.nextLine();
      ArrayList<String> code = new ArrayList<String>();
      while(cin.hasNextLine()) {
         String line = cin.nextLine();
         //Exits scan if all instructions are read.
         if(line.equals(""))
            break;
         code.add(line);
      }
      if(detectErrors(code)) {
         return null;
      }
      return new CritterSpecies(name, code);
   }
   
   //This method parses through the critter code to check if it will operate
   //without bugs.
   public boolean detectErrors(ArrayList<String> code) {
      //This checks to see if all of the commands given are valid.
      ArrayList<String> conditionals = initializeConditionals();
      ArrayList<String> mutators = initializeMutators();
      ArrayList<String> actions = initializeActions();
      
      for (int line = 0; line < code.size(); line++) {
         String codeLine[] = code.get(line).split(" "); 
         String command = codeLine[0];
         
         if(!(conditionals.contains(command) || mutators.contains(command) || 
               actions.contains(command) || command.equals("go"))) {
            err.println("Command not found: " + command);
            return true;
         }
      }
      //This checks to see if all of the parameters are valid numbers or
      //registers.
      for(int line = 0; line < code.size(); line++) {
         String codeLine[] = code.get(line).split(" ");
         for(int i = 1; i < codeLine.length; i++) {
            //This sees if the parameter matches a register value
            if(!codeLine[i].matches("r[1-9]") && !codeLine[i].matches("r10")) {
               //If the integer has a + or a - for implementation in the 'go'
               //method, then it will successfully parse as an int.
               try {
                  Integer.parseInt(codeLine[i]);
               }
               catch(Exception e) {
                  err.println("Invalid Parameter: " + codeLine[i]);
                  return true;
               }
            }
            if(!codeLine[0].equals("go") && (codeLine[i].indexOf('+') >= 0 || 
                  codeLine[i].indexOf('-') >= 0)) {
               err.println("Parameter " + codeLine[i] + " not expected for "
                     + "command " + codeLine[0]);
               return true;
            }
         }
      }
      
      //This checks to see if the proper number of parameters is written for
      //each method.
      HashMap<Integer, HashSet<String>> numParameters = 
            initializeNumParameters();
      for(int line = 0; line < code.size(); line++) {
         String codeLine[] = code.get(line).split(" ");
         HashSet<String> commands = numParameters.get(codeLine.length - 1);
         if(!commands.contains(codeLine[0])) {
            err.println("Command " + codeLine[0] + " does not expect " + 
                (int)(codeLine.length - 1) + " parameter(s)");
            return true;
         }
      }
      
    //This method makes sure that the parameters of conditional methods other
      //than ifangle are valid bearings.
      for(int line = 0; line < code.size(); line++) {
         String codeLine[] = code.get(line).split(" ");
         if(codeLine[0].equals("ifempty") || codeLine[0].equals("ifally") || 
               codeLine[0].equals("ifenemy") || codeLine[0].equals("ifwall")) {
            int bearing = Integer.parseInt(codeLine[1]);
            if(!(bearing % 45 == 0 && bearing >= 0 && bearing <= 315)) {
               err.println("Parameter " + bearing + " is not a valid bearing "
                     + "for " + codeLine[0]);
               return true;
            }
         }
      }
      //This method makes sure that the parameters of ifangle are valid 
      //bearings.
      for(int line = 0; line < code.size(); line++) {
         String codeLine[] = code.get(line).split(" ");
         if(codeLine[0].equals("ifangle")) {
            int bearing1 = Integer.parseInt(codeLine[1]);
            if(!(bearing1 % 45 == 0 && bearing1 >= 0 && bearing1 <= 315)) {
               err.println("Parameter " + bearing1 + " is not a valid bearing "
                     + "for " + codeLine[0]);
               return true;
            }
            int bearing2 = Integer.parseInt(codeLine[2]);
            if(!(bearing2 % 45 == 0 && bearing2 >= 0 && bearing2 <= 315)) {
               err.println("Parameter " + bearing2 + " is not a valid bearing "
                     + "for " + codeLine[0]);
               return true;
            }
         }
      }
      
      //This method makes sure that the parameter of write is a valid register.
      for(int line = 0; line < code.size(); line++) {
         String codeLine[] = code.get(line).split(" ");
         if(codeLine[0].equals("write")) {
            if(!codeLine[1].matches("r[1-9]") && !codeLine[1].matches("r10")) {
               err.println("Parameter " + codeLine[1] + " is not a valid "
                     + "register for " + codeLine[0]);
               return true;
            }
         }
      }
      //This method makes sure that the parameters of mutators are valid 
      //registers.
      for(int line = 0; line < code.size(); line++) {
         String codeLine[] = code.get(line).split(" ");
         if(codeLine[0].equals("add") || codeLine[0].equals("sub") || 
               codeLine[0].equals("inc") || codeLine[0].equals("dec")) {
            if(!codeLine[1].matches("r[1-9]") && !codeLine[1].matches("r10")) {
               err.println("Parameter " + codeLine[1] + " is not a valid "
                     + "register for " + codeLine[0]);
               return true;
            }
            if(codeLine[0].equals("add") || codeLine[0].equals("sub"))
               if(!codeLine[2].matches("r[1-9]") && 
                     !codeLine[2].matches("r10")) {
               err.println("Parameter " + codeLine[2] + " is not a valid "
                     + "register for " + codeLine[0]);
               return true;
            }
         }
      }
      
      return false; 
   }

}
