package vgl.platform;

import vgl.tools.async.UniContainer;

public interface ILogger {

	void log(Object message, LogLevel logLevel);

	void info(Object message);

	void warn(Object message);

	void error(Object message);

	void critical(Object message);

	void spaceAfterPrefix(int newSpace);

	void changePrefix(LogLevel which, String newPrefix);

	void resetToDefault();
}
