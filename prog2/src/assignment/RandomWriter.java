package assignment;
import static java.lang.System.*;
import java.io.*;
import java.util.*;

/*
 * CS 315H Assignment 2 - Random Writing
 *
 * This class uses random writing to produce texts that are similar to known 
 * pieces of writing.
 * 
 * @author      Matt Prost
 */
public class RandomWriter implements TextProcessor {
   
   private static String inputSource;
   private static String inputResult;
   private static String inputK;
   private static String inputLength;
   
   private static String sourceText;
   private static HashMap<String, Integer> seedFrequency = 
         new HashMap<String, Integer>();
   private static int numberOfSeeds;
   private static String[] seeds;
   private static char[][] letters;
   private static String finalText;
   
   //The main method takes in four arguments from the command line. 
   //args[0] is the input filename (source).
   //args[1] is the output filename (result).
   //args[2] is the level of analysis (k).
   //args[3] is the length of output (length).
   public static void main(String[] args) throws IOException {
      inputSource = args[0];
      inputResult = args[1];
      inputK = args[2];
      inputLength = args[3];
      
      int k = 0;
      int length = 0;
      
      //This section checks to see if the input was valid.
      //If either k or length are not integer values >= 0, the program will
      //terminate, and an error message will be sent.
      try {
         k = Integer.parseInt(inputK);
         length = Integer.parseInt(inputLength);
         if(k < 0) {
            err.println("Error: k and length must both be positive values.");
            exit(1);
         }
         if(length < 0) {
            err.println("Error: k and length must both be positive values.");
            exit(1);
         }
         
      }
      catch(Exception e) {
         err.println("Error: k and length must both be positive integer values.");
         exit(1);
      }
      
      //If the file name is not valid, the program will terminate, and an error
      //message will be sent.
      File file = new File(inputSource);
      if(!file.exists()) {
         err.println("Error: The file \""+inputSource+"\" could not be found.");
         exit(1);
      }
      
      //If the source file contains less than k characters, the program will
      //terminate, and an error message will be sent. 
      BufferedReader testReader = new BufferedReader(new FileReader(inputSource));
      int numberOfCharacters = 0;
      while(testReader.ready()) {
         numberOfCharacters++;
         testReader.read();
      }
      testReader.close();
      if(k >= numberOfCharacters) {
         err.println("Error: The file \""+inputSource+"\" must have more than"
               + " k characters.");
         exit(1);
      }
      
      //If the output file cannot be created and/ or opened, the program will
      //terminate, and an error message will be sent.
      try {
         PrintWriter testWriter = new PrintWriter(inputResult);
         testWriter.close();
      }
      catch(Exception e) {
         err.println("Error: The file \""+inputResult+"\" could not be "
               + "created and/ or opened.");
         exit(1);
      }
      
      
      RandomWriter author = (RandomWriter) createProcessor(k);
      //The print statement was used to help time the processes and improve
      //upon the slowest processes within the code.
      author.writeText(inputResult, length);
      //out.println(seedFrequency);
      //out.println("Output Written to File.");
      
   }

   //This is a factory constructor. It reads in the input text from a file, it
   //finds the frequency of seeds of length 'level', and then it maps out the
   //frequencies of characters that follow those seeds.
   public static TextProcessor createProcessor(int level) throws IOException {
      RandomWriter author = new RandomWriter();
      author.readText(inputSource);
      //These print statements were used to help time the processes and improve
      //upon the slowest processes within the code.
      //out.println("Read Text Completed.");
      author.findSeedFrequency(level);
      //out.println("Seed Frequency Mapped.");
      author.seedMap(level);
      //out.println("Seeds Mapped.");
      return author;
   }
   
