package assignment;

import static org.junit.Assert.*;
import assignment.*;
import assignment.Board.Action;
import assignment.Board.Result;

import org.junit.Test;

public class TetrisBoardTest {

   @Test
   public void testTetrisBoard() {
      fail("Not yet implemented");
   }

   @Test
   public void testMove() {
      Board a = new TetrisBoard(10, 20);
      TetrisBoard b = new TetrisBoard(10, 20);
   }

   @Test
   public void testTestMove() {
      fail("Not yet implemented");
   }

   @Test
   public void testNextPiece() {
      fail("Not yet implemented");
   }

   @Test
   public void testEqualsObject() {
      Board a = new TetrisBoard(10, 20);
      TetrisBoard b = new TetrisBoard(10, 20);
      boolean equal = a.equals(b);
      assertEquals("Board a equals TetrisBoard b.", true, equal);
   }

   @Test
   public void testGetLastResult() {
      Board a = new TetrisBoard(10, 1);
      Piece p = TetrisPiece.getPiece("0 0");
      a.nextPiece(p);
      
      Result one = a.testMove(Action.RIGHT).getLastResult();
      assertEquals("Piece was successfully righted.", Result.SUCCESS, one);
      
      for(int count = 0; count < 10; count++){
         a = a.testMove(Action.LEFT);
      }
      Result two = a.testMove(Action.LEFT).getLastResult();
      assertEquals("Piece was unsuccessfully lefted.", Result.OUT_BOUNDS, two);
      
      Result three = a.testMove(Action.DROP).getLastResult();
      assertEquals("Piece was successfully dropped.", Result.PLACE, three);
      
      a.nextPiece(p);
      a.move(Action.LEFT);
      a.move(Action.DROP);
      a.nextPiece(p);
      Result four = a.testMove(Action.LEFT).getLastResult();
      assertEquals("Piece was unsuccessfully lefted.", Result.OBSTRUCTED, two);
   }

   @Test
   public void testGetLastAction() {
      Board a = new TetrisBoard(10, 10);
      Piece p = TetrisPiece.getPiece("0 0");
      a.nextPiece(p);
      
      a.move(Action.LEFT);
      assertEquals("Last action was left.", Action.LEFT, a.getLastAction());
      
      a.move(Action.RIGHT);
      assertEquals("Last action was right.", Action.RIGHT, a.getLastAction());
      
      a.move(Action.DOWN);
      assertEquals("Last action was down.", Action.DOWN, a.getLastAction());
      
      a.move(Action.DROP);
      assertEquals("Last action was drop.", Action.DROP, a.getLastAction());
      
      a.move(Action.CLOCKWISE);
      assertEquals("Last action was clockwise.", Action.CLOCKWISE,
            a.getLastAction());
      
      a.move(Action.COUNTERCLOCKWISE);
      assertEquals("Last action was counter clockwise.",
            Action.COUNTERCLOCKWISE, a.getLastAction());
      
   }

   @Test
   public void testGetRowsCleared() {
      fail("Not yet implemented");
   }

   @Test
   //Tests if the width is equal to the width passed into the TetrisBoard.
   public void testGetWidth() {
      TetrisBoard myTest = new TetrisBoard(10, 20);
      int width = myTest.getWidth();
      assertEquals("Width of the TetrisBoard.", 10, width);
   }

   @Test
   //Tests if the height is equal to the height passed into the TetrisBoard.
   public void testGetHeight() {
      TetrisBoard myTest = new TetrisBoard(10, 20);
      int height = myTest.getHeight();
      assertEquals("Height of the TetrisBoard.", 20, height);
   }

   @Test
   public void testGetMaxHeight() {
      /*TetrisBoard myBoard = new TetrisBoard(10, 20);
      boolean[][] matrix = new boolean[10][20];
      for(int r = 0; r < 19; r++) {
         for(int c = 0; c < 9; c++) {
            matrix[r][c] = true;
         }
      }
      int height = myBoard.getMaxHeight();
      assertEquals("The max height should be 20", 20, height);*/
   }

   @Test
   public void testDropHeight() {
      fail("Not yet implemented");
   }

   @Test
   public void testGetColumnHeight() {
      Board a = new TetrisBoard(1, 1);
      Piece p = TetrisPiece.getPiece("0 0");
      a.nextPiece(p);
      assertEquals("Column Height is 0.", 0, a.getColumnHeight(0));
      a.move(Action.DROP);
      assertEquals("Column Height is 1.", 1, a.getColumnHeight(0));
   }

   @Test
   public void testGetRowWidth() {
      fail("Not yet implemented");
   }

   @Test
   public void testGetGrid() {
      fail("Not yet implemented");
   }

}
