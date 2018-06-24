package vgl.desktop;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

import vgl.core.buffers.BufferDetails;
import vgl.core.buffers.MemoryBuffer;

public class DesktopMemoryBuffer extends MemoryBuffer {

	private ByteBuffer		direct;
	private BufferDetails	details;

	public DesktopMemoryBuffer(int capacity) {
		super(capacity);
		this.direct = MemoryUtil.memAlloc(capacity);
		this.details = new BufferDetails(direct, capacity);
	}

	@Override
	public DesktopMemoryBuffer putInt(int index, int value) {
		direct.putInt(index, value);
		return this;
	}

	@Override
	public DesktopMemoryBuffer putByte(int index, int value) {
		direct.put(index, (byte) value);
		return this;
	}

	@Override
	public DesktopMemoryBuffer putFloat(int index, float value) {
		direct.putFloat(index, value);
		return this;
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
			buffer.putByte(i, nioBuffer.get());
		}
		return buffer;
	}

	public static DesktopMemoryBuffer wrap(IntBuffer nioBuffer) {
		DesktopMemoryBuffer buffer = new DesktopMemoryBuffer(nioBuffer.capacity() * Integer.BYTES);
		nioBuffer.flip();
		for (int i = 0; i < nioBuffer.capacity(); i++) {
			buffer.putInt(i, nioBuffer.get());
		}
		return buffer;
	}

	public static DesktopMemoryBuffer wrap(FloatBuffer nioBuffer) {
		DesktopMemoryBuffer buffer = new DesktopMemoryBuffer(nioBuffer.capacity() * Float.BYTES);
		nioBuffer.flip();
		for (int i = 0; i < nioBuffer.capacity(); i++) {
			buffer.putFloat(i * 4, nioBuffer.get());
		}
		return buffer;
	}

	@Override
	public void free() {
		MemoryUtil.memFree(direct);
	}
}
