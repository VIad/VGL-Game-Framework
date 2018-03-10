package vgl.platform.gl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import vgl.core.buffers.VertexArray;
import vgl.core.internal.Checks;

abstract public class GLVertexArray {

	protected final int						vaoID;
	protected int							vertexCount;
	protected boolean						hasIndexBuffer;
	protected final Map<Integer, Integer>	buffers;
	protected static List<Integer>			all			= new ArrayList<>();
	protected static List<Buffer>			bufferCache	= new ArrayList<>();
	protected boolean						bound;

	public GLVertexArray() {
		Checks.checkIfInitialized();
		vaoID = createVAO();
		buffers = new HashMap<>();
		hasIndexBuffer = false;
		bound = true;
	}

	private static int createVAO() {
		final int vaoID = GL30.glGenVertexArrays();
		GLVertexArray.all.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
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

	protected static IntBuffer storeDataInIntBuffer(final int[] data) {
		final IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).asIntBuffer();
		bufferCache.add(buffer);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	protected static FloatBuffer storeInFloatBuffer(final float[] data) {
		final FloatBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).asFloatBuffer();
		bufferCache.add(buffer);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

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
		bufferCache.forEach(MemoryUtil::memFree);
	}

}
