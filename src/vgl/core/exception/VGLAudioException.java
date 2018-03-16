package vgl.core.exception;

@SuppressWarnings("serial")
public class VGLAudioException extends RuntimeException {

	public VGLAudioException(String message) {
		super(message);
	}

	public VGLAudioException() {
		super("An Audio error has occured");
	}

}
