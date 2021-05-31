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
        BrainFactory b = new BrainFactory();
        ArrayList<Brain> brains = b.createBrains();
        String[] brainArray = new String[brains.size()];
        for(int i = 0; i < brains.size(); i++)
        {
        String str = brains.get(i).toString();
        brainArray[i] = str;
        
        }
        comboBox = new JComboBox(brainArray);
        c.add(comboBox);
        BrainListener listener = new BrainListener();
        comboBox.addActionListener(listener);
        enabler = new JButton("Enable Brain");
        c.add(enabler);
        EnableListener enableListener = new EnableListener();
        enabler.addActionListener(enableListener);
        return c;
    }
    public class EnableListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
        
        
        
        isBrainOn = true;
        enabler.setText("Brain Enabled");
        
        }
    
    }
    public class BrainListener implements ActionListener
    {
        @Override    
        public void actionPerformed(ActionEvent event)
        {
            if(comboBox.getSelectedItem().equals("Small Brain"))
            {
                brain = new SmallBrain();
        
            }
            else if(comboBox.getSelectedItem().equals("Simple Brain"))
            {
            
            brain = new SimpleBrain();
            
            }
        }

    }
    @Override
    public Piece pickNextPiece()
    {
    
    return null;
    
    }
}
