package vgl.core.geom;

import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;

public class Transform2D {

	private float		angleDegrees;

	private Vector2f	position;

	private Vector2f	scale2D;

	public Transform2D(Vector2f position, Vector2f scale2D, float angleDegrees) {
		this.position = position;
		this.scale2D = scale2D;
		this.angleDegrees = angleDegrees;
	}

	public Transform2D(Vector2f position, Vector2f scale2D) {
		this.position = position;
		this.scale2D = scale2D;
	}

	public Transform2D(Vector2f position, float angleDegrees) {
		this.position = position;
		this.angleDegrees = angleDegrees;
	}

	public Transform2D(Vector2f position) {
		this.position = position;
	}

	public Vector2f position() {
		return position;
	}

	public float angleDegrees() {
		return angleDegrees;
	}

	public void increasePosition(float xAmnt, float yAmnt) {
		this.position.x += xAmnt;
		this.position.y += yAmnt;
	}

	public void increasePosition(Vector2f amnt) {
		this.position.add(amnt);
	}

	public void increaseRotation(float angDegAmnt) {
		this.angleDegrees += angDegAmnt;
	}

	public Matrix4f toMatrix() {
		return vgl.maths.vector.VectorMaths.transformationMatrix(new Vector3f(
		        position.x,
		        position().y,
		        0f), 0, 0, angleDegrees, 1f);
	}
}
