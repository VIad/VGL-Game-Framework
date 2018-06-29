package vgl.core.exception;

import com.shc.gwtal.client.openal.AudioDecoder;
import com.shc.gwtal.client.openal.AudioDecoder.FileFormat;

import vgl.platform.VGLRuntime;

public class PlatformUnsupportedException extends VGLRuntimeException{

	public PlatformUnsupportedException(String message) {
		super(message);
	}

}
