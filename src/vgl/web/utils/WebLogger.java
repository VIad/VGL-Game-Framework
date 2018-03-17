package vgl.web.utils;

import vgl.platform.ILogger;
import vgl.platform.LogLevel;

public class WebLogger implements ILogger {

	private static String	INFO_PREFIX		= "[VGL::INFO]    ";
	private static String	WARN_PREFIX		= "[VGL::WARN]    ";
	private static String	ERROR_PREFIX	= "[VGL::ERROR]   ";
	private static String	CRITICAL_PREFIX	= "[VGL::CRITICAL]";
	private static String	indent			= "  ";

	@Override
	public void log(Object message, LogLevel logLevel) {
		switch (logLevel)
		{
			case CRITICAL:
				nJSError(CRITICAL_PREFIX, message.toString());
				break;
			case ERROR:
				nJSError(ERROR_PREFIX, message.toString());
				break;
			case INFO:
				nJSInfo(INFO_PREFIX, message.toString());
				break;
			case WARN:
				nJSWarn(WARN_PREFIX, message.toString());
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
		WebLogger.indent = newIndent.toString();
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
				WebLogger.CRITICAL_PREFIX = newPrefix;
				break;
			case ERROR:
				WebLogger.ERROR_PREFIX = newPrefix;
				break;
			case INFO:
				WebLogger.INFO_PREFIX = newPrefix;
				break;
			case WARN:
				WebLogger.WARN_PREFIX = newPrefix;
				break;

		}
	}

	@Override
	public void resetToDefault() {
		INFO_PREFIX = "[VGL::INFO]    ";
		WARN_PREFIX = "[VGL::WARN]    ";
		ERROR_PREFIX = "[VGL::ERROR]   ";
		CRITICAL_PREFIX = "[VGL::CRITICAL]";
		indent = "  ";
	}

	private static native void nJSInfo(String prefix, String message) /*-{
		$wnd.console.log(prefix + indent + +message);
	}-*/;

	private static native void nJSWarn(String prefix, String message) /*-{
		$wnd.console.warn(prefix + indent + message);
	}-*/;

	private static native void nJSError(String prefix, String message) /*-{
		$wnd.console.error(prefix + indent + +message);
	}-*/;

}
