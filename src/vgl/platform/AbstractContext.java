package vgl.platform;

import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.GL11;

import vgl.core.annotation.UnusedParameter;
import vgl.core.exception.VGLException;
import vgl.core.internal.ProcessManager;
import vgl.desktop.gfx.layer.LayeredLayout;
import vgl.main.Application;
import vgl.main.VGL;

public abstract class AbstractContext<APP extends Application> {

	private boolean	loop	= true;

	protected APP	application;

	public AbstractContext(APP app) {
		this.application = app;
		this.ns = 1000.0d / (double) application.getRequestedUPS();
		createContext();
	}

	public void setRequestedUPS(int ups) {
		this.ns = 1000.0d / (double) ups;
	}

	public void updateApplicationObject(APP app) {
		this.application = app;
	}

	public void setFixedUpdateTS(float ts) {
		float actual = 1000.0f * ts;
		this.futs = (long) actual;
		System.out.println("FUTS :s: " + futs);
	}

	private long	futs			= 1000;

	private long	lastFixedUpdate;

	private boolean	first			= true;

	long			last;
	long			lastTime;
	double			ns;
	double			delta			= 0d;
	int				fps				= 0;
	int				ups				= 0;

	private boolean	firstTimeCheck	= true;

	protected void loop(@UnusedParameter(reason = "Required for requesting animation frames in JS") double unused)
	        throws VGLException {
		if (firstTimeCheck) {
			last = System.currentTimeMillis();
			lastTime = System.currentTimeMillis();
			lastFixedUpdate = System.currentTimeMillis();
			firstTimeCheck = false;
		}
		VGL.logger.warn("Actually here");
		preLoop();
		first = true;
		while (!shouldStop() || first) {
			if (first)
				first = false;
			long now = System.currentTimeMillis();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				application.update();
				if (loop) {
					ProcessManager.get().runOnUpdate();
					platformSpecificUpdate();
					VGL.errorChannel.supplyChannel();
					VGL.input.updateDeltas();
					ups++;
				}
				delta--;
			}
			platformSpecificRender();
			application.render();
			fps++;
			if ((System.currentTimeMillis() - last) > 1000) {
				last += 1000;
				if (VGL.display.isDisplayingFps())
					VGL.logger.info("FPS : " + fps + ", UPS : " + ups);
				fps = ups = 0;
			}
			if ((System.currentTimeMillis() - lastFixedUpdate) > futs) {
				lastFixedUpdate = System.currentTimeMillis();
				application.fixedUpdate();
			}
			int glError = VGL.api_gfx.glGetError();
			if (glError != GL11.GL_NO_ERROR) {
				VGL.logger.critical("GL_ERROR >> " + glError);
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

	protected abstract void preLoop();

	protected abstract void postLoop() throws VGLException;

	protected abstract void loopEnd();

	protected abstract void platformSpecificUpdate();

	protected abstract void platformSpecificRender();

	protected abstract boolean shouldStop();

}
