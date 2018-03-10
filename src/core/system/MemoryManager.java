package vgl.core.system;

import vgl.natives.memory.MemoryAccess;

//============================================================================
//Name        : MemoryManager
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : Class containing various low - level utility methods
//for information, as well as allocation and others.
//============================================================================
/**
 * Class containing various low - level utility methods for information, as well
 * as allocation and others.
 * 
 * @author vgl
 *
 */
public class MemoryManager {

	private MemoryManager() {

	}

	/**
	 * 
	 * <b> Turning safe mode off will allow for low level memory operations </b>
	 * <br>
	 * <strong> WARNING: All memory altered by the unsafe methods will NOT BE SAFELY
	 * GARBAGE COLLECTED, so it is recommended to use <br>
	 * {@link MemoryManager.memFree} when you want a buffer deleted</strong>
	 * 
	 */
	public static boolean SAFE_MODE = true;

	/**
	 * @return - The current ram usage of the VGL Application (in bytes)
	 */
	public static int usedMemoryB() {
		return (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
	}

	/**
	 * @return - The current ram usage of the VGL Application (in kilobytes)
	 */
	public static int usedMemoryKB() {
		return (int) ((Runtime.getRuntime().totalMemory() / 1024 - Runtime.getRuntime().freeMemory()) / 1024);
	}

	/**
	 *
	 * @return - The current ram usage of the VGL Application (in megabytes)
	 *
	 */
	public static float usedMemoryMB() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
	}

	/**
	 * Unsafe method<br>
	 * <strong> The {@link java.nio.FloatBuffer} object returned will (most likelly)
	 * NOT BE GARBAGE COLLECTED</strong>
	 * 
	 * @param size
	 *            - the size of the buffer (i.e capacity)
	 * @return the Buffer object
	 */
	public static java.nio.FloatBuffer mallocFloat(int size) {
		check();
		return MemoryAccess._allocBufferFloat(size);
	}

	/**
	 * Unsafe method<br>
	 * <strong> The {@link java.nio.IntBuffer} object returned will (most likelly)
	 * NOT BE GARBAGE COLLECTED</strong>
	 * 
	 * @param size
	 *            - the size of the buffer (i.e capacity)
	 * @return the Buffer object
	 */
	public static java.nio.IntBuffer mallocInt(int size) {
		check();
		return MemoryAccess._allocBufferInt(size);
	}

	/**
	 * Unsafe method<br>
	 * <strong> The {@link java.nio.DoubleBuffer} object returned will (most
	 * likelly) NOT BE GARBAGE COLLECTED</strong>
	 * 
	 * @param size
	 *            - the size of the buffer (i.e capacity)
	 * @return the Buffer object
	 */
	public static java.nio.DoubleBuffer mallocDouble(int size) {
		check();
		return MemoryAccess._allocBufferDouble(size);
	}

	/**
	 * Unsafe method<br>
	 * <strong> The {@link java.nio.LongBuffer} object returned will (most likelly)
	 * NOT BE GARBAGE COLLECTED</strong>
	 * 
	 * @param size
	 *            - the size of the buffer (i.e capacity)
	 * @return the Buffer object
	 */
	public static java.nio.LongBuffer mallocLong(int size) {
		check();
		return MemoryAccess._allocBufferLong(size);
	}

	/**
	 * Unsafe method<br>
	 * Frees the {@link java.nio.Buffer} object passed and deletes it from
	 * memory<br>
	 * <strong> Using this function on a normally allocated Buffer, effectivelly
	 * does the Garbage Collector's job <br>
	 * and might be hard to debug </strong>
	 * 
	 * @param buffer
	 *            - the buffer to free
	 */
	public static void memFree(java.nio.Buffer buffer) {
		check();
		MemoryAccess._memFreeBuffer(buffer);
	}

	private static void check() {
		if (SAFE_MODE)
			throw new vgl.core.exception.VGLFatalError(
			        "Cannot use memory functions while in safe mode, field : MemoryManager.SAFE_MODE");
	}

}
