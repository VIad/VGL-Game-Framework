package vgl.core.gfx.renderable;

import vgl.core.gfx.Color;
import vgl.maths.vector.Vector2f;

public final class ColoredSprite extends Renderable2D {

	private Color	color;
	private Color[]	grad;
	private boolean	gradientSprite;

	public ColoredSprite(Color color, float width, float height) {
		this.size = new Vector2f(width, height);
		this.color = color;
	}

	public ColoredSprite(float width, float height) {
		this.size = new Vector2f(width, height);
		this.color = Color.WHITE;
	}

	public ColoredSprite(float width, float height, Color... gradient) {
		this.size = new Vector2f(width, height);
		if (gradient.length != 4 && gradient.length != 2) {
			throw new vgl.core.exception.VGLRuntimeException(
			        "You need to specify either 2 or 4 colors to create gradient colored sprite");
		}
		this.grad = gradient;
		this.color = gradient[0];
		this.gradientSprite = true;
	}

	public Color[] getGrad() {
		return grad;
	}

	public Color getColor() {
		return color;
	}

	public boolean isGradientSprite() {
		return gradientSprite;
	}

}
