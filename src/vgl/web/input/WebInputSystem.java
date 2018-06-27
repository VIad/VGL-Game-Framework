package vgl.web.input;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.NativeEvent;

import vgl.main.VGL;
import vgl.platform.input.IPlatformInputDevice;
import vgl.web.VGLWebApplication;

public class WebInputSystem implements IPlatformInputDevice{

	private boolean[] keys;
	
	private float x, y;
	
	//Make a mapping between the Key and Mouse buttons used by JS and LWJGL
	public WebInputSystem() {
		this.keys = new boolean[2000];
		Canvas drawSurface = ((VGLWebApplication) VGL.app).getContext().display();
		drawSurface.addKeyUpHandler(e -> {
			keys[e.getNativeKeyCode()] = false;
		});
		drawSurface.addKeyDownHandler(e -> {
			keys[e.getNativeKeyCode()] = true;
		});
		drawSurface.addMouseMoveHandler(e -> {
			WebInputSystem.this.x = e.getX();
			WebInputSystem.this.y = e.getY();
		});
		drawSurface.addMouseDownHandler(mdh -> {
			
		});
	}

	@Override
	public boolean isKeyDown(int keyCode) {
		return keys[keyCode];
	}

	@Override
	public boolean isMouseButtonDown(int mouseButton) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getDeltaX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getDeltaY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMouseInputMode(int mInputMode) {
		// TODO Auto-generated method stub
		
	}

}
