package vgl.maths.geom.shape2d;

import vgl.core.collision.Collider2D;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.maths.geom.Size2f;
import vgl.maths.vector.Vector2f;

public interface Shape2D extends Renderable2D {

	RectFloat bounds();

	@Override
	default Size2f getSize() {
		return bounds().getSize();
	}

	default Vector2f relativeCenter() {
		return bounds().getCenter();
	}

	default boolean contains(Shape2D other) {
		return Collider2D.checkShapeContainment(this, other);
	}

	default boolean intersects(Shape2D other) {
		return Collider2D.checkShapeIntersection(this, other);
	}

	Polygon toPolygon();

	<T extends Shape2D> T copy();

	boolean contains(Vector2f point);

}
