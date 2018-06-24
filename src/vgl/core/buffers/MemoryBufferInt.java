package vgl.core.buffers;

import vgl.core.exception.VGLMemoryException;

public class MemoryBufferInt extends TypedBuffer<Integer> {

	public MemoryBufferInt(int capacity) {
		super(capacity, Integer.BYTES);
	}

	@Override
	public MemoryBufferInt put(Integer val) {
		try {
			if (pointer == capacity())
				throw new VGLMemoryException("Buffer Overflow");
			buffer.putInt(bytePointer, val);
		} finally {
			bytePointer += Integer.BYTES;
			pointer++;
		}
		return this;
	}

	@Override
	public MemoryBufferInt put(int index, Integer val) {
		int byteIndex = index << 2;
		if (byteIndex > (typeCapacity << 2)) {
			throw new VGLMemoryException("Buffer Overflow");
		}
		buffer.putInt(byteIndex, val);
		return this;
	}

	@Override
	public Integer read() {
		try {
			if (pointer == capacity())
				throw new VGLMemoryException("Buffer Overflow");
			return buffer.readInt(bytePointer);
		} finally {
			bytePointer += Integer.BYTES;
			pointer++;
		}
	}

	@Override
	public Integer readAbsolute(int index) {
		int byteIndex = index << 2;
		if (byteIndex > (typeCapacity << 2)) {
			throw new VGLMemoryException("Buffer Overflow");
		}
		return buffer.readInt(byteIndex);
	}
}
