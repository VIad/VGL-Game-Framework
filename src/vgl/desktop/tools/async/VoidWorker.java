package vgl.desktop.tools.async;

import vgl.tools.functional.callback.Callback;

public class VoidWorker<A> {

	final private Callback<A>	process;
	final private A				argument;

	public VoidWorker(final A argument, Callback<A> process) {
		this.argument = argument;
		this.process = process;
	}

	public void start() {
		new Thread(() -> {
			process.invoke(argument);
		}).start();
	}

}
