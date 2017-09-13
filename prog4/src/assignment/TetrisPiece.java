package assignment;

import java.awt.*;
import java.util.*;

/**
 * An immutable representation of a tetris piece in a particular rotation.
 * Each piece is defined by the blocks that make up its body.
 */
public final class TetrisPiece extends Piece {
   
   private static TetrisPiece[][] pieceTypes;
   private TetrisPiece[] rotations;
   
   private int width;
   private int height;
   private Point[] body;
   private int[] skirt;
   
   private int pieceType;
   private int rotation;
   
   /**
   * Return a Piece class from a String representation of the points
   * in the piece's body
   */
   public static Piece getPiece(String pieceString) {
      if(pieceTypes == null)
         pieceTypes = new TetrisPiece[0][0];
      //This stores the points from the new String as an array of points.
      Point[] points = parsePoints(pieceString);
      //This loops through the pieceTypes array to see if the array of points
      //matches any of the different types of tetris pieces.
      for(int pT = 0; pT < pieceTypes.length; pT++) {
         //If the TetrisPiece in pieceType matches the array of points, then it
         //returns that type of TetrisPiece.
         for(int rot = 0; rot < pieceTypes[pT].length; rot++) {
            Point[] tempBody = pieceTypes[pT][rot].getBody();
            if(points.length == tempBody.length) {
               int equalityCount = 0;
               for(int index = 0; index < tempBody.length; index++) {
                  if(tempBody[index].getX() == points[index].getX() && 
                        tempBody[index].getY() == points[index].getY())
                     equalityCount++;
               }
               if(equalityCount == tempBody.length) {
                  return pieceTypes[pT][rot];
               }
            }
         }
      }
      //This array represents the rotations for a new type of piece.
      TetrisPiece[] type = new TetrisPiece[4];
      
      //This is the piece represented by the pieceString points. It will be
      //returned at the end of the method, but first all of its rotations will
      //be stored.
      TetrisPiece piece = new TetrisPiece();
      piece.setWidth(points);
      piece.setHeight(points);
      piece.setBody(points);
      piece.setSkirt(points, piece.getWidth());
      piece.setID(pieceTypes.length, 0);
      type[0] = piece;
      
      TetrisPiece tempPiece = new TetrisPiece();
      Point[] tempPoints = Arrays.copyOf(points, points.length);
      //This will continue to rotate the piece and add the rotations until one
      //rotation matches the original piece's orientation.
      for(int count = 1; count < type.length; count ++) {
         if(type[count - 1].getWidth() >= tempPiece.getHeight())
            tempPoints = rotatePoints(tempPoints, type[count - 1].getWidth());
         else
            tempPoints = rotatePoints(tempPoints, type[count - 1].getHeight());
         type[count] = new TetrisPiece();
         type[count].setWidth(tempPoints);
         type[count].setHeight(tempPoints);
         type[count].setBody(tempPoints);
         type[count].setSkirt(tempPoints, type[count].getWidth());
         type[count].setID(pieceTypes.length, count);
      }
      
      int first = bottomHeavy(type);
      //Declare array to place new order of pieces in.
      TetrisPiece[] shiftedType = new TetrisPiece[type.length];
      for(int index = 0; index < type.length; index++) {
         //Assigns the bottomheavy piece to the first element in the new array,
         //and keeps the same order.
         shiftedType[index] = new TetrisPiece();
         Point[] shiftedBody = type[(index + first) % type.length].getBody();
         shiftedType[index].setWidth(shiftedBody);
         shiftedType[index].setHeight(shiftedBody);
         shiftedType[index].setBody(shiftedBody);
         shiftedType[index].setSkirt(shiftedBody, 
               shiftedType[index].getWidth());
         shiftedType[index].setID(pieceTypes.length, index);
      }
      //This adds the new type of piece with all of its rotations to the array 
      //of piece types.
      pieceTypes = Arrays.copyOf(pieceTypes, pieceTypes.length + 1);
      pieceTypes[pieceTypes.length - 1] = shiftedType;
      piece.setID(pieceTypes.length - 1, (4 - first) % 4);
      return piece;
   }

   //This is an accessor method used to find the width of the piece.
   public int getWidth() { 
      return width;
   }

   //This is an accessor method used to find the height of the piece.
   public int getHeight() {
      return height;
   }

   //This is an accessor method used to find the list of points in the piece.
   public Point[] getBody() {
      return body;
   }

   //This is an accessor method used to find the bottom coordinates of each
   //column in the piece.
   public int[] getSkirt() {
      return skirt;
   }
   
   //This sees if the values stored in the tetris pieces (width, height, body,
   //and skirt) are the same.
   public boolean equals(Object other) {
      TetrisPiece temp = (TetrisPiece) other;
      Point[] tempBody = temp.getBody();
      if(tempBody.length != body.length)
         return false;
      for(int index = 0; index < body.length; index++) {
         if(body[index].getX() != (tempBody[index]).getX() || 
               body[index].getY() != (tempBody[index]).getY())
            return false;
      }
      return true;
   }
   
