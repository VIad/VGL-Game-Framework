package vgl.desktop.storage;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import vgl.maths.vector.Matrix4f;

public class BufferStorageUnsafe {

	@Deprecated
	public static FloatBuffer store(Matrix4f m4f, FloatBuffer buffer) {
		buffer.put(m4f.m00);
		buffer.put(m4f.m01);
		buffer.put(m4f.m02);
		buffer.put(m4f.m03);
		buffer.put(m4f.m10);
		buffer.put(m4f.m11);
		buffer.put(m4f.m12);
		buffer.put(m4f.m13);
		buffer.put(m4f.m20);
		buffer.put(m4f.m21);
		buffer.put(m4f.m22);
		buffer.put(m4f.m23);
		buffer.put(m4f.m30);
		buffer.put(m4f.m31);
		buffer.put(m4f.m32);

		buffer.put(m4f.m33);
		buffer.flip();
		return buffer;
	}

	@Deprecated
	public static IntBuffer storeDataInIntBuffer(final int[] data) {
		final IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).asIntBuffer();
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	@Deprecated
	public static FloatBuffer storeInFloatBuffer(final float[] data) {
		final FloatBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).asFloatBuffer();
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}
