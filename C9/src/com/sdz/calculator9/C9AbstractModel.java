package com.sdz.calculator9;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JMenuItem;
/**
 * <b>C9AbstractModel	The base of the Model layer in the C9 application
 * <ul></ul>
 * @author cotpa01
 * @version 1.0
 */
public abstract class C9AbstractModel implements Observable {
	/**
	 * 		Data
	 */
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	/**
	 * 		Abstract methods must be overriden in the extension class; they are prototypes
	 * 		of the methods that are implemented in full in C9Model 
	 */
	public abstract void reset();
	
	public abstract void c9MouseProcess(MouseEvent me);
	public abstract void c9MouseProcess2(MouseEvent me, JMenuItem jmi, Color cc);
	public abstract void c9Reset();
	public abstract void c9OperatorProcess(String o);
	public abstract void c9NumberProcess(String n, int c);
	public abstract void c9DotProcess(int c);
	public abstract void c9EqProcess();
	public abstract void c9SignProcess(char s);
	public abstract void c9UpdateScrntext(String s, String key);
	public abstract void c9TextControl(String s);
	
	public abstract boolean isDotflag();
	public abstract boolean isNum1flag();
	public abstract boolean isNum2flag();
	public abstract boolean isOperflag();
	public abstract String  getScrntext();
	
	public abstract void setDotflag(boolean f);
	public abstract void setNum1flag(boolean f);
	public abstract void setNum2flag(boolean f);
	public abstract void setOperflag(boolean f);
	public abstract void setScrntext(String s);
	public abstract void c9SkinControl(String s);
	
	/**
	 * 		Implement the Observer pattern
	 */
	// update every view that's listened on
	public void notifyObserver(String s1, String s2, String s3, String s4){
		for (Observer obs : listObserver)
				obs.update(s1, s2, s3, s4);
	}
	
	// this method adds a view to the list of observed objects 
	public void addObserver(Observer obs){
		this.listObserver.add(obs);
	}

	// recreates a fresh array of observed objects
	public void removeObserver(){
		listObserver = new ArrayList<Observer>();
	}

	
} // End of the C9AbstractModel class
