package vgl.natives.memory;

import vgl.core.annotation.VGLInternal;

@VGLInternal
public class VGLMemory {

	private VGLMemory() {
	}

	static native java.nio.ByteBuffer __nvglmemAllocByte(int size);

	static native void __nvglmemFree(java.nio.Buffer buffer);

	static native byte[] __nvglbyteArray(int size);

	static native short[] __nvglshortArray(int size);

	static native char[] __nvglcharArray(int size);

	static native int[] __nvglintArray(int size);

	static native float[] __nvglfloatArray(int size);

	static native long[] __nvgllongArray(int size);

	static native double[] __nvgldoubleArray(int size);
}
