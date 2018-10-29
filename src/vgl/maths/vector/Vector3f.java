package vgl.maths.vector;
//============================================================================

//Name        : Vector3f
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
* <code> vec.add(new Vector3f(10,15,1).multiply(3)); </code>
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
public class Vector3f implements java.io.Serializable, Comparable<Vector3f> {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 133500525297853769L;

	/**
	 * Public for easier use
	 */
	public float

									x, y, z;

	public static final byte		SIZE_BYTES			= 3 * Float.BYTES;

	//TODO change all of these to static methods

	/**
	 * <strong> Note : represented in OpenGL space : UP = {0,1,0} </strong>
	 */
	public static final Vector3f	UP					= new Vector3f(0, 1, 0);
	/**
	 * <strong> Note : represented in OpenGL space : DOWN = {0,-1,0} </strong>
	 */
	public static final Vector3f	DOWN				= new Vector3f(0, -1, 0);
	/**
	 * <strong> Note : represented in OpenGL space : LEFT = {-1,0,0} </strong>
	 */
	public static final Vector3f	LEFT				= new Vector3f(-1, 0, 0);
	/**
	 * <strong> Note : represented in OpenGL space : RIGHT = {1,0,0} </strong>
	 */
	public static final Vector3f	RIGHT				= new Vector3f(1, 0, 0);
	/**
	 * <strong> Note : represented in OpenGL space : TO_NEGATIVE_Z = {0,0,-1} </strong>
	 */
	public static final Vector3f	TO_NEGATIVE_Z		= new Vector3f(0, 0, -1);
	/**
	 * <strong> Note : represented in OpenGL space : TO_POSITIVE_Z = {0,0,1} </strong>
	 */
	public static final Vector3f	TO_POSITIVE_Z		= new Vector3f(0, 0, 1);

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f(float scalar) {
		this(scalar, scalar, scalar);
	}

	public Vector3f(Vector3f other) {
		this(other.x, other.y, other.z);
	}

	public Vector3f() {
		this(0, 0, 0);
	}

	public Vector3f(Vector2f position) {
		this.x = position.x;
		this.y = position.y;
	}

	public Vector3f add(final Vector3f other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}

	public Vector3f subtract(final Vector3f other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}

	public Vector3f multiply(Vector3f other) {
		this.x *= other.x;
		this.y *= other.y;
		this.z *= other.z;
		return this;
	}

	public Vector3f divide(Vector3f other) {
		this.x /= other.x;
		this.y /= other.y;
		this.z /= other.z;
		return this;
	}

	public Vector3f add(float scalar) {
		x += scalar;
		y += scalar;
		z += scalar;
		return this;
	}

	public Vector3f subtract(float scalar) {
		x -= scalar;
		y -= scalar;
		z -= scalar;
		return this;
	}

	public Vector3f multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	public Vector3f divide(float scalar) {
		x /= scalar;
		y /= scalar;
		z /= scalar;
		return this;
	}

	public static Vector3f cross(Vector3f v1, Vector3f v2) {
		return new Vector3f(v1.y * v2.z - v1.z - v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x);
	}
	
	public Vector3f multiply(Matrix4f mat) {
//		float x = mat.m00 * this.x + mat.m10 * this.y + mat.m20 * this.z;
//		float y = mat.m01 * this.x + mat.m11 * this.y + mat.m21 * this.z;
//		float z = mat.m02 * this.x + mat.m12 * this.y + mat.m22 * this.z;
//		this.x = x;
//		this.y = y;
//		this.z = z;
//		return this;
        
        float A = mat.m00, B = mat.m10, C = mat.m20, D = mat.m30;
        float E = mat.m01, F = mat.m11, G = mat.m21, H = mat.m31;
        float I = mat.m02, J = mat.m12, K = mat.m22, L = mat.m32;
        
        return set(A * x + B * y + C * z + D * 1,
                E * x + F * y + G * z + H * 1,
                I * x + J * y + K * z + L * 1);
	}

	public Vector3f cross(Vector3f other) {
		return Vector3f.cross(this, other);
	}

	public static float dot(Vector3f v1, Vector3f v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}

	public float dot(Vector3f other) {
		return Vector3f.dot(this, other);
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public float lengthSquared() {
		return x * x + y * y + z * z;
	}

	public Vector3f normalize() {
		float len = length();
		x /= len;
		y /= len;
		z /= len;
		return this;
	}

	public Vector3f copy() {
		return new Vector3f(x, y, z);
	}

	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3f reflect(Vector3f normalizedSurface) {
		return Vector3f.reflect(this, normalizedSurface);
	}

	public static Vector3f reflect(Vector3f vector, Vector3f surfaceNormal) {
		Vector3f result = new Vector3f(vector);
		result.normalize();
		return result.subtract(surfaceNormal.copy().normalize().multiply(-2 * Vector3f.dot(vector, surfaceNormal)));
	}

	public String toString() {
		return "Vector3f [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Vector3f other = (Vector3f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}

	@Override
	public int compareTo(Vector3f o) {
		if (o.x <= x && o.y <= y && o.z <= z)
			return 1;
		if (o.x == x && o.y == y && o.z == z)
			return 0;
		return -1;

	}

}
