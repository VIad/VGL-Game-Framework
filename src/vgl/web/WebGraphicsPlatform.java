package vgl.web;

import java.util.Arrays;

import com.google.gwt.typedarrays.shared.ArrayBufferView;
import com.google.gwt.typedarrays.shared.Uint8Array;
import com.shc.webgl4j.client.WebGL10;
import com.shc.webgl4j.client.WebGL20;
import com.vgl.gwtreq.client.GWTDataView;

import vgl.core.buffers.Buffers;
import vgl.core.buffers.MemoryBuffer;
import vgl.core.exception.VGLFatalError;
import vgl.core.gfx.shader.ShaderType;
import vgl.main.VGL;
import vgl.platform.IGraphicsPlatorm;
import vgl.platform.gl.GLBufferTarget;
import vgl.platform.gl.GLPrimitiveMode;
import vgl.platform.gl.GLTextureType;

public class WebGraphicsPlatform implements IGraphicsPlatorm {

	@Override
	public int glGenVertexArray() {
		return WebGL20.glCreateVertexArray();
	}

	@Override
	public int glGenBuffer() {
		return WebGL10.glCreateBuffer();
	}

	@Override
	public int glGenFramebuffer() {
		return WebGL10.glCreateFramebuffer();
	}

	@Override
	public int glGenTexture() {
		return WebGL10.glCreateTexture();
	}

	@Override
	public void glBindBuffer(GLBufferTarget target, int buffer) {
		WebGL10.glBindBuffer(target.nativeGL(), buffer);
	}

	@Override
	public void glBindBuffer(int target, int buffer) {
		WebGL10.glBindBuffer(target, buffer);
	}

	@Override
	public void glBindVertexArray(int vao) {
		WebGL20.glBindVertexArray(vao);
	}

	@Override
	public void glEnableVertexAttribArray(int index) {
		WebGL10.glEnableVertexAttribArray(index);
	}

	@Override
	public void glDisableVertexAttribArray(int index) {
		WebGL10.glDisableVertexAttribArray(index);
	}

	@Override
	public void glBufferData(GLBufferTarget target, long size, int usage) {
		assert size <= Integer.MAX_VALUE;// Cannot use long for web allocation
		WebGL10.glBufferData(target.nativeGL(), (int) size, usage);
	}

	@Override
	public void glBufferData(GLBufferTarget target, MemoryBuffer data, int usage) {
		WebGL10.glBufferData(target.nativeGL(), ((GWTDataView) data.nativeBufferDetails().getBuffer()).getView(),
		        usage);
	}

	@Override
	public void glBufferData(GLBufferTarget target, float[] data, int usage) {
		WebGL10.glBufferData(target.nativeGL(), data, usage);
	}

	@Override
	public void glBufferData(int target, long size, int usage) {
		assert size <= Integer.MAX_VALUE;// Cannot use long for web allocation
		WebGL10.glBufferData(target, (int) size, usage);
	}

	@Override
	public void glBufferData(int target, MemoryBuffer data, int usage) {
		WebGL10.glBufferData(target, ((GWTDataView) data.nativeBufferDetails().getBuffer()).getView(), usage);
	}

	@Override
	public void glBufferData(int target, float[] data, int usage) {
		WebGL10.glBufferData(target, data, usage);
	}

