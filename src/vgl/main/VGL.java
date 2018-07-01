package vgl.main;

import vgl.audio.IAudioPlatform;
import vgl.core.input.IPlatformInputDevice;
import vgl.core.internal.ErrorChannel;
import vgl.platform.AbstractDisplayDevice;
import vgl.platform.IFactory;
import vgl.platform.IGraphicsPlatorm;
import vgl.platform.IPlatformContext;
import vgl.platform.io.IOSystem;
import vgl.platform.logging.ILogger;
import vgl.platform.logging.IPromptLogger;

public class VGL {

	/**
	 * Factory Used for creating essential vgl objects (Multiplatform)
	 */
	public static IFactory				factory;
	/**
	 * Used for platform independent OpenGL calls
	 */
	public static IGraphicsPlatorm		api_gfx;
	/**
	 * Used for platform independent OpenAL calls
	 */
	public static IAudioPlatform		api_afx;

	public static Application			app;

	public static AbstractDisplayDevice	display;

	public static IOSystem				io;

	public static IPlatformInputDevice	input;
	
	public static IPlatformContext      context;

	public static ILogger				logger;

	public static IPromptLogger			promptLogger;

	public static ErrorChannel			errorChannel;

	public final static String			build	= "0.2a";
	// IContext or st.h with pauseLoopMethod

}
