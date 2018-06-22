package vgl.core.gfx.shader;

import com.shc.webgl4j.client.WebGL10;

import vgl.core.exception.VGLRuntimeException;

public enum ShaderType {
	VERTEX,

	FRAGMENT,

	COMBINED;

	public int glShaderType() {
		switch (this)
		{
			case COMBINED:
			default:
				throw new VGLRuntimeException("Not supported for " + this);
			case FRAGMENT:
				return WebGL10.GL_FRAGMENT_SHADER;
			case VERTEX:
				return WebGL10.GL_VERTEX_SHADER;
		}
	}

}
