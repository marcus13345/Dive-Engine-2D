package diveengine2d;

import java.awt.Color;
import java.awt.Graphics2D;

public class RectRenderer extends DiveScript{
	
	public Color color = null;
	public int width, height;
	
	public void create() {
		name = "Rectangle Renderer";
	}
	
	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect((int)entity.x, (int)entity.y, width, height);
		
	}
}
