package vgl.natives;

import java.io.IOException;

import vgl.core.annotation.VGLInternal;

@VGLInternal
public class VGLNative {

	static {
		boolean __OS_ARCHITECTURE_X64 = Integer.valueOf(System.getProperty("sun.arch.data.model")) == 64;
		try {
			if (__OS_ARCHITECTURE_X64)
				NativeUtils.loadLibraryFromJar("/vgl/natives/vgl_natives.dll");
			else
				NativeUtils.loadLibraryFromJar("/vgl/natives/x86/vgl_natives.dll");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@VGLInternal
	public static void __init() {

	}

}
