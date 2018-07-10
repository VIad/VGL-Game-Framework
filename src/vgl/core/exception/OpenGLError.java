package vgl.core.exception;

public class OpenGLError extends VGLRuntimeException{

	public OpenGLError(String message) {
		super(message);
	}
	
	public OpenGLError(int code) {
		super("OpenGL Error : "+code);
	}

}
