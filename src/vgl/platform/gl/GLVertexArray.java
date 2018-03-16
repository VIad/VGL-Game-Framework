package vgl.platform.gl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vgl.core.buffers.MemoryBuffer;
import vgl.core.internal.Checks;

abstract public class GLVertexArray {

	protected final int						vaoID;
	protected int							vertexCount;
	protected boolean						hasIndexBuffer;
	protected final Map<Integer, Integer>	buffers;
	protected static List<Integer>			all			= new ArrayList<>();
	protected static List<MemoryBuffer>		bufferCache	= new ArrayList<>();
	protected boolean						bound;

	public GLVertexArray() {
		Checks.checkIfInitialized();
		vaoID = glGenVao();
		buffers = new HashMap<>();
		hasIndexBuffer = false;
		bound = true;
	}

	abstract protected int glGenVao();

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

	public abstract void addBuffer(final float[] data, final int attribPosition, final int dataSize);

	public static final int DEFAULT_IBO_KEY = -(2 << 5);

	public abstract void addIndicesBuffer(final int[] ind);

	public int getVaoID() {
		return vaoID;
	}

	public abstract void bindAll();

	public abstract void bindIndexBuffer();

	public abstract void unbindIndexBuffer();

	public abstract void unbindAll();

	public abstract void bind();

	public abstract void unbind();

	public int getVertexCount() {
		return vertexCount;
	}

	public boolean hasIndexBuffer() {
		return hasIndexBuffer;
	}

	public boolean isBound() {
		return bound;
	}

	public static void freeBuffers() {
		bufferCache.forEach(MemoryBuffer::free);
	}

}
