package vgl.web;

import com.google.gwt.canvas.client.Canvas;

import vgl.core.input.Cursor;
import vgl.main.VGL;
import vgl.platform.AbstractDisplayDevice;
import vgl.platform.io.FileDetails;

public class WebDisplay extends AbstractDisplayDevice {

	public WebDisplay(String title, int width, int height, boolean vsync, boolean fullscreen) {
		super(title, width, height, vsync, fullscreen);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void setCursor(Cursor cursor) {
		Canvas drawSurface = ((VGLWebApplication) VGL.app).getContext().display();
		switch(cursor) {
			case ARROW:
				drawSurface.getElement().getStyle().setCursor(com.google.gwt.dom.client.Style.Cursor.DEFAULT);
				break;
			case CROSSHAIR:
				drawSurface.getElement().getStyle().setCursor(com.google.gwt.dom.client.Style.Cursor.CROSSHAIR);
				break;
			case DEFAULT:
				drawSurface.getElement().getStyle().setCursor(com.google.gwt.dom.client.Style.Cursor.DEFAULT);
				break;
			case HAND:
				drawSurface.getElement().getStyle().setCursor(com.google.gwt.dom.client.Style.Cursor.POINTER);				
				break;
			default:
				break;
			
		}
	}

	@Override
	public void setCustomCursor(FileDetails cursor) {
		Canvas drawSurface = ((VGLWebApplication) VGL.app).getContext().display();
		drawSurface.getElement().getStyle().clearCursor();
		drawSurface.getElement().getStyle().setProperty("cursor", "url("+cursor.absolutePath()+"), auto");
	}

	@Override
	public boolean isFocused() {
		return WebSpecific.JS
				          .hasFocus(((VGLWebApplication) VGL.app).getContext().display().getElement());
	}

	
}
