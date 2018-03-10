package vgl.core.gfx.layer;

import vgl.core.gfx.render.IRenderer2D;
import vgl.core.gfx.render.Renderer2D;
import vgl.maths.vector.Vector2f;

public final class Layer2D extends ILayer2D {

	public Layer2D(IRenderer2D layerRenderer, float maxXProj, float maxYProj) {
		super(layerRenderer, maxXProj, maxYProj);
	}

	public Layer2D(IRenderer2D layerRenderer, Vector2f bottomRight) {
		super(layerRenderer, bottomRight);
	}

	public Layer2D(Vector2f bottomRight) {
		super(new Renderer2D(), bottomRight);
	}

	public Layer2D(float bottomRightX, float bottomRightY) {
		super(new Renderer2D(), bottomRightX, bottomRightY);
	}

	@Override
	public void update() {
	}

	@Override
	public void render(GFX2D graphics) {

	}

}
