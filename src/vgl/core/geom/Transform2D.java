package vgl.core.geom;

import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;
import vgl.maths.vector.VectorMaths;

public class Transform2D {

	private float		angleDegrees;

	private Vector2f	position;

	private Vector2f	scale2D;
	
	private Matrix4f self;

	public Transform2D(Vector2f position, Vector2f scale2D, float angleDegrees) {
		this.position = position;
		this.scale2D = scale2D;
		this.angleDegrees = angleDegrees;
		this.self = VectorMaths.transformationMatrix(this);
	}

	public Transform2D() {
		this.position = new Vector2f();
		this.angleDegrees = 0;
		this.self = VectorMaths.transformationMatrix(this);
	}

	public Transform2D(Vector2f position, Vector2f scale2D) {
		this.position = position;
		this.scale2D = scale2D;
		this.self = VectorMaths.transformationMatrix(this);
	}

	public Transform2D(Vector2f position, float angleDegrees) {
		this.position = position;
		this.angleDegrees = angleDegrees;
		this.self = VectorMaths.transformationMatrix(this);
	}

	public Transform2D(Vector2f position) {
		this.position = position;
		this.self = VectorMaths.transformationMatrix(this);
	}

	public Transform2D(Transform2D transform2d) {
		this.position = transform2d.position;
		this.angleDegrees = transform2d.angleDegrees;
		this.scale2D = transform2d.scale2D;
		this.self = VectorMaths.transformationMatrix(this);
	}

	public Vector2f position() {
		return position;
	}

	public float angleDegrees() {
		return angleDegrees;
	}

	public Transform2D translateBy(float xAmnt, float yAmnt) {
		this.position.x += xAmnt;
		this.position.y += yAmnt;
		this.self.multiply(Matrix4f.translation(new Vector3f(xAmnt, yAmnt, 0f)));
		return this;
	}

	public Transform2D copy() {
		return new Transform2D(this);
	}

	public Transform2D translateBy(Vector2f amnt) {
		translateBy(amnt.x, amnt.y);
		return this;
	}

	public Transform2D rotateBy(float angDegAmnt) {
		this.angleDegrees += angDegAmnt;
		this.self.multiply(Matrix4f.rotation(angDegAmnt, Vector3f.TO_POSITIVE_Z));
		return this;
	}

	public Matrix4f toMatrix() {
		return self;
	}
}
