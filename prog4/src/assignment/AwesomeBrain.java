package assignment;

import java.util.*;

import assignment.Board.Action;
import assignment.Board.Result;

public class AwesomeBrain implements Brain {
   
   private TreeMap<Integer, ArrayList<Board.Action>> scoreBoard;
   private ArrayList<Board.Action> actionList;
   private boolean newPiece;
   
   private int filled;
   private int rowsCleared;
   private int maxHeight;
   
   public AwesomeBrain() {
      scoreBoard = new TreeMap<Integer, ArrayList<Board.Action>>();
      newPiece = true;
      
      rowsCleared = 5000;
      maxHeight = 1;
      filled = 4;
   }
   
   public Board.Action nextMove(Board currentBoard) {
      if(newPiece) {
         checkMove(currentBoard, new ArrayList<Board.Action>());
         rightMove(currentBoard, new ArrayList<Board.Action>());
         leftMove(currentBoard, new ArrayList<Board.Action>());
         
         Board board90 = currentBoard.testMove(Action.COUNTERCLOCKWISE);
         if(board90.getLastResult() == Result.SUCCESS) {
            ArrayList<Board.Action> actions90 = new ArrayList<Board.Action>();
            actions90.add(Action.COUNTERCLOCKWISE);
            checkMove(board90, actions90);
            rightMove(board90, actions90);
            leftMove(board90, actions90);
         }
         
         Board board180 = currentBoard.testMove(Action.COUNTERCLOCKWISE);
         board180 = board180.testMove(Action.COUNTERCLOCKWISE);
         if(board180.getLastResult() == Result.SUCCESS) {
            ArrayList<Board.Action> actions180 = new ArrayList<Board.Action>();
            actions180.add(Action.COUNTERCLOCKWISE);
            actions180.add(Action.COUNTERCLOCKWISE);
            checkMove(board180, actions180);
            rightMove(board180, actions180);
            leftMove(board180, actions180);
         }
         
         Board board270 = currentBoard.testMove(Action.CLOCKWISE);
         if(board270.getLastResult() == Result.SUCCESS) {
            ArrayList<Board.Action> actions270 = new ArrayList<Board.Action>();
            actions270.add(Action.CLOCKWISE);
            checkMove(board270, actions270);
            rightMove(board270, actions270);
            leftMove(board270, actions270);
         }
         newPiece = false;
         actionList = scoreBoard.get(scoreBoard.lastKey());
         //System.out.println(actionList);
         //System.out.println(scoreBoard);
      }
      if(actionList.size() == 1) {
         scoreBoard = new TreeMap<Integer, ArrayList<Board.Action>>();
         newPiece = true;
      }
      return actionList.remove(0);
   }
   
   public void leftMove(Board currentBoard, ArrayList<Board.Action> moves) {
      checkMove(currentBoard, moves);
      Board leftBoard = currentBoard.testMove(Action.LEFT);
      if(leftBoard.getLastResult() == Result.SUCCESS) {
         ArrayList<Board.Action> actions = new ArrayList<Board.Action>();
         actions.addAll(moves);
         actions.add(Action.LEFT);
         leftMove(leftBoard, actions);
      }
   }
   
   public void rightMove(Board currentBoard, ArrayList<Board.Action> moves) {
      Board rightBoard = currentBoard.testMove(Action.RIGHT);
      if(rightBoard.getLastResult() == Result.SUCCESS) {
         ArrayList<Board.Action> actions = new ArrayList<Board.Action>();
         actions.addAll(moves);
         actions.add(Action.RIGHT);
         rightMove(rightBoard, actions);
      }
      checkMove(currentBoard, moves);
   }
   
   public void checkMove(Board currentBoard, ArrayList<Board.Action> moves) {
      ArrayList<Board.Action> actions = new ArrayList<Board.Action>();
      actions.addAll(moves);
      evaluateScore(currentBoard.testMove(Action.DROP), actions);
   }
   
   public void evaluateScore(Board currentBoard, ArrayList<Board.Action> moves)
   {
      ArrayList<Board.Action> actions = new ArrayList<Board.Action>();
      actions.addAll(moves);
      actions.add(Action.DROP);
      int score = rowsCleared * currentBoard.getRowsCleared();
      //System.out.println(currentBoard.getMaxHeight());
      for(int row = 0; row < currentBoard.getMaxHeight(); row++) {
         score -= (row + 1) * filled * currentBoard.getRowWidth(row);
         //System.out.println("Row: "+row+", "+currentBoard.getRowWidth(row));
      }
      scoreBoard.put(score, actions);
      //System.out.println(actions + " " +score);
   }
}
