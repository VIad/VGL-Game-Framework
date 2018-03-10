package vgl.core.main;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

import vgl.core.buffers.VertexArray;
import vgl.core.exception.VGLException;
import vgl.core.internal.Checks;
import vgl.core.internal.ProcessManager;
import vgl.core.main.input.Mouse;
import vgl.natives.NativeUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class CoreContext {

	private static final String					version	= "0.1";

	private static CoreContext					context;

	private volatile static GameLoop			loop;

	private volatile static PreLoopInitializer	initializer;

	private volatile static PostLoopCleaner		cleaner;

	private static VGLApplication				application;

	private static Window						w;

	/**
	 * User of the API should not access the constructor
	 */
	private CoreContext() {
	}

	public static void createContext(VGLApplication application) {
		if (!glfwInit())
			throw new Error("Unable to init GLFW >> Shutting down");
		GLFWErrorCallback.createPrint(System.err).set();
		glfwDefaultWindowHints();
		context = new CoreContext();
		CoreContext.application = application;
	}

	/**
	 *
	 * @param title
	 * @param width
	 * @param height
	 * @param vsync
	 * @param fullscreen
	 */
	public static void createWindow(final String title, final int width, final int height, final boolean vsync,
	        final boolean fullscreen) {
		if (context == null)
			throw new NullPointerException("Context >> null");
		w = new Window(title, width, height, vsync, fullscreen);
	}

	/**
	 *
	 * @param title
	 * @param width
	 * @param height
	 * @param vsync
	 */
	public static void createWindow(final String title, final int width, final int height, final boolean vsync) {
		CoreContext.createWindow(title, width, height, vsync, false);
	}

	/**
	 *
	 * @param title
	 * @param width
	 * @param height
	 */
	public static void createWindow(final String title, final int width, final int height) {
		CoreContext.createWindow(title, width, height, false, false);
	}

	public synchronized static void preLoop(final PreLoopInitializer initializer) {
		CoreContext.initializer = initializer;
	}

	public static void initGL() {
		Checks.__setglinit(true);
		Window.create();
		glfwMakeContextCurrent(Window.__ptr());
		glfwShowWindow(Window.__ptr());
		GL.createCapabilities();
		if (Window.isVerticalSyncRequested())
			glfwSwapInterval(1);
		else
			glfwSwapInterval(0);
		System.out.println("VGL " + version + " | OpenGL " + glGetString(GL_VERSION));
	}

	private static boolean f = true;

	public static void startLoop() {
		Checks.checkIfInitialized();
		GLFW.glfwSetWindowSize(Window.__ptr(), 1920, 1080);
		if (f) {
			f = false;
			GLFW.glfwSetWindowSize(Window.__ptr(), application.getWindowWidth(), application.getWindowHeight());
		}
		long last = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0d / (double) application.getRequestedUPS();
		double delta = 0d;
		int fps = 0;
		int ups = 0;
		while (!glfwWindowShouldClose(Window.__ptr())) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				try {
//					ProcessManager.runAll();
					application.update();
					ups++;
				} catch (VGLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				delta--;
			}
			try {
				application.render();
			} catch (VGLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			fps++;
			if ((System.currentTimeMillis() - last) > 1000) {
				last += 1000;
				if (Window.isLoggingFPS())
					System.out.println("FPS : " + fps + ", UPS : " + ups);
				fps = 0;
				ups = 0;
				try {
					application.fixedUpdate();
				} catch (VGLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (Mouse.isInitialized())
				Mouse.update();

			// glFinish();
			// glFlush();
			glfwSwapBuffers(Window.__ptr());
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glfwPollEvents();
		}
		VertexArray.freeBuffers();
		NativeUtils.clearOnFinish();
		try {
			application.finish();
		} catch (VGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GLFW.glfwDestroyWindow(Window.__ptr());
	}

	public synchronized static void postLoop(final PostLoopCleaner cleaner) {
		CoreContext.cleaner = cleaner;
	}

	public static CoreContext getContext() {
		return context;
	}

	public static Window getWindow() {
		if (context == null)
			throw new NullPointerException("Context >> null");
		return w;
	}

	public static synchronized void openLoop(final GameLoop loop) {
		System.out.println("Context loop >> open");
		CoreContext.loop = loop;
	}
}
