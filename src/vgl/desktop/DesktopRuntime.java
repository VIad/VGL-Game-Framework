package vgl.desktop;

import java.util.HashMap;
import java.util.Map;

import vgl.core.BasicState;
import vgl.core.StateControlledApplication;
import vgl.core.StateController;
import vgl.core.exception.VGLException;
import vgl.core.internal.ProcessManager;
import vgl.main.VGL;

public class DesktopRuntime extends VGLApplication {
	
	private BasicState stateStorage;
	
	public DesktopRuntime(String title, int window_width, int window_height, BasicState gameAdapter) {
		super(title, window_width, window_height);
		this.stateStorage = gameAdapter;
	}

	@Override
	public void init() throws VGLException {
		VGL.states.add(stateStorage);
		VGL.states.setActive(stateStorage.getName());
	}

	@Override
	public void render() throws VGLException {
		VGL.states.render();
	}

	@Override
	public void update() throws VGLException {
		VGL.states.update();
	}

	@Override
	public void finish() throws VGLException {
		VGL.states.finish();
	}
	
	@Override
	public void fixedUpdate() throws VGLException {
		VGL.states.fixedUpdate();
	}
	
	@Override
	protected void initGlobals() {
		super.initGlobals();
		VGL.states = new StateController();
	}

}
