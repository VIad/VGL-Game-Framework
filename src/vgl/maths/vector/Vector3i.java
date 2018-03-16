package vgl.maths.vector;

//============================================================================

//Name        : Vector3i
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : A 3 (float) component vector for representing both position
//and direction
//============================================================================

/**
* 
* @author vgl
* <br><br>
* Most methods of the class return a pointer to this, allowing for chained mathematical operations such as <br>
* <code> vec.add(new Vector3i(10,15,1).multiply(3)); </code>
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
public class Vector3i implements java.io.Serializable, Comparable<Vector3i> {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 133500525297853769L;

	/**
	 * Public for easier use
	 */
	public int

									x, y, z;

	public static final byte		SIZE_BYTES			= 3 * Integer.BYTES;

	//TODO change all of these to static methods

	/**
	 * <strong> Note : represented in OpenGL space : UP = {0,1,0} </strong>
	 */
	public static final Vector3i	UP					= new Vector3i(0, 1, 0);
	/**
	 * <strong> Note : represented in OpenGL space : DOWN = {0,-1,0} </strong>
	 */
	public static final Vector3i	DOWN				= new Vector3i(0, -1, 0);
	/**
	 * <strong> Note : represented in OpenGL space : LEFT = {-1,0,0} </strong>
	 */
	public static final Vector3i	LEFT				= new Vector3i(-1, 0, 0);
	/**
	 * <strong> Note : represented in OpenGL space : RIGHT = {1,0,0} </strong>
	 */
	public static final Vector3i	RIGHT				= new Vector3i(1, 0, 0);
	/**
	 * <strong> Note : represented in OpenGL space : TO_NEGATIVE_Z = {0,0,-1} </strong>
	 */
	public static final Vector3i	TO_NEGATIVE_Z		= new Vector3i(0, 0, -1);
	/**
	 * <strong> Note : represented in OpenGL space : TO_POSITIVE_Z = {0,0,1} </strong>
	 */
	public static final Vector3i	TO_POSITIVE_Z		= new Vector3i(0, 0, 1);

	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i(int scalar) {
		this(scalar, scalar, scalar);
	}

	public Vector3i(Vector3i other) {
		this(other.x, other.y, other.z);
	}

	public Vector3i() {
		this(0, 0, 0);
	}

	public Vector3i add(final Vector3i other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}

	public Vector3i subtract(final Vector3i other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}

	public Vector3i multiply(Vector3i other) {
		this.x *= other.x;
		this.y *= other.y;
		this.z *= other.z;
		return this;
	}

	public Vector3i divide(Vector3i other) {
		this.x /= other.x;
		this.y /= other.y;
		this.z /= other.z;
		return this;
	}

	public Vector3i add(float scalar) {
		x += scalar;
		y += scalar;
		z += scalar;
		return this;
	}

	public Vector3i subtract(float scalar) {
		x -= scalar;
		y -= scalar;
		z -= scalar;
		return this;
	}

	public Vector3i multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	public Vector3i divide(float scalar) {
		x /= scalar;
		y /= scalar;
		z /= scalar;
		return this;
	}

	public static Vector3i cross(Vector3i v1, Vector3i v2) {
		return new Vector3i(v1.y * v2.z - v1.z - v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x);
	}

	public Vector3i cross(Vector3i other) {
		return Vector3i.cross(this, other);
	}

	public static float dot(Vector3i v1, Vector3i v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}

	public float dot(Vector3i other) {
		return Vector3i.dot(this, other);
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public float lengthSquared() {
		return x * x + y * y + z * z;
	}

	public Vector3i normalize() {
		float len = length();
		x /= len;
		y /= len;
		z /= len;
		return this;
	}

	public Vector3i copy() {
		return new Vector3i(x, y, z);
	}

	public Vector3i set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3i reflect(Vector3i normalizedSurface) {
		return Vector3i.reflect(this, normalizedSurface);
	}

	public static Vector3i reflect(Vector3i vector, Vector3i surfaceNormal) {
		Vector3i result = new Vector3i(vector);
		result.normalize();
		return result.subtract(surfaceNormal.copy().normalize().multiply(-2 * Vector3i.dot(vector, surfaceNormal)));
	}

	/**
	 * 
	 * transform.rows[0].x * x + transform.rows[0].y * y + transform.rows[0].z * z + transform.rows[0].w,
			transform.rows[1].x * x + transform.rows[1].y * y + transform.rows[1].z * z + transform.rows[1].w,
			transform.rows[2].x * x + transform.rows[2].y * y + transform.rows[2].z * z + transform.rows[2].w
	 */

	public Vector3i multiply(Matrix4f mat) {
		throw new UnsupportedOperationException("TODO NOT YET IMPLEMENTED");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
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
		Vector3i other = (Vector3i) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	public String toString() {
		return "Vector3i [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	@Override
	public int compareTo(Vector3i o) {
		if (o.x <= x && o.y <= y && o.z <= z)
			return 1;
		if (o.x == x && o.y == y && o.z == z)
			return 0;
		return -1;

	}

}