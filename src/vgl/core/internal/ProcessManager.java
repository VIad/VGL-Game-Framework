package vgl.core.internal;

import java.util.ArrayList;
import java.util.List;

import vgl.core.annotation.VGLInternal;
import vgl.main.VGL;

public class ProcessManager {

	private ProcessManager() {

	}
	
	private vgl.core.internal.Tasking tasking;

	private List<Runnable> renderLoops = new ArrayList<>();
	private List<Runnable> updateLoops = new ArrayList<>();
	
	private final static ProcessManager instance = new ProcessManager();

	public static ProcessManager get() {
		/**
		 * Lazy init for web platform
		 */
		if(instance.tasking == null)
			instance.tasking = VGL.factory.internalFrameworkFactory()
			                              .platformSpecificTaskingSystem();
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
	
	public ProcessManager runNow(Runnable runnable, boolean sync) {
		tasking.runNow(runnable, sync);
		return this;
	}
	
	public ProcessManager runLater(Runnable runnable, int ms, boolean sync) {
		tasking.runLater(runnable, ms, sync);
		return this;
	}

	public synchronized ProcessManager runNextUpdate(final Runnable runnable) {
		InternalHandle.getHandle().enqueueOnUpdate(runnable);
		return this;
	}
	
	@VGLInternal
	public synchronized void runOnUpdate() {
		InternalHandle.getHandle().getOnUpdateQueue().removeIf((task) -> {
			task.run();
			return true;
		});
		updateLoops.forEach(Runnable::run);
	}
	
	public void runOnRender() {
		renderLoops.forEach(Runnable::run);
	}

}
