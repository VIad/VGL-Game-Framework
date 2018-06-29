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

import vgl.desktop.Window;
import vgl.main.VGL;
import vgl.platform.input.IPlatformInputDevice;

public class DesktopInputSystem implements IPlatformInputDevice {

	private float	dx, dy, x, y;
	private boolean	leftButtonDown, rightButtonDown;
	private float	mouseWheelDelta;

	public DesktopInputSystem() {

	}

	public void onGlfwInit() {
		Keyboard.create();
		glfwSetCursorPosCallback(((Window) VGL.display).__nativePtr(), (window, x, y) -> {
			dx = (float) (x - x);
			dy = (float) (y - y);
			x = (float) x;
			y = (float) y;
		});
		glfwSetMouseButtonCallback(((Window) VGL.display).__nativePtr(), (window, button, action, mods) -> {
			if (button == GLFW_MOUSE_BUTTON_LEFT)
				leftButtonDown = action == GLFW_PRESS;
			if (button == GLFW_MOUSE_BUTTON_RIGHT)
				rightButtonDown = action == GLFW_PRESS;
		});
		glfwSetScrollCallback(((Window) VGL.display).__nativePtr(), (window, xOffset, yOffset) -> {
			mouseWheelDelta = (float) yOffset;
		});
	}

	@Override
	public boolean isKeyDown(int keyCode) {
		return Keyboard.isKeyDown(keyCode);
	}

	@Override
	public boolean isMouseButtonDown(int mouseButton) {
		return false;
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
			glfwSetInputMode(((Window)VGL.display).__nativePtr(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
			return;
		}
		if (mInputMode == CURSOR_HIDDEN_INPUT_MODE) {
			glfwSetInputMode(((Window)VGL.display).__nativePtr(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
			return;
		}
		if (mInputMode == MOVEMENT_INPUT_MODE)
			glfwSetInputMode(((Window)VGL.display).__nativePtr(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}

	@Override
	public void updateDeltas() {
		dx = dy = 0;
		mouseWheelDelta = 0;
	}

	@Override
	public float getMouseWheelDelta() {
		return mouseWheelDelta;
	}

}
