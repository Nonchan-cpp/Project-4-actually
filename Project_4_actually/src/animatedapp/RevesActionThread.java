package animatedapp;

import animatedapp.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
    

/**
 *  A thread that is used to solve Reve's problem - It is animated.
 * 
 * @author Charles Hoot 
 * @version 5.0
 */

    
public class RevesActionThread extends ActionThread
{
    
    /**
     * Constructor for objects of class RevesActionThread
     */
    public RevesActionThread()
    {
        super();

    }

    public String getApplicationTitle()
    {
        return "Reve's Puzzle (Skeleton)";
    }
    
    

    // **************************************************************************
    // This is application specific code
    // **************************************************************************    

    // These are the variables that are parameters of the application and can be
    // set via the application specific GUI
    // Make sure they are initialized
    private int disksToUse = 10;
   
    
    
    private int disks;
    
    // Displayed objects
    private Pole a, b, c, d;
    private int movesMade;
    private String moveString;
    
    public void init() 
    {
    	 try {
    	        if (disksTextField != null) {  // ensure GUI field exists
    	            int guiValue = Integer.parseInt(disksTextField.getText().trim());
    	            if (guiValue >= 1 && guiValue <= 10) {
    	                disksToUse = guiValue;
    	            }
    	        }
    	    } catch (Exception e) {
    	        // fallback, keep old value
    	    }

    	    movesMade = 0;
    	    moveString = "";

    	    // Always create 10-disk poles visually
    	    a = new Pole("Pole A", 10);
    	    b = new Pole("Pole B", 10);
    	    c = new Pole("Pole C", 10);
    	    d = new Pole("Pole D", 10);

    	    // Add all 10 disks visually
    	    for (int i = 10; i > 0; i--) {
    	        a.addDisk(new Disk(i));
    	    }
    	}
        

    public void executeApplication()
    {
    	 reves(disksToUse, a, d, b, c);
    	 
    	 throw new KillThreadException("Stopping after moving specified disks");
    }

    /**
     * Move a disk from one pole to another pole.
     *
     * @param from      The source pole.
     * @param to        The destination pole.
     */
    public void moveDisk(Pole from, Pole to)
    {
    	// Remove the top disk from the 'from' pole
        Disk toMove = from.removeDisk();
        
        // If there is a disk to move (i.e., the pole isn't empty), add it to the 'to' pole
        if (toMove != null) {
            to.addDisk(toMove);  
        }

        movesMade++;
        moveString = "Move #" + movesMade 
                        + ": Moved disk " + toMove.getSize() 
                        + " from " + from.getName() 
                        + " to " + to.getName();
                            
        // Pause the animation after the move
        animationPause();           
    }

    public void towersOfHanoi(int n, Pole from, Pole to, Pole extra)
    {
        if (n == 1) {
            moveDisk(from, to);
        } else {
            // Recursive case:
            
            towersOfHanoi(n - 1, from, extra, to);
            
            moveDisk(from, to);
            
            towersOfHanoi(n - 1, extra, to, from);
        }
    }
    
    public int computeK(int n)
    {
        int k = 1;
        // Loop until the kth triangular number is greater than or equal to n
        while ((k * (k + 1)) / 2 < n) {
            k++;
        }
        return k;
    }
    
    public void reves(int n, Pole from, Pole to, Pole extra1, Pole extra2) {
        if (n == 0) {
            // no disks to move
            return;
        }
        if (n == 1) {
            // base case: just move one disk
            moveDisk(from, to);
        } else {
            // 1) pick k based on triangular numbers
            int k = computeK(n);
            // 2) move top (n-k) disks to extra1
            reves(n - k, from, extra1, extra2, to);
            // 3) move remaining k disks from 'from' to 'to' via 3-pole Hanoi
            towersOfHanoi(k, from, to, extra2);
            // 4) move the (n-k) disks from extra1 to 'to'
            reves(n - k, extra1, to, from, extra2);
        }
    }
    
    // ADD METHODS HERE
    
    /***************************************************************************
     * *************************************************************************
     * ALL THE CODE PAST THIS POINT SHOULD NOT BE CHANGED
     * *************************************************************************
     * *************************************************************************
     */
    
    
    
