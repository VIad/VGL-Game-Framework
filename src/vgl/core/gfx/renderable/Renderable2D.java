package vgl.core.gfx.renderable;

import vgl.maths.geom.Size2f;

public interface Renderable2D {

	default float getWidth() {
		return getSize().width;
	}

	default float getHeight() {
		return getSize().height;
	}

	static Renderable2D create(float w, float h) {
		return new Renderable2D() {
			
			@Override
			public Size2f getSize() {
				return new Size2f(w, h);
			}
		};
	}
	
	vgl.maths.geom.Size2f getSize();
}
