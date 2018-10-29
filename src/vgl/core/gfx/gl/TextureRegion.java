package vgl.core.gfx.gl;

import vgl.main.VGL;
import vgl.maths.Maths;
import vgl.maths.geom.shape2d.RectInt;
import vgl.tools.IResource;

public class TextureRegion implements IResource {

	private Texture	texture;

	private float	minX, minY, maxX, maxY;
	private RectInt pixelSize;

	public TextureRegion(Texture tex, /*CounterClockwise */RectInt textureRect) {
		this.texture = tex;
		this.pixelSize = textureRect;
		if (textureRect == null)
			this.defaultUVS();
		else {
			this.minX = Maths.linearConversion(textureRect.x, 0, tex.getWidth(), 0f, 1f);
			this.minY = Maths.linearConversion(textureRect.y, 0f, texture.getHeight(), 0f, 1f);
			this.maxX = Maths.linearConversion(textureRect.x + textureRect.width, 0f, texture.getWidth(), 0f, 1f);
			this.maxY = Maths.linearConversion(textureRect.y +textureRect.height, 0f, texture.getWidth(), 0f, 1f);
		}
	}
	
	public RectInt getPixelSize() {
		return pixelSize;
	}

	private void defaultUVS() {
		this.minX = 0f;
		this.minY = 0f;
		this.maxX = 1f;
		this.maxY = 1f;
	}

	public TextureRegion(Texture tex) {
		this(tex, null);
	}

	public Texture getTexture() {
		return texture;
	}

	@Override
	public void releaseMemory() {
		VGL.logger
		   .warn("To release resources, call the release method of the parent texture");
	}

	@Override
	public boolean isDisposed() {
		return texture.isDisposed();
	}

	@Override
	public ResourceState getResourceState() {
		return texture.getResourceState();
	}

	public float getMinX() {
		return minX;
	}

	public float getMinY() {
		return minY;
	}

	public float getMaxX() {
		return maxX;
	}

	public float getMaxY() {
		return maxY;
	}
}
