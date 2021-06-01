import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
/**
 * Write a description of class JBrainTetris here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class JBrainTetris extends JTetris
{
    // instance variables - replace the example below with your own
    private Brain brain;
    private JComboBox comboBox;
    private boolean isBrainOn;
    private JButton enabler;
    private Move move; 
    /**
     * Constructor for objects of class JBrainTetris
     */
    public JBrainTetris(int width, int height)
    {
        // initialise instance variables
        super(width, height);
        isBrainOn = false;
    }

    @Override
    public Container createControlPanel()
    {
        Container c = super.createControlPanel();
        Brain[] brainArray = BrainFactory.createBrains().toArray(new Brain[0]);
        comboBox = new JComboBox(brainArray);
        brain = (Brain)comboBox.getSelectedItem();
        ActionListener b = new BrainListener();
        comboBox.addActionListener(b);
        c.add(comboBox);
        ActionListener e = new EnableListener();
        JButton enabler = new JButton("Enable Brain");
        enabler.addActionListener(e);
        c.add(enabler);
        return c;
    }
    public class EnableListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            random = new Random();
            isBrainOn = true;

            if(gameOn)
            {
                tick(DROP);
            }
            pickNextPiece();
            if (isBrainOn){ 
                enabler.setText("Enable Brain");
            }
            else {
                enabler.setText("Brain is Enabled");
            }
        }

    }
    public class BrainListener implements ActionListener
    {
        @Override    
        public void actionPerformed(ActionEvent event)
        {
            JComboBox box = (JComboBox) event.getSource();
            brain = (Brain) box.getSelectedItem();
        }

    }
    @Override
    public Piece pickNextPiece()
    {
        pieceNum = (int)(this.pieces.length * this.random.nextDouble());

        if(isBrainOn)
        {
            move = brain.bestMove(board, pieces[pieceNum], 16);

        }

        return pieces[pieceNum];

    }

    @Override 
    public void tick(int verb)
    {
        if(isBrainOn && verb == DOWN && move != null)
        {
            if(currentPiece.nextRotation() != move.getPiece().nextRotation())
            {
                tick(ROTATE);
            }
            if(move.getX() < currentX)
            {
                tick(LEFT);

            }
            else if(move.getX() > currentX)
            {

                tick(RIGHT);
            }
        }
        if (!this.gameOn)
        {
            return;
        }

        if (this.currentPiece != null)
        {
            this.board.undo();   // remove the piece from its old position
        }

        // Sets the newXXX attributes
        this.computeNewPosition(verb);

        // try out the new position (rolls back if it doesn't work)
        int status = this.setCurrent(this.newPiece, this.newX, this.newY);

        // if row clearing is going to happen, draw the whole board so the green
        //      row shows up
        if (status ==  Board.PLACE_ROW_FILLED)
        {
            this.repaint();
        }

        boolean failed = (status >= Board.PLACE_OUT_BOUNDS);
        // if it didn't work, put it back the way it was
        if (failed)
        {
            if (this.currentPiece != null)
            {
                this.board.place(this.currentPiece, this.currentX, this.currentY);
            }
        }

        /*
         * How to detect when a piece has landed:
         *      if this move hits something on its DOWN verb, and the previous
         *          verb was also DOWN (i.e. the player was not still moving it),
         *          then the previous position must be the correct "landed"
         *          position, so we're done with the falling of this piece.
         */
        if (failed && verb==DOWN && !this.moved)   // it's landed
        {
            if (this.board.clearRows())
            {
                this.repaint();  // repaint to show the result of the row clearing
            }

            // if the board is too tall, we've lost
            if (this.board.getMaxHeight() > this.board.getHeight() - TOP_SPACE)
            {
                this.stopGame();
            }
            // Otherwise add a new piece and keep playing
            else
            {
                this.addNewPiece();
            }
        }

        // Note if the player made a successful non-DOWN move --
        //      used to detect if the piece has landed on the next tick()
        this.moved = (!failed && verb!=DOWN);
    }
}

