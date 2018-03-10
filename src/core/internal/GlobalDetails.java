package vgl.core.internal;

import vgl.platform.Platform;

public class GlobalDetails {

	public static void set(Platform plat) {
		GlobalDetails.platform = plat;
	}

	private static Platform platform;

	public static int osArchitectueBits() {
		return platform == Platform.DESKTOP_X64 ? 64 : 32;
	}

	public static Platform getPlatform() {
		if (platform == null)
			throw new vgl.core.exception.VGLFatalError("Platform >> null [no valid runtime context for VGL was found]");
		return platform;
	}

}