	@Override
	public void glBufferSubData(int target, int offset, MemoryBuffer data) {
		WebGL10.glBufferSubData(target, offset, ((GWTDataView) data.nativeBufferDetails().getBuffer()).getView());
	}

	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long pointer) {
		WebGL10.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
	}

	@Override
	public void glActiveTexture(int texture) {
		WebGL10.glActiveTexture(texture);
	}

	@Override
	public void glBindTexture(GLTextureType type, int texObjectID) {
		WebGL10.glBindTexture(type.gl(), texObjectID);
	}

	@Override
	public void glBindTexture(int type, int texObjectID) {
		WebGL10.glBindTexture(type, texObjectID);
	}

	@Override
	public void glTexParameteri(int target, int param, int val) {
		WebGL10.glTexParameteri(target, param, val);
	}

	@Override
	public void glGenMipmap(int target) {
		WebGL10.glGenerateMipmap(target);
	}

	@Override
	public void glDeleteTexture(int... textures) {
		Arrays.stream(textures).forEach(WebGL10::glDeleteTexture);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalF, int width, int height, int border, int format,
	        int type, MemoryBuffer data) {
		WebGL10.glTexImage2D(target, level, internalF, width, height, border, format, type,
		        data != null ? ((GWTDataView) data.nativeBufferDetails().getBuffer()).getView() : null);
	}

	public void glTexImage2D(int target, int level, int internalF, int width, int height, int border, int format,
	        int type, ArrayBufferView data) {
		WebGL10.glTexImage2D(target, level, internalF, width, height, border, format, type, data);
	}

	@Override
	public void glEnable(int what) {
		WebGL10.glEnable(what);
	}

	@Override
	public void glDisable(int what) {
		WebGL10.glDisable(what);
	}

	@Override
	public void glClearColor(float r, float g, float b, float a) {
		WebGL10.glClearColor(r, g, b, a);
	}

	@Override
	public void glDepthMask(boolean val) {
		WebGL10.glDepthMask(val);
	}

	@Override
	public void glDepthFunc(int func) {
		WebGL10.glDepthFunc(func);
	}

	@Override
	public void glCullFace(int face) {
		WebGL10.glCullFace(face);
	}

	@Override
	public int glGetError() {
		return WebGL10.glGetError();
	}

	@Override
	public void glBlendFunc(int sfactor, int dfactor) {
		WebGL10.glBlendFunc(sfactor, dfactor);
	}

	@Override
	public void glViewport(int x, int y, int width, int height) {
		WebGL10.glViewport(x, y, width, height);
	}

	@Override
	public void glDrawElements(GLPrimitiveMode primitiveType, int indexCount, int type, long indices) {
		assert indices <= Integer.MAX_VALUE;
		WebGL10.glDrawElements(primitiveType.nativeGL(), indexCount, type, (int) indices);
	}

	@Override
	public void glDrawArrays(GLPrimitiveMode primitiveType, int first, int count) {
		WebGL10.glDrawArrays(primitiveType.nativeGL(), first, count);
	}

	@Override
	public void glDeleteBuffers(int... buffers) {
		Arrays.stream(buffers).forEach(WebGL10::glDeleteBuffer);
	}

	@Override
	public void glDeleteVertexArrays(int... vaos) {
		Arrays.stream(vaos).forEach(WebGL20::glDeleteVertexArray);
	}

	@Override
	public int glCreateProgram() {
		return WebGL10.glCreateProgram();
	}

	@Override
	public void glAttachShader(int program, int shader) {
		WebGL10.glAttachShader(program, shader);
	}

	@Override
	public void glLinkProgram(int program) {
		WebGL10.glLinkProgram(program);
	}

	@Override
	public void glValidateProgram(int program) {
		// throw new UnsupportedOperationException();
	}

	@Override
	public int glGetUniformLocation(int program, String name) {
		return WebGL10.glGetUniformLocation(program, name);
	}

	@Override
	public void glBindAttribLocation(int program, int attribute, String variableName) {
		WebGL10.glBindAttribLocation(program, attribute, variableName);
	}

	@Override
	public int glGetAttributeLocation(int program, String name) {
		return WebGL10.glGetAttribLocation(program, name);
	}

	@Override
	public void glUseProgram(int program) {
		WebGL10.glUseProgram(program);
	}

	@Override
	public void glUniform1i(int location, int v) {
		WebGL10.glUniform1i(location, v);
	}

	@Override
	public void glUniform2i(int location, int v1, int v2) {
		WebGL10.glUniform2i(location, v1, v2);
	}

	@Override
	public void glUniform3i(int location, int v1, int v2, int v3) {
		WebGL10.glUniform3i(location, v1, v2, v3);
	}

	@Override
	public void glUniform4i(int location, int v1, int v2, int v3, int v4) {
		WebGL10.glUniform4i(location, v1, v2, v3, v4);
	}

	@Override
	public void glUniform1f(int location, float v) {
		WebGL10.glUniform1f(location, v);
	}

	@Override
	public void glUniform2f(int location, float v1, float v2) {
		WebGL10.glUniform2f(location, v1, v2);
	}

	@Override
	public void glUniform3f(int location, float v1, float v2, float v3) {
		WebGL10.glUniform3f(location, v1, v2, v3);
	}

	@Override
	public void glUniform4f(int location, float v1, float v2, float v3, float v4) {
		WebGL10.glUniform4f(location, v1, v2, v3, v4);
	}

	@Override
	public void glUniformMatrix4fv(int location, boolean transpose, MemoryBuffer matrix) {
		WebGL10.glUniformMatrix4fv(location, transpose, Buffers.castToFloatUnsafe(matrix));
		// WebGL10.glUniformMatrix4fv(location, transpose,
		// Native.create((GWTArrayBuffer) matrix.nativeBufferDetails().getBuffer()));

	}

	@Override
	public void glUniformMatrix3fv(int location, boolean transpose, MemoryBuffer matrix) {
		// WebGL10.glUniformMatrix4fv(location, transpose,
		// Native.create((GWTArrayBuffer) matrix.nativeBufferDetails().getBuffer()));
		WebGL10.glUniformMatrix3fv(location, transpose, Buffers.castToFloatUnsafe(matrix));
	}

	@Override
	public void glUniform1fv(int location, float[] data) {

	}

	@Override
	public void glUniform1iv(int location, int[] data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glDetachShader(int program, int shader) {
		WebGL10.glDetachShader(program, shader);
	}

	@Override
	public void glDeleteShader(int shader) {
		WebGL10.glDeleteShader(shader);
	}

	@Override
	public void glDeleteProgram(int... id) {
		Arrays.stream(id).forEach(WebGL10::glDeleteProgram);
	}

	@Override
	public int glCreateShader(ShaderType type) {
		return WebGL10.glCreateShader(type.glShaderType());
	}

	@Override
	public void glShaderSource(int shader, String source) {
		WebGL10.glShaderSource(shader, source);
	}

	@Override
	public void glCompileShader(int shader) {
		WebGL10.glCompileShader(shader);
	}

	@Override
	public int glGetShaderi(int shader, int param) {
		return WebGL10.GL_TRUE;
	}

	@Override
	public void glDeleteShader(int... shaders) {
		Arrays.stream(shaders).forEach(WebGL10::glDeleteShader);
	}

	@Override
	public String glGetShaderInfoLog(int shaderID) {
		return WebGL10.glGetShaderInfoLog(shaderID);
	}

	@Override
	public void glClear(int flags) {
		WebGL10.glClear(flags);

	}

	@Override
	public boolean glGetBoolean(int which) {
		return false;// For some reason get functions are not in WGL
	}

	@Override
	public void glBufferData(int target, int[] data, int usage) {
		WebGL10.glBufferData(target, data, usage);
	}

}