   //This method sets the width of the piece.
   private void setWidth(Point[] points) {
      int low = 0;
      int high = 0;
      //for loop that goes through all elements of the points array.
      for(int index = 0; index < points.length; index++) {
         //Sets the low and high values to the x component of the first point.
         if(index == 0) {
            low = (int) points[index].getX();
            high = (int) points[index].getX();
         }
         
         //Changes the value of high if the next x component is greater than
         //the current value of high.
         if((int) points[index].getX() > high) {
            high = (int) points[index].getX();
         }
         
         //Changes the value of low if the next x component is less than
         //the current value of low.
         if((int) points[index].getX() < low) {
            low = (int) points[index].getX();
         }
      }
      width = high - low + 1;
   }
   
   //This method sets the height of the piece.
   private void setHeight(Point[] points) {
      int low = 0;
      int high = 0;
      //for loop that goes through all elements of the points array.
      for(int index = 0; index < points.length; index++) {
         //Sets the low and high values to the y component of the first point.
         if(index == 0) {
            low = (int) points[index].getY();
            high = (int) points[index].getY();
         }
         
         //Changes the value of high if the next y component is greater than
         //the current value of high.
         if((int) points[index].getY() > high) {
            high = (int) points[index].getY();
         }
         
         //Changes the value of low if the next y component is less than
         //the current value of low.
         if((int) points[index].getY() < low) {
            low = (int) points[index].getY();
         }
      }
      height = high - low + 1;
   }
   
   //This method sets the skirt of the piece.
   private void setSkirt(Point[] points, int w) {
      skirt = new int[w];
      for(int index = 0; index < skirt.length; index++) {
         skirt[index] = -1;
      }
      //for loop that goes through all elements of the points array.
      for(int index = 0; index < points.length; index++) {
         //This stores the x and y coordinates of the points.
         int x = (int) Math.round(points[index].getX());
         int y = (int) points[index].getY();
         //If the skirt in column x is uninitialized or higher than the y
         //coordinate in the point, it is lowered to that y coordinate.
         if(skirt[x] == -1 || skirt[x] > y) {
            skirt[x] = y;
         }
      }

      
   }
   
   //This method sets the body of the piece.
   private void setBody(Point[] points) {
      body = new Point[points.length];
      for(int index = 0; index < body.length; index++) {
         body[index] = new Point((int) points[index].getX(), 
               (int) points[index].getY());
      }
   }
   
   //Sets the piece type and rotation, so that it can be located.
   private void setID(int pT, int r) {
      rotation = r;
      pieceType = pT;
   }
   
   //Returns the piece's index of rotation.
   public int getRotation() {
      return rotation;
   }
   
   //This method rotates the points of a specific piece counterclockwise.
   public static Point[] rotatePoints(Point[] pts, int h) {
      Point[] points = new Point[pts.length];
      //Iterates through for loop
      for(int index = 0; index < points.length; index++) {
         int x = (int) pts[index].getX();
         int y = (int) pts[index].getY();
         
         //Creates new point at the index with the rotated coordinates.
         points[index] = new Point(h - y - 1, x);
      }
      
      //Removes empty rows and columns after rotation.
      boolean removeX = true;
      boolean removeY = true;
      //Checks if there are not any empty columns or rows.
      while(removeX || removeY)
      {
         for(Point p: points) {
            if(p.getX() == 0)
               removeX = false;
            if(p.getY() == 0)
               removeY = false;
         }
         for(int index = 0; index < points.length; index++) {
            //Decrements columns and rows if they are empty.
            if(removeX){
               points[index].setLocation(points[index].getX() - 1, 
                     points[index].getY());
            }
            if(removeY){
               points[index].setLocation(points[index].getX(), 
                     points[index].getY() - 1);
            }
         }   
      }
      //Increments negative rows and columns after rotation.
      boolean incX = true;
      boolean incY = true;
      //Checks if there are not any negative columns or rows.
      while(incX || incY)
      {
         incX = false;
         incY = false;
         for(Point p: points) {
            if(p.getX() < 0)
               incX = true;
            if(p.getY() < 0)
               incY = true;
         }
         for(int index = 0; index < points.length; index++) {
            //Increments columns and rows if they are empty.
            if(incX){
               points[index].setLocation(points[index].getX() + 1, 
                     points[index].getY());
            }
            if(incY){
               points[index].setLocation(points[index].getX(), 
                     points[index].getY() + 1);
            }
         }   
      }
      return points;
   }
   
   //Returns a piece that is rotated 90 degrees counter-clockwise.
   public Piece nextRotation() {
      TetrisPiece tP = pieceTypes[pieceType][(rotation + 1) % 
                                             pieceTypes[pieceType].length];
      return tP;
   }
   
   //Finds the position of the bottom heavy rotation of the piece type.
   public static int bottomHeavy(Piece[] pieces) {
      //Checks all of the piece rotation orientations.
      for(int index = 0; index < pieces.length; index++) {
         //Finds the heavy side of the piece.
         if(pieces[index].getWidth() > pieces[index].getHeight()) {
            Point[] points = pieces[index].getBody();
            int bottom = 0;
            int top = 0;
            //Tallies the number of points on the bottom and top rows.
            for(Point p: points) {
               if(p.getY() == 0)
                  bottom++;
               if(p.getY() == pieces[index].getHeight() - 1)
                  top++;
            }
            //This returns the index if this rotation is bottom heavy, or if
            //there is not a bottom heavy side.
            if(bottom >= top) {
               return index;
            }
         }
      }
      //If the width == the height for all pieces, then they are squares, and
      //they do not need to be reordered.
      return 0;
   }
}
