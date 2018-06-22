package vgl.web;

import com.shc.webgl4j.client.WebGL10;
import com.shc.webgl4j.client.WebGL20;
import com.shc.webgl4j.client.WebGLContext;
import com.vgl.gwtreq.client.CanvasDetails;
import com.vgl.gwtreq.client.VGWT;

import vgl.core.exception.VGLException;
import vgl.core.internal.ProcessManager;

public class WebContext {

	private WebGLContext		wglContext;

	private VGLWebApplication	app;

	private CanvasDetails		canvasDetails;

	private long				last;

	public WebContext(VGLWebApplication application) {
		this.app = application;
		enableSupportedGLContext();
	}

	private void enableSupportedGLContext() {
		canvasDetails = VGWT.getDetailsFromDocument(app.renderTargetID);
		canvasDetails.getElement().setAttribute("crossorigin", "anonymous");
		if (WebGL20.isSupported())
			wglContext = WebGL20.createContext(canvasDetails.getElement());
		else
			wglContext = WebGL10.createContext(canvasDetails.getElement());
		// wglContext = (WebGL20.isSupported())
		// ? WebGL20.createContext(details.getElement())
		// : WebGL10.createContext(details.getElement());
		app.set(canvasDetails.getDim());
	}

	void animCallback(double timestamp) {
		try {
			ProcessManager.get().runOnUpdate();
			if (System.currentTimeMillis() > last) {
				last = System.currentTimeMillis() + 1000;
				app.fixedUpdate();
			}
			app.update();
			app.render();
		} catch (VGLException e) {

		}
		VGWT.requestAnimation();
	}

	public CanvasDetails getCanvasDetails() {
		return canvasDetails;
	}

	public void set() {
		wglContext.makeCurrent();
	}

}
