package vgl.core.fileutils.objLoader;

import vgl.maths.vector.Vector3f;

class Vertex {

	private static final int	NO_INDEX		= -1;

	private final Vector3f		position;
	private int					textureIndex	= NO_INDEX;
	private int					normalIndex		= NO_INDEX;
	private Vertex				duplicateVertex	= null;
	private final int			index;
	private final float			length;

	public Vertex(final int index, final Vector3f position) {
		this.index = index;
		this.position = position;
		length = position.length();
	}

	public int getIndex() {
		return index;
	}

	public float getLength() {
		return length;
	}

	public boolean isSet() {
		return (textureIndex != NO_INDEX) && (normalIndex != NO_INDEX);
	}

	public boolean hasSameTextureAndNormal(final int textureIndexOther, final int normalIndexOther) {
		return (textureIndexOther == textureIndex) && (normalIndexOther == normalIndex);
	}

	public void setTextureIndex(final int textureIndex) {
		this.textureIndex = textureIndex;
	}

	public void setNormalIndex(final int normalIndex) {
		this.normalIndex = normalIndex;
	}

	public Vector3f getPosition() {
		return position;
	}

	public int getTextureIndex() {
		return textureIndex;
	}

	public int getNormalIndex() {
		return normalIndex;
	}

	public Vertex getDuplicateVertex() {
		return duplicateVertex;
	}

	public void setDuplicateVertex(final Vertex duplicateVertex) {
		this.duplicateVertex = duplicateVertex;
	}

}