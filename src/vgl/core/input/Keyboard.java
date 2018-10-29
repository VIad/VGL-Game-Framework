package vgl.core.input;

import vgl.main.VGL;

public class Keyboard {

	private Keyboard() {
	}
	
	public static boolean isKeyDown(final int keyCode) {
		return VGL.input.isKeyDown(keyCode);
	}

	public static boolean isKeyTyped(final int keyCode) {
		return VGL.input.isKeyTyped(keyCode);
	}
	
}
