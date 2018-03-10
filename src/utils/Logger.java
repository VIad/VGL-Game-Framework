package vgl.utils;

public class Logger {

	private static boolean	def_ostream_used	= false;

	private static String	INFO_PREFIX			= "[VGL::INFO]    ";
	private static String	WARN_PREFIX			= "[VGL::WARN]    ";
	private static String	ERROR_PREFIX		= "[VGL::ERROR]   ";
	private static String	CRITICAL_PREFIX		= "[VGL::CRITICAL]";
	private static String	indent				= "  ";

	private Logger() {
	}

	public static void log(LogLevel level, String message) {
		switch (level)
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

	public static void info(String message) {
		log(LogLevel.INFO, message);
	}

	public static void warn(String message) {
		log(LogLevel.WARN, message);
	}

	public static void error(String message) {
		log(LogLevel.ERROR, message);
	}

	public static void critical(String message) {
		log(LogLevel.CRITICAL, message);
	}

	public static void info(Object message) {
		log(LogLevel.INFO, message.toString());
	}

	public static void warn(Object message) {
		log(LogLevel.WARN, message.toString());
	}

	public static void error(Object message) {
		log(LogLevel.ERROR, message.toString());
	}

	public static void critical(Object message) {
		log(LogLevel.CRITICAL, message.toString());
	}

	public static void changeSpacing(int spaces) {
		StringBuilder newIndent = new StringBuilder();
		for (int i = 0; i < spaces; i++) {
			newIndent.append(' ');
		}
		Logger.indent = newIndent.toString();
	}

	public static void changePrefix(LogLevel which, String newPrefix) {
		if (which == null || newPrefix == null)
			throw new NullPointerException("Null parameter passed to Logger.changePrefix()");
		switch (which)
		{
			default:
				throw new vgl.core.exception.VGLFatalError("Not possible");
			case CRITICAL:
				Logger.CRITICAL_PREFIX = newPrefix;
				break;
			case ERROR:
				Logger.ERROR_PREFIX = newPrefix;
				break;
			case INFO:
				Logger.INFO_PREFIX = newPrefix;
				break;
			case WARN:
				Logger.WARN_PREFIX = newPrefix;
				break;

		}
	}

	public static void resetToDefault() {
		INFO_PREFIX = "[VGL::INFO]    ";
		WARN_PREFIX = "[VGL::WARN]    ";
		ERROR_PREFIX = "[VGL::ERROR]   ";
		CRITICAL_PREFIX = "[VGL::CRITICAL]";
		indent = "  ";
	}
}
