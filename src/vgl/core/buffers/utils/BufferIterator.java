package vgl.core.buffers.utils;

import java.util.Iterator;

import vgl.core.buffers.TypedBuffer;

public class BufferIterator<T> implements Iterator<T> {

	private TypedBuffer<T> buffer;

	public BufferIterator(TypedBuffer<T> tb) {
		this.buffer = tb;
		tb.flip();
	}

	@Override
	public boolean hasNext() {
		return buffer.pointer() < buffer.capacity();
	}

	@Override
	public T next() {
		return buffer.read();
	}

}
