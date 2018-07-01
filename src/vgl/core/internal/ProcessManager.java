package vgl.core.internal;

import vgl.core.annotation.VGLInternal;
import vgl.main.VGL;

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

	public synchronized ProcessManager runNextUpdate(final Runnable runnable) {
		System.out.println("here ");
		InternalHandle.getHandle().enqueueOnUpdate(runnable);
		return this;
	}

	@VGLInternal
	public synchronized void runOnUpdate() {
		InternalHandle.getHandle().getOnUpdateQueue().removeIf((r) -> {
			r.run();
			return true;
		});
	}

}
