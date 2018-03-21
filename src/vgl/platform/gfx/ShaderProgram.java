package vgl.platform.gfx;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.shc.webgl4j.client.WebGL10;

import vgl.core.buffers.MemoryBuffer;
import vgl.core.gfx.shader.ShaderType;
import vgl.main.VGL;
import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;
import vgl.maths.vector.Vector4f;
import vgl.platform.gl.Shader;
import vgl.utils.BufferStorageSafe;

abstract public class ShaderProgram extends Shader {

	protected static MemoryBuffer buffer4f = VGL.factory.newMemoryBuffer(16 * 4);

	public ShaderProgram(String vertexFilePath, String fragmentFilePath) {
		super(vertexFilePath, fragmentFilePath);
	}

	@Override
	public void bindAttribute(int attribute, String variableName) {
		VGL.api.glBindAttribLocation(programID, attribute, variableName);
	}

	@Override
	protected int createProgram() {
		return VGL.api.glCreateProgram();
	}

	@Override
	protected void attachShader(int prog_id, int shaderId) {
		VGL.api.glAttachShader(prog_id, shaderId);
	}

	@Override
	protected void link(int prog_id) {
		VGL.api.glLinkProgram(prog_id);
	}

	@Override
	protected void validate(int prog_id) {
		VGL.api.glValidateProgram(prog_id);
	}

	@Override
	public void cleanUp() {
		stop();
		VGL.api.glDetachShader(programID, vertexShaderID);
		VGL.api.glDetachShader(programID, fragmentShaderID);
		VGL.api.glDeleteShader(vertexShaderID);
		VGL.api.glDeleteShader(fragmentShaderID);
		VGL.api.glDeleteProgram(programID);
		// MemoryUtil.memFree(buffer4f);
	}

	@Override
	protected int getUniformLocation(String uniformName) {
		return VGL.api.glGetUniformLocation(programID, uniformName);
	}

	@Override
	protected int loadShader(String source, ShaderType type) {
		final int shaderID = VGL.api.glCreateShader(type);
		VGL.api.glShaderSource(shaderID, source);
		VGL.api.glCompileShader(shaderID);
		if (VGL.api.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(VGL.api.glGetShaderInfoLog(shaderID));
			throw new vgl.core.exception.VGLFatalError("Shader failed to compile >> [" + source + "]");
		}
		System.out.println("Succesfull compilation of shader : " + type);
		return shaderID;
	}

	@Override
	public void start() {
		VGL.api.glUseProgram(programID);
		currentlyBound = this;
	}

	@Override
	public void stop() {

		VGL.api.glUseProgram(0);
		if (currentlyBound == this)
			currentlyBound = null;
	}

	@Override
	public void uniformFloat(String uniformName, float value) {
		VGL.api.glUniform1f(findUniform(uniformName), value);
	}

	@Override
	public void uniformInt(String uniformName, int value) {
		VGL.api.glUniform1i(findUniform(uniformName), value);
	}

	@Override
	public void uniformVec3f(String uniformName, Vector3f vector) {
		VGL.api.glUniform3f(findUniform(uniformName), vector.x, vector.y, vector.z);
	}

	@Override
	public void uniformVec2f(String uniformName, Vector2f vector) {
		VGL.api.glUniform2f(findUniform(uniformName), vector.x, vector.y);
	}

	@Override
	public void uniformVec4f(String uniformName, Vector4f vector4f) {
		VGL.api.glUniform4f(findUniform(uniformName), vector4f.x, vector4f.y, vector4f.z, vector4f.w);

	}

	@Override
	public void uniform1fv(String uniformName, float[] data) {
		VGL.api.glUniform1fv(findUniform(uniformName), data);

	}

	@Override
	public void uniform1iv(String uniformName, int[] data) {
		VGL.api.glUniform1iv(findUniform(uniformName), data);
	}

	@Override
	public void uniformMat4f(String uniformName, Matrix4f matrix) {
		BufferStorageSafe.storeInBuffer(buffer4f, matrix);
		VGL.api.glUniformMatrix4fv(findUniform(uniformName), false, buffer4f);
	}

	@Override
	public void uniformBool(String uniformName, boolean value) {
		float toLoad = 0;
		if (value)
			toLoad = 1;
		VGL.api.glUniform1f(findUniform(uniformName), toLoad);
	}

}
