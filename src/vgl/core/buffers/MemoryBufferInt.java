package vgl.core.buffers;

import java.nio.Buffer;

import vgl.core.exception.VGLMemoryException;
import vgl.main.VGL;

public class MemoryBufferInt extends TypedBuffer<Integer> {

	public MemoryBufferInt(int capacity) {
		super(capacity, Integer.BYTES);
	}

	@Override
	public void put(Integer val) {
		buffer.putInt(bytePointer += 4, val);
		pointer++;
	}

	@Override
	public void put(int index, Integer val) {
		int byteIndex = index << 2;
		if (byteIndex > (typeCapacity << 2)) {
			throw new VGLMemoryException("Buffer Overflow");
		}
		buffer.putInt(byteIndex, val);
	}

	@Override
	public Integer read() {
		try {
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

	//
	// public int getCapacity() {
	// return typeCapacity;
	// }
	//
	// public MemoryBuffer getBuffer() {
	// return buffer;
	// }
	//
	// public int pointer() {
	// return pointer;
	// }
	//
	// int _bytePointer() {
	// return bytePointer;
	// }
	//
	// public void put(int val) {
	// buffer.putInt(bytePointer += 4, val);
	// pointer++;
	// }
	//
	// public void put(int index, int val) {
	// int byteIndex = index << 2;
	// if (byteIndex > (intCapacity << 2)) {
	// throw new VGLMemoryException("Buffer Overflow");
	// }
	// buffer.putInt(byteIndex, val);
	// }
	//
	// public void readInt() {
	// buffer.readInt(bytePointer);
	// bytePointer += 4;
	// pointer++;
	// }
	//
	// public void flip() {
	// pointer = 0;
	// bytePointer = 0;
	// }

}
