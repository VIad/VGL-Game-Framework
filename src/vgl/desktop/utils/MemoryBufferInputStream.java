package vgl.desktop.utils;

import java.io.IOException;
import java.io.InputStream;

import vgl.core.buffers.MemoryBuffer;

public class MemoryBufferInputStream extends InputStream {

	final private MemoryBuffer memoryBuffer;

	public MemoryBufferInputStream(MemoryBuffer buffer) {
		this.memoryBuffer = buffer;
	}

	@Override
	public int read() throws IOException {
		return memoryBuffer.readByte(memoryBuffer.getPointer());
	}

}
