package vgl.platform;

abstract public class Application {

	protected static int instancesOf;

	public Application() {
		if (instancesOf > 0)
			throw new vgl.core.exception.VGLFatalError("VGL currently supports 1 application running concurrently");
		instancesOf++;
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

	abstract public void init() throws vgl.core.exception.VGLException;

	abstract public void render() throws vgl.core.exception.VGLException;

	abstract public void update() throws vgl.core.exception.VGLException;

	abstract public void finish() throws vgl.core.exception.VGLException;

	public void fixedUpdate() throws vgl.core.exception.VGLException {
	}

}