    private static int DISPLAY_HEIGHT = 300;
    private static int DISPLAY_WIDTH = 500;
    
    public JPanel createAnimationPanel()
    {
        return new AnimationPanel();
    }
    
    private static int NORTH_PANEL_HEIGHT = 50;
    private static int INDENT = 50;
    private static int SCALE = 2;
    private static int TEXT_HEIGHT = 30;
    private static int MAX_DISKS = 15;
    

    // This privately defined class does the drawing the application needs
    public class AnimationPanel extends JPanel
    {
        public AnimationPanel()
        {
            super();
            setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        }
        
        public void paintComponent(Graphics g)
        {
            int sw;
            super.paintComponent(g);
            int delta = Disk.BASEWIDTH*SCALE*MAX_DISKS;
            
            // draw the move string if it has a value
            if(moveString != null)
            {
                g.drawString(moveString, INDENT, NORTH_PANEL_HEIGHT + TEXT_HEIGHT);
            }
            
            
            // draw the four poles if they have been created
            FontMetrics fm = g.getFontMetrics();            
            if(d!= null)
            {
                a.drawOn(g, delta/2, DISPLAY_HEIGHT-2*TEXT_HEIGHT, SCALE);
                sw = fm.stringWidth(a.getName());
                g.drawString(a.getName(), delta/2 - sw/2, DISPLAY_HEIGHT-TEXT_HEIGHT);
                
                b.drawOn(g, 3*delta/2, DISPLAY_HEIGHT-2*TEXT_HEIGHT, SCALE);
                sw = fm.stringWidth(b.getName());
                g.drawString(b.getName(), 3*delta/2 - sw/2, DISPLAY_HEIGHT-TEXT_HEIGHT);
                
                c.drawOn(g, 5*delta/2, DISPLAY_HEIGHT-2*TEXT_HEIGHT, SCALE);
                sw = fm.stringWidth(c.getName());
                g.drawString(c.getName(), 5*delta/2 - sw/2, DISPLAY_HEIGHT-TEXT_HEIGHT);
                
                d.drawOn(g, 7*delta/2, DISPLAY_HEIGHT-2*TEXT_HEIGHT, SCALE);
                sw = fm.stringWidth(d.getName());
                g.drawString(d.getName(), 7*delta/2 - sw/2, DISPLAY_HEIGHT-TEXT_HEIGHT);
            }

        }
    }
    
    // **************************************************************************
    // This is the application specific GUI code
    // **************************************************************************    

    private JTextField disksTextField;
    private JLabel setupStatusLabel;
    private JPanel setupPanel;
    
    public void setUpApplicationSpecificControls()
    {
        getAnimationPanel().setLayout(new BorderLayout());
        
        
        disksTextField = new JTextField("");
        disksTextField.addActionListener(
            new ActionListener() 
            {
                public void actionPerformed(ActionEvent event) 
                {
                    disksTextFieldHandler();
                    getAnimationPanel().repaint();
                }
            }
        );


        
        setupStatusLabel = new JLabel("");
        
        setupPanel = new JPanel();
        setupPanel.setLayout(new GridLayout(2,2));
        
        setupPanel.add(new JLabel("Number of disks (1-15):"));
        setupPanel.add(disksTextField);
        setupPanel.add(setupStatusLabel);
        
        getAnimationPanel().add(setupPanel,BorderLayout.NORTH);
               
    }

   
   
    private void disksTextFieldHandler()
    {
    try
        {
            if(applicationControlsAreActive())   // Only change if we are in the setup phase
            {
                String input = disksTextField.getText().trim();
                int value = Integer.parseInt(input);
                if( value>=1 &&value <= MAX_DISKS)
                {
                    disksToUse = value;
                    setupStatusLabel.setText("Set number of disks to " + disksToUse);
                }
                else
                {
                    setupStatusLabel.setText("Bad value for number of disks");
                }
                init();
                getAnimationPanel().repaint();
                
            }
        }
        catch(Exception e)
        {
            // don't change the delta if we had an exception
            setupStatusLabel.setText("Need integer value for number of disks");
        }
    
    }  
            
} // end class RevesActionThread

