package com.sdz.calculator9;

import java.awt.Color;
import java.awt.Font;

/**
 * <b>C9Screen		Container for the properties of the Screen container</b>
 * <ul></ul>
 * @author cotpa01
 * @version 1.0
 */
public class C9Screen {
	protected Font font = new Font("Arial",Font.BOLD,20);
	protected String fontname = "Arial";
	protected int	fonttype=Font.BOLD, fontsize=20;
	protected Color bg = new Color(248,240,0);
	protected Color fg = Color.blue;
	protected Color bd = new Color(207,23,63);
	
	/*
	 * 	Getters and Setters
	 */

	public void setFont(Font font) {
		this.font = font;
	}
	
	public Font getFont(){
		return font;
	}
	
	public String getFontname() {
		return fontname;
	}

	public void setFontname(String fontname) {
		this.fontname = fontname;
	}

	public int getFonttype() {
		return fonttype;
	}

	public void setFonttype(int fonttype) {
		this.fonttype = fonttype;
	}

	public int getFontsize() {
		return fontsize;
	}

	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}

	public Color getBg() {
		return bg;
	}
	public void setBg(Color bg) {
		this.bg = bg;
	}
	public Color getFg() {
		return fg;
	}
	public void setFg(Color fg) {
		this.fg = fg;
	}
	public Color getBd() {
		return bd;
	}
	public void setBd(Color bd) {
		this.bd = bd;
	}

}
