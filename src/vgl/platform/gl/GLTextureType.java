package vgl.platform.gl;

import com.shc.webgl4j.client.WebGL10;

public enum GLTextureType {

	TEXTURE_2D, TEXTURE_CUBE_MAP;

	public int gl() {
		switch (this)
		{
			case TEXTURE_2D:
				return WebGL10.GL_TEXTURE_2D;
			case TEXTURE_CUBE_MAP:
				return WebGL10.GL_TEXTURE_CUBE_MAP;
			default:
				throw new Error();
		}
	}
}
