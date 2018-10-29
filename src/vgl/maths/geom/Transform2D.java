package vgl.maths.geom;

import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;

public class Transform2D {

	private Matrix4f self;

	public Transform2D() {
		this.self = Matrix4f.identity();
	}

	public Transform2D(Vector2f position, Vector2f scale2D) {
		this();
		this.self.multiply(Matrix4f.translation(new Vector3f(position)));
	}

	public Transform2D(Vector2f position, float angleDegrees) {
		this();
		this.self.multiply(Matrix4f.translation(new Vector3f(position)));
	}

	public Transform2D(Vector2f position) {
		this();
		this.self.multiply(Matrix4f.translation(new Vector3f(position)));
	}

	public Transform2D(Transform2D transform2d) {
		this.self = transform2d.self.copy();
	}

	public Transform2D translateBy(float xAmnt, float yAmnt) {
		this.self.multiply(Matrix4f.translation(new Vector3f(xAmnt, yAmnt, 0f)));
		return this;
	}

	public Transform2D copy() {
		return new Transform2D(this);
	}

	public Transform2D translateBy(Vector2f amnt) {
		return translateBy(amnt.x, amnt.y);
	}

	public Transform2D translateTo(float x, float y) {
		this.self.multiply(Matrix4f.translation(new Vector3f(x, y, 0f)));
		return this;
	}

	public Transform2D rotateBy(float angDegAmnt) {
		this.self.multiply(Matrix4f.rotation(angDegAmnt, Vector3f.TO_NEGATIVE_Z));
		return this;
	}

	public Transform2D rotateByAt(float x, float y, float angDegAmnt) {
		translateBy(x, y);
		rotateBy(angDegAmnt);
		translateBy(-x, -y);
		return this;
	}

	public Transform2D rotateByAt(Vector2f position, float angDegAmnt) {
		return rotateByAt(position.x, position.y, angDegAmnt);
	}

	public Matrix4f toMatrix() {
		return self.copy();
	}
	
	public static Transform2D identity() {
		return new Transform2D();
	}
}
