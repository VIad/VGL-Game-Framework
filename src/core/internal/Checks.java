package vgl.core.internal;

import vgl.core.annotation.VGLInternal;

public class Checks {

	private static boolean	GL_INIT	= false;
	private static boolean	init	= false;

	@VGLInternal
	public static void __setglinit(boolean gL_INIT) {
		GL_INIT = gL_INIT;
	}

	@VGLInternal
	public static void checkIfInitialized() {
		if (!GL_INIT)
			throw new vgl.core.exception.VGLFatalError(
			        "Unable to find an Application object >> create an instance of either VGLApplication or VGLWebApplication");
	}

}
