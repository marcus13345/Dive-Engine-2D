package diveengine2d;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Input implements KeyListener{
	
	private static boolean[] keys = new boolean[512];
	private static List<KeyListener> listeners = new ArrayList<KeyListener>();
	
	public static void addKeyListener(KeyListener listener) {
		listeners.add(listener);
	}
	
	public static boolean getKeyDown(int keyCode) {
		return keys[keyCode];
	}

	public static boolean getKeyUp(int keyCode) {
		return !keys[keyCode];
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		for(KeyListener l : listeners) l.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		for(KeyListener l : listeners) l.keyReleased(e);
	}
}
