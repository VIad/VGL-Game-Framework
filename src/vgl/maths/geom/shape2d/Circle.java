package vgl.maths.geom.shape2d;

import vgl.maths.vector.Vector2f;

public class Circle extends CircularShape {

	public Circle(Vector2f position, float radius) {
		super(position, radius);
	}

	public Circle(Vector2f position, float radius, int resolution) {
		super(position, radius, radius, resolution);
	}
	
	public Circle(float x, float y, float radius) {
		super(x, y, radius, radius, 30);
	}

	public Circle(float x, float y, float radius, int resolution) {
		super(new Vector2f(x, y), radius, radius, resolution);
	}

}
