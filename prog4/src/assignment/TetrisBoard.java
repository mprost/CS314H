package assignment;

import java.awt.*;
import java.util.Arrays;

/**
 * Represents a Tetris board -- essentially a 2-d grid of
 * booleans. Supports tetris pieces and row clearing.
 * Does not do any drawing or have any idea of pixels. Instead, just
 * represents the abstract 2-d board.
 */
public final class TetrisBoard implements Board {

   private boolean[][] matrix;
   
   private TetrisPiece currentPiece;
   private int pieceRow;
   private int pieceCol;
   
   private Action lastAction;
   private Result lastResult;
   
   private int rowsCleared;
   private int maxHeight;
   private int[] columnHeight;
   private int[] rowWidth;
   
   // JTetris will use this constructor
   public TetrisBoard(int Width, int Height) {
      matrix = new boolean[Height][Width];
      columnHeight = new int[Width];
      rowWidth = new int[Height];
      rowsCleared = 0;
      maxHeight = 0;
      currentPiece = null;
   }

   //Allows the client to interact with the active piece on the board.
   public Result move(Action act) {
      lastAction = act;
      TetrisPiece rotatedPiece = new TetrisPiece();
      int max = Math.abs(currentPiece.getHeight() - 
            currentPiece.getWidth());
      if(currentPiece == null) {
         lastResult = Result.NO_PIECE;
         return Result.NO_PIECE;
      }
      int[] skirt = new int[0];
      rowsCleared = 0;
      //Removes the piece from the grid.
      Point[] points = currentPiece.getBody();
      for(Point pt: points) {
         //Checks if the point is in bounds.
         if(pt.getY() + pieceRow >= 0 && pt.getX() + pieceCol >= 0 && pt.getY()
               + pieceRow < getHeight() && pt.getX() + pieceCol < getWidth()) {
            matrix[(int) pt.getY() + pieceRow][(int) pt.getX() + pieceCol] = 
               false;
         }
      }
      switch(act) {
      
         case LEFT : 
            //Because the piece can never have a point with a negative 
            //x-coordinate, the column determines if it is in bounds to the 
            //left.
            if(pieceCol <= 0) {
               lastResult = Result.OUT_BOUNDS;
               return Result.OUT_BOUNDS;
            }  
            //Detects points to the left of the body of currentPiece.
            if(obstructed(points, -1, 0)) {
               lastResult = Result.OBSTRUCTED;
               return Result.OBSTRUCTED;
            }
            //Decrements the column upon successful movement.
            pieceCol--;
            break;
            
         case RIGHT :
            //Because the piece always has an x-coordinate in its rightmost 
            //point, the column + the width determines if it is in bounds to 
            //the right.
            if(pieceCol + currentPiece.getWidth() >= getWidth()) {
               lastResult = Result.OUT_BOUNDS;
               return Result.OUT_BOUNDS;
            }
            //Detects points to the right of the body of currentPiece.
            if(obstructed(points, 1, 0)) {
               lastResult = Result.OBSTRUCTED;
               return Result.OBSTRUCTED;
            }
            //Increments the column upon successful movement.
            pieceCol++;
            break;
            
         case DOWN :
            //Checks if the piece is in the bottom row.
            if(pieceRow == 0) {
               place(points);
               lastResult = Result.PLACE;
               return Result.PLACE;
            }
            skirt = currentPiece.getSkirt();
            //Compares the skirt with the points below it in the matrix.
            for(int c = pieceCol; c < pieceCol + currentPiece.getWidth(); c++) {
               if(matrix[pieceRow - 1 + skirt[c - pieceCol]][c]) {
                  place(points);
                  lastResult = Result.PLACE;
                  return Result.PLACE;
               }
            }
            pieceRow--;
            break;
            
         case DROP :
            //Checks if the piece is in the bottom row.
            if(pieceRow == 0) {
               place(points);
               lastResult = Result.PLACE;
               return Result.PLACE;
            }
            skirt = currentPiece.getSkirt();
            //Compares the skirt with the points below it in the matrix.
            for(int c = pieceCol; c < pieceCol + currentPiece.getWidth(); c++) {
               if(matrix[pieceRow - 1 + skirt[c - pieceCol]][c]) {
                  place(points);
                  lastResult = Result.PLACE;
                  return Result.PLACE;
               }
            }
            //Finds the greatest difference between the skirt and the column
            //height.
            int minDifference = dropHeight(currentPiece, pieceCol);
            pieceRow -= minDifference;
            place(points);
            lastResult = Result.PLACE;
            return Result.PLACE;
         
         case CLOCKWISE :
            rotatedPiece = (TetrisPiece) currentPiece.nextRotation()
                  .nextRotation().nextRotation();
            points = rotatedPiece.getBody();
            boolean resolved = true;
            max = Math.abs(rotatedPiece.getHeight() - rotatedPiece.getWidth());
            if(outOfBounds(points, 0, 0)) {
               resolved = false;
               for(int index = 1; index <= max + 2; index++) {
                  
                  if(!outOfBounds(points, 0, -index) && 
                        !obstructed(points, 0, -index)) {
                     pieceRow -= index;
                     resolved = true;
                     break;
                  }
                  else if(!outOfBounds(points, 0, index) && 
                        !obstructed(points, 0, index)) {
                     pieceRow += index;
                     resolved = true;
                     break;
                  }
                  else if(!outOfBounds(points, -index, 0) && 
                        !obstructed(points, -index, 0)) {
                     pieceCol -= index;
                     resolved = true;
                     break;
                  }
                  else if(!outOfBounds(points, index, 0) && 
                        !obstructed(points, index, 0)) {
                     pieceCol += index;
                     resolved = true;
                     break;
                  }
                  
               }
               if(!resolved) {
                  lastResult = Result.OUT_BOUNDS;
                  return Result.OUT_BOUNDS;
               }
            }
            //Mathematically shifts the pieces to adjust for center of 
            //rotation.
            currentPiece = (TetrisPiece) currentPiece.nextRotation()
                  .nextRotation().nextRotation();
            points = currentPiece.getBody();
            switch(currentPiece.getRotation()) {
            case 0:
               if(outOfBounds(points, 0, 0)) {
                  lastResult = Result.OUT_BOUNDS;
                  return Result.OUT_BOUNDS;
               }
               else if(obstructed(points, 0, 0)) {
                  lastResult = Result.OBSTRUCTED;
                  return Result.OBSTRUCTED;
               }
               break;
            case 1:
               if(outOfBounds(points, 0, 0)) {
                  lastResult = Result.OUT_BOUNDS;
                  return Result.OUT_BOUNDS;
               }
               else if(obstructed(points, 0, 0)) {
                  lastResult = Result.OBSTRUCTED;
                  return Result.OBSTRUCTED;
               }
            case 2: if(outOfBounds(points, 0, 0)) {
                  lastResult = Result.OUT_BOUNDS;
                  return Result.OUT_BOUNDS;
               }
               else if(obstructed(points, 0, 0)) {
                  lastResult = Result.OBSTRUCTED;
                  return Result.OBSTRUCTED;
               }
               break;
            case 3:
               if(outOfBounds(points, 0, 0)) {
                  lastResult = Result.OUT_BOUNDS;
                  return Result.OUT_BOUNDS;
               }
               else if(obstructed(points, 0, 0)) {
                  lastResult = Result.OBSTRUCTED;
                  return Result.OBSTRUCTED;
               }
               break;
            }
            //Adds the piece to the grid.
            for(Point pt: points) {
               matrix[(int)pt.getY() + pieceRow][(int) pt.getX() + pieceCol]
                     = true;
            }
            break;
         case COUNTERCLOCKWISE : 
            points = currentPiece.nextRotation().getBody();
            //The rotation is successful.
            rotatedPiece = (TetrisPiece) currentPiece.nextRotation();
            
            max = Math.abs(currentPiece.getHeight() - currentPiece.getWidth());
            if(outOfBounds(points, 0, 0)) {
               for(int index = 1; index <= max; index++) {
                  if(!outOfBounds(points, 0, -index) && 
                        !obstructed(points, 0, -index)) {
                     pieceCol -= index;
                     break;
                  }
                  else if(!outOfBounds(points, 0, index) && 
                        !obstructed(points, 0, index)) {
                     pieceCol += index;
                     break;
                  }
                  else if(!outOfBounds(points, -index, 0) && 
                        !obstructed(points, -index, 0)) {
                     pieceRow -= index;
                     break;
                  }
                  else if(!outOfBounds(points, index, 0) && 
                        !obstructed(points, +index, 0)) {
                     pieceRow += index;
                     break;
                  }
               }
            }
            //Mathematically shifts the pieces to adjust for center of 
            //rotation.
            currentPiece = (TetrisPiece) currentPiece.nextRotation();
            switch(currentPiece.getRotation()) {
            case 0:
               if(outOfBounds(points, 0, 0)) {
                  lastResult = Result.OUT_BOUNDS;
                  return Result.OUT_BOUNDS;
               }
               else if(obstructed(points, 0, 0)) {
                  lastResult = Result.OBSTRUCTED;
                  return Result.OBSTRUCTED;
               }
               break;
            case 1:
               if(outOfBounds(points, 0, 0)) {
                  lastResult = Result.OUT_BOUNDS;
                  return Result.OUT_BOUNDS;
               }
               else if(obstructed(points, 0, 0)) {
                  lastResult = Result.OBSTRUCTED;
                  return Result.OBSTRUCTED;
               }
               break;
            case 2:
               if(outOfBounds(points, 0, 0)) {
                  lastResult = Result.OUT_BOUNDS;
                  return Result.OUT_BOUNDS;
               }
               else if(obstructed(points, 0, 0)) {
                  lastResult = Result.OBSTRUCTED;
                  return Result.OBSTRUCTED;
               }
               break;
            case 3: if(outOfBounds(points, 0, 0)) {
                  lastResult = Result.OUT_BOUNDS;
                  return Result.OUT_BOUNDS;
               }
               else if(obstructed(points, 0, 0)) {
                  lastResult = Result.OBSTRUCTED;
                  return Result.OBSTRUCTED;
               }
               break;
            }
            //Adds the piece to the grid.
            for(Point pt: points) {
               matrix[(int)pt.getY() + pieceRow][(int) pt.getX() + pieceCol]
                     = true;
            }
            break;
         //Does Nothing.
         case NOTHING : break;
      }
      //Adds the piece to the grid.
      for(Point pt: points) {
         matrix[(int)pt.getY() + pieceRow][(int) pt.getX() + pieceCol] = true;
      }
      lastResult = Result.SUCCESS;
      return Result.SUCCESS;
   }
   
