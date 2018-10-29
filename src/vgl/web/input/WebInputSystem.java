package vgl.web.input;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.NativeEvent;

import vgl.core.annotation.VGLInternal;
import vgl.core.exception.VGLRuntimeException;
import vgl.core.input.IPlatformInputDevice;
import vgl.core.input.Mouse;
import vgl.main.VGL;
import vgl.platform.input.PlatformSpecificMapping;
import vgl.web.VGLWebApplication;

public class WebInputSystem implements IPlatformInputDevice{

	private boolean[] keys;
	
	private float x, y, dx, dy;
	
	private float mouseWheelDelta;
	
	private boolean lmbDown, rmbDown, mmbDown;
	
	//Make a mapping between the Key and Mouse buttons used by JS and LWJGL
	public WebInputSystem() {
		PlatformSpecificMapping.init();
		this.keys = new boolean[2000];
		Canvas drawSurface = ((VGLWebApplication) VGL.app).getContext().display();
		
		drawSurface.addKeyUpHandler(e -> {
			keys[e.getNativeKeyCode()] = false;
		});
		drawSurface.addKeyDownHandler(e -> {
			keys[e.getNativeKeyCode()] = true;
		});
		drawSurface.addMouseMoveHandler(e -> {
			WebInputSystem.this.dx = (float) (e.getX() - x);
			WebInputSystem.this.dy = (float) (e.getY() - y);
			WebInputSystem.this.x = e.getX();
			WebInputSystem.this.y = e.getY();
		});
		drawSurface.addMouseWheelHandler(mwh -> {
			WebInputSystem.this.mouseWheelDelta = mwh.getDeltaY();		
		});
		drawSurface.addMouseDownHandler(mdh -> {
			if(mdh.getNativeButton() == NativeEvent.BUTTON_LEFT)
				lmbDown = true;
			if(mdh.getNativeButton() == NativeEvent.BUTTON_RIGHT)
				rmbDown = true;
			if(mdh.getNativeButton() == NativeEvent.BUTTON_MIDDLE)
				mmbDown = true;
		});
		drawSurface.addMouseUpHandler(muh -> {
			if(muh.getNativeButton() == NativeEvent.BUTTON_LEFT)
				lmbDown = false;
			if(muh.getNativeButton() == NativeEvent.BUTTON_RIGHT)
				rmbDown = false;
			if(muh.getNativeButton() == NativeEvent.BUTTON_MIDDLE)
				mmbDown = false;
		});
	}

	@Override
	public boolean isKeyDown(int keyCode) {
		return keys[PlatformSpecificMapping.forKey(keyCode)];
	}

	@Override
	public boolean isMouseButtonDown(int mouseButton) {
		if(mouseButton == Mouse.LEFT_BUTTON) {
			return lmbDown;
		}
		if(mouseButton == Mouse.RIGHT_BUTTON) {
			return rmbDown;
		}
		if(mouseButton == Mouse.MIDDLE_BUTTON) {
			return mmbDown;
		}
		throw new VGLRuntimeException("Unrecognised button >> "+mouseButton + " Use Mouse class");
	}

	@Override
	public float getMouseX() {
		return x;
	}

	@Override
	public float getMouseY() {
		return y;
	}

	@Override
	public float getDeltaX() {
		return dx;
	}

	@Override
	public float getDeltaY() {
		return dy;
	}

	@Override
	public void setMouseInputMode(int mInputMode) {
		
	}

	@Override
	public float getMouseWheelDelta() {
		return mouseWheelDelta;
	}

	@Override
	@VGLInternal
	public void updateDeltas() {
		this.mouseWheelDelta = 0;
		this.dx = 0;
		this.dy = 0;
	}

	@Override
	public boolean isKeyTyped(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}

}
