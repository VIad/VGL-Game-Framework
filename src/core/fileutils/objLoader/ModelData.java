package vgl.core.fileutils.objLoader;

import vgl.core.buffers.VertexArray;

public class ModelData {

	private final float[]	vertices;
	private final float[]	textureCoords;
	private final float[]	normals;
	private final int[]		indices;
	private final float		furthestPoint;

	public ModelData(final float[] vertices, final float[] textureCoords, final float[] normals, final int[] indices,
	        final float furthestPoint) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
		this.furthestPoint = furthestPoint;
	}

	public float[] getVertices() {
		return vertices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public float[] getNormals() {
		return normals;
	}

	public int[] getIndices() {
		return indices;
	}

	public float getFurthestPoint() {
		return furthestPoint;
	}

}