package vgl.core.buffers;

import java.util.stream.IntStream;

import vgl.main.VGL;

abstract public class MemoryBuffer {

	final protected int					capacity;
	protected int						pointer;

	public static final MemoryBuffer	EMPTY_BUFFER	= new UnmodifiableMemoryBuffer(0);

	public MemoryBuffer(int capacity) {
		this.capacity = capacity;
		this.pointer = 0;
	}

	abstract public void putInt(int index, int value);

	abstract public void putByte(int index, int value);

	abstract public void putFloat(int index, float value);

	abstract public int readInt(int index);

	abstract public float readFloat(int index);

	abstract public byte readByte(int index);

	abstract public BufferDetails nativeBufferDetails();

	abstract public int getPointer();

	abstract public void free();

	public static MemoryBuffer wrap(final byte... bytes) {
		assert bytes.length > 0;
		MemoryBuffer buffer = VGL.factory.newMemoryBuffer(bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			buffer.putByte(i, bytes[i]);
		}
		return buffer;
	}

	public static MemoryBuffer wrap(final int... ints) {
		assert ints.length > 0;
		MemoryBufferInt buffer = new MemoryBufferInt(ints.length);
		IntStream.range(0, ints.length).forEach(i -> buffer.put(ints[i]));
		return buffer.getBuffer();
	}

	public static MemoryBuffer wrap(final float... floats) {
		assert floats.length > 0;
		MemoryBufferFloat buffer = new MemoryBufferFloat(floats.length);
		IntStream.range(0, floats.length).forEach(i -> buffer.put(floats[i]));
		return buffer.getBuffer();
	}

}
