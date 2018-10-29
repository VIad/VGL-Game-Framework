package vgl.core.gfx.layer;

import java.util.Collections;

import vgl.core.exception.VGLGraphicsException;
import vgl.core.gfx.Color;
import vgl.core.gfx.font.FontSpecifics;
import vgl.core.gfx.font.IFont;
import vgl.core.gfx.gl.TextureRegion;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.maths.geom.GeomUtils;
import vgl.maths.geom.Size2i;
import vgl.maths.geom.Transform2D;
import vgl.maths.geom.shape2d.Polygon;
import vgl.maths.geom.shape2d.RectFloat;
import vgl.maths.geom.shape2d.Shape2D;
import vgl.maths.geom.shape2d.Triangle;
import vgl.maths.vector.Vector2f;

public class GFX2D {

	private ILayer2D	layer;

	private IFont		font;

	private Color		color;
	
	private Transform2D transform;

	GFX2D(ILayer2D layer) {
		this.layer = layer;
		setColor(Color.CYAN);
	}
	
	public void setTransform(Transform2D transform) {
		if (transform == null)
			throw new NullPointerException("Transform >> null");
		this.transform = transform;
	}

	public Transform2D getTransform() {
		return transform;
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

	public void fillTriangle(Triangle triangle) {
		layer.layerRenderer.drawTriangle(triangle, color);
	}

	public void fill(Shape2D shape) {
		if (shape instanceof RectFloat)
			fillRect((RectFloat) shape);
		else if (shape instanceof Triangle)
			fillTriangle((Triangle) shape);
		fillPolygon(shape.toPolygon());
	}

	public void fillPolygon(Polygon polygon) {
		GeomUtils.Triangulator
				 .triangulate(polygon)
				 .orElseGet(Collections::emptyList)
				 .forEach(tri -> layer.layerRenderer.drawTriangle(tri, color));
	}

	public void fillRect(RectFloat rectangle) {
		fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public void fillRect(float x, float y, float width, float height) {
		layer.layerRenderer.draw(Renderable2D.create(width, height), x, y, width, height, color, null, false);
	}

	public void drawRectangle(RectFloat rectangle) {
		drawRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	public void drawTextureRegion(TextureRegion texRegion, float x, float y, float w, float h) {
		layer.layerRenderer.drawTextureRegion(texRegion, x, y, w, h);
	}

	public void drawRectangle(float x, float y, float width, float height) {
		drawRectangle(x, y, width, height, 0.01f);
	}

	public void drawRectangle(float x, float y, float width, float height, float thiccness) {
		layer.layerRenderer.drawLine(x, y, x + width, y, thiccness, color) // Top
		                   .drawLine(x, y + height, x + width, y + height, thiccness, color)// Bottom
		                   .drawLine(x + thiccness, y, x + thiccness, y + height, thiccness, color)// Left
		                   .drawLine(x + width - thiccness, y, x + width - thiccness, y + height, thiccness, color);// Right
	}

	public void drawLine(float x0, float y0, float x1, float y1, float thiccness) {
		layer.layerRenderer.drawLine(x0, y0, x1, y1, thiccness, color);
	}

	public void drawSprite(Renderable2D renderable, float x, float y) {
		layer.layerRenderer.draw(renderable, x, y, renderable.getWidth(), renderable.getHeight(), null, true);
	}

	public void drawSprite(Renderable2D renderable, float x, float y, float width, float height) {
		layer.layerRenderer.draw(renderable, x, y, width, height, null, false);
	}

	public void drawSprite(Renderable2D renderable, Vector2f position) {
		layer.layerRenderer.draw(renderable, position);
	}

	public void drawSprite(Renderable2D renderable,float x, float y, Transform2D spriteTransform) {
		layer.layerRenderer.draw(renderable, x, y,
		        spriteTransform.toMatrix());
	}
	
	public void drawSprite(Renderable2D renderable, float x, float y, float width, float height, Transform2D transform) {
		layer.layerRenderer
		     .draw(renderable, x, y, width,height, transform.toMatrix(), false);
	}
	

	public void drawText(String string, float x, float y, IFont font) {
		layer.layerRenderer.drawText(string, x, y, font, color);
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
	
	public void drawTextCentered(String text, float x, float y) {
		drawTextCentered(text, x, y, font);
	}
	
	public void drawTextCentered(String text, float x, float y, IFont font) {
		FontSpecifics fontInfo = font.getFontSpecifics();
		Size2i textDim = fontInfo.getTextDimensions(text);
		x = x - (textDim.width / layer.renderContext.user2Device().x) / 2;
		y = y - (textDim.height / layer.renderContext.user2Device().y) / 2;

		drawText(text, x, y, font);
	}

}
