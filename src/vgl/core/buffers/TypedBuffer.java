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
		this.buffer = VGL.factory.dataBuffer(capacity * dataSize);
		VGL.io.memset(buffer, (byte) 0); // Make sure no garbage is left in buffers, this is java ffs :)
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

	public int capacity() {
		return typeCapacity;
	}

	public int byteCapacity() {
		return typeCapacity * dataSizeBytes;
	}

	public void flip() {
		pointer = 0;
		bytePointer = 0;
	}

	abstract public TypedBuffer<T> put(T val);

	abstract public TypedBuffer<T> put(int index, T val);

	abstract public T read();

	abstract public T readAbsolute(int index);

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[pointer=" + pointer + ", bytePointer=" + bytePointer
		        + ", typeCapacity=" + typeCapacity + ", dataSizeBytes=" + dataSizeBytes + "]";
	}

}
