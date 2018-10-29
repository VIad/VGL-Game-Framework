package vgl.desktop.system;

import java.util.Timer;
import java.util.TimerTask;

import vgl.core.internal.ProcessManager;
import vgl.core.internal.Tasking;
import vgl.desktop.DesktopSpecific;

public class DesktopTasking implements Tasking{

	@Override
	public void runLater(Runnable runnable, int ms, boolean sync) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if(sync)
					ProcessManager.get()
					              .runNextUpdate(runnable);
				else
					runnable.run();
				t.cancel();
			}
		}, ms);
	}

	@Override
	public void runNow(Runnable runnable, boolean sync) {
		if(sync)
			runnable.run();
		else
			DesktopSpecific.Tasking
			               .THREAD_POOL.execute(runnable);
	}

}
