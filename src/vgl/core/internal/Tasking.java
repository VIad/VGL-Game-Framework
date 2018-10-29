package vgl.core.internal;

public interface Tasking {

	default void runNow(Runnable runnable) {
		runNow(runnable, false);
	}
	
	default void runLater(Runnable runnable, int ms) {
		runLater(runnable, ms, false);
	}
	
	void runNow(Runnable runnable, boolean sync);
	
	void runLater(Runnable runnable, int ms, boolean sync);
	
}
