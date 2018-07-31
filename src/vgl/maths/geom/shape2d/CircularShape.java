package vgl.maths.geom.shape2d;

import vgl.core.internal.Checks;
import vgl.maths.vector.Vector2f;

public class CircularShape implements Shape2D {

	protected Polygon wrapper;
	
	protected float rx, ry;

	public CircularShape(float x, float y, float rx, float ry, int resolution) {
		wrapper = new Polygon();
		Checks.doAssert(resolution >= 3, "A shape can not have less than 3 vertices");
		for (int i = 0; i < resolution; i++)
			wrapper.addVertex(x + rx + (float) Math.cos(2 * Math.PI * i / resolution) * rx,
			        		  y + ry + (float) Math.sin(2 * Math.PI * i / resolution) * ry);

	}

	public CircularShape(Vector2f position, float rx, float ry, int resolution) {
		this(position.x, position.y, rx, ry, resolution);
	}

	public CircularShape(float x, float y, float rx, float ry) {
		this(x, y, rx, ry, 30);
	}

	public CircularShape(Vector2f position, float radius) {
		this(position.x, position.y, radius, radius, 30);
	}

	public CircularShape(CircularShape other) {

	}

	@Override
	public RectFloat bounds() {
		return wrapper.bounds();
	}

	@Override
	public Polygon toPolygon() {
		return wrapper;
	}
	
	public float getRadiusX() {
		return rx;
	}
	
	public float getRadiusY() {
		return ry;
	}

	@Override
	@SuppressWarnings("unchecked")
	public CircularShape copy() {
		return new CircularShape(this);
	}

	@Override
	public boolean contains(Vector2f point) {
		return wrapper.contains(point);
	}

}
