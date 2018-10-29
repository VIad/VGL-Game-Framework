package vgl.core.internal;

import vgl.main.VGL;
import vgl.platform.gl.GLBufferTarget;

public class gpu_device_l {

	public static int alloc_buffer(int size, int usage) {
		int buffer = VGL.api_gfx.glGenBuffer();
		VGL.api_gfx.glBindBuffer(GLBufferTarget.ARRAY_BUFFER, buffer);
		VGL.api_gfx.glBufferData(GLBufferTarget.ARRAY_BUFFER, size, usage);
		VGL.api_gfx.glBindBuffer(GLBufferTarget.ARRAY_BUFFER, 0);
		return buffer;
	}
	
}
