package com.sdz.calculator9;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;

/**
 * <b>C9AbstractController		The base of the Controler layer in the C9 application</b>
 * <ul></ul>
 * <li>On a mouse action on a number or an operator, we verify the input is valid and call 
 * appropriate method in the Model class.
 * <li>On another mouse event no verification is needed, the appropriate method 
 * in the Model class is invoked.
 * <ul></ul>
 * @author cotpa01
 * @version 1.0
 */
public abstract class C9AbstractController {
	/**
	 * 	Data
	 */
	protected C9AbstractModel c9mod;						// Work storage for our MODEL
	
	/**
	 * C9AbstractionController copies the input object to work. 
	 * @param c9modparm
	 */
	public C9AbstractController(C9AbstractModel c9modparm){
		this.c9mod = c9modparm;				// copy to our work area
	}	
	
	/**
	 * C9cntl_Mousecontrol abstract method for the rigth-click
	 * @param me
	 */
	public void c9cntl_Mousecontrol(MouseEvent me){
		//System.out.println("Abstract Model is passing me: "+me);
		c9mod.c9MouseProcess(me);
	}
	
	/**
	 * C9cntl__Mousecontrol2 for the contextual menu items
	 * @param me
	 * @param jmi
	 * @param cc
	 */
	public void c9cntl_Mousecontrol2(MouseEvent me, JMenuItem jmi, Color cc){
		//System.out.println("Abstract Model is passing me: "+me);
		c9mod.c9MouseProcess2(me, jmi, cc);
	}
	
	/**
	 * C9cntl_GblListener abstract method handles the clicks on buttons
	 * @param ae
	 */
	public void c9cntl_GblListener(ActionEvent ae, int c){
		//System.out.println("Getting from the View this event: "+me);
	}
	
	/**
	 * c9cntl_Kbdcontrol abstract method	handles the keyboard actions
	 */
	public void c9cntl_Kbdcontrol(KeyEvent k, int i, String s){ 
		
	}
	
	/**
	 * c9cntl_text_control	abstract method handles text rollback
	 */
	public void c9cntl_text_control(String s){
		
	}
	
	/**
	 * c9cntl_Skin_control	abstract method for skin change 
	 *  
	 */
	public void c9cntl_Skin_change(String s){
		
	}
	
	
} // End of C9AbstractController class
