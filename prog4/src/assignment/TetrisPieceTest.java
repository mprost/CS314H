package assignment;

import static org.junit.Assert.*;
import java.util.*;
import java.awt.Point;

import org.junit.Test;

public class TetrisPieceTest {

   @Test
   public void testNextRotation() {
      /*String pieceStrings = "0 0  1 0  2 0  3 0";
      String rotated = "0 0  0 1  0 2  0 3";
      TetrisPiece myPiece = new TetrisPiece();
      Point[] p = myPiece.parsePoints(pieceStrings);
      Piece a = myPiece.nextRotation();
      Point[] rot = a.parsePoints(pieceStrings);
      boolean equals = true;
      for(int i = 0; i < points.length; i++) {
         if(rot[i].getX() != 0 && rot[i].getY() != i) {
            equals = false;
         }
      }
      assertEquals("Rotation should be successful", true, equals);*/
   }

   @Test
   public void testGetWidth() {
      String pieceStrings = "0 0  1 0  2 0  3 0";
      TetrisPiece myPiece = new TetrisPiece();
      int width = myPiece.getPiece(pieceStrings).getWidth();
      assertEquals("Width should be 4", 4, width);
   }

   @Test
   public void testGetHeight() {
      String pieceStrings = "0 0  1 0  2 0  3 0";
      TetrisPiece myPiece = new TetrisPiece();
      int height = myPiece.getPiece(pieceStrings).getHeight();
      assertEquals("Height should be 1", 1, height);
   }

   @Test
   public void testGetBody() {
      /*TetrisPiece myPiece = new TetrisPiece();
      String[] pieceStrings = { "0 0  1 0  2 0  3 0"};
      Point[] a = myPiece.getBody();
      Point[] b = myPiece.parsePoints(pieceStrings);
      assertEquals(a, b);*/
   }

   @Test
   public void testGetSkirt() {
      /*String pieceStrings = "0 0  1 0  2 0  3 0";
      TetrisPiece myPiece = new TetrisPiece();
      int[] skirt = myPiece.getPiece(pieceStrings).getSkirt();
      assertArrayEquals("Skirt should be 0", [0, 0, 0, 0], skirt);*/
   }

   @Test
   public void testEqualsObject() {
      TetrisPiece a = new TetrisPiece();
      TetrisPiece b = new TetrisPiece();
      String pieceStrings = "0 0  1 0  2 0  3 0";
      TetrisPiece c = (TetrisPiece) a.getPiece(pieceStrings);
      TetrisPiece d = (TetrisPiece) b.getPiece(pieceStrings);
      boolean equal = c.equals(d);
      assertEquals("TetrisPiece c equals TetrisPiece d.", true, equal);
   }

   @Test
   public void testGetPiece() {
      fail("Not yet implemented");
   }

   @Test
   public void testGetRotation() {
      fail("Not yet implemented");
   }

   @Test
   public void testRotatePoints() {
      String pieceStrings = "0 0  1 0  2 0  3 0";
      String rotated = "0 0  0 1  0 2  0 3";
      TetrisPiece myPiece = new TetrisPiece();
      Point[] p = myPiece.parsePoints(pieceStrings);
      int h = 4;
      Point[] rot = myPiece.parsePoints(rotated); 
      Point[] points = myPiece.rotatePoints(p, h);
      boolean equals = true;
      for(int i = 0; i < points.length; i++) {
         if(rot[i].getX() != 0 && rot[i].getY() != i) {
            equals = false;
         }
      }
      assertEquals("Rotation should be successful", true, equals);
   }

   @Test
   public void testBottomHeavy() {
      String piece = "0 0  1 0  2 0  3 0";
      //Point[] p = Piece.parsePoints(piece);
      Piece p = TetrisPiece.getPiece(piece);
      assertEquals("", 0, TetrisPiece.bottomHeavy(new Piece[]{p}));
   }

}
