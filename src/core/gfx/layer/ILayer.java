package vgl.core.gfx.layer;

import vgl.core.annotation.VGLInternal;

public interface ILayer {

	/**
	 * Method called internally from VGL, calls to this method outside VGL scope
	 * will likelly result in poor performance / errors
	 */
	@VGLInternal
	void _renderInternal();

}
