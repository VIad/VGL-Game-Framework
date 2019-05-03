package vgl.platform;

import com.shc.webgl4j.client.WebGL10;

import vgl.core.buffers.MemoryBuffer;
import vgl.core.gfx.shader.ShaderType;
import vgl.platform.gl.GLBufferTarget;
import vgl.platform.gl.GLPrimitiveMode;
import vgl.platform.gl.GLTextureType;

public interface IGraphicsPlatorm {
	// Creating Objects

	int glGenVertexArray();

	int glGenBuffer();

	int glGenFramebuffer();

	int glGenTexture();
	
	int glGenRenderbuffer();

	// Binding objects

	void glBindBuffer(GLBufferTarget target, int buffer);

	void glBindBuffer(int target, int buffer);

	void glBindVertexArray(int vao);

	void glEnableVertexAttribArray(int index);

	void glDisableVertexAttribArray(int index);
	
	void glBindRenderbuffer(int target, int renderBuffer);
	
	void glDeleteRenderbuffer(int renderBufferID);
	
	int glGetRenderbufferParameteri(int target, int pname);
	
	void glRenderbufferStorage(int target, int internalFormat, int width, int height);
	
	void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer);

	int glCheckFramebufferStatus(int target);
	
	void glBindFramebuffer(int target, int fbo);
	
	void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level);
	
	// Buffer data

	void glBufferData(GLBufferTarget target, long size, int usage);

	void glBufferData(GLBufferTarget target, MemoryBuffer data, int usage);

	void glBufferData(GLBufferTarget target, float[] data, int usage);

	void glBufferData(int target, long size, int usage);

	void glBufferData(int target, MemoryBuffer data, int usage);

	void glBufferData(int target, float[] data, int usage);
	
	void glBufferData(int target, int[] data, int usage);

	void glBufferSubData(int target, int offset, MemoryBuffer data);

	void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long pointer);

	// Textures

	void glActiveTexture(int texture);

	void glBindTexture(GLTextureType type, int texObjectID);

	void glBindTexture(int type, int texObjectID);

	void glTexParameteri(int target, int param, int val);

	void glGenMipmap(int target);

	void glDeleteTexture(int... textures);

	void glTexImage2D(int target, int level, int internalF, int width, int height, int border, int format, int type,
	        MemoryBuffer data);
	
	void glReadPixels(int x, int y, int w, int h, int format, int type, MemoryBuffer pixels);

	// Core GL
	void glEnable(int what);

	void glDisable(int what);

	void glClear(int flags);

	void glClearColor(float r, float g, float b, float a);

	void glDepthMask(boolean val);

	void glDepthFunc(int func);

	void glCullFace(int face);

	int glGetError();

	void glBlendFunc(int sfactor, int dfactor);

	void glViewport(int x, int y, int width, int height);

	// Draw Calls
	void glDrawElements(GLPrimitiveMode primitiveType, int indexCount, int type, long indices);

	void glDrawArrays(GLPrimitiveMode primitiveType, int first, int count);

	// Delete calls
	void glDeleteBuffers(int... buffers);

	void glDeleteVertexArrays(int... vaos);

	// Shaders
	int glCreateProgram();

	void glAttachShader(int program, int shader);

	void glLinkProgram(int program);

	void glValidateProgram(int program);

	int glGetUniformLocation(int program, String name);

	void glBindAttribLocation(int program, int attribute, String variableName);

	int glGetAttributeLocation(int program, String name);

	void glUseProgram(int program);

	void glUniform1i(int location, int v);

	void glUniform2i(int location, int v1, int v2);

	void glUniform3i(int location, int v1, int v2, int v3);

	void glUniform4i(int location, int v1, int v2, int v3, int v4);

	void glUniform1f(int location, float v);

	void glUniform2f(int location, float v1, float v2);

	void glUniform3f(int location, float v1, float v2, float v3);

	void glUniform4f(int location, float v1, float v2, float v3, float v4);

	void glUniformMatrix4fv(int location, boolean transpose, MemoryBuffer matrix);

	void glUniformMatrix3fv(int location, boolean transpose, MemoryBuffer matrix);

	void glUniform1fv(int location, float[] data);

	void glUniform1iv(int location, int[] data);

	void glDetachShader(int program, int shader);

	void glDeleteShader(int shader);

	void glDeleteProgram(int... id);

	int glCreateShader(ShaderType type);
	
	int glGetProgrami(int program, int flag);

	void glShaderSource(int shader, String source);

	void glCompileShader(int shader);

	int glGetShaderi(int shader, int param);

	void glDeleteShader(int... shaders);

	String glGetShaderInfoLog(int shaderID);

	boolean glGetBoolean(int which);

	void glReadBuffer(int glFront);

}
