package vgl.main;

import vgl.core.gfx.layer.ILayout;
import vgl.core.internal.ErrorChannel;

abstract public class Application {

	protected static int	instancesOf;
	private ILayout			layout;
	protected float fixedUpdateTs;

	public Application() {
		if (instancesOf > 0)
			throw new vgl.core.exception.VGLFatalError("VGL currently supports 1 application running concurrently");
		instancesOf++;
		VGL.errorChannel = new ErrorChannel();
		VGL.errorChannel
		   .setErrorHandler(error -> {
			   error.printStackTrace();
		   });
		this.UPS = 60;
	}

	protected int	w_width, w_height;
	protected int	UPS;
	protected int	FPS;

	public int getRequestedFPS() {
		return FPS;
	}

	public int getRequestedUPS() {
		return UPS;
	}

	public int getWindowHeight() {
		return w_height;
	}

	public int getWindowWidth() {
		return w_width;
	}

	public void setLayout(ILayout layout) {
		this.layout = layout;
	}

	public ILayout getLayout() {
		return layout;
	}
	
	abstract public void setFixedUpdateTimestamp(float seconds);

	abstract public void init() throws vgl.core.exception.VGLException;

	abstract public void render() throws vgl.core.exception.VGLException;

	abstract public void update() throws vgl.core.exception.VGLException;

	abstract public void finish() throws vgl.core.exception.VGLException;

	public void fixedUpdate() throws vgl.core.exception.VGLException {
	}
	
	public void setUpdatesPerSecond(int ups) {
		this.UPS = ups;
	}
	
	abstract public void startApplication();

	abstract protected void initGlobals();

}