   //Determines if a piece would be out of bounds in a certain position.
   private boolean outOfBounds(Point[] points, int x, int y) {
      //Checks validity of all points in the array.
      for(int index = 0; index < points.length; index++) {
         //Checks that the x-coordinates are in bounds.
         if((int) points[index].getX() + x + pieceCol >= getWidth() || 
               (int) points[index].getX() + x + pieceCol < 0) {
            return true;
         }
         //Checks that the y-coordinates are in bounds.
         if((int) points[index].getY() + y + pieceRow >= getHeight() || 
               (int) points[index].getY() + y + pieceRow < 0) {
            return true;
         }
      }
      return false;
   }
   
   //Determines if a piece would be obstructed in a certain position.
   private boolean obstructed(Point[] points, int x, int y) {
      //Checks validity of all points in the array.
      for(int index = 0; index < points.length; index++) {
         //Checks if the matrix is already full at that coordinate. 
         if(matrix[(int) points[index].getY() + y + pieceRow]
               [(int) points[index].getX() + x + pieceCol]) {
            return true;
         }
      }
      return false;
   }
   
   //Places the tetris piece and calculates new board values.
   private void place(Point[] points) {
      //The top skirt tells the height of each of the columns.
      int[] topSkirt = new int[currentPiece.getWidth()];
      
      //Adds the piece to the grid.
      for(int index = 0; index < points.length; index++) {
         Point pt = points[index];
         matrix[(int) pt.getY() + pieceRow][(int) pt.getX() + pieceCol] = true;
         //Keeps track of the highest points in the piece.
         if(index == 0 || (int) pt.getY() > topSkirt[(int) pt.getX()]) {
            topSkirt[(int) pt.getX()] = (int) pt.getY();
         }
         //Adds the piece to the row width
         rowWidth[(int) pt.getY() + pieceRow]++;
      }
      //Adds the placed  piece to the column heights.
      for(int index = 0; index < topSkirt.length; index++) {
         columnHeight[index + pieceCol] += topSkirt[index] + 1;
         if(columnHeight[index + pieceCol] > maxHeight) {
            maxHeight = columnHeight[index + pieceCol];
         }
      }
      //Clears any potential rows.
      clearRows(pieceRow, pieceRow + currentPiece.getHeight());
   }

