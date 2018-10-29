package vgl.maths.vector;

import vgl.maths.geom.Transform2D;

//============================================================================
//Name        : VectorMaths
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : Class Responsible for creating all types of transformation matrices,
//as well as some basic vector mathematics
//============================================================================

public class VectorMaths {

	public static final Vector3f	X_AXIS		= new Vector3f(1, 0, 0);
	public static final Vector3f	Y_AXIS		= new Vector3f(0, 1, 0);
	public static final Vector3f	Z_AXIS		= new Vector3f(0, 0, 1);
	public static final Vector3f	scaleVector	= new Vector3f(1, 1, 1);

	private VectorMaths() {

	}

	public static void changeScaling(final float xScale, final float yScale, final float zScale) {
		scaleVector.x = xScale;
		scaleVector.y = yScale;
		scaleVector.z = zScale;
	}

	public static Matrix4f transformationMatrix(final Vector3f translation, final float rx,
	        final float ry, final float rz, final float scale) {
		final Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		matrix.multiply(Matrix4f.scale(scale));
		matrix.multiply(Matrix4f.rotation(rx, X_AXIS));
		matrix.multiply(Matrix4f.rotation(ry, Y_AXIS));
		matrix.multiply(Matrix4f.rotation(rz, Z_AXIS));
		matrix.multiply(Matrix4f.translation(translation));
		return matrix;
	}

	public static Matrix4f transformationMatrix(final Vector3f translation, final float rx,
	        final float ry, final float rz, final Vector3f scale) {
		final Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		matrix.multiply(Matrix4f.scale(scale));
		matrix.multiply(Matrix4f.rotation(rx, X_AXIS));
		matrix.multiply(Matrix4f.rotation(ry, Y_AXIS));
		matrix.multiply(Matrix4f.rotation(rz, Z_AXIS));
		matrix.multiply(Matrix4f.translation(translation));
		return matrix;
	}

	public static Matrix4f transformationMatrix(final vgl.maths.geom.Transform transform) {
		return transformationMatrix(transform.position(),
		        transform.rotationX(),
		        transform.rotationY(),
		        transform.rotationZ(),
		        transform.scale());
	}
	
	public static Matrix4f translationMatrix(final Vector3f translation) {
		final Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		matrix.multiply(Matrix4f.translation(translation));
		return matrix;
	}

	public static Matrix4f rotationMatrix(final float rx, final float ry, final float rz) {
		final Matrix4f matrix = new Matrix4f();
		matrix.rotate(rx, X_AXIS);
		matrix.rotate(ry, Y_AXIS);
		matrix.rotate(rz, Z_AXIS);
		return matrix;
	}

	public static Matrix4f scaleMatrix(final Vector3f scale) {
		final Matrix4f matrix = new Matrix4f();
		matrix.multiply(Matrix4f.scale(scale));
		return matrix;
	}

	private static Vector3f nCameraPos = new Vector3f();

	//
	public static Matrix4f cameraPerspectiveMatrix(
	        final vgl.core.gfx.camera.PerspectiveCamera camera3D) {
		final Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.multiply(Matrix4f.rotation(camera3D.getRotX(), X_AXIS));
		viewMatrix.multiply(Matrix4f.rotation(camera3D.getRotY(), Y_AXIS));
		viewMatrix.multiply(Matrix4f.rotation(camera3D.getRotZ(), Z_AXIS));
		final Vector3f cameraPos = camera3D.getPosition();
		nCameraPos.x = -cameraPos.x;
		nCameraPos.y = -cameraPos.y;
		nCameraPos.z = -cameraPos.z;
		viewMatrix.multiply(Matrix4f.translation(nCameraPos));
		return viewMatrix;
	}

	// public static vgl.maths.vector.Matrix4f orthographicMatrix(final float left,
	// final float right, final float top,
	// final float bottom, final float near, final float far) {
	// return Matrix4f.orthographic(left, right, bottom, top, near, far);
	// }

	// public static vgl.maths.vector.Matrix4f topLeftOrthographic(final float
	// maximumX, final float maximumY) {
	// return orthographicMatrix(0, maximumX, 0, maximumY, -1, 1);
	// }

	public static Vector2f rotate(Vector2f vec, float angRad) {
		Vector2f result = new Vector2f(vec);
		if (result.length() != 1) {
			result.normalize();
		}
		float newX = (float) Math.cos(angRad);
		float newY = (float) Math.sin(angRad);
		result.set(newX, newY);
		return result;
	}

	public static Matrix4f transformationMatrix(Transform2D transform2d) {
		return transform2d.toMatrix();
	}

}
