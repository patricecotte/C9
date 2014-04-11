package com.sdz.calculator9;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.swing.JMenuItem;

/**
 * <b>C9Model		Extension of the abstract class C9AbstractModel</b>
 * <ul></ul>
 * @author cotpa01
 * @version 1.0
 */
public class C9Model extends C9AbstractModel{
	/**
	 * 	Data
	 */
	C9About abt = null;										// pointer to the About panel
	C9Skinner c9sk = null;									// pointer to the ColorChooser
	protected BigDecimal number1 = new BigDecimal("0");		// work storage for numbers
	protected BigDecimal number2 = new BigDecimal("0");
	protected BigDecimal ZERO;
	protected String	 scrntext = "0";					// Number in text format 
	protected String	 operator = "";						// Operator
	protected boolean	 num1flag = false;					// User can only build num#1
	protected boolean	 operflag = false;					// User may enter an operator
	protected boolean	 num2flag = false;					// User may enter num2
	protected boolean	 dotflag  = false;					// User may enter a dot
	protected String n1 = null, n2 = null, n3 = null;
	
	/**
	 * 		Overriden methods
	 */
	public void reset(){
		
	}
	
	/**
	 * <b<C9MouseProcess	handles the right-click in the outside buttons</b>
	 * <ul></ul>
	 * It notifies the view to display the contextual menu
	 * <ul></ul>
	 */
	public void c9MouseProcess(MouseEvent me){	
		//String n1 = null, n2 = null, n3 = null;
		// Note the Model cannot modify the view directly, it can only send
		// a feedback to the view through the notification mechanism
        if(me.isPopupTrigger()){
        	  n1 = "showjpm";
        	  n2 = String.valueOf(me.getXOnScreen()+10);
        	  n3 = String.valueOf(me.getYOnScreen()+10);
          }
         else {  // left click anywhere outside buttons
        	  n1 = "hidejpm";
         }
		// n4 = null, don't change the coloring
		notifyObserver(n1, n2, n3, null);
		
	} // End of C9MouseProcess method

	
	/**
	 * <b>C9MouseProcess2	handles the selection of an item in the contextual menu with the pointer</b>
	 * <ul></ul>
	 * The desired second level object is instantiated and threaded
	 * <ul></ul>
	 */
 	public void c9MouseProcess2(MouseEvent me,JMenuItem jmi, Color cc){
		// Instantiate the desired class and thread it
		switch (jmi.getText()){
			case "About" :
				notifyObserver("hidejpm",null,null,null);
				// Always reuse the same instance
				if (abt == null){
					abt = new C9About();
				}
				/*
				 * --- the runnable version displays inccorectly; changed it into
				 * --- a simple call.
				Thread t1 = new Thread(abt.new RunAbout(abt, cc));
				t1.start();	
				* -----------------------------------------------------------------
				*/ 
				abt.setVisible(true);
				break;
			case "Skin me" :
				notifyObserver("hidejpm",null,null,null);
				if (c9sk == null) {
					c9sk = new C9Skinner();
				}
				Thread t2 = new Thread(c9sk.new C9SkinnerRun());
				t2.start();
				break;
			case "Help me" :		
				// the help system is started in C9View directly
				notifyObserver("hidejpm",null,null,null);
				break;
			 default:
		}
	
	 
 	} // End of C9MouseProcess2 method
 	
 	
 	/**
 	 * <b>C9Reset	perform the Reset request </b>
 	 * <ul></ul>
 	 * reset internal flags and notify the main view to display 0.
 	 * <ul></ul>
 	 */
 	public void c9Reset(){
 		num1flag = true; num2flag = false; dotflag = false; operflag = false;
 		operator = ""; scrntext ="0"; number1 = BigDecimal.ZERO;
 		notifyObserver(scrntext, null, null,"C");
 		
 	} // End of C9Reset
 	

