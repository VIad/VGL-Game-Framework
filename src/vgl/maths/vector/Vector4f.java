package vgl.maths.vector;
//============================================================================

//Name        : Vector4f
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : A 4 (float) component vector for representing both position
//and direction
//============================================================================

/**
* 
* @author vgl
* <br><br>
* Most methods of the class return a pointer to this, allowing for chained mathematical operations such as <br>
* <code> vec.add(new Vector4f(10,15,-5,1).multiply(3)); </code>
* <br>
*  <strong> Note : none of the methods instead of <code> copy </code> create any new object, but return a pointer to this, so if you
* would like to keep your original vector after an operation, just place a <code> .copy() </code> before you execute an operation
* <br>
* eg:
* </strong>
* <br>
* <code> vec.add(otherVec.copy().multiply(3)) </code>
*
*/
public class Vector4f implements java.io.Serializable, Comparable<Vector4f> {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5656054344311312671L;

	/**
	 * Public for easier use
	 */
	public float

								x, y, z, w;

	public static final byte	SIZE_BYTES			= 4 * Float.BYTES;

	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector4f(float scalar) {
		this(scalar, scalar, scalar, scalar);
	}

	public Vector4f(Vector3f other) {
		this(other.x, other.y, other.z, 1);
	}

	public Vector4f(Vector4f other) {
		this(other.x, other.y, other.z, other.w);
	}

	public Vector4f() {
		this(0, 0, 0, 0);
	}

	public Vector4f add(final Vector4f other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		this.w += other.w;
		return this;
	}

	public Vector4f subtract(final Vector4f other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		this.w -= other.w;
		return this;
	}

	public Vector4f multiply(Vector4f other) {
		this.x *= other.x;
		this.y *= other.y;
		this.z *= other.z;
		this.w *= other.w;
		return this;
	}

	public Vector4f divide(Vector4f other) {
		this.x /= other.x;
		this.y /= other.y;
		this.z /= other.z;
		this.w /= other.w;
		return this;
	}

	public Vector4f add(float scalar) {
		x += scalar;
		y += scalar;
		z += scalar;
		w += scalar;
		return this;
	}

	public Vector4f subtract(float scalar) {
		x -= scalar;
		y -= scalar;
		z -= scalar;
		w -= scalar;
		return this;
	}

	public Vector4f multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		w *= scalar;
		return this;
	}

	public Vector4f divide(float scalar) {
		x /= scalar;
		y /= scalar;
		z /= scalar;
		w /= scalar;
		return this;
	}

	//	public Vector4f multiply(Matrix4f matrix) {
	//		float x = matrix.m00 * this.x + matrix.m01 * this.y + matrix.m02 * this.z + matrix.m03 * this.w;
	//		float y = matrix.m10 * this.x + matrix.m11 * this.y + matrix.m12 * this.z + matrix.m13 * this.w;
	//		float z = matrix.m20 * this.x + matrix.m21 * this.y + matrix.m22 * this.z + matrix.m23 * this.w;
	//		float w = matrix.m30 * this.x + matrix.m31 * this.y + matrix.m32 * this.z + matrix.m33 * this.w;
	//		this.x = x;
	//		this.y = y;
	//		this.z = z;
	//		this.w = w;
	//		return this;
	//	}
//
//	public void store(java.nio.FloatBuffer buffer) {
//		buffer.put(x).put(y).put(z).put(w).flip();
//	}

	public float dot(Vector4f other) {
		return x * other.x + y * other.y + z * other.z + w * other.w;
	}

	public static float dot(Vector4f v1, Vector4f v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z + v1.w * v2.w;
	}

	@Override
	public String toString() {
		return "Vector4f [x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(w);
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector4f other = (Vector4f) obj;
		if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w))
			return false;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}

	@Override
	public int compareTo(Vector4f o) {
		if (o.x <= x && o.y <= y && o.z <= z && o.w <= w)
			return 1;
		if (o.x == x && o.y == y && o.z == z && o.w == w)
			return 0;
		return -1;
	}

	public Vector4f copy() {
		return new Vector4f(this);
	}

}
