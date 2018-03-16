package vgl.desktop.gl;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;

import vgl.core.internal.Checks;

public final class Buffer {

	private int		buffer;
	private boolean	bound;

	public Buffer(int dataUsage, float[] data) {
		Checks.checkIfInitialized();
		Checks.checkCanInstantiate(getClass());
		buffer = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, buffer);
		glBufferData(buffer, data, dataUsage);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		this.bound = false;
	}

	public Buffer(FloatBuffer data, int dataUsage) {
		Checks.checkIfInitialized();
		Checks.checkCanInstantiate(getClass());
		buffer = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, buffer);
		glBufferData(buffer, data, dataUsage);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		this.bound = false;
	}

	public Buffer(int dataSizeBytes, int usage) {
		Checks.checkIfInitialized();
		Checks.checkCanInstantiate(getClass());
		buffer = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, buffer);
		glBufferData(GL_ARRAY_BUFFER, dataSizeBytes, usage);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		this.bound = false;
	}

	public void bind() {
		Checks.checkIfInitialized();
		glBindBuffer(GL_ARRAY_BUFFER, buffer);
		bound = true;
	}

	public void reconfigure(int size, int usage) {
		Checks.checkIfInitialized();
		bind();
		glBufferData(GL_ARRAY_BUFFER, size, usage);
		unbind();
	}

	public void unbind() {
		Checks.checkIfInitialized();
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		bound = false;
	}

	public java.nio.ByteBuffer map(int bufferAccess) {
		Checks.checkIfInitialized();
		bind();
		return glMapBuffer(GL_ARRAY_BUFFER, bufferAccess);
	}

	public void unmap() {
		Checks.checkIfInitialized();
		glUnmapBuffer(GL_ARRAY_BUFFER);
		unbind();
	}

	public boolean isBound() {
		return bound;
	}
}
