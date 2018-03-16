package vgl.core.gfx.renderable;

import vgl.maths.vector.Vector2f;
import vgl.platform.gl.GLTexture;

public class ImageSprite extends Renderable2D {

	private GLTexture	tex;
	private Vector2f[]	UVs;

	public ImageSprite(GLTexture texture) {
		this.tex = texture;
		this.size = new Vector2f(texture.getWidth(), texture.getHeight());
		this.UVs = defaultUVS();
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

	public GLTexture getTexture() {
		return tex;
	}

	public Vector2f[] getUVs() {
		return UVs;
	}

}
