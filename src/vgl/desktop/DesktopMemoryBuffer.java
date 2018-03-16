package vgl.desktop;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import vgl.core.buffers.BufferDetails;
import vgl.core.buffers.MemoryBuffer;

public class DesktopMemoryBuffer extends MemoryBuffer {

	private ByteBuffer		direct;
	private BufferDetails	details;

	public DesktopMemoryBuffer(int capacity) {
		super(capacity);
		this.direct = ByteBuffer.allocateDirect(capacity);
		this.details = new BufferDetails(direct, capacity);
	}

	@Override
	public void writeInt(int value) {
		direct.putInt(value);
	}

	@Override
	public void writeInt(int index, int value) {
		direct.putInt(index, value);
	}

	@Override
	public void writeByte(byte value) {
		direct.put(value);
	}

	@Override
	public void writeFloat(float value) {
		direct.putFloat(value);
	}

	@Override
	public int readInt(int index) {
		return direct.getInt(index);
	}

	@Override
	public float readFloat(int index) {
		return direct.getFloat(index);
	}

	@Override
	public byte readByte(int index) {
		return direct.get(index);
	}

	@Override
	public BufferDetails nativeBufferDetails() {
		return details;
	}

	public static DesktopMemoryBuffer wrap(ByteBuffer nioBuffer) {
		DesktopMemoryBuffer buffer = new DesktopMemoryBuffer(nioBuffer.capacity());
		nioBuffer.flip();
		for (int i = 0; i < nioBuffer.capacity(); i++) {
			buffer.writeByte(nioBuffer.get());
		}
		return buffer;
	}

	public static DesktopMemoryBuffer wrap(IntBuffer nioBuffer) {
		DesktopMemoryBuffer buffer = new DesktopMemoryBuffer(nioBuffer.capacity() * Integer.BYTES);
		nioBuffer.flip();
		for (int i = 0; i < nioBuffer.capacity(); i++) {
			buffer.writeInt(nioBuffer.get());
		}
		return buffer;
	}

	public static DesktopMemoryBuffer wrap(FloatBuffer nioBuffer) {
		DesktopMemoryBuffer buffer = new DesktopMemoryBuffer(nioBuffer.capacity() * Float.BYTES);
		nioBuffer.flip();
		for (int i = 0; i < nioBuffer.capacity(); i++) {
			buffer.writeFloat(nioBuffer.get());
		}
		return buffer;
	}

	@Override
	public int getPointer() {
		return direct.position();
	}
}
