package vgl.web.tools;

import vgl.core.internal.ProcessManager;
import vgl.core.internal.Tasking;
import vgl.web.WebSpecific;

public class WebTaskingSystem implements Tasking {

	@Override
	public void runNow(Runnable runnable, boolean sync) {
		if (sync)
			runnable.run();
		else
			WebSpecific.JS.setTimeout(1, runnable);
	}

	@Override
	public void runLater(Runnable runnable, int ms, boolean sync) {
		WebSpecific.JS.setTimeout(ms, sync ? () -> ProcessManager.get().runNextUpdate(runnable) : runnable);
	}

}
