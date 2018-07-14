package vgl.core.gfx.renderable;

import vgl.maths.geom.Size2f;

abstract public class Renderable2D {

	protected Size2f size;

	public float getWidth() {
		return size.width;
	}

	public float getHeight() {
		return size.height;
	}

}
