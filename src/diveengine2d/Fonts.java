package diveengine2d;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;

public class Fonts {
	public static TrueTypeFont defaultFont;
	
	static {
		
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		defaultFont = new TrueTypeFont(awtFont, true);
	    
		
	}
}
