package vgl.web;

import com.vgl.gwtreq.client.Dim;
import com.vgl.gwtreq.client.VGWT;

import vgl.core.exception.VGLException;
import vgl.core.internal.GlobalDetails;
import vgl.main.VGL;
import vgl.platform.Application;
import vgl.platform.Display;
import vgl.platform.Platform;
import vgl.web.input.WebInputSystem;
import vgl.web.io.WebIOSystem;
import vgl.web.utils.WebLogger;

abstract public class VGLWebApplication extends Application {

	String				renderTargetID;
	private WebContext	context;

	public VGLWebApplication(String documentCanvasId) {
		super();
		GlobalDetails.set((Application) this);
		GlobalDetails.set(Platform.WEB);
		this.renderTargetID = documentCanvasId;
		context = new WebContext(this);
		context.set();
		WebGLExtensions.tryEnableAll();

		try {
			init();
		} catch (VGLException e) {

		}

		VGWT.setAnimationCallback(context::animCallback);
		VGWT.requestAnimation();
	}

	@Override
	protected void initGlobals() {
		VGL.display = new Display(w_width, w_height);
		VGL.factory = new WebFactory();
		VGL.logger = new WebLogger();
		VGL.api_gfx = new WebGraphicsPlatform();
		VGL.io = new WebIOSystem();
		VGL.input = new WebInputSystem();
		VGL.app = (Application) this;
	}

	void set(Dim dim) {
		this.w_width = dim.width;
		this.w_height = dim.height;
	}

	public WebContext getContext() {
		return context;
	}
	
	public String getRenderTargetID() {
		return renderTargetID;
	}
}
