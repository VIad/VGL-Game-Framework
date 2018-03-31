package vgl.core.buffers;

import vgl.main.VGL;

abstract public class TypedBuffer<T> {

	protected MemoryBuffer	buffer;

	protected int			pointer;
	protected int			bytePointer;
	protected int			typeCapacity;
	protected final int		dataSizeBytes;

	protected TypedBuffer(int capacity, int dataSize) {
		this.dataSizeBytes = dataSize;
		this.pointer = 0;
		this.bytePointer = 0;
		this.typeCapacity = capacity;
		this.buffer = VGL.factory.newMemoryBuffer(capacity * dataSize);
	}

	public MemoryBuffer getBuffer() {
		return buffer;
	}

	public int pointer() {
		return pointer;
	}

	protected int bytePointer() {
		return bytePointer;
	}

	public int getCapacity() {
		return typeCapacity;
	}

	public void flip() {
		pointer = 0;
		bytePointer = 0;
	}

	abstract public void put(T val);

	abstract public void put(int index, T val);

	abstract public T read();

	abstract public T readAbsolute(int index);

}
