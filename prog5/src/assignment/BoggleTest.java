package assignment;


import static java.lang.System.err;
import static java.lang.System.in;
import static java.lang.System.out;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import assignment.*;
import assignment.BoggleGame.SearchTactic;

public class BoggleTest {

   public static void main(String[] args) {
      GameManager game = new GameManager();
      GameDictionary dictionary = new GameDictionary();
      
      Scanner keyBoard = new Scanner(in);
      
      String wordFile = "words.txt";
      int size = 4;
      int numPlayers = 2;
      String cubeFile = "cubes.txt";
      
      dictionary.loadDictionary(wordFile);
      
      
      
      game.newGame(size, numPlayers, cubeFile, dictionary); 
      dictionary.loadDictionary(wordFile);
      game.setGame(new char[][]{"rcos".toCharArray(), "hnri".toCharArray(), 
         "usey".toCharArray(), "oeho".toCharArray()});
      game.setSearchTactic(SearchTactic.SEARCH_BOARD);
      int currentPlayer = 0;
      
      String input = "ryes, siren, herons, conus, cornus, sires, cone, shyers, usher, coney, hoers, hush, cores, sons, rees, senors, cones, erns, cons, osier, seisor, sirs, oyer, sers, sone, oyes, snore, ensue, sire, crone, cosie, shoers, sones, hues, sores, sirees, heros, sorns, irons, ions, heron, irone, rhus, shoer, onery, oyers, cornu, corns, soiree, herns, eros, creesh, corn, onus, noir, shun, cries, huns, heirs, ires, sori, reis, sorn, users, osiers, cosier, ushers, core, cosies, hens, sirens, sore, coir, senor, nosier, hoer, hoes, eery, seis, hern, hero, hers, irones, heir, crones, coirs, shoe, ores, yens, uncos, henry, unco, shyer, cris, iron, ones, siree, sris, user, resh, soirees, corse, q";
      String[] inputs = input.split(", ");
      
      char[][] board = game.getBoard();
      out.println();
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[r].length; c++) {
            out.print(board[r][c] + " ");
         }
         out.println();
      }
      out.println();
      
      int lastWord = 0;
      List<Point> lastPath = new LinkedList<Point>();
      int index = 0;
      while(index < inputs.length) {
         String s = inputs[index++];
         
         if(s.equals("q")) {
            break;
         }
         lastWord = game.addWord(s, currentPlayer);
         currentPlayer++;
         currentPlayer %= numPlayers;
         
         board = game.getBoard();
         //out.println();
         if(lastWord == 0) {
            out.println(s + "Not a word.\n");
            /*for(int r = 0; r < board.length; r++) {
               for(int c = 0; c < board[r].length; c++) {
                  out.print(board[r][c] + " ");
               }
               out.println();
            }
            out.println();*/
            //out.println("Player " + (int)(currentPlayer + 1) + "\'s turn.");
         }
         else {
            /*out.println(lastWord + " points!\n");
            lastPath = game.getLastAddedWord();
            for(int r = 0; r < board.length; r++) {
               for(int c = 0; c < board[r].length; c++) {
                  boolean traced = false;
                  for(Point p : lastPath) {
                     if((int)p.getX() == r && (int)p.getY() == c) {
                        traced = true;
                     }
                  }
                  if(!traced) {
                     out.print(board[r][c] + " ");
                  }
                  else {
                     out.print((char)(board[r][c] - 32) + " ");
                  }
               }
               out.println();
            }
            out.println();*/
            //out.println("Player " + (int)(currentPlayer + 1) + "\'s turn.");
         }
         
      }
      out.println("Final Scores: ");
      int[] scores = game.getScores();
      currentPlayer = 1;
      for(int i : scores) {
         if(i == 1) {
            out.println("Player " + currentPlayer + ": " + i + " point");
         }
         else {
            out.println("Player " + currentPlayer + ": " + i + " points");
         }
         currentPlayer++;
      }
      out.println();
      //out.println("These are the words you missed.");
      //out.println(game.getMissedWords());
      
      
      
      
      
      game = new GameManager();
      dictionary = new GameDictionary();
      
      keyBoard = new Scanner(in);
      
      wordFile = "words.txt";
      size = 4;
      numPlayers = 2;
      cubeFile = "cubes.txt";
      
      dictionary.loadDictionary(wordFile);
      game.newGame(size, numPlayers, cubeFile, dictionary); 
      dictionary.loadDictionary(wordFile);
      game.setGame(new char[][]{"rcos".toCharArray(), "hnri".toCharArray(), 
         "usey".toCharArray(), "oeho".toCharArray()});
      game.setSearchTactic(SearchTactic.SEARCH_DICT);
      currentPlayer = 0;
      
      input = "ryes, siren, herons, conus, cornus, sires, cone, shyers, usher, coney, hoers, hush, cores, sons, rees, senors, cones, erns, cons, osier, seisor, sirs, oyer, sers, sone, oyes, snore, ensue, sire, crone, cosie, shoers, sones, hues, sores, sirees, heros, sorns, irons, ions, heron, irone, rhus, shoer, onery, oyers, cornu, corns, soiree, herns, eros, creesh, corn, onus, noir, shun, cries, huns, heirs, ires, sori, reis, sorn, users, osiers, cosier, ushers, core, cosies, hens, sirens, sore, coir, senor, nosier, hoer, hoes, eery, seis, hern, hero, hers, irones, heir, crones, coirs, shoe, ores, yens, uncos, henry, unco, shyer, cris, iron, ones, siree, sris, user, resh, soirees, corse, q";
      inputs = input.split(", ");
      
      board = game.getBoard();
      out.println();
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[r].length; c++) {
            out.print(board[r][c] + " ");
         }
         out.println();
      }
      out.println();
      
       lastWord = 0;
      lastPath = new LinkedList<Point>();
      index = 0;
      while(index < inputs.length) {
         String s = inputs[index++];
         
         if(s.equals("q")) {
            break;
         }
         lastWord = game.addWord(s, currentPlayer);
         currentPlayer++;
         currentPlayer %= numPlayers;
         
         board = game.getBoard();
         //out.println();
         if(lastWord == 0) {
            out.println(s + "Not a word.\n");
            /*for(int r = 0; r < board.length; r++) {
               for(int c = 0; c < board[r].length; c++) {
                  out.print(board[r][c] + " ");
               }
               out.println();
            }
            out.println();*/
            //out.println("Player " + (int)(currentPlayer + 1) + "\'s turn.");
         }
         else {
            /*out.println(lastWord + " points!\n");
            lastPath = game.getLastAddedWord();
            for(int r = 0; r < board.length; r++) {
               for(int c = 0; c < board[r].length; c++) {
                  boolean traced = false;
                  for(Point p : lastPath) {
                     if((int)p.getX() == r && (int)p.getY() == c) {
                        traced = true;
                     }
                  }
                  if(!traced) {
                     out.print(board[r][c] + " ");
                  }
                  else {
                     out.print((char)(board[r][c] - 32) + " ");
                  }
               }
               out.println();
            }
            out.println();*/
            //out.println("Player " + (int)(currentPlayer + 1) + "\'s turn.");
         }
         
      }
      out.println("Final Scores: ");
      scores = game.getScores();
      currentPlayer = 1;
      for(int i : scores) {
         if(i == 1) {
            out.println("Player " + currentPlayer + ": " + i + " point");
         }
         else {
            out.println("Player " + currentPlayer + ": " + i + " points");
         }
         currentPlayer++;
      }
      out.println();
      //out.println("These are the words you missed.");
      //out.println(game.getMissedWords());

   }

}
