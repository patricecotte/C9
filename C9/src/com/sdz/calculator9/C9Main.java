package com.sdz.calculator9;

import java.awt.EventQueue;

/**
 * <b>C9Main	Entry point for the C9 application</b>
 * <ul>C9 is an MVC application that is made of 
 * <li>A Model      C9Model+C9AbstractModel
 * <li>A Controller C9Controller+C9AbstractController
 * <li>A View       C9View
 * </ul>
 * Dependency       The help files must be on the Classpath
 * <ul></ul>
 * @author cotpa01
 * @version 1.0
 */
public class C9Main {

	/**
	 * @param args n/a
	 * <ul>Actions:
	 * <li>Create the Model
	 * <li>Create the Controller, being passed the Model instance
	 * <li>Create the View, being passed the Controller instance
	 * <li>Add the View to the model's listened objects
	 * </ul>
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
				public void run() {
				C9Model c9mod = new C9Model();
				C9Controller c9cntl = new C9Controller(c9mod);
				C9View c9view = new C9View(c9cntl);
				c9mod.addObserver(c9view);
			}; // End of run
		}); // End of runnable
		
	}  // End of main
	
} // End of C9Main class
