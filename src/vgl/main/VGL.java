package vgl.main;

import vgl.platform.Application;
import vgl.platform.Display;
import vgl.platform.IFactory;
import vgl.platform.GraphicsPlatform;
import vgl.platform.ILogger;
import vgl.platform.input.IPlatformInputDevice;
import vgl.platform.io.IOSystem;

public class VGL {

	/**
	 * Factory Used for creating essential vgl objects (Multiplatform)
	 */
	public static IFactory				factory;
	/**
	 * GFXPlatform Platform independent OpenGL calls
	 */
	public static GraphicsPlatform		api_gfx;

	public static Application			app;

	public static Display				display;

	public static IOSystem				io;

	public static IPlatformInputDevice	input;

	public static ILogger				logger;

}
