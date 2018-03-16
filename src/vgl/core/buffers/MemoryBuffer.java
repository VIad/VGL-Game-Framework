package vgl.core.buffers;

abstract public class MemoryBuffer {

	final protected int	capacity;
	protected int		pointer;

	public MemoryBuffer(int capacity) {
		this.capacity = capacity;
		this.pointer = 0;
	}

	abstract public void writeInt(int value);

	abstract public void writeInt(int index, int value);

	abstract public void writeByte(byte value);

	abstract public void writeFloat(float value);

	abstract public int readInt(int index);

	abstract public float readFloat(int index);

	abstract public byte readByte(int index);

	abstract public BufferDetails nativeBufferDetails();

	abstract public int getPointer();

	abstract public void free();

}
