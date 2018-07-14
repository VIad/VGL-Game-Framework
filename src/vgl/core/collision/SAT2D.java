package vgl.core.collision;

import java.util.List;

import vgl.maths.geom.GeomUtils;
import vgl.maths.geom.OrthoProjection;
import vgl.maths.geom.shape2d.IRect;
import vgl.maths.geom.shape2d.Polygon;
import vgl.maths.vector.Vector2f;

public class SAT2D implements ICollisionEngine2D {

	@Override
	public CollisionReport2D testPolygonIntersection(Polygon first, Polygon second) {
		float overlap = Float.MAX_VALUE;
		boolean allProjectionsInsideA = true;
		boolean allProjectionsInsideB = true;

		Vector2f smallest = null;
		List<Vector2f> axes1 = GeomUtils.getNormals(first);
		List<Vector2f> axes2 = GeomUtils.getNormals(second);
		for (Vector2f axis : axes1) {
			OrthoProjection p1 = first.project(axis);
			OrthoProjection p2 = second.project(axis);
			if (!p1.overlaps(p2))
				return new CollisionReport2D();
			else {
				if(!p1.contains(p2)) {
					allProjectionsInsideA = false;
				}
				if(!p2.contains(p1))
					allProjectionsInsideB = false;

				float o = p1.getOverlap(p2);

				if (p1.contains(p2) || p2.contains(p1)) {

					float mins = Math.abs(p1.min - p2.min);
					float maxs = Math.abs(p1.max - p2.max);

					if (mins < maxs) {
						o += mins;
					} else {
						o += maxs;
					}
				}

				if (o < overlap) {
					overlap = o;
					smallest = axis;
				}
			}
		}
		for (Vector2f axis : axes2) {
			OrthoProjection p1 = first.project(axis);
			OrthoProjection p2 = second.project(axis);
			if (!p1.overlaps(p2))
				return new CollisionReport2D();
			else {
				
				if(!p1.contains(p2)) {
					allProjectionsInsideA = false;
				}
				if(!p2.contains(p1))
					allProjectionsInsideB = false;
				
				float o = p1.getOverlap(p2);
				if (p1.contains(p2) || p2.contains(p1)) {
					float mins = Math.abs(p1.min - p2.min);
					float maxs = Math.abs(p1.max - p2.max);
					// NOTE: Perhaps negate the separating axis 
					if (mins < maxs) {
						o += mins;
					} else {
						o += maxs;
					}
				}

				if (o < overlap) {
					overlap = o;
					smallest = axis;
				}
			}
		}
		return new CollisionReport2D()
				           .collided(true)
				           .firstContainsSecond(allProjectionsInsideA)
				           .secondContainsFirst(allProjectionsInsideB)
				           .mtv(new CollisionReport2D.MTV(smallest, overlap));
	}

	@Override
	public CollisionReport2D testRectangleIntersection(IRect rect1, IRect rect2) {
		return new CollisionReport2D()
				            .collided(rect1.intersects(rect2))
				            .mtv(null)
				            .firstContainsSecond(rect1.contains(rect2))
				            .secondContainsFirst(rect2.contains(rect1));
	}

	//TODO
	@Override
	public boolean testCircularCollision() {
		// TODO Auto-generated method stub
		return false;
	}

}
