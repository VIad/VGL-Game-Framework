package vgl.core.buffers;

import vgl.core.exception.VGLMemoryException;

public class MemoryBufferFloat extends TypedBuffer<Float> {

	public MemoryBufferFloat(int capacity) {
		super(capacity, Float.BYTES);
	}

	@Override
	public void put(Float val) {
		buffer.putFloat(bytePointer += 4, val);
		pointer++;
	}

	@Override
	public void put(int index, Float val) {
		int byteIndex = index << 2;
		if (byteIndex > (typeCapacity << 2)) {
			throw new VGLMemoryException("Buffer Overflow");
		}
		buffer.putFloat(byteIndex, val);
	}

	@Override
	public Float read() {
		try {
			return buffer.readFloat(bytePointer);
		} finally {
			bytePointer += Integer.BYTES;
			pointer++;
		}
	}

	@Override
	public Float readAbsolute(int index) {
		int byteIndex = index << 2;
		if (byteIndex > (typeCapacity << 2)) {
			throw new VGLMemoryException("Buffer Overflow");
		}
		return buffer.readFloat(byteIndex);
	}

}
