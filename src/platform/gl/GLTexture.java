package vgl.platform.gl;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import vgl.core.internal.Checks;

abstract public class GLTexture {

	public static final List<GLTexture> all;

	static {
		all = new ArrayList<>();
	}

	protected int		width, height;
	protected final int	texture;

	public GLTexture(final String path) {
		Checks.checkIfInitialized();
		texture = load(path);
		all.add(this);
	}

	public GLTexture(final BufferedImage image) {
		Checks.checkIfInitialized();
		texture = load(image);
		all.add(this);
	}

	public static void cleanTextureCache() {
		all.forEach(GLTexture::removeFromGL);
	}

	protected abstract void removeFromGL();

	protected abstract int load(final BufferedImage texImage);

	protected abstract int load(final String path);

	public abstract void bind();

	public abstract void unbind();

	public int getTextureID() {
		return texture;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}