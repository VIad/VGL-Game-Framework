package vgl.core.internal;

import vgl.core.annotation.VGLInternal;
import vgl.platform.Platform;

public class GlobalDetails {

	private static Platform					platform;
	private static vgl.main.Application	application;


	/**
	 * 
	 * @return the current runtime application object<br>
	 *         <strong> DO NOT CALL METHODS OR MODIFY VALUES, METHOD MEANT AS
	 *         READ-ONLY <strong>
	 */
	public static vgl.main.Application getApplication() {
		return application;
	}

	public static Platform getPlatform() {
		if (platform == null)
			throw new vgl.core.exception.VGLFatalError("Platform >> null [no valid runtime context for VGL was found]");
		return platform;
	}

	/**
	 * METHOD MEANT FOR INTERNAL VGL CALLS, CALLS FROM ELSEWHERE WILL LIKELY RESULT
	 * IN A FAILIURE / POOR PERFORMANCE
	 * 
	 * @param application
	 */
	@VGLInternal
	public static void set(vgl.main.Application application) {
		GlobalDetails.application = application;
	}

	/**
	 * METHOD MEANT FOR INTERNAL VGL CALLS, CALLS FROM ELSEWHERE WILL LIKELY RESULT
	 * IN A FAILIURE / POOR PERFORMANCE
	 * 
	 * @param platform
	 */
	@VGLInternal
	public static void set(Platform platform) {
		GlobalDetails.platform = platform;
	}

}
