package vgl.core.gfx.camera;

import vgl.core.gfx.shader.ShaderProgram;
import vgl.maths.geom.Size2f;
import vgl.maths.geom.Transform2D;
import vgl.maths.geom.shape2d.RectFloat;
import vgl.maths.vector.Vector2f;

public class OrthographicCamera {

	public static final String	SHADER_UNIFORM_NAME	= "u_viewMatrix";

	private Transform2D			cameraTransform;
	
	private RectFloat           viewport;
	
	private Vector2f offset;

	public OrthographicCamera(RectFloat viewport) {
		this(viewport.getLocation(), viewport.getSize());
	}
	
	public OrthographicCamera(Size2f viewportSize) {
		this(new Vector2f(), viewportSize);
	}
	
	public OrthographicCamera(Vector2f position, Size2f viewportsize) {
		this.viewport = new RectFloat(viewportsize);
		this.cameraTransform = new Transform2D();
		this.offset = position;
	}

	public void uploadToShader(ShaderProgram shader) {
		uploadToShader(SHADER_UNIFORM_NAME, shader);
	}

	public void uploadToShader(String matrixUniformName, ShaderProgram shader) {
		shader.uniformMat4f(matrixUniformName, cameraTransform.toMatrix());
	}
	
	public OrthographicCamera moveBy(float x, float y) {
		this.cameraTransform.translateBy(x, y);
		this.offset.subtract(x,y);
		return this;
	}
	
	public Vector2f offset() {
		return offset;
	}
	
	public OrthographicCamera rotateByDegrees(float degrees) {
		this.cameraTransform.rotateBy(degrees);
		return this;
	}
	
	public OrthographicCamera rotateByRads(float rads) {
		this.cameraTransform.rotateBy((float) Math.toDegrees(rads));
		return this;
	}
	
	public Transform2D transform() {
		return cameraTransform;
	}
	
}
