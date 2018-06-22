package vgl.core.geom;

import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector3f;
import vgl.maths.vector.VectorMaths;

public class Transform implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6511816091833564275L;

	private Vector3f			position;

	private Vector3f			scale;

	private float				rotXDeg, rotYDeg, rotZDeg;

	private Matrix4f			currentTransformation;

	public Transform() {
		this.position = new Vector3f();
		this.scale = new Vector3f(1, 1, 1);
		this.rotXDeg = rotYDeg = rotZDeg = 0;
		currentTransformation = VectorMaths.transformationMatrix(this);
	}

	public Transform(Vector3f position) {
		this.position = position;
		this.scale = new Vector3f();
		this.rotXDeg = rotYDeg = rotZDeg = 0;
		this.currentTransformation = new Matrix4f();
		currentTransformation = VectorMaths.transformationMatrix(this);
	}

	public Transform(Vector3f position, Vector3f scale) {
		this.position = position;
		this.scale = scale;
		this.rotXDeg = rotYDeg = rotZDeg = 0;
		currentTransformation = VectorMaths.transformationMatrix(this);
	}

	public Transform(Vector3f position, float rotXDeg, float rotYDeg, float rotZDeg) {
		this.position = position;
		this.scale = new Vector3f(1, 1, 1);
		this.rotXDeg = rotXDeg;
		this.rotYDeg = rotYDeg;
		this.rotZDeg = rotZDeg;
		currentTransformation = VectorMaths.transformationMatrix(this);
	}

	public Transform(Vector3f position, Vector3f scale, float rotXDeg, float rotYDeg, float rotZDeg) {
		this.position = position;
		this.scale = scale;
		this.rotXDeg = rotXDeg;
		this.rotYDeg = rotYDeg;
		this.rotZDeg = rotZDeg;
		currentTransformation = VectorMaths.transformationMatrix(this);
	}

	public Vector3f position() {
		return position;
	}

	public float rotationX() {
		return rotXDeg;
	}

	public float rotationY() {
		return rotYDeg;
	}

	public float rotationZ() {
		return rotZDeg;
	}

	public Transform translateBy(Vector3f amount) {
		this.position.add(amount);
		currentTransformation = VectorMaths.transformationMatrix(this);
		return this;
	}

	public Transform translateBy(float xAmnt, float yAmnt, float zAmnt) {
		this.position.x += xAmnt;
		this.position.y += yAmnt;
		this.position.z += zAmnt;
		currentTransformation = VectorMaths.transformationMatrix(this);
		return this;
	}

	public Transform rotateBy(float amntRotXDeg, float amntRotYDeg, float amntRotZDeg) {
		this.rotXDeg += amntRotXDeg;
		this.rotYDeg += amntRotYDeg;
		this.rotZDeg += amntRotZDeg;
		currentTransformation = VectorMaths.transformationMatrix(this);
		return this;
	}

	public Matrix4f toMatrix() {
		return vgl.maths.vector.VectorMaths.transformationMatrix(this);
	}

	public Vector3f scale() {
		return scale;
	}
}
