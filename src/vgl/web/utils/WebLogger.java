package vgl.web.utils;

import java.util.Objects;

import vgl.platform.LogLevel;
import vgl.platform.logging.ILogger;

public class WebLogger implements ILogger {

	private  String	INFO_PREFIX		= "[VGL::INFO]    ";
	private  String	WARN_PREFIX		= "[VGL::WARN]    ";
	private  String	ERROR_PREFIX	= "[VGL::ERROR]   ";
	private  String	CRITICAL_PREFIX	= "[VGL::CRITICAL]";
	private  String	indent			= "  ";

	private String name;
	
	public WebLogger(String name) {
		this.name = name;
		INFO_PREFIX =      "["+name+"::INFO]    ";
		WARN_PREFIX =      "["+name+"::WARN]    ";
		ERROR_PREFIX =     "["+name+"::ERROR]   ";
		CRITICAL_PREFIX =  "["+name+"::CRITICAL]";
		indent = "  ";
	}
	
	public WebLogger() {
		this("VGL");
	}
	
	@Override
	public void log(Object message, LogLevel logLevel) {
		StringBuilder builder = new StringBuilder();
		switch (logLevel)
		{
			case CRITICAL:
				builder.append(CRITICAL_PREFIX).append(indent).append(Objects.toString(message, "null"));
				nJSError(builder.toString());
				break;
			case ERROR:
				builder.append(ERROR_PREFIX).append(indent).append(Objects.toString(message, "null"));
				nJSError(builder.toString());
				break;
			case INFO:
				builder.append(INFO_PREFIX).append(indent).append(Objects.toString(message, "null"));
				nJSInfo(builder.toString());
				break;
			case WARN:
				builder.append(WARN_PREFIX).append(indent).append(Objects.toString(message, "null"));
				nJSWarn(builder.toString());
				break;
			default:
				throw new vgl.core.exception.VGLFatalError("Not Possible");
		}
	}

	@Override
	public void info(Object message) {
		log(message, LogLevel.INFO);
	}

	@Override
	public void warn(Object message) {
		log(message, LogLevel.WARN);
	}

	@Override
	public void error(Object message) {
		log(message, LogLevel.ERROR);
	}

	@Override
	public void critical(Object message) {
		log(message, LogLevel.CRITICAL);
	}

	@Override
	public void spaceAfterPrefix(int newSpace) {
		StringBuilder newIndent = new StringBuilder();
		for (int i = 0; i < newSpace; i++) {
			newIndent.append(' ');
		}
		indent = newIndent.toString();
	}

	@Override
	public void changePrefix(LogLevel which, String newPrefix) {
		if (which == null || newPrefix == null)
			throw new NullPointerException("Null parameter passed to Logger.changePrefix()");
		switch (which)
		{
			default:
				throw new vgl.core.exception.VGLFatalError("Not possible");
			case CRITICAL:
				CRITICAL_PREFIX = newPrefix;
				break;
			case ERROR:
				ERROR_PREFIX = newPrefix;
				break;
			case INFO:
				INFO_PREFIX = newPrefix;
				break;
			case WARN:
				WARN_PREFIX = newPrefix;
				break;
		}
	}

	@Override
	public void resetToDefault() {
		 INFO_PREFIX =      "["+name+"::INFO]    ";
		 WARN_PREFIX =      "["+name+"::WARN]    ";
		 ERROR_PREFIX =     "["+name+"::ERROR]   ";
		 CRITICAL_PREFIX =  "["+name+"::CRITICAL]";
		 indent = "  ";
	}

	private static native void nJSInfo(String message) /*-{
		$wnd.console.log(message);
	}-*/;

	private static native void nJSWarn(String message) /*-{
		$wnd.console.warn(message);
	}-*/;

	private static native void nJSError(String message) /*-{
		$wnd.console.error(message);
	}-*/;

}
