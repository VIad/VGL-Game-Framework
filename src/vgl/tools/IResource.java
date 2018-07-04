package vgl.tools;

import vgl.core.annotation.VGLInternal;
import vgl.core.exception.VGLRuntimeException;

public interface IResource {

	void releaseMemory();
	
	boolean isDisposed();
	
	default void validate() {
		if(isDisposed())
			throw new IResource.ResourceException("Resource has already been released");
	}
	
	public static class ResourceException extends VGLRuntimeException{

		public ResourceException(String message) {
			super(message);
		}
		
	}
	
	IResource.ResourceState getResourceState();
	
	public enum ResourceState{
		
		UNAVAILABLE,
		
		LOADING,
		
		AVAILABLE,
		
		DISPOSED;
	}
	
}
