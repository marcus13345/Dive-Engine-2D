package diveengine2d;

public class RigidBody extends DiveScript{
	
	public double dx, dy, drot;
	public double friction;
	
	public void update() {
		entity.x += dx * Time.deltaTime;
		entity.y += dy * Time.deltaTime;
		entity.rotation += drot * Time.deltaTime;
		double friction = 1 - ((1 - this.friction) * Time.deltaTime);
		dx *= friction;
		dy *= friction;
		drot *= friction;
	}
}
