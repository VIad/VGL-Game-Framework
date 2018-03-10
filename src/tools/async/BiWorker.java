package vgl.tools.async;

import vgl.tools.functional.callback.Callback;
import vgl.tools.functional.callback.UniParameterReturnCallback;

public class BiWorker<A, R> {

	final private UniParameterReturnCallback<A, R>	process;
	final private Callback<R>						result;
	final private A									argument;

	public BiWorker(final A argument, UniParameterReturnCallback<A, R> process, Callback<R> result) {
		this.argument = argument;
		this.process = process;
		this.result = result;
	}

	public void start() {
		new Thread(() -> {
			R ret = process.get(argument);
			result.invoke(ret);
		}).start();
	}

}
