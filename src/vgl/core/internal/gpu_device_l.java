package vgl.core.internal;

import vgl.main.VGL;
import vgl.platform.gl.GLBufferTarget;
import vgl.platform.gl.GLBufferUsage;

public class gpu_device_l {

	public static int alloc_buffer(int size, int usage) {
		int buffer = VGL.api.glGenBuffer();
		VGL.api.glBufferData(GLBufferTarget.ARRAY_BUFFER, size, usage);
		return buffer;
	}
	
}
