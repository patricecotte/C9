package com.sdz.calculator9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
// These are in the rt.jar 

public class C9Skinner extends JFrame
					implements ChangeListener {

	private static final long serialVersionUID = 1L;
	private static C9Skinner c9sk = null;		// reference to this instance
	private static C9AbstractController c9cntl = null;
	
	private JPanel contentPane;
	
	private JPanel lPanel;						// Left panel
	private JRadioButton rdbtn_Case;
	private JRadioButton rdbtn_Numbers;
	private JRadioButton rdbtn_Lf;
	private JRadioButton rdbtn_Operators;
	private JRadioButton rdbtn_Reset;
	private JRadioButton rdbtn_Screen;
	private JComboBox<String> cmbBox_Lf;
	private JComboBox<String> cmbBox_theme;
	private JLabel lbl_Color;
	public  static JTextField txtSample;		//  public static so that Fontchooser can change it
	private JButton btnApply;
	private JButton btnReset;
	private JButton btnBrowse;
	private JButton btnClear;
	private IPanel iPanel;						// Insert panel for lbl_Color
	private int rdbtn_Lf_true = 1;
	private int rdbtn_Case_true = 1;
	private int rdbtn_Numbers_true = 1;
	private int rdbtn_Operators_true = 1;
	private int rdbtn_Reset_true = 1;
	private int rdbtn_Screen_true = 1;

	private String currLf, newLf;						// work fields
	private String currTheme, newTheme;			
	private Color currCaseColor, newCaseColor; 
	private String currCaseImage, newCaseImage;			// Path+file name strings
	// Image FILES
	private File  currCaseFileImage;					// current Image file
	private File  newCaseFileImage;						// new Image file
	private File  tmpImage;								// temp area
	// Images DIRECTORY FILES
    private File  currDir;								// Current directory for file chooser
    private File  newDir;								// New directory for file chooser
    private File  tmpDir;								// temp area
	private Color currNumColor, newNumColor;
	private Color currOpeColor, newOpeColor;
	private Color currResetColor, newResetColor;
	private Color currScreenColor, newScreenColor;
	private Color tmpColor;
	private Font  currScreenFont;
	private static Font newScreenFont;
	private boolean ApplyStatus = false;
	private static C9Default newc9d = null;	// New setting objects
	private static C9Frame newc9f = null;
	private static C9Cbutton newc9c = null;
	private static C9Nbutton newc9n = null;
	private static C9Obutton newc9o = null;
	private static C9Screen newc9s = null;
	
	private static C9Frame currc9f = null; 
	private static C9Default currc9d = null;
	private static C9Cbutton currc9c = null;
	private static C9Obutton currc9o = null;
	private static C9Nbutton currc9n = null;
	private static C9Screen  currc9s = null;
	
	private JPanel rPanel;						// Right panel
    private JColorChooser tcc;					// Create a Color chooser
    private JFileChooser filechooser;			// Create a File chooser

    private C9FontChooser c9fcc = null;			// Create an extended Font chooser
    private Image image;		     		    // Background decoration
    private Graphics g;				         	// Graphics interface
   
    private MouseListener radiolistener = new RadioListener();
    private MouseListener buttonlistener = new ButtonListener();
    private MouseListener iPanlistener = new IPanListener();
    private MouseListener txtboxlistener = new TxtBoxListener();
    
    
 
	/**
	 *<b>main	Entry point</b>
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (c9sk == null){
						C9Skinner c9sk = new C9Skinner();
						c9sk.setVisible(true);
						c9sk.setResizable(false);
						c9sk.setTitle("Skinner");
						c9sk.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				 
				try {
					if (c9cntl == null) {
						c9cntl = C9View.getC9cntl();
					}
				} finally {}
				 
			}
		});
	}

	/**
	 * <b>C9Skinner		Constructor for the C9Skinner color chooser</b>
	 */
	public C9Skinner() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLPanel(),BorderLayout.WEST);
		contentPane.add(getRPanel(),BorderLayout.EAST);
	}
	private JPanel getLPanel() {
		if (lPanel == null) {
			lPanel = new JPanel();
			lPanel.setBounds(0, 0, 280, 299);
			lPanel.setLayout(null);
			lPanel.add(getRdbtn_Lf());
			lPanel.add(getRdbtn_Case());
			lPanel.add(getCmbBox_Lf());		
			lPanel.add(getCmbBox_theme());
			lPanel.add(getRdbtn_Numbers());
			lPanel.add(getRdbtn_Operators());
			lPanel.add(getRdbtn_Reset());
			lPanel.add(getRdbtn_Screen());
		    lPanel.add(getIPanel());
			lPanel.add(getTxtSample());
			lPanel.add(getBtnApply());
			lPanel.add(getBtnReset());
		//	lPanel.add(getBtnBrowse());				// Don't set Image handling buttons
		//	lPanel.add(getBtnClear());				// Don't set Image handling buttons
			int eb = 5;
	        lPanel.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createEmptyBorder(eb, eb, eb, eb), // outer border
	            BorderFactory.createTitledBorder("Components")));
		}
		return lPanel;
	}
	private JRadioButton getRdbtn_Lf() {
		if (rdbtn_Lf == null) {
			rdbtn_Lf = new JRadioButton("Look and Feel");
			rdbtn_Lf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			rdbtn_Lf.setBounds(8, 30, 109, 23);
			rdbtn_Lf.addMouseListener(radiolistener);
			rdbtn_Lf.setSelected(true);
			currc9d = C9View.getC9deflt_prop();
		}
		return rdbtn_Lf;
	}
	private JRadioButton getRdbtn_Case() {
		if (rdbtn_Case == null) {
			rdbtn_Case = new JRadioButton("Case");
			rdbtn_Case.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			rdbtn_Case.setBounds(8, 73, 109, 23);
			rdbtn_Case.addMouseListener(radiolistener);
			currc9f = C9View.getC9view_prop();
			currCaseColor = currc9f.framecolor;
			currCaseImage = currc9f.frametexture;
			/*
			 * ---------------------- image handling is incorrect; commented it
			currCaseFileImage = new File(currCaseImage);
			try {
			currDir = new File(currCaseImage.substring(0,currCaseImage.lastIndexOf(File.separator)));
				} catch (Exception direx){}
				*/ 
		}
		return rdbtn_Case;
	}
	private JRadioButton getRdbtn_Numbers() {
		if (rdbtn_Numbers == null) {
			rdbtn_Numbers = new JRadioButton("Numbers");
			rdbtn_Numbers.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			rdbtn_Numbers.setBounds(8, 116, 109, 23);
			rdbtn_Numbers.addMouseListener(radiolistener);
			currc9n = C9View.getC9nbut_prop();
			currNumColor = currc9n.bg;
		}
		return rdbtn_Numbers;
	}
	private JRadioButton getRdbtn_Operators() {
		if (rdbtn_Operators == null) {
			rdbtn_Operators = new JRadioButton("Operators");
			rdbtn_Operators.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			rdbtn_Operators.setBounds(8, 159, 109, 23);
			rdbtn_Operators.addMouseListener(radiolistener);
			currc9o = C9View.getC9obut_prop();
			currOpeColor = currc9o.bg;
		}
		return rdbtn_Operators;
	}
	private JRadioButton getRdbtn_Reset() {
		if (rdbtn_Reset == null) {
			rdbtn_Reset = new JRadioButton("Reset");
			rdbtn_Reset.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			rdbtn_Reset.setBounds(8, 202, 109, 23);
			rdbtn_Reset.addMouseListener(radiolistener);
			currc9c = C9View.getC9cbut_prop();
			currResetColor = currc9c.bg;
		}
		return rdbtn_Reset;
	}
	private JRadioButton getRdbtn_Screen() {
		if (rdbtn_Screen == null) {
			rdbtn_Screen = new JRadioButton("Screen");
			rdbtn_Screen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			rdbtn_Screen.setBounds(8, 250, 109, 23);
			rdbtn_Screen.addMouseListener(radiolistener);
			currc9s = C9View.getC9scrn_prop();
			currScreenColor = currc9s.bg;
			currScreenFont = currc9s.font;
		}
		return rdbtn_Screen;
	}
	private JComboBox getCmbBox_Lf() {
		if (cmbBox_Lf == null) {
			//cmbBox_Lf = new JComboBox<String>(); 
			currLf = UIManager.getLookAndFeel().getName();
			cmbBox_Lf = new JComboBox<String>(); 
			cmbBox_Lf.addItem(currLf);
			// Build an Array of the LookAndFeels available in this implementation
			try {
			    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			    	if (!currLf.equals(info.getName()))
			    			cmbBox_Lf.addItem(info.getName()); 	
			    } 
		 	} catch (Exception e){		// reset when LookAndFeel is not found.
	             C9View.showMessage("Could notlist the LookAndFeels in this system."); 
		 	}
				
			cmbBox_Lf.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			if (rdbtn_Lf.isSelected())		
				cmbBox_Lf.setEnabled(true);
			else cmbBox_Lf.setEnabled(false);
			cmbBox_Lf.setEditable(false);
			cmbBox_Lf.setBounds(116, 31, 77, 20);
			cmbBox_Lf.addItemListener(new ComboItemChanged());
			cmbBox_Lf.setName("lf");
		}
		return cmbBox_Lf;
	}
	private JComboBox getCmbBox_theme() {
		if (cmbBox_theme == null) {
			cmbBox_theme = new JComboBox<String>();
			if (currTheme == null) currTheme = "DefaultMetal";		
			cmbBox_theme.addItem(currTheme);
			
			if (!currTheme.equals("DefaultMetal")) 
				cmbBox_theme.addItem("DefaultMetal");
			if (!currTheme.equals("Ocean"))
					cmbBox_theme.addItem("Ocean");
			if (!currTheme.equals("Test"))
				cmbBox_theme.addItem("Test");
			cmbBox_theme.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			cmbBox_theme.setEditable(false);
			
			if (cmbBox_Lf.isEnabled() && cmbBox_Lf.getSelectedItem().equals("Metal"))	
				cmbBox_theme.setEnabled(true);
			else cmbBox_theme.setEnabled(false);
			cmbBox_theme.setBounds(194, 31, 77, 20);
			cmbBox_theme.addItemListener(new ComboItemChanged());
			cmbBox_theme.setName("theme");
		}
		return cmbBox_theme;
	}
	private JLabel getLbl_Color() {
		if (lbl_Color == null) {
			lbl_Color = new JLabel("");
			lbl_Color.setBounds(8, 16, 150, 82);
			lbl_Color.setBackground(Color.WHITE);
		}
		return lbl_Color;
	}
	private JTextField getTxtSample() {
		if (txtSample == null) {
			txtSample = new JTextField();
			txtSample.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			txtSample.setHorizontalAlignment(SwingConstants.CENTER);
			txtSample.setText("Sample");
			txtSample.setBounds(121, 189, 140, 35);
			txtSample.setColumns(10);
			txtSample.setEditable(false);
			txtSample.addMouseListener(txtboxlistener);
		}
		return txtSample;
	}
	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton("Apply");
			btnApply.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			btnApply.setBounds(121, 242, 70, 40);
			btnApply.addMouseListener(buttonlistener);
		}
		return btnApply;
	}
	private JButton getBtnReset() {
		if (btnReset == null) {
			btnReset = new JButton("Reset");
			btnReset.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			btnReset.setBounds(195, 242, 70, 40);
			btnReset.addMouseListener(buttonlistener);
		}
		return btnReset;
	}
	
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton("Browse");
			btnBrowse.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			btnBrowse.setBounds(121, 75, 70, 40);
			btnBrowse.setEnabled(false);
			btnBrowse.addMouseListener(buttonlistener);
		}
		return btnBrowse;
	}
	
	private JButton getBtnClear() {
		if (btnClear == null) {
			btnClear = new JButton("Clear");
			btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			btnClear.setBounds(195, 75, 70, 40);
			btnClear.setEnabled(false);
			btnClear.addMouseListener(buttonlistener);
		}
		return btnClear;
	}
	
	/**
	 * <b>getIPanel 	This methods inserts a small panel in the Left panel</b>
	 * <ul></ul> It changes dynamically when user selects a color for any component or 
	 * user sets a background image. 
	 */
	private IPanel getIPanel() {
		if (iPanel == null) {
			iPanel = new IPanel(null);		// create panel without image
			iPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
			iPanel.setBounds(121, 121, 140, 56);
			iPanel.setLayout(null);
			iPanel.add(getLbl_Color());		
			iPanel.setBackground(Color.WHITE);
			iPanel.addMouseListener(iPanlistener);
		}
		return iPanel;
	}
	
	private JPanel getRPanel() {
		if (rPanel == null) {
			rPanel  = new JPanel ();
			rPanel.setBounds(300, 0, 650, 299);
			rPanel.add(getTcc(),BorderLayout.WEST);
		}
		return rPanel;
	}
	private JColorChooser getTcc() {
		if (tcc  == null) {	
			// Implement java's color chooser 
			tcc = new JColorChooser(Color.BLUE);   	
			AbstractColorChooserPanel[] oldPanels = tcc.getChooserPanels();
	        
	        // Remove panels but RGB from the Java utility
	        for (int i=0; i<oldPanels.length; i++) {
	        	String clsName = oldPanels[i].getClass().getName();
	  
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
	       
		}
		return tcc ;
	}
	
	/**
	 * <b>setTcc	Thsi method changes the color in the color chooser on clicking the
	 * sample panel</b>
	 * <ul></ul> 
	 * @param col
	 */
	public void setTcc(Color col){
		tcc.setColor(col);
	}
	
	/**
	 * <b>stateChanged	ChangeListener implementation </b>
	 * @param e
	 */
    public void stateChanged(ChangeEvent e) {

        if (rdbtn_Case.isSelected()) newCaseColor = tcc.getColor(); 
        if (rdbtn_Numbers.isSelected()) newNumColor = tcc.getColor();
        if (rdbtn_Operators.isSelected()) newOpeColor = tcc.getColor();
        if (rdbtn_Reset.isSelected()) newResetColor = tcc.getColor();
        if (rdbtn_Screen.isSelected()) newScreenColor = tcc.getColor();
        iPanel.setBackground(tcc.getColor());
    }
   
    
    /*
     * IPanel class extends JPanel by including a graphic method in order to draw
     * a background
     */
    public class IPanel extends JPanel{
    	private static final long serialVersionUID = 1L;
    	private Image image = null;
    	private int iWidth;
    	private int iHeight;
    	
    	public IPanel(Image image)
    	{
    	    this.image = image;
    	    this.iWidth = this.getWidth();
    	    this.iHeight = this.getHeight();
    	}
    	
    	public void paintComponent(Graphics g)
    	{
    	    super.paintComponent(g);
    	    if (image != null)
    	    {
    	        g.drawImage(image,0,0,iWidth, iHeight,this);
    	    }
    	}

    }
    
    /*
     * L I S T E N E R S - for the various objects in C9Skinner frame
     */
    /**
     * <b>radioListener	MouseListener for the radio buttons</b>
     * <ul></ul>
     */
    public class RadioListener extends MouseAdapter{
    	// Each radio button sets the other buttons off.
 
	    public void mouseReleased(MouseEvent event){   
	    	
	    	String str = ((JRadioButton) event.getSource()).getText();
	    	//System.out.println("Event mouseReleased on"+str);
	    	switch (str){
	 			case "Look and Feel" : 
	 				toggleRadioButtons(rdbtn_Lf_true,0,0,0,0,0);
	 				break;
	    		case "Case" :
					toggleRadioButtons(0,rdbtn_Case_true,0,0,0,0);
	    			break;
	    		case "Numbers" :
					toggleRadioButtons(0,0,rdbtn_Numbers_true,0,0,0);
	    			break;
	    		case "Operators" :
					toggleRadioButtons(0,0,0,rdbtn_Operators_true,0,0);
	    			break;
	    		case "Reset" :
					toggleRadioButtons(0,0,0,0,rdbtn_Reset_true,0);
	    			break;	
	    		case "Screen" :
	    			toggleRadioButtons(0,0,0,0,0,rdbtn_Screen_true);
	    		default :    				
	    	}
	    	if (rdbtn_Lf.isSelected()){
	    		cmbBox_Lf.setEnabled(true);
				cmbBox_theme.setEnabled(true);
	    	}
	    	else {
	    		cmbBox_Lf.setEnabled(false);
				cmbBox_theme.setEnabled(false);
	    	}
	    }
	    
	    /*
	     * The Clicked event is processed after MouseReleased.
	     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	     */
	  	public void mouseClicked(MouseEvent event){
	    }
    		
    } // End of class MouseListener
    
    /**
     * <b>ButtonListener	applies or resets the changes to the appearance </b>
     * <ul></ul>
     */
    public class ButtonListener extends MouseAdapter {
	    public void mouseReleased(MouseEvent mr){   
	    	
	    	String str = ((JButton) mr.getSource()).getText();
    		//newc9d = C9View.getC9deflt_prop(); 
	    	//System.out.print("Buttonlistener "+str+" newLf "+newLf+" newTheme "+newTheme);
	    	switch (str) {
	    	case "Browse" :
	    		showOpenFileDialog();
	    		break;
	    	case "Clear" :
	    		iPanel.image = null;
	    		newCaseImage = null; currCaseImage = null; 
	    		tmpImage = null;
	    		iPanel.repaint();
	    		break;
	    	case "Apply" :		// Pass changes to the Model, change local L&F
	    		ApplyStatus = true; 
	    		newc9d = currc9d;					// copy current
	    		if (newLf != null)
	    			newc9d.lookandfeel = newLf;		//  and override
	    		if (newTheme != null)
	    			newc9d.theme = newTheme;
	    
	    		// Apply to skinner
	    		BtnListenerApplyLf(newLf, newTheme);
	    		
	    		// Apply ALL changes to colors
	    		// Case manages color and image
	    		if (newCaseColor != null){
	    			if (newc9f == null) newc9f = currc9f;
	    			if (newCaseColor != null)
	    				newc9f.framecolor = newCaseColor;
	    			
	    		}
	    		if (newCaseColor != null ) {
	    			if (newc9f == null) newc9f = currc9f;
	    			newc9f.framecolor = newCaseColor;
	    			C9View.setC9view_prop(newc9f);
	    		}
	    		/*
	    		 *  ------------------------------------------------------------
	    		
	    		if (newCaseColor != null || newCaseImage != null) {
	    			if (newc9f == null) newc9f = currc9f;
	    			if (newCaseColor != null) 
	    				newc9f.framecolor = newCaseColor;
	    			 
	    			if (newCaseImage != null){
	    				newc9f.frametexture = newCaseImage;
	    				tmpImage = newCaseFileImage;	// Set Image for iPanel
	    			}
	    			  
	    			C9View.setC9view_prop(newc9f);
	    		}
	    		* ---------------------------------------------------------------
	    		*/
	    		
	    		// If user cleared an existing iamge or there ie none
	    		/*
	    		 * -----------------------------------------------------------
	    		if ((newCaseImage == null) && (currCaseImage == null)){ 
	    			newc9f.frametexture = null;
	    			C9View.setC9view_prop(newc9f);
	    		}
	    		* -----------------------------------------------------------
	    		*/
	    		
	    		if (newNumColor != null) {
	    			newc9n = currc9n;			 
	    			newc9n.bg = newNumColor;	
	    		}
	    		if (newOpeColor != null) {
	    			newc9o = currc9o;
	    			newc9o.bg = newOpeColor;
	    		}
	    		if (newResetColor != null) {
	    			newc9c = currc9c;
	    			newc9c.bg = newResetColor;
	    		}
	    		// Screen is special in that the font may have been changed also
	    		if (newScreenColor != null || newScreenFont != null) {
	    			newc9s = currc9s;
	    			if (newScreenColor != null) 
	    				newc9s.bg = newScreenColor;
	    			if (newScreenFont != null)
	    				newc9s.font = newScreenFont;
	    		}
	    		// Recolor the sample Panel according to the active radio button. 
    	        if (rdbtn_Lf.isSelected()) 
    	        	tmpColor = Color.white;			// No color if Lf, Theme
    	    	if (rdbtn_Case.isSelected())
    	    		tmpColor = newCaseColor;
    	    	if (rdbtn_Numbers.isSelected())
    	    		tmpColor = newNumColor;
    	    	if (rdbtn_Operators.isSelected())
    	    		tmpColor = newOpeColor;
    	    	if (rdbtn_Reset.isSelected())
    	    		tmpColor = newResetColor;
    	    	if (rdbtn_Screen.isSelected())
    	    		tmpColor = newScreenColor;   	
    	    	
	    		break;	
	    	case "Reset" :		// Pass changes to the Model, change local L&F
	    		ApplyStatus = false;
	    		if (newLf != null){ 
	    			newc9d.lookandfeel = currLf;
	    			if (newTheme != null)
	    				newc9d.theme = currTheme;
	    			c9cntl.c9cntl_Skin_change(str);
	    			BtnListenerApplyLf(currLf, currTheme);
	    		}
	    		
	    		// Reset ALL color changes to their initial values.
	    		// Case manage both a color and an image
	    		if (newCaseColor != null) {
	    			if (newCaseColor != null)
	    				newc9f.framecolor = currCaseColor;
	    			C9View.setC9view_prop(newc9f);
	    		/* 
	    		 * -----------------------------------------------------------------
	    		if (newCaseColor != null || newCaseImage != null) {
	    			if (newCaseColor != null)
	    				newc9f.framecolor = currCaseColor;
	    			if (newCaseImage != null){
	    				// We don't revert the directory, the assumption is that user
	    				// may want to go pick up another image from the same place
	    				newc9f.frametexture = currCaseImage;
	    				tmpImage = currCaseFileImage;
	    			}
	    			C9View.setC9view_prop(newc9f);
	    		* ---------------------------------------------------------------------
	      		*/
	    		}
	    		if (newNumColor != null) {
	    			newc9n.bg = currNumColor;
	    			C9View.setC9nbut_prop(newc9n);
	    		}
	    		if (newOpeColor != null) {
	    			newc9o.bg = currOpeColor;
	    			C9View.setC9obut_prop(newc9o);
	    		}
	    		if (newResetColor != null) {
	    			newc9c.bg = currResetColor;
	    			C9View.setC9cbut_prop(newc9c);
	    		}
	    		// Screen is special in that we manage both the color and font
	    		if (newScreenColor != null || newScreenFont != null) {
	    			newc9s.bg = currScreenColor;
	    			newc9s.font = currScreenFont;
	    			C9View.setC9screen_prop(newc9s);
	    		}
	    		
	    		// Recolor the sample Panel according to the active radio button. 
	    	        if (rdbtn_Lf.isSelected()) 
	    	        	tmpColor = Color.white;			// No color if Lf, Theme
	    	    	if (rdbtn_Case.isSelected()){ 
	    	    		if (currCaseImage.equals(""))
	    	    			iPanel.image = null;
	    	    		tmpColor = currCaseColor;
	    	    	}
	    	    	if (rdbtn_Numbers.isSelected())
	    	    		tmpColor = currNumColor;
	    	    	if (rdbtn_Operators.isSelected())
	    	    		tmpColor = currOpeColor;
	    	    	if (rdbtn_Reset.isSelected())
	    	    		tmpColor = currResetColor;
	    	    	if (rdbtn_Screen.isSelected())
	    	    		tmpColor = currScreenColor;
	    		
	    		break;
	    	}
	    	// propagate to main application, recolor the sample panel
	    	
	    	if ((str != "Browse") && (str != "Clear")) {  // If Browse or clear we don't need to apply changes now
				c9cntl.c9cntl_Skin_change(str);
				if (tmpColor != null)
					iPanel.setBackground(tmpColor);
				/*
				 * -------------------------------------------------
				if (tmpImage != null){
					try{
					iPanel.image = ImageIO.read(tmpImage);
			        iPanel.iWidth = iPanel.getWidth();
		            iPanel.iHeight = iPanel.getHeight();
		            iPanel.paintComponent(iPanel.getGraphics());
					} catch (IOException ex){
						System.out.println("Error applying the image file");
					}
				}
				* --------------------------------------------------
				*/ 
				if (newScreenFont != null){
					txtSample.setFont(new Font(newc9s.font.getName(),newc9s.font.getStyle(),18));
				}
	    	}
	    }
	    
	    /**
	     * <b> BtlListenerApplyLf	applies the L&F change locally for consistency with 
	     * the main display and messages screens</b>
	     */
	    public void BtnListenerApplyLf(String l, String t) {
			//Try to apply the proposed LookAndFeel if we have one. For Metal we set the
			//them then the LookAndFeel
	    	String lf = l, theme = t;
	   
			if (lf != null){
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {			    	// System.out.println("L&F: "+info.getName());
					       if (lf.equals(info.getName())) {
					    	   // For Metal LookAndFeel
					    	   if (lf.equals("Metal")) {
					    		   if (theme.equals("DefaultMetal"))
						                  MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
						               else if (theme.equals("Ocean"))
						                  MetalLookAndFeel.setCurrentTheme(new OceanTheme());
						               else
						                 MetalLookAndFeel.setCurrentTheme(new C9TestTheme());
					    	   }
					    	   // Try to set the LookAndFeel
					    	   UIManager.setLookAndFeel(info.getClassName());
				               //SwingUtilities.updateComponentTreeUI(getC9sk());
				            break;
				        }
				    } 
			 	} 
		         catch (ClassNotFoundException e) {
		        	 System.out.println("Class not found Exception in Skinner setting Lf "+lf+", theme"+theme);
		         } 
		         
		         catch (UnsupportedLookAndFeelException e) {
		        	 System.out.println("Unsupported L&F Exception in Skinner setting Lf "+lf+", theme"+theme);
		         }
		         
		         catch (InstantiationException e) {
		        	 System.out.println("Instantiation error in Skinner setting Lf "+lf+", theme"+theme);
		         }
		         catch (IllegalAccessException e) {
		        	 System.out.println("Illegal access in Skinner setting Lf "+lf+", theme"+theme);
		         }
				catch (Exception e){		// LookAndFeel could not be changed.
							System.out.println("Exception in Skinner setting Lf "+lf+", theme"+theme);
			 	}
			}
	    }
    } // End of ButtonListener
    
    /**
     * <b>IPanListener	implements a listener on the Sample panel in order to match the
     * color chooser with the color of the Sample panel 
     */
    public class IPanListener extends MouseAdapter {

		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			setTcc(iPanel.getBackground());
		}
    	
    }
    
    /**
     * <b>TxtBoxListener	implements a listener on the text so that user can change the font</b>
     * <ul></ul>
     */
    public class TxtBoxListener extends MouseAdapter {

		public void mouseReleased(MouseEvent mt) {
			super.mouseReleased(mt);
			if (c9fcc == null){  // Ensure we open an occurrence only
				C9FontChooser c9fcc = new C9FontChooser();	// My instance		
				Thread t2 = new Thread(c9fcc.new C9FontChooserRun());
				t2.start();
			}
		}
    	
    }
    
    /**
     * </b>ComboItemChanged	change items for Combo boxes </b>
     * <ul></ul>
     * Handle the pair of combo boxes. When LF = metal the second box is enabled. 
     * The output is the newLf and newTheme set. 
     */
    public class ComboItemChanged implements ItemListener{
		public void itemStateChanged(ItemEvent ie) {
     		String str = ((JComboBox) ie.getSource()).getName();
      		String entry =((JComboBox) ie.getSource()).getSelectedItem().toString();
	    	//System.out.println("Item changed on Combo "+str+" entry "+entry);
	    	// Lf change 
	    	if (str.equals("lf")){
	    		if (entry.equals("Metal")) 	{
	    			cmbBox_theme.setEnabled(true);
	    			if (newTheme == null) newTheme = "DefaultMetal";
	    		}
	    			else cmbBox_theme.setEnabled(false);
	    		newLf = entry; 
	    	}
	    	// Theme change
	    	else newTheme = entry;
		}
    	
    }
    
    /**
     * <b>toggleRadioButtons change the state of the Radio buttons as requested by the input</b>
     * <ul></ul>
     * On clicking a radio button set the iPanel color to the color of the selected object
     * - if ApplyStatus is on, set it to the new color if it exists (user has set one in the color chooser)
     * or the current color (user has not chosen any new color for this object yet)
     * - clicking the Reset button
     * @param s1 int 0|1
     * @param s2 int 0|1
     * @param s3 int 0|1
     * @param s4 int 0|1
     * @param s5 int 0|1
     * @param s6 int 0|1
     */
    public void toggleRadioButtons(int s1, int s2, int s3, int s4, int s5, int s6){
    	rdbtn_Lf.setSelected(intToBoolean(s1));
    	rdbtn_Case.setSelected(intToBoolean(s2));
    	rdbtn_Numbers.setSelected(intToBoolean(s3));
    	rdbtn_Operators.setSelected(intToBoolean(s4));
    	rdbtn_Reset.setSelected(intToBoolean(s5));
    	rdbtn_Screen.setSelected(intToBoolean(s6));
    	tmpColor = null;					//  Clear work object
    	  	
    	if (rdbtn_Case.isSelected()) {
    		if (ApplyStatus) {
    			if (newCaseColor != null) tmpColor = newCaseColor;
    				else tmpColor = currCaseColor;
    			// User has applied changes, set a new image if one
    			/*
    			 * ---------------------------------------------------
    			if (newCaseImage != null){
    				try{
    					iPanel.image = ImageIO.read(newCaseFileImage);
    		          	iPanel.iWidth = iPanel.getWidth();
    	            	iPanel.iHeight = iPanel.getHeight();
    	                iPanel.paintComponent(iPanel.getGraphics());
    					} catch (IOException ex){
    						System.out.println("Error applying the new image file");
    					}
    			}
    			* ----------------------------------------------------
    			*/ 
    			// User has applied changes that do not include an image, apply the old
    			// image if one exists.
    			/*
    			 * ---------------------------------------------------			  
    			else if (currCaseImage != null){
    				try{
    					iPanel.image = ImageIO.read(currCaseFileImage);
    		          	iPanel.iWidth = iPanel.getWidth();
    	            	iPanel.iHeight = iPanel.getHeight();
    	                iPanel.paintComponent(iPanel.getGraphics());
    					} catch (IOException ex){
    						System.out.println("Error applying the old image file");
    					}
    			}
    			* ------------------------------------------------------
    			*/
    		}		
    		// Apply Status not on. (Re-)apply the current status when they exist
    		
    		else {
    			tmpColor = currCaseColor;
    			/*
        		 * --------------------------------------------------------
    			if (currCaseImage != null){
    				try{
    					iPanel.image = ImageIO.read(currCaseFileImage);
      		          	iPanel.iWidth = iPanel.getWidth();
    	            	iPanel.iHeight = iPanel.getHeight();
    	                iPanel.paintComponent(iPanel.getGraphics());
    					} catch (IOException ex){
    						System.out.println("Error applying the old image file");
    					}
    			}
    			*/
    		}
    		/*
    		btnBrowse.setEnabled(true);
    		btnClear.setEnabled(true);
    		* ----------------------------------------------------------
    		*/
    	}
    	/*
    	 * -------------------------------------------------------------
    	else {
    		btnBrowse.setEnabled(false);
    		btnClear.setEnabled(true);
    	}
    	* --------------------------------------------------------------
    	*/
    	
    	if (rdbtn_Numbers.isSelected()) {
    		if (ApplyStatus) {
    			if (newNumColor != null) tmpColor = newNumColor;
    			else tmpColor = currNumColor;
    		}
    		else tmpColor = currNumColor;
    		iPanel.image = null;		// clear image
    	}
    	
    	if (rdbtn_Operators.isSelected()) {
    		if (ApplyStatus){
    			if(newOpeColor != null) tmpColor = newOpeColor;
    			else tmpColor = currOpeColor;
    		}
    		else tmpColor = currOpeColor;
    	}
    	
    	if (rdbtn_Reset.isSelected()) {
    		if (ApplyStatus) {
    			if (newResetColor != null) tmpColor = newResetColor;
    			else tmpColor = currResetColor;
    		}
    		else tmpColor = currResetColor;  
       		iPanel.image = null;		// clear image
    	}
    	
    	if (rdbtn_Screen.isSelected()) {
    		if (ApplyStatus) {
    			if(newScreenColor != null) tmpColor = newScreenColor;
    			else tmpColor = currScreenColor;
    		}
    		else tmpColor = currScreenColor;		
       		iPanel.image = null;		// clear image
    	}
    	if (tmpColor != null)		// Just in case! 
    		iPanel.setBackground(tmpColor);
    	
    }
    
  /**
   * <b>showOpenFileDialog	This dialog walks user through the file system in order to load an image</b>
   * <ul></ul>
   * @return
   */
    
    private void showOpenFileDialog() {
    	
    	filechooser = new JFileChooser();
    	tmpDir = null;
    	
    	if (newDir == null){
    		if (currDir == null) 
    			tmpDir = new File(System.getProperty("user.dir"));
    		else tmpDir = currDir;
    	}
    	else tmpDir = newDir;
    	filechooser.setCurrentDirectory(tmpDir);
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // filechooser.addChoosableFileFilter(new FileNameExtensionFilter("MS Office Documents", "docx", "xlsx", "pptx"));
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
        filechooser.setAcceptAllFileFilterUsed(true);
        int result = filechooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            newCaseFileImage = filechooser.getSelectedFile();		// File image
            newDir = filechooser.getCurrentDirectory();					// Directory File image
            newCaseImage = newCaseFileImage.getAbsolutePath();			// file path (string)
            // imagepath = newCaseImage.substring(0,newCaseImage.lastIndexOf(File.separator));
            if (newc9f == null) newc9f = currc9f;		// create new from old 
            // apply the image to the sample screen.
            try{
            	iPanel.image = ImageIO.read(newCaseFileImage);
            	iPanel.iWidth = iPanel.getWidth();
            	iPanel.iHeight = iPanel.getHeight();
                iPanel.paintComponent(iPanel.getGraphics());
            } catch (IOException ex) {
            	System.out.println("Unable to load the selected image");
            }
 
        }
    }
       
    /*
     *	Getters and Setters for the Property objects 
     */
    public C9Default getC9deflt_prop_new(){
    	return newc9d;
    }
    
    public C9Frame getC9frame_prop_new(){
    	return newc9f;
    }
    
    
    public C9Cbutton getC9cbutton_prop_new() {
		return newc9c;
	}

	public C9Nbutton getC9nbutton_prop_new() {
		return newc9n;
	}

	public C9Obutton getC9obutton_prop_new() {
		return newc9o;
	}

	public C9Screen getC9screen_prop_new() {
		return newc9s;
	}

	public static void setNewc9sFont(Font f){
		newScreenFont = f;						// and overwrite
	}

	/**
     * <b>intToBoolean	converts an integer to booleans</b>
     * <ul></ul>
     * @param i
     * @return
     */
    public boolean intToBoolean(int i){
    	return i > 0 ? true : false;
    }
	   /**
     * <b>C9skinnerRun		Runnable method</b>
     * <ul></ul>
     * Call the color chooser from the contextual menu
     * <ul></ul>
     */
    public class C9SkinnerRun implements Runnable {
     	 
    	// Default contstructor
        public C9SkinnerRun(){ }
      	  
    	public void run() {
    		    String[] args = {"",""};
    	        main(args);
    	  }                           
     } // End of ColorChooserRun class   
    
} // End of C9Skinner class

