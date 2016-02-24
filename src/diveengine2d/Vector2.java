package diveengine2d;

public class Vector2 {
	public double x, y;
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vector2 subtract(Vector2 b) {
		Vector2 _return = new Vector2(x, y);
		_return.x -= b.x;
		_return.y -= b.y;
		return _return;
	}
	
	public Vector2 toPolar() {
		double radius = (double) Math.sqrt( x * x + y * y );
		double angleInRadians = (double) Math.acos( x / radius );
		if(this.y > 0) angleInRadians = 2*(double)Math.PI - ((double) Math.acos( x / radius ));
		return new Vector2(angleInRadians, radius);
	}
	
	public Vector2 toRect() {
		double x = (double) (Math.cos( this.x ) * this.y);
		double y = (double) (-Math.sin( this.x ) * this.y);
		return new Vector2(x, y);
	}
}
