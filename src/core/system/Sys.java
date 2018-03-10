package vgl.core.system;

import java.io.IOException;

public class Sys {

	public static boolean runCommand(String command) {
		try {
			Runtime.getRuntime().exec(command);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static long timeMs() {
		return System.currentTimeMillis();
	}

	public static long timeNano() {
		return System.nanoTime();
	}

}
