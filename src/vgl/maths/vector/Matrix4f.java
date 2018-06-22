package vgl.maths.vector;
//==================================================================================

//Name        : Matrix4f
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : 4x4 Matrix used for 3 dimensional transformations (float) components
//===================================================================================

/**
 * @author vgl Column major implementation of a 4x4 matrix <br>
 *         <strong> First number is columns, second is rows (eg m31 = 4th
 *         column, 2nd row)</strong> <br>
 *         <br>
 *         Most methods of the class return a pointer to this, allowing for
 *         chained mathematical operations such as <br>
 *         <code> matrix.add(matrix2).multiply(3)); </code> <br>
 *         <strong> Note : none of the methods instead of <code> copy </code>
 *         create any new object, but return a pointer to this, so if you would
 *         like to keep your original vector after an operation, just place a
 *         <code> .copy() </code> before you execute an operation <br>
 *         eg: </strong> <br>
 *         <code> matrix.add(otherMatrix.copy().multiply(new Matrix4f(1f)) </code>
 */
public class Matrix4f implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -34548003976367253L;

	// public float
	// m00, m01, m02, m03,
	// m10, m11, m12, m13,
	// m20, m21, m22, m23,
	// m30, m31, m32, m33;

	public static final byte	SIZE_BYTES	= 4 * 4 * Float.BYTES;

	/**
	 * Public for easier use
	 */
	public float

	m00, m01, m02, m03,
	m10, m11, m12, m13,
	m20, m21, m22, m23,
	m30, m31, m32, m33;

	public Matrix4f() {
		setIdentity();
	}

	public Matrix4f(float diagonal) {
		setAll(0);
		m00 = m11 = m22 = m33 = diagonal;
	}

	public Matrix4f(Vector4f col0, Vector4f col1, Vector4f col2, Vector4f col3) {
		m00 = col0.x;
		m10 = col0.y;
		m20 = col0.z;
		m30 = col0.w;

		m01 = col1.x;
		m11 = col1.y;
		m21 = col1.z;
		m31 = col1.w;

		m02 = col2.x;
		m12 = col2.y;
		m22 = col2.z;
		m32 = col2.w;

		m03 = col3.x;
		m13 = col3.y;
		m23 = col3.z;
		m33 = col3.w;
	}

	// public Matrix4f(java.nio.FloatBuffer from) {
	// read(from);
	// }

	public Matrix4f(Matrix4f other) {
		set(other);
	}

	public void setIdentity() {
		setAll(0);
		m00 = m11 = m22 = m33 = 1;
	}

	public Matrix4f add(Matrix4f other) {
		m00 += other.m00;
		m01 += other.m01;
		m02 += other.m02;
		m03 += other.m03;
		m10 += other.m10;
		m11 += other.m11;
		m12 += other.m12;
		m13 += other.m13;
		m20 += other.m20;
		m21 += other.m21;
		m22 += other.m22;
		m23 += other.m23;
		m30 += other.m30;
		m31 += other.m31;
		m32 += other.m32;
		m33 += other.m33;
		return this;
	}

	public Vector4f multiply(Vector4f vec) {
		Vector4f mul = vec.copy();
		float x = this.m00 * mul.x + this.m10 * mul.y + this.m20 * mul.z + this.m30 * mul.w;
		float y = this.m01 * mul.x + this.m11 * mul.y + this.m21 * mul.z + this.m31 * mul.w;
		float z = this.m02 * mul.x + this.m12 * mul.y + this.m22 * mul.z + this.m32 * mul.w;
		float w = this.m03 * mul.x + this.m13 * mul.y + this.m23 * mul.z + this.m33 * mul.w;
		mul.x = x;
		mul.y = y;
		mul.z = z;
		mul.w = w;
		return mul;
	}

	public Matrix4f multiply(float scalar) {
		m00 *= scalar;
		m01 *= scalar;
		m02 *= scalar;
		m03 *= scalar;
		m10 *= scalar;
		m11 *= scalar;
		m12 *= scalar;
		m13 *= scalar;
		m20 *= scalar;
		m21 *= scalar;
		m22 *= scalar;
		m23 *= scalar;
		m30 *= scalar;
		m31 *= scalar;
		m32 *= scalar;
		m33 *= scalar;
		return this;
	}

	public void set(Matrix4f other) {
		m00 = other.m00;
		m01 = other.m01;
		m02 = other.m02;
		m03 = other.m03;
		m10 = other.m10;
		m11 = other.m11;
		m12 = other.m12;
		m13 = other.m13;
		m20 = other.m20;
		m21 = other.m21;
		m22 = other.m22;
		m23 = other.m23;
		m30 = other.m30;
		m31 = other.m31;
		m32 = other.m32;
		m33 = other.m33;
	}

	public void setAll(float n) {
		m01 = m02 = m03 = m10 = m12 = m13 = m20 = m21 = m23 = m30 = m31 = m32 = m00 = m11 = m22 = m33 = n;
	}

	// private void read(java.nio.FloatBuffer buffer) {
	// buffer.flip();
	// m00 = buffer.get();
	// m10 = buffer.get();
	// m20 = buffer.get();
	// m30 = buffer.get();
	// m01 = buffer.get();
	// m11 = buffer.get();
	// m21 = buffer.get();
	// m31 = buffer.get();
	// m02 = buffer.get();
	// m12 = buffer.get();
	// m22 = buffer.get();
	// m32 = buffer.get();
	// m03 = buffer.get();
	// m13 = buffer.get();
	// m23 = buffer.get();
	// m33 = buffer.get();
	// }

	public void transpose() {
		float m00 = this.m00;
		float m01 = this.m10;
		float m02 = this.m20;
		float m03 = this.m30;
		float m10 = this.m01;
		float m11 = this.m11;
		float m12 = this.m21;
		float m13 = this.m31;
		float m20 = this.m02;
		float m21 = this.m12;
		float m22 = this.m22;
		float m23 = this.m32;
		float m30 = this.m03;
		float m31 = this.m13;
		float m32 = this.m23;
		float m33 = this.m33;

		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
	}

	// public void store(java.nio.FloatBuffer buffer) {
	// buffer.put(m00);
	// buffer.put(m01);
	// buffer.put(m02);
	// buffer.put(m03);
	// buffer.put(m10);
	// buffer.put(m11);
	// buffer.put(m12);
	// buffer.put(m13);
	// buffer.put(m20);
	// buffer.put(m21);
	// buffer.put(m22);
	// buffer.put(m23);
	// buffer.put(m30);
	// buffer.put(m31);
	// buffer.put(m32);
	// buffer.put(m33);
	// buffer.flip();
	// }

