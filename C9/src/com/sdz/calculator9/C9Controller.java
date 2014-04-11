package com.sdz.calculator9;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

public class C9Controller extends C9AbstractController {
	/*
	 * 	Data defintions
	 */
	C9Range range = new C9Range(); 							// set up range 0-9
	C9Range orange= new C9Range("-/*+.=,c");				// set up range of non numerics	
	String bdetext = "";

	public C9Controller(C9AbstractModel c9modparm) {
		super(c9modparm);
	}

	/**
	 * <b<This method overrides the abstract method. </b>
	 * <ul>It performs a verification on the operator or number sent by the View. 
	 * If OK it updates a number of flags and let the Model perform the desired action.</ul>
	 * <ul>This listener cannot consume the event. If it detects an invalid input the internal
	 * variable will not be updated. It is expected that the InputTextVerifier (in C9View) will 
	 * restore the last good display and synchronize the scrntext variable in the Model through
	 * the controller (c9cntl.c9cntl_text_controller -> c9mod.c9TextControl)
	 * @param  
	 */
	public void c9cntl_GblListener(ActionEvent ae, int c){
		String src =  ((JButton)ae.getSource()).getText();
		//System.out.println("c9cntl_GblListener receives: "+src+", csr:"+c);
		 
		switch (src){
		case "+" :
		case "-" :	
			/* operators: 		+ and - in csr position 0, sign indicator
			 * 	NB: in the initial display "0" cursor position is always 0 even after moving the cursor
			 * with the left key arrow.
			 *					+ and - in position < length = invalid
			 *					+ and - in last position = operators. 
			 * 					+ and - in position 1  and "-" already set, sign indicator
			 */
			if (c == 0) {
				if (c9mod.getScrntext().equals("0")) c9mod.c9OperatorProcess(src);		// an operator
				else c9mod.c9SignProcess(src.charAt(0));				// a sign indicator
			}
			else{ 
				if ((c == 1) && (c9mod.getScrntext().substring(0,1).equals("-"))) c9mod.c9SignProcess(src.charAt(0));
				if (c == c9mod.getScrntext().length())	c9mod.c9OperatorProcess(src);
				// else ignore
			};
			break;
		case "/" :
		case "*" :	
			c9mod.c9OperatorProcess(src);
			break;
		case "C" :				// Reset resets flags and transfers to the Model
			c9mod.c9Reset();
			break;
		case "=" :				// user wants to see the temporary or final result
								// Only if an operator and the second number have been entered
			if (c9mod.isNum2flag() && c9mod.isOperflag())
					c9mod.c9EqProcess();
			break;
		case "." :				
			if (!c9mod.isDotflag()){			// first time dot is clicked
					c9mod.c9DotProcess(c);				
				}
			else {								// dot flag is set.
				if (!c9mod.isOperflag()) break;                     // in number 1
				if (!c9mod.isNum2flag()) c9mod.c9DotProcess(c);		// starting number 2 
				// else ignore
			}
			break;
		default  :		
			 c9mod.c9NumberProcess(src, c);    		// Process number #1 or #2			
			// Else ignore
		}
		
	}  // End of C9cntl_GblListener
	
