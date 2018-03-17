package vgl.platform.gl;

import org.lwjgl.opengl.GL11;

public enum GLPrimitiveMode {

	POINTS, LINES, LINE_LOOP, QUADS, LINE_STRIP, TRIANGLES, TRIANGLE_STRIP, TRIANGLE_FAN;

	public int nativeGL() {
		switch (this)
		{
			case LINES:
				return GL11.GL_LINES;
			case QUADS:
				return GL11.GL_QUADS;
			case LINE_LOOP:
				return GL11.GL_LINE_LOOP;
			case LINE_STRIP:
				return GL11.GL_LINE_STRIP;
			case POINTS:
				return GL11.GL_POINTS;
			case TRIANGLES:
				return GL11.GL_TRIANGLES;
			case TRIANGLE_FAN:
				return GL11.GL_TRIANGLE_FAN;
			case TRIANGLE_STRIP:
				return GL11.GL_TRIANGLE_STRIP;
			default:
				throw new Error();
		}
	}

}
