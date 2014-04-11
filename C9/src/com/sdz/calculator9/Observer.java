package com.sdz.calculator9;
/**
 * <b>Observer		Part of the Observer Pattern </b>
 * <ul>
 * It overrides the java.util.Observer class in order to define an update
 * method that works on strings.
 * </ul>
 *The update sends the following
 *<li> parm 1 - showjpm/hidejpm/text to redisplay
 *<li> parm 2 - when showjpm, the x location 
 *<li> parm 3 - when showjpm, the y location
 *<li> parm 4 - when a text to redisplay, the button with a color change.
 * @param
 * @author cotpa01
 * @version 1.0
 */
public interface Observer {
	public void update(String s1,String sg2, String s3, String s4);
}
