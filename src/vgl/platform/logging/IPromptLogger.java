package vgl.platform.logging;

import java.util.function.Supplier;

/**
 * Prompt logger, should only be used in critical situations,
 * pops a box on the screen and halts execution until acknowledged
 */
public interface IPromptLogger {

	void promptMessage(String message);
	
	void promptError(Supplier<StackTraceElement[]> error);
	
}
