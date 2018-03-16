package vgl.platform.gl;

import org.lwjgl.opengl.GL15;

public class GLBufferUsage {
	
	public static final int STATIC_DRAW = GL15.GL_STATIC_DRAW;
	public static final int STATIC_READ = GL15.GL_STATIC_READ;
	public static final int STATIC_COPY = GL15.GL_STATIC_COPY;

	public static final int DYNAMIC_DRAW = GL15.GL_DYNAMIC_DRAW;
	public static final int DYNAMIC_READ = GL15.GL_DYNAMIC_READ;
	public static final int DYNAMIC_COPY = GL15.GL_DYNAMIC_COPY;
	
	public static final int STREAM_DRAW = GL15.GL_STREAM_DRAW;
	public static final int STREAM_READ = GL15.GL_STREAM_READ;
	public static final int STREAM_COPY = GL15.GL_STREAM_COPY;
}
