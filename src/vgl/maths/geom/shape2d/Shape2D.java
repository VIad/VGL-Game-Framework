package vgl.maths.geom.shape2d;

import vgl.maths.vector.Vector2f;

public interface Shape2D {

	RectFloat bounds();
	
	default Vector2f relativeCenter() {
		return bounds().getCenter();
	}
	
	Polygon toPolygon();
	
	<T extends Shape2D> T copy();
	
	boolean contains(Vector2f point);
	
	boolean intersects(Shape2D other);
}
