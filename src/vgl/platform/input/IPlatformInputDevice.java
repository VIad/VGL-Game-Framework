package vgl.platform.input;

import vgl.core.annotation.VGLInternal;

public interface IPlatformInputDevice {
	
	byte	CURSOR_INPUT_MODE			= 0xf;
	byte	MOVEMENT_INPUT_MODE			= 0xc;
	byte	CURSOR_HIDDEN_INPUT_MODE	= 0xa;

	boolean isKeyDown(int keyCode);
	
	boolean isMouseButtonDown(int mouseButton);
	
	float getMouseX();
	
	float getMouseY();
	
	float getDeltaX();
	
	float getDeltaY();
	
	float getMouseWheelDelta();
	
	void setMouseInputMode(int mInputMode);
	
	@VGLInternal
	void updateDeltas();
}
