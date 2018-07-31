package vgl.tools.functional;

import java.util.function.Supplier;

import vgl.core.annotation.SupportedPlatforms;
import vgl.core.exception.VGLRuntimeException;
import vgl.main.VGL;
import vgl.platform.Platform;
import vgl.tools.functional.callback.BinaryCallback;
import vgl.tools.functional.callback.Callback;

public class Result<T> {

	private Throwable	throwable;
	volatile T			successRes;
	private State		state	= State.PENDING;

	public static enum State {
		FAILED, SUCCEEDED, PENDING
	}

	public Result(BinaryCallback<Callback<T>, Callback<Throwable>> callback) {
		try {
			callback.invoke(this::tryRun, this::reject);
		} catch (Throwable ex) {
			this.reject(ex);
		}
		this.state = State.PENDING;
	}

	private void validate() {
		if (state != State.PENDING)
			throw new VGLRuntimeException("Result already consumed");
	}

	public synchronized void tryRun(T arg) {
		// validate();
//		if (state == State.SUCCEEDED)
//			throw new IllegalStateException("Result was already successful");
//		if (state == State.FAILED)
//			throw new IllegalStateException("Result already failed");
		
		this.successRes = arg;
		if (cb != null)
			cb.invoke(arg);
		this.state = State.SUCCEEDED;
		this.throwable = null;
	}

	public synchronized void supply(Supplier<T> result) {
		tryRun(result.get());
	}

	@SupportedPlatforms(values = { Platform.DESKTOP_X64, Platform.DESKTOP_X86 })
	public T get() {
		return successRes;
	}

	private Callback<T>			cb;
	private Callback<Throwable>	tb;

	public synchronized Result<T> get(Callback<T> callback) {
		if (state == State.PENDING && cb == null)
			this.cb = callback;
		else if (state == State.SUCCEEDED && cb == null)
			callback.invoke(successRes);
		return this;
	}

	public synchronized void orElse(Callback<Throwable> cb) {
		this.tb = cb;
		if (state == State.FAILED)
			cb.invoke(throwable);
	}

	public synchronized void reject(Throwable throwable) {
		validate();
		this.state = State.FAILED;
		this.successRes = null;
		this.throwable = throwable;
		if (tb != null)
			tb.invoke(throwable);
	}

}
