package vgl.core.gfx.renderable;


import vgl.core.gfx.gl.Texture;
import vgl.maths.geom.Size2f;
import vgl.maths.vector.Vector2f;

public class Sprite extends Renderable2D {

	private Texture	tex;
	private Vector2f[]	UVs;

	public Sprite(Texture texture) {
		this.tex = texture;
		this.size = new Size2f(texture.getWidth(), texture.getHeight());
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

	public Texture getTexture() {
		return tex;
	}

	public Vector2f[] getUVs() {
		return UVs;
	}

}
