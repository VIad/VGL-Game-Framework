package vgl.maths.vector;

//============================================================================
//Name        : Vector2i
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : A 2 (int) component vector for representing both position
//and direction
//============================================================================

/**
* 
* @author vgl
* <br><br>
* Most methods of the class return a pointer to this, allowing for chained mathematical operations such as <br>
* <code> vec.add(new Vector2i(10,15).multiply(3)); </code>
* <br>
* <strong> Note : none of the methods instead of <code> copy </code> create any new object, but return a pointer to this, so if you
* would like to keep your original vector after an operation, just place a <code> .copy() </code> before you execute an operation
* <br>
* eg:
* </strong>
* <br>
* <code> vec.add(otherVec.copy().multiply(3)) </code>
*
*
*/
public class Vector2i implements java.io.Serializable, Comparable<Vector2i> {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 781321951371634045L;

	public static final byte		SIZE_BYTES			= 2 * Integer.BYTES;

	/**
	 * <strong> Note : represented in OpenGL space : UP = {0,1} </strong>
	 */
	public static final Vector2i	UP					= new Vector2i(0, 1);
	/**
	 * <strong> Note : represented in OpenGL space : DOWN = {0,-1} </strong>
	 */
	public static final Vector2i	DOWN				= new Vector2i(0, -1);
	/**
	 * <strong> Note : represented in OpenGL space : LEFT = {-1,0} </strong>
	 */
	public static final Vector2i	LEFT				= new Vector2i(-1, 0);
	/**
	 * <strong> Note : represented in OpenGL space : RIGHT = {1,0} </strong>
	 */
	public static final Vector2i	RIGHT				= new Vector2i(1, 0);

	/**
	 * Public for easier use
	 */
	public int

									x, y;

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2i(int scalar) {
		this(scalar, scalar);
	}

	public Vector2i(Vector2i other) {
		this(other.x, other.y);
	}

	public Vector2i() {
		this(0, 0);
	}

	public Vector2i add(final Vector2i other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}

	public Vector2i subtract(final Vector2i other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}

	public Vector2i multiply(Vector2i other) {
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}

	public Vector2i divide(Vector2i other) {
		this.x /= other.x;
		this.y /= other.y;
		return this;
	}

	public Vector2i add(float scalar) {
		x += scalar;
		y += scalar;
		return this;
	}

	public Vector2i subtract(float scalar) {
		x -= scalar;
		y -= scalar;
		return this;
	}

	public Vector2i multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public Vector2i divide(float scalar) {
		x /= scalar;
		y /= scalar;
		return this;
	}

	public Vector2i copy() {
		return new Vector2i(x, y);
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float lengthSquared() {
		return ((x * x) + (y * y));
	}

	public static float dot(Vector2i v, Vector2i v2) {
		return v.x * v2.x + v.y * v2.y;
	}

	public float dot(Vector2i other) {
		return Vector2i.dot(this, other);
	}

	public Vector2i negate() {
		x = -x;
		y = -y;
		return this;
	}

	public Vector2i reflectX() {
		x = -x;
		return this;
	}

	public Vector2i reflectY() {
		y = -y;
		return this;
	}

	public Vector2i set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2i normalize() {
		float len = length();
		x /= len;
		y /= len;
		return this;
	}

	public Vector2i reflect(Vector2i normalizedSurface) {
		return Vector2i.reflect(this, normalizedSurface);
	}

	public static Vector2i reflect(Vector2i vector, Vector2i surfaceNormal) {
		Vector2i result = new Vector2i(vector);
		result.normalize();
		return result.subtract(surfaceNormal.copy().normalize().multiply(-2 * Vector2i.dot(vector, surfaceNormal)));
	}

	@Override
	public String toString() {
		return "Vector2i [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Vector2i other = (Vector2i) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public int compareTo(Vector2i o) {
		if (o.x <= x && o.y <= y)
			return 1;
		if (o.x == x && o.y == y)
			return 0;
		return -1;
	}
}