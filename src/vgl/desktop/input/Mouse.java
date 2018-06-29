package vgl.desktop.input;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

import vgl.desktop.DesktopContext;
import vgl.desktop.Window;

public class Mouse {

	private static float		x, y;
	private static float		dx, dy;
	private static boolean		rightButtonDown				= false;
	private static boolean		leftButtonDown				= false;
	private static boolean		init						= false;
	private static float        mouseWheelDelta;

	public static final byte	CURSOR_INPUT_MODE			= 0xf;
	public static final byte	MOVEMENT_INPUT_MODE			= 0xc;
	public static final byte	CURSOR_HIDDEN_INPUT_MODE	= 0xa;
	
	public static final int LEFT_BUTTON = 0;
	public static final int RIGHT_BUTTON = 1;
	public static final int MIDDLE_BUTTON = 2;

	/**
	 * No instances of the class can exist
	 */
	private Mouse() {}

//	public static void create() {
//		if ((DesktopContext.getContext() == null) || (DesktopContext.getWindow() == null))
//			throw new NullPointerException("Context || window >> null");
//		glfwSetCursorPosCallback(Window.__nativePtr(), (window, x, y) -> {
//			Mouse.dx = (float) (x - Mouse.x);
//			Mouse.dy = (float) (y - Mouse.y);
//			Mouse.x = (float) x;
//			Mouse.y = (float) y;
//		});
//		glfwSetMouseButtonCallback(Window.__nativePtr(), (window, button, action, mods) -> {
//			if (button == GLFW_MOUSE_BUTTON_LEFT)
//				leftButtonDown = action == GLFW_PRESS;
//			if (button == GLFW_MOUSE_BUTTON_RIGHT)
//				rightButtonDown = action == GLFW_PRESS;
//		});
//		glfwSetScrollCallback(Window.__nativePtr(), (window, xOffset, yOffset) -> {
//			mouseWheelDelta = (float) yOffset;
//		});
//		init = true;
//	}

//	public static void setInputMode(final byte inputMode) {
//		if (inputMode == CURSOR_INPUT_MODE) {
//			glfwSetInputMode(Window.__nativePtr(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
//			return;
//		}
//		if (inputMode == CURSOR_HIDDEN_INPUT_MODE) {
//			glfwSetInputMode(Window.__nativePtr(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
//
//			return;
//		}
//		if (inputMode == MOVEMENT_INPUT_MODE)
//			glfwSetInputMode(Window.__nativePtr(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
//	}

	public static float getX() {
		if (!init)
			throw new NullPointerException("Mouse >> not initialized [create()]");
		return x;
	}

	public static float getY() {
		if (!init)
			throw new NullPointerException("Mouse >> not initialized [create()]");
		return y;
	}

	public static float getDx() {
		if (!init)
			throw new NullPointerException("Mouse >> not initialized [create()]");
		return dx;
	}

	public static float getDy() {
		if (!init)
			throw new NullPointerException("Mouse >> not initialized [create()]");
		return dy;
	}

	public static boolean isLeftButtonDown() {
		if (!init)
			throw new NullPointerException("Mouse >> not initialized [create()]");
		return leftButtonDown;
	}

	public static boolean isRightButtonDown() {
		if (!init)
			throw new NullPointerException("Mouse >> not initialized [create()]");
		return rightButtonDown;
	}

	public static void update() {
		dx = dy = 0;
	}

	public static boolean isInitialized() {
		return init;
	}
}
