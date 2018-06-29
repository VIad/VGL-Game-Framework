package vgl.desktop;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.JNI;

import vgl.core.annotation.VGLInternal;
import vgl.core.gfx.Color;
import vgl.maths.vector.Vector4f;
import vgl.platform.AbstractDisplayDevice;

public class Window extends AbstractDisplayDevice{


	

	static private long		NULL	= 0;

	private long		__ptr;

	private boolean	loggingFPS;

//	Window(final String title, final int width, final int height, final boolean verticalSynchronization,
//	        final boolean fullscreen) {
//		Window.height = height;
//		Window.width = width;
//		Window.title = title;
//		Window.verticalSync = verticalSynchronization;
//		Window.resizable = false;
//		Window.fullscreen = fullscreen;
//		final GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//		glfwWindowHint(GLFW.GLFW_RED_BITS, vidMode.redBits());
//		glfwWindowHint(GLFW.GLFW_GREEN_BITS, vidMode.greenBits());
//		glfwWindowHint(GLFW.GLFW_BLUE_BITS, vidMode.blueBits());
//		if (verticalSynchronization)
//			glfwWindowHint(GLFW.GLFW_REFRESH_RATE, vidMode.refreshRate());
//		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
//		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
//	}
	
	public Window(String title, int width, int height, boolean vsync, boolean fullsreen) {
		super(title, width, height, vsync, fullsreen);
		this.resizable = false;
		final GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwWindowHint(GLFW.GLFW_RED_BITS, vidMode.redBits());
		glfwWindowHint(GLFW.GLFW_GREEN_BITS, vidMode.greenBits());
		glfwWindowHint(GLFW.GLFW_BLUE_BITS, vidMode.blueBits());
		if (vsync)
			glfwWindowHint(GLFW.GLFW_REFRESH_RATE, vidMode.refreshRate());
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
	}
	
	private volatile static boolean updateOnResize = false;

	public void setResizable(final boolean resizable) {
		this.resizable = resizable;
		glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GL11.GL_TRUE : GL11.GL_FALSE);
	}

	@VGLInternal
	public long __nativePtr() {
		return __ptr;
	}

	public void setClearColor(int r, int g, int b) {
		super.setClearColor(new Color(r, g, b));
	}

	void create() {
		__ptr = GLFW.glfwCreateWindow(width, height, title, fullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);
		VGLPrivateUtils.__wresizeCallback(__nativePtr());
	}
	
	void internalSet(int w, int h) {
		super.width = w;
		super.height = h;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void hint(int hint, int value) {
		if (__ptr != NULL)
			glfwWindowHint(hint, value);
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		GLFW.glfwSetWindowSize(__ptr, width, height);
	}

}
