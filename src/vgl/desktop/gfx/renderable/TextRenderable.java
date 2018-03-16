package vgl.desktop.gfx.renderable;

import vgl.core.annotation.VGLInternal;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.desktop.gfx.font.VFont;
import vgl.maths.vector.Vector2f;

@VGLInternal
@Deprecated
public class TextRenderable extends Renderable2D {

	private float	x, y;

	private VFont	font;
	private String	text;

	@VGLInternal
	public TextRenderable(String text, float x, float y, VFont font) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.font = font;
	}

	@VGLInternal
	public TextRenderable(String text, Vector2f pos, VFont font) {
		this(text, pos.x, pos.y, font);
	}

	@VGLInternal
	public VFont getFont() {
		return font;
	}

	@VGLInternal
	public String getText() {
		return text;
	}

	@VGLInternal
	public float getX() {
		return x;
	}

	@VGLInternal
	public float getY() {
		return y;
	}
}
