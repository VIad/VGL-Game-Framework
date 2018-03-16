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
	//
	// public static void runNextUpdate(final Runnable runnable) {
	// InternalHandle.getHandle().enqueueProcess(runnable, true);
	// }
	//
	// public static void enqueue(final Runnable runnable) {
	// InternalHandle.getHandle().enqueueProcess(runnable, false);
	// }
	//
	// public static void runOnUpdate() {
	// final Queue<Runnable> taskQueue =
	// InternalHandle.getHandle().getOnUpdateQueue();
	// for (Runnable runnable : taskQueue) {
	// runnable.run();
	// }
	// }

}
