package vgl.platform.input;

public interface IPlatformInputDevice {

	boolean isKeyDown(int keyCode);
	
	boolean isMouseButtonDown(int mouseButton);
	
	float getX();
	
	float getY();
	
	float getDeltaX();
	
	float getDeltaY();
	
	void setMouseInputMode(int mInputMode);
}
