package vgl.desktop.utils;

import vgl.platform.LogLevel;
import vgl.platform.logging.ILogger;

public class DesktopLogger implements ILogger {

	private static boolean	def_ostream_used	= true;
	private String			INFO_PREFIX;
	private String			WARN_PREFIX;
	private String			ERROR_PREFIX;
	private String			CRITICAL_PREFIX;
	private String			indent;	

	
	private String name;
	
	public DesktopLogger(String name) {
		this.name = name;
		INFO_PREFIX =      "["+name+"::INFO]    ";
		WARN_PREFIX =      "["+name+"::WARN]    ";
		ERROR_PREFIX =     "["+name+"::ERROR]   ";
		CRITICAL_PREFIX =  "["+name+"::CRITICAL]";
		indent = "  ";
	}
	
	public DesktopLogger() {
		this("VGL");
	}

	@Override
	public void log(Object message, LogLevel logLevel) {
		switch (logLevel)
		{
			case INFO:
				if (!def_ostream_used)
					System.out.println("\n" + INFO_PREFIX + indent + message);
				else
					System.out.println(INFO_PREFIX + indent + message);
				def_ostream_used = true;
				break;
			case WARN:
				if (!def_ostream_used)
					System.out.println("\n" + WARN_PREFIX + indent + message);
				else
					System.out.println(WARN_PREFIX + indent + message);
				def_ostream_used = true;
				break;
			case ERROR:
				if (def_ostream_used)
					System.err.println("\n" + ERROR_PREFIX + indent + message);
				else
					System.err.println(ERROR_PREFIX + indent + message);
				def_ostream_used = false;
				break;
			case CRITICAL:
				if (def_ostream_used)
					System.err.println("\n" + CRITICAL_PREFIX + indent + message);
				else
					System.err.println(CRITICAL_PREFIX + indent + message);
				def_ostream_used = false;
				break;
			default:
				break;
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
		INFO_PREFIX = "[VGL::INFO]    ";
		WARN_PREFIX = "[VGL::WARN]    ";
		ERROR_PREFIX = "[VGL::ERROR]   ";
		CRITICAL_PREFIX = "[VGL::CRITICAL]";
		indent = "  ";
	}

	@Override
	public void setInternalLoggingPolicy(ILogger.InternalLogPolicy internalLogPolicy) {
		// TODO Auto-generated method stub
		
	}
}
