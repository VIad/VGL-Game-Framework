package vgl.core.collision;

import vgl.maths.geom.shape2d.IRect;
import vgl.maths.geom.shape2d.Polygon;
import vgl.maths.geom.shape2d.Shape2D;

public interface ICollisionEngine2D {

	CollisionReport2D testPolygonIntersection(Polygon p1, Polygon p2);
	
	CollisionReport2D testRectangleIntersection(IRect rect1, IRect rect2);
	
	default CollisionReport2D testShapeIntersection(Shape2D first, Shape2D second) {
		if(first instanceof IRect && second instanceof IRect)
			return testRectangleIntersection((IRect) first, (IRect) second);
		return testPolygonIntersection( first.toPolygon(), 
				                       second.toPolygon());
	}
	
	boolean testCircularCollision();
	
}
