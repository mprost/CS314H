package assignment;

/**
 * Implementers of this interface represent the behavior of a Tetris board.
 */
public interface Board {

   public enum Result {
      SUCCESS, OUT_BOUNDS, OBSTRUCTED, NO_PIECE, PLACE
   }

   public enum Action {
      LEFT, RIGHT, DOWN, DROP, CLOCKWISE, COUNTERCLOCKWISE, NOTHING,
      HOLD // Used only in Karma
   }


   /**
    * Allows the client to interact with the active piece on the board.
    * The above enums are used for the inputs and outputs of the function
    */
   Result move(Action act);


   /**
    * Returns a new board whose state is equal to what the state of this
    * board would be after the input Action.
    * This is useful for any AI using the board.
    */
   Board testMove(Action act);


   /**
    * Give a piece to the board to use as its next piece
    */
   void nextPiece(Piece p);


   /**
    * Every board should be able to tell if another Board is in the same
    * state as itself
    */
   boolean equals(Object other);


   /**
    * Returns the result of the last action given to the board.
    */
   Result getLastResult();


   /**
    * Returns the last action given to the board.
    */
   Action getLastAction();


   /**
    * Returns the number of rows cleared by the last action.
    */
   int getRowsCleared();


   /**
    * Returns the width of the board in blocks.
    */
   int getWidth();


   /**
    * Returns the height of the board in blocks.
    */
   int getHeight();


   /**
    * Returns the max column height present in the board.
    * For an empty board this is 0.
    */
   int getMaxHeight();


   /**
    * Given a piece and an x, returns the y
    * value where the piece would come to rest
    * if it were dropped straight down at that x.
    */
   int dropHeight(Piece piece, int x);


   /**
    * Returns the height of the given column --
    * i.e. the y value of the highest block + 1.
    * The height is 0 if the column contains no blocks.
    */
   int getColumnHeight(int x);


   /**
    * Returns the number of filled blocks in
    * the given row.
    */
   int getRowWidth(int y);


   /**
    * Returns true if the given block is filled in the board.
    * Blocks outside of the valid width/height area
    * always return true.
    */
   boolean getGrid(int x, int y);
}
