package vgl.desktop.gl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import vgl.core.buffers.Buffers;
import vgl.core.buffers.MemoryBuffer;
import vgl.core.internal.Checks;
import vgl.main.VGL;
import vgl.platform.gl.GLBufferTarget;
import vgl.platform.gl.GLBufferUsage;

/**
 * Not really applicable in many cases tbh
 *
 */
public class VertexArray {
	
	protected final int						vaoID;
	protected int							vertexCount;
	protected boolean						hasIndexBuffer;
	protected final Map<Integer, Integer>	buffers;
	protected static List<Integer>			all			= new ArrayList<>();
	protected static List<MemoryBuffer>		bufferCache	= new ArrayList<>();
	protected boolean						bound;

	public VertexArray() {
		Checks.checkIfInitialized();
		vaoID = VGL.api_gfx.glGenVertexArray();
		buffers = new HashMap<>();
		hasIndexBuffer = false;
		bound = true;
	}
	
	public void setVertexBuffer(final float[] data, final int attribPosition, final int dataSize) {
		hasIndexBuffer = false;
		addBuffer(data, attribPosition, dataSize);
		vertexCount = data.length;
	}
	
	public void addVertexBuffer(final float[] data, final int[] indices, final int attribPosition, final int dataSize) {
		if (buffers.containsKey(attribPosition))
			throw new IllegalArgumentException("Buffer >> already created !!");
		addBuffer(data, attribPosition, dataSize);
		addIndicesBuffer(indices);
		hasIndexBuffer = true;
	}
	
	public static void freeBuffers() {
		bufferCache.forEach(MemoryBuffer::free);
	}

	
	public void addBuffer(float[] data, int attribPosition, int dataSize) {
		if (buffers.containsKey(attribPosition))
			throw new IllegalArgumentException("Buffer >> already created !!");
		bind();
		final int vboID = VGL.api_gfx.glGenBuffer();
		buffers.put(attribPosition, vboID);
		VGL.api_gfx.glBindBuffer(GLBufferTarget.ARRAY_BUFFER, vboID);
		final MemoryBuffer memBuffer = Buffers.wrap(data).getBuffer();
//		final FloatBuffer buffer = ((ByteBuffer) memBuffer.nativeBufferDetails().getBuffer()).asFloatBuffer();
		VGL.api_gfx.glBufferData(GLBufferTarget.ARRAY_BUFFER, memBuffer, GLBufferUsage.STATIC_DRAW);
		bufferCache.add(memBuffer);
		VGL.api_gfx.glVertexAttribPointer(attribPosition, dataSize, GL11.GL_FLOAT, false, 0, 0);
		VGL.api_gfx.glBindBuffer(GLBufferTarget.ARRAY_BUFFER, 0);
		unbind();
	}

	
	public static final int DEFAULT_IBO_KEY = -(2 << 5);
	
	public void addIndicesBuffer(int[] ind) {
		if (buffers.containsKey(DEFAULT_IBO_KEY))
			buffers.remove(DEFAULT_IBO_KEY);
		bind();
		final int vboID = VGL.api_gfx.glGenBuffer();
		buffers.put(DEFAULT_IBO_KEY, vboID);
		VGL.api_gfx.glBindBuffer(GLBufferTarget.ARRAY_BUFFER, vboID);
		final MemoryBuffer memBuffer = Buffers.wrap(ind).getBuffer();
//		final FloatBuffer buffer = ((ByteBuffer) memBuffer.nativeBufferDetails().getBuffer()).asFloatBuffer();
		VGL.api_gfx.glBufferData(GLBufferTarget.ARRAY_BUFFER, memBuffer, GLBufferUsage.STATIC_DRAW);
		bufferCache.add(memBuffer);
		vertexCount = ind.length;
		hasIndexBuffer = true;
		unbind();
	}

	
	public void bindAll() {
		for (final Map.Entry<Integer, Integer> en : buffers.entrySet())
			if (!(en.getKey() == DEFAULT_IBO_KEY))
				VGL.api_gfx.glEnableVertexAttribArray(en.getKey());
	}

	
	public void bindIndexBuffer() {
		VGL.api_gfx.glBindBuffer(GLBufferTarget.ELEMENT_ARRAY_BUFFER, buffers.get(DEFAULT_IBO_KEY));
	}

	
	public void unbindIndexBuffer() {
		VGL.api_gfx.glBindBuffer(GLBufferTarget.ELEMENT_ARRAY_BUFFER, 0);
	}

	
	public void unbindAll() {
		for (final Map.Entry<Integer, Integer> en : buffers.entrySet())
			if (!(en.getKey() == DEFAULT_IBO_KEY))
				VGL.api_gfx.glDisableVertexAttribArray(en.getKey());
	}

	
	public void bind() {
		VGL.api_gfx.glBindVertexArray(vaoID);
		bound = true;
	}

	
	public void unbind() {
		VGL.api_gfx.glBindVertexArray(0);
		bound = false;
	}

}
