package vgl;

import vgl.core.annotation.VGLInternal;

@VGLInternal
public class VGLInitializator {

	/**
	 * Method called internally from VGL
	 */
	public static void __init() {
		vgl.natives.VGLNative.__init();
	}

}
