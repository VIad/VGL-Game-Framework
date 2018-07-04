package vgl.platform.gl;

import org.lwjgl.opengl.GL11;

import com.shc.webgl4j.client.WebGL10;

public enum Primitive {
	
	                 
	BYTE             (GL11.GL_BYTE, 1),
	SHORT            (GL11.GL_SHORT, 2),
	INT              (GL11.GL_INT, 4),
	FLOAT            (GL11.GL_FLOAT, 4),
	DOUBLE           (GL11.GL_DOUBLE, 8),
	UNSIGNED_INT     (GL11.GL_UNSIGNED_INT, 4),
	UNSIGNED_BYTE    (WebGL10.GL_UNSIGNED_BYTE, 1),
	UNSIGNED_SHORT   (GL11.GL_UNSIGNED_SHORT, 2);
	
	private int size;
	
	private int glType;
	
	Primitive(int glType, int size) {
		this.glType = glType;
		this.size = size;
	}
	
	public int toGLType() {
		return glType;
	}
	
	public int size() {
		return size;
	}
}
