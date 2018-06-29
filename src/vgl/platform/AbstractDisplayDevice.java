package vgl.platform;

import vgl.core.gfx.Color;
import vgl.main.VGL;

abstract public class AbstractDisplayDevice {
	
	protected int		width, height;
	
	private Color clearColor;
	
	protected String title;
	private boolean displayFps;
	protected boolean vsync, fullscreen;
	protected boolean resizable;

	public AbstractDisplayDevice(String title ,int width, int height, boolean vsync, boolean fullscreen) {
		this.width = width;
		this.height = height;
		this.clearColor = Color.BLACK;
		this.displayFps = false;
		this.vsync = vsync;
		this.title = title;
		this.fullscreen = fullscreen;
	}
	
	public boolean isDisplayingFps() {
		return displayFps;
	}
	
	public void setDisplayFps(boolean displayFps) {
		this.displayFps = displayFps;
	}
	
	abstract public void resize(int width, int height);

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Color getClearColor() {
		return clearColor;
	}
	
	public void setClearColor(Color color) {
		if (color == null)
			this.clearColor = Color.BLACK;
		else
			this.clearColor = color;
		VGL.api_gfx.glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
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
