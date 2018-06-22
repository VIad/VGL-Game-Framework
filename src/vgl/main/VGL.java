package vgl.main;

import vgl.platform.Display;
import vgl.platform.Factory;
import vgl.platform.GraphicsPlatform;
import vgl.platform.ILogger;
import vgl.platform.io.IOSystem;

public class VGL {

	/**
	 * Factory Used for creating essential vgl objects (Multiplatform)
	 */
	public static Factory			factory;
	/**
	 * GFXPlatform Platform independent OpenGL calls
	 */
	public static GraphicsPlatform	api;

	public static Display			display;
	public static IOSystem			io;

	public static ILogger			logger;

}
