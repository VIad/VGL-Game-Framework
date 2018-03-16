package vgl.desktop;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

class VGLPrivateUtils {

	static boolean	UPDATE_VIEWPORT_ON_RESIZE	= false;

	static boolean __wrc_SET = false;

	static void __wresizeCallback(final long window_ptr) {
		__wrc_SET = true;
		GLFW.glfwSetWindowSizeCallback(window_ptr, (window, newWidth, newHeight) -> {
			if (UPDATE_VIEWPORT_ON_RESIZE) {
				GL11.glViewport(0, 0, newWidth, newHeight);
				Window.width = newWidth;
				Window.height = newHeight;
			}
		});
	}

}
