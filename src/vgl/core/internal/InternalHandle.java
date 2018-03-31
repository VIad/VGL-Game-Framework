package vgl.core.internal;

import java.util.LinkedList;
import java.util.Queue;

//TODO
public class InternalHandle {

	private final Queue<Runnable>		onUpdateQueue;

	private static final InternalHandle	handle	= new InternalHandle();

	private InternalHandle() {
		onUpdateQueue = new LinkedList<>();
	}

	public static InternalHandle getHandle() {
		return handle;
	}

	void enqueueOnUpdate(final Runnable process) {
		onUpdateQueue.offer(process);
	}

	Queue<Runnable> getOnUpdateQueue() {
		return onUpdateQueue;
	}

}
