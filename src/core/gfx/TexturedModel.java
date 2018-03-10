package vgl.core.gfx;

import vgl.core.buffers.VertexArray;

public class TexturedModel {
	// TODO
	private final VertexArray	mMesh;

	private final ModelTexture	mTexture;

	public TexturedModel(final VertexArray model, final ModelTexture texture) {
		mMesh = model;
		mTexture = texture;
	}

	public VertexArray getVAO() {
		return mMesh;
	}

	public ModelTexture getTexture() {
		return mTexture;
	}
}
