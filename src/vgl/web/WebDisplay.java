package vgl.web;

import vgl.main.VGL;
import vgl.platform.AbstractDisplayDevice;

public class WebDisplay extends AbstractDisplayDevice {

	public WebDisplay(String title, int width, int height, boolean vsync, boolean fullscreen) {
		super(title, width, height, vsync, fullscreen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void resize(int width, int height) {
//		this.nResize(width, height, ((VGLWebApplication) VGL.app).renderTargetID);
	}

	private native void nResize(int newWidth, int newHeight, String canvasID);/*-{
		var canvas = document.getElementById(canvasID);
		canvas.width = newWidth;
		canvas.height = newHeight;
	}-*/
	
}
