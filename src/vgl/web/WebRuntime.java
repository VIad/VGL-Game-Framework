package vgl.web;

import vgl.core.BasicState;
import vgl.core.StateController;
import vgl.core.exception.VGLException;
import vgl.main.VGL;

public class WebRuntime extends VGLWebApplication{

	private BasicState stateStorage;
	
	public WebRuntime(String documentCanvasId, BasicState stateStorage) {
		super(documentCanvasId);
		this.stateStorage = stateStorage;
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
