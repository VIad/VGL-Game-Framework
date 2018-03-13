package vgl.web;

import java.awt.Dimension;

import com.vgl.gwtreq.client.VGWT;

import vgl.core.exception.VGLException;
import vgl.core.internal.GlobalDetails;
import vgl.platform.Application;
import vgl.platform.Platform;

abstract public class VGLWebApplication extends Application {

	String				renderTargetID;
	private WebContext	context;

	public VGLWebApplication(String documentCanvasId) {
		GlobalDetails.set((Application) this);
		GlobalDetails.set(Platform.WEB);
		this.renderTargetID = documentCanvasId;
		context = new WebContext(this);
		context.set();
		try {
			init();
		} catch (VGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VGWT.setAnimationCallback(this::animCallback);
		VGWT.requestAnimation();
	}

	private void animCallback(double timestamp) {
		try {
			update();
			render();
		} catch (VGLException ignored) {
		}
		VGWT.requestAnimation();
	}

	void set(Dimension dim) {
		this.w_width = dim.width;
		this.w_height = dim.height;
	}

	public WebContext getContext() {
		return context;
	}
}
