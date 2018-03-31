package vgl.platform.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import vgl.platform.gl.GLTexture;
import vgl.platform.gl.GLTypes;

public class Texture extends GLTexture {

	public Texture(BufferedImage image) {
		super(image);
	}

	public Texture(final String path) {
		super(path);
	}

	@Override
	protected void removeFromGL() {
		GL11.glDeleteTextures(texture);
	}

	@Override
	protected int load(BufferedImage texImage) {
		int[] pixels = null;
		width = texImage.getWidth();
		height = texImage.getHeight();
		pixels = new int[width * height];
		texImage.getRGB(0, 0, width, height, pixels, 0, width);

		final int[] data = new int[width * height];
		for (int i = 0; i < (width * height); i++) {
			final int a = (pixels[i] & 0xff000000) >> 24;
			final int r = (pixels[i] & 0xff0000) >> 16;
			final int g = (pixels[i] & 0xff00) >> 8;
			final int b = (pixels[i] & 0xff);

			data[i] = (a << 24) | (b << 16) | (g << 8) | r;
		}

		final int result = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, result);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		final IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(
		        ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(data).flip();

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GLTypes.UNSIGNED_BYTE, buffer);
		glBindTexture(GL_TEXTURE_2D, 0);
		if (!glGetBoolean(GL_BLEND))
			glEnable(GL11.GL_BLEND);
		glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		return result;
	}

	@Override
	protected int load(String path) {
		try {
			return load(ImageIO.read(new FileInputStream(path)));
		} catch (IOException e) {
			return -110;
		}
	}

	@Override
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	}

	@Override
	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

}
