package vgl.core.main;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;

import vgl.core.annotation.VGLInternal;
import vgl.core.gfx.Color;
import vgl.maths.vector.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class Window {

	static private long		NULL	= 0;

	static int				width, height;

	private static String	title;

	private static long		__ptr;

	private static boolean	verticalSync;

	private static boolean	resizable;

	private static boolean	loggingFPS;

	private static boolean	fullscreen;

	Window(final String title, final int width, final int height, final boolean verticalSynchronization,
	        final boolean fullscreen) {
		Window.height = height;
		Window.width = width;
		Window.title = title;
		Window.verticalSync = verticalSynchronization;
		Window.resizable = false;
		Window.fullscreen = fullscreen;
		final GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwWindowHint(GLFW.GLFW_RED_BITS, vidMode.redBits());
		glfwWindowHint(GLFW.GLFW_GREEN_BITS, vidMode.greenBits());
		glfwWindowHint(GLFW.GLFW_BLUE_BITS, vidMode.blueBits());
		if (verticalSynchronization)
			glfwWindowHint(GLFW.GLFW_REFRESH_RATE, vidMode.refreshRate());
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);

	}

	private volatile static boolean updateOnResize = false;

	public static void setResizable(final boolean resizable) {
		Window.resizable = resizable;
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
	}

	public static int getHeight() {
		return height;
	}

	public static int getWidth() {
		return width;
	}

	@VGLInternal
	public static long __ptr() {
		return __ptr;
	}

	public static void setClearColor(int r, int g, int b) {
		setClearColor(new Color(r, g, b));
	}

	public static void setClearColor(float r, float g, float b) {
		GL11.glClearColor(r, g, b, 1f);
	}

	public static void setClearColor(float r, float g, float b, float a) {
		assert Color.isValid(r, g, b, a); //TODO, Do on other instances of setClearColor
		GL11.glClearColor(r, g, b, a);
	}

	public static void setClearColor(Vector4f clearColor) {
		assert Color.isValid(clearColor); //TODO, Do on other instances of setClearColor
		GL11.glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
	}

	public static void setClearColor(Color color) {
		GL11.glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	static void create() {
		__ptr = GLFW.glfwCreateWindow(width, height, title, fullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);
	}

	public static void updateOnResize(final boolean b) {
		updateOnResize = b;
		VGLPrivateUtils.UPDATE_VIEWPORT_ON_RESIZE = b;
		if (!VGLPrivateUtils.__wrc_SET)
			VGLPrivateUtils.__wresizeCallback(__ptr());
	}

	public static boolean isVerticalSyncRequested() {
		return verticalSync;
	}

	public static boolean isResizable() {
		return resizable;
	}

	public static void logFps(final boolean logFPS) {
		Window.loggingFPS = logFPS;
	}

	static boolean isLoggingFPS() {
		return loggingFPS;
	}

	public static void hint(int hint, int value) {
		if (__ptr != NULL)
			glfwWindowHint(hint, value);
	}
}
