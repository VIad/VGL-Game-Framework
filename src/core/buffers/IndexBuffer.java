package vgl.core.buffers;

import org.lwjgl.opengl.GL15;

import vgl.core.gl.GLBufferUsage;
import vgl.platform.gl.GLIndexBuffer;

public class IndexBuffer extends GLIndexBuffer {

	public IndexBuffer(int[] data) {
		super(data);
	}

	@Override
	protected int newBuffer() {
		return GL15.glGenBuffers();
	}

	@Override
	protected void storeData(int[] data) {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer_ID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, data, GLBufferUsage.STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	@Override
	public void bind() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer_ID);
	}

	@Override
	public void unbind() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

}
