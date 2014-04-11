package com.sdz.calculator9;

 /*
  * This example shows 5 choosers:
  * - swatches	- for choosing among a gallery of swatches
  * - The HSV 	- hue, saturation and value - chooser
  * - The HSL	- hue, saturation and ligthness - chooser	(JDK 1.7)
  * - The RGB	- red, green , blue chooser 
  * - the CMYK	- cyan, magenta, yellow, k four colors chooser (JDK 1.7)
  * 
  * Use the addChooserPanel to add custom choosers
  * Use the removeChooserPanel to remove choosers.
  */
 
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
 
/**
 * 
 * @author cotpa01
 *
 */
public class C9ColorChooser extends JFrame
                              implements ChangeListener {
	/**
	 * 	Data
	 */
	private static final long serialVersionUID = 1L;
	private static C9ColorChooser c9cc = null;	// reference to this instance
	
	protected JPanel lPanel;					// left panel shows the list of objects
	
	protected JRadioButton  radio_Screen = new JRadioButton("Screen");
	protected JRadioButton	radio_CButton = new JRadioButton("Cancel");
	protected JRadioButton  radio_NButton = new JRadioButton("Numbers");
	protected JRadioButton  radio_OButton = new JRadioButton("Operators"); 
	protected JRadioButton	radio_Frame = new JRadioButton("Case");
	protected JLabel		label_current = new JLabel("Current");
	protected JLabel		label_color	  = new JLabel("Color");
	protected JLabel		label_font	  = new JLabel("Font");
	
	protected JPanel rPanel;					// right panel shows the color chooser		
	protected Color newColor = null;			// create a Color object
    protected JColorChooser tcc;				// create a Color chooser

    // Constructor 
    public C9ColorChooser() {
    	
        this.setTitle("ColorChooser");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        int bgGreen = this.getBackground().getGreen();
        int bgRed	= this.getBackground().getRed();
        int bgBlue	= this.getBackground().getBlue();
 
        lPanel = new JPanel();        
        //lPanel.setBorder(BorderFactory.createTitledBorder("Components"));
        int eb = 5;
        lPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(eb, eb, eb, eb), // outer border
            BorderFactory.createTitledBorder("Components")));
        
        //    BorderFactory.createLoweredBevelBorder()));      // inner border
        lPanel.add(radio_Frame);
        radio_Frame.setSelected(true);
        lPanel.add(radio_Screen);
        lPanel.add(radio_CButton);
        lPanel.add(radio_NButton);
        lPanel.add(radio_OButton);
      
        rPanel = new JPanel();
        tcc = new JColorChooser(Color.BLUE);     
        // System.out.println("tcc"+tcc);
        AbstractColorChooserPanel[] oldPanels = tcc.getChooserPanels();
        
        // Remove panels but RGB from the Java utility
        for (int i=0; i<oldPanels.length; i++) {
        	String clsName = oldPanels[i].getClass().getName();
        	//System.out.println("Panelname "+oldPanels[i].getDisplayName());
  
        	if (clsName.equals("javax.swing.colorchooser.DefaultSwatchChooserPanel")) {
             // Remove swatch chooser if desired
             tcc.removeChooserPanel(oldPanels[i]);
        	}
        	/* 
        	else if (oldPanels[i].getDisplayName().equals("RGB")) {
             // Remove rgb chooser if desired
             tcc.removeChooserPanel(oldPanels[i]);
        	} 
        	*/ 
        	else if (oldPanels[i].getDisplayName().equals("TSV") |
        			oldPanels[i].getDisplayName().equals("HSV")) {
             // Remove tsv = hsb chooser if desired
             tcc.removeChooserPanel(oldPanels[i]);
        	}
        	else if (oldPanels[i].getDisplayName().equals("TSL") |
        			oldPanels[i].getDisplayName().equals("HSL")) {
        	 // Remove tsl = hsl panel
        	tcc.removeChooserPanel(oldPanels[i]);
        	}
        	else if (oldPanels[i].getDisplayName().equals("CMYK"))  {
           	 // Remove CMYK panel
           	tcc.removeChooserPanel(oldPanels[i]);
           	}
        }
      
        tcc.getSelectionModel().addChangeListener(this);
        tcc.setBorder(BorderFactory.createTitledBorder(
                                             "Choose Object Color"));
        
        tcc.setPreviewPanel(new JPanel());		/* hide preview panel */
        //tcc.setPreviewPanel(null);			   show preview panel        
 
        //add(bannerPanel, BorderLayout.CENTER);
        
        
        rPanel.add(tcc, BorderLayout.PAGE_END);
        this.add(rPanel, BorderLayout.EAST);
        this.add(lPanel, BorderLayout.WEST);
       
        //add(tcc, BorderLayout.PAGE_END);
        
    }
 
    
    public void stateChanged(ChangeEvent e) {
    	String lfname = UIManager.getLookAndFeel().getName();
        newColor = tcc.getColor();
        //UIManager.put("nimbusbase", newColor);
        UIManager.put("control", newColor);
        // nimbusBlueGrey applies to the buttons and Colorchooser square.
        UIManager.put("nimbusBlueGrey", newColor);
        //System.out.println("new color: "+newColor);
    }
   
    
   /**
    * <b>main	Entry point for this color chooser</b>
    * <ul></ul>
    * @param args
    */
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	if (c9cc == null) {
            		c9cc = new C9ColorChooser();	
            	}   
                c9cc.pack();
                c9cc.setVisible(true);
            }
        });
    }
    
    /**
     * <b>C9ColorChooserRun		Runnable method</b>
     * <ul></ul>
     * Call the color chooser from the contextual menu
     * <ul></ul>
     */
    public class C9ColorChooserRun implements Runnable {
     	 
    	// Default contstructor
        public C9ColorChooserRun(){ }
      	  
    	public void run() {
    		    String[] args = {"",""};
    	        main(args);
    	  }                           
     } // End of ColorChooserRun class   
    
  
} // End of class ColorChooser 


