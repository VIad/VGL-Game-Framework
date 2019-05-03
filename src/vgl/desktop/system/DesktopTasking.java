package vgl.desktop.system;

import java.util.Timer;
import java.util.TimerTask;

import vgl.core.internal.ProcessManager;
import vgl.core.internal.Tasking;
import vgl.desktop.DesktopSpecific;

public class DesktopTasking implements Tasking {

	@Override
	public void runLater(Runnable runnable, int ms) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				runnable.run();
				t.cancel();
			}
		}, ms);
	}

	@Override
	public void runNow(Runnable runnable, boolean sync) {
		if (sync)
			runnable.run();
		else
			DesktopSpecific.Tasking.THREAD_POOL.execute(runnable);
	}

	@Override
	public void runLater(Runnable runnable, int ms, When when) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if(when == Tasking.When.NEXT_RENDER) {
					ProcessManager.get()
								  .runNextRender(runnable);
				}
				if(when == Tasking.When.NEXT_UPDATE) {
					ProcessManager.get()
								  .runNextUpdate(runnable);
				}
				t.cancel();
			}
		}, ms);
	}

}