 	/**
 	 * <b>C9OperatorProcess	handles an operator +,-,/ or *</b>
 	 * <ul></ul>
 	 * <li>If the first number was being entered, save its value into number1
 	 * <li>If an operator was entered already and typing number2 has not started yet
 	 * replace the operator.
 	 * <li>If the second number was being entered, perform the calculation, save the result and notify the view with the result
 	 * <ul></ul>
 	 */
 	public void c9OperatorProcess(String o){
 		if (operflag && num2flag) { 			// ** operator triggers the calculation
 			calculation(scrntext);				// perform calculation
 	 		showresult();						// format scrntext
 												// set dot flag depending on the result
 			if (scrntext.indexOf(".") == -1) dotflag = false;
 					else dotflag = true;
			num1flag = true;					// number 1 set with result
 			num2flag = false; 					// reset flags about 2nd number and dot
 			operator = o;						// save operator for the next calculation
 
 		}
 		else {
 			if (!num2flag) {
 				operator = o;					// set or update the operator field
 				operflag = true;				// make sure oper flag is on
 				dotflag  = false;				//			 dot flag is off
 			}
 			// num2flag true and operflag false should not happen
 		}
		notifyObserver(scrntext,null,null,o); // let the view display the result
 		
 	} // End of C9operatorProcess
 	 
 	/**
 	 * <b>C9NumberProcess	handles a number</b>
 	 * <ul></ul>
 	 * It replaces an initial 0 otherwise concatenates it to the existing number. The view is notified 
 	 * to display the updated number.
 	 * <ul></ul>
 	 */
 	public void c9NumberProcess(String n, int c){
 		String[] lr = splittext(scrntext,c);				// split text on cursor position
 		//System.out.println("c9mod.c9NumberProcess receives String: "+n+" at: "+c);
 		
 		if (operflag) {
 			if (num2flag) {									// ** continue number 2
 				if (scrntext.equals("0")) scrntext = n;		// replace 0
 					else{ 
 						scrntext = lr[0]+n+lr[1];			// else insert at cursor position
 					}
 			}
 			else {											// ** start number 2 now
 				num2flag = true;							// raise flag
 				number1 = BigDecimal.ZERO.add(new BigDecimal(scrntext));	// save current scrntext value
 				dotflag = false;								// allow decimals
 				scrntext = n;								// number 2's first digit
 			}
 		}
 		else {												// ** continue number 1
 			num1flag = true;								// just in case
				if (scrntext.equals("0")) scrntext = n;		// replace 0
					else { 
 						scrntext = lr[0]+n+lr[1];			// else insert at cursor position
 					}	
 		}
 		
 		//System.out.println("c9mod.c9NumberProcess says lr[0]: "+lr[0]+", lr[1]: "+lr[1]+" scrntext: "+scrntext);
 		notifyObserver(scrntext,null,null,n);
 		 		
 	} // End of c9NumberProcess
 
 	
 	/**
 	 * <b>c9DotProcess	handles the period as the decimal separator</b>
 	 * <ul></ul>
 	 * The dot is concatenated to the existing number and the view is notified to display
 	 * the modified number. If the dot is sent right after an operator then it is translated 
 	 * into "0."
 	 * <ul></ul>
 	 */
 	public void c9DotProcess(int c){
		if (operflag && !num2flag){						// special case
				scrntext = "0.";  		 				// start number 2 
				num2flag = true;						// set flag
			}
		else {
 		String[] lr = splittext(scrntext,c);			// split text on cursor position
		if (lr[0].equals("")) lr[0] = "0";				// add leading 0 
		scrntext = lr[0]+"."+lr[1];						// general case
		dotflag = true;
		}
		notifyObserver(scrntext,null,null,".");
 	} // End of c9DotProcess
 	
 	
 	/**
 	 * <b>C9EqProcess	handles '='</b>
 	 * <ul></ul>
 	 * <li>The calculation is performed 
 	 * <li>The result is saved internally
 	 * <li>The view is notified to display the result
 	 * <ul></ul>
 	 */
 	public void c9EqProcess(){
 		calculation(scrntext);						// perform the calculation
 		showresult();								// format scrntext
		if (scrntext.indexOf(".") == -1) dotflag = false;
				else dotflag = true;
		num1flag = true;							// number 1 set with result
		num2flag = false;  							// reset flags about 2nd number and dot
		operator = ""; operflag = false;			// reset the operator and flag
		notifyObserver(scrntext,null,null,"="); 	// let the view display the result
 		
 	} // End of C9EqProcess
 	
 	/**
 	 * <b>C9SignProcess		sets the sign in the string's leading position</b>
 	 * <ul></ul>
 	 * This routine is called whenever user pressed a sign key and the cursor was set ahead
 	 * of the number in the display.
 	 * <ul></ul>
 	 */
 	public void c9SignProcess(char s){
 		switch (s){
 		case '+' :
 			if (scrntext.substring(0,1).equals("-")) 
 					scrntext = scrntext.substring(1);  			// remove "-" sign
 			break;
 		case '-' :
 			if (scrntext.substring(0,1) != "-") 
 					scrntext = "-" + scrntext;					// set - sign
 			else scrntext = scrntext.substring(1);				// remove "-" sign	
 		}
 		String str = ""+s;													// char to string
 		notifyObserver(scrntext,null,null,str);
 		
 	}
 	
