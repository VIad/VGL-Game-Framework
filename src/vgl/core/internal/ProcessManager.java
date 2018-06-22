package vgl.core.internal;

import vgl.core.annotation.VGLInternal;

public class ProcessManager {

	// public static void init() {
	// new Thread(() -> {
	// while (true) {
	// Runnable runnable = InternalHandle.getHandle().getTaskQueue().poll();
	// if (runnable != null)
	// runnable.run();
	// }
	// }).start();
	// }

	private ProcessManager() {

	}

	private final static ProcessManager instance = new ProcessManager();

	public static ProcessManager get() {
		return instance;
	}

	public ProcessManager runNextUpdate(final Runnable runnable) {
		InternalHandle.getHandle().enqueueOnUpdate(runnable);
		return this;
	}

	@VGLInternal
	public void runOnUpdate() {
		InternalHandle.getHandle().getOnUpdateQueue().removeIf((r) -> {
			r.run();
			return true;
		});
	}

}