	/**
	 * <b>c9cntl_Kbdcontrol		keyboard handler</b>
	 * <ul></ul>
	 * The keyboard handler receives control first about the KeyPressed event then about 
	 * the KeyTyped event. Under KeyPressed we deal with BackSpace, Delete and Enter. U
	 * Under KeyTyped we deak with the numbers, the operators, the signs and dot.
	 * <li>parm 1 - event 
	 * <li>parm 2 - csr position (for +,- handling)
	 * <li>parm 3 - current string text (for backspace, delete handling)
	 * <ul></ul>
	 * @param 
	 */
	public void c9cntl_Kbdcontrol(KeyEvent k, int c, String s){ 
		
		/*
		 * Consistently with the buttons we only accept numbers and one dot per entry.
		 * The buttons change colors as they are typed in.  
		 * 
		 * Operators behave roughly the same as their counterpart in the interface 
		 * with a number of differences:
		 * - An additional behaviors for '+' and '-' is they can be used to change the
		 * sign of the numbed on display when entered ahead of the numbers. In that
		 * case they do not work as operators.
		 * - '=' does not reset, so that user change change the number of display and
		 * continue with the modified number. Consequently the only way to reset 
		 * the current display is celaring it using the delete or backspace.
		 * 
		 * Enter and Return keys behave like '='.
		 * 
		 * Backspace and Delete keys can be used to rework the number on display.
		 * On suppressing the last number the display is forced to 0. 
		 * Left, Rigth, insert work the same as in normal text.
		 * 
		 * Other keys let the event be ignored (consumed). Note, the ability to consume an 
		 * unwanted keystroke eliminates the need to run an InputTextVerifier as with the
		 * mouse events!  
		 */		
		
		String key = Character.toString(k.getKeyChar());
		int keynum = Character.getNumericValue(k.getKeyChar());	
		String keytext   = KeyEvent.getKeyText(k.getKeyCode());
		
		// Process Keypressed. Remember if Delete or Backspace have been entered
		if (k.getID() == KeyEvent.KEY_PRESSED) {
			switch (keytext){
			case "Backspace" :								// Backspace
				bdetext = keytext;
				break;
			case "Delete" :									// Delete
				bdetext = keytext;
				break;
			case "Enter" :									// Enter behaves like "="
				if (c9mod.isNum2flag() && c9mod.isOperflag())
					c9mod.c9EqProcess();
				default:				
			}
		}
		
		// Process KEYTYPED
		if (k.getID() == KeyEvent.KEY_TYPED) { 
			
			// If Backspace or Delete where detected in the KEYPRESSED section we must
			// update scrntext with the current value from the GUI and reset colors. 
			if (bdetext.equals("Backspace") || bdetext.equals("Delete")){
					c9mod.c9UpdateScrntext(s,"C"); 
					bdetext = "";
			}
			else {   // ***
			// Accept numbers; 
			if (range.contains(keynum)){	
				c9mod.c9NumberProcess(key, c);
			}
		
			// Accept the dot and the operators
			if (orange.ocontains(k.getKeyChar())){
						
				switch (k.getKeyChar()){
				case '=' :									// '=', show user the result
					if (c9mod.isNum2flag() && c9mod.isOperflag())
						c9mod.c9EqProcess();
					break;
				case '.' : 								// Ensure only one period in a number
					if (!c9mod.isDotflag()){			// first time dot is clicked
						c9mod.c9DotProcess(c);				
					}
					break;
				case 'c' :								// reset requested
					c9mod.c9Reset();
				case '+' :								
				case '-' :					
					/* operators: 		+ and - in csr position 0, sign indicator
					 * 	NB: in the initial display "0" cursor position is always 0 even after moving the cursor
					 * with the left key arrow.
					 *					+ and - in position < length = invalid
					 *					+ and - in last position = operators. 
					 * 					+ and - in position 1  and "-" already set, sign indicator
					 */
					if (c == 0) {
						if (c9mod.getScrntext().equals("0")) c9mod.c9OperatorProcess(key);		// an operator
						else c9mod.c9SignProcess(key.charAt(0));				// a sign indicator
					}
					else{ 
						if ((c == 1) && (c9mod.getScrntext().substring(0,1).equals("-"))) c9mod.c9SignProcess(key.charAt(0));
						if (c == c9mod.getScrntext().length())	c9mod.c9OperatorProcess(key);
						// else ignore
					};
					break;
				default  :								// operators
					c9mod.c9OperatorProcess(key);
				} // End of switch
				
			} // End of operators, '=' and '.'
			
			k.consume();
			} // End else ***
		}
		
	}
	
   /**
    *<b> cn9cntl_text_control	makes sure the final text is acceptable</b>
    *<ul></ul>
    *Because ActionEvents cannot be consumed the screen is udpated even if the controller
    *detected an incorrect action. In such a case the screen text will be different from the
    *internal value. 
    */
	public void c9cntl_text_control(String s){
		c9mod.c9TextControl(s);	
	} // End of c9cntl_text_control 
	
	/**
	 * <b>c9cntl_Skin_change	applies or resets changes to the appearance</b>
	 * <ul></ul>
	 */
	public void c9cntl_Skin_change(String s){
			c9mod.c9SkinControl(s);
	}
} // End of C9Controller class