   //Returns a new board whose state is equal to what the state of this board 
   //would be after the input Action. This is useful for any AI using the 
   //board.
   public Board testMove(Action act) {
      TetrisBoard tempBoard = new TetrisBoard(getWidth(), getHeight());
      tempBoard.setBoard(matrix, currentPiece, pieceRow, pieceCol, 
            rowsCleared, columnHeight, rowWidth, maxHeight);
      tempBoard.move(act);
      return tempBoard;
   }

   public void nextPiece(Piece p) {
      currentPiece = (TetrisPiece)p;
      pieceCol = getWidth() / 2 - p.getWidth() / 2;
      pieceRow = getHeight() - currentPiece.getHeight();
      //Adds the piece to the grid.
      Point[] points = currentPiece.getBody();
      for(Point pt: points) {
         matrix[(int)pt.getY() + pieceRow][(int) pt.getX() + pieceCol] = true;
      }
      
   }

   //This tells if the board is in the same state as another board.
   public boolean equals(Object other) {
      TetrisBoard b = (TetrisBoard)other;
      //This compares all of the accessor methods of the two boards to see
      //if the boards are the same size and have had the same actions, etc.
      if(getWidth() != b.getWidth() || getHeight() != b.getHeight() || 
            getRowsCleared() != b.getRowsCleared())
         return false;
      //This compares the blocks in the two matrices to see if the boards
      //are in the same state.
      for(int r = 0; r < matrix.length; r++) {
         for(int c = 0; c < matrix[r].length; c++) {
            //The getGrid method uses x y coordinates and the matrix uses r c
            //coordinates, so they are flipped.
            if(matrix[r][c] != b.getGrid(c, r)) {
               return false;
            }
         }
      }
      return true;
   }

