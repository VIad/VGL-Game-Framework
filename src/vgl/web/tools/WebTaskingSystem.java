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
	public void runLater(Runnable runnable, int ms) {
		WebSpecific.JS.setTimeout(ms, runnable);
	}

	@Override
	public void runLater(Runnable runnable, int ms, When when) {
		WebSpecific.JS.setTimeout(ms, when == Tasking.When.NEXT_UPDATE ? () -> ProcessManager.get().runNextUpdate(runnable) 
																  : () -> ProcessManager.get().runNextRender(runnable));
	}

}
