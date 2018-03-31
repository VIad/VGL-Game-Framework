package vgl.core.internal;

import java.util.Queue;

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

	public static void runNextUpdate(final Runnable runnable) {
		InternalHandle.getHandle().enqueueOnUpdate(runnable);
	}

	public static void runOnUpdate() {
		InternalHandle.getHandle().getOnUpdateQueue().removeIf((r) -> {
			r.run();
			return true;
		});
	}

}
