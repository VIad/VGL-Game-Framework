package vgl.desktop.gl;

import java.util.Arrays;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import vgl.core.buffers.MemoryBuffer;
import vgl.core.gfx.shader.ShaderType;
import vgl.main.VGL;
import vgl.platform.IGraphicsPlatorm;
import vgl.platform.gl.GLBufferTarget;
import vgl.platform.gl.GLPrimitiveMode;
import vgl.platform.gl.GLTextureType;

public class DesktopGraphicsPlatform implements IGraphicsPlatorm {

	private boolean shouldTest() {
		return true;
	}

	private <T> T runGLMethod(BooleanSupplier condition, String methodName, Supplier<T> action) {
		if (condition.getAsBoolean()) {
			try {
				return action.get();
			} finally {
				int glError = VGL.api_gfx.glGetError();
				if (glError != GL11.GL_NO_ERROR)
					VGL.logger.critical("GL_ERROR @ " + methodName + " >> " + glError);
			}
		}
		return action.get();
	}

	private void runGLVoidMethod(BooleanSupplier condition, String methodName, Runnable action) {
		runGLMethod(condition, methodName, () -> {
			action.run();
			return null;
		});
	}
	
	private <T> T runGLMethod(String methodName, Supplier<T> action) {
		return runGLMethod(this::shouldTest, methodName, action);
	}
	
	private void runGLVoidMethod(String methodName, Runnable action) {
		 runGLVoidMethod(this::shouldTest, methodName, action);
	}

	@Override
	public int glGenVertexArray() {
		return runGLMethod(this::shouldTest, "genVAO", GL30::glGenVertexArrays);
	}

	@Override
	public int glGenBuffer() {
		return runGLMethod(this::shouldTest, "glGenBuffer", GL15::glGenBuffers);
	}

	@Override
	public int glGenFramebuffer() {
		return runGLMethod(this::shouldTest, "glGenFBO", GL30::glGenFramebuffers);
	}

	@Override
	public int glGenTexture() {
		return runGLMethod(this::shouldTest, "glGenTexture", GL11::glGenTextures);
	}

	@Override
	public void glBindBuffer(GLBufferTarget target, int buffer) {
		runGLVoidMethod(this::shouldTest, "glBindBuffer", () -> GL15.glBindBuffer(target.nativeGL(), buffer));
	}

	@Override
	public void glBindBuffer(int target, int buffer) {
		runGLVoidMethod(this::shouldTest, "glBindBuffer", () -> GL15.glBindBuffer(target, buffer));
	}

	@Override
	public void glBindVertexArray(int vao) {
		// try {
		// GL30.glBindVertexArray(vao);
		// } finally {
		// int glError = VGL.api_gfx.glGetError();
		// if (glError != GL11.GL_NO_ERROR) {
		// VGL.logger.critical("GL_ERROR [glBindVao(vao)] >> " + glError);
		// }
		// }
		runGLVoidMethod(this::shouldTest, "glBindVao", () -> GL30.glBindVertexArray(vao));
	}

	@Override
	public void glEnableVertexAttribArray(int index) {
		GL20.glEnableVertexAttribArray(index);
	}

	@Override
	public void glBufferData(GLBufferTarget target, long size, int usage) {
		GL15.glBufferData(target.nativeGL(), size, usage);
	}

	@Override
	public void glBufferData(GLBufferTarget target, MemoryBuffer data, int usage) {
		GL15.glBufferData(target.nativeGL(), ((java.nio.ByteBuffer) data.nativeBufferDetails().getBuffer()), usage);
	}

	@Override
	public void glBufferData(GLBufferTarget target, float[] data, int usage) {
		GL15.glBufferData(target.nativeGL(), data, usage);
	}

	@Override
	public void glBufferData(int target, long size, int usage) {
		GL15.glBufferData(target, size, usage);
	}

	@Override
	public void glBufferData(int target, MemoryBuffer data, int usage) {
		GL15.glBufferData(target, ((java.nio.ByteBuffer) data.nativeBufferDetails().getBuffer()), usage);
	}

	@Override
	public void glBufferData(int target, float[] data, int usage) {
		GL15.glBufferData(target, data, usage);
	}

