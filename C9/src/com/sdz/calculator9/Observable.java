package com.sdz.calculator9;

/**
 * <b>Observable:	Part of the Observer Pattern in the C9 applicationw</b>
 * <ul></ul> 
 *A notification contains the following
 *<li> parm 1 - showjpm/hidejpm/text to redisplay
 *<li> parm 2 - when showjpm, the x location 
 *<li> parm 3 - when showjpm, the y location
 *<li> parm 4 - when a text to redisplay, the button with a color change.
 * @author cotpa01
 * @version 1.0
 */
public interface Observable {
	public void addObserver(Observer obs);
//	public void removeObserver();
	public void notifyObserver(String s1, String s2, String s3, String s4);
}
