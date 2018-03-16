package vgl.desktop.gl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.gl.GLVertexArray;
import vgl.utils.BufferStorageSafe;

public class VertexArray extends GLVertexArray {

	public VertexArray() {
		super();
	}

	@Override
	public void addBuffer(float[] data, int attribPosition, int dataSize) {
		if (buffers.containsKey(attribPosition))
			throw new IllegalArgumentException("Buffer >> already created !!");
		bind();
		final int vboID = GL15.glGenBuffers();
		buffers.put(attribPosition, vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		final MemoryBuffer memBuffer = BufferStorageSafe.storeInBuffer(data);
		final FloatBuffer buffer = ((ByteBuffer) memBuffer.nativeBufferDetails().getBuffer()).asFloatBuffer();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		bufferCache.add(memBuffer);
		GL20.glVertexAttribPointer(attribPosition, dataSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		unbind();
	}

	@Override
	public void addIndicesBuffer(int[] ind) {
		if (buffers.containsKey(DEFAULT_IBO_KEY))
			buffers.remove(DEFAULT_IBO_KEY);
		bind();
		final int vboID = GL15.glGenBuffers();
		buffers.put(DEFAULT_IBO_KEY, vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		final MemoryBuffer memBuffer = BufferStorageSafe.storeInBuffer(ind);
		final IntBuffer buffer = ((ByteBuffer) memBuffer.nativeBufferDetails().getBuffer()).asIntBuffer();
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		bufferCache.add(memBuffer);
		vertexCount = ind.length;
		hasIndexBuffer = true;
		unbind();
	}

	@Override
	public void bindAll() {
		for (final Map.Entry<Integer, Integer> en : buffers.entrySet())
			if (!(en.getKey() == DEFAULT_IBO_KEY))
				GL20.glEnableVertexAttribArray(en.getKey());

	}

	@Override
	public void bindIndexBuffer() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, buffers.get(DEFAULT_IBO_KEY));
	}

	@Override
	public void unbindIndexBuffer() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	@Override
	public void unbindAll() {
		for (final Map.Entry<Integer, Integer> en : buffers.entrySet())
			if (!(en.getKey() == DEFAULT_IBO_KEY))
				GL20.glDisableVertexAttribArray(en.getKey());
	}

	@Override
	public void bind() {
		GL30.glBindVertexArray(vaoID);
		bound = true;
	}

	@Override
	public void unbind() {
		GL30.glBindVertexArray(0);
		bound = false;
	}

	@Override
	protected int glGenVao() {
		return GL30.glGenVertexArrays();
	}

}
