package vgl.web;

import com.google.gwt.user.client.Window;
import com.shc.gwtal.client.openal.AudioDecoder;
import com.vgl.gwtreq.client.Dim;
import com.vgl.gwtreq.client.GWTDataView;
import com.vgl.gwtreq.client.VGWT;

import vgl.audio.Sound;
import vgl.core.exception.VGLException;
import vgl.core.internal.GlobalDetails;
import vgl.main.Application;
import vgl.main.VGL;
import vgl.platform.AbstractDisplayDevice;
import vgl.platform.Platform;
import vgl.web.audio.WebAudioPlatform;
import vgl.web.input.WebInputSystem;
import vgl.web.io.WebIOSystem;
import vgl.web.utils.WebLogger;
import vgl.web.utils.WebPromptLogger;

abstract public class VGLWebApplication extends Application {

	String				renderTargetID;
	private WContext	context;

	public VGLWebApplication(String documentCanvasId) {
		super();
		GlobalDetails.set((Application) this);
		GlobalDetails.set(Platform.WEB);
		this.renderTargetID = documentCanvasId;
		this.context = new WContext(this);
		context.set();
		WebGLExtensions.tryEnableAll();
		initGlobals();
		try {
			init();
		} catch (VGLException e) {

		}
		VGWT.setAnimationCallback(timestamp -> {
			try {
				context.loop(timestamp);
			} catch (VGLException e) {
				VGL.errorChannel
				   .forward(() -> e);
			}
		});
		
	}
	
	public void decodeAndPlayTest() {
		VGL.io.readBytes(VGL.io.file("http://localhost/net/bet.ogg"), buffer -> {
			AudioDecoder.decodeAudio(((GWTDataView)buffer.nativeBufferDetails().getBuffer()).getView().buffer(), sb -> {
				Sound s = new Sound(sb);
				s.setPitch(5f);
				s.play();
			}, error -> {
				Window.alert(error);
			});
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
	}
	
	@Override
	public void startApplication() {
		VGWT.requestAnimation();
	}

	@Override
	protected void initGlobals() {
		VGL.app = (Application) this;
		VGL.context = this.context;
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

	public WContext getContext() {
		return context;
	}
	
	public String getRenderTargetID() {
		return renderTargetID;
	}

	public void set(WContext webContext) {
		this.context = webContext;
	}
}
