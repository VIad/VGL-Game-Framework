package vgl.core.gfx.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

public class TextureLoader {

	private TextureLoader() {}

	public static int loadTexture(final String filePath, boolean genMipMap) {
		final Texture tex = new Texture(filePath);
		final int textureID = tex.getTextureID();
		tex.bind();
		if (genMipMap) {
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -1);
		}
		tex.unbind();
		return textureID;
	}

}
