package vgl.core.input;

import vgl.main.VGL;
import vgl.maths.vector.Vector2f;

public class Mouse {

	public static final int	LEFT_BUTTON		= 0;
	public static final int	RIGHT_BUTTON	= 1;
	public static final int	MIDDLE_BUTTON	= 2;

	/**
	 * No instances of the class can exist
	 */
	private Mouse() {
	}

	public static float getX() {
		return VGL.input.getMouseX();
	}

	public static float getY() {
		return VGL.input.getMouseY();
	}
	
	public static Vector2f getPosition() {
		return VGL.input.getMousePosition();
	}
	
	public static Vector2f getDelta() {
		return new Vector2f(VGL.input.getDeltaX(),
				            VGL.input.getDeltaY());
	}

	public static float getDx() {
		return VGL.input.getDeltaX();
	}

	public static float getDy() {
		return VGL.input.getDeltaY();
	}

	public static boolean isLeftButtonDown() {
		return VGL.input.isMouseButtonDown(LEFT_BUTTON);
	}

	public static boolean isRightButtonDown() {
		return VGL.input.isMouseButtonDown(RIGHT_BUTTON);
	}
}
