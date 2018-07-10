package vgl.desktop.input;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

import org.lwjgl.glfw.GLFW;

import vgl.desktop.DesktopContext;
import vgl.desktop.Window;
import vgl.main.VGL;

public class Keyboard {

	private static boolean[]	keys;
	private static boolean		init	= false;

	/**
	 * No instances of the class can exist
	 */
	private Keyboard() {
	}

	/**
	 * Initializes the keyboard and does the required checks Call method before
	 * using
	 */
	public static void create() {
		init = true;
		keys = new boolean[2000];
		glfwSetKeyCallback(((Window)VGL.display).__nativePtr(), (window, key, scancode, action, mods) -> {
			if (key > keys.length || key < 0)
				return;
			keys[key] = action != GLFW_RELEASE;
		});
	}

	/**
	 * Using GLFW codes
	 * 
	 * @param keyCode
	 *            - the keyCode in GLFW
	 * @return t / f
	 */
	public static boolean isKeyDown(final int keyCode) {
		if (!init)
			throw new NullPointerException("Keyboard >> not initialized [create()]");
		return keys[keyCode];
	}

	public static boolean isInitialized() {
		return init;
	}

}
