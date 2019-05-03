package vgl.platform;

import vgl.core.annotation.VGLInternal;
import vgl.core.gfx.Color;
import vgl.core.gfx.Image;
import vgl.core.input.Cursor;
import vgl.main.VGL;
import vgl.platform.io.FileDetails;
import vgl.tools.ScreenRecorder;
import vgl.tools.functional.callback.Callback;

abstract public class AbstractDisplayDevice {
	
	protected int		width, height;
	
	private Color clearColor;
	
	protected String title;
	private boolean displayFps;
	protected boolean vsync, fullscreen;
	protected boolean resizable;
	
	protected boolean focused;
	
	int lastFps;

	public AbstractDisplayDevice(String title ,int width, int height, boolean vsync, boolean fullscreen) {
		this.width = width;
		this.height = height;
		this.clearColor = Color.BLACK;
		this.displayFps = false;
		this.vsync = vsync;
		this.title = title;
		this.fullscreen = fullscreen;
		focused = false;
	}
	
	public boolean isDisplayingFps() {
		return displayFps;
	}
	
	public void setDisplayFps(boolean displayFps) {
		this.displayFps = displayFps;
	}
	
	@VGLInternal
	public void _internalSet(String property, Object value) {
		if(property.equals("focused")) {
			this.focused = (Boolean) value;
		}
	}
	
	public void screenshot(Callback<Image> result) {
		ScreenRecorder.captureScreen(result);
	}
	
	abstract public void resize(int width, int height);

	public int getWidth() {
		return width;
	}
	
	public int getFramesPerSecond() {
		return lastFps;
	}

	public int getHeight() {
		return height;
	}
	
	public Color getClearColor() {
		return clearColor;
	}
	
	abstract public void setCursor(Cursor cursor);
	
	abstract public void setCustomCursor(FileDetails cursor);
	
	abstract public boolean isFocused();
	
	public void setClearColor(Color color) {
		if (color == null)
			this.clearColor = Color.BLACK;
		else
			this.clearColor = color;
		VGL.api_gfx.glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
	}

	public String getTitle() {
		return title;
	}

	public boolean isVerticalSyncRequested() {
		return vsync;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public void setVsync(boolean vsync2) {
		this.vsync = vsync2;
	}
}
