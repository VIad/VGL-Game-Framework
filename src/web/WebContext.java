package vgl.web;

import com.shc.webgl4j.client.WebGL20;
import com.shc.webgl4j.client.WebGLContext;
import com.vgl.gwtreq.client.CanvasDetails;
import com.vgl.gwtreq.client.VGWT;

public class WebContext {

	private WebGLContext		wglContext;

	private VGLWebApplication	app;

	public WebContext(VGLWebApplication application) {
		this.app = application;
		enableSupportedGLContext();
	}

	private void enableSupportedGLContext() {
		final CanvasDetails details = VGWT.getDetailsFromDocument(app.renderTargetID);
		wglContext = (WebGL20.isSupported())
		        ? WebGL20.createContext(details.getElement())
		        : WebGL10.createContext(details.getElement());
		app.set(details.getDim());
	}

	public void set() {
		wglContext.makeCurrent();
	}

}
