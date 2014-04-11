package com.sdz.calculator9;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.ini4j.Wini;


/**
 * <b>C9Inifile	This class reads and writes to an Ini/Properties file</b>
 * <ul></ul>
 * We use the ini4j properties in order to benefit the enhanced Properties this
 * package offers, namely the ability to handle sections in the Ini file.
 * @author	Patrice Cotte 
 * @param args
 * @version 1.0
 * 
 */
public class C9Inifile {

	/**
	 *	Data 
	 */
	 protected static String filename = "C9.ini";
	 protected static Wini ini = null; 
	 
	 // Pointers to property objects (C9View appearance)	
	 protected static C9Default c9d = new C9Default();
	 protected static C9Frame 	c9f = new C9Frame();	
	 protected static C9Cbutton c9c = new C9Cbutton();
	 protected static C9Obutton c9o = new C9Obutton();
	 protected static C9Nbutton c9n = new C9Nbutton();
	 protected static C9Screen 	c9s = new C9Screen();
	 
	 protected static String    iniskinlist; 
	 protected static String	iniskininuse;
	 protected static String[]  skinList = {"Default"};
	 protected static ArrayList<C9Skin> skinArray = new ArrayList<C9Skin>(); 
	 private   static boolean inifileExists = true;
	 
	 
	/**
	 * <b>main	Entry point for the C9Inifile utility</b>
	 * <ul></ul>
	 * The program expects one parameter 'r' or 'w' that indicates whether the
	 * utility should read or write the ini file. Default is r.
	 * @param args
	 * @throws IOException 
	 */
	
	public static void main(String[] args){

		String action = "r";						// default action
		if (args.length != 0){
			if (args[0].equals("w")) action = args[0];
			// else ignore invalid input
			}
		
		C9Inifile c9ini = new C9Inifile();	
		try {
			processIni(action, null);
		} catch (IOException e) {
			//	processIni handles the exceptions
		}

	} // End of main 
	
	/**
	 * <b>Main procedure.	Is invoked by main or by C9View. </b>
	 * <ul></ul> 
	 * This procedure allocates a non existing Ini file and populates it from the
	 * properties coded in the C9Frame, C9Screen, CObutton, C9Nbutton, C9Cbutton classes 
	 * <ul></ul>
	 * @param
	 */
	public static void processIni(String s, C9View c9view) throws IOException{
	
		File inout = new File(filename);
		if (!inout.exists()) {
			inifileExists = false;					// Remind we INI file initially  not there
			if (!inout.createNewFile()) {
				JOptionPane.showMessageDialog(null,"The ini file does not exist and could not be created.");
				return; 
			};
		}

		
		try {
			ini = new Wini(new File(inout.getAbsolutePath()));
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "Ini file not available or its format is invalid.");	
			return;
		}
		
		String 	action = s;					// default is read;
		
