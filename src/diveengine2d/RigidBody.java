package diveengine2d;

public class RigidBody extends DiveScript{
	
	public float dx, dy, drot;
	public float friction = 0.99f;
	
	public void update() {
		entity.x += dx;
		entity.y += dy;
		entity.rotation += drot;
		dx *= .3;
		dy *= .3;
		drot *= .3;
	}
}
