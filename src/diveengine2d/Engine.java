package diveengine2d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFrame;

public class Engine extends Canvas {
	public static String gameFolder = null;
	public static int WIDTH, HEIGHT;
	public static String startScene = null;
	public static String name = null;
	public static BufferStrategy bs = null;

	public Engine(String gameFolder) {

		// setup the folder
		this.gameFolder = gameFolder;

		System.out.println("Engine started with folder " + gameFolder + " ...");

		boolean configFile = false;
		try {

			// get the config values from the config file
			loadConfig();
			configFile = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		// if we failed, screw this.
		if (!configFile)
			return;

		// now, lets make our window.

		System.out.println("start scene: " + startScene);
		System.out.println("resolution:  " + WIDTH + " X " + HEIGHT);

		SceneManager.loadScene(startScene);

		SceneManager.entityDump();

		JFrame frame = new JFrame(name);
		frame.add(this);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.requestFocus();
		this.addKeyListener(new Input());
		this.addMouseMotionListener(new Input());
		this.addMouseListener(new Input());
		
		createBufferStrategy(2);
		bs = getBufferStrategy();
		
		Time.nanos = System.nanoTime();
		
		
		while(true) {
			
			Time.startTime = System.currentTimeMillis();
			if (System.currentTimeMillis() > Time.nextSecond) {
				Time.nextSecond += 1000;
				Time.FPS = Time.framesInCurrentSecond;
				Time.framesInCurrentSecond = 0;
				System.out.println("Timed Frames: " + Time.timedFramesCurrent);
				System.out.println("Calculated Frames: " + Time.FPS);
				Time.timedFramesCurrent = 0;
			}
			Time.framesInCurrentSecond++;

			render();
			updateScene();
			Time.tickTime = (System.nanoTime() - Time.nanos)/16640000d;
			Time.deltaTime = Time.tickTime * Time.timeScale;
			Time.nanos = System.nanoTime();
//			System.out.println("dTime: " + Time.deltaTime);
			
			Time.timedFramesCurrent += Time.deltaTime;
			
		}

	}
	
	public void render() {
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		render(g);
		DebugSettings.render(g);

		Vector2 a = new Vector2(512, 100);
		Vector2 b = new Vector2(256, 200);
		Vector2 c = new Vector2(768, 400);
		Vector2 mouse = new Vector2(Input.mouseX, Input.mouseY);
		
		g.setColor(DiveMath.inTriangle(a, b, c, mouse) ? Color.GREEN : Color.RED);

		g.fillOval((int)a.x, (int)a.y, 10, 10);
		g.fillOval((int)b.x, (int)b.y, 10, 10);
		g.fillOval((int)c.x, (int)c.y, 10, 10);
		
		bs.show();
	}
	
	private void updateScene() {
		SceneManager.updateAll();
	}

	private void loadConfig() throws Exception {

		File configFile = null;
		try {
			configFile = new File(gameFolder + File.separatorChar + "build.config");
		} catch (Exception e) {
			throw new Exception("Configuration File not found");
		}

		List<String> lines = Files.readAllLines(Paths.get(gameFolder, "build.config"), Charset.forName("UTF-8"));

		for (String line : lines) {

			if (line.startsWith("#"))
				continue;

			String[] parts = line.split("=");

			if (parts.length != 2) {
				System.out.println("line has incorrect parts length: '" + line + "'");
				System.out.println("ignoring...");
				continue;
			}

			parts[0] = parts[0].trim();
			parts[1] = parts[1].trim();

			if (parts[0].equals("StartScene")) {
				Engine.startScene = parts[1];
			} else if (parts[0].equals("Resolution")) {

				String[] resparts = parts[1].split("x");

				if (resparts.length != 2) {
					System.out.println("line has incorrect parts length: '" + resparts + "'");
					System.out.println("ignoring...");
					continue;
				}

				resparts[0] = resparts[0].trim();
				resparts[1] = resparts[1].trim();

				try {
					WIDTH = Integer.parseInt(resparts[0]);
					HEIGHT = Integer.parseInt(resparts[1]);
				} catch (NumberFormatException e) {
					System.out.println("line has incorrect resolution: '" + parts[1] + "'");
					System.out.println("ignoring...");
					continue;
				}
			} else if (parts[0].equals("name")) {
				name = parts[1].trim();
			}

		}

		System.out.println("Loaded Config File...");

	}
	private void render(Graphics2D g) {
		SceneManager.render(g);
		g.setColor(Color.BLACK);
	}
}
