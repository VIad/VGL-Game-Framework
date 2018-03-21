package vgl.platform;

import vgl.core.buffers.MemoryBuffer;
import vgl.core.gfx.shader.ShaderType;
import vgl.platform.gl.GLBufferTarget;
import vgl.platform.gl.GLPrimitiveMode;
import vgl.platform.gl.GLTextureType;

public interface GraphicsPlatform {
	// Creating Objects

	int glGenVertexArray();

	int glGenBuffer();

	int glGenFramebuffer();

	int glGenTexture();

	// Binding objects

	void glBindBuffer(GLBufferTarget target, int buffer);

	void glBindBuffer(int target, int buffer);

	void glBindVertexArray(int vao);

	void glEnableVertexAttribArray(int index);

	void glDisableVertexAttribArray(int index);

	// Buffer data

	void glBufferData(GLBufferTarget target, long size, int usage);

	void glBufferData(GLBufferTarget target, MemoryBuffer data, int usage);

	void glBufferData(GLBufferTarget target, float[] data, int usage);

	void glBufferData(int target, long size, int usage);

	void glBufferData(int target, MemoryBuffer data, int usage);

	void glBufferData(int target, float[] data, int usage);

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

	// Core GL
	void glEnable(int what);

	void glDisable(int what);

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

	int glGetUniformLocation(int program, CharSequence name);

	int glGetAttributeLocation(int program, CharSequence name);

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

	void glDeleteProgram(int... id);

	int glCreateShader(ShaderType type);

	void glShaderSource(int shader, String source);

	void glCompileShader(int shader);

	int glGetShaderi(int shader, int param);

	String getShaderInfoLog(int shader);

	void glDeleteShader(int... shaders);

}