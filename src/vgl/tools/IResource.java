package vgl.tools;

import vgl.core.exception.VGLResourceException;

public interface IResource {

	void releaseMemory();
	
	boolean isDisposed();
	
	default void validate() {
		if(isDisposed())
			throw new VGLResourceException("Resource has already been released");
	}
	
}