		switch (action){
		case "r" : 
			if (inifileExists == false){
				writeIni();
			}
			inifileExists = true;
			readIni();
			break;
		case "w" :
			if (inifileExists == true) writeIni();
		}
	
	} // End of processIni

	/*
	 * Action is read; if no file is found we create one. 
	 * 
	 * Note, the get method supports a third parameter, the object type.
	 * - int.class seems OK
	 * - Color.class is accepted but does not work
	 * - Font.class is accepted but does not work
	 */
	public static void readIni() {
		 
		// Override in the C9Defauls properties of the application and main window
		if (ini.get("Default","posx",int.class) != null)
			c9d.posx = ini.get("Default","posx",int.class);
		if (ini.get("Default","posy",int.class) != null)
			c9d.posy = ini.get("Default","posy",int.class);
		if (ini.get("Default","width",int.class) != null)
			c9d.width = ini.get("Default","width",int.class);	
		if (ini.get("Default","height", int.class) != null) 		
			c9d.height = ini.get("Default","height", int.class);
		if (ini.get("Default","lookandfeel") != null)		
			c9d.lookandfeel = ini.get("Default","lookandfeel");
		if (ini.get("Default","theme") != null)
			c9d.theme = ini.get("Default","theme");
		if (!ini.get("Default","nimbusbase").equals(""))
			c9d.nimbusbase = stringRGBToColor(ini.get("Default", "nimbusbase"));
		if (!ini.get("Default", "nimbusBlueGrey").equals(""))
			c9d.nimbusBlueGrey = stringRGBToColor(ini.get("Default", "nimbusBlueGrey"));
		if (!ini.get("Default","nimbuscontrol").equals(""))
			c9d.nimbuscontrol = stringRGBToColor(ini.get("Default","nimbuscontrol"));
		if (!ini.get("Default","iconimage").equals(""))
			c9d.iconimage = ini.get("Default","iconimage");
		
		// Override the C9Frame properties
		if (ini.get("Default","framecolor") != null)
			c9f.framecolor = stringRGBToColor(ini.get("Default","framecolor"));
		if (ini.get("Default","frametexture") != null)
			c9f.frametexture = ini.get("Default","frametexture");
		
		// Fetch the list of skins
		iniskinlist = "Default";
		if (ini.get("Default","skinlist") != null) 
			iniskinlist = ini.get("Default","skinlist");
		iniskininuse = "Default";
		if (ini.get("Default","skininuse") != null) 
			iniskininuse = ini.get("Default","skininuse");
		
		
		// Scan iniskinlist to determine the number of skin objects to read. Top item
		// must be Default;
		int pos = 0, j = 0;
		while (iniskinlist.indexOf(',',pos) != -1) {
			skinList[j] = iniskinlist.substring(pos,iniskinlist.indexOf(',')); 
			pos = iniskinlist.indexOf(',')+1;
			j++;
		};
		skinList[j] = iniskinlist.substring(pos);	// only skin name or last one
		
		/* -- debug purpose
		for (int i = 0;i < skinList.length; i++){
			System.out.println(skinList[i]);
		}
		*/
		
		// For each skin - for now limited to skinList[0] = "Default"
		for (int i=0; i < skinList.length; i++){
			// Properties of the screen area 	
		c9s.font = stringToFont(ini.get(skinList[i],"screenfont"));
		c9s.bg = stringRGBToColor(ini.get(skinList[i],"screenbgColor"));			
		c9s.fg = stringRGBToColor(ini.get(skinList[i],"screenfgColor"));
		c9s.bd = stringRGBToColor(ini.get(skinList[i],"screenbdColor"));
	
			// Properties of the Number buttons
		c9n.font = stringToFont(ini.get(skinList[i],"numberfont"));
		c9n.bg = stringRGBToColor(ini.get(skinList[i],"numberbgColor"));				
		c9n.fg = stringRGBToColor(ini.get(skinList[i],"numberfgColor"));
		
			// Properties of the Operator buttons
		c9o.font = stringToFont(ini.get(skinList[i],"operatorfont"));
		c9o.bg = stringRGBToColor(ini.get(skinList[i],"operatorbgColor"));				
		c9o.fg = stringRGBToColor(ini.get(skinList[i],"operatorfgColor"));
		
			// Properties of the Default button 
		c9c.font = stringToFont(ini.get(skinList[i],"resetfont"));
		c9c.bg = stringRGBToColor(ini.get(skinList[i],"resetbgColor"));				
		c9c.fg = stringRGBToColor(ini.get(skinList[i],"resetfgColor"));
		}

	    
	} // End of readIni 	
	

	/*
	 * Action is write. the section names are the skin names from the skinlist. There is
	 * always one skin at least: default. Each occurrence in the skinArray builds a separate 
	 * section.
	 */
	public static void writeIni(){
		
	for (int i=0; i < skinList.length; i++){
		// Properties of the application are stored in the Default skin
		if (skinList[i].equals("Default")){
			ini.put(skinList[i],"posx",c9d.posx);
			ini.put(skinList[i],"posy",c9d.posy);
			ini.put(skinList[i],"width",c9d.width);				 
			ini.put(skinList[i],"height",c9d.height);				 
			ini.put(skinList[i],"lookandfeel",c9d.lookandfeel);
			ini.put(skinList[i],"theme",c9d.theme);
			ini.put(skinList[i],"nimbusbase",colorToStringRGB(c9d.nimbusbase));
			ini.put(skinList[i],"nimbusBlueGrey",colorToStringRGB(c9d.nimbusBlueGrey));
			ini.put(skinList[i],"nimbuscontrol",colorToStringRGB(c9d.nimbuscontrol));
			ini.put(skinList[i],"iconimage",c9d.iconimage);
			// build list of skins; first skin must be the default.
			iniskinlist="Default";
			for (int j=1; j < skinList.length; j++){
				iniskinlist = iniskinlist+","+skinList[j];
			}
			ini.put(skinList[i],"skinlist",iniskinlist);
			ini.put(skinList[i],"skininuse",c9d.skininuse);
		};
	
		// Additional frame properties
		ini.put(skinList[i],"framecolor",colorToStringRGB(c9f.framecolor));
		ini.put(skinList[i],"frametexture",c9f.frametexture);
		
		// Properties of the screen area 	
		ini.put(skinList[i],"screenfont",fontToString(c9s.font));
		ini.put(skinList[i],"screenbgColor",colorToStringRGB(c9s.bg));		
		ini.put(skinList[i],"screenfgColor",colorToStringRGB(c9s.fg));	
		ini.put(skinList[i],"screenbdColor",colorToStringRGB(c9s.bd));

		// Properties of the Number buttons
		ini.put(skinList[i],"numberfont",fontToString(c9n.font));
		ini.put(skinList[i],"numberbgColor",colorToStringRGB(c9n.bg));		     
		ini.put(skinList[i],"numberfgColor",colorToStringRGB(c9n.fg));

		// Properties of the Operator buttons
		ini.put(skinList[i],"operatorfont",fontToString(c9o.font));
		ini.put(skinList[i],"operatorbgColor",colorToStringRGB(c9o.bg));		     
		ini.put(skinList[i],"operatorfgColor",colorToStringRGB(c9o.fg));
	
		// Properties of the Reset button 
		ini.put(skinList[i],"resetfont",fontToString(c9c.font));
		ini.put(skinList[i],"resetbgColor",colorToStringRGB(c9c.bg));		     
		ini.put(skinList[i],"resetfgColor",colorToStringRGB(c9c.fg));
	}
	
		// Write the ini file
	try {
		ini.store();
	} catch (IOException e) {
		JOptionPane.showMessageDialog(null, "Error storing the initialization file");
	}
		
	} // End of writeIni
	
	/**
	 * <b>colorToString	returns the RGB values of a color in a String such as R,G,B.</b>
	 * @param c
	 * @return
	 */
	public static String colorToStringRGB(Color c){
		if (c != null){
			return c.getRed()+","+c.getGreen()+","+c.getBlue();
		}
		else return "";
	}
	
	/**
	 * <b>stringToColor	sets a Color object from a R,G,B string</b>
	 * @param
	 * @return
	 */
	public static Color stringRGBToColor(String rgb){
		int pos = -1;
		int[] color = {255,255,255}; 						// default values = White
		String tmp = rgb;
	
		for (int i=0; i<3; i++) {
			pos = tmp.indexOf(",");		
			if (pos != -1) {										// have parameter  
				color[i] = Integer.parseInt(tmp.substring(0,pos));
				tmp = tmp.substring(pos+1);							// skip value	
			}
			else{ 									// missing parameter or last parameter
				if (i == 2) {
					color[2] = Integer.parseInt(tmp);				// last value
				}
			}
		}	
		return new Color(color[0], color[1], color[2]);
	}
	
	/**
	 * <b>stringToFont	sets a Font from the following parameters </b>
	 * <ul></ul>
	 * <li>	Input - The Font properties in the form [family,name,type,size] 
	 * <li> Output- The Font object 
	 * <li> Size
	 */
	public static Font stringToFont(String s){
		// System.out.println("input "+"*"+s+"*"+s.length());
		String name = "Arial"; int type = Font.BOLD; int size = 20;
		// Empty parm, return the default font setting.
		if ((s.length() == 0)) return new Font(name,type,size);
		if (!s.substring(0,1).equals("[")) return new Font(name,type,size);	
		 
		// Search the two first commas, return the default font is an error was found
		int pos1 = s.indexOf(',');	
		int pos2 = s.indexOf(',',pos1+1);
		
		if ((pos2 != -1) && (pos2-pos1 < 1)) return new Font(name,type,size);
		else { 	// Search the '=' sign 
			String 	tmpname = s.substring(pos1+1,pos2);
			pos1 = tmpname.indexOf('=');
			if ((pos1 != -1) && (tmpname.length()-pos1 < 1)) return new Font(name,type,size);
			else name = tmpname.substring(pos1+1);
			}
		
		// search the third comma
		pos1 = pos2;
		pos2 = s.indexOf(',',pos1+1);
		if ((pos2 != -1) && (pos2-pos1 < 1))  return new Font(name,type,size);
		else {	// search the '=' sign
			String tmpname = s.substring(pos1+1,pos2);
			pos1 = tmpname.indexOf('=');
			if ((pos1 != -1) && (tmpname.length()-pos1 < 1)) return new Font(name,type,size);
			
			switch (tmpname.substring(pos1+1)){
			case "bold" :	
				type = Font.BOLD;
				break;
			case "plain":	
			default :
				type = Font.PLAIN;
			}
		}
		
		// search the ending square bracket
		pos1 = pos2;
		int pos3 = s.indexOf(']');
		if ((pos3 != -1) && (pos3-pos1 < 1)) return new Font(name,type,size);
		String tmpname = s.substring(pos1+1,pos3);
		
		// search the = sign in size parameter
		pos1 = pos2;				
		pos2 = s.indexOf('=',pos1+1);
		if ((pos2 != -1) && (pos2-pos1 < 1)) return new Font(name,type,size);
		size = Integer.parseInt(s.substring(pos2+1,pos3));	
		 
		return new Font(name,type,size);
	}
	
	/**
	 * <b>FontToString builds the Name,Type,Size string from the Font parameters</b>
	 * <ul></ul>
	 * <li> Input  - the toString() for a Font object
	 * <li> Output - [family,name,type,size]
	 * @param
	 * @return
	 */
	public static String fontToString(Font f){
		// The toString() method returns package name+[font properties], for example:
		// 		java.awt.Font[family=Arial,name=Arial,style=bold,size=20]
		// We remove the package name.
		return f.toString().substring(f.toString().indexOf('['));
		
	}
	
	/*
	 * 	Getters and Setters are used by the C9View view initialization process
	 */

	public static C9Default getC9d() {
		return c9d;
	}

	public static void setC9d(C9Default c9d) {
		C9Inifile.c9d = c9d;
	}
	
	public static C9Frame getC9f() {
		return c9f;
	}


	public static void setC9f(C9Frame c9f) {
		C9Inifile.c9f = c9f;
	}

	public static C9Cbutton getC9c() {
		return c9c;
	}

	public static void setC9c(C9Cbutton c9c) {
		C9Inifile.c9c = c9c;
	}

	public static C9Obutton getC9o() {
		return c9o;
	}

	public static void setC9o(C9Obutton c9o) {
		C9Inifile.c9o = c9o;
	}

	public static C9Nbutton getC9n() {
		return c9n;
	}

	public static void setC9n(C9Nbutton c9n) {
		C9Inifile.c9n = c9n;
	}

	public static C9Screen getC9s() {
		return c9s;
	}

	public static void setC9s(C9Screen c9s) {
		C9Inifile.c9s = c9s;
	}
	
	
}  //End of class C9Inifile
