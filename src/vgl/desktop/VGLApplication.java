package vgl.desktop;

import vgl.core.exception.VGLException;
import vgl.core.internal.GlobalDetails;
import vgl.desktop.gl.DesktopGraphicsPlatform;
import vgl.desktop.input.Keyboard;
import vgl.desktop.input.Mouse;
import vgl.desktop.io.DesktopIOSystem;
import vgl.desktop.utils.DesktopLogger;
import vgl.main.VGL;
import vgl.platform.Application;
import vgl.platform.Display;
import vgl.platform.Platform;

abstract public class VGLApplication extends Application {

	private String	title;
	private boolean	vsync;
	private boolean	resizable;

	public VGLApplication(String title, int window_width, int window_height) {
		super();
		this.title = title;
		this.resizable = false;
		this.w_height = window_height;
		this.w_width = window_width;
		boolean osarch64 = Integer.valueOf(System.getProperty("sun.arch.data.model")) == 64;
		GlobalDetails.set((Application) this);
		GlobalDetails.set(osarch64 ? Platform.DESKTOP_X64 : Platform.DESKTOP_X86);
		CoreContext.createContext(this);
	}

	public void startApplication() {
		CoreContext.createWindow(title, w_width, w_height, vsync, false);
		Window.setResizable(resizable);
		CoreContext.initGL();
		Mouse.create();
		Keyboard.create();
		try {
			init();
		} catch (VGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CoreContext.startLoop();
	}

	public String getTitle() {
		return title;
	}

	public final void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public final void setVerticalSynchronized(boolean vsync) {
		this.vsync = vsync;
	}

	public final void setUpdatesPerSecond(int ups) {
		this.UPS = ups;
	}

	public final void setFramesPerSecond(int fps) {
		this.FPS = fps;
	}

	@Override
	protected void initGlobals() {
		VGL.factory = new DesktopFactory();
		VGL.logger = new DesktopLogger();
		VGL.display = new Display(w_width, w_height);
		VGL.api = new DesktopGraphicsPlatform();
		VGL.io = new DesktopIOSystem();
	}

}
