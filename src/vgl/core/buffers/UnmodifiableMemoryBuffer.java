package vgl.core.buffers;

public class UnmodifiableMemoryBuffer extends MemoryBuffer {

	UnmodifiableMemoryBuffer(int capacity) {
		super(capacity);
	}

	@Override
	public void putInt(int index, int value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putByte(int index, int value) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void putFloat(int index, float value) {
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
	public int getPointer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void free() {
		throw new UnsupportedOperationException();
	}

}
