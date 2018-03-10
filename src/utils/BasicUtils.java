package vgl.utils;

import java.util.concurrent.TimeUnit;

public class BasicUtils {

	public static void delayThread(final TimeUnit timeUnit, final int ms) {
		try {
			timeUnit.sleep(ms);
		} catch (final Exception e) {}
	}

	public static void delayThread(final int ms) {
		try {
			Thread.sleep(ms);
		} catch (final Exception e) {}
	}

}
