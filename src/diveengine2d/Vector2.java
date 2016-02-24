package diveengine2d;

public class Vector2 {
	public float x, y;
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public Vector2 subtract(Vector2 b) {
		Vector2 _return = new Vector2(x, y);
		_return.x -= b.x;
		_return.y -= b.y;
		return _return;
	}
	
	public Vector2 rectToPolar(Vector2 b) {
		Vector2 _return = new Vector2(x, y);
		
		return _return;
	}
}
