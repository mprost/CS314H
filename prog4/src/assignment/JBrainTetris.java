package assignment;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

public class JBrainTetris extends JTetris {
   
   public JBrainTetris() {
     super();
     setPreferredSize(new Dimension(WIDTH*PIXELS+2,
                            (HEIGHT+TOP_SPACE)*PIXELS+2));
     gameOn = false;

     board = new TetrisBoard(WIDTH, HEIGHT + TOP_SPACE);

     Brain brain = new AwesomeBrain();
     
     //Implements the brain's ticks with a timer.
     timer = new javax.swing.Timer(DELAY, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              tick(brain.nextMove(board));
           }
        });
   }
   
   public static void main(String[] args) {
     createGUI(new JBrainTetris());
  }

}
