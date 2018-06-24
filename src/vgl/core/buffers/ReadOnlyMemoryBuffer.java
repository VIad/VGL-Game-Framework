package vgl.core.buffers;

public class ReadOnlyMemoryBuffer extends MemoryBuffer {

	ReadOnlyMemoryBuffer(int capacity) {
		super(capacity);
	}

	@Override
	public MemoryBuffer putInt(int index, int value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MemoryBuffer putByte(int index, int value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MemoryBuffer putFloat(int index, float value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readInt(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float readFloat(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte readByte(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BufferDetails nativeBufferDetails() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void free() {
		throw new UnsupportedOperationException();
	}

}