//	public void store(float[] array) {
//		throw new IllegalAccessError();
//		// if (array.length < 16)
//		// throw new BufferOverflowException();
//		// array[0] = m00;
//		// array[1] = m01;
//		// array[1] = m02;
//		// array[1] = m03;
//	}

	// public void storeTransposed(java.nio.FloatBuffer buffer) {
	// Matrix4f copy = copy();
	// buffer.put(copy.m00).put(copy.m10).put(copy.m20).put(copy.m30).put(copy.m01).put(
	// copy.m11).put(copy.m21).put(copy.m31).put(copy.m02).put(copy.m12).put(copy.m22).put(
	// copy.m32).put(copy.m03).put(copy.m13).put(copy.m23).put(copy.m33);
	// buffer.flip();
	// }

	public Matrix4f multiply(Matrix4f other) {

		float m00 = this.m00 * other.m00 + this.m10 * other.m01 + this.m20 * other.m02 + this.m30 * other.m03;
		float m01 = this.m01 * other.m00 + this.m11 * other.m01 + this.m21 * other.m02 + this.m31 * other.m03;
		float m02 = this.m02 * other.m00 + this.m12 * other.m01 + this.m22 * other.m02 + this.m32 * other.m03;
		float m03 = this.m03 * other.m00 + this.m13 * other.m01 + this.m23 * other.m02 + this.m33 * other.m03;
		float m10 = this.m00 * other.m10 + this.m10 * other.m11 + this.m20 * other.m12 + this.m30 * other.m13;
		float m11 = this.m01 * other.m10 + this.m11 * other.m11 + this.m21 * other.m12 + this.m31 * other.m13;
		float m12 = this.m02 * other.m10 + this.m12 * other.m11 + this.m22 * other.m12 + this.m32 * other.m13;
		float m13 = this.m03 * other.m10 + this.m13 * other.m11 + this.m23 * other.m12 + this.m33 * other.m13;
		float m20 = this.m00 * other.m20 + this.m10 * other.m21 + this.m20 * other.m22 + this.m30 * other.m23;
		float m21 = this.m01 * other.m20 + this.m11 * other.m21 + this.m21 * other.m22 + this.m31 * other.m23;
		float m22 = this.m02 * other.m20 + this.m12 * other.m21 + this.m22 * other.m22 + this.m32 * other.m23;
		float m23 = this.m03 * other.m20 + this.m13 * other.m21 + this.m23 * other.m22 + this.m33 * other.m23;
		float m30 = this.m00 * other.m30 + this.m10 * other.m31 + this.m20 * other.m32 + this.m30 * other.m33;
		float m31 = this.m01 * other.m30 + this.m11 * other.m31 + this.m21 * other.m32 + this.m31 * other.m33;
		float m32 = this.m02 * other.m30 + this.m12 * other.m31 + this.m22 * other.m32 + this.m32 * other.m33;
		float m33 = this.m03 * other.m30 + this.m13 * other.m31 + this.m23 * other.m32 + this.m33 * other.m33;

		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;

		return this;
	}

	// public float
	// m00, m01, m02, m03,
	// m10, m11, m12, m13,
	// m20, m21, m22, m23,
	// m30, m31, m32, m33;
	public Vector4f column(int index) {
		switch (index)
		{
			default:
				throw new IllegalArgumentException("Index must be between 0 and 3");
			case 0:
				return new Vector4f(m00, m10, m20, m30);
			case 1:
				return new Vector4f(m01, m11, m21, m31);
			case 2:
				return new Vector4f(m02, m12, m22, m32);
			case 3:
				return new Vector4f(m03, m13, m23, m33);
		}
	}

	public static Matrix4f identity() {
		return new Matrix4f();
	}

	public Matrix4f rotate(float angDeg, Vector3f axis) {
		Matrix4f m4fRot = rotation(angDeg, axis);
		return this.multiply(m4fRot);
	}

	public static Matrix4f translation(Vector3f translation) {
		Matrix4f trans = new Matrix4f();
		trans.m30 += trans.m00 * translation.x + trans.m10 * translation.y + trans.m20 * translation.z;
		trans.m31 += trans.m01 * translation.x + trans.m11 * translation.y + trans.m21 * translation.z;
		trans.m32 += trans.m02 * translation.x + trans.m12 * translation.y + trans.m22 * translation.z;
		trans.m33 += trans.m03 * translation.x + trans.m13 * translation.y + trans.m23 * translation.z;
		return trans;
	}

	public static Matrix4f scale(Vector3f vec) {
		Matrix4f m4f = identity();
		m4f.m00 = m4f.m00 * vec.x;
		m4f.m01 = m4f.m01 * vec.x;
		m4f.m02 = m4f.m02 * vec.x;
		m4f.m03 = m4f.m03 * vec.x;
		m4f.m10 = m4f.m10 * vec.y;
		m4f.m11 = m4f.m11 * vec.y;
		m4f.m12 = m4f.m12 * vec.y;
		m4f.m13 = m4f.m13 * vec.y;
		m4f.m20 = m4f.m20 * vec.z;
		m4f.m21 = m4f.m21 * vec.z;
		m4f.m22 = m4f.m22 * vec.z;
		m4f.m23 = m4f.m23 * vec.z;
		return m4f;
	}

	public static Matrix4f scale(float uniformScale) {
		Matrix4f m4f = identity();
		m4f.m00 = uniformScale;
		m4f.m11 = uniformScale;
		m4f.m22 = uniformScale;
		return m4f;
	}

	public static Matrix4f rotation(float angDeg, Vector3f axis) {
		Matrix4f rotation = new Matrix4f();
		float c = (float) Math.cos(Math.toRadians(angDeg));
		float s = (float) Math.sin(Math.toRadians(angDeg));
		float omc = 1.0f - c;
		float xy = axis.x * axis.y;
		float yz = axis.y * axis.z;
		float xz = axis.x * axis.z;
		float xs = axis.x * s;
		float ys = axis.y * s;
		float zs = axis.z * s;

		float f00 = axis.x * axis.x * omc + c;
		float f01 = xy * omc + zs;
		float f02 = xz * omc - ys;

		float f10 = xy * omc - zs;
		float f11 = axis.y * axis.y * omc + c;
		float f12 = yz * omc + xs;

		float f20 = xz * omc + ys;
		float f21 = yz * omc - xs;
		float f22 = axis.z * axis.z * omc + c;

		float t00 = rotation.m00 * f00 + rotation.m10 * f01 + rotation.m20 * f02;
		float t01 = rotation.m01 * f00 + rotation.m11 * f01 + rotation.m21 * f02;
		float t02 = rotation.m02 * f00 + rotation.m12 * f01 + rotation.m22 * f02;
		float t03 = rotation.m03 * f00 + rotation.m13 * f01 + rotation.m23 * f02;
		float t10 = rotation.m00 * f10 + rotation.m10 * f11 + rotation.m20 * f12;
		float t11 = rotation.m01 * f10 + rotation.m11 * f11 + rotation.m21 * f12;
		float t12 = rotation.m02 * f10 + rotation.m12 * f11 + rotation.m22 * f12;
		float t13 = rotation.m03 * f10 + rotation.m13 * f11 + rotation.m23 * f12;
		rotation.m20 = rotation.m00 * f20 + rotation.m10 * f21 + rotation.m20 * f22;
		rotation.m21 = rotation.m01 * f20 + rotation.m11 * f21 + rotation.m21 * f22;
		rotation.m22 = rotation.m02 * f20 + rotation.m12 * f21 + rotation.m22 * f22;
		rotation.m23 = rotation.m03 * f20 + rotation.m13 * f21 + rotation.m23 * f22;
		rotation.m00 = t00;
		rotation.m01 = t01;
		rotation.m02 = t02;
		rotation.m03 = t03;
		rotation.m10 = t10;
		rotation.m11 = t11;
		rotation.m12 = t12;
		rotation.m13 = t13;
		return rotation;
	}

	public Matrix4f copy() {
		return new Matrix4f(this);
	}

	public Matrix4f negate() {
		return multiply(-1);
	}

	public Matrix4f subtract(Matrix4f other) {
		return add(other.copy().negate());
	}

	@Override
	public String toString() {
		return "Matrix4f [m00=" + m00 + ", m01=" + m01 + ", m02=" + m02 + ", m03=" + m03 + ", m10=" + m10 + ", m11="
		        + m11 + ", m12=" + m12 + ", m13=" + m13 + ", m20=" + m20 + ", m21=" + m21 + ", m22=" + m22 + ", m23="
		        + m23 + ", m30=" + m30 + ", m31=" + m31 + ", m32=" + m32 + ", m33=" + m33 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(m00);
		result = prime * result + Float.floatToIntBits(m01);
		result = prime * result + Float.floatToIntBits(m02);
		result = prime * result + Float.floatToIntBits(m03);
		result = prime * result + Float.floatToIntBits(m10);
		result = prime * result + Float.floatToIntBits(m11);
		result = prime * result + Float.floatToIntBits(m12);
		result = prime * result + Float.floatToIntBits(m13);
		result = prime * result + Float.floatToIntBits(m20);
		result = prime * result + Float.floatToIntBits(m21);
		result = prime * result + Float.floatToIntBits(m22);
		result = prime * result + Float.floatToIntBits(m23);
		result = prime * result + Float.floatToIntBits(m30);
		result = prime * result + Float.floatToIntBits(m31);
		result = prime * result + Float.floatToIntBits(m32);
		result = prime * result + Float.floatToIntBits(m33);
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
		Matrix4f other = (Matrix4f) obj;
		if (Float.floatToIntBits(m00) != Float.floatToIntBits(other.m00))
			return false;
		if (Float.floatToIntBits(m01) != Float.floatToIntBits(other.m01))
			return false;
		if (Float.floatToIntBits(m02) != Float.floatToIntBits(other.m02))
			return false;
		if (Float.floatToIntBits(m03) != Float.floatToIntBits(other.m03))
			return false;
		if (Float.floatToIntBits(m10) != Float.floatToIntBits(other.m10))
			return false;
		if (Float.floatToIntBits(m11) != Float.floatToIntBits(other.m11))
			return false;
		if (Float.floatToIntBits(m12) != Float.floatToIntBits(other.m12))
			return false;
		if (Float.floatToIntBits(m13) != Float.floatToIntBits(other.m13))
			return false;
		if (Float.floatToIntBits(m20) != Float.floatToIntBits(other.m20))
			return false;
		if (Float.floatToIntBits(m21) != Float.floatToIntBits(other.m21))
			return false;
		if (Float.floatToIntBits(m22) != Float.floatToIntBits(other.m22))
			return false;
		if (Float.floatToIntBits(m23) != Float.floatToIntBits(other.m23))
			return false;
		if (Float.floatToIntBits(m30) != Float.floatToIntBits(other.m30))
			return false;
		if (Float.floatToIntBits(m31) != Float.floatToIntBits(other.m31))
			return false;
		if (Float.floatToIntBits(m32) != Float.floatToIntBits(other.m32))
			return false;
		if (Float.floatToIntBits(m33) != Float.floatToIntBits(other.m33))
			return false;
		return true;
	}

}
