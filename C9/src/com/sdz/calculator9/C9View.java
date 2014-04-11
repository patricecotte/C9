package com.sdz.calculator9;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
// These are in the rt.jar 

// Default thme for Nimbus lookandfeel, it only sets the three primary colors 

/**
 * <b>C9View	The View layer of the C9 application
 * <ul></ul>
 * @see #C9View
 * @author cotpa01
 * @version 1.0
 */
public class C9View extends JFrame implements Observer{

	/**
	 * 		Data areas: area for the Controller instance
	 * 					areas for the GUI objects
	 */
	private static final long serialVersionUID = -2493384230920821040L;
	static  C9AbstractController c9cntl;			// Work area for our controller
	private C9View c9view = this;					// A Reference to us
	private HelpSet c9hs = null;					// Object references
	private HelpBroker c9hb = null;					// for the Java help system
	private CSH.DisplayHelpFromSource csh = null;	// Java help instance
	private Graphics g;								// Graphic interface
	private File tmpFileImage;						// background image (FILE)
	private Image tmpImage;							// background image (IMAGE)
	 
	// Objects:	A collection of objects
	// - application icon
	// - JtextField = window
	// - JPopupMenu, JMenuitem
	// - An array of JButtons
	// - Containers
	private ImageIcon img = null;							 
	private JTextField screen  = new JTextField();			 
	private JPopupMenu jpm = new JPopupMenu();
	private JMenuItem about = new JMenuItem("About");      
	private JMenuItem skinme = new JMenuItem("Skin me");
	private JMenuItem help = new JMenuItem("Help me");
	private String[] tab_string = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "=", "C", "+", "-", "*", "/"};
	private JButton[] tab_button = new JButton[tab_string.length];
	
	// Properties:	A collection of objects that hold the graphical properties for our objects

	static C9Default c9deflt_prop   = null;
	static C9Frame   c9view_prop 	= null;
	static C9Cbutton c9cbut_prop 	= null;
	static C9Nbutton c9nbut_prop 	= null;
	static C9Obutton c9obut_prop 	= null;
	static C9Screen  c9scrn_prop 	= null;
	
	// This getter is used by C9skinner
	public static C9AbstractController getC9cntl(){
		return c9cntl;
	}
	
