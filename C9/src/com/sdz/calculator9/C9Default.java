package com.sdz.calculator9;

import java.awt.Color;

/**
 * <b>C9Frame		Container for the properties of the application<b>
 * <ul></ul> 
 * @author cotpa01
 * @version 1.0
 */
public class C9Default { 
	// Lookandfeel and theme are obtained from the system. We create the properties protected
	// so that thay can be manipulated by same package classes (C9Inifile).
	protected int	posx = 0;
	protected int posy = 0;
	protected int width = 414;
	protected int height = 420;
	protected String lookandfeel = "Nimbus";
	protected String theme = null;
	protected Color  nimbusbase = new Color(124,125,126);
	protected Color  nimbusBlueGrey = new Color(65,95,132);
	protected Color  nimbuscontrol = new Color(220,251,12);
	protected String iconimage = "images/C9Icon.png";
	protected String skinlist = "Default";
	protected String skininuse = "Default";
	
	/*
	 * 	Getters and Setters
	 */
	public String getLookandfeel() {
		return lookandfeel;
	}
	public void setLookandfeel(String lookandfeel) {
		this.lookandfeel = lookandfeel;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public int getPosx() {
		return posx;
	}
	public void setPosx(int posx) {
		this.posx = posx;
	}
	public int getPosy() {
		return posy;
	}
	public void setPosy(int posy) {
		this.posy = posy;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
} // End fo class C9Default

