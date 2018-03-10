package vgl.core.essentials;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import vgl.core.gfx.texture.Texture;

public class Loader {

	private final List<Integer>	vaos		= new ArrayList<>();
	private final List<Integer>	vbos		= new ArrayList<>();
	private final List<Integer>	textures	= new ArrayList<>();

	//	public VertexArray loadToVAO(float[] positions, float[] textureCoords, float[] normals, int indices[]) {
	//		int vao = createVAO();
	//		addIndicesBuffer(indices);
	//		storeDataInAttributeList(0, 3, positions);
	//		storeDataInAttributeList(1, 2, textureCoords);
	//		storeDataInAttributeList(2, 3, normals);
	//		unbindVAO();
	//		return new VertexArray(vao, indices.length);
	//	}
	//
	//	public void addBufferToVAO(VertexArray m, float[] data, int attribPosition, int dataSize) {
	//		bindVAO(m);
	//		storeDataInAttributeList(attribPosition, dataSize, data);
	//		unbindVAO();
	//	}
	//
	//	public VertexArray loadToVAO(float[] positions, float[] textureCoords, int indices[]) {
	//		int vao = createVAO();
	//		addIndicesBuffer(indices);
	//		storeDataInAttributeList(0, 3, positions);
	//		storeDataInAttributeList(1, 2, textureCoords);
	//		unbindVAO();
	//		return new VertexArray(vao, indices.length);
	//	}
	//
	//	public VertexArray loadToVAO(float[] positions, int indices[]) {
	//		int vao = createVAO();
	//		addIndicesBuffer(indices);
	//		storeDataInAttributeList(0, 3, positions);
	//		unbindVAO();
	//		return new VertexArray(vao, indices.length);
	//	}
	//
	//	public void bindVAO(VertexArray m) {
	//		GL30.glBindVertexArray(m.getVaoID());
	//	}
	//
	//	public VertexArray loadToVAO(float[] positions) {
	//		int vao = createVAO();
	//		storeDataInAttributeList(0, 3, positions);
	//		unbindVAO();
	//		return new VertexArray(vao, positions.length);
	//	}

	public int loadTexture(final String fileName) {
		final Texture tex = new Texture(fileName);
		final int textureID = tex.getTextureID();
		tex.bind();
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -1);
		tex.unbind();
		textures.add(textureID);
		return textureID;
	}

	public void cleanUp() {
		vaos.forEach(GL30::glDeleteVertexArrays);
		textures.forEach(GL11::glDeleteTextures);
	}
}
