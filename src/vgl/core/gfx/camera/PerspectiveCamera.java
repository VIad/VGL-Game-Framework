package vgl.core.gfx.camera;

import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector3f;;

abstract public class PerspectiveCamera {

	private Vector3f	position;

	private float		rotX;

	private float		rotY;

	private float		rotZ;

	public PerspectiveCamera(final float RotX, final float RotY, final float RotZ) {
		this(new Vector3f(0, 0, 0), RotX, RotY, RotZ);
	}

	public PerspectiveCamera(final Vector3f position) {
		this(position, 0, 0, 0);
	}

	public PerspectiveCamera(final Vector3f position, final float RotX, final float RotY, final float RotZ) {
		this.position = position;
		rotX = RotX;
		rotY = RotY;
		rotZ = RotZ;
	}

	abstract public void update();

	public void increaseRotX(final float rotX) {
		this.rotX += rotX;
	}

	public void increaseRotY(final float rotY) {
		this.rotY += rotY;
	}

	public void increaseRotZ(final float rotZ) {
		this.rotZ += rotZ;
	}

	public void increasePosition(final Vector3f difference) {
		increasePosition(difference.x, difference.y, difference.z);
	}

	public void increasePosition(final float x, final float y, final float z) {
		position.x += x;
		position.y += y;
		position.z += z;
	}

	/**
	 * @return the position
	 */
	public vgl.maths.vector.Vector3f getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(final Vector3f position) {
		this.position = position;
	}

	/**
	 * @return the RotX
	 */
	public float getRotX() {
		return rotX;
	}

	/**
	 * @param RotX the RotX to set
	 */
	public void setRotX(final float RotX) {
		rotX = RotX;
	}

	/**
	 * @return the RotY
	 */
	public float getRotY() {
		return rotY;
	}

	/**
	 * @param RotY the RotY to set
	 */
	public void setRotY(final float RotY) {
		rotY = RotY;
	}

	/**
	 * @return the
	 */
	public float getRotZ() {
		return rotZ;
	}

	/**
	 * @param RotZ the RotZ to set
	 */
	public void setRotZ(final float RotZ) {
		rotZ = RotZ;
	}

	public Matrix4f toMatrix() {
		return vgl.maths.vector.VectorMaths.cameraPerspectiveMatrix(this);
	}

}
