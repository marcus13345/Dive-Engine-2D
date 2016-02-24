package diveengine2d;

public class DiveMath {

	public static boolean inTriangle(Vector2 a, Vector2 b, Vector2 c, Vector2 p) {
		return sameSide(p, a, b, c) && sameSide(p, b, c, a) && sameSide(p, c, a, b);
	}
	
	public static boolean sameSide(Vector2 p1, Vector2 p2, Vector2 a, Vector2 b) {
		Vector2 bma = b.subtract(a);
		Vector2 p1ma = p1.subtract(a);
		Vector2 p2ma = p2.subtract(a);

		float cp1 = bma.x*p1ma.y-bma.y*p1ma.x;
		float cp2 = bma.x*p2ma.y-bma.y*p2ma.x;
		
		return cp1/cp2 >= 0;
	}
	
	/*
	function SameSide(p1,p2, a,b)
    cp1 = CrossProduct(b-a, p1-a)
    cp2 = CrossProduct(b-a, p2-a)
    if DotProduct(cp1, cp2) >= 0 then return true
    else return false

function PointInTriangle(p, a,b,c)
    if SameSide(p,a, b,c) and SameSide(p,b, a,c)
        and SameSide(p,c, a,b) then return true
    else return false
	*/
}
