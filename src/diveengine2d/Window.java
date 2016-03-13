package diveengine2d;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {

	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	private GLFWFramebufferSizeCallback frameBufferCallback;
	private GLFWWindowSizeCallback windowSizeCallback;

	private long window;

	private String title;
	private int width, height;
	private boolean vsync, fullscreen, visible, resizable;

	public Window(String title, int width, int height, boolean vsync, boolean fullscreen, boolean visible, boolean resizable) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.vsync = vsync;
		this.fullscreen = fullscreen;
		this.visible = visible;
		this.resizable = resizable;

		init();
	}

	private void init() {
		if (glfwInit() != GL_TRUE) {
			throw new IllegalStateException("Failed to initialize GLFW.");
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, visible ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GL_TRUE : GL_FALSE);

		window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window.");
		}

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

		glfwMakeContextCurrent(window);

		errorCallback = GLFWErrorCallback.createPrint();
		errorCallback.set();

		frameBufferCallback = new GLFWFramebufferSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				glViewport(0, 0, width, height);
			}
		};
		frameBufferCallback.set(window);

		windowSizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				Window.this.width = width;
				Window.this.height = height;
			}
		};
		windowSizeCallback.set(window);

		glfwSwapInterval(vsync ? 1 : 0);

		GL.createCapabilities();
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	public void dispose() {
		keyCallback.release();
		mouseButtonCallback.release();
		cursorPosCallback.release();
		frameBufferCallback.release();
		windowSizeCallback.release();
		glfwTerminate();
		errorCallback.release();
	}

	public void close() {
		glfwSetWindowShouldClose(window, GL_TRUE);
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(window) == GL_TRUE;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean vsync() {
		return vsync;
	}

	public void setVsync(boolean vsync) {
		this.vsync = vsync;
		glfwSwapInterval(vsync ? 1 : 0);
	}

	public boolean fullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		// TODO
		this.fullscreen = fullscreen;
	}

	public boolean visible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		if (visible) {
			glfwShowWindow(window);
		} else {
			glfwHideWindow(window);
		}
	}

	public boolean resizable() {
		return resizable;
	}

}