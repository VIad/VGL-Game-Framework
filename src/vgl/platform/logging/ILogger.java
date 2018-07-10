package vgl.platform.logging;

import vgl.platform.LogLevel;

public interface ILogger {
	
	public enum InternalLogPolicy{
		
		LOG_ALWAYS,
		
		LOG_WARNINGS,
		
		LOG_ERRORS,
		
		LOG_NEVER;
		
	}
	
	void setInternalLoggingPolicy(InternalLogPolicy internalLogPolicy);

	void log(Object message, LogLevel logLevel);

	void info(Object message);

	void warn(Object message);

	void error(Object message);

	void critical(Object message);

	void spaceAfterPrefix(int newSpace);

	void changePrefix(LogLevel which, String newPrefix);

	void resetToDefault();
}
