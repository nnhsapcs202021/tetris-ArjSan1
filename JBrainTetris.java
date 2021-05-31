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
    /**
     * Constructor for objects of class JBrainTetris
     */
    public JBrainTetris(int width, int height)
    {
        // initialise instance variables
        super(width, height);

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
        Listener listener = new Listener();
        comboBox.addActionListener(listener);
        return c;
    }
    class Listener implements ActionListener
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
}
