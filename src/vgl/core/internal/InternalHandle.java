package vgl.core.internal;

import java.util.LinkedList;
import java.util.Queue;

//TODO
public class InternalHandle {

	private final Queue<Runnable>		taskQueue;
	private final Queue<Runnable>		onUpdateQueue;

	private static final InternalHandle	handle	= new InternalHandle();

	private InternalHandle() {
		taskQueue = new LinkedList<>();
		onUpdateQueue = new LinkedList<>();
	}

	public static InternalHandle getHandle() {
		return handle;
	}

	void enqueueProcess(final Runnable process, boolean onUpdate) {
		if (onUpdate)
			handle.onUpdateQueue.offer(process);
		else
			handle.taskQueue.offer(process);
	}

	Queue<Runnable> getOnUpdateQueue() {
		return onUpdateQueue;
	}

	Queue<Runnable> getTaskQueue() {
		return taskQueue;
	}

}
