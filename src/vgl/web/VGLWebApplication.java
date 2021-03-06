package vgl.web;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;

import vgl.core.events.EventFeedback;
import vgl.core.exception.VGLException;
import vgl.core.gfx.Color;
import vgl.core.internal.Checks;
import vgl.core.internal.GlobalDetails;
import vgl.main.Application;
import vgl.main.VGL;
import vgl.platform.Platform;
import vgl.tools.Dim;
import vgl.web.audio.WebAudioPlatform;
import vgl.web.input.WebInputSystem;
import vgl.web.io.WebFiles;
import vgl.web.io.WebIOSystem;
import vgl.web.networking.WebNetworkSystem;
import vgl.web.utils.JSRenderInfoContainer;
import vgl.web.utils.WebLogger;
import vgl.web.utils.WebPromptLogger;

abstract public class VGLWebApplication extends Application {

	String				renderTargetID;
	private WebContext	context;

	public VGLWebApplication(String documentCanvasId) {
		super();
		this.renderTargetID = documentCanvasId;
		GlobalDetails.set((Application) this);
		GlobalDetails.set(Platform.WEB);
		this.context = new WebContext(this);
		context.set();
		WebGLExtensions.tryEnableAll();
		initGlobals();
		Checks.__setglinit(true);
		
		JSRenderInfoContainer.setAnimationCallback((timestamp) -> {
			try {
				context.loop(timestamp);
			} catch (VGLException e) {
				VGL.errorChannel.forward(() -> e);
			}
		});
		
	}
	
	@Override
	public void setUpdatesPerSecond(int ups) {
		super.setUpdatesPerSecond(ups);
		this.context.updateApplicationObject(this);
		this.context.setRequestedUPS(ups);
	}
	
	@Override
	public void setFixedUpdateTimestamp(float seconds) {
		this.context.setFixedUpdateTS(seconds);
		this.fixedUpdateTs = seconds;
		this.context.updateApplicationObject(this);
	}
	
	@Override
	public void startApplication() {
		try {
			init();
		} catch (VGLException e) {
			VGL.errorChannel.forward(() -> e);
		}
		VGL.display.setClearColor(Color.BLACK);
		JSRenderInfoContainer.requestFrame();
	}

	@Override
	protected void initGlobals() {
		VGL.app = (Application) this;
		VGL.context = this.context;
		VGL.files = new WebFiles();
		VGL.net = new WebNetworkSystem();
		VGL.eventController = new EventFeedback();
		VGL.display = new WebDisplay("display",w_width, w_height, true, false);
		VGL.factory = new WebFactory();
		VGL.logger = new WebLogger();
		VGL.promptLogger = new WebPromptLogger();
		VGL.api_gfx = new WebGraphicsPlatform();
		VGL.api_afx = new WebAudioPlatform();
		VGL.io = new WebIOSystem();
		VGL.input = new WebInputSystem();
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

	public void set(WebContext webContext) {
		this.context = webContext;
	}
}
