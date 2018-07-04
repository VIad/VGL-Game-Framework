package vgl.web;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.shc.webgl4j.client.WebGL10;
import com.shc.webgl4j.client.WebGL20;
import com.shc.webgl4j.client.WebGLContext;
import com.vgl.gwtreq.client.CanvasDetails;
import com.vgl.gwtreq.client.Dim;
import com.vgl.gwtreq.client.VGWT;

import vgl.core.exception.VGLException;
import vgl.core.internal.ProcessManager;
import vgl.main.VGL;

@Deprecated
public class WebContextOld {

	private WebGLContext		wglContext;

	private VGLWebApplication	app;

	private Canvas				canvas;

	private long				last;
	
	private CanvasDetails details;

	public WebContextOld(VGLWebApplication application) {
		this.app = application;
		enableSupportedGLContext();
	}

	private void enableSupportedGLContext() {
		CanvasElement ce;
		canvas = Canvas.wrap(ce = (CanvasElement) Document.get().getElementById(app.renderTargetID));
		// canvasDetails = VGWT.getDetailsFromDocument(app.renderTargetID);
		if (WebGL20.isSupported())
			wglContext = WebGL20.createContext(canvas);
		else
			wglContext = WebGL10.createContext(canvas);
		// wglContext = (WebGL20.isSupported())
		// ? WebGL20.createContext(details.getElement())
		// : WebGL10.createContext(details.getElement());
		app.set(new Dim(ce.getWidth(), ce.getHeight()));
		this.details = new CanvasDetails(ce);
	}

	void animCallback(double timestamp) {
		try {
			ProcessManager.get().runOnUpdate();
			if (System.currentTimeMillis() > last) {
				last = System.currentTimeMillis() + 1000;
				app.fixedUpdate();
			}
			app.update();
			VGL.input.updateDeltas();
			app.render();
		} catch (VGLException e) {

		}
		VGWT.requestAnimation();
	}

	public CanvasDetails getCanvasDetails() {
		return details;
	}
	
	public Canvas display() {
		return canvas;
	}

	public void set() {
		wglContext.makeCurrent();
	}

}
