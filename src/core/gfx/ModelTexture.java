package vgl.core.gfx;

public class ModelTexture {
	private final int	textureID;

	private float		shineDamper				= 1;
	private float		reflectivity;

	private boolean		hasTransparency			= false;
	private boolean		fakeLightningEnabled	= false;

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(final float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(final float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public ModelTexture(final int id) {
		textureID = id;
	}

	public int getTextureID() {
		return textureID;
	}

	public void setHasTransparency(final boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

	public boolean isTransparent() {
		return hasTransparency;
	}

	public boolean isFakeLightningEnabled() {
		return fakeLightningEnabled;
	}

	public void setFakeLightningEnabled(final boolean fakeLightningEnabled) {
		this.fakeLightningEnabled = fakeLightningEnabled;
	}
}
