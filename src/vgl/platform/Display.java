package vgl.platform;

import vgl.core.gfx.Color;
import vgl.main.VGL;

public class Display {
	
	private int		width, height;
	
	private Color clearColor;

	public Display(int width, int height) {
		this.width = width;
		this.height = height;
		this.clearColor = Color.BLACK;
	}

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
		this.clearColor = color;
		VGL.api_gfx.glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
}