 	/**
 	 * <b>c9UpdateScrntext	synchronize View and Model on the screen text</b>
 	 * <ul></ul>
 	 * By using the keyboard the user modifies the screen text directly; when this results into
 	 * an empty text (using delete or backspace) the Model sets it to "0". 
 	 * <ul></ul> 
 	 */
 	public void c9UpdateScrntext(String s, String key){
 		
		// If all decimals are 0s remove .0000s
		String pattern = "([-\\d,]*)(\\.0*[^1-9])";   	// figures+','+'.'+ zeroes + no other figure trailing
		if (s.matches(pattern)) 
				s = s.replaceAll(pattern,"$1");
		
		// Detect d.00dd000 and remove trailing 0s after non zero decimals.
		pattern = "([-\\d,]+\\.0*[1-9]+)(0+)";	
		if (s.matches(pattern))
				s = s.replaceAll(pattern,"$1");
		
		// If we end up with the empty string or only "-" we set it to 0
 		if (s.equals("") || s.equals("-")){
 			s = "0";
 			dotflag = false;
 		}
 		
 		// Set scrntext, notify the view passing back the key that triggered the change
 		// for coloring purpose. 
 		scrntext = s;
 		notifyObserver(scrntext,null,null,key);
 	}
 	
 	/**
 	 * <b>c9TextControl		makes sure the screen text is synchronized with scrntext variable</b>
 	 * <ul></ul>
 	 * Some actions cannot be disposed after the controller detected them invalid and the screen
 	 * have been modified whereas the scrntext variable is unchanged.   
 	 */
 	public void c9TextControl (String s){
 		//System.out.println("c9Model has received this rollback: "+s+" for scrntext currently = "+scrntext );
 		scrntext = s;
 	}
 	
 	
 	/**
 	 * <b>c9SkinControl 	applies or resets the appearance </b>
 	 * <ul></ul>
 	 */
 	public void c9SkinControl(String s){
 		switch (s) {
 		case "Apply" :
 			
 		case "Reset" :
 		}
 		if (c9sk.getC9deflt_prop_new() != null)
 			C9View.setC9deflt_prop(c9sk.getC9deflt_prop_new());
 		if (c9sk.getC9frame_prop_new() != null)
 			C9View.setC9view_prop(c9sk.getC9frame_prop_new());
 		if (c9sk.getC9cbutton_prop_new() != null)
 			C9View.setC9cbut_prop(c9sk.getC9cbutton_prop_new());
 		if (c9sk.getC9obutton_prop_new() != null)
 			C9View.setC9obut_prop(c9sk.getC9obutton_prop_new());
 		if (c9sk.getC9nbutton_prop_new() != null)
 			C9View.setC9nbut_prop(c9sk.getC9nbutton_prop_new());
 		if (c9sk.getC9screen_prop_new() != null)
 			C9View.setC9screen_prop(c9sk.getC9screen_prop_new());
 		notifyObserver("repaint",null,null,null);
 	}
 	