/* --------------------------------------------------------------
   The Getters/Setters are used by C9Skin
   --------------------------------------------------------------- */
	
	public static C9Default getC9deflt_prop() {
		return c9deflt_prop;
	}

	public static void setC9deflt_prop(C9Default c9deflt_prop) {
		C9View.c9deflt_prop = c9deflt_prop;
	}

	public static C9Frame getC9view_prop() {
		return c9view_prop;
	}

	public static void setC9view_prop(C9Frame c9view_prop) {
		C9View.c9view_prop = c9view_prop;
	}

	public static C9Cbutton getC9cbut_prop() {
		return c9cbut_prop;
	}

	public static void setC9cbut_prop(C9Cbutton c9cbut_prop) {
		C9View.c9cbut_prop = c9cbut_prop;
	}

	public static C9Nbutton getC9nbut_prop() {
		return c9nbut_prop;
	}

	public static void setC9nbut_prop(C9Nbutton c9nbut_prop) {
		C9View.c9nbut_prop = c9nbut_prop;
	}

	public static C9Obutton getC9obut_prop() {
		return c9obut_prop;
	}

	public static void setC9obut_prop(C9Obutton c9obut_prop) {
		C9View.c9obut_prop = c9obut_prop;
	}

	public static C9Screen getC9scrn_prop() {
		return c9scrn_prop;
	}

	public static void setC9screen_prop(C9Screen c9scrn_prop) {
		C9View.c9scrn_prop = c9scrn_prop;
	}

	
	// Create containers so that objects can be given a different layout and their
	// GraidBagLayosuts and GridBagConstraints
	Container ctn0 = new Container();	// container for the numbers 
	Container ctn1 = new Container();	// container for the screen object
	Container ctn2 = new Container();	// container for the operators
	GridBagLayout gbl  = new GridBagLayout();
	GridBagLayout gbl0 = new GridBagLayout();
	GridBagLayout gbl1 = new GridBagLayout();
	GridBagLayout gbl2 = new GridBagLayout();
	GridBagConstraints gbc  = new GridBagConstraints();
    GridBagConstraints gbc0 = new GridBagConstraints();
	GridBagConstraints gbc1 = new GridBagConstraints();
    GridBagConstraints gbc2 = new GridBagConstraints();
		
	
	//Work fields
	C9Range range = new C9Range(); 							// set up range 0-9
	C9Range orange= new C9Range("-/*+.=");					// set up range of non numerics							
	private Dimension dim = new Dimension(50, 42);   		// an AWT object 
	private Dimension dim2 = new Dimension(50, 33);
	private Color deftcolor;  					// Work field for the default color 
	private String savetext = "0";              // Work field for the last good text

	
	/**
	 * 	<b>C9View method</b>
	 * 	<p><b>Actions</b></p>
	 *  <li>Save the controller instance
	 * 	<li>Create the GUI
	 * 	<li>Set listeners
	 * 	<li>Display the UI
	 *  <ul></ul>  
	 * 	@param A Controller 
	 */
	public C9View(C9AbstractController c9cntl){
		this.c9cntl = c9cntl;					// Copy the controller object

		C9Inifile c9ini =  new C9Inifile();		// Create an instance of the Ini utility 
		try {
			//C9Inifile.processIni("r",c9view);			// static call 
			c9ini.processIni("r", c9view);
			c9deflt_prop = c9ini.getC9d();				// copy objects set by Readini
			c9view_prop = c9ini.getC9f();
			c9cbut_prop = c9ini.getC9c();
			c9obut_prop = c9ini.getC9o();
			c9nbut_prop = c9ini.getC9n();
			c9scrn_prop = c9ini.getC9s();
			
		} catch (IOException e1) {
			// processIni handles the exceptions
		}
		
		
		// System.out.println("Frame properties : "+c9ini.getC9f());
		// System.out.println("Screen properties: "+c9ini.getC9s());
		
		// Although little useful we plug the Keyboard listener in the JPanel object. 
		// Calculator9  will receive the keys until no other object is clicked and grabs
		// the focus. 
		KeyListener listener = new KeyboardAction();
		addKeyListener(listener);
		MouseListener mouseaction = new MouseAction();
		MouseListener showmore = new ShowMore();
		MouseListener buttonmouselistener = new ButtonMouseListener();
		TextInputVerifier textinputverifier  = new TextInputVerifier();
		WindowListener windowlistener =  new  WindowListener();
		this.addMouseListener(mouseaction);
		this.addWindowListener(windowlistener);
		setFocusable(true);   		

		//Set up and initializes the screen (JTextField) 
		screen = new JTextField("");
		screen .setToolTipText("Numbers");	
		screen .setText("0");
		screen .addKeyListener(listener);						// set up key listener

	    //Loop over creating buttons with their listeners. Note each object is
	    //added to the panel it must show in. Sign '=' and '.' go to numbers.
	    //Each button is set in the GridLayout as follows:
	    // 	container 0 (numbers)
	    //	row 0	-	buttons 1,2,3;	row 1	- buttons	4,5,6
	    //	row 2	- 	buttons 7,8,9;	row 3	- buttons	0,.,=
	    //  container 1 (operators)
	    //  row 1 to 5 all in column 0 : operators 
	    for(int i = 0; i < tab_string.length; i++){
	      tab_button[i] = new JButton(tab_string[i]);
          tab_button[i].addActionListener(new GblListener());
          tab_button[i].addMouseListener(buttonmouselistener);
          tab_button[i].setInputVerifier(textinputverifier);
	    }
    
		// Add menu items to the contextual menu, jpm.
	    // Add jpm to the frame
        jpm.add(about);
        about.addMouseListener(showmore);
        jpm.addSeparator();
        jpm.add(skinme);
        skinme.addMouseListener(showmore);
        jpm.add(help);
        
        /*
         * Set up the java help system
         */
		c9hs = getHelpSet("C9Helpset.hs");			// Create help objects
		if (c9hs != null){
			c9hb = c9hs.createHelpBroker();
			CSH.setHelpIDString(help, "top");		// relate the help menu item to CSH
			help.addActionListener(new CSH.DisplayHelpFromSource(c9hb));
			help.addMouseListener(showmore);
		};
       
        this.add(jpm);
               
        // apply a skin on the objects. 
        initSkin();
        
        // Add a Component listener to container 0
        //ctn0.addComponentListener(new CmpListener());
        c9view.addComponentListener(new CmpListener());
        
		// set the application's icon or default to the java icon instead.
		try {
			img = new ImageIcon("images/C9Icon.png");
			this.setIconImage(img.getImage());
		} catch (Exception e) { };
		

	} // End of constructor
	
	/*
	 * 		Additional methods
	 * 			setNewFont		-	update the Font of ther screen object
	 * 			initSkin		- 	applies the skin 
	 * 			saveskin		-	save skin and position to HD
	 * 			swapcolors		- 	change the color of the last clicked button
	 * 			getHelpSet		- 	locate the Help file
	 * 			showMessage		-	display the input string in a message box
	 */
	
	/**
	 *<b>setNewFont sets the new font on resizing the application</b>
	 *<ul></ul>
	 */
	public Font setNewFont(String fn, int ft, int fs){
		Font newfnt = new Font(fn, ft,fs);
		return newfnt;
		
	} // end of setNewFont
	

    /**
     *<b>initSkin		This method applies a skin</b>
     *<ul></ul>
     *This method applies the skin as saved in the ini file, the skin as modified in the
     *Skinner class. 
     */
	public void initSkin(){	
		// Apply the LookAndFeel and Theme
		// "System" is the default LookAndFeel. Other LookAndFeels are:
		//  "Nimbus", "Metal", "Windows", "Windows Classic" , "CDE/Motif", and "GTK".
		//   - Nimbus takes 3 parameters: nimbusBase, nimbusBlueGrey and control
		//   - Metal L&F has three variations: "DefaultMetal", "Ocean",  and "Test" where
		// Test refers to TestTheme, a private theme.
		
		//String lf = c9deflt_prop.getLookandfeel();
		//String theme = c9deflt_prop.getTheme();
		String lf = c9deflt_prop.lookandfeel;
		String theme = c9deflt_prop.theme;
		
		//Try to apply the proposed LookAndFeel if we have one. For Metal we set the
		//theme then the LookAndFeel
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
				    	   // Refresh the display - moved to the bottom of the method
			               //SwingUtilities.updateComponentTreeUI(c9view);
			            break;
			        }
			    } 
		 	} 
	         catch (ClassNotFoundException e) {
	             showMessage("Look-and-feel "+lf+" and theme "+theme+" is not found." 
	            		 	+"The system defaults will be used.");
	         } 
	         
	         catch (UnsupportedLookAndFeelException e) {
	             showMessage("Look-and-feel "+lf+" is not supported on this platform." 
	            		 	+"The system defaults will be used.");
	         }
	         
	         catch (InstantiationException e) {
	             showMessage("Look-and-feel "+lf+" cannot be instantiated on this platform." 
	            		 	+"The system defaults will be used.");
	         }
	         catch (IllegalAccessException e) {
	             showMessage("Look-and-feel "+lf+" is illegal on this platform." 
	            		 	+"The system defaults will be used.");
	         }
			catch (Exception e){		// reset when LookAndFeel is not found.
	             showMessage("Look-and-feel "+lf+" could not be set up." 
	            		 	+"The system defaults will be used.");
		 	}
		}
		
		// Apply the properties to the JTextArea

		screen .setForeground(c9scrn_prop.fg);
		screen .setBackground(c9scrn_prop.bg);
		screen .setBorder(BorderFactory.createLineBorder(c9scrn_prop.bd));
		
		screen .setHorizontalAlignment(JTextField.RIGHT);
		screen .setPreferredSize(new Dimension(220,24));		// set Font size
		// Make sure the font is alawys 4/5 of the screen height
		screen .setFont(new Font(c9scrn_prop.font.getName(),c9scrn_prop.font.getStyle(),4*screen .getHeight()/5));					// Set font specifics

	    // set up the GridBagLayouts and GriBagConstraints
		// Create a GridLayout for the cnt1 container.
		ctn1.setLayout(gbl1);
		gbc1.gridx = 0;	gbc1.gridy = 0;					// default pos is top left corner
		gbc1.fill = GridBagConstraints.BOTH;			// resize in both directions
		gbc1.weightx = 0.9; gbc1.weighty = 0.8;					
		gbc1.gridwidth = 4;								// span over 4 columns
		gbc1.insets = new Insets(12,8,2,8);				// external object padding
		gbc1.ipadx = 3; gbc1.ipady = 3;					// internal object padding
		ctn1.add(screen, gbc1);							// add screen+gbc to container
	    
		//Set up a GridLayoutfor the operators. (1 column only).
	    ctn2.setLayout(gbl2);
		gbc2.gridx = 0;	gbc2.gridy = 0;					// default pos is top left corner
		gbc2.fill = GridBagConstraints.BOTH;			// resize in both directions
		gbc2.weightx = 0.9; gbc2.weighty = 0.8;					
		gbc2.gridwidth = 4;								// span over 4 columns
		gbc2.insets = new Insets(12,8,2,8);				// external object padding
		gbc2.ipadx = 3; gbc2.ipady = 3;					// internal object padding	
	    
		// Set up a GridLayout for the numbers (4 rows, 3 columns).    
	    ctn0.setLayout(gbl0);
		gbc0.gridx = 0;	gbc0.gridy = 0;					// default pos is top left corner
	    gbc0.fill = GridBagConstraints.BOTH;			// resize in both directions	
	    gbc0.weightx = 0.9; gbc0.weighty = 0.8;
	    gbc0.gridwidth = 4;								// span over 4 columns
	    gbc0.insets = new Insets(12,8,2,8);				// external object padding
	    gbc0.ipadx = 3; gbc0.ipady = 3;				    // internal object padding

		// Apply the properties to the buttons
	    gbc0.fill = GridBagConstraints.BOTH;
	    gbc0.weightx = 0.9; gbc0.weighty = 0.8;
	    gbc0.gridwidth = 1;
	    gbc0.insets = new Insets(2,8,2,8);				// external object padding
		for(int i = 0; i < tab_string.length; i++){
			switch(i){
			case 12 :											// reset
				tab_button[i].setPreferredSize(dim2);
				tab_button[i].setFont(c9cbut_prop.font);
				tab_button[i].setForeground(c9cbut_prop.fg);
			 	tab_button[i].setBackground(c9cbut_prop.bg);
				gbc2.gridy = i - 11;
				gbc2.gridx = 3;
				ctn2.add(tab_button[i],gbc2);
				break;
			case 13 :											// operators
			case 14 :
			case 15 :
			case 16 :
				tab_button[i].setPreferredSize(dim2);
				tab_button[i].setFont(c9obut_prop.font);
				tab_button[i].setForeground(c9obut_prop.fg);
			 	tab_button[i].setBackground(c9obut_prop.bg);
				gbc2.gridy = i - 11;
				gbc2.gridx = 3;
				ctn2.add(tab_button[i],gbc2);
	            break;
			default :											// numbers
				tab_button[i].setPreferredSize(dim);
				tab_button[i].setFont(c9nbut_prop.font);
				tab_button[i].setForeground(c9nbut_prop.fg);	
			 	tab_button[i].setBackground(c9nbut_prop.bg);
				gbc0.gridy = 1 + (i/3);
				gbc0.gridx = i - (3*(gbc0.gridy-1));
				ctn0.add(tab_button[i],gbc0);
			}
			
		}
	    deftcolor = tab_button[1].getBackground();		// save the default color

	    // Set up the GridBagLayout for the frame
		c9view.setLayout(gbl);

		gbc.fill = GridBagConstraints.BOTH;
	    gbc.weightx = 0.9; gbc.weighty = 0.8;					
	    gbc.insets = new Insets(12,8,2,12);						// external object padding
	    gbc.ipadx = 3; gbc.ipady = 3;							// internal object padding
	    	
	    // Add containers+gbc to the frame
	    gbc.gridx = 0;	gbc.gridy = 0; gbc.gridwidth = 2;		// span over two columns
	    c9view.add(ctn1, gbc);
	    gbc.gridx = 0;	gbc.gridy = 1; gbc.gridwidth = 1;		// no span 
	    c9view.add(ctn0, gbc);
	    gbc.gridx = 1;	gbc.gridy = 1;  
	    c9view.add(ctn2, gbc);
	    
		// apply the properties to the Frame. Note we color the contentPane, not the c9view frame.
		c9view.setBounds(c9deflt_prop.getPosx(),c9deflt_prop.getPosy(),c9deflt_prop.getWidth(),c9deflt_prop.getHeight());
		c9view.getContentPane().setBackground(c9view_prop.framecolor);
		
		// apply a background if one exists in the properties.
		/* 
		 * ------------------------------------------------------------
		 
		if (c9view_prop.frametexture != null) {
			tmpFileImage = new File(c9view_prop.frametexture);		// Create File image
			try{
        	tmpImage = ImageIO.read(tmpFileImage);					// Create Image image
			} catch (IOException iox){
				System.out.println("Error applying the image to View");
			}
			//c9view.getContentPane().imageUpdate(tmpImage, ALLBITS, 0, 0, c9view.getWidth(), c9view.getHeight());
			//c9view.getContentPane().paintComponents(getContentPane().getGraphics());
			c9view.getContentPane().paintComponents(getGraphics());
		}

		* --------------------------------------------------------------
		*/ 
        SwingUtilities.updateComponentTreeUI(c9view);
		c9view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		c9view.setResizable(true);
		c9view.setVisible(true);

		// Don't pack if dimensions in skin must be used.
		//c9view.pack();	
		
	} //  End of initSkin
	
	/*
	 * -----------------------------------------------------------------------
	
	public void paint(Graphics g) {
	    g.drawImage(tmpImage, 0, 0, c9view.getWidth(), c9view.getHeight(),this);

	}
	* ------------------------------------------------------------------------
	*/ 
	
	/**
     *<b>saveSkin	This method saves the skin to a hard unit</b>
     *<ul></ul> 
     */
	public void saveSkin(){
		try {
			C9Inifile.processIni("w",c9view);			// static call 
		} catch (IOException e1) {
			// processIni handles the exceptions
		}
		
	} //  End of initSkin
	
	/**
	 * <b>swapcolor changes the color of the last clicked button</b>
	 * <ul></ul> 
	 * @param s1
	 */
	public void swapcolors(String s1){
		   if (s1 == null) {
			   screen .requestFocusInWindow();	// Ensure screen gets the focus
			   return;
		   }
		   switch (s1){
		   case "=" :
		   case "C" :
			   for (int i = 0; i < tab_string.length; i++){
				   tab_button[i].setBackground(deftcolor);   // reset color of each button
			   }
			   break;
		   case "+" :
		   case "-" :
		   case "/" :
		   case "*" :		// reset operators and numbers except the last clicked one.
			   for (int i = 0; i < tab_string.length; i++){ 			  
				   if (s1.equals(tab_button[i].getText())) {
					   tab_button[i].setBackground(Color.orange);
				   		}
				   		else tab_button[i].setBackground(deftcolor);  
				   }
		   		break;
		   default :		// reset numbers and dot, color the button w/focus
			   				// when called from the keyboard listener colors are reset
			   				// but can't color the button with the number! 
			   for (int i = 0; i < 11; i++){
				   if (s1.equals(tab_button[i].getText())) {
					   tab_button[i].setBackground(Color.yellow);
				   		}
				   		else tab_button[i].setBackground(deftcolor);
				   }
		   		break;
	       }  // end of switch
		    screen .requestFocusInWindow();	// Ensure screen gets the focus
		    screen .validate();
		   
	 } // End of swapcolors	
	
	
	/**
 	 * <b>getHelpSet	this method is used by the JavaHelp system</b>
 	 * <ul></ul>
 	 * The hs file is searched on the CLASSPATH
 	 */
 	
 	public HelpSet getHelpSet(String helpsetfile) {
 		HelpSet hs = null;
 		ClassLoader cl = this.getClass().getClassLoader();
 		try {
 		URL hsURL = HelpSet.findHelpSet(cl, helpsetfile);
 		hs = new HelpSet(null, hsURL);
 		} catch(Exception ee) {
 		System.out.println("HelpSet: "+ee.getMessage());
 		System.out.println("HelpSet: "+ helpsetfile + " non trouvé");
 		}
 		return hs;
 		}
 	
 	
 	/**
 	 * <b>showInfo	This method displays an information box with the input it gets</b>
 	 * <ul></ul>
 	 * @param s
 	 */
 	public static void showMessage(String s){
 		JLabel l = new JLabel(s);
    	l.setFont(new Font("Segoe UI",Font.PLAIN,14));
    	JOptionPane.showMessageDialog(null,l);		
 	} // End of showMessage
 	
	
	/**
	 *<b>update		Observer pattern</b>
	 *<ul></ul>
	 *A notification contains the following
	 *<li> parm 1 - showjpm/hidejpm/text to redisplay
	 *<li> parm 2 - when showjpm, the x location 
	 *<li> parm 3 - when showjpm, the y location
	 *<li> parm 4 - when a text to redisplay, the button with a color change.
	 *@param 
	 */
	public void update(String not1, String not2, String not3, String not4) {
		switch (not1){
			case "showjpm" : 
			   	// Display the contextual menu. C9Model sends us the coordinates where to show the menu
	        	  jpm.setVisible(true);
	        	  jpm.setLocation(Integer.valueOf(not2), Integer.valueOf(not3));
				break;
			case "hidejpm" : 
				// hide the contextual menu - likely a left click while the menu was showing
				jpm.setVisible(false);
				break;
			case "repaint" :	
				// Apply a skin change
				initSkin();
				break;
			default :
				// Display the string in the window as sent back by the Model
				screen .setText(not1); 
		}
		swapcolors(not4);
		
	} // End of the update method 
	
	
	/*
	 * 		Listeners send user's input to the controller for verification
	 * 		GblListener		=	ActionListener
	 * 		KeyboardAction	=	KeyListener
	 * 		MouseAction		= 	MouseListener (rigth click)
	 *      ShowMore		=   MouseListener (contextual menu)
	 *      CmpListener		=   Change size listener
	 *      WindowListener	=   Window Listener 
	 */
	
	/**
	 * 
	 * <b>ActionListener interface. This interface is implemented in each button object</b>
	 * <ul></ul>
	 * 
	 */
  public class GblListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			savetext = screen .getText();			// Save last good value
			c9cntl.c9cntl_text_control(savetext);	// Synchronize the Model area
			jpm.setVisible(false);					// Make sure the contextual menu is hidden
			c9cntl.c9cntl_GblListener(ae, screen .getCaretPosition());		// Call the controller
		}
  }
  
	/**
	 * 
	 *  <b>KeyListener interface. Used initially at the JPanel level then
	 *  by the TextArea.</b>
	 *  <ul></ul>
	 *  <li> This listener handles the operators, numerics, dot and sign in keyTyped
	 *  <li> This listener handles the Backspace, Delete and Enter keys in keyPressed
	 *  <ul></ul>
	 */
  public class KeyboardAction implements KeyListener{
	    
		public void keyTyped(KeyEvent k) {
			jpm.setVisible(false);				// Make sure the contextual menu is hidden	
			c9cntl.c9cntl_Kbdcontrol(k, screen .getCaretPosition(), screen .getText());	
		} //  End of keyTyped

		// Backspace and Delete modify the screen directly, synchronize with the Model
		public void keyPressed(KeyEvent k) {
			jpm.setVisible(false);
			c9cntl.c9cntl_Kbdcontrol(k, screen .getCaretPosition(), screen .getText());	
		} // End of keyPressed


		public void keyReleased(KeyEvent k) {
		// KeyReleased is used when using combinations of keys and the result is obtained
		// when releasing the last key. For example using an escape sequence to enter an ASCII 
		// character.
		
		// System.out.println("keyReleased="+KeyEvent.getKeyText(k.getKeyCode()));
	
		}
		
 } // End of KeyboardAction
	
	/** 
	 * <b>MouseAction	MouseListener - we use MouseAdapater so that we only need to redefine 
	 * mouseReleased.</b>
	 * <ul></ul>
	 * This listener isolates the handling of the right click in order to show the 
	 * contextual menu.
	 * <ul></ul>
	 */
 public class MouseAction extends MouseAdapter{
	    public void mouseReleased(MouseEvent event){
	    	c9cntl.c9cntl_Mousecontrol(event);  	
	    }

 } // End of MouseAction
	    
 	/**
 	 * <b>ShowMore MouseListener - this listener isolates the mouse clicks in the contextual menu</b>
 	 * <ul></ul>
 	 */
 public class ShowMore extends MouseAdapter{
		public void mouseClicked(MouseEvent event){
			JMenuItem jmi = (JMenuItem) event.getSource();	// identify the item
			c9cntl.c9cntl_Mousecontrol2(event, jmi, deftcolor);    
			}
 }

    /**
     * <b>ButtonMouseListener - this listener traps the mouse actions on buttons</b>
     * <ul></ul<
     */
 public class ButtonMouseListener extends MouseAdapter{
	 public void mouseClicked(MouseEvent me){
		 //System.out.println("mouseClicked on buttons: "+screen .getText());
		 //me.consume();
	 }
 }
 
	/**
	 *<b>CmpListener	 ChangeListener resizes the text and the faces of the buttons whenever
	 * the application is resized. </b>
	 * <ul></ul>
	 */
