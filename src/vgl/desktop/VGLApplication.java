package vgl.desktop;

import vgl.core.events.EventFeedback;
import vgl.core.exception.VGLException;
import vgl.core.internal.GlobalDetails;
import vgl.desktop.audio.DesktopAudioPlatform;
import vgl.desktop.gl.DesktopGraphicsPlatform;
import vgl.desktop.input.DesktopInputSystem;
import vgl.desktop.io.DesktopFiles;
import vgl.desktop.io.DesktopIOSystem;
import vgl.desktop.net.DesktopNetworkSystem;
import vgl.desktop.utils.DesktopLogger;
import vgl.desktop.utils.DesktopPromptLogger;
import vgl.main.Application;
import vgl.main.VGL;
import vgl.maths.vector.Vector2i;
import vgl.platform.Platform;

abstract public class VGLApplication extends Application {

	private String	title;
	private boolean	vsync;
	private boolean	resizable;
	

	private DesktopContext context;
	
	public VGLApplication(String title, int window_width, int window_height) {
		super();
		this.title = title;
		this.resizable = false;
		this.w_height = window_height;
		this.w_width = window_width;
		this.fixedUpdateTs = 1f;
		GlobalDetails.set((Application) this);
		GlobalDetails.set(Platform.DESKTOP);
		context = new DesktopContext(this);
		initGlobals();
	}

	public void startApplication() {
//		DesktopContext.createWindow(title, w_width, w_height, vsync, false);
		context.initGL();
		try {
			init();
		} catch (VGLException e) {
			VGL.errorChannel
			   .forward(() -> e);
		}
		try {
			context.startLoop();
		} catch (VGLException e) {
			VGL.errorChannel
			   .forward(() -> e);
		}
	}
	
	@Override
	public void setFixedUpdateTimestamp(float seconds) {
		this.context.setFixedUpdateTS(seconds);
		this.fixedUpdateTs = seconds;
	}

	public String getTitle() {
		return title;
	}

	public final void setResizable(boolean resizable) {
		VGL.display.setResizable(resizable);
		this.resizable = resizable;
	}

	public final void setVerticalSynchronized(boolean vsync) {
		VGL.display.setVsync(vsync);
		this.vsync = vsync;
	}
	
	public final void setPosition(Vector2i pos) {
		setPosition(pos.x, pos.y);
	}
	
	public final void setPosition(int x, int y) {
		((Window) VGL.display).setPosition(x, y);
	}
	
	//TODO ADD UPDATING WINDOW

	public final void setUpdatesPerSecond(int ups) {
		super.setUpdatesPerSecond(ups);
		this.context.updateApplicationObject(this);
		this.context.setRequestedUPS(ups);
	}

	public final void setFramesPerSecond(int fps) {
		this.FPS = fps;
	}

	@Override
	protected void initGlobals() {
		VGL.context = this.context;
		VGL.factory = new DesktopFactory();
		VGL.files = new DesktopFiles();
		VGL.logger = new DesktopLogger();
		VGL.net = new DesktopNetworkSystem();
		VGL.promptLogger = new DesktopPromptLogger();
		VGL.eventController = new EventFeedback();
		VGL.display = new Window(title, w_width, w_height, vsync, false);
		VGL.api_gfx = new DesktopGraphicsPlatform();
		VGL.api_afx = new DesktopAudioPlatform();
		VGL.io = new DesktopIOSystem();
		VGL.input = new DesktopInputSystem();
		VGL.app = (Application) this;
	}

}
