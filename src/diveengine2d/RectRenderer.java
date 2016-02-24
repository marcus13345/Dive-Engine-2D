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
		
		/* MAINTAINED FOR USAGE IN ROTATION BRANCH
		Polygon polygon = new Polygon(new int[]{0, width, width, 0}, new int[]{0, 0, height, height}, 4);
		
		if(entity.name.equals("Player"))
			polygon = DiveMath.rotatePolygon(polygon, (float)Math.toRadians(i+=Time.deltaTime), new Vector2(entity.x + width/2, entity.y + height/2));
			polygon = DiveMath.rotatePolygon(polygon, 0, new Vector2(entity.x + width/2, entity.y + height/2));
		polygon.translate((int)entity.x, (int)entity.y);
		g.fillPolygon(polygon);
		*/
		
		g.fillRect((int)entity.x, (int)entity.y, width, height);
		//g.rotate(Math.toRadians(-i));
	}
}
