package vgl.core.gfx.render;

import vgl.core.gfx.renderable.Renderable2D;
import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;

public interface IRenderer2D {

	void begin();

	void end();

	void render();

	void setScaling(float projWidth, float projHeight);

	void renderSprite(Renderable2D renderable, float x, float y);

	void renderSprite(Renderable2D renderable, float x, float y, float width, float height);

	void renderSprite(Renderable2D renderable, float x, float y, Matrix4f transformation);

	void renderSprite(Renderable2D renderable, float x, float y, float width, float height, Matrix4f transformation);

	void renderSprite(Renderable2D renderable, Vector2f position);

	void drawText(String text, float x, float y, vgl.core.gfx.font.IFont font);
}
