package vgl.maths.vector;

//============================================================================
//Name        : Vector2f
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : A 2 (float) component vector for representing both position
//and direction
//============================================================================

/**
 * 
 * @author vgl <br>
 *         <br>
 *         Most methods of the class return a pointer to this, allowing for
 *         chained mathematical operations such as <br>
 *         <code> vec.add(new Vector2f(10,15).multiply(3)); </code> <br>
 *         <strong> Note : none of the methods instead of <code> copy </code>
 *         create any new object, but return a pointer to this, so if you would
 *         like to keep your original vector after an operation, just place a
 *         <code> .copy() </code> before you execute an operation <br>
 *         eg: </strong> <br>
 *         <code> vec.add(otherVec.copy().multiply(3)) </code>
 *
 *
 */
public class Vector2f implements java.io.Serializable, Comparable<Vector2f> {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 781321951371634045L;

	public static final byte		SIZE_BYTES			= 2 * Float.BYTES;

	/**
	 * <strong> Note : represented in OpenGL space : UP = {0,1} </strong>
	 */
	public static final Vector2f	UP					= new Vector2f(0f, 1f);
	/**
	 * <strong> Note : represented in OpenGL space : DOWN = {0,-1} </strong>
	 */
	public static final Vector2f	DOWN				= new Vector2f(0f, -1f);
	/**
	 * <strong> Note : represented in OpenGL space : LEFT = {-1,0} </strong>
	 */
	public static final Vector2f	LEFT				= new Vector2f(-1f, 0f);
	/**
	 * <strong> Note : represented in OpenGL space : RIGHT = {1,0} </strong>
	 */
	public static final Vector2f	RIGHT				= new Vector2f(1f, 0f);

	/**
	 * Public for easier use
	 */
	public float

									x, y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2f(float scalar) {
		this(scalar, scalar);
	}

	public Vector2f(Vector2f other) {
		this(other.x, other.y);
	}

	public Vector2f() {
		this(0f, 0f);
	}

	public Vector2f add(final Vector2f other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}

	public Vector2f subtract(final Vector2f other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}

	public Vector2f multiply(Vector2f other) {
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}

	public Vector2f divide(Vector2f other) {
		this.x /= other.x;
		this.y /= other.y;
		return this;
	}

	public Vector2f add(float scalar) {
		x += scalar;
		y += scalar;
		return this;
	}
	
	public Vector2f add(float xamnt, float yamnt) {
		return set(x + xamnt, y + yamnt);
	}

	public Vector2f subtract(float scalar) {
		x -= scalar;
		y -= scalar;
		return this;
	}

	public Vector2f multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public Vector2f divide(float scalar) {
		x /= scalar;
		y /= scalar;
		return this;
	}

	public Vector2f rotate(float angleDegrees) {
		float c = (float) Math.cos(Math.toRadians(angleDegrees));
		float s = (float) Math.sin(Math.toRadians(angleDegrees));

		return set(x * c - y * s, x * s + y * c);
	}

	public float angleRad() {
		return (float) Math.atan2(y, x);
	}

	public float angleDegrees() {
		return (float) Math.toDegrees(angleRad());
	}

	public Vector2f copy() {
		return new Vector2f(x, y);
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float lengthSquared() {
		return ((x * x) + (y * y));
	}

	public boolean isParallel(Vector2f other) {
		float xx = x == 0 ? Math.abs(x) : x;
		float yy = y == 0 ? Math.abs(y) : y;
		float xo = other.x == 0 ? Math.abs(other.x) : other.x;
		float yo = other.y == 0 ? Math.abs(other.y) : other.y;
		return new Vector2f(xo, yo).negate().equals(new Vector2f(xx, yy).negate());
	}

	public static float dot(Vector2f v, Vector2f v2) {
		return v.x * v2.x + v.y * v2.y;
	}

	public Vector2f scale(float xFactor/* See what i did there.... */, float yFactor) {
		return set(x * xFactor, y * yFactor);
	}

	public float dot(Vector2f other) {
		return Vector2f.dot(this, other);
	}

	public Vector2f negate() {
		if (x != 0)
			x = -x;
		if (y != 0)
			y = -y;
		return this;
	}

	public Vector2f reflectX() {
		x = -x;
		return this;
	}

	public Vector2f reflectY() {
		y = -y;
		return this;
	}

	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2f normalize() {
		float len = length();
		if (len == 0 || len == 1)
			return this;
		x /= len;
		y /= len;
		return this;
	}

	public Vector2f perp(boolean flip) {
		return !flip ? new Vector2f(y, -x) : new Vector2f(-y, x);
	}

	public Vector2f perp() {
		return perp(false);
	}

	public Vector2f reflect(Vector2f normalizedSurface) {
		return Vector2f.reflect(this, normalizedSurface);
	}

	public static Vector2f reflect(Vector2f vector, Vector2f surfaceNormal) {
		Vector2f result = new Vector2f(vector);
		result.normalize();
		return result.subtract(surfaceNormal.copy().normalize().multiply(-2 * Vector2f.dot(vector, surfaceNormal)));
	}

	@Override
	public String toString() {
		return "Vector2f [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
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
		Vector2f other = (Vector2f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}

	@Override
	public int compareTo(Vector2f o) {
		if (o.x <= x && o.y <= y)
			return 1;
		if (o.x == x && o.y == y)
			return 0;
		return -1;
	}

	public Vector2f subtract(float anchorX, float anchorY) {
		return set(x - anchorX, y - anchorY);
	}
}
