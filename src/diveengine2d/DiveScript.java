package diveengine2d;

import java.awt.Graphics2D;

public abstract class DiveScript {
	public boolean enabled = true;
	public String name;
	public Entity entity;
	
	/**
	 * called every frame
	 */
	public void update() {}
	
	/**
	 * when initialized in the scene. <br />
	 * SCENE WILL BE INCOMPLETE HERE.
	 */
	public void create() {}
	
	/**
	 * called after the scene has been initialized
	 */
	public void awake() {}
	
	/**
	 * render method beeboop
	 * @param g
	 */
	public void render(Graphics2D g) {}
}
