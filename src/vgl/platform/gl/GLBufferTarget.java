package vgl.platform.gl;

import com.shc.webgl4j.client.WebGL10;
import com.shc.webgl4j.client.WebGL20;

public enum GLBufferTarget {

	ARRAY_BUFFER, ELEMENT_ARRAY_BUFFER, UNIFORM_BUFFER;
	public int nativeGL() {
		switch (this)
		{
			case ARRAY_BUFFER:
				return WebGL10.GL_ARRAY_BUFFER;
			case ELEMENT_ARRAY_BUFFER:
				return WebGL10.GL_ELEMENT_ARRAY_BUFFER;
			case UNIFORM_BUFFER:
				return WebGL20.GL_UNIFORM_BUFFER;
			default:
				throw new Error();
		}
	}
}