public class CmpListener implements ComponentListener{
		Font testfnt = null;
		
		// this method is executed on the initial display and each time the
		// calculator is resized so that texth=4/5*object height
		public void componentResized(ComponentEvent e) {				
			// local variables and defaults for the Text area

			jpm.setVisible(false);		// Make sure the contextual menu is hidden
			
			int h = screen .getHeight();					//	get Object height
			int texth = screen .getPreferredSize().height;	//  get Font height
			int textw = screen .getPreferredSize().width;	//			width
			
			//int fnttype = Font.BOLD, fntsize = 10;			
			//String fntname = "Arial";
			testfnt = screen .getFont();
			c9scrn_prop.setFont(testfnt);
			c9scrn_prop.setFontname(testfnt.getName());
			c9scrn_prop.setFontsize(testfnt.getSize());
			c9scrn_prop.setFonttype(testfnt.getStyle());
			int fntsize = c9scrn_prop.getFontsize();
			int fnttype = c9scrn_prop.getFonttype();
			String fntname = c9scrn_prop.getFontname();
			
			
			// local variables and defaults for the number button faces
			int btnh = tab_button[1].getHeight();
			int textbtnh = tab_button[1].getPreferredSize().height;
			int textbtnw = tab_button[1].getPreferredSize().width;		
			
			testfnt = tab_button[1].getFont();
			c9nbut_prop.setFont(testfnt);
			c9nbut_prop.setFontname(testfnt.getName());
			c9nbut_prop.setFontsize(testfnt.getSize());
			c9nbut_prop.setFonttype(testfnt.getStyle());
			int btn_fnttype = c9nbut_prop.getFonttype();
			int btn_fntsize = c9nbut_prop.getFontsize();
			String btn_fntname = c9nbut_prop.getFontname();
			
			// local variables and defauts for the reset button
			int cbtnh = tab_button[12].getHeight();
			int ctextbtnh = tab_button[12].getPreferredSize().height;
			int ctextbtnw = tab_button[12].getPreferredSize().width;		
			c9cbut_prop.setFont(tab_button[12].getFont());
			
			testfnt = tab_button[12].getFont();
			c9cbut_prop.setFont(testfnt);
			c9cbut_prop.setFontname(testfnt.getName());
			c9cbut_prop.setFontsize(testfnt.getSize());
			c9cbut_prop.setFonttype(testfnt.getStyle());
			int cbtn_fnttype = c9cbut_prop.getFonttype();
			int cbtn_fntsize = c9cbut_prop.getFontsize();
			String cbtn_fntname = c9cbut_prop.getFontname();
			
			// local variables and defauts for the operator buttons
			int opebtnh = tab_button[13].getHeight();
			int opetextbtnh = tab_button[13].getPreferredSize().height;
			int opetextbtnw = tab_button[13].getPreferredSize().width;		
			c9obut_prop.setFont(tab_button[13].getFont());
			
			testfnt = tab_button[13].getFont();
			c9obut_prop.setFont(testfnt);
			c9obut_prop.setFontname(testfnt.getName());
			c9obut_prop.setFontsize(testfnt.getSize());
			c9obut_prop.setFonttype(testfnt.getStyle());
			int opebtn_fnttype = c9obut_prop.getFonttype();
			int opebtn_fntsize = c9obut_prop.getFontsize();
			String opebtn_fntname = c9obut_prop.getFontname();			
			
			// local variables and defaults for the Reset button 
			// On the initial call, before display h = 0;
			
			if (h > 0) {
				// resize Text area
				fntsize = (4*h)/5;
				Font scrnfnt = new Font(fntname, fnttype, fntsize);
				screen .setPreferredSize(new Dimension(textw, texth));
				screen .setFont(scrnfnt);
			}
			if (btnh > 0) {
				// resize faces on number buttons.
				btn_fntsize = (4*btnh)/8;
				Font btnnumfnt = new Font(btn_fntname, btn_fnttype, btn_fntsize);
				for (int i = 0; i < 11; i++){
					tab_button[i].setPreferredSize(new Dimension(textbtnw, textbtnh));
					tab_button[i].setFont(btnnumfnt);
				}			
			}
			if (opebtnh > 0) {
				// resize faces on operator buttons.
				opebtn_fntsize = (4*opebtnh)/8;
				Font opebtnnumfnt = new Font(opebtn_fntname, opebtn_fnttype, opebtn_fntsize);
				for (int i = 12; i < 17; i++){
					tab_button[i].setPreferredSize(new Dimension(opetextbtnw, opetextbtnh));
					tab_button[i].setFont(opebtnnumfnt);
				}			
			}
			
			if (cbtnh > 0) {
				// resize the Reset button.
				cbtn_fntsize = (4*cbtnh)/8;
				Font cbtnnumfnt = new Font(cbtn_fntname, cbtn_fnttype, cbtn_fntsize);		
				tab_button[13].setPreferredSize(new Dimension(ctextbtnw, ctextbtnh));
				tab_button[13].setFont(cbtnnumfnt);	
				
			}

			c9deflt_prop.setHeight(c9view.getHeight());
			c9deflt_prop.setWidth(c9view.getWidth());
			c9deflt_prop.setPosx(c9view.getX());
			c9deflt_prop.setPosy(c9view.getY());
		}

