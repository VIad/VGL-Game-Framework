package vgl.utils;

import vgl.core.buffers.MemoryBuffer;
import vgl.main.VGL;

public class BufferStorageSafe {

	public static MemoryBuffer storeInBuffer(final float[] data) {
		MemoryBuffer memBuffer = VGL.factory.newMemoryBuffer(data.length << 2);
		for (int i = 0; i < data.length; i++) {
			memBuffer.writeFloat(data[i]);
		}
		return memBuffer;
	}

	public static MemoryBuffer storeInBuffer(final int[] data) {
		MemoryBuffer memBuffer = VGL.factory.newMemoryBuffer(data.length << 2);
		for (int i = 0; i < data.length; i++) {
			memBuffer.writeInt(data[i]);
		}
		return memBuffer;
	}

}
