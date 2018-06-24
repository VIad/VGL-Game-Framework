package vgl.core.buffers;

import vgl.core.exception.VGLMemoryException;

public class MemoryBufferFloat extends TypedBuffer<Float> {

	public MemoryBufferFloat(int capacity) {
		super(capacity, Float.BYTES);
	}

	@Override
	public MemoryBufferFloat put(Float val) {
		try {
			if (pointer == capacity())
				throw new VGLMemoryException("Buffer Overflow");
			buffer.putFloat(bytePointer, val);
		} finally {
			bytePointer += Float.BYTES;
			pointer++;
		}
		return this;
	}

	@Override
	public MemoryBufferFloat put(int index, Float val) {
		int byteIndex = index << 2;
		if (byteIndex > (typeCapacity << 2)) {
			throw new VGLMemoryException("Buffer Overflow");
		}
		buffer.putFloat(byteIndex, val);
		return this;
	}

	@Override
	public Float read() {
		try {
			if (pointer == capacity())
				throw new VGLMemoryException("Buffer Overflow");
			return buffer.readFloat(bytePointer);
		} finally {
			bytePointer += Float.BYTES;
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
