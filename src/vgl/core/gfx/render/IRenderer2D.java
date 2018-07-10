package vgl.core.gfx.render;

import vgl.core.gfx.Color;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;

public interface IRenderer2D {

	void begin();
	
	void dispose();

	void end();

	void render();

	void setScaling(float projWidth, float projHeight);

	default IRenderer2D draw(Renderable2D renderable, float x, float y) {
		draw(renderable, x, y, renderable.getWidth(), renderable.getHeight(), null);
		return this;
	}

	default IRenderer2D draw(Renderable2D renderable, float x, float y, float width, float height) {
		draw(renderable, x, y, width, height, null);
		return this;
	}

	default IRenderer2D draw(Renderable2D renderable, float x, float y, Matrix4f transformation) {
		draw(renderable, x, y, renderable.getWidth(), renderable.getHeight(), transformation);
		return this;
	}

	default IRenderer2D draw(Renderable2D renderable, Vector2f position) {
		draw(renderable, position.x, position.y, renderable.getWidth(), renderable.getHeight(), null);
		return this;
	}
	
	IRenderer2D drawLine(float x0, float y0, float x1, float y1, float thiccness, Color col);
	
	IRenderer2D draw(Renderable2D renderable, float x, float y, float width, float height, Matrix4f transformation);
	
	IRenderer2D drawText(String text, float x, float y, vgl.core.gfx.font.IFont font);
	
	IRenderer2D usingOverflowPolicy(OverflowPolicy policy);
	
	public enum OverflowPolicy{
		DO_RENDER,
		DO_THROW_EXCEPTION,
		UNSPECIFIED;
	}
}
