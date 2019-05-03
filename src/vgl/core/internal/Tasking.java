package vgl.core.internal;

public interface Tasking {
	
	public static enum When{
		NEXT_RENDER,
		NEXT_UPDATE;
	}

	default void runNow(Runnable runnable) {
		runNow(runnable, false);
	}
	
	void runNow(Runnable runnable, boolean sync);
	
	void runLater(Runnable runnable, int ms);
	
	/**
	 * Like runLater(Runnable, int) but runs SYNCHRONOUS
	 * @param runnable
	 * @param ms
	 * @param when
	 */
	void runLater(Runnable runnable, int ms, Tasking.When when);
	
}
