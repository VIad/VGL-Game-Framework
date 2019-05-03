package vgl.desktop;

import vgl.core.GameAdapter;
import vgl.core.exception.VGLException;

public class DesktopRuntime extends VGLApplication{

	private GameAdapter gameAdapter;
	
	public DesktopRuntime(String title, int window_width, int window_height) {
		super(title, window_width, window_height);
	}
	
	public DesktopRuntime setAdapter(GameAdapter gameAdapter) {
		this.gameAdapter = gameAdapter;
		return this;
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
