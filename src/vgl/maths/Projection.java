package vgl.maths;

import vgl.maths.vector.Matrix4f;

public class Projection {

	public static Matrix4f orthographicProjectionMatrix(float left, float right, float top,
	        float bottom, float near, float far) {
		Matrix4f m4f = Matrix4f.identity();
		m4f.m00 = 2 / (right - left);
		m4f.m30 = -((right + left) / (right - left));
		m4f.m11 = 2 / (top - bottom);
		m4f.m31 = -((top + bottom) / (top - bottom));
		m4f.m22 = -2 / (far - near);
		m4f.m32 = -((far + near) / (far - near));
		return m4f;
	}

	public static Matrix4f topLeftOrthographic(float width, float height) {
		return orthographicProjectionMatrix(0, width, 0, height, -1, 1f);
	}

	/**
	 *
	 * @param FOV
	 *            - THE FOV ANGLE IN DEGREES, NOT RADS
	 * @param FAR_PLANE
	 * @param NEAR_PLANE
	 * @param windowWidth
	 * @param windowHeight
	 * @return
	 */
	public static Matrix4f perspectiveProjectionMatrix(float FOV, int windowWidth, int windowHeight,
	        float near, float far) {
		Matrix4f projectionMatrix;
		float aspectRatio = (float) windowWidth / (float) windowHeight;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far - near;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((far + near) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * near * far) / frustum_length);
		projectionMatrix.m33 = 0;
		return projectionMatrix;
	}

}
