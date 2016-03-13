package diveengine2d;

import java.awt.Graphics2D;

import java.awt.Color;

public class DebugSettings {
	public static int debugLevel = 0;
	public static Color backgroundColor = new Color(250, 250, 250);
	public static Color foregroundColor = new Color(25, 118, 210);
	
	public static void render(Graphics2D g) {
		if(debugLevel == 0) return;
		g.setColor(backgroundColor);
		
		int y = 20;
		int x = 20;
		g.fillRect(x, y, 140, debugLevel == 1 ? 72 : 100);
		g.setColor(foregroundColor);
		
		x += 10;
		y += 20;
		int yOff = 20;
		g.drawString("" + Time.renderTime, x, y);
		y += yOff;
		g.drawString("" + Time.timedFramesCurrent, x, y);
		y += yOff;
		g.drawString("" + SceneManager.entityCount(), x, y);
		y += yOff;
		
	}
}
