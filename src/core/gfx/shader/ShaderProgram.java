package vgl.core.gfx.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;
import vgl.maths.vector.Vector4f;
import vgl.platform.gl.Shader;

abstract public class ShaderProgram extends Shader {

	public ShaderProgram(String vertexFilePath, String fragmentFilePath) {
		super(vertexFilePath, fragmentFilePath);
	}

	@Override
	public void bindAttribute(int attribute, String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}

	@Override
	protected int createProgram() {
		return GL20.glCreateProgram();
	}

	@Override
	protected void attachShader(int prog_id, int shaderId) {
		GL20.glAttachShader(prog_id, shaderId);
	}

	@Override
	protected void link(int prog_id) {
		GL20.glLinkProgram(prog_id);
	}

	@Override
	protected void validate(int prog_id) {
		GL20.glValidateProgram(prog_id);
	}

	@Override
	public void cleanUp() {
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
		MemoryUtil.memFree(buffer4f);
	}

	@Override
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName);
	}

	@Override
	protected int loadShader(String source, ShaderType type) {
		final int ShaderID = GL20.glCreateShader(type == ShaderType.VERTEX
		        ? GL20.GL_VERTEX_SHADER
		        : type == ShaderType.FRAGMENT ? GL20.GL_FRAGMENT_SHADER : 0);
		GL20.glShaderSource(ShaderID, source);
		GL20.glCompileShader(ShaderID);
		if (GL20.glGetShaderi(ShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(ShaderID, 500));
			throw new vgl.core.exception.VGLFatalError("Shader failed to compile >> [" + source + "]");
		}
		System.out.println("Succesfull compilation of shader : " + type);
		return ShaderID;
	}

	@Override
	public void start() {
		GL20.glUseProgram(programID);
		currentlyBound = this;
	}

	@Override
	public void stop() {

		GL20.glUseProgram(0);
		if (currentlyBound == this)
			currentlyBound = null;
	}

	@Override
	public void uniformFloat(String uniformName, float value) {
		GL20.glUniform1f(findUniform(uniformName), value);
	}

	@Override
	public void uniformInt(String uniformName, int value) {
		GL20.glUniform1i(findUniform(uniformName), value);
	}

	@Override
	public void uniformVec3f(String uniformName, Vector3f vector) {
		GL20.glUniform3f(findUniform(uniformName), vector.x, vector.y, vector.z);
	}

	@Override
	public void uniformVec2f(String uniformName, Vector2f vector) {
		GL20.glUniform2f(findUniform(uniformName), vector.x, vector.y);
	}

	@Override
	public void uniformVec4f(String uniformName, Vector4f vector4f) {
		GL20.glUniform4f(findUniform(uniformName), vector4f.x, vector4f.y, vector4f.z, vector4f.w);

	}

	@Override
	public void uniform1fv(String uniformName, float[] data) {
		GL20.glUniform1fv(findUniform(uniformName), data);

	}

	@Override
	public void uniform1iv(String uniformName, int[] data) {
		GL20.glUniform1iv(findUniform(uniformName), data);
	}

	@Override
	public void uniformMat4f(String uniformName, Matrix4f projMat) {
		projMat.store(buffer4f);
		GL20.glUniformMatrix4fv(findUniform(uniformName), false, buffer4f);
		buffer4f.clear();
	}

	@Override
	public void uniformBool(String uniformName, boolean value) {
		float toLoad = 0;
		if (value)
			toLoad = 1;
		GL20.glUniform1f(findUniform(uniformName), toLoad);
	}

}
