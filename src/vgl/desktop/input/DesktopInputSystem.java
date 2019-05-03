package vgl.desktop.input;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

import java.util.HashMap;
import java.util.Map;

import vgl.core.input.IPlatformInputDevice;
import vgl.core.input.KeyEvent;
import vgl.core.input.Mouse;
import vgl.desktop.Window;
import vgl.main.VGL;

public class DesktopInputSystem implements IPlatformInputDevice {

	private float	dx, dy, x, y;
	private boolean	leftButtonDown, rightButtonDown, mmbDown;
	private float	mouseWheelDelta;

	private static enum KeyState {
		DOWN, TYPED, UP;
	}

	private static Map<Integer, KeyState>	keys;
	private static boolean					init	= false;

	public DesktopInputSystem() {
		
	}

	public void onGlfwInit() {
		init = true;
		keys = new HashMap<>();
		glfwSetCursorPosCallback(((Window) VGL.display).__nativePtr(), (window, x, y) -> {
			dx = (float) (x - this.x);
			dy = (float) (y - this.y);
			this.x = (float) x;
			this.y = (float) y;
		});
		glfwSetMouseButtonCallback(((Window) VGL.display).__nativePtr(), (window, button, action, mods) -> {
			if (button == GLFW_MOUSE_BUTTON_LEFT)
				leftButtonDown = action == GLFW_RELEASE;
			if (button == GLFW_MOUSE_BUTTON_RIGHT)
				rightButtonDown = action == GLFW_RELEASE;
			if (button == GLFW_MOUSE_BUTTON_MIDDLE)
				mmbDown = action != GLFW_RELEASE;

		});
		glfwSetKeyCallback(((Window) VGL.display).__nativePtr(), (window, key, scancode, action, mods) -> {
			if (key > 2000 || key < 0)
				return;
			if(action == GLFW_REPEAT) {
				if (VGL.eventController.shouldDispatchForContext(KeyEvent.class))
					VGL.eventController.fire(new KeyEvent(key, KeyEvent.EventType.DOWN));
			}
			if (!keys.containsKey(key) && action != GLFW_RELEASE) {
				keys.put(key, KeyState.TYPED);
			} else if (keys.containsKey(key) && action == GLFW_RELEASE) {
				keys.remove(key);
				if (VGL.eventController.shouldDispatchForContext(KeyEvent.class))
					VGL.eventController.fire(new KeyEvent(key, KeyEvent.EventType.RELEASED));
			}
		});
		glfwSetScrollCallback(((Window) VGL.display).__nativePtr(), (window, xOffset, yOffset) -> {
			mouseWheelDelta = (float) yOffset;
		});
	}

	@Override
	public boolean isKeyDown(int keyCode) {
		return keys.getOrDefault(keyCode, KeyState.UP) != KeyState.UP;
	}

	@Override
	public boolean isKeyTyped(int keyCode) {
		return keys.getOrDefault(keyCode, KeyState.UP) == KeyState.TYPED;
	}

	@Override
	public boolean isMouseButtonDown(int mouseButton) {
		switch (mouseButton)
		{
			case Mouse.LEFT_BUTTON:
				return leftButtonDown;
			case Mouse.RIGHT_BUTTON:
				return rightButtonDown;
			case Mouse.MIDDLE_BUTTON:
				return mmbDown;
			default:
				return false;
		}
	}

	@Override
	public float getMouseX() {
		return x;
	}

	@Override
	public float getMouseY() {
		return y;
	}

	@Override
	public float getDeltaX() {
		return dx;
	}

	@Override
	public float getDeltaY() {
		return dy;
	}

	@Override
	public void setMouseInputMode(int mInputMode) {
		if (mInputMode == CURSOR_INPUT_MODE) {
			glfwSetInputMode(((Window) VGL.display).__nativePtr(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
			return;
		}
		if (mInputMode == CURSOR_HIDDEN_INPUT_MODE) {
			glfwSetInputMode(((Window) VGL.display).__nativePtr(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
			return;
		}
		if (mInputMode == MOVEMENT_INPUT_MODE)
			glfwSetInputMode(((Window) VGL.display).__nativePtr(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}

	@Override
	public void updateDeltas() {
		dx = dy = 0;
		mouseWheelDelta = 0;
		keys.forEach((key, value) -> {
			if (value == KeyState.TYPED) {
				if (VGL.eventController.shouldDispatchForContext(KeyEvent.class)) {
					VGL.eventController.fire(new KeyEvent(key, KeyEvent.EventType.TYPED));
					VGL.eventController.fire(new KeyEvent(key, KeyEvent.EventType.DOWN));
				}
				keys.put(key, KeyState.DOWN);
			}
		});
	}

	@Override
	public float getMouseWheelDelta() {
		return mouseWheelDelta;
	}

}
