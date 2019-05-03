package vgl.web;

import vgl.core.GameAdapter;
import vgl.core.exception.VGLException;

public class WebRuntime extends VGLWebApplication{

	private GameAdapter gameAdapter;
	
	public WebRuntime(String documentCanvasId, GameAdapter gameAdapter) {
		super(documentCanvasId);
		this.gameAdapter = gameAdapter;
	}

	@Override
	public void init() throws VGLException {
		gameAdapter.init();
	}

	@Override
	public void render() throws VGLException {
		gameAdapter.render();
	}

	@Override
	public void update() throws VGLException {
		gameAdapter.update();
	}

	@Override
	public void finish() throws VGLException {
		gameAdapter.finish();
	}
	
	@Override
	public void fixedUpdate() throws VGLException {
		gameAdapter.fixedUpdate();
	}

}