		public void componentMoved(ComponentEvent e) { 	
			c9deflt_prop.setHeight(c9view.getHeight());
			c9deflt_prop.setWidth(c9view.getWidth());
			c9deflt_prop.setPosx(c9view.getX());
			c9deflt_prop.setPosy(c9view.getY());
		}

	
		public void componentShown(ComponentEvent e) {	}

		
		public void componentHidden(ComponentEvent e) { }
		
	} // End of CmpListener
	

/**
 * <b>TextInputVerifier	makes sure the input conforms to the expected</b>
 * <ul></ul>
 * When the a text field is modified by mouse actions , the action cannot be consumed even if deected invalid.
 * The solution is to test the input after the controller has worked. If it turns out the input
 * should not be accepted, the screen text is restored to the last known good input and the model is
 * invoekd through the controller to synchronize the restored screen text with the internal scrntext 
 * variable. The returned boolean does not matter.
 * <ul></ul> 
 * <li> If a sign exists it must be leading
 * <li> Disallow if contains something that's not a number, a period.
 * <li> Disallow more than one period.
 * <li> Disallow if longer that 16 digits (whole number) or 17 digits (decimals)
 * <ul></ul>
 * @author cotpa01
 * @version 1.0
 */
public class TextInputVerifier extends InputVerifier{
	C9Range scrnrange = new C9Range("0123456789+-.");			// use numeric range