   //This finds the number of occurrences of each character or substring,
   //so that the most common characters can be tracked.
   public void findSeedFrequency(int level) throws IOException {
      //This HashMap keeps track of all seeds and the number of times that
      //they appear in the text.
      seedFrequency = new HashMap<String, Integer>();
      //If the level of analysis is 0, then all of the seeds would be "", so
      //there is no need to map their frequency.
      if(level == 0) {
         return;
      }
      char[] listOfChars = sourceText.toCharArray();
      numberOfSeeds = 0;
      for(int x = 0; x <= listOfChars.length - level; x++) {
         String key = "";
         //This loop creates new keys that start at index x.
         for(int y = 0; y < level; y++) {
            key += listOfChars[x + y];
         }
         if(!seedFrequency.containsKey(key)) {
            seedFrequency.put(key, 1);
         }
         else {
            seedFrequency.put(key, seedFrequency.get(key) + 1);
         }
         numberOfSeeds++;
      }
      out.println(seedFrequency);
   }
   
   //This creates the list of characters that come after each seed.
   public void seedMap(int level) {
      //This is a list of all of the different seeds.
      seeds = new String[seedFrequency.keySet().size()];
      int index = 0;
      for(String seed : seedFrequency.keySet()) {
         seeds[index++] = seed;
      }
      
      //This two-dimensional array contains each of the characters that follow
      //different seeds. The rows of the array correspond with the indices of
      //the seeds that those rows belong to.
      letters = new char[seeds.length][];
      for(index = 0; index < letters.length; index++) {
         letters[index] = new char[seedFrequency.get(seeds[index])];
         String tempText = sourceText;
         for(int column = 0; column < letters[index].length; column++) {
            //This adds a character to the map only if it is not the last seed
            //in the sourceText.
            try {
               letters[index][column] = 
                  tempText.charAt(tempText.indexOf(seeds[index]) + level);
            tempText = tempText.substring(tempText.indexOf(seeds[index]) + 1);
            }
            catch(Exception e) {
               
            }
            
         }
      }
   }

   //This method parses through the source text, storing all of the characters
   //into the String 'sourceText'.
   public void readText(String inputFileName) throws IOException {
      BufferedReader reader = 
         new BufferedReader(new FileReader(new File(inputFileName)));
      sourceText = "";
      String temp = "";
      int count = 0;
      while(reader.ready()) {
         temp += (char) reader.read();
         count++;
         //During my time trial with large input, I found that storing the
         //characters in a temporary string before adding them to the source
         //text can reduce the time spent reading in a file by up to 90%.
         if(count == 128) {
            count = 0;
            sourceText += temp;
            temp = "";
         }
      }
      sourceText += temp;
      reader.close();
   }
   
   //This randomly generates the text and writes it to the output file.
   public void writeText(String outputFileName, int length) throws IOException {
      finalText = "";
      finalText += newSeed();
      int level = Integer.parseInt(inputK);
      String seed = finalText.substring(finalText.length() - level);
      //If the analysis level is not 0, then the code finds a random character
      //following the seed, and then uses that character to form the next seed
      //in the sequence.
      if(seed.length() > 0) {
         while(finalText.length() < length) {
            char next = getCharacter(seed);
            finalText += next;
            seed = seed.substring(1) + next;
         }
      }
      //If the analysis level is 0, then the code simply selects a random
      //character from the text to add to the output.
      else {
         while(finalText.length() < length) {
            int seedIndex = (int)(Math.random() * sourceText.length());
            finalText += sourceText.charAt(seedIndex);
         }
      }
      //This was used to make sure that the finalText was the proper length
      //out.println("File Length: " + finalText.length());
      
      File outputFile = new File(outputFileName);
      FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(finalText);
      bw.close();
      
   }
   
   //This finds a random character that follows the seed provided.
   public char getCharacter(String targetSeed) {
      for(int index = 0; index < seeds.length; index++) {
         if(targetSeed.equals(seeds[index])) {
            int seedIndex = (int)(Math.random() * letters[index].length);
            return letters[index][seedIndex];
         }
      }
      return newSeed().charAt(0);
   }
   
   //This determines which seed will be the first in the finalText. It finds a 
   //random number between 0 and the numberOfSeeds, then it cycles through the 
   //seedFrequency HashMap to find that seed.
   public String newSeed() {
      int seedIndex = (int)(Math.random() * numberOfSeeds);
      int index = 0;
      for(String seed : seedFrequency.keySet()) {
         index += seedFrequency.get(seed);
         if(index > seedIndex)
            return seed;
      }
      return "";
   }
}
