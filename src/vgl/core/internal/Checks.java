package vgl.core.internal;

import org.lwjgl.opengl.GL11;

import vgl.core.annotation.VGLInternal;
import vgl.core.exception.OpenGLError;
import vgl.main.VGL;

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
	
	public static void checkGLError() {
		int error = GL11.GL_NO_ERROR;
		while((error = VGL.api_gfx.glGetError()) != GL11.GL_NO_ERROR) {
			VGL.errorChannel
			   .forward(new OpenGLError(error));
		}
		
	}

	public static void checkCanInstantiate(Class<?> clazz) {
//		if (GlobalDetails.getPlatform() == Platform.DESKTOP_X64
//		        || GlobalDetails.getPlatform() == Platform.DESKTOP_X86) {
//			if (clazz == WGLTexture.class)
//				throw new vgl.core.exception.VGLFatalError("Cannot use web texture on desktop context");
//			if (clazz == WGLIndexBuffer.class)
//				throw new vgl.core.exception.VGLFatalError("Cannot use web index buffer on desktop context");
//		} else {
//			if (clazz == Texture.class)
//				throw new vgl.core.exception.VGLFatalError("Cannot use desktop texture on web context");
//			if (clazz == IndexBuffer.class)
//				throw new vgl.core.exception.VGLFatalError("Cannot use desktop indexbuffer on web context");
//			if (clazz == vgl.desktop.gl.Buffer.class) {
//				throw new vgl.core.exception.VGLFatalError("Cannot use desktop buffer on web context");
//			}
//		}
	}

}
