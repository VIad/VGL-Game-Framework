package vgl.utils;

import java.util.concurrent.TimeUnit;

public class Timer {

	private TimeUnit	measureUnit;

	private long		last;

	public Timer(TimeUnit measureUnit) {
		if (measureUnit == TimeUnit.DAYS)
			throw new vgl.core.exception.VGLRuntimeException("DAYS is not supported by VGL");
		this.measureUnit = measureUnit;
	}

	public void record() {
		last = System.currentTimeMillis();
	}

	public long elapsed() {
		return measureUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
		        - measureUnit.convert(last, TimeUnit.MILLISECONDS);
	}
}
