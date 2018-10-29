package vgl.core.gfx.shader;

import vgl.core.buffers.Buffers;
import vgl.core.buffers.MemoryBuffer;
import vgl.main.VGL;
import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;
import vgl.maths.vector.Vector4f;
import vgl.platform.gl.Shader;

abstract public class ShaderProgram extends Shader {

	protected static MemoryBuffer buffer4f = VGL.factory.dataBuffer(16 * 4);

	public ShaderProgram(String vertexSource, String fragmentSource) {
		super(vertexSource,fragmentSource);
	}

	@Override
	public void bindAttribute(int attribute, String variableName) {
		VGL.api_gfx.glBindAttribLocation(programID, attribute, variableName);
	}
	
	public int getAttribLocation(String attrib) {
		return VGL.api_gfx.glGetAttributeLocation(programID, attrib);
	}

	@Override
	protected int createProgram() {
		return VGL.api_gfx.glCreateProgram();
	}

	@Override
	protected void attachShader(int prog_id, int shaderId) {
		VGL.api_gfx.glAttachShader(prog_id, shaderId);
	}

	@Override
	protected void link(int prog_id) {
		VGL.api_gfx.glLinkProgram(prog_id);
	}

	@Override
	protected void validate(int prog_id) {
		VGL.api_gfx.glValidateProgram(prog_id);
	}

	@Override
	public void cleanUp() {
		stop();
		VGL.api_gfx.glDetachShader(programID, vertexShaderID);
		VGL.api_gfx.glDetachShader(programID, fragmentShaderID);
		VGL.api_gfx.glDeleteShader(vertexShaderID);
		VGL.api_gfx.glDeleteShader(fragmentShaderID);
		VGL.api_gfx.glDeleteProgram(programID);
		// MemoryUtil.memFree(buffer4f);
	}

	@Override
	protected int getUniformLocation(String uniformName) {
		return VGL.api_gfx.glGetUniformLocation(programID, uniformName);
	}

	@Override
	protected int loadShader(String source, ShaderType type) {
		final int shaderID = VGL.api_gfx.glCreateShader(type);
		VGL.api_gfx.glShaderSource(shaderID, source);
		VGL.api_gfx.glCompileShader(shaderID);

		VGL.logger.warn("*********Shader Compilation Info log**********");
		VGL.logger.warn(VGL.api_gfx.glGetShaderInfoLog(shaderID));
		VGL.logger.warn("**********************************************");
		VGL.logger.info("Succesfull compilation of shader : " + type);
		return shaderID;
	}

	@Override
	public void start() {
		VGL.api_gfx.glUseProgram(programID);
		currentlyBound = this;
	}

	@Override
	public void stop() {

		VGL.api_gfx.glUseProgram(0);
		if (currentlyBound == this)
			currentlyBound = null;
	}

	@Override
	public void uniformFloat(String uniformName, float value) {
		VGL.api_gfx.glUniform1f(findUniform(uniformName), value);
	}

	@Override
	public void uniformInt(String uniformName, int value) {
		VGL.api_gfx.glUniform1i(findUniform(uniformName), value);
	}

	@Override
	public void uniformVec3f(String uniformName, Vector3f vector) {
		VGL.api_gfx.glUniform3f(findUniform(uniformName), vector.x, vector.y, vector.z);
	}

	@Override
	public void uniformVec2f(String uniformName, Vector2f vector) {
		VGL.api_gfx.glUniform2f(findUniform(uniformName), vector.x, vector.y);
	}

	@Override
	public void uniformVec4f(String uniformName, Vector4f vector4f) {
		VGL.api_gfx.glUniform4f(findUniform(uniformName), vector4f.x, vector4f.y, vector4f.z, vector4f.w);

	}

	@Override
	public void uniform1fv(String uniformName, float[] data) {
		VGL.api_gfx.glUniform1fv(findUniform(uniformName), data);
	}

	@Override
	public void uniform1iv(String uniformName, int[] data) {
		VGL.api_gfx.glUniform1iv(findUniform(uniformName), data);
	}

	@Override
	public void uniformMat4f(String uniformName, Matrix4f matrix) {
		Buffers.wrap(buffer4f, matrix);
		VGL.api_gfx.glUniformMatrix4fv(findUniform(uniformName), false, buffer4f);
	}

	@Override
	public void uniformBool(String uniformName, boolean value) {
		float toLoad = 0;
		if (value)
			toLoad = 1;
		VGL.api_gfx.glUniform1f(findUniform(uniformName), toLoad);
	}
	
	public static ShaderProgram create(String vsSource, String fsSource) {
		final ShaderTokener tokener = new ShaderTokener(vsSource, fsSource);
		System.out.println("VERT ***** ")
		;
		System.out.println(tokener.getVertexSourceSafe());
		
		System.out.println("FRAG******");
		
		System.out.println(tokener.getFragmentSourceSafe());
		return new ShaderProgram(tokener.getVertexSourceSafe(), tokener.getFragmentSourceSafe()) {
			
			@Override
			public void getAllUniformLocations() {
				tokener.getData()
				       .uniforms
				       .stream()
				       .filter(decl -> !decl.getName().contains("["))
				       .forEach(System.out::println);
			}
			
			@Override
			public void bindVertexShaderInputAttribs() {
				tokener.getData()
				       .attributes
				       .forEach(attribute -> bindAttribute(attribute.getIndex(), attribute.getName()));
			}
		};
	}

}
