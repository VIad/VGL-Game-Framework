package vgl.main;

import vgl.core.StateController;
import vgl.core.audio.IAudioPlatform;
import vgl.core.events.EventFeedback;
import vgl.core.input.IPlatformInputDevice;
import vgl.core.internal.ErrorChannel;
import vgl.core.net.Net;
import vgl.platform.AbstractDisplayDevice;
import vgl.platform.IFactory;
import vgl.platform.IGraphicsPlatorm;
import vgl.platform.IPlatformContext;
import vgl.platform.io.Files;
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
	/**
	 * The application object, meant as read onlu
	 */
	public static Application			app;
	/**
	 * Reference of the platform independent display device
	 */
	public static AbstractDisplayDevice	display;
	/**
	 * Methods for receiving cross-platform file I/O and other utilities
	 */
	public static IOSystem				io;
	/**
	 * Framework for doing any kind of http requests
	 */
	public static Net                   net;

	public static StateController       states;
	
	public static IPlatformInputDevice	input;
	
	public static IPlatformContext      context;

	public static ILogger				logger;

	public static IPromptLogger			promptLogger;
	
	public static EventFeedback         eventController;

	public static Files                 files;
	
	public static ErrorChannel			errorChannel;
	public final static String			build	= "0.2a";

}
