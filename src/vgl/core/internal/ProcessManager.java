package vgl.core.internal;

import java.util.ArrayList;
import java.util.List;

import vgl.core.annotation.VGLInternal;

public class ProcessManager {

	private ProcessManager() {

	}

	private List<Runnable> renderLoops = new ArrayList<>();
	private List<Runnable> updateLoops = new ArrayList<>();
	
	private final static ProcessManager instance = new ProcessManager();

	public static ProcessManager get() {
		return instance;
	}
	
	public ProcessManager registerRenderLoop(Runnable render) {
		this.renderLoops.add(render);
		return this;
	}
	
	public ProcessManager registerUpdateLoop(Runnable update) {
		this.updateLoops.add(update);
		return this;
	}

	public synchronized ProcessManager runNextUpdate(final Runnable runnable) {
		InternalHandle.getHandle().enqueueOnUpdate(runnable);
		return this;
	}

	@VGLInternal
	public synchronized void runOnUpdate() {
		InternalHandle.getHandle().getOnUpdateQueue().removeIf((r) -> {
			r.run();
			return true;
		});
		updateLoops.forEach(Runnable::run);
	}
	
	public void runOnRender() {
		renderLoops.forEach(Runnable::run);
	}

}
