package vgl.core.gfx.layer;

import org.apache.commons.io.filefilter.IOFileFilter;

import vgl.core.exception.VGLGraphicsException;
import vgl.core.geom.RectFloat;
import vgl.core.geom.Size2i;
import vgl.core.geom.Transform2D;
import vgl.core.gfx.Color;
import vgl.core.gfx.font.FontSpecifics;
import vgl.core.gfx.font.IFont;
import vgl.core.gfx.renderable.ColoredSprite;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.maths.vector.Vector2f;

public class GFX2D {

	private ILayer2D	layer;

	private IFont		font;

	private Color		color;

	GFX2D(ILayer2D layer) {
		this.layer = layer;
		setColor(Color.CYAN);
	}

	public void setFont(IFont font) {
		if (font == null)
			throw new NullPointerException("font >> null");
		this.font = font;
	}

	public IFont getFont() {
		return font;
	}

	public void setColor(Color color) {
		if (color == null)
			throw new NullPointerException("color >> null");
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void fillRectangle(RectFloat rectangle) {
		fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public void fillRect(float x, float y, float width, float height) {
		layer.layerRenderer.draw(new ColoredSprite(getColor(), width, height), x, y, width, height, null);
	}

	public void drawRectangle(RectFloat rectangle) {
		drawRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public void drawRectangle(float x, float y, float width, float height) {
		drawRectangle(x, y, width, height, 0.01f);
	}

	public void drawRectangle(float x, float y, float width, float height, float thiccness) {
		layer.layerRenderer.drawLine(x, y, x + width, y, thiccness, getColor()) // Top line
		                   .drawLine(x, y + height, x + width, y + height, thiccness, getColor())// Bottom line
		                   .drawLine(x + thiccness, y, x + thiccness, y + height, thiccness, getColor())// Left line
		                   .drawLine(x + width - thiccness, y, x + width - thiccness, y + height, thiccness,getColor());// Right line

	}

	public void drawLine(float x0, float y0, float x1, float y1, float thiccness) {
		layer.layerRenderer.drawLine(x0, y0, x1, y1, thiccness, color);
	}

	public void drawSprite(Renderable2D renderable, float x, float y) {
		layer.layerRenderer.draw(renderable, x, y, renderable.getWidth(), renderable.getHeight(), null);
	}

	public void drawSprite(Renderable2D renderable, float x, float y, float width, float height) {
		layer.layerRenderer.draw(renderable, x, y, width, height, null);
	}

	public void drawSprite(Renderable2D renderable, Vector2f position) {
		layer.layerRenderer.draw(renderable, position);
	}

	public void drawSprite(Renderable2D renderable, Transform2D spriteTransform) {
		layer.layerRenderer.draw(renderable, spriteTransform.position().x, spriteTransform.position().y,
		        spriteTransform.toMatrix());
	}

	public void drawText(String string, float x, float y, IFont font) {
		layer.layerRenderer.drawText(string, x, y, font);
	}

	public void drawText(String text, Vector2f pos, IFont font) {
		drawText(text, pos.x, pos.y, font);
	}
	
	public void drawText(String string, float x, float y) {
		if (font == null)
			throw new VGLGraphicsException("No font was found, set font first using GFX2D.setFont()");
		drawText(string, x, y, getFont());
	}
	
	public void drawTextCentered(String text, Vector2f pos, IFont font) {
		drawTextCentered(text, pos.x, pos.y, font);
	}

	public void drawTextCentered(String text, float x, float y, IFont font) {
		FontSpecifics fontInfo = font.getFontSpecifics();
		Size2i textDim = fontInfo.getTextDimensions(text);
		x = x - (textDim.width / layer.renderContext.user2Device().x) / 2;
		y = y - (textDim.height / layer.renderContext.user2Device().y) / 2;

		drawText(text, x, y, font);
	}

}
