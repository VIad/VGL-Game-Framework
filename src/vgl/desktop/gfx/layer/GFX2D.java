package vgl.desktop.gfx.layer;

import java.nio.file.Files;
import java.nio.file.Path;

import vgl.core.geom.Transform2D;
import vgl.core.gfx.Color;
import vgl.core.gfx.render.IRenderer2D;
import vgl.core.gfx.renderable.ImageSprite;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.desktop.gfx.font.VFont;
import vgl.maths.vector.Vector2f;

public class GFX2D {

	private IRenderer2D	renderer;

	private ILayer2D	layer;

	private VFont		font;

	private Color		color;

	GFX2D(ILayer2D layer) {
		this.layer = layer;
		this.renderer = layer.layerRenderer;
	}

	public void setFont(VFont font) {
		if (font == null)
			throw new NullPointerException("font >> null");
		this.font = font;
	}

	public VFont getFont() {
		return font;
	}

	public void setColor(Color color) {
		if (font == null)
			throw new NullPointerException("color >> null");
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void draw(Renderable2D renderable, float x, float y) {
		layer.submitSprite(renderable, x, y, renderable.getWidth(), renderable.getHeight(), null);
	}

	public void renderSprite(Renderable2D renderable, float x, float y, float width, float height) {
		layer.submitSprite(renderable, x, y, width, height, null);
	}

	public void renderSprite(Renderable2D renderable, Vector2f position) {
		layer.submitSprite(renderable, position.x, position.y, renderable.getWidth(), renderable.getHeight(), null);
	}

	public void renderSprite(Renderable2D renderable, Transform2D spriteTransform) {
		layer.submitSprite(renderable, spriteTransform.position().x, spriteTransform.position().y,
		        renderable.getWidth(), renderable.getHeight(), spriteTransform);
	}

}