	public boolean verify(JComponent input) {
		String  scrntext = screen. getText();
		int     dotnumber = 0; 
		boolean invalid = false;
		
		// System.out.println("c9view.TextInputverifier scrntext: "+scrntext);
		
		// First level verification
		for (int i = 0; i < scrntext.length(); i++){				// examine left to right
			String scrni = scrntext.substring(i,i+1);
			if (scrnrange.ocontains(scrntext.charAt(i))){			// char in range
					if (scrni.equals("+") || scrni.equals("-"))
							if (i > 0) {							// sign within string
								invalid = true;						// raise error flag
								break;						 
							};
					if (scrni.equals(".")) {						// "." located
						dotnumber = dotnumber + 1;
						if (dotnumber > 1)  {  	 					// More than one
							invalid = true;							// raise error flag
							break;
						};
					}	
			}
			else {													// invalid digit
				invalid = true;										// raise error flag
				break;
			}
		}
		
		if (invalid){
			screen .setText(savetext);					// restore last good value	
			c9cntl.c9cntl_text_control(savetext);
			return false;
		}
		
		// Trim a possible leading "+"
		String scrni = scrntext.substring(0,1);
		if (scrni.equals("+")) 									// trim leading "+"
				scrntext = scrntext.substring(1);	
			
		// Second level verification
		int    scrnl = scrntext.length();
		if ((dotnumber == 0) && (scrni != "-"))
				if (scrnl > 16) {			
					c9cntl.c9cntl_text_control(savetext);
					screen .setText(savetext);					// restore last good value	
					return false;
				}
		
		if (((dotnumber == 0) && (scrni == "-")) ||
				((dotnumber == 1) && (scrni != "-"))) 
				if (scrnl > 17) {
					c9cntl.c9cntl_text_control(savetext);
					screen .setText(savetext);
					return false;
				}
		 
		if ((dotnumber == 1) && (scrni == "-"))
				if (scrnl > 18) {
					c9cntl.c9cntl_text_control(savetext);
					screen .setText(savetext);
					return false;
				}
				
		// String passed the verifications! 
		
		return true;
	}
	
}

 /**
  *<b>WindowListener	This WindowAdater is used to trap the closing event</b>
  *<ul></ul> 
  */
public class WindowListener extends WindowAdapter{
    public void windowClosing(WindowEvent w) {
    	jpm.setVisible(false);		// Make sure the contextual menu does not stay up
    	String msg = "<html><b>The application is about to close:</b>"
        		+"<li>Click Yes to save the changes anc close" 
        		+"<li>Click No to ignore the changes and close"
        		+"<li>Click Cancel to stay in the application</html>";
    
    	JLabel label = new JLabel(msg);
    	label.setFont(new Font("Segoe UI",Font.PLAIN,14));
    	int response = JOptionPane.showConfirmDialog(null, label);
    	 
        switch (response){
        case JOptionPane.YES_OPTION :
        	c9deflt_prop.setPosx(c9view.getBounds().x);
        	c9deflt_prop.setPosy(c9view.getBounds().y);
        	c9deflt_prop.setHeight(c9view.getBounds().height);
        	c9deflt_prop.setWidth(c9view.getBounds().width);
        	c9view.saveSkin();
            c9view.dispose();
            System.exit(0);
        case JOptionPane.NO_OPTION :
            c9view.dispose();
            System.exit(0);
        	break;
        default :	// Cancel, do nothing
        }
    } // End of windowClosing
 
}



/**
 *  Trailer
 */
 
} // End of c9View class


