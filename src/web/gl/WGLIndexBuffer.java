package vgl.web.gl;

import com.shc.webgl4j.client.WebGL10;

import vgl.platform.gl.GLIndexBuffer;

public class WGLIndexBuffer extends GLIndexBuffer {

	public WGLIndexBuffer(int[] data) {
		super(data);
	}

	@Override
	protected int newBuffer() {
		return WebGL10.glCreateBuffer();
	}

	@Override
	protected void storeData(int[] data) {
		WebGL10.glBindBuffer(WebGL10.GL_ELEMENT_ARRAY_BUFFER, buffer_ID);
		WebGL10.glBufferData(WebGL10.GL_ELEMENT_ARRAY_BUFFER, data, WebGL10.GL_STATIC_DRAW);
		WebGL10.glBindBuffer(WebGL10.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	@Override
	public void bind() {
		WebGL10.glBindBuffer(WebGL10.GL_ELEMENT_ARRAY_BUFFER, buffer_ID);
	}

	@Override
	public void unbind() {
		WebGL10.glBindBuffer(WebGL10.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

}
