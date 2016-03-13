package diveengine2d.components;

import diveengine2d.DebugSettings;
import diveengine2d.DiveScript;
import diveengine2d.Time;

import java.awt.Color;
import java.awt.Graphics2D;

import static org.lwjgl.opengl.GL11.*;

public class RigidBody extends DiveScript {

	private static Color xAxisColor = new Color(244, 67, 54); //A500 red
	private static Color yAxisColor = new Color(33, 150, 243); //A500 blue
	private static Color debugColor = new Color(76, 175, 80); //A500 green
	public double dx, dy, drot;
	public double friction;
	public boolean physics = false;
	//public bool
	
	public void update() {
		dx += (dx*friction - dx) * Time.deltaTime;
		dy += (dy*friction - dy) * Time.deltaTime;
		drot += (drot*friction - drot) * Time.deltaTime;
		
		entity.x += dx * Time.deltaTime;
		entity.y += dy * Time.deltaTime;
		entity.rotation += drot * Time.deltaTime;
	}
	
	public void render(Graphics2D g) {
		if(DebugSettings.debugLevel > 0) {
			g.setColor(xAxisColor);
			g.drawLine((int)entity.x, (int)entity.y, (int)entity.x+(int)(dx*20), (int)entity.y);
			g.setColor(yAxisColor);
			g.drawLine((int)entity.x, (int)entity.y, (int)entity.x, (int)entity.y+(int)(dy*20));
			g.setColor(debugColor);
			g.drawLine((int)entity.x, (int)entity.y, (int)entity.x+(int)(dx*15), (int)entity.y+(int)(dy*15));
			
			glLineWidth(2.5f); 
			glColor3f(1.0f, 0.0f, 0.0f);
			glBegin(GL_LINES);
			glVertex3f(0.0f, 0.0f, 0.0f);
			glVertex3f(15f, 0f, 0f);
			glEnd();
		}
	}
}
