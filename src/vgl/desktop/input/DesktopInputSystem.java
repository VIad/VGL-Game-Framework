package vgl.desktop.input;

import vgl.platform.input.IPlatformInputDevice;

public class DesktopInputSystem implements IPlatformInputDevice {

	static {
		Keyboard.create();
		Mouse.create();
	}

	@Override
	public boolean isKeyDown(int keyCode) {
		return Keyboard.isKeyDown(keyCode);
	}

	@Override
	public boolean isMouseButtonDown(int mouseButton) {
		return false;
	}

	@Override
	public float getX() {
		return Mouse.getX();
	}

	@Override
	public float getY() {
		return Mouse.getY();
	}

	@Override
	public float getDeltaX() {
		return Mouse.getDx();
	}

	@Override
	public float getDeltaY() {
		return Mouse.getDy();
	}

	@Override
	public void setMouseInputMode(int mInputMode) {
		Mouse.setInputMode((byte) mInputMode);
	}

}
