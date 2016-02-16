package diveengine2d;

public class RigidBody extends DiveScript{
	
	public float dx, dy, drot;
	public float friction;
	
	public void update() {
		entity.x += dx;
		entity.y += dy;
		entity.rotation += drot;
		dx *= friction;
		dy *= friction;
		drot *= friction;
	}
}
