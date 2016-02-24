package diveengine2d;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class Input implements KeyListener, MouseListener, MouseMotionListener{
	
	private static boolean[] keys = new boolean[512];
	private static List<KeyListener> listeners = new ArrayList<KeyListener>();
	public static int mouseX, mouseY;
	
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
