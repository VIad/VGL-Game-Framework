package vgl.platform.gl;

import com.shc.webgl4j.client.WebGL10;

public enum GLPrimitiveMode {

	POINTS, LINES, LINE_LOOP, QUADS, LINE_STRIP, TRIANGLES, TRIANGLE_STRIP, TRIANGLE_FAN;

	public int nativeGL() {
		switch (this)
		{
			case LINES:
				return WebGL10.GL_LINES;
			case QUADS:
				return 7;// glQuads
			case LINE_LOOP:
				return WebGL10.GL_LINE_LOOP;
			case LINE_STRIP:
				return WebGL10.GL_LINE_STRIP;
			case POINTS:
				return WebGL10.GL_POINTS;
			case TRIANGLES:
				return WebGL10.GL_TRIANGLES;
			case TRIANGLE_FAN:
				return WebGL10.GL_TRIANGLE_FAN;
			case TRIANGLE_STRIP:
				return WebGL10.GL_TRIANGLE_STRIP;
			default:
				throw new Error();
		}
	}

}
