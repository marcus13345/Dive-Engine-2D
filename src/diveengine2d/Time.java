package diveengine2d;

public class Time {

	public static long nanos;
	public static int framesInCurrentSecond;
	public static int FPS = 0;
	public static double tickTime = 0;
	public static double timeScale = 1;
	public static double deltaTime = 0;
	public static long nextSecond = System.currentTimeMillis() + 1000, startTime = 0;
	public static double timedFramesCurrent;
	
}
