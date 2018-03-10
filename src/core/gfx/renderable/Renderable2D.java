package vgl.core.gfx.renderable;

import vgl.maths.vector.Vector2f;

abstract public class Renderable2D {

	protected Vector2f size;

	public float getWidth() {
		return size.x;
	}

	public float getHeight() {
		return size.y;
	}

}
