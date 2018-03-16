package vgl.core.gfx.shader;

import org.lwjgl.opengl.GL20;

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
				return GL20.GL_FRAGMENT_SHADER;
			case VERTEX:
				return GL20.GL_VERTEX_SHADER;
		}
	}

}
