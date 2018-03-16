package vgl.desktop.utils;

import java.io.IOException;
import java.io.OutputStream;

import vgl.core.buffers.MemoryBuffer;

@Deprecated
public class MemoryBufferOutputStream extends OutputStream {

	final private MemoryBuffer buffer;

	public MemoryBufferOutputStream(MemoryBuffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void write(int b) throws IOException {

	}

}
