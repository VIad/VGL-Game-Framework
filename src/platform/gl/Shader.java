package vgl.platform.gl;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import vgl.core.gfx.shader.ShaderProgram;
import vgl.core.gfx.shader.ShaderType;
import vgl.core.internal.Checks;
import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;
import vgl.maths.vector.Vector4f;

abstract public class Shader {

	protected final int						programID;
	protected final int						vertexShaderID;
	protected final int						fragmentShaderID;

	protected static Shader					currentlyBound	= null;

	protected static FloatBuffer			buffer4f		= MemoryUtil.memAllocFloat(4 * 4);
	protected final Map<String, Integer>	uniformCache	= new HashMap<>();

	public Shader(final String vertexSource, final String fragmentSource) {
		Checks.checkIfInitialized();
		vertexShaderID = loadShader(vertexSource, ShaderType.VERTEX);
		fragmentShaderID = loadShader(fragmentSource, ShaderType.FRAGMENT);
		programID = createProgram();
		attachShader(programID, vertexShaderID);
		attachShader(programID, fragmentShaderID);
		bindVertexShaderInputAttribs();
		link(programID);
		validate(programID);
		getAllUniformLocations();
	}

	protected abstract int createProgram();

	protected abstract void attachShader(int prog_id, int shaderId);

	protected abstract void link(int prog_id);

	protected abstract void validate(int prog_id);

	abstract public void start();

	abstract public void stop();

	abstract public void cleanUp();

	abstract public void bindVertexShaderInputAttribs();

	abstract public void getAllUniformLocations();

	abstract public void bindAttribute(final int attribute, final String variableName);

	abstract protected int getUniformLocation(final String uniformName);

	public static final int UNIFORM_LOAD_ERROR = -1;

	// /**
	// * Caches the uniforms to later be accessed via uniform methods
	// *<strong> <br> NOTE : If the uniform exists, but ISN'T used anywhere in the
	// shader, the method will throw </strong> <code> ShaderException </code>
	// * @param uniformNames - the uniforms to be cached
	// * @throws ShaderException - if either the uniform is already cached or if it
	// doesnt exist
	// */
	// public final void cacheUniforms(String uniformFirst, String... uniformNames)
	// {
	// if (getUniformLocation(uniformFirst) == UNIFORM_LOAD_ERROR) {
	// throw new vgl.core.exception.ShaderException("Unable to find uniform with
	// name >> " + uniformFirst);
	// }
	// if (uniformCache.containsKey(uniformFirst))
	// throw new vgl.core.exception.ShaderException("Shader uniform already
	// cached");
	// uniformCache.put(uniformFirst, GL20.glGetUniformLocation(programID,
	// uniformFirst));
	//
	// for (String s : uniformNames) {
	// if (getUniformLocation(s) == UNIFORM_LOAD_ERROR) {
	// throw new vgl.core.exception.ShaderException("Unable to find uniform with
	// name >> " + s);
	// }
	// if (uniformCache.containsKey(s))
	// throw new vgl.core.exception.ShaderException("Shader uniform already
	// cached");
	// uniformCache.put(s, GL20.glGetUniformLocation(programID, s));
	// }
	// }

	protected int findUniform(final String uniformName) {
		int uniformLocation = uniformCache.containsKey(uniformName)
		        ? uniformCache.get(uniformName)
		        : getUniformLocation(uniformName);
		if (uniformLocation == UNIFORM_LOAD_ERROR) {
			throw new vgl.core.exception.ShaderException("Unable to find uniform with name >> " + uniformName);
		}
		uniformCache.put(uniformName, uniformLocation);
		return uniformLocation;
	}

	abstract public void uniformFloat(final String uniformName, final float value);

	abstract public void uniformInt(final String uniformName, final int value);

	abstract public void uniformVec3f(final String uniformName, final Vector3f vector);

	abstract public void uniformVec2f(final String uniformName, final Vector2f vector);

	abstract public void uniformVec4f(final String uniformName, final Vector4f vector4f);

	abstract public void uniform1fv(final String uniformName, final float[] data);

	abstract public void uniform1iv(final String uniformName, final int[] data);

	abstract public void uniformMat4f(final String uniformName, final Matrix4f projMat);

	abstract public void uniformBool(final String uniformName, final boolean value);

	abstract protected int loadShader(final String file, final ShaderType type);

	public static Shader getCurrentlyBoundProgram() {
		return currentlyBound;
	}

}
