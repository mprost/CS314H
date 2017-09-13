package assignment;
import static java.lang.System.*;

public class InterpreterTest {

   public static void main(String[] args) {
      Interpreter test = new Interpreter();
      
      //Tests if loadSpecies method reads the input file correctly.
      CritterSpecies species = test.loadSpecies("TestCritter1.cri");
      out.println(species.getName() + " " + species.getCode());
      //Output for TestCritter1 is correct.
      
      //Tests if loadSpecies method reads the input file correctly with edge 
      //cases like registers, relative jumps, ifangle, and infect with multiple
      //numbers of parameters.
      species = test.loadSpecies("TestCritter2.cri");
      out.println(species.getName() + " " + species.getCode());
      //Output for TestCritter2 is correct.
      
      //Checks if error statement is printed when an invalid command is input.
      test.loadSpecies("InvalidCommandTest.cri");
      //Error message "Command not found: eaat" was printed as expected.
      
      //Checks if error statement is printed when parameters are words.
      test.loadSpecies("InvalidParameterTest1.cri");
      //Error message "Invalid Parameter: five" was printed as expected.
      
      //Checks if error statement is printed when parameters are not integers.
      test.loadSpecies("InvalidParameterTest2.cri");
      //Error message "Invalid Parameter: 5.5" was printed as expected.
      
      //Checks if error statement is printed when parameters are invalid 
      //registers.
      test.loadSpecies("InvalidParameterTest3.cri");
      //Error message "Invalid Parameter: r11" was printed as expected.
      
      //Checks if error statement is printed when parameters are registers 
      //being used for relative jumps.
      test.loadSpecies("InvalidParameterTest4.cri");
      //Error message "Invalid Parameter: -r1" was printed as expected.
      
      //Checks if error statement is printed when a command line has an 
      //unexpected number of parameters.
      test.loadSpecies("WrongNumberOfParametersTest.cri");
      //Error message "Command ifally does not expect 1 parameter(s)" was
      //printed as expected.
      
      //Checks if error statement is printed when conditional methods with one
      //bearing parameter use an invalid bearing.
      test.loadSpecies("ValidBearingTest1.cri");
      //Error message "Parameter -12 not expected for command ifally" was 
      //printed as expected.
      
      //Checks if error statement is printed when conditional methods with two
      //bearing parameters use an invalid bearing in the first parameter.
      test.loadSpecies("ValidBearingTest2.cri");
      //Error message "Parameter 360 is not a valid bearing for ifangle" was 
      //printed as expected.
      
      //Checks if error statement is printed when conditional methods with two
      //bearing parameters use an invalid bearing in the second parameter.
      test.loadSpecies("ValidBearingTest3.cri");
      //Error message "Parameter 44 is not a valid bearing for ifangle" was 
      //printed as expected.
      
      //Checks if error statement is printed when mutator methods with one
      //register parameter use an invalid register.
      test.loadSpecies("ValidRegisterTest1.cri");
      //Error message "Parameter 45 is not a valid register for write" was 
      //printed as expected.
      
      //Checks if error statement is printed when mutator methods with two
      //register parameters use an invalid register for the first register.
      test.loadSpecies("ValidRegisterTest2.cri");
      //Error message "Parameter 30 is not a valid register for add" was 
      //printed as expected.
      
      //Checks if error statement is printed when mutator methods with two
      //register parameters use an invalid register for the second register.
      test.loadSpecies("ValidRegisterTest3.cri");
      //Error message "Parameter 50 is not a valid register for add" was 
      //printed as expected.
      
      //Checks if error statement is printed when a parameter is preceded by a
      //+ or - in a method besides go.
      test.loadSpecies("ParameterPrefixTest.cri");
      //Error message "Parameter: +5 not expected for command ifally" was
      //printed as expected.
      
      
      
      //This is a class that implements the Critter interface for testing 
      //purposes.
      TestCritter crit = new TestCritter();
      
      //Checks that the executeCritter functions as expected. These tests print
      //out the next line that the TestCritter executes in the code.
      
      //This shows that the negative relative jumps work as intended. 
      out.println("\nExecute Test 1");
      species = test.loadSpecies("TestCritter3.cri");
      crit.setCode(species.getCode());
      crit.setNextCodeLine(1);
      for(int i = 0; i < 4; i++) {
         test.executeCritter(crit);
      }
      //The critter hops, rights, lefts, then repeats the left command as 
      //expected.

      //This shows that the positive relative jumps work as intended. 
      out.println("\nExecute Test 2");
      species = test.loadSpecies("TestCritter4.cri");
      crit.setCode(species.getCode());
      crit.setNextCodeLine(1);
      for(int i = 0; i < 4; i++) {
         test.executeCritter(crit);
      }
      //The critter hops, skips line 3, lefts, then repeats as expected.
      
      //This shows that the register values are properly processed and 
      //accessed.
      out.println("\nExecute Test 3");
      species = test.loadSpecies("TestCritter5.cri");
      crit.setCode(species.getCode());
      crit.setNextCodeLine(1);
      for(int i = 0; i < 5; i++) {
         test.executeCritter(crit);
      }
      //The critter writes the value 2 to r3, rights, lefts, infects, then
      //repeats all commands after line 1, as expected.
      
      //This shows that the conditional methods with two parameters work 
      //correctly.
      out.println("\nExecute Test 4");
      species = test.loadSpecies("TestCritter6.cri");
      crit.setCode(species.getCode());
      crit.setCellContent(Critter.WALL);
      crit.setNextCodeLine(1);
      for(int i = 0; i < 4; i++) {
         test.executeCritter(crit);
      }
      //The critter eats, infects, lefts, and skips line 5 as expected.
      
      //This shows that the conditional methods with three parameters work 
      //correctly.
      out.println("\nExecute Test 5");
      species = test.loadSpecies("TestCritter7.cri");
      crit.setCode(species.getCode());
      crit.setNextCodeLine(1);
      for(int i = 0; i < 3; i++) {
         test.executeCritter(crit);
      }
      //The critter ends up with two equal parameters r1 and r2, then infects,
      //lefts, and skips line 7 as expected.
      
      //This shows that the conditional methods with three parameters work 
      //correctly.
      out.println("\nExecute Test 6");
      species = test.loadSpecies("TestCritter8.cri");
      crit.setCode(species.getCode());
      crit.setNextCodeLine(1);
      for(int i = 0; i < 3; i++) {
         test.executeCritter(crit);
      }
      //The critter writes register 1 to 0 and increments by 1 until it is 
      //equal to register 2, then it decrements twice as expected.
   }

}
