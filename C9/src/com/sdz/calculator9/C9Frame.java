package com.sdz.calculator9;

import java.awt.Color;

/**
 * <b>C9Frame		Container for the properties of the application<b>
 * <ul></ul> 
 * @author cotpa01
 * @version 1.0
 */
public class C9Frame { 

	protected Color  framecolor = new Color(219,211,79);
	protected String frametexture;
	public Color getFramecolor() {
		return framecolor;
	}
	public void setFramecolor(Color framecolor) {
		this.framecolor = framecolor;
	}
	public String getFrametexture() {
		return frametexture;
	}
	public void setFrametexture(String frametexture) {
		this.frametexture = frametexture;
	}
	
		
} // End fo class C9Frame

