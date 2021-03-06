package vgl.core.gfx.gl;

import org.lwjgl.opengl.GL15;

import vgl.main.VGL;
import vgl.platform.gl.GLBufferTarget;
import vgl.platform.gl.GLBufferUsage;

public class IndexBuffer {

	private int buffer;
	
	public IndexBuffer(int[] data) {
		this.buffer =VGL.api_gfx.glGenBuffer(); 
		bind();
		VGL.api_gfx.glBufferData(GLBufferTarget.ELEMENT_ARRAY_BUFFER.nativeGL(), data, GLBufferUsage.STATIC_DRAW);
		unbind();
	}

	public void bind() {
		VGL.api_gfx.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer);
	}


	public void unbind() {
		VGL.api_gfx.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public void dispose() {
		VGL.api_gfx.glDeleteBuffers(buffer);
	}

}
