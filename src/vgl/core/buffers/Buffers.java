package vgl.core.buffers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import vgl.core.buffers.utils.BufferIterator;
import vgl.core.exception.VGLMemoryException;
import vgl.main.VGL;
import vgl.maths.vector.Matrix4f;

abstract public class Buffers {

	private Buffers() {
	}

	public static MemoryBufferFloat wrap(Matrix4f matrix) {
		return new MemoryBufferFloat(4 * 4)
				.put(matrix.m00).put(matrix.m01).put(matrix.m02).put(matrix.m03)
				.put(matrix.m10).put(matrix.m11).put(matrix.m12).put(matrix.m13)
				.put(matrix.m20).put(matrix.m21).put(matrix.m22).put(matrix.m23)
				.put(matrix.m30).put(matrix.m31).put(matrix.m32).put(matrix.m33);
	}
	
	public static MemoryBuffer wrap(byte... data) {
		MemoryBuffer buffer = VGL.factory.dataBuffer(data.length);
		for (int i = 0; i < data.length; i++) {
			buffer.putByte(i, data[i]);
		}
		return buffer;
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

	public static MemoryBuffer combine(MemoryBuffer... buffers) {
		int capacity = Arrays.stream(buffers)
				             .mapToInt(b -> b.capacity())
				             .sum();
		MemoryBuffer combined = VGL.factory.dataBuffer(capacity);
		int index = 0;
		for(MemoryBuffer buffer : buffers) {
			for(int i = 0; i < buffer.capacity(); i++) {				
				combined.putByte(index++, buffer.readByte(i));
			}
		}
		return combined;
	}

	public static <T> Iterator<T> iterator(TypedBuffer<T> buffer) {
		return new BufferIterator<>(buffer);
	}
	
	public static float[] castToFloatUnsafe(MemoryBuffer memory) {
		if (memory.capacity() % Float.BYTES != 0)
			throw new VGLMemoryException("Memory cast failed, bytes aren't divisible by 4");
		float[] array = new float[memory.capacity() / 4];
		for (int i = 0; i < array.length; i++) {
			try {
				array[i] = memory.readFloat(i * 4);// Could fail
			} catch (Exception ex) {
				throw new VGLMemoryException("Memory cast failed, internal buffer error");
			}
		}
		return array;
	}

	public static int[] castToIntUnsafe(MemoryBuffer memory) {
		if (memory.capacity() % Integer.BYTES != 0)
			throw new VGLMemoryException("Memory cast failed, bytes aren't divisible by 4");
		int[] array = new int[memory.capacity() / Integer.BYTES];
		for (int i = 0; i < array.length; i++) {
			try {
				array[i] = memory.readInt(i * 4);// Could fail
			} catch (Exception ex) {
				throw new VGLMemoryException("Memory cast failed, internal buffer error");
			}
		}
		return array;
	}
	
	public static MemoryBuffer emptyBuffer(int size) {
		return VGL.factory.dataBuffer(size);
	}

	
//	public static <T> T[] toArray(T[] array, TypedBuffer<T> buffer) {
//		// T[] array = (T[]) new Object[buffer.capacity()];
//		Iterator<T> iterator = iterator(buffer);
//		int ptr = 0;
//		while (iterator.hasNext()) {
//			array[ptr++] = iterator.next();
//		}
//		return array;
//	}
	
	public static <T> T[] toArray(TypedBuffer<T> buffer, IntFunction<T[]> arrayProducer) {
		T[] array = arrayProducer.apply(buffer.capacity());
		Iterator<T> it = iterator(buffer);
		int ptr = 0;
		while(it.hasNext()) {
			array[ptr++] = it.next();
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
