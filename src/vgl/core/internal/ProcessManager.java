package vgl.core.internal;

import java.util.ArrayList;
import java.util.List;

import vgl.core.annotation.VGLInternal;
import vgl.core.internal.Tasking.When;
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
		if (instance.tasking == null)
			instance.tasking = VGL.factory.internalFrameworkFactory().platformSpecificTaskingSystem();
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

	public ProcessManager runLater(Runnable runnable, int ms) {
		tasking.runLater(runnable, ms);
		return this;
	}

	public ProcessManager runLater(Runnable runnable, int ms, When when) {
		tasking.runLater(runnable, ms, when);
		return this;
	}

	public synchronized ProcessManager runNextUpdate(final Runnable runnable) {
		InternalHandle.getHandle().enqueueOnUpdate(runnable);
		return this;
	}

	public synchronized ProcessManager runNextRender(final Runnable runnable) {
		InternalHandle.getHandle().enqueueOnUpdate(runnable);
		return this;
	}

	@VGLInternal
	synchronized public void runOnUpdate() {
		InternalHandle.getHandle().getOnUpdateQueue().removeIf((task) -> {
			task.run();
			return true;
		});
		updateLoops.forEach(Runnable::run);
	}

	@VGLInternal
	synchronized public void runOnRender() {
		renderLoops.forEach(Runnable::run);
		InternalHandle.getHandle().getOnRenderQueue().removeIf((task) -> {
			task.run();
			return true;
		});
	}

}
