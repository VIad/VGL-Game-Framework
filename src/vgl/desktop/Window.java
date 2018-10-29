package vgl.desktop;

import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CROSSHAIR_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;
import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import vgl.core.annotation.VGLInternal;
import vgl.core.gfx.Color;
import vgl.core.gfx.Image;
import vgl.core.input.Cursor;
import vgl.maths.vector.Vector2i;
import vgl.platform.AbstractDisplayDevice;
import vgl.platform.io.FileDetails;

public class Window extends AbstractDisplayDevice {

	final static private long	NULL	= 0;

	private long		__ptr;

	private boolean		loggingFPS;

	private Vector2i	position;

	// Window(final String title, final int width, final int height, final boolean
	// verticalSynchronization,
	// final boolean fullscreen) {
	// Window.height = height;
	// Window.width = width;
	// Window.title = title;
	// Window.verticalSync = verticalSynchronization;
	// Window.resizable = false;
	// Window.fullscreen = fullscreen;
	// final GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
	// glfwWindowHint(GLFW.GLFW_RED_BITS, vidMode.redBits());
	// glfwWindowHint(GLFW.GLFW_GREEN_BITS, vidMode.greenBits());
	// glfwWindowHint(GLFW.GLFW_BLUE_BITS, vidMode.blueBits());
	// if (verticalSynchronization)
	// glfwWindowHint(GLFW.GLFW_REFRESH_RATE, vidMode.refreshRate());
	// glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
	// glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
	// }

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
		
		this.position = new Vector2i(200, 200);

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
		setPosition(position.x, position.y);
		VGLPrivateUtils.__wresizeCallback(__nativePtr());
		DesktopSpecific.Display.initListeners();
		focused = true;
//		VGL.display.setClearColor(Color.BLACK);
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

	public void setPosition(int x, int y) {
		position.x = x;
		position.y = y;
		if (__ptr != NULL) {			
			GLFW.glfwSetWindowPos(__ptr, x, y);
		}
	}

	/**
	 * To avoid cursor memory leak
	 */
	private long lastCursorPtr = NULL;

	/**
	 * Add support for hidden
	 */
	@Override
	public void setCursor(Cursor cursor) {
		if (lastCursorPtr != NULL)
			glfwDestroyCursor(lastCursorPtr);
		switch (cursor)
		{
			case ARROW:
				glfwSetCursor(__ptr, lastCursorPtr = glfwCreateStandardCursor(GLFW_ARROW_CURSOR));
				break;
			case CROSSHAIR:
				glfwSetCursor(__ptr, lastCursorPtr = glfwCreateStandardCursor(GLFW_CROSSHAIR_CURSOR));
				break;
			case HIDDEN:
//				GLFW.glfwSetInputMode(__ptr, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
				break;
			case DEFAULT:
				glfwSetCursor(__ptr, lastCursorPtr = glfwCreateStandardCursor(GLFW_ARROW_CURSOR));
				break;
			case HAND:
				glfwSetCursor(__ptr, lastCursorPtr = glfwCreateStandardCursor(GLFW_HAND_CURSOR));
				break;
			default:
				break;

		}
	}

	@Override
	public void setCustomCursor(FileDetails cursor) {
		if(lastCursorPtr != NULL)
			glfwDestroyCursor(lastCursorPtr);
		try {
			glfwSetCursor(__ptr, lastCursorPtr = createCursor(DesktopSpecific.FileLoading.loadImage0(cursor.absolutePath())));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private long createCursor(Image image) {
		try(MemoryStack stack = MemoryStack.stackPush()){
			GLFWImage glfw = GLFWImage.mallocStack(stack);
			
			glfw.width(image.getWidth());
			glfw.height(image.getHeight());
			glfw.pixels((java.nio.ByteBuffer) image.getPixels().getBuffer().nativeBufferDetails().getBuffer());
			return GLFW.glfwCreateCursor(glfw, 0, 0);
		}		
	}

	@Override
	public boolean isFocused() {
		return focused;
	}

}
