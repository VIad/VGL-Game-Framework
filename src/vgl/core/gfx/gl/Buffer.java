package vgl.core.gfx.gl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;

import vgl.core.buffers.MemoryBufferFloat;
import vgl.core.internal.Checks;
import vgl.main.VGL;

public final class Buffer {

	private int		buffer;
	private boolean	bound;

	public Buffer(int dataUsage, float[] data) {
		Checks.checkIfInitialized();
		buffer = VGL.api_gfx.glGenBuffer();
		VGL.api_gfx.glBindBuffer(GL_ARRAY_BUFFER, buffer);
		VGL.api_gfx.glBufferData(buffer, data, dataUsage);
		VGL.api_gfx.glBindBuffer(GL_ARRAY_BUFFER, 0);
		this.bound = false;
	}

	public Buffer(MemoryBufferFloat data, int dataUsage) {
		Checks.checkIfInitialized();
		buffer = VGL.api_gfx.glGenBuffer();
		VGL.api_gfx.glBindBuffer(GL_ARRAY_BUFFER, buffer);
		VGL.api_gfx.glBufferData(buffer, data.getBuffer(), dataUsage);
		VGL.api_gfx.glBindBuffer(GL_ARRAY_BUFFER, 0);
		this.bound = false;
	}

	public Buffer(int dataSizeBytes, int usage) {
		Checks.checkIfInitialized();
		buffer = VGL.api_gfx.glGenBuffer();
		VGL.api_gfx.glBindBuffer(GL_ARRAY_BUFFER, buffer);
		VGL.api_gfx.glBufferData(buffer, dataSizeBytes, usage);
		VGL.api_gfx.glBindBuffer(GL_ARRAY_BUFFER, 0);
		this.bound = false;
	}

	public void bind() {
		Checks.checkIfInitialized();
		VGL.api_gfx.glBindBuffer(GL_ARRAY_BUFFER, buffer);
		bound = true;
	}

	public void reconfigure(int size, int usage) {
		Checks.checkIfInitialized();
		bind();
		VGL.api_gfx.glBufferData(GL_ARRAY_BUFFER, size, usage);
		unbind();
	}

	public void unbind() {
		Checks.checkIfInitialized();
		VGL.api_gfx.glBindBuffer(GL_ARRAY_BUFFER, 0);
		bound = false;
	}

	public boolean isBound() {
		return bound;
	}
}
