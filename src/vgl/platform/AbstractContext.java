package vgl.platform;

import org.lwjgl.openal.AL10;

import vgl.core.annotation.UnusedParameter;
import vgl.core.exception.VGLException;
import vgl.core.internal.ProcessManager;
import vgl.main.Application;
import vgl.main.VGL;

public abstract class AbstractContext<APP extends Application> {

	private boolean	loop	= true;

	protected APP	application;

	public AbstractContext(APP app) {
		this.application = app;
		this.ns = 1000000000.0d / (double) application.getRequestedUPS();
		createContext();
	}

	public void setRequestedUPS(int ups) {
		this.ns = 1000000000.0d / (double) ups;
	}

	public void updateApplicationObject(APP app) {
		this.application = app;
	}

	public void setFixedUpdateTS(float ts) {
		float actual = 1000.0f * ts;
		this.futs = (long) actual;
		VGL.logger.info("Fixed update timestamp now "+futs + " ms");
	}

	protected long		futs			= 1000;

	private long		lastFixedUpdate;

	private boolean		first			= true;

	long				last;
	long				lastTime;
	protected double	ns;
	double				delta			= 0d;
	protected int		fps				= 0;
	protected int		ups				= 0;

	private boolean		firstTimeCheck	= true;
	
	protected void setFPS(int fps) {
		VGL.display.lastFps = fps;
	}
	
	protected void loop(@UnusedParameter(reason = "Required for requesting animation frames in JS") double unused)
	        throws VGLException {
		if (firstTimeCheck) {
			last = System.currentTimeMillis();
			lastTime = platformCurrentTime();
			lastFixedUpdate = System.currentTimeMillis();
			firstTimeCheck = false;
		}
		preLoop();
		first = true;
		while (!shouldStop() || first) {
			if (first)
				first = false;
			long now = platformCurrentTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				application.update();
				if (loop) {
					ProcessManager.get().runOnUpdate();
					VGL.errorChannel.supplyChannel();
					VGL.input.updateDeltas();
					ups++;
				}
				delta--;
			}
			ProcessManager.get().runOnRender();
			application.render();
			fps++;
			if ((System.currentTimeMillis() - last) > 1000) {
				last += 1000;
				if (VGL.display.isDisplayingFps())
					VGL.logger.info("FPS : " + fps + ", UPS : " + ups);
				setFPS(fps);
				fps = ups = 0;
			}
			if ((System.currentTimeMillis() - lastFixedUpdate) > futs) {
				lastFixedUpdate = System.currentTimeMillis();
				application.fixedUpdate();
			}
			
			if (VGL.api_afx.isInitialized()) {
				int alError = VGL.api_afx.alGetError();
				if (alError != AL10.AL_NO_ERROR) {
					VGL.logger.critical("AL_ERROR >> " + alError);
				}
			}
			loopEnd();
		}
		postLoop();
	}

	public void toggleInternalOperations() {
		this.loop = !loop;
	}

	public abstract void createContext();

	protected abstract void startLoop() throws VGLException;

	protected abstract void initGL();
	
	protected long platformCurrentTime() {
		return System.currentTimeMillis();
	}

	protected abstract void preLoop();

	protected abstract void postLoop() throws VGLException;

	protected abstract void loopEnd();

	protected abstract boolean shouldStop();

}