	@Override
	public void glBufferSubData(int target, int offset, MemoryBuffer data) {
		GL15.glBufferSubData(target, offset, ((java.nio.ByteBuffer) data.nativeBufferDetails().getBuffer()));
	}

	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long pointer) {
		GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
	}

	@Override
	public void glActiveTexture(int texture) {
		GL13.glActiveTexture(texture);
	}

	@Override
	public void glBindTexture(GLTextureType type, int texObjectID) {
		GL11.glBindTexture(type.gl(), texObjectID);
	}

	@Override
	public void glBindTexture(int type, int texObjectID) {
		GL11.glBindTexture(type, texObjectID);
	}

	@Override
	public void glTexParameteri(int target, int param, int val) {
		GL11.glTexParameteri(target, param, val);
	}

	@Override
	public void glGenMipmap(int target) {
		GL30.glGenerateMipmap(target);
	}

	@Override
	public void glDeleteTexture(int... textures) {
		GL11.glDeleteTextures(textures);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalF, int width, int height, int border, int format,
	        int type, MemoryBuffer data) {
		GL11.glTexImage2D(target, level, internalF, width, height, border, format, type,
		        data != null ? ((java.nio.ByteBuffer) data.nativeBufferDetails().getBuffer()) : null);
	}

	@Override
	public void glEnable(int what) {
		GL11.glEnable(what);
	}

	@Override
	public void glDisable(int what) {
		GL11.glDisable(what);
	}

	@Override
	public void glClear(int mask) {
		GL11.glClear(mask);
	}

	@Override
	public void glClearColor(float r, float g, float b, float a) {
		GL11.glClearColor(r, g, b, a);
	}

	@Override
	public void glDepthMask(boolean val) {
		GL11.glDepthMask(val);
	}

	@Override
	public void glDepthFunc(int func) {
		GL11.glDepthFunc(func);
	}

	@Override
	public void glCullFace(int face) {
		GL11.glCullFace(face);
	}

	@Override
	public int glGetError() {
		return GL11.glGetError();
	}

	@Override
	public void glBlendFunc(int sfactor, int dfactor) {
		GL11.glBlendFunc(sfactor, dfactor);
	}

	@Override
	public void glViewport(int x, int y, int width, int height) {
		GL11.glViewport(x, y, width, height);
	}

	@Override
	public void glDrawElements(GLPrimitiveMode primitiveType, int indexCount, int type, long indices) {
		GL11.glDrawElements(primitiveType.nativeGL(), indexCount, type, indices);
	}

	@Override
	public void glDrawArrays(GLPrimitiveMode primitiveType, int first, int count) {
		GL11.glDrawArrays(primitiveType.nativeGL(), first, count);
	}

	@Override
	public void glDeleteBuffers(int... buffers) {
		GL15.glDeleteBuffers(buffers);
	}

	@Override
	public void glDeleteVertexArrays(int... vaos) {
		GL30.glDeleteVertexArrays(vaos);
	}

	@Override
	public int glCreateProgram() {
		return GL20.glCreateProgram();
	}

	@Override
	public void glAttachShader(int program, int shader) {
		GL20.glAttachShader(program, shader);
	}

	@Override
	public void glLinkProgram(int program) {
		GL20.glLinkProgram(program);
	}

	@Override
	public int glGetUniformLocation(int program, String name) {
		return GL20.glGetUniformLocation(program, name);
	}

	@Override
	public int glGetAttributeLocation(int program, String name) {
		return GL20.glGetAttribLocation(program, name);
	}

	@Override
	public void glUseProgram(int program) {
		GL20.glUseProgram(program);
	}

	@Override
	public void glUniform1i(int location, int v) {
		GL20.glUniform1i(location, v);
	}

	@Override
	public void glUniform2i(int location, int v1, int v2) {
		GL20.glUniform2i(location, v1, v2);
	}

	@Override
	public void glUniform3i(int location, int v1, int v2, int v3) {
		GL20.glUniform3i(location, v1, v2, v3);
	}

	@Override
	public void glUniform4i(int location, int v1, int v2, int v3, int v4) {
		GL20.glUniform4i(location, v1, v2, v3, v4);
	}

	@Override
	public void glUniform1f(int location, float v) {
		GL20.glUniform1f(location, v);
	}

	@Override
	public void glUniform2f(int location, float v1, float v2) {
		GL20.glUniform2f(location, v1, v2);
	}

	@Override
	public void glUniform3f(int location, float v1, float v2, float v3) {
		GL20.glUniform3f(location, v1, v2, v3);

	}

	@Override
	public void glUniform4f(int location, float v1, float v2, float v3, float v4) {
		GL20.glUniform4f(location, v1, v2, v3, v4);

	}

	@Override
	public void glUniformMatrix4fv(int location, boolean transpose, MemoryBuffer matrix) {
		GL20.glUniformMatrix4fv(location, transpose,
		        ((java.nio.ByteBuffer) matrix.nativeBufferDetails().getBuffer()).asFloatBuffer());
	}

	@Override
	public void glUniformMatrix3fv(int location, boolean transpose, MemoryBuffer matrix) {
		GL20.glUniformMatrix3fv(location, transpose,
		        ((java.nio.ByteBuffer) matrix.nativeBufferDetails().getBuffer()).asFloatBuffer());
	}

	@Override
	public void glDeleteProgram(int... id) {
		for (int i : id)
			GL20.glDeleteProgram(i);
	}

	@Override
	public int glCreateShader(ShaderType type) {
		return GL20.glCreateShader(type.glShaderType());
	}

	@Override
	public void glShaderSource(int shader, String source) {
		GL20.glShaderSource(shader, source);
	}

	@Override
	public void glCompileShader(int shader) {
		GL20.glCompileShader(shader);
	}

	@Override
	public int glGetShaderi(int shader, int param) {
		return GL20.glGetShaderi(shader, param);
	}

	@Override
	public void glDeleteShader(int... shaders) {
		Arrays.stream(shaders).forEach(GL20::glDeleteShader);
	}

	@Override
	public void glDisableVertexAttribArray(int index) {
		GL20.glDisableVertexAttribArray(index);
	}

	@Override
	public void glUniform1fv(int location, float[] data) {
		GL20.glUniform1fv(location, data);
	}

	@Override
	public void glUniform1iv(int location, int[] data) {
		GL20.glUniform1iv(location, data);
	}

	@Override
	public void glDetachShader(int program, int shader) {
		GL20.glDetachShader(program, shader);
	}

	@Override
	public void glDeleteShader(int shader) {
		GL20.glDeleteShader(shader);
	}

	@Override
	public void glBindAttribLocation(int program, int attribute, String variableName) {
		GL20.glBindAttribLocation(program, attribute, variableName);
	}

	@Override
	public void glValidateProgram(int program) {
		GL20.glValidateProgram(program);
	}

	@Override
	public String glGetShaderInfoLog(int shaderID) {
		return GL20.glGetShaderInfoLog(shaderID);
	}

	@Override
	public boolean glGetBoolean(int which) {
		return GL11.glGetBoolean(which);
	}

	@Override
	public void glBufferData(int target, int[] data, int usage) {
		GL15.glBufferData(target, data, usage);
	}

	@Override
	public int glGenRenderbuffer() {
		return GL30.glGenRenderbuffers();
	}

	@Override
	public void glBindRenderbuffer(int target, int renderBuffer) {
		GL30.glBindRenderbuffer(target, renderBuffer);
	}

	@Override
	public void glDeleteRenderbuffer(int renderBufferID) {
		GL30.glDeleteRenderbuffers(renderBufferID);
	}

	@Override
	public int glGetRenderbufferParameteri(int target, int pname) {
		return GL30.glGetRenderbufferParameteri(target, pname);
	}

	@Override
	public void glRenderbufferStorage(int target, int internalFormat, int width, int height) {
		GL30.glRenderbufferStorage(target, internalFormat, width, height);
	}

	@Override
	public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
		GL30.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
	}

	@Override
	public int glCheckFramebufferStatus(int target) {
		return GL30.glCheckFramebufferStatus(target);
	}

	@Override
	public void glBindFramebuffer(int target, int fbo) {
		GL30.glBindFramebuffer(target, fbo);
	}

	@Override
	public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
		GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
	}

	@Override
	public int glGetProgrami(int program, int flag) {
		return GL20.glGetProgrami(program, flag);
	}

}
