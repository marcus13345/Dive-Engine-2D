package diveengine2d;

import java.awt.Color;
import java.awt.Graphics2D;

public class Tests {

	public static void run(Graphics2D g) {
		triangleTest(g);
		coordinateTest(g);
	}

	private static void coordinateTest(Graphics2D g) {

		Vector2 a = new Vector2(512, 300);
		Vector2 mouse = new Vector2(Input.mouseX, Input.mouseY);
		
		g.setColor(Color.RED);
		g.fillOval((int)a.x, (int)a.y, 10, 10);
		
		Vector2 polar = mouse.subtract(a).toPolar();

		g.setColor(Color.BLACK);
		g.drawString("r: " + polar.y, 100, 200);
		g.drawString("theta: " + polar.x, 100, 220);
	}

	private static void triangleTest(Graphics2D g) {
		Vector2 a = new Vector2(512, 100);
		Vector2 b = new Vector2(256, 200);
		Vector2 c = new Vector2(768, 400);
		Vector2 mouse = new Vector2(Input.mouseX, Input.mouseY);
		
		g.setColor(DiveMath.inTriangle(a, b, c, mouse) ? Color.GREEN : Color.RED);

		g.fillOval((int)a.x, (int)a.y, 10, 10);
		g.fillOval((int)b.x, (int)b.y, 10, 10);
		g.fillOval((int)c.x, (int)c.y, 10, 10);
	}

}
