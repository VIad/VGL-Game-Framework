package vgl.core.buffers;

import java.util.stream.IntStream;

import vgl.main.VGL;

abstract public class MemoryBuffer {

	final protected int					capacity;

	public static final MemoryBuffer	EMPTY_BUFFER	= new ReadOnlyMemoryBuffer(0);

	public MemoryBuffer(int capacity) {
		this.capacity = capacity;
	}

	abstract public MemoryBuffer putInt(int index, int value);

	abstract public MemoryBuffer putByte(int index, int value);

	abstract public MemoryBuffer putFloat(int index, float value);

	abstract public int readInt(int index);

	abstract public float readFloat(int index);

	abstract public byte readByte(int index);

	abstract public BufferDetails nativeBufferDetails();

	abstract public void free();

	public int capacity() {
		return capacity;
	}

}
