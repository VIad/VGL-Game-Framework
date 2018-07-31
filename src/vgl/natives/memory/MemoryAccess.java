package vgl.natives.memory;

import static vgl.natives.memory.VGLMemory.__nvglbyteArray;
import static vgl.natives.memory.VGLMemory.__nvglcharArray;
import static vgl.natives.memory.VGLMemory.__nvgldoubleArray;
import static vgl.natives.memory.VGLMemory.__nvglfloatArray;
import static vgl.natives.memory.VGLMemory.__nvglintArray;
import static vgl.natives.memory.VGLMemory.__nvgllongArray;
import static vgl.natives.memory.VGLMemory.__nvglmemAllocByte;
import static vgl.natives.memory.VGLMemory.__nvglmemFree;
import static vgl.natives.memory.VGLMemory.__nvglshortArray;

import vgl.core.annotation.VGLInternal;

/**
 * No
 *
 */
@VGLInternal
public class MemoryAccess {
	
	public static java.nio.ByteBuffer _allocBufferByte(int size) {
		return __nvglmemAllocByte(size);
	}

	public static java.nio.FloatBuffer _allocBufferFloat(int size) {
		return __nvglmemAllocByte(size << 2).asFloatBuffer();
	}

	public static java.nio.IntBuffer _allocBufferInt(int size) {
		return __nvglmemAllocByte(size << 2).asIntBuffer();
	}

	public static java.nio.LongBuffer _allocBufferLong(int size) {
		return __nvglmemAllocByte(size << 3).asLongBuffer();
	}

	public static java.nio.DoubleBuffer _allocBufferDouble(int size) {
		return __nvglmemAllocByte(size << 3).asDoubleBuffer();
	}

	public static void _memFreeBuffer(java.nio.Buffer buffer) {
		__nvglmemFree(buffer);
	}

	@Deprecated
	public static byte[] _byteArray(int size) {
		return __nvglbyteArray(size);
	}

	@Deprecated
	public static short[] _shortArray(int size) {
		return __nvglshortArray(size);
	}

	@Deprecated
	public static char[] _charArray(int size) {
		return __nvglcharArray(size);
	}

	@Deprecated
	public static int[] _intArray(int size) {
		return __nvglintArray(size);
	}

	@Deprecated
	public static float[] _floatArray(int size) {
		return __nvglfloatArray(size);
	}

	@Deprecated
	public static long[] _longArray(int size) {
		return __nvgllongArray(size);
	}

	@Deprecated
	public static double[] _doubleArray(int size) {
		return __nvgldoubleArray(size);
	}

}
