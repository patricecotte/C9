package com.sdz.calculator9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.border.Border;

/**
 * <b>C9About	About screen</b>
 * <ul></ul>
 * The About screen has a link to the site's web page. The screen closes when clicked on.
 * <ul>It can be run as a standalone or as a runnable</ul>
 * <ul></ul> 
 * @see #C9About
 * @version		1.0
 * @author		Patrice Cotte
 * 
 */
//CTRL + SHIFT + O pour générer les imports nécessaires
 
public class C9About extends JWindow{
 
/**
 * Data
 */
private static final long serialVersionUID = 4899594647844623674L;
private static C9About abt = null;

private static JPanel pan = new JPanel();
private static JLabel label = null;
private static LinkLabel linkLabelWeb = null;
private static Color bgColor = Color.WHITE;


/**
 * @param  args the RGB strings for the background color (optional)
 */

public static void main(String[] args){

	// window must be final so that we can free it. Create a C9About GUI if it does not
	// already exist.
	
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			if (abt == null){
			try {			
				abt = new C9About();
				} catch (Exception e) {
				e.printStackTrace();
				} 
			}; // End of if
			
			// Apply the color that was passed or the default
			label.setBackground(bgColor);
		    pan.setBackground(bgColor);    
		    abt.setVisible(true);		
			
		} // End of run	
	});
	
	// If a RGB values have been passed update bgColor, add a transparency rate 
	if (args.length != 0){
		bgColor = new Color(Integer.parseInt(args[0]),
							Integer.parseInt(args[1]),
							Integer.parseInt(args[2]), 0x33);
		 label.setBackground(bgColor);
		 linkLabelWeb.setBackground(bgColor);
		}
	
    
  } // end of main
   
  /**
   * <b>C9About creates the following objects:</b>
   * <ul></ul>
   * <dl>JPanel		pan
   * 	<li>LinkLabel (JTextField)	linkLabelWeb 
   * 	<li>JLabel			text
   * </dl>
   * <ul></ul>
   */
  public C9About(){      
    setSize(400, 300);
    setLocationRelativeTo(null);      
    //JPanel pan = new JPanel();
    //JLabel img = new JLabel(new ImageIcon("planète.jpeg"));
    //img.setVerticalAlignment(JLabel.CENTER);
    //img.setHorizontalAlignment(JLabel.CENTER); 
    //pan.add(img);
       
    // Set up LinkLabelWeb an extended JTextField object that behaves like a URI
	try {
		linkLabelWeb = new LinkLabel(new URI("http://patricecotte.wix.com/mycloud"));
		       
	} catch (URISyntaxException e) {
		e.printStackTrace();
	}
    linkLabelWeb.setStandardColor(new Color(0,128,0));
    linkLabelWeb.setHoverColor(new Color(222,128,0));
    linkLabelWeb.init();
    
    // Customize text, the JLabel object
    String lbtext = "<html></b><center>Thank you for using this great software!</center></b><br><br>"+
        			 "Please check out the latest information" +
        			 "<br> and news at the below address<br><br></html>";
    label = new JLabel(lbtext);
    label.setFont(new Font("Segoe UI", Font.BOLD, 18));
    label.setForeground(new Color(0,0,255));
    label.setBackground(bgColor);
       
        
    // add LinkLabelWeb to panel and customize the panel
    pan.add(label,BorderLayout.SOUTH);
    pan.add(linkLabelWeb,BorderLayout.CENTER);
    pan.setBackground(bgColor);
    pan.setBorder(BorderFactory.createLineBorder(Color.blue));     
    getContentPane().add(pan);
    
   // Set the listener on the mouse so that to close the window when clicked on 
    this.addMouseListener(new MouseListener() {
		
		public void mouseReleased(MouseEvent e) {}
		
		public void mousePressed(MouseEvent e) {}
		
		public void mouseExited(MouseEvent e) {}
		
		public void mouseEntered(MouseEvent e) {}
		
		public void mouseClicked(MouseEvent e) {
			  	dispose();
		}
	});
    
    
  } // End of C9About
  
  
 
  /*
   * 	This runnable is for calling applications
   */
  class RunAbout implements Runnable {
  	  private Color color = null;
  	  private String[] ccrgb = new String[3];
  	 
  	  // Default constructor
  	  public RunAbout(){
  	  }
  	  
  	  // Receive a color sepcification
  	  public RunAbout(C9About c9abt, Color cc){
  		  this.color = cc;
  		  ccrgb[0] = Integer.toString(this.color.getRed()); 
  		  ccrgb[1] = Integer.toString(this.color.getGreen());
  		  ccrgb[2] = Integer.toString(this.color.getBlue());
  		  
  		  abt = c9abt;		// Set the reference to the Object
  	  }
  	  public void run() {
  		  main(ccrgb); 
  	  }                           
   } // End of RunAbout class   
  
 /*
  * Class LinkLabel extends JTextField so we can set up different forms
  */
  public class LinkLabel extends JTextField   implements MouseListener, FocusListener, ActionListener {
	    
	    private URI target;			// The target or href of this link. 

	    public Color standardColor = new Color(0,0,255);
	    public Color hoverColor = new Color(255,0,0);
	    public Color activeColor = new Color(128,0,128);
	    public Color transparent = new Color(0,0,0,0);

	    public boolean underlineVisible = true;

	    private Border activeBorder;
	    private Border hoverBorder;
	    private Border standardBorder;
	    

	    // Form #1 - the URI is also the text user sees
	    public LinkLabel(URI target) {
	        this( target, target.toString() );
	    }

	    // Form #2 - the URI is associated to some text 
	    public LinkLabel(URI target, String text) {
	        super(text);
	        this.target = target;
	    }

	    // Set the active color for this link (default is purple).
	    public void setActiveColor(Color active) {
	        activeColor = active;
	    }

	    // Set the hover/focused color for this link (default is red).
	    public void setHoverColor(Color hover) {
	        hoverColor = hover;
	    }

	    // Set the standard (non-focused, non-active) color for this link (default is blue).
	    public void setStandardColor(Color standard) {
	        standardColor = standard;
	    }

	    // Make underline visible or hidden
	    public void setUnderlineVisible(boolean underlineVisible) {
	        this.underlineVisible = underlineVisible;
	    }

	    // add listeners so that the URI acts like .. an URI
	    public void init() {
	        this.addMouseListener(this);
	        this.addFocusListener(this);
	        this.addActionListener(this);
	        setToolTipText(target.toString());

	        /*
	        if (underlineVisible) {
	            activeBorder = new MatteBorder(0,0,1,0,activeColor);
	            hoverBorder = new MatteBorder(0,0,1,0,hoverColor);
	            standardBorder = new MatteBorder(0,0,1,0,transparent);
	        } else {
	            activeBorder = new MatteBorder(0,0,0,0,activeColor);
	            hoverBorder = new MatteBorder(0,0,0,0,hoverColor);
	            standardBorder = new MatteBorder(0,0,0,0,transparent);
	        }
			*/
	        
	        // make it appear like a label/link
	        setEditable(false);
	        setFont(new Font("Segoe UI", Font.BOLD, 16));
	        setForeground(standardColor);
	        setBackground(bgColor);
	        setBorder(hoverBorder); 
	        setCursor( new Cursor(Cursor.HAND_CURSOR) );
	    }

	    // Below are the listeners for action, mouse and focus
	    public void actionPerformed(ActionEvent ae) {
	        browse();
	    }

	    public void mouseClicked(MouseEvent me) {
	        browse();
	        me.consume();
	    }

	    public void mouseEntered(MouseEvent me) {
	        //setForeground(hoverColor);
	        //setBorder(hoverBorder);
	    }

	    public void mouseExited(MouseEvent me) {
	        //setForeground(standardColor);
	        //setBorder(standardBorder);
	    }

	    public void mouseReleased(MouseEvent me) {}

	    public void mousePressed(MouseEvent me) {}

	    /** Set the color to the standard color. */
	    public void focusLost(FocusEvent fe) {
	        //setForeground(standardColor);
	        //setBorder(standardBorder);
	    }

	    /** Set the color to the hover color. */
	    public void focusGained(FocusEvent fe) {
	        //setForeground(hoverColor);
	        //setBorder(hoverBorder);
	    }
	    
	    
	    /** Browse to the target URI using the Desktop.browse(URI)
	    method.  For visual indication, change to the active color
	    at method start, and return to the standard color once complete.
	    This is usually so fast that the active color does not appear,
	    but it will take longer if there is a problem finding/loading
	    the browser or URI (e.g. for a File). */
	    public void browse() {
	        setForeground(activeColor);
	        setBorder(activeBorder);
	        try {
	            Desktop.getDesktop().browse(target);
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	        setForeground(standardColor);
	        setBorder(standardBorder);
	    }

  } // End of LinkLabel class
  
} // End of C9About class