   public Result getLastResult() {
      return lastResult; 
   }

   public Action getLastAction() { 
      return lastAction;
   }

   public int getRowsCleared() {
      return rowsCleared;
   }

   //Returns the width of the board.
   public int getWidth() {
      return matrix[0].length;
   }

   //Returns the height of the board.
   public int getHeight() {
      return matrix.length;
   }

   //Returns the maximum column height present in the board.
   public int getMaxHeight() {
      return maxHeight;
   }

   public int dropHeight(Piece piece, int x) {
      //Finds the smallest difference between the skirt and the column
      //height.
      int[] skirt = piece.getSkirt();
      int minDifference = 0;
      for(int c = x; c < x + piece.getWidth(); c++) {
         int difference = (pieceRow + skirt[c - x]) - (getColumnHeight(c));
         if(c == x || minDifference > difference) {
            minDifference = difference;
         }
      }
      return minDifference;
   }

   //Returns the height of a given column.
   public int getColumnHeight(int x) {
      return columnHeight[x];
   }

   //Returns the number of filled blocks in a given row.
   public int getRowWidth(int y) {
      return rowWidth[y];
   }

   //This returns if a spot in the grid is occupied.
   public boolean getGrid(int x, int y) {
      try{
         //The matrix implements row column order, so the x and y coordinates
         //correspond inversely.
         return matrix[y][x];
      }
      catch(Exception e) {
         //Out of bounds blocks always return true.
         return true;
      }
   }

   //This method removes all filled rows of range [start, stop).
   private void clearRows(int start, int stop) {
      for(int r = stop - 1; r >= start; r--) {
         //Checks if the entire row is filled or not.
         boolean isFilled = true;
         for(int c = 0; c < matrix[0].length && isFilled; c++) {
            if(!matrix[r][c]) {
               isFilled = false;
            }
         }
         //If row r is filled, this moves all other rows down 1.
         if(isFilled) {
            rowsCleared++;
            for(int row = r + 1; row < matrix.length; row++) {
               matrix[row - 1] = 
                     Arrays.copyOf(matrix[row], matrix[row].length);
               rowWidth[row - 1] = rowWidth[row];
            }
            //Clears the top row.
            matrix[matrix.length - 1] = new boolean[matrix.length];
            rowWidth[matrix.length - 1] = 0;
         }
      }
      //Reevaluates column heights.
      boolean bot = true;
      for(int x = 0; x < matrix[0].length; x++) {
         for(int y = matrix.length - 1; y >= 0 && bot; y--) {
            if(matrix[y][x]) {
               columnHeight[x] = y + 1;
               bot = false;
            }
            if(x == 0 || columnHeight[x] > maxHeight) {
               maxHeight = columnHeight[x];
            }
         }
         if(bot) {
            columnHeight[x] = 0;
         }
         bot = true;
      }
   }
   
   //This method sets all the values.
   private void setBoard(boolean[][] Matrix, TetrisPiece CurrentPiece, 
         int PieceRow, int PieceCol, int RowsCleared, int[] ColumnHeight, 
         int[] RowWidth, int MaxHeight) {
      for(int row = 0; row < Matrix.length; row++) {
         matrix[row] = Arrays.copyOf(Matrix[row], Matrix[row].length);
      }
      currentPiece = CurrentPiece;
      pieceRow = PieceRow;
      pieceCol = PieceCol;
      rowsCleared = RowsCleared;
      columnHeight = Arrays.copyOf(ColumnHeight, ColumnHeight.length);
      rowWidth = Arrays.copyOf(RowWidth, RowWidth.length);
      maxHeight = MaxHeight;
   }
   
}
