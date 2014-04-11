package com.sdz.calculator9;

/**
 * <b>C9Range		This class is used to prototype different types of ranges</b>
 * <ul>Also contains the valditation methods</ul>
 * @author Patrice Cotte
 * @version 1.0
 */ 

public class C9Range{
	private int low;
	private int high;
	private String srng;
	
	// Default constructor
	public C9Range(){
		low = 0;
		high= 9;
	}
	
	// Overload, for custom numeric ranges
	public C9Range(int l, int h){
		this.low = l;
		this.high = h;
	}
	
	// Overload, for non numeric ranges
	public C9Range(String s){
		this.srng = s;		
	}

	// test an integer against a numeric range 
	public boolean contains(int number){
        return (number >= low && number <= high);   
	}

	// test input char against srng range
	public boolean ocontains(char c){
		return (srng.indexOf(c) >= 0);
	}
	
} // End of C9Range class 