 	/*
 	 * 		Second level routines
 	 */
 	/**
 	 * <b>splittext	separates scrntext in its left part + right part based on the cursor position</b>
 	 * <ul></ul>
 	 */
 	public String[] splittext (String s, int c){
 		String[] splitText = {"",""};				// work string array
 		splitText[0] = s.substring(0,c);			// left part
 		splitText[1] = s.substring(c);				// rigth part
 		
 		//System.out.println(splitText[0]+","+splitText[1]);
 		
 		return splitText;
 		
 	}
 	/**
 	 * <b>calculation	performs the calculation number1 operation number2</b>
 	 * <ul></ul>
 	 * @param s
 	 */
 	/* ----------------------------------------------------------------------------------------
	 * We cannot use the simple operations on double variables as their precision is very low.
	 * We use instead BigDecimal objects. Accuracy however requires a rounding specification.
	 * - For +,- and * operators this goes through specifying a math context. The value in the 
	 * 	math context is the number of decimals after rounding.
	 * - For / this goes through specifying the number of decimals and a rounding method.
	 * 	See the details in the Java documentation.
	 * BigDecimals are also sensitive to scalability (setScale) but we don't need it here.
	 * - An operation on 0 returns 0E-n where n is the maximum number of decimals, which is 
	 * correct bu displays ugly. We trap this condition by testing number1 being a 0 expression
	 * (0 or 0.0 ... etc)
	 * --------------------------------------------------------------------------------------- */
	public void calculation(String s){
	  	// calculation  
	  	// System.out.println("number1:"+number1+(number1.compareTo(BigDecimal.ZERO) == 0)+" operator:"+(operator == "/" | operator == "*"));
	  	
	  	if (number1.compareTo(BigDecimal.ZERO) == 0 & (operator.equals("/") | operator.equals("*"))) return;
	  		
	  	BigDecimal number2 = new BigDecimal(s);
	  	MathContext mc = new MathContext(16);
	  	
	  	switch(operator){
	  	case "+" : 
	  		number1 = number1.add(number2,mc);
	      	break;
	  	case "-" :
	  		number1 = number1.subtract(number2,mc); 
	      	 break;
	      case "*" :
	      	number1 = number1.multiply(number2,mc);
	      	  break;
	  	case "/" :
	  			try{
	  				number1 = number1.divide(number2,16,BigDecimal.ROUND_HALF_DOWN);
	  			} catch(ArithmeticException e) {  // catch divide by 0
	              number1 = BigDecimal.ZERO;
	  			}
	           break;
	  	};
	  	// System.out.println("number1 now "+number1);	  	  	
	  	
} // End of calculation
	
	/**
	 * <b>showResult	this routine prepares the text that is sent back to the view</b>
	 * <ul></ul>
	 * Number1 big decimal is converted into a string, which is reformatted using regular expressions
	 * to suppress trailing decimal zeroes or a period only followed by zeroes. 
	 * <ul></ul>
	 */
	 public void showresult(){
		    // Test for and remove decimal positions that are zeroes only.
			scrntext = String.valueOf(number1);
			
			// If all decimals are 0s remove .0000s
			String pattern = "([-\\d,]*)(\\.0*[^1-9])";   	// figures+','+'.'+ zeroes + no other figure trailing
			if (scrntext.matches(pattern)) 
					scrntext = scrntext.replaceAll(pattern,"$1");
			
			// Detect d.00dd000 and remove trailing 0s after non zero decimals.
			pattern = "([-\\d,]+\\.0*[1-9]+)(0+)";	
			if (scrntext.matches(pattern))
					scrntext = scrntext.replaceAll(pattern,"$1");

		} // End of showresult
	
	
	/*
 	 * 	Getters and Setters
 	 */
 	
 	/**
 	 * 
 	 * @return	BigDecimal number1
 	 */
	public BigDecimal getNumber1() {
		return number1;
	} 

	/**
	 * 
	 * @param n1 
	 */
	public void setNumber1(BigDecimal n1) {
		this.number1 = n1;
	}

	/**
	 * 
	 * @return	BigDecimal number2
	 */
	public BigDecimal getNumber2() {
		return number2;
	}

	/**
	 * 
	 * @param n2
	 */
	public void setNumber2(BigDecimal n2) {
		this.number2 = n2;
	}
	
	/**
	 * 
	 * @return numflag1
	 */
	public boolean isNum1flag() {
		return num1flag;
	}

	/**
	 * 
	 * @param num1flag
	 */
	public void setNum1flag(boolean num1flag) {
		this.num1flag = num1flag;
	}

	/**
	 * 
	 * @return operflag
	 */
	public boolean isOperflag() {
		return operflag;
	}

	/**
	 * 
	 * @param operflag
	 */
	public void setOperflag(boolean operflag) {
		this.operflag = operflag;
	}

	/**
	 * 
	 * @return num2flag
	 */
	public boolean isNum2flag() {
		return num2flag;
	}

	/**
	 * 
	 * @param num2flag
	 */
	public void setNum2flag(boolean num2flag) {
		this.num2flag = num2flag;
	}

	/**
	 * 
	 * @return dotflag
	 */
	public boolean isDotflag() {
		return dotflag;
	}

	/**
	 * 
	 * @param dotflag
	 */
	public void setDotflag(boolean dotflag) {
		this.dotflag = dotflag;
	}

	public String getScrntext() {
		return scrntext;
	}

	public void setScrntext(String scrntext) {
		this.scrntext = scrntext;
	}
 	
 	
} // End of class C9Model
