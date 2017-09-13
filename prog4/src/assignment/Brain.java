package assignment;

/**
 * Brain interface for JTetris
 */

public interface Brain {
  /**
   * Decide what the next move should be based on the state of the board.
   */
  Board.Action nextMove(Board currentBoard);
}
