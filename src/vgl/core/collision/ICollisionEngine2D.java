package vgl.core.collision;

import vgl.maths.geom.shape2d.IRect;
import vgl.maths.geom.shape2d.Polygon;
import vgl.maths.geom.shape2d.Shape2D;

public interface ICollisionEngine2D {

	CollisionReport2D testPolygonIntersection(Polygon p1, Polygon p2);
	
	CollisionReport2D testRectangleIntersection(IRect rect1, IRect rect2);
	
	CollisionReport2D testCircularCollision();
	
}
