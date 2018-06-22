package vgl.desktop.utils;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;

import vgl.core.gfx.Color;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;

public class VertexBufferUtils {

	public static int createEmtpyVBO(final int floatAmmount) {
		final int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatAmmount * Float.BYTES, GL15.GL_STREAM_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	public static void addInstancedAttribute(final int vao, final int vbo, final int attribute, final int dataSize,
	                                         final int instancedStrideLength, final int offset) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL30.glBindVertexArray(vao);
		GL20.glVertexAttribPointer(attribute, dataSize, GL11.GL_FLOAT, false, instancedStrideLength * Float.BYTES,
		                           offset * Float.BYTES
		);
		GL33.glVertexAttribDivisor(attribute, 1);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}

	public static void updateVBO(final int vbo, final float[] data, final FloatBuffer buffer) {
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * 4, GL15.GL_STREAM_DRAW);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public static void __storeVec(FloatBuffer buffer, Vector3f vec, int dataSize) {
		buffer.put(vec.x
		).put(vec.y).put(vec.z);
		dataSize -= 3;
		if (dataSize != 0) {
			for (int i = 0; i < dataSize; i++) {
				buffer.put(0f);
			}
		}
	}

	public static void __storeVec(FloatBuffer buffer, Vector2f vec, int dataSize) {
		buffer.put(vec.x
		).put(vec.y);
		dataSize -= 2;
		if (dataSize != 0) {
			for (int i = 0; i < dataSize; i++) {
				buffer.put(0f);
			}
		}
	}

	public static void __storeCol(FloatBuffer buffer, Color color, int dataSize) {
		buffer.put(color.getRed()).put(color.getGreen()).put(color.getBlue()).put(color.getAlpha());
		dataSize -= 4;
		if (dataSize != 0) {
			for (int i = 0; i < dataSize; i++) {
				buffer.put(0f);
			}
		}
	}

}
