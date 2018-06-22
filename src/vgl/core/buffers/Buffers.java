package vgl.core.buffers;

import java.util.Iterator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import vgl.core.buffers.utils.BufferIterator;
import vgl.main.VGL;
import vgl.maths.vector.Matrix4f;

public class Buffers {

	private Buffers() {
	}

	public static MemoryBuffer wrap(Matrix4f matrix) {
		MemoryBuffer memBuffer = VGL.factory.newMemoryBuffer(Float.BYTES * 4 * 4);
		memBuffer.putFloat(0, matrix.m00);
		memBuffer.putFloat(4, matrix.m01);
		memBuffer.putFloat(8, matrix.m02);
		memBuffer.putFloat(12, matrix.m03);
		memBuffer.putFloat(16, matrix.m10);
		memBuffer.putFloat(20, matrix.m11);
		memBuffer.putFloat(24, matrix.m12);
		memBuffer.putFloat(28, matrix.m13);
		memBuffer.putFloat(32, matrix.m20);
		memBuffer.putFloat(36, matrix.m21);
		memBuffer.putFloat(40, matrix.m22);
		memBuffer.putFloat(44, matrix.m23);
		memBuffer.putFloat(48, matrix.m30);
		memBuffer.putFloat(52, matrix.m31);
		memBuffer.putFloat(56, matrix.m32);
		memBuffer.putFloat(60, matrix.m33);
		return memBuffer;
	}

	public static void wrap(MemoryBuffer memBuffer, Matrix4f matrix) {
		memBuffer.putFloat(0, matrix.m00);
		memBuffer.putFloat(4, matrix.m01);
		memBuffer.putFloat(8, matrix.m02);
		memBuffer.putFloat(12, matrix.m03);
		memBuffer.putFloat(16, matrix.m10);
		memBuffer.putFloat(20, matrix.m11);
		memBuffer.putFloat(24, matrix.m12);
		memBuffer.putFloat(28, matrix.m13);
		memBuffer.putFloat(32, matrix.m20);
		memBuffer.putFloat(36, matrix.m21);
		memBuffer.putFloat(40, matrix.m22);
		memBuffer.putFloat(44, matrix.m23);
		memBuffer.putFloat(48, matrix.m30);
		memBuffer.putFloat(52, matrix.m31);
		memBuffer.putFloat(56, matrix.m32);
		memBuffer.putFloat(60, matrix.m33);
	}

	public static MemoryBufferFloat wrap(final float... data) {
		MemoryBufferFloat memBuffer = new MemoryBufferFloat(data.length);
		IntStream.range(0, data.length).forEach(i -> memBuffer.put(data[i]));
		return memBuffer;
	}

	public static MemoryBufferInt wrap(final int... data) {
		MemoryBufferInt memBuffer = new MemoryBufferInt(data.length);
		IntStream.range(0, data.length).forEach(i -> memBuffer.put(data[i]));
		return memBuffer;
	}

	public static float[] toArray(MemoryBuffer buffer) {
		final float[] arr = new float[16];
		IntStream.range(0, 16).forEach(i -> arr[i] = buffer.readFloat(i * 4));
		return arr;
	}

	public static <T> Iterator<T> iterator(TypedBuffer<T> buffer) {
		return new BufferIterator<>(buffer);
	}

	
	public static <T> T[] toArray(T[] array, TypedBuffer<T> buffer) {
		// T[] array = (T[]) new Object[buffer.capacity()];
		Iterator<T> iterator = iterator(buffer);
		int ptr = 0;
		while (iterator.hasNext()) {
			array[ptr++] = iterator.next();
		}
		return array;
	}

	public static java.util.stream.DoubleStream stream(MemoryBufferFloat buffer) {
		DoubleStream.Builder builder = DoubleStream.builder();
		buffer.flip();
		iterator(buffer).forEachRemaining(builder::add);
		return builder.build();
	}

	public static java.util.stream.IntStream stream(MemoryBufferInt buffer) {
		IntStream.Builder builder = IntStream.builder();
		buffer.flip();
		iterator(buffer).forEachRemaining(builder::add);
		return builder.build();
	}

	public static <T> java.util.stream.Stream<T> stream(TypedBuffer<T> buffer) {
		Stream.Builder<T> builder = Stream.builder();
		buffer.flip();
		iterator(buffer).forEachRemaining(builder::add);
		return builder.build();
	}

}
