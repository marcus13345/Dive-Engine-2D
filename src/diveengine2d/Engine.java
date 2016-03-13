package diveengine2d;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Engine {
	public static String gameFolder = null;
	public static int WIDTH, HEIGHT;
	public static String startScene = null;
	public static String name = null;

	//literally a byte address
	private long window;

	public Engine(String gameFolder) {

		// setup the folder
		Engine.gameFolder = gameFolder;

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

		try {
			init();
			loop();

			// Destroy window and window callbacks
			glfwDestroyWindow(window);
		} finally {
			// Terminate GLFW and free the GLFWErrorCallback
			glfwTerminate();
		}

	}
	
	public void render() {
		/*
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		render(g);
		DebugSettings.render(g);
		Tests.run(g);
		bs.show();
		*/
		SceneManager.render();
	}
	
	private void updateScene() {
		SceneManager.updateAll();
	}

	private void loadConfig() throws Exception {

		try {
			new File(gameFolder + File.separatorChar + "build.config");
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

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( glfwInit() != GLFW_TRUE )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure our window
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(WIDTH, HEIGHT, name, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center our window
		glfwSetWindowPos(
				window,
				(vidmode.width() - WIDTH) / 2,
				(vidmode.height() - HEIGHT) / 2
		);

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		//glViewport(0, 0, WIDTH, HEIGHT);
		//glMatrixMode(GL_PROJECTION);
		//glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		//glMatrixMode(GL_MODELVIEW);
		//glLoadIdentity();
		/*
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	    */
		
		//ratio = WIDTH / (float) HEIGHT;
		

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		
		
		

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( glfwWindowShouldClose(window) == GLFW_FALSE ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			//glRotatef(50.f, 0.f, 0.f, 1.f);
			
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
			
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}
}
