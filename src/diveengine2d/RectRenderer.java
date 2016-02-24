package diveengine2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class RectRenderer extends DiveScript{
	
	public Color color = null;
	public int width, height;
	
	public void create() {
		name = "Rectangle Renderer";
	}
	
	float i = 0;
	public void render(Graphics2D g) {
		//g.rotate(Math.toRadians(i+=Time.deltaTime));
		g.setColor(color);
		Polygon p = new Polygon(new int[]{0, width, width, 0}, new int[]{0, 0, height, height}, 4);
		for(int i = 0; i < p.npoints; i ++) {
			
		}
		p.translate((int)entity.x, (int)entity.y);
		g.fillPolygon(p);
		
		//g.fillRect((int)entity.x, (int)entity.y, width, height);
		//g.rotate(Math.toRadians(-i));
	}
}
