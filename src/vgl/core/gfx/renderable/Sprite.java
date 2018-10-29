package vgl.core.gfx.renderable;

import vgl.core.gfx.gl.Texture;
import vgl.core.gfx.gl.TextureRegion;
import vgl.maths.geom.Size2f;
import vgl.maths.vector.Vector2f;

public class Sprite implements Renderable2D {

	private Texture		tex;
	private Vector2f[]	UVs;
	private Size2f		size;

	public Sprite(TextureRegion texRegion) {
		this.tex = texRegion.getTexture();
		this.UVs = new Vector2f[4];
		this.size = new Size2f(texRegion.getPixelSize().width, texRegion.getPixelSize().height);
		this.UVs[0] = new Vector2f(texRegion.getMinX(), texRegion.getMinY());
		this.UVs[1] = new Vector2f(texRegion.getMinX(), texRegion.getMaxY());
		this.UVs[2] = new Vector2f(texRegion.getMaxX(), texRegion.getMaxY());
		this.UVs[3] = new Vector2f(texRegion.getMaxX(), texRegion.getMinY());
	}
	
	public Sprite(Texture texture) {
		this.tex = texture;
		this.size = new Size2f(texture.getWidth(), texture.getHeight());
		this.UVs = defaultUVS();
	}

	public Size2f getSize() {
		return size;
	}

	private static Vector2f[] defaultUVs;

	static {
		defaultUVs = new Vector2f[4];
		defaultUVs[0] = new Vector2f(0, 0);
		defaultUVs[1] = new Vector2f(0, 1);
		defaultUVs[2] = new Vector2f(1, 1);
		defaultUVs[3] = new Vector2f(1, 0);
	}

	public static Vector2f[] defaultUVS() {
		return defaultUVs;
	}

	public Texture getTexture() {
		return tex;
	}

	public Vector2f[] getUVs() {
		return UVs;
	}

}
