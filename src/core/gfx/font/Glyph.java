package vgl.core.gfx.font;

public class Glyph {

	private float	advance;
	private float	u0, v0, u1, v1;

	Glyph(int atlasW, int atlasH, float advance, float u0, float v0, float u1, float v1) {
		this.advance = advance;
		this.u0 = u0 / (float) atlasW * 1.0f;
		this.v0 = v0 / (float) atlasH * 1.0f;
		this.u1 = u1 / (float) atlasW * 1.0f;
		this.v1 = v1 / (float) atlasH * 1.0f;
	}

	public float getAdvance() {
		return advance;
	}

	public float getU0() {
		return u0;
	}

	public float getU1() {
		return u1;
	}

	public float getV0() {
		return v0;
	}

	public float getV1() {
		return v1;
	}
}
